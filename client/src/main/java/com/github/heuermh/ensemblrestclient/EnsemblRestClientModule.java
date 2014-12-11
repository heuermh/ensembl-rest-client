/*

    ensembl-rest-client  Java client for the Ensembl REST API.
    Copyright (c) 2013-2014 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package com.github.heuermh.ensemblrestclient;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.fasterxml.jackson.core.JsonFactory;

import javax.annotation.concurrent.Immutable;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;

/**
 * Ensembl REST client module.
 *
 * @author  Michael Heuer
 */
@Immutable
public final class EnsemblRestClientModule extends AbstractModule {
    private final String endpointUrl;

    /**
     * Default endpoint URL, <code>http://rest.ensembl.org/</code>.
     *
     * @since 2.0
     */
    public static final String DEFAULT_ENDPOINT_URL = "http://rest.ensembl.org/";


    /**
     * Create a new Ensembl REST client module with the default endpoint URL.
     */
    public EnsemblRestClientModule() {
        this(DEFAULT_ENDPOINT_URL);
    }

    /**
     * Create a new Ensembl REST client module with the specified endpoint URL.
     *
     * @since 2.0
     * @param endpointUrl endpoint URL, must not be null
     */
    public EnsemblRestClientModule(final String endpointUrl) {
        checkNotNull(endpointUrl);
        this.endpointUrl = endpointUrl;
    }


    @Override
    protected void configure() {
        bind(String.class).annotatedWith(EndpointURL.class).toInstance(endpointUrl);
    }

    @Provides @Singleton
    static JsonFactory createJsonFactory() {
        return new JsonFactory();
    }

    @Provides @Singleton
    static ErrorHandler createErrorHandler() {
        return new EnsemblRestClientErrorHandler();
    }

    @Provides @Singleton
    static ArchiveService createArchiveService(@EndpointURL final String endpointUrl, final JsonFactory jsonFactory, final ErrorHandler errorHandler) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonArchivedSequenceConverter(jsonFactory))
            .build().create(ArchiveService.class);
    }

    @Provides @Singleton
    static LookupService createLookupService(@EndpointURL final String endpointUrl, final JsonFactory jsonFactory, final ErrorHandler errorHandler) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonLookupConverter(jsonFactory))
            .build().create(LookupService.class);
    }

    @Provides @Singleton
    static OverlapService createOverlapService(@EndpointURL final String endpointUrl, final JsonFactory jsonFactory, final ErrorHandler errorHandler) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonOverlapConverter(jsonFactory))
            .build().create(OverlapService.class);
    }

    @Provides @Singleton
    static VariationService createVariationService(@EndpointURL final String endpointUrl, final JsonFactory jsonFactory, final ErrorHandler errorHandler) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonVariationConverter(jsonFactory))
            .build().create(VariationService.class);
    }

    @Provides @Singleton
    static SequenceService createSequenceService(@EndpointURL final String endpointUrl, final JsonFactory jsonFactory, final ErrorHandler errorHandler) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonSequenceConverter(jsonFactory))
            .build().create(SequenceService.class);
    }
}

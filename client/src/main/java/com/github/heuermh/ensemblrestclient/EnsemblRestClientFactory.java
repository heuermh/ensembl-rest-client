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

import com.fasterxml.jackson.core.JsonFactory;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;

/**
 * Ensembl REST client factory, for clients unable to use Guice injection.
 *
 * @since 1.2
 * @author  Michael Heuer
 */
public final class EnsemblRestClientFactory {
    private final String defaultEndpointUrl;
    private final JsonFactory jsonFactory;
    private final ErrorHandler errorHandler;

    /**
     * Default endpoint URL, <code>http://rest.ensembl.org/</code>.
     *
     * @since 1.3
     */
    public static final String DEFAULT_ENDPOINT_URL = "http://rest.ensembl.org/";


    /**
     * Create a new Ensembl REST client factory.
     */
    public EnsemblRestClientFactory() {
        this(DEFAULT_ENDPOINT_URL, new JsonFactory());
    }

    /**
     * Create a new Ensembl REST client factory with the specified default endpoint URL.
     *
     * @since 1.3
     * @param defaultEndpointUrl default endpoint URL, must not be null
     */
    public EnsemblRestClientFactory(final String defaultEndpointUrl) {
        this(defaultEndpointUrl, new JsonFactory());
    }

    /**
     * Create a new Ensembl REST client factory with the specified JsonFactory.
     *
     * @param jsonFactory JsonFactory, must not be null
     */
    public EnsemblRestClientFactory(final JsonFactory jsonFactory) {
        this(DEFAULT_ENDPOINT_URL, jsonFactory);
    }

    /**
     * Create a new Ensembl REST client factory with the specified default endpoint URL and JsonFactory.
     *
     * @since 1.3
     * @param defaultEndpointUrl default endpoint URL, must not be null
     * @param jsonFactory JsonFactory, must not be null
     */
    public EnsemblRestClientFactory(final String defaultEndpointUrl, final JsonFactory jsonFactory) {
        checkNotNull(defaultEndpointUrl);
        checkNotNull(jsonFactory);
        this.defaultEndpointUrl = defaultEndpointUrl;
        this.jsonFactory = jsonFactory;
        this.errorHandler = new EnsemblRestClientErrorHandler();
    }


    /**
     * Create and return a new archive service with the default endpoint URL.
     *
     * @since 2.0
     * @return a new archive service with the default endpoint URL
     */
    public ArchiveService createArchiveService() {
        return createArchiveService(defaultEndpointUrl);
    }

    /**
     * Create and return a new archive service with the specified endpoint URL.
     *
     * @since 2.0
     * @param endpointUrl endpoint URL, must not be null
     * @return a new archive service with the specified endpoint URL
     */
    public ArchiveService createArchiveService(final String endpointUrl) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonArchivedSequenceConverter(jsonFactory))
            .build().create(ArchiveService.class);
    }

    /**
     * Create and return a new lookup service with the default endpoint URL.
     *
     * @since 1.3
     * @return a new lookup service with the default endpoint URL
     */
    public LookupService createLookupService() {
        return createLookupService(defaultEndpointUrl);
    }

    /**
     * Create and return a new lookup service with the specified endpoint URL.
     *
     * @param endpointUrl endpoint URL, must not be null
     * @return a new lookup service with the specified endpoint URL
     */
    public LookupService createLookupService(final String endpointUrl) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonLookupConverter(jsonFactory))
            .build().create(LookupService.class);
    }

    /**
     * Create and return a new overlap service with the default endpoint URL.
     *
     * @since 2.0
     * @return a new overlap service with the default endpoint URL
     */
    public OverlapService createOverlapService() {
        return createOverlapService(defaultEndpointUrl);
    }

    /**
     * Create and return a new lookup service with the specified endpoint URL.
     *
     * @since 2.0
     * @param endpointUrl endpoint URL, must not be null
     * @return a new overlap service with the specified endpoint URL
     */
    public OverlapService createOverlapService(final String endpointUrl) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonOverlapConverter(jsonFactory))
            .build().create(OverlapService.class);
    }

    /**
     * Create and return a new variation service with the default endpoint URL.
     *
     * @since 1.3
     * @return a new variation service with the default endpoint URL
     */
    public VariationService createVariationService() {
        return createVariationService(defaultEndpointUrl);
    }

    /**
     * Create and return a new variation service with the specified endpoint URL.
     *
     * @param endpointUrl endpoint URL, must not be null
     * @return a new variation service with the specified endpoint URL
     */
    public VariationService createVariationService(final String endpointUrl) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonVariationConverter(jsonFactory))
            .build().create(VariationService.class);
    }

    /**
     * Create and return a new sequence service with the default endpoint URL.
     *
     * @since 1.3
     * @return a new sequence service with the default endpoint URL
     */
    public SequenceService createSequenceService() {
        return createSequenceService(defaultEndpointUrl);
    }

    /**
     * Create and return a new sequence service with the specified endpoint URL.
     *
     * @since 1.3
     * @param endpointUrl endpoint URL, must not be null
     * @return a new sequence service with the specified endpoint URL
     */
    public SequenceService createSequenceService(final String endpointUrl) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setErrorHandler(errorHandler)
            .setConverter(new JacksonSequenceConverter(jsonFactory))
            .build().create(SequenceService.class);
    }
}

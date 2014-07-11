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

    /**
     * Default endpoint URL, <code>http://beta.rest.ensembl.org/</code>.
     *
     * @since 1.3
     */
    public static final String DEFAULT_ENDPOINT_URL = "http://beta.rest.ensembl.org/";

    @Deprecated
    public static final String DEFAULT_SERVER_URL = DEFAULT_ENDPOINT_URL;


    /**
     * Create a new Ensembl REST client factory.
     */
    public EnsemblRestClientFactory() {
        this(DEFAULT_ENDPOINT_URL, new JsonFactory());
    }

    /**
     * Create a new Ensembl REST client factory with the specified default endpoint URL.
     *
     * @param defaultEndpointUrl default endpoint URL, must not be null
     * @since 1.3
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
     * @param defaultEndpointUrl default endpoint URL, must not be null
     * @param jsonFactory JsonFactory, must not be null
     * @since 1.3
     */
    public EnsemblRestClientFactory(final String defaultEndpointUrl, final JsonFactory jsonFactory) {
        checkNotNull(defaultEndpointUrl);
        checkNotNull(jsonFactory);
        this.defaultEndpointUrl = defaultEndpointUrl;
        this.jsonFactory = jsonFactory;
    }


    /**
     * Create and return a new feature service with the default endpoint URL.
     *
     * @return a new feature service with the default endpoint URL
     * @since 1.3
     */
    public FeatureService createFeatureService() {
        return createFeatureService(defaultEndpointUrl);
    }

    /**
     * Create and return a new feature service with the specified endpoint URL.
     *
     * @param endpointUrl endpoint URL, must not be null
     * @return a new feature service with the specified endpoint URL
     */
    public FeatureService createFeatureService(final String endpointUrl) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setConverter(new JacksonFeatureConverter(jsonFactory))
            .setErrorHandler(new EnsemblRestClientErrorHandler())
            .build().create(FeatureService.class);
    }

    /**
     * Create and return a new lookup service with the default endpoint URL.
     *
     * @return a new lookup service with the default endpoint URL
     * @since 1.3
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
            .setConverter(new JacksonLookupConverter(jsonFactory))
            .setErrorHandler(new EnsemblRestClientErrorHandler())
            .build().create(LookupService.class);
    }

    /**
     * Create and return a new variation service with the default endpoint URL.
     *
     * @return a new variation service with the default endpoint URL
     * @since 1.3
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
            .setConverter(new JacksonVariationConsequencesConverter(jsonFactory))
            .setErrorHandler(new EnsemblRestClientErrorHandler())
            .build().create(VariationService.class);
    }

    /**
     * Create and return a new sequence service with the default endpoint URL.
     *
     * @return a new sequence service with the default endpoint URL
     * @since 1.3
     */
    public SequenceService createSequenceService() {
        return createSequenceService(defaultEndpointUrl);
    }

    /**
     * Create and return a new sequence service with the specified endpoint URL.
     *
     * @param endpointUrl endpoint URL, must not be null
     * @return a new sequence service with the specified endpoint URL
     * @since 1.3
     */
    public SequenceService createSequenceService(final String endpointUrl) {
        return new RestAdapter.Builder()
            .setEndpoint(endpointUrl)
            .setConverter(new JacksonSequenceConverter(jsonFactory))
            .setErrorHandler(new EnsemblRestClientErrorHandler())
            .build().create(SequenceService.class);
    }
}

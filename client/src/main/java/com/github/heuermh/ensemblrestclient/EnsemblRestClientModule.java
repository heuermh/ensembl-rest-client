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

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.fasterxml.jackson.core.JsonFactory;

import retrofit.RestAdapter;

/**
 * Ensembl REST client module.
 *
 * @author  Michael Heuer
 */
public final class EnsemblRestClientModule extends AbstractModule {
    // todo:  allow endpoint URL as configurable property

    @Override
    protected void configure() {
        // empty
    }

    @Provides @Singleton
    static JsonFactory createJsonFactory() {
        return new JsonFactory();
    }

    @Provides @Singleton
    static FeatureService createFeatureService(final JsonFactory jsonFactory) {
        return new RestAdapter.Builder()
            .setEndpoint("http://beta.rest.ensembl.org/")
            .setConverter(new JacksonFeatureConverter(jsonFactory))
            .setErrorHandler(new EnsemblRestClientErrorHandler())
            .build().create(FeatureService.class);
    }

    @Provides @Singleton
    static LookupService createLookupService(final JsonFactory jsonFactory) {
        return new RestAdapter.Builder()
            .setEndpoint("http://beta.rest.ensembl.org/")
            .setConverter(new JacksonLookupConverter(jsonFactory))
            .setErrorHandler(new EnsemblRestClientErrorHandler())
            .build().create(LookupService.class);
    }

    @Provides @Singleton
    static VariationService createVariationService(final JsonFactory jsonFactory) {
        return new RestAdapter.Builder()
            .setEndpoint("http://beta.rest.ensembl.org/")
            .setConverter(new JacksonVariationConsequencesConverter(jsonFactory))
            .setErrorHandler(new EnsemblRestClientErrorHandler())
            .build().create(VariationService.class);
    }

    @Provides @Singleton
    static SequenceService createSequenceService(final JsonFactory jsonFactory) {
        return new RestAdapter.Builder()
            .setEndpoint("http://beta.rest.ensembl.org/")
            .setConverter(new JacksonSequenceConverter(jsonFactory))
            .setErrorHandler(new EnsemblRestClientErrorHandler())
            .build().create(SequenceService.class);
    }
}

/*

    ensembl-rest-client  Java client for the Ensembl REST API.
    Copyright (c) 2013 held jointly by the individual authors.

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
 */
public final class EnsemblRestClientModule extends AbstractModule {
    // todo:  allow server URL as configurable property

    @Override
    protected void configure() {
        // empty
    }

    @Provides @Singleton
    JsonFactory createJsonFactory() {
        return new JsonFactory();
    }

    @Provides @Singleton
    FeatureService createFeatureService(final JsonFactory jsonFactory) {
        return new RestAdapter.Builder()
            .setServer("http://beta.rest.ensembl.org/")
            .setConverter(new JacksonFeatureConverter(jsonFactory))
            .build().create(FeatureService.class);
    }

    @Provides @Singleton
    LookupService createLookupService(final JsonFactory jsonFactory) {
        return new RestAdapter.Builder()
            .setServer("http://beta.rest.ensembl.org/")
            .setConverter(new JacksonLookupConverter(jsonFactory))
            .build().create(LookupService.class);
    }

    @Provides @Singleton
    VariationService createVariationService(final JsonFactory jsonFactory) {
        return new RestAdapter.Builder()
            .setServer("http://beta.rest.ensembl.org/")
            .setConverter(new JacksonVariationConsequencesConverter(jsonFactory))
            .build().create(VariationService.class);
    }
}
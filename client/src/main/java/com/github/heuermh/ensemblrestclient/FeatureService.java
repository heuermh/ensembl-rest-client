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

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Feature service.  See <a href="http://beta.rest.ensembl.org">http://beta.rest.ensembl.org</a>.
 *
 * @author  Michael Heuer
 */
public interface FeatureService {

    /**
     * Return the variation feature with the specified identifier.
     *
     * @param species species
     * @param id id
     * @return the variation feature with the specified identifier
     */
    @GET("/feature/id/{id}?feature=variation")
    @Headers("Content-type: application/json")
    Variation variationFeature(@Query("species") String species, @Path("id") String id);

    /**
     * Return zero or more variation features that overlap with the specified region.
     *
     * @param species species
     * @param region region
     * @return zero or more variation features that overlap with the specified region
     */
    @GET("/feature/region/{species}/{region}?feature=variation")
    @Headers("Content-type: application/json")
    List<Variation> variationFeatures(@Path("species") String species, @Path("region") String region);
}

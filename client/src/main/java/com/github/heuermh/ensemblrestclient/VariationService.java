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

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Variation service.  See <a href="http://rest.ensembl.org">http://rest.ensembl.org</a>.
 *
 * @author  Michael Heuer
 */
public interface VariationService {

    /**
     * Return the variation with the specified identifier.
     * See <a href="http://rest.ensembl.org/documentation/info/variation_id">http://rest.ensembl.org/documentation/info/variation_id</a>.
     *
     * @since 2.0
     * @param species species
     * @param id id
     * @return the variation with the specified identifier
     * @throws EnsemblRestClientException if an error occurs
     */
    @GET("/variation/{species}/{id}")
    @Headers("Accept: application/json")
    Variation variation(@Path("species") String species, @Path("id") String id);

    /**
     * Fetch variant consequences based on a variation identifier.
     * See <a href="http://beta.rest.ensembl.org/documentation/info/vep_id">http://beta.rest.ensembl.org/documentation/info/vep_id</a>.
     *
     * @param species registry name/aliases used to restrict searches by
     * @param id variation id to look up; supports dbSNP, COSMIC, and HGMD identifiers
     * @return variant consequences for the specified species and variation identifier
     * @throws EnsemblRestClientException if an error occurs
     */
    @GET("/vep/{species}/id/{id}?canonical=1&protein=1&hgvs=1")
    @Headers("Accept: application/json")
    VariationConsequences consequences(@Path("species") String species, @Path("id") String id);

    /**
     * Fetch variant consequences.
     * See <a href="http://beta.rest.ensembl.org/documentation/info/vep">http://beta.rest.ensembl.org/documentation/info/vep</a>.
     *
     * @param species registry name/aliases used to restrict searches by
     * @param region region to mutate in the specified genome
     * @param allele allele to change the reference genome to
     * @return variant consequences for the specified species, region, and allele
     * @throws EnsemblRestClientException if an error occurs
     */
    @GET("/vep/{species}/region/{region}/{allele}?canonical=1&protein=1&hgvs=1")
    @Headers("Accept: application/json")
    VariationConsequences consequences(@Path("species") String species, @Path("region") String region, @Path("allele") String allele);
}

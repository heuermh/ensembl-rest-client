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
import retrofit.http.Query;

/**
 * Sequence service.  See <a href="http://rest.ensembl.org">http://rest.ensembl.org</a>.
 *
 * @since 1.3
 * @author  Michael Heuer
 */
public interface SequenceService {

    /**
     * Query for multiple types of sequence by its stable identifier.
     * See <a href="http://rest.ensembl.org/documentation/info/sequence_id">http://rest.ensembl.org/documentation/info/sequence_id</a>.
     *
     * @param species species
     * @param id id
     * @param type type
     * @return sequence for the specified identifier
     * @throws EnsemblRestClientException if an error occurs
     */
    @GET("/sequence/id/{id}")
    @Headers("Accept: application/json")
    Sequence sequence(@Query("species") String species, @Path("id") String id, @Query("type") String type);

    /**
     * Query for genomic sequence by its stable identifier.
     * See <a href="http://rest.ensembl.org/documentation/info/sequence_id">http://rest.ensembl.org/documentation/info/sequence_id</a>.
     *
     * @param species species
     * @param id id
     * @param type type
     * @param expand5Prime expand the sequence upstream of the sequence by this many basepairs, only valid if type is genomic
     * @param expand3Prime expand the sequence downstream of the sequence by this many basepairs, only valid if type is genomic
     * @param mask request the sequence masked for repeat sequences, hard will mask all repeats as N's and soft will mask repeats as lowercased characters, only valid if type is genomic
     * @return sequence for the specified identifier
     * @throws EnsemblRestClientException if an error occurs
     */
    @GET("/sequence/id/{id}?type=genomic")
    @Headers("Accept: application/json")
    Sequence sequence(@Query("species") String species,
                      @Path("id") String id,
                      @Query("type") String type,
                      @Query("expand_5prime") int expand5Prime,
                      @Query("expand_3prime") int expand3Prime,
                      @Query("mask") String mask);

    /**
     * Query for a region of genomic sequence based on its location.
     * See <a href="http://rest.ensembl.org/documentation/info/sequence_region">http://rest.ensembl.org/documentation/info/sequence_region</a>.
     *
     * @param species species
     * @param region region
     * @return genomic sequence for the specified region
     * @throws EnsemblRestClientException if an error occurs
     */
    @GET("/sequence/region/{species}/{region}")
    @Headers("Accept: application/json")
    Sequence sequence(@Path("species") String species, @Path("region") String region);

    /**
     * Query for a region of genomic sequence based on its location.
     * See <a href="http://rest.ensembl.org/documentation/info/sequence_region">http://rest.ensembl.org/documentation/info/sequence_region</a>.
     *
     * @param species species
     * @param region region
     * @param expand5Prime expand the sequence upstream of the sequence by this many basepairs
     * @param expand3Prime expand the sequence downstream of the sequence by this many basepairs
     * @param mask request the sequence masked for repeat sequences, hard will mask all repeats as N's and soft will mask repeats as lowercased characters
     * @return genomic sequence for the specified region
     * @throws EnsemblRestClientException if an error occurs
     */
    @GET("/sequence/region/{species}/{region}")
    @Headers("Accept: application/json")
    Sequence sequence(@Path("species") String species,
                      @Path("region") String region,
                      @Query("expand_5prime") int expand5Prime,
                      @Query("expand_3prime") int expand3Prime,
                      @Query("mask") String mask);
}

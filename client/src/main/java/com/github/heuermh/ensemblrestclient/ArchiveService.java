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

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.POST;

/**
 * Archive service.  See <a href="http://rest.ensembl.org">http://rest.ensembl.org</a>.
 *
 * @since 2.0
 * @author  Michael Heuer
 */
public interface ArchiveService {

    /**
     * Return an archived sequence with the specified identifier.
     * See <a href="http://rest.ensembl.org/documentation/info/archive_id_get">http://rest.ensembl.org/documentation/info/archive_id_get</a>.
     *
     * @param id id
     * @return an archived sequence with the specified identifier
     * @throws EnsemblRestClientException if an error occurs
     */
    @GET("/archive/id/{id}")
    @Headers("Accept: application/json")
    ArchivedSequence archivedSequence(@Path("id") String id);

    /**
     * Return the archived sequences with the specified identifiers.
     * See <a href="http://rest.ensembl.org/documentation/info/archive_post_get">http://rest.ensembl.org/documentation/info/archive_id_post</a>.
     *
     * @param id zero or more ids
     * @return the archived sequences with the specified identifiers
     * @throws EnsemblRestClientException if an error occurs
     */
    @POST("/archive/id")
    @Headers("Accept: application/json")
    ArchivedSequence archivedSequence(@Body String[] id);
}

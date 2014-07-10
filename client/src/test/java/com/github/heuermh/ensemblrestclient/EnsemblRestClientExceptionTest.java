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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import retrofit.RetrofitError;

import retrofit.client.Header;
import retrofit.client.Response;

import retrofit.converter.ConversionException;

/**
 * Unit test for EnsemblRestClientException.
 *
 * @author  Michael Heuer
 */
public final class EnsemblRestClientExceptionTest {
    private Response response;
    private RetrofitError conversionError;
    private RetrofitError httpError;
    private RetrofitError networkError;
    private RetrofitError unexpectedError;

    @Before
    public void setUp() {
        response = new Response("url", 404, "Not found", Collections.<Header>emptyList(), null);
        conversionError = RetrofitError.conversionError("url", response, null, null, new ConversionException("message"));
        httpError = RetrofitError.httpError("url", response, null, null);
        networkError = RetrofitError.networkError("url", new IOException());
        unexpectedError = RetrofitError.networkError("url", new IOException());
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullRetrofitError() {
        new EnsemblRestClientException(null);
    }

    @Test
    public void testConversionError() {
        EnsemblRestClientException exception = new EnsemblRestClientException(conversionError);
        assertNotNull(exception);
        assertEquals("url", exception.getUrl());
        assertEquals(404, exception.getStatus());
        assertEquals("Not found", exception.getReason());
        assertFalse(exception.isNetworkError());
    }

    @Test
    public void testHttpError() {
        EnsemblRestClientException exception = new EnsemblRestClientException(httpError);
        assertNotNull(exception);
        assertEquals("url", exception.getUrl());
        assertEquals(404, exception.getStatus());
        assertEquals("Not found", exception.getReason());
        assertFalse(exception.isNetworkError());
    }

    @Test
    public void testNetworkError() {
        EnsemblRestClientException exception = new EnsemblRestClientException(networkError);
        assertNotNull(exception);
        assertEquals("url", exception.getUrl());
        assertEquals(-1, exception.getStatus());
        assertEquals("", exception.getReason());
        assertTrue(exception.isNetworkError());
    }

    @Test
    public void testUnexpectedError() {
        EnsemblRestClientException exception = new EnsemblRestClientException(unexpectedError);
        assertNotNull(exception);
        assertEquals("url", exception.getUrl());
        assertEquals(-1, exception.getStatus());
        assertEquals("", exception.getReason());
        assertTrue(exception.isNetworkError());
    }
}

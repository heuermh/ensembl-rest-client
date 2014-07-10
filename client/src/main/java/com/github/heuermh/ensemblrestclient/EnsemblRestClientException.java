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

import retrofit.RetrofitError;

import retrofit.client.Header;

/**
 * Ensembl REST client runtime exception.
 *
 * @since 1.4
 * @author  Michael Heuer
 */
public final class EnsemblRestClientException extends RuntimeException {
    private final RetrofitError retrofitError;


    /**
     * Create a new Ensembl REST client runtime exception wrapping the specified Retrofit error.
     *
     * @param retrofitError retrofit error, must not be null
     */
    EnsemblRestClientException(final RetrofitError retrofitError) {
        super(retrofitError);
        checkNotNull(retrofitError);
        this.retrofitError = retrofitError;
    }


    /**
     * Return the request URL.
     *
     * @return the request URL
     */
    public String getUrl()
    {
        return retrofitError.getUrl();
    }

    /**
     * Return the status line code.
     *
     * @return the status line code, or <code>-1</code> if the error response is null
     */
    public int getStatus()
    {
        return retrofitError.getResponse() == null ? -1 : retrofitError.getResponse().getStatus();
    }

    /**
     * Return the status line reason phrase.
     *
     * @return the status line reason phrase, or <code>""</code> if the error response is null
     */
    public String getReason()
    {
        return retrofitError.getResponse() == null ? "" : retrofitError.getResponse().getReason();
    }

    /**
     * Return true if this runtime exception was the result of a network error.
     *
     * @return true if this runtime exception was the result of a network error
     */
    public boolean isNetworkError()
    {
        return retrofitError.isNetworkError();
    }

    /**
     * Return true if this runtime exception was the result of a HTTP 429 Too Many Requests rate limit error.
     *
     * @since 1.5
     * @return true if this runtime exception was the result of a HTTP 429 Too Many Requests rate limit error
     */
    public boolean isRateLimitError()
    {
        return getStatus() == 429;
    }

    /**
     * Return the number of seconds before the rate limit resets, or <code>-1</code> if the exception was not
     * the result of a HTTP 429 Too Many Requests rate limit error.
     *
     * @since 1.5
     * @see #isRateLimitError
     * @return the number of seconds before the rate limit resets, or <code>-1</code> if the exception was not
     *    the result of a HTTP 429 Too Many Requests rate limit error
     */
    public long getRateLimitReset()
    {
        if (retrofitError.getResponse() == null)
        {
            return -1;
        }
        for (Header header : retrofitError.getResponse().getHeaders())
        {
            if ("X-RateLimit-Reset".equals(header.getName()))
            {
                return Long.parseLong(header.getValue());
            }
        }
        return -1;
    }
}


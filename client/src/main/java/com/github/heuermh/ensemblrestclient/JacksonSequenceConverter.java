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

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Type;

import java.io.InputStream;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;

import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Sequence converter built with Jackson.
 */
final class JacksonSequenceConverter implements Converter {
    private final JsonFactory jsonFactory;

    JacksonSequenceConverter(final JsonFactory jsonFactory) {
        checkNotNull(jsonFactory);
        this.jsonFactory = jsonFactory;
    }

    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        try {
            return parseSequence(jsonFactory, body.in());
        }
        catch (IOException e) {
            throw new ConversionException("could not parse sequence", e);
        }
    }

    @Override
    public TypedOutput toBody(final Object object) {
        throw new UnsupportedOperationException("toBody operation not supported");
    }

    static Sequence parseSequence(final JsonFactory jsonFactory, final InputStream inputStream) throws IOException {
        JsonParser parser = null;
        try {
            parser = jsonFactory.createParser(inputStream);
            parser.nextToken();

            String id = null;
            String seq = null;
            String molecule = null;

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.getCurrentName();
                parser.nextToken();
                if ("id".equals(field)) {
                    id = parser.getText();
                }
                else if ("seq".equals(field)) {
                    seq = parser.getText();
                }
                else if ("molecule".equals(field)) {
                    molecule = parser.getText();
                }
            }
            return new Sequence(id, seq, molecule);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (Exception e) {
                // ignored
            }
            try {
                parser.close();
            }
            catch (Exception e) {
                // ignored
            }
        }
    }
}
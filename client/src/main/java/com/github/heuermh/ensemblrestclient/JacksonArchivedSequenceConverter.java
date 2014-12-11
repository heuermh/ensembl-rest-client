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

import java.io.InputStream;
import java.io.IOException;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;

import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Archived sequence converter built with Jackson.
 *
 * @author  Michael Heuer
 */
@Immutable
final class JacksonArchivedSequenceConverter implements Converter {
    private final JsonFactory jsonFactory;


    JacksonArchivedSequenceConverter(final JsonFactory jsonFactory) {
        checkNotNull(jsonFactory);
        this.jsonFactory = jsonFactory;
    }


    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        try {
            return parseArchivedSequence(jsonFactory, body.in());
        }
        catch (IOException e) {
            throw new ConversionException("could not parse archived sequence", e);
        }
    }

    @Override
    public TypedOutput toBody(final Object object) {
        throw new UnsupportedOperationException("toBody operation not supported");
    }

    static ArchivedSequence parseArchivedSequence(final JsonFactory jsonFactory, final InputStream inputStream) throws IOException {
        JsonParser parser = null;
        try {
            parser = jsonFactory.createParser(inputStream);
            parser.nextToken();

            String id = null;
            String type = null;
            String assembly = null;
            String release = null;
            String version = null;
            String latest = null;
            String peptide = null;
            boolean current = false;
            List<String> possibleReplacement = new ArrayList<String>();

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.getCurrentName();
                parser.nextToken();

                if ("id".equals(field)) {
                    id = parser.getText();
                }
                else if ("type".equals(field)) {
                    type = parser.getText();
                }
                else if ("assembly".equals(field)) {
                    assembly = parser.getText();
                }
                else if ("release".equals(field)) {
                    release = parser.getText();
                }
                else if ("version".equals(field)) {
                    version = parser.getText();
                }
                else if ("latest".equals(field)) {
                    latest = parser.getText();
                }
                else if ("peptide".equals(field)) {
                    peptide = parser.getText();
                }
                else if ("is_current".equals(field)) {
                    current = (Integer.parseInt(parser.getText()) > 0);
                }
                else if ("possible_replacement".equals(field)) {
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        possibleReplacement.add(parser.getText());
                    }
                }
            }
            return new ArchivedSequence(id, type, assembly, release, version, latest, peptide, current, possibleReplacement.toArray(new String[possibleReplacement.size()]));
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

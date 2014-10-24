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

import java.lang.reflect.Type;

import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;

import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Feature converter built with Jackson.
 *
 * @author  Michael Heuer
 * @deprecated will be removed in version 2.0
 */
final class JacksonFeatureConverter implements Converter {
    private final JsonFactory jsonFactory;

    JacksonFeatureConverter(final JsonFactory jsonFactory) {
        checkNotNull(jsonFactory);
        this.jsonFactory = jsonFactory;
    }

    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        try {
            return parseFeature(jsonFactory, body.in());
        }
        catch (IOException e) {
            throw new ConversionException("could not parse feature", e);
        }
    }

    @Override
    public TypedOutput toBody(final Object object) {
        throw new UnsupportedOperationException("toBody operation not supported");
    }

    static Variation parseFeature(final JsonFactory jsonFactory, final InputStream inputStream) throws IOException {
        JsonParser parser = null;
        try {
            parser = jsonFactory.createParser(inputStream);
            parser.nextToken();

            String id = null;
            String reference = null;
            List<String> alternateAlleles = new ArrayList<String>();

            String locationName = null;
            String coordinateSystem = "chromosome";
            int start = -1;
            int end = -1;
            int strand = -1;

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.getCurrentName();
                parser.nextToken();
                if ("name".equals(field)) {
                    id = parser.getText();
                }
                else if ("mappings".equals(field)) {
                    // todo:  will only catch last mapping
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        while (parser.nextToken() != JsonToken.END_OBJECT) {
                            String mappingsField = parser.getCurrentName();
                            parser.nextToken();

                            if ("seq_region_name".equals(mappingsField)) {
                                locationName = parser.getText();
                            }
                            else if ("start".equals(mappingsField)) {
                                start = Integer.parseInt(parser.getText());
                            }
                            else if ("end".equals(mappingsField)) {
                                end = Integer.parseInt(parser.getText());
                            }
                            else if ("strand".equals(mappingsField)) {
                                strand = Integer.parseInt(parser.getText());
                            }
                            else if ("allele_string".equals(mappingsField)) {
                                String[] tokens = parser.getText().split("/");
                                // todo:  check ref here against ancestral_allele
                                reference = tokens[0];
                                alternateAlleles.add(tokens[1]);
                            }
                        }
                    }
                }
                else if ("synonyms".equals(field)) {
                     while (parser.nextToken() != JsonToken.END_ARRAY) {
                         // ignore
                     }
                }
                else if ("evidence".equals(field)) {
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        // ignore
                    }
                }
            }
            return new Variation(id, reference, alternateAlleles, new Location(locationName, coordinateSystem, start, end, strand));
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

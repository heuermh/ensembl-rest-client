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

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;

import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Overlap converter built with Jackson.
 *
 * @author  Michael Heuer
 */
@Immutable
final class JacksonOverlapConverter implements Converter {
    private final JsonFactory jsonFactory;


    JacksonOverlapConverter(final JsonFactory jsonFactory) {
        checkNotNull(jsonFactory);
        this.jsonFactory = jsonFactory;
    }


    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        try {
            return parseOverlap(jsonFactory, body.in());
        }
        catch (IOException e) {
            throw new ConversionException("could not parse overlap", e);
        }
    }

    @Override
    public TypedOutput toBody(final Object object) {
        throw new UnsupportedOperationException("toBody operation not supported");
    }

    static List<Variation> parseOverlap(final JsonFactory jsonFactory, final InputStream inputStream) throws IOException {
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
            List<Variation> variationFeatures = new ArrayList<Variation>();
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                while (parser.nextToken() != JsonToken.END_OBJECT) {
                    String field = parser.getCurrentName();
                    parser.nextToken();
                    if ("id".equals(field)) {
                        id = parser.getText();
                    }
                    else if ("seq_region_name".equals(field)) {
                        locationName = parser.getText();
                    }
                    else if ("start".equals(field)) {
                        start = Integer.parseInt(parser.getText());
                    }
                    else if ("end".equals(field)) {
                        end = Integer.parseInt(parser.getText());
                    }
                    else if ("strand".equals(field)) {
                        strand = Integer.parseInt(parser.getText());
                    }
                    else if ("alt_alleles".equals(field)) {
                        int index = 0;
                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                            if (index == 0) {
                                reference = parser.getText();
                            }
                            else if (index == 1) {
                                alternateAlleles.add(parser.getText());
                            }
                            index++;
                        }
                    }
                }
                variationFeatures.add(new Variation(id, reference, alternateAlleles, new Location(locationName, coordinateSystem, start, end, strand)));
                id = null;
                reference = null;
                alternateAlleles.clear();
                locationName = null;
                start = -1;
                end = -1;
                strand = -1;
            }
            return variationFeatures;
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

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
 * Variation and variation consequences converter built with Jackson.
 *
 * @author  Michael Heuer
 */
@Immutable
final class JacksonVariationConverter implements Converter {
    private final JsonFactory jsonFactory;


    JacksonVariationConverter(final JsonFactory jsonFactory) {
        checkNotNull(jsonFactory);
        this.jsonFactory = jsonFactory;
    }


    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        try {
            return Variation.class.equals(type) ? parseVariation(jsonFactory, body.in()) : parseVariationConsequences(jsonFactory, body.in());
        }
        catch (IOException e) {
            throw new ConversionException("could not parse variation" + (Variation.class.equals(type) ? "" : " consequences"), e);
        }
    }

    @Override
    public TypedOutput toBody(final Object object) {
        throw new UnsupportedOperationException("toBody operation not supported");
    }

    static Variation parseVariation(final JsonFactory jsonFactory, final InputStream inputStream) throws IOException {
        JsonParser parser = null;
        try {
            parser = jsonFactory.createParser(inputStream);
            parser.nextToken();

            String identifier = null;
            String referenceAllele = null;
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
                    identifier = parser.getText();
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
                                referenceAllele = tokens[0];
                                for (int i = 1; i < tokens.length; i++) {
                                    alternateAlleles.add(tokens[i]);
                                }
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
            return new Variation(identifier, referenceAllele, alternateAlleles, new Location(locationName, coordinateSystem, start, end, strand));
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

    static VariationConsequences parseVariationConsequences(final JsonFactory jsonFactory, final InputStream inputStream) throws IOException {
        JsonParser parser = null;
        try {
            parser = jsonFactory.createParser(inputStream);
            parser.nextToken();

            String identifier = null;
            String referenceAllele = null;
            List<String> alternateAlleles = new ArrayList<String>();

            String locationName = null;
            String coordinateSystem = "chromosome";
            int start = -1;
            int end = -1;
            int strand = -1;

            List<TranscriptConsequences> transcriptConsequences = new ArrayList<TranscriptConsequences>();

            String alternateAllele = null;
            int transcriptStrand = -1;
            boolean canonical = false;
            String geneId = null;
            String transcriptId = null;
            String translationId = null;
            String transcriptAlleleString = null;
            String codons = null;
            String hgvsc = null;
            String aminoAcids = null;
            String hgvsp = null;
            List<String> consequenceTerms = new ArrayList<String>();

            while (parser.nextToken() != JsonToken.END_ARRAY) {
                while (parser.nextToken() != JsonToken.END_OBJECT) {
                    String field = parser.getCurrentName();
                    parser.nextToken();

                    if ("id".equals(field)) {
                        identifier = parser.getText();
                    }
                    else if ("seq_region_name".equals(field)) {
                        locationName = parser.getText();
                    }
                    else if ("start".equals(field)) {
                        start = parser.getIntValue();
                    }
                    else if ("end".equals(field)) {
                        end = parser.getIntValue();
                    }
                    else if ("strand".equals(field)) {
                        strand = parser.getIntValue();
                    }
                    else if ("allele_string".equals(field)) {
                        String[] tokens = parser.getText().split("/");
                        referenceAllele = tokens[0];
                        for (int i = 1; i < tokens.length; i++) {
                            alternateAlleles.add(tokens[i]);
                        }
                    }
                    else if ("transcript_consequences".equals(field)) {
                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                            while (parser.nextToken() != JsonToken.END_OBJECT) {
                                String transcriptField = parser.getCurrentName();
                                parser.nextToken();

                                if ("variant_allele".equals(transcriptField)) {
                                    alternateAllele = parser.getText();
                                }
                                else if ("strand".equals(transcriptField)) {
                                    transcriptStrand = parser.getIntValue();
                                }
                                else if ("canonical".equals(transcriptField)) {
                                    canonical = (Integer.parseInt(parser.getText()) > 0);
                                }
                                else if ("gene_id".equals(transcriptField)) {
                                    geneId = parser.getText();
                                }
                                else if ("transcript_id".equals(transcriptField)) {
                                    transcriptId = parser.getText();
                                }
                                else if ("protein_id".equals(transcriptField)) {
                                    translationId = parser.getText();
                                }
                                else if ("codons".equals(transcriptField)) {
                                    codons = parser.getText();
                                }
                                else if ("hgvsc".equals(transcriptField)) {
                                    hgvsc = parser.getText();
                                }
                                else if ("amino_acids".equals(transcriptField)) {
                                    aminoAcids = parser.getText();
                                }
                                else if ("hgvsp".equals(transcriptField)) {
                                    hgvsp = parser.getText();
                                }
                                else if ("consequence_terms".equals(transcriptField)) {
                                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                                        consequenceTerms.add(parser.getText());
                                    }
                                }
                            }

                            transcriptConsequences.add(new TranscriptConsequences(alternateAllele, transcriptStrand, canonical, geneId, transcriptId, translationId, codons, hgvsc, aminoAcids, hgvsp, consequenceTerms));

                            alternateAllele = null;
                            transcriptStrand = -1;
                            canonical = false;
                            geneId = null;
                            transcriptId = null;
                            translationId = null;
                            transcriptAlleleString = null;
                            codons = null;
                            hgvsc = null;
                            aminoAcids = null;
                            hgvsp = null;
                            consequenceTerms.clear();
                        }
                    }
                    else if ("colocated_variants".equals(field)) {
                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                            while (parser.nextToken() != JsonToken.END_OBJECT) {
                                // ignore
                            }
                        }
                    }
                }
            }
            Location location = new Location(locationName, coordinateSystem, start, end, strand);
            return new VariationConsequences(identifier, referenceAllele, alternateAlleles, location, transcriptConsequences);
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

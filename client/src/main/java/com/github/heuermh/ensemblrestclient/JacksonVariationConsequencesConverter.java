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

import java.io.InputStream;
import java.io.IOException;

import java.lang.reflect.Type;

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
 * Variation consequences converter built with Jackson.
 */
final class JacksonVariationConsequencesConverter implements Converter {
    private final JsonFactory jsonFactory;

    JacksonVariationConsequencesConverter(final JsonFactory jsonFactory) {
        checkNotNull(jsonFactory);
        this.jsonFactory = jsonFactory;
    }

    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        try {
            return parseVariationConsequences(jsonFactory, body.in());
        }
        catch (IOException e) {
            throw new ConversionException("could not parse variation consequences", e);
        }
    }

    @Override
    public TypedOutput toBody(final Object object) {
        throw new UnsupportedOperationException("toBody operation not supported");
    }

    static VariationConsequences parseVariationConsequences(final JsonFactory jsonFactory, final InputStream inputStream) throws IOException {
        JsonParser parser = null;
        try {
            parser = jsonFactory.createParser(inputStream);
            parser.nextToken();

            String name = null;
            boolean somatic = false;
            Location location = null;
            List<Transcript> transcripts = new ArrayList<Transcript>();

            String locationName = null;
            String coordinateSystem = null;
            int start = -1;
            int end = -1;
            int strand = -1;

            String transcriptName = null;
            String biotype = null;
            boolean canonical = false;
            String geneId = null;
            String transcriptId = null;
            String translationId = null;
            String transcriptAlleleString = null;
            List<Allele> alleles = new ArrayList<Allele>();

            String alleleString = null;
            String codonAlleleString = null;
            String translationAlleleString = null;
            List<String> consequenceTerms = new ArrayList<String>();

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.getCurrentName();
                parser.nextToken();
                if ("data".equals(field)) {
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        while (parser.nextToken() != JsonToken.END_OBJECT) {
                            String consequencesField = parser.getCurrentName();
                            parser.nextToken();
                            if ("name".equals(consequencesField)) {
                                name = parser.getText();
                            }
                            else if ("is_somatic".equals(consequencesField)) {
                                somatic = (Integer.parseInt(parser.getText()) > 0);
                            }
                            else if ("hgvs".equals(consequencesField)) {
                                while (parser.nextToken() != JsonToken.END_OBJECT) {
                                    // ignore
                                }
                            }
                            else if ("location".equals(consequencesField)) {
                                while (parser.nextToken() != JsonToken.END_OBJECT) {
                                    String locationField = parser.getCurrentName();
                                    parser.nextToken();
                                    if ("name".equals(locationField)) {
                                        locationName = parser.getText();
                                    }
                                    else if ("coord_system".equals(locationField)) {
                                        coordinateSystem = parser.getText();
                                    }
                                    else if ("start".equals(locationField)) {
                                        start = Integer.parseInt(parser.getText());
                                    }
                                    else if ("end".equals(locationField)) {
                                        end = Integer.parseInt(parser.getText());
                                    }
                                    else if ("strand".equals(locationField)) {
                                        strand = Integer.parseInt(parser.getText());
                                    }
                                }
                                location = new Location(locationName, coordinateSystem, start, end, strand);
                            }
                            else if ("transcripts".equals(consequencesField)) {
                                while (parser.nextToken() != JsonToken.END_ARRAY) {
                                    while (parser.nextToken() != JsonToken.END_OBJECT) {
                                        String transcriptField = parser.getCurrentName();
                                        parser.nextToken();
                                        if ("name".equals(transcriptField)) {
                                            transcriptName = parser.getText();
                                        }
                                        else if ("biotype".equals(transcriptField)) {
                                            biotype = parser.getText();
                                        }
                                        else if ("is_canonical".equals(transcriptField)) {
                                            canonical = (Integer.parseInt(parser.getText()) > 0);
                                        }
                                        else if ("gene_id".equals(transcriptField)) {
                                            geneId = parser.getText();
                                        }
                                        else if ("transcript_id".equals(transcriptField)) {
                                            transcriptId = parser.getText();
                                        }
                                        else if ("translation_stable_id".equals(transcriptField)) {
                                            translationId = parser.getText();
                                        }
                                        else if ("cdna_allele_string".equals(transcriptField)) {
                                            transcriptAlleleString = parser.getText();
                                        }
                                        else if ("protein_features".equals(transcriptField)) {
                                            while (parser.nextToken() != JsonToken.END_ARRAY) {
                                                while (parser.nextToken() != JsonToken.END_OBJECT) {
                                                    // ignore
                                                }
                                            }
                                        }
                                        else if ("alleles".equals(transcriptField)) {
                                            while (parser.nextToken() != JsonToken.END_ARRAY) {
                                                while (parser.nextToken() != JsonToken.END_OBJECT) {
                                                    String alleleField = parser.getCurrentName();
                                                    parser.nextToken();
                                                    if ("allele_string".equals(alleleField)) {
                                                        alleleString = parser.getText(); 
                                                    }
                                                    else if ("codon_allele_string".equals(alleleField)) {
                                                        codonAlleleString = parser.getText();
                                                    }
                                                    else if ("pep_allele_string".equals(alleleField)) {
                                                        translationAlleleString = parser.getText();
                                                    }
                                                    else if ("consequence_terms".equals(alleleField)) {
                                                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                                                            consequenceTerms.add(parser.getText());
                                                        }
                                                    }
                                                }
                                                alleles.add(new Allele(alleleString, codonAlleleString, translationAlleleString, consequenceTerms));

                                                alleleString = null;
                                                codonAlleleString = null;
                                                translationAlleleString = null;
                                                consequenceTerms.clear();
                                            }
                                        }
                                    }
                                    transcripts.add(new Transcript(transcriptName, biotype, canonical, geneId, transcriptId, translationId, transcriptAlleleString, alleles));

                                    transcriptName = null;
                                    biotype = null;
                                    canonical = false;
                                    geneId = null;
                                    transcriptId = null;
                                    translationId = null;
                                    alleles.clear();
                                }
                            }
                        }
                    }
                }
            }
            return new VariationConsequences(name, somatic, location, transcripts);
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
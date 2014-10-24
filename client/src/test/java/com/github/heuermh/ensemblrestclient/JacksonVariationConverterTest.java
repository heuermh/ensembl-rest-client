/*

    ensembl-rest-client  Ensembl REST API client.
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

import static com.github.heuermh.ensemblrestclient.JacksonVariationConverter.parseVariation;
import static com.github.heuermh.ensemblrestclient.JacksonVariationConverter.parseVariationConsequences;

import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;

import retrofit.converter.ConversionException;

import retrofit.mime.TypedInput;

/**
 * Unit test for JacksonVariationConverter.
 *
 * @author  Michael Heuer
 */
public final class JacksonVariationConverterTest {
    private JsonFactory jsonFactory;
    private JacksonVariationConverter converter;

    @Before
    public void setUp() {
        jsonFactory = new JsonFactory();
        converter = new JacksonVariationConverter(jsonFactory);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullJsonFactory() {
        new JacksonVariationConverter(null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(converter);
    }

    @Test
    public void testParseVariation_rs56116432() throws Exception {
        Variation variation = parseVariation(jsonFactory, getClass().getResourceAsStream("rs56116432.json"));
        assertNotNull(variation);
    }

    @Test
    public void testParseVariationConsequences_COSM476() throws Exception {
        VariationConsequences vc = parseVariationConsequences(jsonFactory, getClass().getResourceAsStream("COSM476.consequences.json"));
        assertNotNull(vc);
    }

    @Test
    public void testParseVariationConsequences_rs10244642() throws Exception {
        VariationConsequences vc = parseVariationConsequences(jsonFactory, getClass().getResourceAsStream("rs10244642.consequences.json"));
        assertNotNull(vc);
    }

    @Test
    public void testFromBodyVariation() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("rs56116432.json");
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenReturn(inputStream);
        Variation variation = (Variation) converter.fromBody(body, Variation.class);
        assertNotNull(variation);
    }

    @Test(expected=ConversionException.class)
    public void testFromBodyVariationIOException() throws Exception {
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenThrow(new IOException());
        converter.fromBody(body, Variation.class);
    }

    @Test
    public void testFromBodyVariationConsequences() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("rs10244642.consequences.json");
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenReturn(inputStream);
        VariationConsequences vc = (VariationConsequences) converter.fromBody(body, VariationConsequences.class);
        assertNotNull(vc);
    }

    @Test(expected=ConversionException.class)
    public void testFromBodyVariationConsequencesIOException() throws Exception {
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenThrow(new IOException());
        converter.fromBody(body, VariationConsequences.class);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testToBody() {
        converter.toBody(new Object());
    }
}
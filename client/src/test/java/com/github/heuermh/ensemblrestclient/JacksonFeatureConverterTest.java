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

import static com.github.heuermh.ensemblrestclient.JacksonFeatureConverter.parseFeature;
import static com.github.heuermh.ensemblrestclient.JacksonFeatureConverter.parseFeatures;

import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.IOException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;

import retrofit.converter.ConversionException;

import retrofit.mime.TypedInput;

/**
 * Unit test for JacksonFeatureConverter.
 */
public final class JacksonFeatureConverterTest {
    private JsonFactory jsonFactory;
    private JacksonFeatureConverter converter;

    @Before
    public void setUp() {
        jsonFactory = new JsonFactory();
        converter = new JacksonFeatureConverter(jsonFactory);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullJsonFactory() {
        new JacksonFeatureConverter(null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(converter);
    }

    @Test
    public void testParseFeature_rs185077298() throws Exception {
        Variation variation = parseFeature(jsonFactory, getClass().getResourceAsStream("rs185077298.json"));
        assertNotNull(variation);
    }

    @Test
    public void testParseFeatures_7_140424943_140425943_variation() throws Exception {
        List<Variation> variationFeatures = parseFeatures(jsonFactory, getClass().getResourceAsStream("7_140424943-140425943_variation.json"));
        assertNotNull(variationFeatures);
    }

    @Test
    public void testParseFeatures_ENSG00000157764_variation() throws Exception {
        List<Variation> variationFeatures = parseFeatures(jsonFactory, getClass().getResourceAsStream("ENSG00000157764_variation.json"));
        assertNotNull(variationFeatures);
    }

    @Test
    public void testFromBodyFeature() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("rs185077298.json");
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenReturn(inputStream);
        Variation variation = (Variation) converter.fromBody(body, Variation.class);
        assertNotNull(variation);
    }

    @Test
    public void testFromBodyFeatures() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("7_140424943-140425943_variation.json");
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenReturn(inputStream);
        List<Variation> variationFeatures = (List<Variation>) converter.fromBody(body, List.class);
        assertNotNull(variationFeatures);
    }

    @Test(expected=ConversionException.class)
    public void testFromBodyIOException() throws Exception {
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenThrow(new IOException());
        converter.fromBody(body, Variation.class);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testToBody() {
        converter.toBody(new Object());
    }
}

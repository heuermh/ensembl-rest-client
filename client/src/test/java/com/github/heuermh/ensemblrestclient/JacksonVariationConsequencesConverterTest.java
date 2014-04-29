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

import static com.github.heuermh.ensemblrestclient.JacksonVariationConsequencesConverter.parseVariationConsequences;

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
 * Unit test for JacksonVariationConsequencesConverter.
 */
public final class JacksonVariationConsequencesConverterTest {
    private JsonFactory jsonFactory;
    private JacksonVariationConsequencesConverter converter;

    @Before
    public void setUp() {
        jsonFactory = new JsonFactory();
        converter = new JacksonVariationConsequencesConverter(jsonFactory);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullJsonFactory() {
        new JacksonVariationConsequencesConverter(null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(converter);
    }

    @Test
    public void testParseVariationConsequences_COSM476() throws Exception {
        VariationConsequences vc = parseVariationConsequences(jsonFactory, getClass().getResourceAsStream("COSM476.json"));
        assertNotNull(vc);
    }

    @Test
    public void testParseVariationConsequences_rs116035550() throws Exception {
        VariationConsequences vc = parseVariationConsequences(jsonFactory, getClass().getResourceAsStream("rs116035550.json"));
        assertNotNull(vc);
    }

    @Test
    public void testParseVariationConsequences_9_22125503_22125502_1_C() throws Exception {
        VariationConsequences vc = parseVariationConsequences(jsonFactory, getClass().getResourceAsStream("9_22125503-22125502_1_C.json"));
        assertNotNull(vc);
    }

    @Test
    public void testParseVariationConsequences_1_6524705_6524705_T() throws Exception {
        VariationConsequences vc = parseVariationConsequences(jsonFactory, getClass().getResourceAsStream("1_6524705_6524705_T.json"));
        assertNotNull(vc);
    }

    @Test
    public void testFromBody() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("COSM476.json");
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenReturn(inputStream);
        VariationConsequences vc = (VariationConsequences) converter.fromBody(body, VariationConsequences.class);
        assertNotNull(vc);
    }

    @Test(expected=ConversionException.class)
    public void testFromBodyIOException() throws Exception {
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenThrow(new IOException());
        converter.fromBody(body, VariationConsequences.class);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testToBody() {
        converter.toBody(new Object());
    }
}

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

import static com.github.heuermh.ensemblrestclient.JacksonSequenceConverter.parseSequence;

import static org.junit.Assert.assertEquals;
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
 * Unit test for JacksonSequenceConverter.
 */
public final class JacksonSequenceConverterTest {
    private JsonFactory jsonFactory;
    private JacksonSequenceConverter converter;

    @Before
    public void setUp() {
        jsonFactory = new JsonFactory();
        converter = new JacksonSequenceConverter(jsonFactory);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullJsonFactory() {
        new JacksonSequenceConverter(null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(converter);
    }

    @Test
    public void testParseSequence_X_1000000_1000100_1() throws Exception {
        Sequence sequence = parseSequence(jsonFactory, getClass().getResourceAsStream("X_1000000_1000100_1.json"));
        assertNotNull(sequence);
        assertEquals("chromosome:GRCh37:X:1000000:1000100:1", sequence.getIdentifier());
        assertEquals("GAAACAGCTACTTGGAAGGCTGAAGCAGGAGGATTGTTTGAGTCTAGGAGTTTGAGGCTGCAGTGAGTTATGAGCACACCACGGCACTCCAGCCTGGGAGA", sequence.getSequence());
        assertEquals("dna", sequence.getMolecule());
    }

    @Test
    public void testFromBody() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("X_1000000_1000100_1.json");
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenReturn(inputStream);
        Sequence sequence = (Sequence) converter.fromBody(body, Sequence.class);
        assertNotNull(sequence);
    }

    @Test(expected=ConversionException.class)
    public void testFromBodyIOException() throws Exception {
        TypedInput body = mock(TypedInput.class);
        when(body.in()).thenThrow(new IOException());
        converter.fromBody(body, Sequence.class);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testToBody() {
        converter.toBody(new Object());
    }
}

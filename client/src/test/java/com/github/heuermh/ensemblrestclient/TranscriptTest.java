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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Unit test for Transcript.
 */
public final class TranscriptTest {
    private Allele allele;
    private Transcript transcript;

    @Before
    public void setUp() {
        allele = new Allele("A/T", "GTG/GAG", "V/E", ImmutableList.of("missense_variant"));
        transcript = new Transcript("BRAF", "protein_coding", false, "ENSG00000157764", "ENST00000288602", "ENSP00000288602", "T/A", ImmutableList.of(allele));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullName() {
        new Transcript(null, "protein_coding", false, "ENSG00000157764", "ENST00000288602", "ENSP00000288602", "T/A", ImmutableList.of(allele));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullBiotype() {
        new Transcript("BRAF", null, false, "ENSG00000157764", "ENST00000288602", "ENSP00000288602", "T/A", ImmutableList.of(allele));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullTranscriptId() {
        new Transcript("BRAF", "protein_coding", false, "ENSG00000157764", null, "ENSP00000288602", "T/A", ImmutableList.of(allele));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullAlleles() {
        new Transcript("BRAF", "protein_coding", false, "ENSG00000157764", "ENST00000288602", "ENSP00000288602", "T/A", null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(transcript);
    }

    @Test
    public void testName() {
        assertEquals("BRAF", transcript.getName());
    }

    @Test
    public void testBiotype() {
        assertEquals("protein_coding", transcript.getBiotype());
    }

    @Test
    public void testCanonical() {
        assertFalse(transcript.isCanonical());
    }

    @Test
    public void testGeneId() {
        assertEquals("ENSG00000157764", transcript.getGeneId());
    }

    @Test
    public void testTranscriptId() {
        assertEquals("ENST00000288602", transcript.getTranscriptId());
    }

    @Test
    public void testTranslationId() {
        assertEquals("ENSP00000288602", transcript.getTranslationId());
    }

    @Test
    public void testAlleleString() {
        assertEquals("T/A", transcript.getAlleleString());
    }

    @Test
    public void testAlleles() {
        assertNotNull(transcript.getAlleles());
        assertEquals(1, transcript.getAlleles().size());
        assertTrue(transcript.getAlleles().contains(allele));
    }
}

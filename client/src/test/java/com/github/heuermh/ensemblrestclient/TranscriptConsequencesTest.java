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
 * Unit test for TranscriptConsequences.
 *
 * @author  Michael Heuer
 */
public final class TranscriptConsequencesTest {
    private TranscriptConsequences transcriptConsequences;

    @Before
    public void setUp() {
        transcriptConsequences = new TranscriptConsequences("T", -1, true, "ENSG00000157764", "ENST00000288602", "ENSP00000288602", "gTg/gAg", "ENST00000288602.3:c.83T>A", "V/E", "ENSP00000288602.1:p.Val28Glu", ImmutableList.of("missense_variant", "NMD_transcript_variant"));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullAlternateAllele() {
        new TranscriptConsequences(null, -1, true, "ENSG00000157764", "ENST00000288602", "ENSP00000288602", "gTg/gAg", "ENST00000288602.3:c.83T>A", "V/E", "ENSP00000288602.1:p.Val28Glu", ImmutableList.of("missense_variant", "NMD_transcript_variant"));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullTranscriptId() {
        new TranscriptConsequences("T", -1, true, "ENSG00000157764", null, "ENSP00000288602", "gTg/gAg", "ENST00000288602.3:c.83T>A", "V/E", "ENSP00000288602.1:p.Val28Glu", ImmutableList.of("missense_variant", "NMD_transcript_variant"));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullConsequenceTerms() {
        new TranscriptConsequences("T", -1, true, "ENSG00000157764", "ENST00000288602", "ENSP00000288602", "gTg/gAg", "ENST00000288602.3:c.83T>A", "V/E", "ENSP00000288602.1:p.Val28Glu", null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(transcriptConsequences);
        assertEquals("T", transcriptConsequences.getAlternateAllele());
        assertEquals(-1, transcriptConsequences.getStrand());
        assertEquals("ENSG00000157764", transcriptConsequences.getGeneId());
        assertEquals("ENST00000288602", transcriptConsequences.getTranscriptId());
        assertEquals("ENSP00000288602", transcriptConsequences.getTranslationId());
        assertEquals("gTg/gAg", transcriptConsequences.getCodons());
        assertEquals("ENST00000288602.3:c.83T>A", transcriptConsequences.getHgvsC());
        assertEquals("V/E", transcriptConsequences.getAminoAcids());
        assertEquals("ENSP00000288602.1:p.Val28Glu", transcriptConsequences.getHgvsP());
        assertEquals(2, transcriptConsequences.getConsequenceTerms().size());
        assertTrue(transcriptConsequences.getConsequenceTerms().contains("missense_variant"));
        assertTrue(transcriptConsequences.getConsequenceTerms().contains("NMD_transcript_variant"));
    }
}

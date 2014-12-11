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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Unit test for VariationConsequences.
 *
 * @author  Michael Heuer
 */
public final class VariationConsequencesTest {
    private Location location;
    private TranscriptConsequences transcriptConsequences;
    private VariationConsequences variationConsequences;

    @Before
    public void setUp() {
        location = new Location("7", "chromosome", 140453136, 140453136, 1);
        transcriptConsequences = new TranscriptConsequences("T", -1, true, "ENSG00000157764", "ENST00000288602", "ENSP00000288602", "gTg/gAg", "ENSP00000288602.1:pVal28Glu", "V/E", "ENST00000288602.3:c.83T>A", ImmutableList.of("missense_variant", "NMD_transcript_variant"));
        variationConsequences = new VariationConsequences("COSM476", "A", ImmutableList.of("T"), location, ImmutableList.of(transcriptConsequences));
    }

    @Test
    public void testConstructor() {
        assertNotNull(variationConsequences);
        assertEquals("COSM476", variationConsequences.getIdentifier());
        assertEquals("A", variationConsequences.getReferenceAllele());
        assertEquals(ImmutableList.of("T"), variationConsequences.getAlternateAlleles());
        assertEquals("7", variationConsequences.getLocation().getName());
        assertEquals(140453136, variationConsequences.getLocation().getStart());
        assertEquals(140453136, variationConsequences.getLocation().getEnd());
        assertEquals(1, variationConsequences.getLocation().getStrand());
        assertEquals(1, variationConsequences.getTranscriptConsequences().size());
        assertEquals("T", variationConsequences.getTranscriptConsequences().get(0).getAlternateAllele());
    }
}

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
    private Allele allele;
    private Transcript transcript;
    private VariationConsequences variationConsequences;

    @Before
    public void setUp() {
        location = new Location("7", "chromosome", 140453136, 140453136, 1);
        allele = new Allele("A/T", "GTG/GAG", "V/E", ImmutableList.of("missense_variant"));
        transcript = new Transcript("BRAF", "protein_coding", false, "ENSG00000157764", "ENST00000288602", "ENSP00000288602", "T/A", ImmutableList.of(allele));
        variationConsequences = new VariationConsequences("COSM476", true, location, ImmutableList.of(transcript));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullLocation() {
        new VariationConsequences("COSM476", true, null, ImmutableList.of(transcript));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullTranscripts() {
        new VariationConsequences("COSM476", true, location, null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(variationConsequences);
    }

    @Test
    public void testName() {
        assertEquals("COSM476", variationConsequences.getName());
    }

    @Test
    public void testSomatic() {
        assertTrue(variationConsequences.isSomatic());
    }

    @Test
    public void testLocation() {
        assertNotNull(variationConsequences.getLocation());
        assertEquals(location, variationConsequences.getLocation());
    }

    @Test
    public void testTranscripts() {
        assertNotNull(variationConsequences.getTranscripts());
        assertEquals(1, variationConsequences.getTranscripts().size());
        assertTrue(variationConsequences.getTranscripts().contains(transcript));
    }
}

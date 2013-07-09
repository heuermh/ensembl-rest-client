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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Unit test for Allele.
 */
public final class AlleleTest {
    private Allele allele;

    @Before
    public void setUp() {
        allele = new Allele("A/T", "GTG/GAG", "V/E", ImmutableList.of("missense_variant"));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullAlleleString() {
        new Allele(null, "GTG/GAG", "V/E", ImmutableList.of("missense_variant"));
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullConsequenceTerms() {
        new Allele("A/T", "GTG/GAG", "V/E", null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(allele);
    }

    @Test
    public void testAlleleString() {
        assertEquals("A/T", allele.getAlleleString());
    }

    @Test
    public void testCodonAlleleString() {
        assertEquals("GTG/GAG", allele.getCodonAlleleString());
    }

    @Test
    public void testTranslationAlleleString() {
        assertEquals("V/E", allele.getTranslationAlleleString());
    }

    @Test
    public void testConsequenceTerms() {
        assertNotNull(allele.getConsequenceTerms());
        assertEquals(1, allele.getConsequenceTerms().size());
        assertTrue(allele.getConsequenceTerms().contains("missense_variant"));
    }
}
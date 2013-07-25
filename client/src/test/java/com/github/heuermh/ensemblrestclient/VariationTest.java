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

import java.util.List;

import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Variation.
 */
public final class VariationTest {
    private Variation variation;
    private Location location;
    private List<String> alternateAlleles = ImmutableList.of("T", "TT");

    @Before
    public void setUp() {
        location = new Location("7", "chromosome", 140424949, 140424949, 1);
        variation = new Variation("rs185077298", "C", alternateAlleles, location);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullIdentifier() {
        new Variation(null, "C", alternateAlleles, location);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullReferenceAllele() {
        new Variation("rs185077298", null, alternateAlleles, location);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullAlternateAlleles() {
        new Variation("rs185077298", "C", null, location);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullLocation() {
        new Variation("rs185077298", "C", alternateAlleles, null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(variation);
    }

    @Test
    public void testIdentifier() {
        assertEquals("rs185077298", variation.getIdentifier());
    }

    @Test
    public void testReferenceAllele() {
        assertEquals("C", variation.getReferenceAllele());
    }

    @Test
    public void testAlternateAlleles() {
        assertEquals(alternateAlleles, variation.getAlternateAlleles());
    }

    @Test
    public void testLocation() {
        assertEquals(location, variation.getLocation());
    }
}
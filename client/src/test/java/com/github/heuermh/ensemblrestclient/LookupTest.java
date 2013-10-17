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

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for Lookup.
 */
public final class LookupTest {
    private Lookup lookup;
    private Location location;

    @Before
    public void setUp() {
        location = new Location("7", "chromosome", 140424943, 140624564, -1);
        lookup = new Lookup("ENSG00000157764", "homo_sapiens", "Gene", "core", location);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullIdentifier() {
        new Lookup(null, "homo_sapiens", "Gene", "core", location);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullSpecies() {
        new Lookup("ENSG00000157764", null, "Gene", "core", location);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullLocation() {
        new Lookup("ENSG00000157764", "homo_sapiens", "Gene", "core", null);
    }

    @Test
    public void testConstructor() {
        assertNotNull(lookup);
    }

    @Test
    public void testIdentifier() {
        assertEquals("ENSG00000157764", lookup.getIdentifier());
    }

    @Test
    public void testSpecies() {
        assertEquals("homo_sapiens", lookup.getSpecies());
    }

    @Test
    public void testType() {
        assertEquals("Gene", lookup.getType());
    }

    @Test
    public void testDatabase() {
        assertEquals("core", lookup.getDatabase());
    }

    @Test
    public void testLocation() {
        assertEquals(location, lookup.getLocation());
    }
}
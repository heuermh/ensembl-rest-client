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

/**
 * Unit test for Location.
 */
public final class LocationTest {
    private Location location;

    @Before
    public void setUp() {
        location = new Location("7", "chromosome", 140453136, 140453136, 1);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullName() {
        new Location(null, "chromosome", 140453136, 140453136, 1);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullCoordinateSystem() {
        new Location("7", null, 140453136, 140453136, 1);
    }

    @Test
    public void testConstructor() {
        assertNotNull(location);
    }

    @Test
    public void testName() {
        assertEquals("7", location.getName());
    }

    @Test
    public void testCoordinateSystem() {
        assertEquals("chromosome", location.getCoordinateSystem());
    }

    @Test
    public void testStart() {
        assertEquals(140453136, location.getStart());
    }

    @Test
    public void testEnd() {
        assertEquals(140453136, location.getEnd());
    }

    @Test
    public void testStrand() {
        assertEquals(1, location.getStrand());
    }
}
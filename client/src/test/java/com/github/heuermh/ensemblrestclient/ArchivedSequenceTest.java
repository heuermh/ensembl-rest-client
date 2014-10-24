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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for ArchivedSequence.
 *
 * @author  Michael Heuer
 */
public final class ArchivedSequenceTest {
    private ArchivedSequence archivedSequence;

    @Before
    public void setUp() {
        archivedSequence = new ArchivedSequence("id", "type", "assembly", "release", "version", "latest", null, true, new String[0]);
    }

    @Test
    public void testConstructor() {
        assertNotNull(archivedSequence);
        assertEquals("id", archivedSequence.getId());
        assertEquals("type", archivedSequence.getType());
        assertEquals("assembly", archivedSequence.getAssembly());
        assertEquals("release", archivedSequence.getRelease());
        assertEquals("version", archivedSequence.getVersion());
        assertEquals("latest", archivedSequence.getLatest());
        assertNull(archivedSequence.getPeptide());
        assertTrue(archivedSequence.isCurrent());
        assertEquals(new String[0], archivedSequence.getPossibleReplacement());
    }
}

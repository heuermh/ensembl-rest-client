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

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.core.JsonFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for EnsemblRestClientFactory.
 *
 * @author  Michael Heuer
 */
public final class EnsemblRestClientFactoryTest {
    private EnsemblRestClientFactory factory;

    @Before
    public void setUp() {
        factory = new EnsemblRestClientFactory();
    }

    @Test
    public void testConstructor() {
        assertNotNull(factory);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullJsonFactory() {
        new EnsemblRestClientFactory((JsonFactory) null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullDefaultServerUrl() {
        new EnsemblRestClientFactory((String) null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorDefaultServerUrlNullJsonFactory() {
        new EnsemblRestClientFactory("http://beta.rest.ensembl.org/", null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorJsonFactoryNullDefaultServerUrl() {
        new EnsemblRestClientFactory(null, new JsonFactory());
    }

    @Test
    public void testConstructorJsonFactory() {
        assertNotNull(new EnsemblRestClientFactory(new JsonFactory()));
    }

    @Test
    public void testConstructorDefaultServerUrl() {
        assertNotNull(new EnsemblRestClientFactory("http://beta.rest.ensembl.org/"));
    }

    @Test(expected=NullPointerException.class)
    public void testCreateFeatureServiceNullServerURL() {
        factory.createFeatureService(null);
    }

    @Test
    public void testCreateFeatureService() {
        assertNotNull(factory.createFeatureService("http://beta.rest.ensembl.org/"));
    }

    @Test
    public void testCreateFeatureServiceDefaultServerUrl() {
        assertNotNull(factory.createFeatureService());
    }

    @Test(expected=NullPointerException.class)
    public void testCreateLookupServiceNullServerURL() {
        factory.createLookupService(null);
    }

    @Test
    public void testCreateLookupService() {
        assertNotNull(factory.createLookupService("http://beta.rest.ensembl.org/"));
    }

    @Test
    public void testCreateLookupServiceDefaultServerUrl() {
        assertNotNull(factory.createLookupService());
    }

    @Test(expected=NullPointerException.class)
    public void testCreateVariationServiceNullServerURL() {
        factory.createVariationService(null);
    }

    @Test
    public void testCreateVariationService() {
        assertNotNull(factory.createVariationService("http://beta.rest.ensembl.org/"));
    }

    @Test
    public void testCreateVariationServiceDefaultServerUrl() {
        assertNotNull(factory.createVariationService());
    }

    @Test(expected=NullPointerException.class)
    public void testCreateSequenceServiceNullServerURL() {
        factory.createSequenceService(null);
    }

    @Test
    public void testCreateSequenceService() {
        assertNotNull(factory.createSequenceService("http://beta.rest.ensembl.org/"));
    }

    @Test
    public void testCreateSequenceServiceDefaultServerUrl() {
        assertNotNull(factory.createSequenceService());
    }
}

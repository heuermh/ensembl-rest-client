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

import static org.junit.Assert.assertNotNull;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for EnsemblRestClientModule.
 */
public final class EnsemblRestClientModuleTest {
    private EnsemblRestClientModule module;

    @Before
    public void setUp() {
        module = new EnsemblRestClientModule();
    }

    @Test
    public void testConstructor() {
        assertNotNull(module);
    }

    @Test
    public void testEnsemblRestClientModule() {
        Injector injector = Guice.createInjector(module);
        FeatureService featureService = injector.getInstance(FeatureService.class);
        LookupService lookupService = injector.getInstance(LookupService.class);
        VariationService variationService = injector.getInstance(VariationService.class);
        SequenceService sequenceService = injector.getInstance(SequenceService.class);
        assertNotNull(featureService);
        assertNotNull(lookupService);
        assertNotNull(variationService);
        assertNotNull(sequenceService);
    }
}
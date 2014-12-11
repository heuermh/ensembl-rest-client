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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableList;

/**
 * Variation consequences.
 *
 * @author  Michael Heuer
 */
@Immutable
public final class VariationConsequences {
    private final String identifier;
    private final String referenceAllele;
    private final List<String> alternateAlleles;
    private final Location location;
    private final List<TranscriptConsequences> transcriptConsequences;


    VariationConsequences(final String identifier,
                          final String referenceAllele,
                          final List<String> alternateAlleles,
                          final Location location,
                          final List<TranscriptConsequences> transcriptConsequences) {

        checkNotNull(alternateAlleles);
        checkNotNull(location);
        checkNotNull(transcriptConsequences);

        this.identifier = identifier;
        this.referenceAllele = referenceAllele;
        this.alternateAlleles = ImmutableList.copyOf(alternateAlleles);
        this.location = location;
        this.transcriptConsequences = ImmutableList.copyOf(transcriptConsequences);
    }


    public String getIdentifier() {
        return identifier;
    }

    public String getReferenceAllele() {
        return referenceAllele;
    }

    public List<String> getAlternateAlleles() {
        return alternateAlleles;
    }

    public Location getLocation() {
        return location;
    }

    public List<TranscriptConsequences> getTranscriptConsequences() {
        return transcriptConsequences;
    }
}

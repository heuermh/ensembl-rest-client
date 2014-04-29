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

import com.google.common.collect.ImmutableList;

/**
 * Variation consequences.
 *
 * @author  Michael Heuer
 */
public final class VariationConsequences {
    private final String name;
    private final boolean somatic;
    private final Location location;
    private final List<Transcript> transcripts;

    VariationConsequences(final String name,
                          final boolean somatic,
                          final Location location,
                          final List<Transcript> transcripts) {

        checkNotNull(location);
        checkNotNull(transcripts);

        this.name = name;
        this.somatic = somatic;
        this.location = location;
        this.transcripts = ImmutableList.copyOf(transcripts);
    }

    public String getName() {
        return name;
    }

    public boolean isSomatic() {
        return somatic;
    }

    public Location getLocation() {
        return location;
    }

    public List<Transcript> getTranscripts() {
        return transcripts;
    }
}

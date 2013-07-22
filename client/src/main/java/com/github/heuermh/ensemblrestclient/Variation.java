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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Variation.
 */
public final class Variation {
    private final String id;
    private final String reference;
    private final String alternate;
    private final Location location;

    Variation(final String id,
              final String reference,
              final String alternate,
              final Location location) {

        checkNotNull(id);
        checkNotNull(reference);
        checkNotNull(alternate);
        checkNotNull(location);

        this.id = id;
        this.reference = reference;
        this.alternate = alternate;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public String getAlternate() {
        return alternate;
    }

    public Location getLocation() {
        return location;
    }
}
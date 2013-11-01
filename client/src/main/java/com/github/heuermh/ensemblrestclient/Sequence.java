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
 * Sequence.
 *
 * @since 1.3
 */
public final class Sequence {
    private final String identifier;
    private final String sequence;
    private final String molecule;

    Sequence(final String identifier,
             final String sequence,
             final String molecule) {

        checkNotNull(identifier);
        checkNotNull(sequence);
        checkNotNull(molecule);

        this.identifier = identifier;
        this.sequence = sequence;
        this.molecule = molecule;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getSequence() {
        return sequence;
    }

    public String getMolecule() {
        return molecule;
    }
}
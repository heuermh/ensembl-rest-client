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

/**
 * Archived sequence.
 *
 * @since 2.0
 * @author  Michael Heuer
 */
public final class ArchivedSequence {
    private final String id;
    private final String type;
    private final String assembly;
    private final String release;
    private final String version;
    private final String latest;
    private final String peptide;
    private final boolean current;
    private final String[] possibleReplacement;


    ArchivedSequence(final String id,
                     final String type,
                     final String assembly,
                     final String release,
                     final String version,
                     final String latest,
                     final String peptide,
                     final boolean current,
                     final String[] possibleReplacement) {
        this.id = id;
        this.type = type;
        this.assembly = assembly;
        this.release = release;
        this.version = version;
        this.latest = latest;
        this.peptide = peptide;
        this.current = current;
        this.possibleReplacement = possibleReplacement;
    }


    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getAssembly() {
        return assembly;
    }

    public String getRelease() {
        return release;
    }

    public String getVersion() {
        return version;
    }

    public String getLatest() {
        return latest;
    }

    public String getPeptide() {
        return peptide;
    }

    public boolean isCurrent() {
        return current;
    }

    public String[] getPossibleReplacement() {
        return possibleReplacement;
    }
}

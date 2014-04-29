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
 * Transcript.
 *
 * @author  Michael Heuer
 */
public final class Transcript {
    private final String name;
    private final String biotype;
    private final boolean canonical;
    private final String geneId;
    private final String transcriptId;
    private final String translationId;
    private final String alleleString;
    private final List<Allele> alleles;

    Transcript(final String name,
               final String biotype,
               final boolean canonical,
               final String geneId,
               final String transcriptId,
               final String translationId,
               final String alleleString,
               final List<Allele> alleles) {

        checkNotNull(name);
        checkNotNull(biotype);
        checkNotNull(transcriptId);
        checkNotNull(alleles);

        this.name = name;
        this.biotype = biotype;
        this.canonical = canonical;
        this.geneId = geneId;
        this.transcriptId = transcriptId;
        this.translationId = translationId;
        this.alleleString = alleleString;
        this.alleles = ImmutableList.copyOf(alleles);
    }

    public String getName() {
        return name;
    }

    public String getBiotype() {
        return biotype;
    }

    public boolean isCanonical() {
        return canonical;
    }

    public String getGeneId() {
        return geneId;
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public String getTranslationId() {
        return translationId;
    }

    public String getAlleleString() {
        return alleleString;
    }

    public List<Allele> getAlleles() {
        return alleles;
    }
}

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
 * Transcript consequences.
 *
 * @author  Michael Heuer
 */
public final class TranscriptConsequences {
    private final String alternateAllele;
    private final int strand;
    private final boolean canonical;
    private final String geneId;
    private final String transcriptId;
    private final String translationId;
    private final String codons;
    private final String hgvsc;
    private final String aminoAcids;
    private final String hgvsp;
    private final List<String> consequenceTerms;

    TranscriptConsequences(final String alternateAllele,
                           final int strand,
                           final boolean canonical,
                           final String geneId,
                           final String transcriptId,
                           final String translationId,
                           final String codons,
                           final String hgvsc,
                           final String aminoAcids,
                           final String hgvsp,
                           final List<String> consequenceTerms) {

        checkNotNull(alternateAllele);
        checkNotNull(transcriptId);
        checkNotNull(consequenceTerms);

        this.alternateAllele = alternateAllele;
        this.strand = strand;
        this.canonical = canonical;
        this.geneId = geneId;
        this.transcriptId = transcriptId;
        this.translationId = translationId;
        this.codons = codons;
        this.hgvsc = hgvsc;
        this.aminoAcids = aminoAcids;
        this.hgvsp = hgvsp;
        this.consequenceTerms = ImmutableList.copyOf(consequenceTerms);
    }


    public String getAlternateAllele() {
        return alternateAllele;
    }

    public int getStrand() {
        return strand;
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

    public String getCodons() {
        return codons;
    }

    public String getHgvsC() {
        return hgvsc;
    }

    public String getAminoAcids() {
        return aminoAcids;
    }

    public String getHgvsP() {
        return hgvsp;
    }

    public List<String> getConsequenceTerms() {
        return consequenceTerms;
    }
}

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
package com.github.heuermh.ensemblrestclient.example;

import com.github.heuermh.ensemblrestclient.ArchivedSequence;
import com.github.heuermh.ensemblrestclient.ArchiveService;
import com.github.heuermh.ensemblrestclient.EnsemblRestClientModule;
import com.github.heuermh.ensemblrestclient.Location;
import com.github.heuermh.ensemblrestclient.Lookup;
import com.github.heuermh.ensemblrestclient.LookupService;
import com.github.heuermh.ensemblrestclient.OverlapService;
import com.github.heuermh.ensemblrestclient.Sequence;
import com.github.heuermh.ensemblrestclient.SequenceService;
import com.github.heuermh.ensemblrestclient.TranscriptConsequences;
import com.github.heuermh.ensemblrestclient.Variation;
import com.github.heuermh.ensemblrestclient.VariationService;
import com.github.heuermh.ensemblrestclient.VariationConsequences;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Example.
 *
 * @author  Michael Heuer
 */
public final class Example {

    public static void main(final String args[]) {
        Injector injector = Guice.createInjector(new EnsemblRestClientModule());

        ArchiveService archiveService = injector.getInstance(ArchiveService.class);

        System.out.println("\narchived sequence, ENSG00000157764");
        ArchivedSequence archivedSequence = archiveService.archivedSequence("ENSG00000157764");
        System.out.println(archivedSequence.getId() + "\t" + archivedSequence.getType() + "\t" + archivedSequence.getAssembly() + "\t" + archivedSequence.getRelease() + "\t" + archivedSequence.getVersion() + "\t" + archivedSequence.getLatest());

        LookupService lookupService = injector.getInstance(LookupService.class);

        System.out.println("\nlookup, ENSG00000157764");
        Lookup ensg00000157764 = lookupService.lookup("human", "ENSG00000157764");
        System.out.println(ensg00000157764.getIdentifier() + "\t" + ensg00000157764.getSpecies() + "\t" + ensg00000157764.getType() + "\t" + ensg00000157764.getDatabase() + "\t" + ensg00000157764.getLocation().getName() + "\t" + ensg00000157764.getLocation().getStart() + "\t" + ensg00000157764.getLocation().getEnd() + "\t" + ensg00000157764.getLocation().getStrand());

        OverlapService overlapService = injector.getInstance(OverlapService.class);

        System.out.println("\noverlapping variations, 7:140424943-140425043");
        for (Variation variation : overlapService.variations("human", "7:140424943-140425043")) {
            System.out.println(variation.getIdentifier() + "\t" + variation.getReferenceAllele() + "\t" + variation.getAlternateAlleles() + "\t" + variation.getLocation().getName() + "\t" + variation.getLocation().getStart() + "\t" + variation.getLocation().getEnd() + "\t" + variation.getLocation().getStrand());
        }

        VariationService variationService = injector.getInstance(VariationService.class);

        System.out.println("\nvariation, rs376247534");
        Variation rs376247534 = variationService.variation("human", "rs376247534");
        System.out.println(rs376247534.getIdentifier() + "\t" + rs376247534.getReferenceAllele() + "\t" + rs376247534.getAlternateAlleles() + "\t" + rs376247534.getLocation().getName() + "\t" + rs376247534.getLocation().getStart() + "\t" + rs376247534.getLocation().getEnd() + "\t" + rs376247534.getLocation().getStrand());

        System.out.println("\nconsequences id search, COSM476");
        VariationConsequences cosm476 = variationService.consequences("human", "COSM476");

        for (TranscriptConsequences transcript : cosm476.getTranscriptConsequences()) {
            for (String consequenceTerm : transcript.getConsequenceTerms()) {
                Location location = cosm476.getLocation();
                System.out.println(cosm476.getIdentifier() + "\t" + cosm476.getReferenceAllele() + "\t" + cosm476.getAlternateAlleles() + "\t" + location.getName() + "\t" + location.getStart() + "\t" + location.getEnd() + "\t" + location.getStrand() + "\t" + transcript.getGeneId() + "\t" + transcript.getTranscriptId() + "\t" + (transcript.isCanonical() ? "*" : "") + "\t" + consequenceTerm);
            }
        }

        System.out.println("\nconsequences region search, 9:22125502-22125502:1 T>C");
        VariationConsequences region = variationService.consequences("human", "9:22125502-22125502:1", "C");

        for (TranscriptConsequences transcript : region.getTranscriptConsequences()) {
            for (String consequenceTerm : transcript.getConsequenceTerms()) {
                Location location = region.getLocation();
                System.out.println(region.getIdentifier() + "\t" + region.getReferenceAllele() + "\t" + region.getAlternateAlleles() + "\t" + location.getName() + "\t" + location.getStart() + "\t" + location.getEnd() + "\t" + location.getStrand() + "\t" + transcript.getGeneId() + "\t" + transcript.getTranscriptId() + "\t" + (transcript.isCanonical() ? "*" : "") + "\t" + consequenceTerm);
            }
        }

        SequenceService sequenceService = injector.getInstance(SequenceService.class);

        System.out.println("\nsequence, 9:22125502-22125502:1 plus 25 bp flanking sequence");
        Sequence sequence = sequenceService.sequence("human", "9:22125502-22125502:1", 25, 25, "soft");

        System.out.println(">" + sequence.getIdentifier());
        System.out.println(sequence.getSequence());
    }
}

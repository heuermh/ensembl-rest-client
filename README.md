ensembl-rest-client
===================

Java client for the Ensembl REST API.

[![Build Status](https://travis-ci.org/heuermh/ensembl-rest-client.png)](https://travis-ci.org/heuermh/ensembl-rest-client)


###Hacking ensembl-rest-client

Install

 * JDK 1.6 or later, http://openjdk.java.net/
 * Apache Maven 3.0.5 or later, http://maven.apache.org/


To build

    $ mvn install


To assemble example

    $ cd example
    $ mvn assembly:assembly


To run example

    $ java -jar target/ensembl-rest-client-example-1.0-SNAPSHOT-jar-with-dependencies.jar 
    lookup, ENSG00000157764
    ENSG00000157764 homo_sapiens    Gene    core    7       140424943       140624564       -1
    id search, COSM476
    COSM476    7    140453136       140453136       1       ENSG00000157764 ENST00000288602 A/T     missense_variant
    COSM476    7    140453136       140453136       1       ENSG00000157764 ENST00000479537 A/T     missense_variant
    COSM476    7    140453136       140453136       1       ENSG00000157764 ENST00000479537 A/T     NMD_transcript_variant
    COSM476    7    140453136       140453136       1       ENSG00000157764 ENST00000496384 A/T     missense_variant
    COSM476    7    140453136       140453136       1       ENSG00000157764 ENST00000497784 A/T     NMD_transcript_variant
    COSM476    7    140453136       140453136       1       ENSG00000157764 ENST00000497784 A/T     3_prime_UTR_variant
    region search, 9:22125503-22125502:1
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000585267 -/C     downstream_gene_variant
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000580576 -/C     downstream_gene_variant
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000428597 -/C     downstream_gene_variant
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000584816 -/C     downstream_gene_variant
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000584020 -/C     downstream_gene_variant
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000577551 -/C     downstream_gene_variant
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000584637 -/C     downstream_gene_variant
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000581051 -/C     downstream_gene_variant
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000582072 -/C     downstream_gene_variant
    null   9       22125503 22125502        1       ENSG00000240498 ENST00000422420 -/C     downstream_gene_variant


###Using ensembl-rest-client

```java
// create an injector
Injector injector = Guice.createInjector(new EnsemblRestClientModule());

// lookup service
LookupService lookupService = injector.getInstance(LookupService.class);
Lookup ensg00000157764 = lookupService.lookup("human", "ENSG00000157764");
    
// variation service
VariationService variationService = injector.getInstance(VariationService.class);
VariationConsequences cosm476 = variationService.consequences("human", "COSM476");
VariationConsequences region = variationService.consequences("human", "9:22125503-22125502:1", "C");
```
ensembl-rest-client
===================

Java client for the Ensembl REST API.

[![Build Status](https://travis-ci.org/heuermh/ensembl-rest-client.png)](https://travis-ci.org/heuermh/ensembl-rest-client)


###Hacking ensembl-rest-client

Install

 * JDK 1.7 or later, http://openjdk.java.net/
 * Apache Maven 3.2.3 or later, http://maven.apache.org/


To build

    $ mvn install


To assemble example

    $ cd example
    $ mvn assembly:assembly


To run example
    
    $ java -jar target/ensembl-rest-client-example-2.0-SNAPSHOT-jar-with-dependencies.jar 
    
    archived sequence, 
    ENSG00000157764    Gene GRCh38  77      10      ENSG00000157764.10
    
    lookup, ENSG00000157764
    ENSG00000157764 homo_sapiens    Gene    core    7       140719327       140924764       -1
    
    overlapping variations, 7:140424943-140425043
    rs7793448   T           [A]     7       140425023       140425023       1
    rs10244642  T           [C]     7       140425026       140425026       1
    
    variation, rs376247534
    rs376247534     T       [C]     7       140425406       140425406       1
    
    consequences id search, COSM476
    COSM476      7  140753336       140753336       1       ENSG00000157764 ENST00000479537 T       missense_variant
    COSM476      7  140753336       140753336       1       ENSG00000157764 ENST00000479537 T       NMD_transcript_variant
    COSM476      7  140753336       140753336       1       ENSG00000157764 ENST00000288602 T       missense_variant
    COSM476      7  140753336       140753336       1       ENSG00000157764 ENST00000496384 T       missense_variant
    COSM476      7  140753336       140753336       1       ENSG00000157764 ENST00000497784 T       3_prime_UTR_variant
    COSM476      7  140753336       140753336       1       ENSG00000157764 ENST00000497784 T       NMD_transcript_variant
    
    consequences region search, 9:22125503-22125502:1
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000584020 C       downstream_gene_variant
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000584816 C       downstream_gene_variant
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000585267 C       downstream_gene_variant
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000581051 C       downstream_gene_variant
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000577551 C       downstream_gene_variant
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000584637 C       downstream_gene_variant
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000582072 C       downstream_gene_variant
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000428597 C       downstream_gene_variant
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000422420 C       downstream_gene_variant
    temp         9      22125502    22125502        1       ENSG00000240498 ENST00000580576 C       downstream_gene_variant
    
    sequence, 9:22125502-22125502:1 plus 25 bp flanking sequence
    >chromosome:GRCh38:9:22125477:22125527:1
    CTCATACTAACCATATGATCAACAGTTGAAAAGCAGCCACTCGCAGAGGTA


###Using ensembl-rest-client

Add the following dependency declaration to your pom.xml

```xml
<dependency>
  <groupId>com.github.heuermh.ensemblrestclient</groupId>
  <artifactId>ensembl-rest-client</artifactId>
  <version>2.0-SNAPSHOT</version>
</dependency>
```


E.g.

```java
// create an injector
Injector injector = Guice.createInjector(new EnsemblRestClientModule());

// lookup service
LookupService lookupService = injector.getInstance(LookupService.class);
Lookup ensg00000157764 = lookupService.lookup("human", "ENSG00000157764");

// overlap service
OverlapService overlapService = injector.getInstance(OverlapService.class);
Variation variation : overlapService.variations("human", "7:140425000-140426000") { ... }

// variation service
VariationService variationService = injector.getInstance(VariationService.class);
VariationConsequences cosm476 = variationService.consequences("human", "COSM476");
VariationConsequences chr9 = variationService.consequences("human", "9:22125502-22125502:1", "C");

// sequence service
SequenceService sequenceService = injector.getInstance(SequenceService.class);
Sequence sequence = sequenceService.sequence("human", "9:22125502-22125502:1", 25, 25, "soft");
```

or for clients unable to use Guice injection

```java
// create a factory
EnsemblRestClientFactory factory = new EnsemblRestClientFactory("http://rest.ensembl.org/");

// lookup service
LookupService lookupService = factory.createLookupService();
Lookup ensg00000157764 = lookupService.lookup("human", "ENSG00000157764");

// overlap service
OverlapService overlapService = factory.createOverlapService();
Variation variation : overlapService.variations("human", "7:140425000-140426000") { ... }
    
// variation service
VariationService variationService = factory.createVariationService();
VariationConsequences cosm476 = variationService.consequences("human", "COSM476");
VariationConsequences chr9 = variationService.consequences("human", "9:22125502-22125502:1", "C");

// sequence service
SequenceService sequenceService = factory.createSequenceService();
Sequence sequence = sequenceService.sequence("human", "9:22125502-22125502:1", 25, 25, "soft");
```


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

    $ java -jar target/ensembl-rest-client-example-1.3-SNAPSHOT-jar-with-dependencies.jar
    
    features, 7:140424943-140425943
    rs185077298     C       T       7       140424949       140424949       1
    rs188275729     G       A       7       140424968       140424968       1
    rs180985059     C       G       7       140424979       140424979       1
    rs55702309      A       T       7       140425000       140425000       1
    rs139404728     A       G       7       140425101       140425101       1
    rs184694572     A       G       7       140425102       140425102       1
    rs145149263     AAG     -       7       140425282       140425284       1
    rs189717101     A       G       7       140425377       140425377       1
    rs181357222     A       G       7       140425385       140425385       1
    rs144129600     G       A       7       140425388       140425388       1
    rs186773220     A       G       7       140425423       140425423       1
    rs188964443     G       C       7       140425441       140425441       1
    rs34603310      A       -       7       140425500       140425500       1
    rs78422198      AA      -       7       140425500       140425501       1
    rs78068602      A       C       7       140425511       140425511       1
    rs80171608      C       A       7       140425514       140425514       1
    rs75839948      G       C       7       140425720       140425720       1
    
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
    
    sequence, 9:22125502-22125502:1 plus 25 bp flanking sequence
    >chromosome:GRCh37:9:22125477:22125527:1
    TCATACTAACCATATGATCAACAGTTGAAAAGCAGCCACTCGCAGAGGTAA


###Using ensembl-rest-client

Add the following dependency declaration to your pom.xml

```xml
<dependency>
  <groupId>com.github.heuermh.ensemblrestclient</groupId>
  <artifactId>ensembl-rest-client</artifactId>
  <version>1.3-SNAPSHOT</version>
</dependency>
```


E.g.

```java
// create an injector
Injector injector = Guice.createInjector(new EnsemblRestClientModule());

// feature service
FeatureService featureService = injector.getInstance(FeatureService.class);
Variation rs185077298 = featureService.variationFeature("human", "rs185077298");
List<Variation> chr7 = featureService.variationFeatures("human", "7:140424943-140425943");

// lookup service
LookupService lookupService = injector.getInstance(LookupService.class);
Lookup ensg00000157764 = lookupService.lookup("human", "ENSG00000157764");
    
// variation service
VariationService variationService = injector.getInstance(VariationService.class);
VariationConsequences cosm476 = variationService.consequences("human", "COSM476");
VariationConsequences chr9 = variationService.consequences("human", "9:22125503-22125502:1", "C");

// sequence service
SequenceService sequenceService = injector.getInstance(SequenceService.class);
Sequence sequence = sequenceService.sequence("human", "9:22125502-22125502:1", 25, 25, "soft");
```

or for clients unable to use Guice injection

```java
// create a factory
EnsemblRestClientFactory factory = new EnsemblRestClientFactory("http://beta.rest.ensembl.org/");

// feature service
FeatureService featureService = factory.createFeatureService();
Variation rs185077298 = featureService.variationFeature("human", "rs185077298");
List<Variation> chr7 = featureService.variationFeatures("human", "7:140424943-140425943");

// lookup service
LookupService lookupService = factory.createLookupService();
Lookup ensg00000157764 = lookupService.lookup("human", "ENSG00000157764");
    
// variation service
VariationService variationService = factory.createVariationService();
VariationConsequences cosm476 = variationService.consequences("human", "COSM476");
VariationConsequences chr9 = variationService.consequences("human", "9:22125503-22125502:1", "C");

// sequence service
SequenceService sequenceService = factory.createSequenceService();
Sequence sequence = sequenceService.sequence("human", "9:22125502-22125502:1", 25, 25, "soft");
```

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/heuermh/ensembl-rest-client/trend.png)](https://bitdeli.com/free "Bitdeli Badge")


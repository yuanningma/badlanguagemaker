package edu.brown.cs.group1.search;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SearchTest {

  @Test
  public void testConstructor() {
    List<String> doc1 = new ArrayList<String>(Arrays.asList("This",
        "is",
        "first",
        "first",
        "line"));
    List<String> doc2 = new ArrayList<String>(Arrays.asList("I",
        "really",
        "hate",
        "this",
        "thing"));
    List<List<String>> docs = new ArrayList<List<String>>(Arrays.asList(doc1,
        doc2));
    Search search = new Search(docs);
    assertNotNull(search);

    assertTrue(search.termFrequency("first", doc1) == 2.0);

  }

  @Test
  public void testIdf() {
    List<String> antDoc = new ArrayList<String>(Arrays.asList("Ants are eusocial insects of the family Formicidae and, along with the related wasps and bees, belong to the order Hymenoptera. Ants evolved from wasp-like ancestors in the Cretaceous period, about 140 million years ago, and diversified after the rise of flowering plants. More than 12,500 of an estimated total of 22,000 species have been classified.[5][6] They are easily identified by their elbowed antennae and the distinctive node-like structure that forms their slender waists. Ants form colonies that range in size from a few dozen predatory individuals living in small natural cavities to highly organised colonies that may occupy large territories and consist of millions of individuals. Larger colonies consist of various castes of sterile, wingless females, most of which are workers (ergates), as well as soldiers (dinergates) and other specialised groups.[7][8] Nearly all ant colonies also have some fertile males called 'drones' (aner) and one or more fertile females called 'queens' (gynes).[8] The colonies are described as superorganisms because the ants appear to operate as a unified entity, collectively working together to support the colony.[9][10] Ants have colonised almost every landmass on Earth. The only places lacking indigenous ants are Antarctica and a few remote or inhospitable islands. Ants thrive in most ecosystems and may form 15–25% of the terrestrial animal biomass.[11] Their success in so many environments has been attributed to their social organisation and their ability to modify habitats, tap resources, and defend themselves. Their long co-evolution with other species has led to mimetic, commensal, parasitic, and mutualistic relationships.[12] Ant societies have division of labour, communication between individuals, and an ability to solve complex problems.[13] These parallels with human societies have long been an inspiration and subject of study. Many human cultures make use of ants in cuisine, medication, and rituals. Some species are valued in their role as biological pest control agents.[14] Their ability to exploit resources may bring ants into conflict with humans, however, as they can damage crops and invade buildings. Some species, such as the red imported fire ant (Solenopsis invicta), are regarded as invasive species, establishing themselves in areas where they have been introduced accidentally.[15]".split(" ")));
    List<String> anteaterDoc = new ArrayList<String>(Arrays.asList("Anteater is a common name for the four extant mammal species of the suborder Vermilingua[1] (meaning 'worm tongue') commonly known for eating ants and termites.[2] The individual species have other names in English and other languages. Together with the sloths, they are within the order Pilosa. The name 'anteater' is also colloquially applied to the unrelated aardvark, numbat, echidnas, pangolins and some members of the Oecobiidae. Extant species are the giant anteater Myrmecophaga tridactyla, about 1.8 m (5 ft 11 in) long including the tail; the silky anteater Cyclopes didactylus, about 35 cm (14 in) long; the southern tamandua or collared anteater Tamandua tetradactyla, about 1.2 m (3 ft 11 in) long; and the northern tamandua Tamandua mexicana of similar dimensions.".split(" ")));
    List<String> fishDoc = new ArrayList<String>(Arrays.asList("Fish are gill-bearing aquatic craniate animals that lack limbs with digits. They form a sister group to the tunicates, together forming the olfactores. Included in this definition are the living hagfish, lampreys, and cartilaginous and bony fish as well as various extinct related groups. Tetrapods emerged within lobe-finned fishes, so cladistically they are fish as well. However, traditionally fish are rendered paraphyletic by excluding the tetrapods (i.e., the amphibians, reptiles, birds and mammals which all descended from within the same ancestry). Because in this manner the term 'fish' is defined negatively as a paraphyletic group, it is not considered a formal taxonomic grouping in systematic biology, unless it is used in the cladistic sense, including tetrapods.[1][2] The traditional term pisces (also ichthyes) is considered a typological, but not a phylogenetic classification. The earliest organisms that can be classified as fish were soft-bodied chordates that first appeared during the Cambrian period. Although they lacked a true spine, they possessed notochords which allowed them to be more agile than their invertebrate counterparts. Fish would continue to evolve through the Paleozoic era, diversifying into a wide variety of forms. Many fish of the Paleozoic developed external armor that protected them from predators. The first fish with jaws appeared in the Silurian period, after which many (such as sharks) became formidable marine predators rather than just the prey of arthropods. Most fish are ectothermic ('cold-blooded'), allowing their body temperatures to vary as ambient temperatures change, though some of the large active swimmers like white shark and tuna can hold a higher core temperature.[3][4] Fish can communicate in their underwater environments through the use of acoustic communication. Acoustic communication in fish involves the transmission of acoustic signals from one individual of a species to another. The production of sounds as a means of communication among fish is most often used in the context of feeding, aggression or courtship behaviour.[5] The sounds emitted by fish can vary depending on the species and stimulus involved. They can produce either stridulatory sounds by moving components of the skeletal system, or can produce non-stridulatory sounds by manipulating specialized organs such as the swimbladder.[6] Fish are abundant in most bodies of water. They can be found in nearly all aquatic environments, from high mountain streams (e.g., char and gudgeon) to the abyssal and even hadal depths of the deepest oceans (e.g., gulpers and anglerfish), although no species has yet been documented in the deepest 25% of the ocean. With 33,600 described species, fish exhibit greater species diversity than any other group of vertebrates.[7][8] Fish are an important resource for humans worldwide, especially as food. Commercial and subsistence fishers hunt fish in wild fisheries (see fishing) or farm them in ponds or in cages in the ocean (see aquaculture). They are also caught by recreational fishers, kept as pets, raised by fishkeepers, and exhibited in public aquaria. Fish have had a role in culture through the ages, serving as deities, religious symbols, and as the subjects of art, books and movies.".split(" ")));

    List<List<String>> docs = new ArrayList<List<String>>(Arrays.asList(antDoc,
        anteaterDoc,
        fishDoc));
    Search search = new Search(docs);
    double antIdf = search.tfIdf("ants", antDoc);
    double anteaterIdf = search.tfIdf("ants", anteaterDoc);
    double fishIdf = search.tfIdf("ants", fishDoc);

    System.out.println("ANTS: " + antIdf
        + ", EATERS: "
        + anteaterIdf
        + ", FISH: "
        + fishIdf);
  }

  @Test
  public void testRanking() {
    List<String> antDoc = new ArrayList<String>(Arrays.asList("Ants are eusocial insects of the family Formicidae and, along with the related wasps and bees, belong to the order Hymenoptera. Ants evolved from wasp-like ancestors in the Cretaceous period, about 140 million years ago, and diversified after the rise of flowering plants. More than 12,500 of an estimated total of 22,000 species have been classified.[5][6] They are easily identified by their elbowed antennae and the distinctive node-like structure that forms their slender waists. Ants form colonies that range in size from a few dozen predatory individuals living in small natural cavities to highly organised colonies that may occupy large territories and consist of millions of individuals. Larger colonies consist of various castes of sterile, wingless females, most of which are workers (ergates), as well as soldiers (dinergates) and other specialised groups.[7][8] Nearly all ant colonies also have some fertile males called 'drones' (aner) and one or more fertile females called 'queens' (gynes).[8] The colonies are described as superorganisms because the ants appear to operate as a unified entity, collectively working together to support the colony.[9][10] Ants have colonised almost every landmass on Earth. The only places lacking indigenous ants are Antarctica and a few remote or inhospitable islands. Ants thrive in most ecosystems and may form 15–25% of the terrestrial animal biomass.[11] Their success in so many environments has been attributed to their social organisation and their ability to modify habitats, tap resources, and defend themselves. Their long co-evolution with other species has led to mimetic, commensal, parasitic, and mutualistic relationships.[12] Ant societies have division of labour, communication between individuals, and an ability to solve complex problems.[13] These parallels with human societies have long been an inspiration and subject of study. Many human cultures make use of ants in cuisine, medication, and rituals. Some species are valued in their role as biological pest control agents.[14] Their ability to exploit resources may bring ants into conflict with humans, however, as they can damage crops and invade buildings. Some species, such as the red imported fire ant (Solenopsis invicta), are regarded as invasive species, establishing themselves in areas where they have been introduced accidentally.[15]".split(" ")));
    List<String> anteaterDoc = new ArrayList<String>(Arrays.asList("Anteater is a common name for the four extant mammal species of the suborder Vermilingua[1] (meaning 'worm tongue') commonly known for eating ants and termites.[2] The individual species have other names in English and other languages. Together with the sloths, they are within the order Pilosa. The name 'anteater' is also colloquially applied to the unrelated aardvark, numbat, echidnas, pangolins and some members of the Oecobiidae. Extant species are the giant anteater Myrmecophaga tridactyla, about 1.8 m (5 ft 11 in) long including the tail; the silky anteater Cyclopes didactylus, about 35 cm (14 in) long; the southern tamandua or collared anteater Tamandua tetradactyla, about 1.2 m (3 ft 11 in) long; and the northern tamandua Tamandua mexicana of similar dimensions.".split(" ")));
    List<String> fishDoc = new ArrayList<String>(Arrays.asList("Fish are gill-bearing aquatic craniate animals that lack limbs with digits. They form a sister group to the tunicates, together forming the olfactores. Included in this definition are the living hagfish, lampreys, and cartilaginous and bony fish as well as various extinct related groups. Tetrapods emerged within lobe-finned fishes, so cladistically they are fish as well. However, traditionally fish are rendered paraphyletic by excluding the tetrapods (i.e., the amphibians, reptiles, birds and mammals which all descended from within the same ancestry). Because in this manner the term 'fish' is defined negatively as a paraphyletic group, it is not considered a formal taxonomic grouping in systematic biology, unless it is used in the cladistic sense, including tetrapods.[1][2] The traditional term pisces (also ichthyes) is considered a typological, but not a phylogenetic classification. The earliest organisms that can be classified as fish were soft-bodied chordates that first appeared during the Cambrian period. Although they lacked a true spine, they possessed notochords which allowed them to be more agile than their invertebrate counterparts. Fish would continue to evolve through the Paleozoic era, diversifying into a wide variety of forms. Many fish of the Paleozoic developed external armor that protected them from predators. The first fish with jaws appeared in the Silurian period, after which many (such as sharks) became formidable marine predators rather than just the prey of arthropods. Most fish are ectothermic ('cold-blooded'), allowing their body temperatures to vary as ambient temperatures change, though some of the large active swimmers like white shark and tuna can hold a higher core temperature.[3][4] Fish can communicate in their underwater environments through the use of acoustic communication. Acoustic communication in fish involves the transmission of acoustic signals from one individual of a species to another. The production of sounds as a means of communication among fish is most often used in the context of feeding, aggression or courtship behaviour.[5] The sounds emitted by fish can vary depending on the species and stimulus involved. They can produce either stridulatory sounds by moving components of the skeletal system, or can produce non-stridulatory sounds by manipulating specialized organs such as the swimbladder.[6] Fish are abundant in most bodies of water. They can be found in nearly all aquatic environments, from high mountain streams (e.g., char and gudgeon) to the abyssal and even hadal depths of the deepest oceans (e.g., gulpers and anglerfish), although no species has yet been documented in the deepest 25% of the ocean. With 33,600 described species, fish exhibit greater species diversity than any other group of vertebrates.[7][8] Fish are an important resource for humans worldwide, especially as food. Commercial and subsistence fishers hunt fish in wild fisheries (see fishing) or farm them in ponds or in cages in the ocean (see aquaculture). They are also caught by recreational fishers, kept as pets, raised by fishkeepers, and exhibited in public aquaria. Fish have had a role in culture through the ages, serving as deities, religious symbols, and as the subjects of art, books and movies.".split(" ")));

    List<List<String>> docs = new ArrayList<List<String>>(Arrays.asList(antDoc,
        anteaterDoc,
        fishDoc));
    Search search = new Search(docs);

    List<List<String>> sortedDocs = new ArrayList<List<String>>();
    List<String> terms = new ArrayList<String>();
    terms.add("ants");
    // terms.add("ant");
    sortedDocs = search.rankDocs(terms, docs);
    // System.out.println(sortedDocs.size());
    // System.out.println(sortedDocs.get(0));
    // System.out.println(sortedDocs.get(1));
    assertTrue(sortedDocs.get(0).equals(antDoc));
    assertTrue(sortedDocs.get(1).equals(anteaterDoc));
    terms.add("ant");
    terms.add("mammal");
    sortedDocs = search.rankDocs(terms, docs);
    // System.out.println(sortedDocs.get(0));
    // System.out.println(sortedDocs.get(1));

  }

  @Test
  public void testThreading() throws InterruptedException {
    List<String> antDoc = new ArrayList<String>(Arrays.asList("Ants are eusocial insects of the family Formicidae and, along with the related wasps and bees, belong to the order Hymenoptera. Ants evolved from wasp-like ancestors in the Cretaceous period, about 140 million years ago, and diversified after the rise of flowering plants. More than 12,500 of an estimated total of 22,000 species have been classified.[5][6] They are easily identified by their elbowed antennae and the distinctive node-like structure that forms their slender waists. Ants form colonies that range in size from a few dozen predatory individuals living in small natural cavities to highly organised colonies that may occupy large territories and consist of millions of individuals. Larger colonies consist of various castes of sterile, wingless females, most of which are workers (ergates), as well as soldiers (dinergates) and other specialised groups.[7][8] Nearly all ant colonies also have some fertile males called 'drones' (aner) and one or more fertile females called 'queens' (gynes).[8] The colonies are described as superorganisms because the ants appear to operate as a unified entity, collectively working together to support the colony.[9][10] Ants have colonised almost every landmass on Earth. The only places lacking indigenous ants are Antarctica and a few remote or inhospitable islands. Ants thrive in most ecosystems and may form 15–25% of the terrestrial animal biomass.[11] Their success in so many environments has been attributed to their social organisation and their ability to modify habitats, tap resources, and defend themselves. Their long co-evolution with other species has led to mimetic, commensal, parasitic, and mutualistic relationships.[12] Ant societies have division of labour, communication between individuals, and an ability to solve complex problems.[13] These parallels with human societies have long been an inspiration and subject of study. Many human cultures make use of ants in cuisine, medication, and rituals. Some species are valued in their role as biological pest control agents.[14] Their ability to exploit resources may bring ants into conflict with humans, however, as they can damage crops and invade buildings. Some species, such as the red imported fire ant (Solenopsis invicta), are regarded as invasive species, establishing themselves in areas where they have been introduced accidentally.[15]".split(" ")));
    List<String> anteaterDoc = new ArrayList<String>(Arrays.asList("Anteater is a common name for the four extant mammal species of the suborder Vermilingua[1] (meaning 'worm tongue') commonly known for eating ants and termites.[2] The individual species have other names in English and other languages. Together with the sloths, they are within the order Pilosa. The name 'anteater' is also colloquially applied to the unrelated aardvark, numbat, echidnas, pangolins and some members of the Oecobiidae. Extant species are the giant anteater Myrmecophaga tridactyla, about 1.8 m (5 ft 11 in) long including the tail; the silky anteater Cyclopes didactylus, about 35 cm (14 in) long; the southern tamandua or collared anteater Tamandua tetradactyla, about 1.2 m (3 ft 11 in) long; and the northern tamandua Tamandua mexicana of similar dimensions.".split(" ")));
    List<String> fishDoc = new ArrayList<String>(Arrays.asList("Fish are gill-bearing aquatic craniate animals that lack limbs with digits. They form a sister group to the tunicates, together forming the olfactores. Included in this definition are the living hagfish, lampreys, and cartilaginous and bony fish as well as various extinct related groups. Tetrapods emerged within lobe-finned fishes, so cladistically they are fish as well. However, traditionally fish are rendered paraphyletic by excluding the tetrapods (i.e., the amphibians, reptiles, birds and mammals which all descended from within the same ancestry). Because in this manner the term 'fish' is defined negatively as a paraphyletic group, it is not considered a formal taxonomic grouping in systematic biology, unless it is used in the cladistic sense, including tetrapods.[1][2] The traditional term pisces (also ichthyes) is considered a typological, but not a phylogenetic classification. The earliest organisms that can be classified as fish were soft-bodied chordates that first appeared during the Cambrian period. Although they lacked a true spine, they possessed notochords which allowed them to be more agile than their invertebrate counterparts. Fish would continue to evolve through the Paleozoic era, diversifying into a wide variety of forms. Many fish of the Paleozoic developed external armor that protected them from predators. The first fish with jaws appeared in the Silurian period, after which many (such as sharks) became formidable marine predators rather than just the prey of arthropods. Most fish are ectothermic ('cold-blooded'), allowing their body temperatures to vary as ambient temperatures change, though some of the large active swimmers like white shark and tuna can hold a higher core temperature.[3][4] Fish can communicate in their underwater environments through the use of acoustic communication. Acoustic communication in fish involves the transmission of acoustic signals from one individual of a species to another. The production of sounds as a means of communication among fish is most often used in the context of feeding, aggression or courtship behaviour.[5] The sounds emitted by fish can vary depending on the species and stimulus involved. They can produce either stridulatory sounds by moving components of the skeletal system, or can produce non-stridulatory sounds by manipulating specialized organs such as the swimbladder.[6] Fish are abundant in most bodies of water. They can be found in nearly all aquatic environments, from high mountain streams (e.g., char and gudgeon) to the abyssal and even hadal depths of the deepest oceans (e.g., gulpers and anglerfish), although no species has yet been documented in the deepest 25% of the ocean. With 33,600 described species, fish exhibit greater species diversity than any other group of vertebrates.[7][8] Fish are an important resource for humans worldwide, especially as food. Commercial and subsistence fishers hunt fish in wild fisheries (see fishing) or farm them in ponds or in cages in the ocean (see aquaculture). They are also caught by recreational fishers, kept as pets, raised by fishkeepers, and exhibited in public aquaria. Fish have had a role in culture through the ages, serving as deities, religious symbols, and as the subjects of art, books and movies.".split(" ")));
    List<String> whatDoc = new ArrayList<String>(Arrays.asList("What is an ant, anyways? I've never understood mankind's fascination with small insects. Ants are irksome and tiny, and I would rather eat an ant than eat one.".split(" ")));
    List<String> everythingDoc = new ArrayList<String>(Arrays.asList("Antinas ants ants ants ant ant insect mammal mammal fish fish mammal".split(" ")));
    List<String> nothingDoc = new ArrayList<String>(Arrays.asList("Shi jian dou qu na er le hai mei hao hao gan sou nian qing jiu lao le shen er yang ni yi bei zi man nao zi dou shi hai zi ku le xiao le shi jian dou qu na er le hai mei hao hao kan kan ni yan jin jiu hua le".split(" ")));

    List<List<String>> docs = new ArrayList<List<String>>(Arrays.asList(antDoc,
        anteaterDoc,
        fishDoc,
        whatDoc,
        everythingDoc,
        nothingDoc));
    Search search = new Search(docs);

    List<List<String>> sortedDocs = new ArrayList<List<String>>();
    List<String> terms = new ArrayList<String>();
    terms.add("ants");
    terms.add("ant");
    terms.add("mammal");
    terms.add("spark");
    // terms.add("ant");
    sortedDocs = search.rankDocs(terms, docs);
    System.out.println("TRUE SORTED");
    System.out.println(sortedDocs.get(0).get(0));
    System.out.println(sortedDocs.get(1).get(0));
    System.out.println(sortedDocs.get(2).get(0));
    System.out.println(sortedDocs.get(3).get(0));
    System.out.println(sortedDocs.get(4).get(0));
    System.out.println(sortedDocs.get(5).get(0));

    List<List<String>> threadSorted = search.threadedRankDocs(terms, docs);
    System.out.println("THREAD SORTED");
    System.out.println(threadSorted.get(0).get(0));
    System.out.println(threadSorted.get(1).get(0));
    System.out.println(threadSorted.get(2).get(0));
    System.out.println(threadSorted.get(3).get(0));
    System.out.println(threadSorted.get(4).get(0));
    System.out.println(threadSorted.get(5).get(0));

  }
}

package com.aoh.ghumdim.cosineSim;

import com.aoh.ghumdim.places.entity.Destinations;
import com.aoh.ghumdim.places.repo.DestinationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestCosine {
  private final CosineSimilarityService cosineSimilarityService;
  private final TextToVectorConverter textToVectorConverter;
  private final DestinationRepository destinationRepository;

  public List<Destinations> getDestinationsByCosineSimilarity(String query) {
    log.info("testing "+query);
//    List<Double> queryVector = textToVectorConverter.documentToVector(query.split(" "));
    textToVectorConverter.addDocument(query.split(" "));
    List<Double> queryVector = textToVectorConverter.documentToVector(query.split(" "));


    List<Destinations> destinations = destinationRepository.findAll();
    List<DestinationWithSimilarity> destinationWithSimilarities = new ArrayList<>();
    log.info(("==========================vectorr==============="));
    log.info(queryVector.toString());

    for (Destinations destination : destinations) {
      if(!destination.getStatus().equals("REJECTED")) {
        textToVectorConverter.addDocument(destination.getAddress().split(" "));
        List<Double> destinationVector = textToVectorConverter.documentToVector((destination.getName() + " " + destination.getDescription()).split(" "));
        double similarity = cosineSimilarityService.cosineSimilarity(queryVector, destinationVector);
        log.info(("===========================testing similarity==============="));
        log.info(String.valueOf(similarity));
        if(similarity >0) {
          destinationWithSimilarities.add(new DestinationWithSimilarity(destination, similarity));
        }
      }
    }
    destinationWithSimilarities.sort((d1, d2) -> Double.compare(d2.getSimilarity(), d1.getSimilarity()));

    List<Destinations> dest = new ArrayList<>();
    // Extract sorted destinations
    dest = destinationWithSimilarities.stream()
            .map(DestinationWithSimilarity::getDestination)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    Collections.reverse(dest);
    return dest;
  }

  private static class DestinationWithSimilarity {
    private final Destinations destination;
    private final double similarity;

    DestinationWithSimilarity(Destinations destination, double similarity) {
      this.destination = destination;
      this.similarity = similarity;
    }

    Destinations getDestination() {
      return destination;
    }

    double getSimilarity() {
      return similarity;
    }
  }
}

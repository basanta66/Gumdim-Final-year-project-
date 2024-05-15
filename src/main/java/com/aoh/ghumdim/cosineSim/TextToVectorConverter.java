package com.aoh.ghumdim.cosineSim;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TextToVectorConverter {
  private final Map<String, Integer> vocabulary;
  private int totalDocuments;

  public TextToVectorConverter() {
    this.vocabulary = new HashMap<>();
    this.totalDocuments = 0;
  }

  // add
  public void addDocument(String[] document) {
    for (String word : document) {
      vocabulary.put(word, vocabulary.getOrDefault(word, 0) + 1);
    }
    totalDocuments++;
  }

  //TF-IDF nikaleko
  public List<Double> documentToVector(String[] document) {
    List<Double> vector = new ArrayList<>();
    for (String term : vocabulary.keySet()) {
      double tf = termFrequency(term, document);
      double idf = inverseDocumentFrequency(term);
      vector.add(tf * idf);
    }
    return vector;
  }

  //TF calculation
  private double termFrequency(String term, String[] document) {
    double frequency = 0;
    for (String word : document) {
      if (term.equalsIgnoreCase(word)) {
        frequency++;
      }
    }
    return frequency / document.length;
  }

  // calculate with data available -> aaile chhai document ko description ra name
  private double inverseDocumentFrequency(String term) {
    if (vocabulary.containsKey(term)) {
      int documentFrequency = vocabulary.get(term);
      return Math.log((double) totalDocuments / (double) (documentFrequency + 1)) + 1; // add 1 to avoid zero division
    }
    return 0; // Term not found in vocabulary
  }
}

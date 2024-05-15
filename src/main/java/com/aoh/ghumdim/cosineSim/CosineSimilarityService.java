package com.aoh.ghumdim.cosineSim;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CosineSimilarityService {
  public double cosineSimilarity(List<Double> vectorA, List<Double> vectorB) {
    double dotProduct = dotProduct(vectorA, vectorB);
    // magnitude A
    double magnitudeA = magnitude(vectorA);
    //magnitude B
    double magnitudeB = magnitude(vectorB);
    // cosine similarity
    if (magnitudeA != 0 && magnitudeB != 0) {
      return dotProduct / (magnitudeA * magnitudeB);
    } else {
      return 0.0;
    }
  }
  // dueta ko dot product ko value
  private double dotProduct(List<Double> vectorA, List<Double> vectorB) {
    double result = 0.0;
    int n = Math.min(vectorA.size(), vectorB.size());
    for (int i = 0; i < n; i++) {
      result += vectorA.get(i) * vectorB.get(i);
    }
    return result;
  }
  //magnitude calculate garxa eslee
  private double magnitude(List<Double> vector) {
    double sum = 0.0;
    for (Double value : vector) {
      sum += Math.pow(value, 2);
    }
    return Math.sqrt(sum);
  }
}

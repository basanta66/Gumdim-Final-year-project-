package com.aoh.ghumdim.cosineSim.trash;

import java.util.*;

//@Component
//@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
public class BM25Okapi extends BM25 {


  private final double k1;
  private final double b;
  private final double epsilon;


  public BM25Okapi(List<List<String>> corpus, Tokenizer tokenizer, double k1, double b, double epsilon) {
    super(corpus, tokenizer);
    this.k1 = k1;
    this.b = b;
    this.epsilon = epsilon;
  }

  @Override
  protected void calculateIdf(Map<String, Integer> nd) {
    double idfSum = 0;
    List<String> negativeIdfs = new ArrayList<>();
    for (String word : nd.keySet()) {
      double idf = Math.log(corpusSize - nd.get(word) + 0.5) - Math.log(nd.get(word) + 0.5);
      idfSum += idf;
      if (idf < 0) {
        negativeIdfs.add(word);
      }
      this.idf.put(word, idf); // Change 'idf' to 'this.idf'
    }
    double avgIdf = idfSum / this.idf.size(); // Change 'idf' to 'this.idf'
    double eps = epsilon * avgIdf;
    for (String word : negativeIdfs) {
      this.idf.put(word, eps); // Change 'idf' to 'this.idf'
    }
  }


  @Override
  public double[] getScores(String[] query) {
    double[] score = new double[corpusSize];
    double[] docLengths = docLen.stream().mapToDouble(Integer::doubleValue).toArray();
    for (String q : query) {
      double[] qFreq = new double[corpusSize];
      for (int i = 0; i < corpusSize; i++) {
        qFreq[i] = docFreqs.get(i).getOrDefault(q, 0);
      }
      for (int i = 0; i < corpusSize; i++) {
        score[i] += (idf.getOrDefault(q, 0.0) * (qFreq[i] * (k1 + 1) /
                (qFreq[i] + k1 * (1 - b + b * docLengths[i] / avgdl))));
      }
    }
    return score;
  }

  @Override
  public double[] getBatchScores(String[] query, int[] docIds) {
    double[] score = new double[docIds.length];
    double[] docLengths = new double[docIds.length];
    for (int i = 0; i < docIds.length; i++) {
      docLengths[i] = docLen.get(docIds[i]);
    }
    for (String q : query) {
      double[] qFreq = new double[docIds.length];
      for (int i = 0; i < docIds.length; i++) {
        qFreq[i] = docFreqs.get(docIds[i]).getOrDefault(q, 0);
      }
      for (int i = 0; i < docIds.length; i++) {
        score[i] += (idf.getOrDefault(q, 0.0) * (qFreq[i] * (k1 + 1) /
                (qFreq[i] + k1 * (1 - b + b * docLengths[i] / avgdl))));
      }
    }
    return score;
  }
}
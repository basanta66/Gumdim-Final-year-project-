package com.aoh.ghumdim.cosineSim.trash;

import com.aoh.ghumdim.places.dto.DestinationResponseDto;

import java.util.*;
import java.util.concurrent.*;

//@Component
public abstract class BM25 {
  protected int corpusSize;
  protected double avgdl;
  protected List<Map<String, Integer>> docFreqs;
  protected Map<String, Double> idf;
  protected List<Integer> docLen;
  protected Tokenizer tokenizer;


  public BM25(List<List<String>> corpus, Tokenizer tokenizer) {
    this.corpusSize = 0;
    this.avgdl = 0;
    this.docFreqs = new ArrayList<>();
    this.idf = new HashMap<>();
    this.docLen = new ArrayList<>();
    this.tokenizer = tokenizer;

    if (tokenizer != null) {
      corpus = tokenizeCorpus(corpus);
    }

    Map<String, Integer> nd = initialize(corpus);
    calculateIdf(nd);
  }

  protected abstract void calculateIdf(Map<String, Integer> nd);
  public abstract double[] getScores(String[] query);
  public abstract double[] getBatchScores(String[] query, int[] docIds);

  protected List<List<String>> tokenizeCorpus(List<List<String>> corpus) {
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    List<List<String>> tokenizedCorpus = new ArrayList<>();
    try {
      List<Callable<List<String>>> callables = new ArrayList<>();
      for (List<String> document : corpus) {
        callables.add(() -> tokenizer.tokenize(document));
      }
      List<Future<List<String>>> futures = executorService.invokeAll(callables);
      for (Future<List<String>> future : futures) {
        tokenizedCorpus.add(future.get());
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
    }
    return tokenizedCorpus;
  }

  protected Map<String, Integer> initialize(List<List<String>> corpus) {
    Map<String, Integer> nd = new HashMap<>();
    int numDocs = 0;
    for (List<String> document : corpus) {
      docLen.add(document.size());
      numDocs += document.size();
      Map<String, Integer> frequencies = new HashMap<>();
      for (String word : document) {
        frequencies.put(word, frequencies.getOrDefault(word, 0) + 1);
      }
      docFreqs.add(frequencies);
      for (String word : frequencies.keySet()) {
        nd.put(word, nd.getOrDefault(word, 0) + 1);
      }
      corpusSize++;
    }
    avgdl = numDocs / (double) corpusSize;
    return nd;
  }

  public List<DestinationResponseDto> getTopN(String[] query, List<DestinationResponseDto> documents, int n) {
    assert corpusSize == documents.size() : "The documents given don't match the index corpus!";
    double[] scores = getScores(query);
    List<Integer> topN = new ArrayList<>();
    for (int i = 0; i < scores.length; i++) {
      topN.add(i);
    }
    Collections.sort(topN, (a, b) -> Double.compare(scores[b], scores[a]));
    List<DestinationResponseDto> topResults = new ArrayList<>();
    for (int i = 0; i < Math.min(n, topN.size()); i++) {
      topResults.add(documents.get(topN.get(i)));
    }
    return topResults;
  }

}
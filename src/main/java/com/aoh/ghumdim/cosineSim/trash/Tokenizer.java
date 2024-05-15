package com.aoh.ghumdim.cosineSim.trash;

import java.util.ArrayList;
import java.util.List;

//@Component
public class Tokenizer {
  public List<String> tokenize(List<String> document) {
    List<String> tokens = new ArrayList<>();
    for (String line : document) {
      // Split the line into words using whitespace as delimiter
      String[] words = line.split("\\s+");
      // Add each word to the list of tokens
      for (String word : words) {
        tokens.add(word);
      }
    }
    return tokens;
  }
}

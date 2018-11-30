package com.example.allymcgilloway.wordcounter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allymcgilloway on 13/12/2017.
 */

public class WordCounterExtended {

    private Map<String, Integer> concordance;
    private String[] words;

    public WordCounterExtended(String text) {
        this.concordance = new HashMap<String, Integer>();
        this.words = text.toLowerCase().split("[,\\s]+");

        for (String word : words) {
            if (concordance.get(word) == null) {
                concordance.put(word, 1);
            } else {
                concordance.put(word, concordance.get(word) + 1);
            }
        }
    }

    public int getCount() {
        return this.words.length;
    }

    public int getOccurrencesCount(String word) {
        return concordance.get(word);
    }

    public String toString() {
        String output = "Total Words: " + words.length + "\n";
        return output + sortedConcordance();
    }

    public String sortedConcordance () {
        String sortedHashMap = "";

        Object[] array = this.concordance.entrySet().toArray();
        Arrays.sort(array, new Comparator() {
            public int compare(Object object1, Object object2) {
                return ((Map.Entry<String, Integer>) object2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) object1).getValue());
            }
        });

        for (Object entry : array) {
            sortedHashMap += (((Map.Entry<String, Integer>) entry).getKey() + " : "
                    + ((Map.Entry<String, Integer>) entry).getValue()) + "\n";
        }
        return sortedHashMap;
    }
}



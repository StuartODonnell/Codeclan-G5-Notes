package com.example.allymcgilloway.wordcounter;

/**
 * Created by allymcgilloway on 13/12/2017.
 */

public class WordCounter {
    public int getCount(String text) {
        String[] wordArray = text.toLowerCase().split(" ");
        return wordArray.length;
    }
}
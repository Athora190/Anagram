/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashMap<String,ArrayList<String>> lettersToWord = new HashMap<String,ArrayList<String>>();
    private Set<String> wordSet = new HashSet<String>();
    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            String Signature = sortLetters(word.toLowerCase());
            //checks if the hash map already has the key value then
            //creates a tmp array list of string then
            // adds the value (word) to the tmp array list
            // then the hash map gets the key (signature) and adds all
            // the tmp array lists words.
            ArrayList<String> tmpArray= new ArrayList<String>();
            tmpArray.add(word);
            if(lettersToWord.containsKey(Signature))
            {
                lettersToWord.get(Signature).addAll(tmpArray);
            }
            else {
                lettersToWord.put(Signature,tmpArray);
            }
            wordSet.add(word);
        }
    }
    //works with the hash set
    public boolean isGoodWord(String word, String base) {

        if(wordSet.contains(word))
        {
            Log.d("jade","returned true");
            return true;
        }
        else
        {
            Log.d("jade","returned false");
            return false;

        }
    }

    public static String sortLetters(String inputString)
    {
        char tmpArray[] = inputString.toCharArray();
        Arrays.sort(tmpArray);
        return new String(tmpArray);

    }
    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String targetwordSignature = sortLetters(targetWord.toLowerCase());

        if(lettersToWord.containsKey(targetwordSignature))
        {
            result = lettersToWord.get(targetwordSignature);
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();


        char [] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(char l:alphabet)
        {
            String newword = word + l;
            result.addAll(getAnagrams(newword));
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int Anagram = 0;
        String s = new String();
        while (Anagram < MIN_NUM_ANAGRAMS) {
            Random r = new Random();
            int tmpsize = wordSet.size();
            int randomindex = r.nextInt(tmpsize);
            Object[] tmp = wordSet.toArray();
            s = (String) tmp[randomindex];
            String targetwordSignature = sortLetters(s.toLowerCase());
            if(lettersToWord.containsKey(targetwordSignature))
            {
                Anagram = lettersToWord.get(targetwordSignature).size();
            }
        }

            return s;

    }
}

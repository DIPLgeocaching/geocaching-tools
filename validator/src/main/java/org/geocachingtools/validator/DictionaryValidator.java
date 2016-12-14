/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 *
 * @author 20120093
 */
public class DictionaryValidator {
    
    private final SortedSet<String> words = new TreeSet<>();

    public DictionaryValidator(URL url) throws IOException {
        InputStream stream = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while((line = reader.readLine()) != null)
            words.add(line);
    }
    
    public double check(String text) {
        return Stream.of(text.split("\\s"))
                .map(String::toLowerCase)
                .mapToDouble(str -> {
                    return words.contains(str) ? 1 : 0;
                })
                .parallel()
                .average()
                .getAsDouble();
    }
}

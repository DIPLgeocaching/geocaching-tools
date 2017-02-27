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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;

/**
 *
 * @author 20120093
 */
public class Validator {

    private static final Validator instance = new Validator();
    private String[] dictionaries={"/english.txt","/german.txt"};

    public static Validator getInstance() {
        return instance;
    }

    private final SortedSet<String> words = new TreeSet<>();

    private Validator() {
        this.init();
    }

    private void init() {
        Arrays.stream(dictionaries).parallel().forEach(this::addDictionary);
    }

    private void addDictionary(String path){
        try (InputStream stream = Validator.class.getResourceAsStream(path);
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, "VALIDATOR INIT EXCEPTION", ex);
        }
    }
    
    public ValidatorResult check(ValidatorRequest request) {
        String plain = request.getPlaintext();
        if(plain.trim().isEmpty()){
            return new ValidatorResult(0);
        }
        return new ValidatorResult(Stream.of(request.getPlaintext().split("\\s"))
                .map(String::toLowerCase)
                .mapToDouble(str -> {
                    return words.contains(str) ? 1 : 0;
                })
                .parallel()
                .average()
                .getAsDouble());
    }
}

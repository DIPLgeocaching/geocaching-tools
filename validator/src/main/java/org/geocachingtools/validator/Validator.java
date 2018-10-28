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
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;

/**
 *
 * @author 20120093
 */
public class Validator {

    private static final Validator instance = new Validator();
    private static Pattern p1;
    private String[] dictionaries = {"/english.txt", "/german.txt"};

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

    private void addDictionary(String path) {
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
        String plain = request.getPlaintext().toLowerCase();
        if (plain.trim().isEmpty()) {//Do not accept empty results
            return new ValidatorResult(0);
        }
        
        //Words Splitted
        double relevance = Stream.of(plain.split("\\W"))
                .mapToDouble(str -> {
                    return words.contains(str) ? 1 : 0;
                })
                .parallel()
                .average()
                .getAsDouble();

        //KORRDINATES
        
        // .*\d{1,2}[°.,d: ]? ?\d{1,2}[.,:′'’] ?\d{2,}[.,"′'’\d]\d*\D*\d{1,3}[°.,d: ]? ?\d{1,2}[.,:′'’] ?\d{2,}[.,"′'’\d].*
        //[NS] ?\d{1,2}[.,]\d{2,}[d°]?[ .,]*[OWE] ?\d{1,3}[.,]\d{2,}
        //\d{1,2}[.,]\d{2,}\D*[d°NS]\D*\d{1,3}[.,]\d{2,}\D*[d°OWE]
        if (plain.matches(".*\\d{1,2}[°.,d: ]? ?\\d{1,2}[.,:′'’] ?\\d{2,}[.,\"′'’´\\d]\\d*\\D*\\d{1,3}[°.,d: ]? ?\\d{1,2}[.,:′'’´] ?\\d{2,}[.,\"′'’´\\d].*")) {
            relevance += ValidatorResult.THRESHOLD + 0.1;
        } else if (plain.matches("[NS] ?\\d{1,2}[.,]\\d{2,}[d°]?[ .,]*[OWE] ?\\d{1,3}[.,]\\d{2,}")                     
                || plain.matches("\\d{1,2}[.,]\\d{2,}\\D*[d°NS]\\D*\\d{1,3}[.,]\\d{2,}\\D*[d°OWE]")) {
            relevance += ValidatorResult.THRESHOLD;
        }
        
        //No words splitted
        for(int i=0;i<plain.length()-3;i++){
            for(int j=i+4;j<=plain.length()&&j-i<16;j++){
                if(words.contains(plain.subSequence(i, j))){
                    relevance+=0.2;
                }
            }
        }
        
        return new ValidatorResult((relevance>1)?1:0);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.management.RuntimeErrorException;
import org.geocachingtools.decoder.*;

/**
 *
 * @author 20120451
 */
@Named(value = "textcon")
@SessionScoped
public class TextController implements Serializable {

    private Class<?> type = String.class;
    private String cipher;
    private String passwordText;//The Textfield for passwords
    private List<String> passwords;
    private List<DecoderMethod> methods = new ArrayList<>();
    private Map<DecoderMethod, Boolean> methodsToUse = new HashMap<>();//the flags to decide which methods are executed
    private Map<DecoderMethod, DecoderResult> results = new HashMap<>();
    private Decoder decoder = Decoder.getInstance();
    @Inject
    private LocaleController localecon;

    {
        decoder.getMethods(type).stream().forEach(o -> {
            methods.add(o);
            //methodsToUse.put(o, Boolean.TRUE);
        });
    }

    public void submit() {
        //TODO Build passwords
        System.out.println(methodsToUse);
        System.out.println(passwordText);
        System.out.println("cipher: "+cipher);
        for (DecoderMethod method : methods) {
            if (methodsToUse.get(method)) {
                System.out.println(method.getName());
                Future<DecoderResult> future;
                future = decoder.decode(
                        new DecoderRequest(
                                type,
                                cipher,
                                method,
                                passwords,
                                localecon.getLocale()
                        )
                );
                try {
                    results.put(method, future.get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(TextController.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException(ex);//TODO better exception handling
                }
            }
        }
    }

    /**
     * Creates a new instance of TextController
     */
    public TextController() {
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
    }

    public List<DecoderMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<DecoderMethod> methods) {
        this.methods = methods;
    }

    public Map<DecoderMethod, Boolean> getMethodsToUse() {
        return methodsToUse;
    }

    public void setMethodsToUse(Map<DecoderMethod, Boolean> methodsToUse) {
        this.methodsToUse = methodsToUse;
    }

    public Map<DecoderMethod, DecoderResult> getResults() {
        return results;
    }

    public void setResults(Map<DecoderMethod, DecoderResult> results) {
        this.results = results;
    }

}

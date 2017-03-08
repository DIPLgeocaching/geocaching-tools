package org.geocachingtools.geoui.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.geocachingtools.decoder.*;

import org.primefaces.model.UploadedFile;

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
    private List<DecoderMethod> methods = new ArrayList<>();//Available Methods
    private List<DecoderMethod> methodsToUse;//Selected Methods
    private Map<DecoderMethod, DecoderResult> results = new HashMap<>();
    private Decoder decoder = Decoder.getInstance();

    private UploadedFile passwordFile;

    @Inject
    private LocaleController localecon;

    @PostConstruct
    public void init(){
        decoder.getMethods(type).stream().forEach(o -> {
            methods.add(o);
        });
    }

    private void upload() {

        if (passwordFile.getSize() > 0) {
            FacesMessage message = new FacesMessage("Succesfull", passwordFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            try (InputStream is = passwordFile.getInputstream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String line = "";
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        passwords.add(line);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(TextController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesMessage message = new FacesMessage("Kein File gefunden...");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void submit() {
        results.clear();
        passwords = new ArrayList<>();
        if (!passwordText.trim().isEmpty()) {
            passwords.addAll(Arrays.asList(passwordText.split(",")));
        }
        upload();//Adds passwords from file to passwordlist

        System.out.println(methodsToUse);
        System.out.println(passwordText);
        passwords.stream().forEach(System.out::println);
        System.out.println("cipher: " + cipher);

        for (DecoderMethod method : methodsToUse) {
            // System.out.println(method.getName());
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
        if (methodsToUse != null) {
            methodsToUse.clear();
        }
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

    public List<DecoderMethod> getMethodsToUse() {
        return methodsToUse;
    }

    public void setMethodsToUse(List<DecoderMethod> methodsToUse) {
        this.methodsToUse = methodsToUse;
    }

    public Map<DecoderMethod, DecoderResult> getResults() {
        return results;
    }

    public void setResults(Map<DecoderMethod, DecoderResult> results) {
        this.results = results;
    }

    public UploadedFile getPasswordFile() {
        return passwordFile;
    }

    public void setPasswordFile(UploadedFile passwordFile) {
        this.passwordFile = passwordFile;
    }

}

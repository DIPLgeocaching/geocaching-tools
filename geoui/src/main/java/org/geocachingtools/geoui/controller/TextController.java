/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;
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
    private List<DecoderMethod> methods = new ArrayList<>();
    private Map<DecoderMethod, Boolean> methodsToUse = new HashMap<>();//the flags to decide which methods are executed
    private Map<DecoderMethod, DecoderResult> results = new HashMap<>();
    private Decoder decoder = Decoder.getInstance();
//    private UploadedFile passwordFile;
    private Part file;

    @Inject
    private LocaleController localecon;

    {
        decoder.getMethods(type).stream().forEach(o -> {
            methods.add(o);
        });
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public String upload() throws IOException {
        InputStream inputStream = file.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(getFilename(file));

        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while (true) {
            bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            } else {
                break;
            }
        }
        outputStream.close();
        inputStream.close();

        return "success";
    }

    public void submit() {

        passwords = Arrays.asList(passwordText.split(","));
        try {
            upload();
//        if (passwordFile != null) {
//            BufferedReader br = null;
//            try {
//                FacesMessage message = new FacesMessage("Succesfull", passwordFile.getFileName() + " is uploaded.");
//                FacesContext.getCurrentInstance().addMessage(null, message);
//
//                br = new BufferedReader(new InputStreamReader(passwordFile.getInputstream()));
//                String temp;
//
//                while ((temp = br.readLine()) != null) {
//                    passwords.add(temp);
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(TextController.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                try {
//                    br.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(TextController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        } else {
//            FacesMessage message = new FacesMessage("Kein File gefunden...");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//        }
        } catch (IOException ex) {
            Logger.getLogger(TextController.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(methodsToUse);
        System.out.println(passwordText);
        passwords.stream().forEach(System.out::println);
        System.out.println("cipher: " + cipher);

        for (DecoderMethod method : methods) {
            if (methodsToUse.get(method)) {
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    
}

/**
 * The MIT License (MIT)
 *
 * Copyright (c) [2016] [Geocaching-Tools: Stefan Kurzbauer, Jakob Geringer,
 * Thomas Rapberger, Lukas Wallenboeck, Simon Lehner-Dittenberger]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.geocachingtools.geoui.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.geocachingtools.decoder.Decoder;
import org.geocachingtools.decoder.DecoderMethod;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author 20120451
 */
@Named(value = "picCon")
@SessionScoped
public class PictureController implements Serializable {

    private final Class<?> type = InputStream.class;
    private UploadedFile passwordFile;
    private String passwordText;//The Textfield for passwords
    private List<String> passwords;
    private List<DecoderMethod> methods = new ArrayList<>();//Available Methods
    private List<DecoderMethod> methodsToUse;//Selected Methods
    private Map<DecoderMethod, DecoderResult> results = new HashMap<>();
    private final Decoder decoder = Decoder.getInstance();
    private UploadedFile uploadedPic;

    @Inject
    private LocaleController localecon;

    @PostConstruct
    public void init() {
        decoder.getMethods(type).stream().forEach(o -> {
            methods.add(o);
        });
    }

    public void handlePictureUpload(FileUploadEvent event) {
        UploadedFile picture = event.getFile();
        System.out.println("----");
        System.out.println(picture.getFileName());
    }

    public void upload() {
        FacesMessage message = new FacesMessage("Succesfull", passwordFile.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        try (InputStream is = passwordFile.getInputstream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line = "";
            while ((line = br.readLine()) != null) {
                passwords.add(line);
            }

        } catch (IOException ex) {
            Logger.getLogger(TextController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void submit() throws IOException {
        results.clear();
        passwords = new ArrayList<>(Arrays.asList(passwordText.split(",")));
        
        System.out.println(methodsToUse);
        System.out.println(passwordText);
        System.out.println(passwords);
        System.out.println("cipher: " + uploadedPic.getFileName());

        if (uploadedPic != null) {
            for (DecoderMethod method : methodsToUse) {
                // System.out.println(method.getName());
                Future<DecoderResult> future;
                future = decoder.decode(
                        new DecoderRequest(
                                type,
                                uploadedPic.getInputstream(),
                                method,
                                passwords,
                                localecon.getLocale()
                        )
                );
                try {
                    results.put(method, future.get());
                } catch (InterruptedException | ExecutionException ex) {
                    throw new RuntimeException(ex);//TODO better exception handling
                }
            }
        } else {
            FacesMessage message = new FacesMessage("Kein File gefunden...");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        if (methodsToUse != null) {
            methodsToUse.clear();
        }
    }

    public void uploadPicture(FileUploadEvent event) throws IOException {
        System.out.println("Success");

        FacesMessage message = new FacesMessage(event.getFile().getFileName());
        FacesContext.getCurrentInstance().addMessage(null, message);

        uploadedPic = event.getFile();
    }

    public UploadedFile getPasswordFile() {
        return passwordFile;
    }

    public void setPasswordFile(UploadedFile passwordFile) {
        this.passwordFile = passwordFile;
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

    public UploadedFile getUploadedPic() {
        return uploadedPic;
    }

    public void setUploadedPic(UploadedFile uploadedPic) {
        this.uploadedPic = uploadedPic;
    }

}

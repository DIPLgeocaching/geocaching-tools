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
import java.net.MalformedURLException;
import java.net.URL;
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
import javax.faces.component.UIComponent;
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
    private UploadedFile uploadedPic = null;
    private UIComponent pwd;
    private UIComponent pic;
    private String url;
    private InputStream urlImage;

    @Inject
    private LocaleController localeCon;

    @PostConstruct
    public void init() {
        decoder.getMethods(type).stream().forEach(o -> {
            methods.add(o);
        });
    }

    public InputStream getPictureByURL() {
        try {
            return new URL(url).openStream();

        } catch (IOException ex) {
            Logger.getLogger(PictureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void handlePictureUpload(FileUploadEvent event) {
        UploadedFile picture = event.getFile();
        System.out.println("----");
        System.out.println(picture.getFileName());
    }

    public void upload() {
        if (passwordFile != null) {
            FacesMessage message = new FacesMessage(String.format(localeCon.getI18n("fileUpload"),passwordFile.getFileName()));
            FacesContext.getCurrentInstance().addMessage(null, message);

            try (InputStream is = passwordFile.getInputstream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        passwords.add(line);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(TextController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void submit() throws IOException {
        if (methodsToUse.isEmpty()) {
            FacesMessage message = new FacesMessage(localeCon.getI18n("methodRequired"));
            FacesContext.getCurrentInstance().addMessage(pwd.getClientId(FacesContext.getCurrentInstance()), message);
        } else {
            results.clear();
            passwords = new ArrayList<>();
            if (!passwordText.trim().isEmpty()) {
                passwords.addAll(Arrays.asList(passwordText.split(",")));
            }
            upload();//Adds passwords from file to passwordlist

            System.out.println(methodsToUse);
            System.out.println(passwordText);
            System.out.println(passwords);

            InputStream inputStream = null;
            inputStream = getPictureByURL();

            if (uploadedPic != null) {
                inputStream = uploadedPic.getInputstream();
            }

            if (inputStream != null) {

                // System.out.println("cipher: " + inputStream.toString());
                for (DecoderMethod method : methodsToUse) {
                    // System.out.println(method.getName());
                    if (passwords.isEmpty() && method.getRequiresPassword()) {
                        results.put(method, new DecoderResult(method, localeCon.getI18n("pwdRequired"), 1.0));
                        FacesMessage message = new FacesMessage(localeCon.getI18n("pwdRequired"));
                        FacesContext.getCurrentInstance().addMessage(pwd.getClientId(FacesContext.getCurrentInstance()), message);
                    } else {
                        Future<DecoderResult> future;
                        future = decoder.decode(
                                new DecoderRequest(
                                        type,
                                        inputStream,
                                        method,
                                        passwords,
                                        localeCon.getLocale()
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

                if (methodsToUse != null) {
                    methodsToUse.clear();
                }
            } else {
                FacesMessage message = new FacesMessage(localeCon.getI18n("pictureRequired"));
                FacesContext.getCurrentInstance().addMessage(pwd.getClientId(FacesContext.getCurrentInstance()), message);
            }
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

    public UIComponent getPwd() {
        return pwd;
    }

    public void setPwd(UIComponent pwd) {
        this.pwd = pwd;
    }

    public UIComponent getPic() {
        return pic;
    }

    public void setPic(UIComponent pic) {
        this.pic = pic;
    }

    public void clearPic() {
        uploadedPic = null;
        System.out.println("remove");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPicEmpty() {
        return uploadedPic == null;
    }
}

package org.geocachingtools.geoui.controller;

import java.io.BufferedReader;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.geocachingtools.decoder.*;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author 20120451
 */
@Named(value = "textCon")
@SessionScoped
public class TextController implements Serializable {

    private final Class<?> type = String.class;
    private String cipher;
    private String passwordText;//The Textfield for passwords
    private List<String> passwords;
    private List<DecoderMethod> methods = new ArrayList<>();//Available Methods
    private List<DecoderMethod> methodsToUse;//Selected Methods
    private Map<DecoderMethod, DecoderResult> results = new HashMap<>();
    private final Decoder decoder = Decoder.getInstance();
    private UploadedFile passwordFile;
    private UIComponent pwd;
    private boolean pwdRequired = false;

    @Inject
    private LocaleController localecon;

    @PostConstruct
    public void init() {
        decoder.getMethods(type).stream().forEach(o -> {
            methods.add(o);
        });
    }

    private void upload() {
        if (passwordFile != null) {
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

    public void submit() {
        if (methodsToUse.isEmpty()) {
            FacesMessage message = new FacesMessage("Es muss ein Verfahren ausgwählt werden!");
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
            passwords.stream().forEach(System.out::println);

            if (!cipher.isEmpty()) {
                System.out.println("cipher: " + cipher);
                for (DecoderMethod method : methodsToUse) {
                    // System.out.println(method.getName());
                    if (passwords.isEmpty() && method.getRequiresPassword()) {
                        results.put(method, new DecoderResult(method, "Das ausgewaehlte Verfahren verlangt ein Passwort!", 1.0));
                        pwdRequired = true;
                    } else {
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
                if (methodsToUse != null) {
                    methodsToUse.clear();
                }
            } else {
                FacesMessage message = new FacesMessage("Es muss ein Ciphertext eingegeben werden!");
                FacesContext.getCurrentInstance().addMessage(pwd.getClientId(FacesContext.getCurrentInstance()), message);
            }
            if (pwdRequired) {
                FacesMessage message = new FacesMessage("Eines der ausgewaehlten Verfahren verlangen ein Passwort!");
                FacesContext.getCurrentInstance().addMessage(pwd.getClientId(FacesContext.getCurrentInstance()), message);
                pwdRequired = false;
            }
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

    public UIComponent getPwd() {
        return pwd;
    }

    public void setPwd(UIComponent pwd) {
        this.pwd = pwd;
    }
}

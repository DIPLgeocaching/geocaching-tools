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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author 20120451
 */
@Named(value = "picCon")
@SessionScoped
public class PictureController implements Serializable {

    private UploadedFile passwordFile;

    @PostConstruct
    public void init() {

    }

    public void handlePictureUpload(FileUploadEvent event) {
        UploadedFile picture = event.getFile();
        System.out.println("----");
        System.out.println(picture.getFileName());
    }

    public void upload() {

        if (passwordFile.getSize() > 0) {
            FacesMessage message = new FacesMessage("Succesfull", passwordFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            try (InputStream is = passwordFile.getInputstream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String line = "";
                while ((line = br.readLine()) != null) {
                   //Add Passwords to list here
                }

            } catch (IOException ex) {
                Logger.getLogger(TextController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesMessage message = new FacesMessage("Kein File gefunden...");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void justDoIt() {
        System.out.println("Swag");
    }

    public UploadedFile getPasswordFile() {
        return passwordFile;
    }

    public void setPasswordFile(UploadedFile passwordFile) {
        this.passwordFile = passwordFile;
    }

    
}

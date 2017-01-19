/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.decoder;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Simon
 * @param <T>
 */
public class DecoderRequest<T> {
    private Class<T> dataType;
    private T data;
    private DecoderMethod method;
    private List<String> passwords;
    private Locale locale;

    public DecoderRequest() {
    }

    public DecoderRequest(Class<T> dataType, T data, DecoderMethod method, List<String> passwords) {
        this.dataType = dataType;
        this.data = data;
        this.method = method;
        this.passwords = passwords;
        this.locale=Locale.getDefault();
    }

    public DecoderRequest(Class<T> dataType, T data, DecoderMethod method, List<String> passwords, Locale locale) {
        this.dataType = dataType;
        this.data = data;
        this.method = method;
        this.passwords = passwords;
        this.locale = locale;
    }
    
    public T getData() {
        return data;
    }

    public Class<T> getDataType() {
        return dataType;
    }

    public DecoderMethod getMethod() {
        return method;
    }
    
    public Collection<String> getPasswords() {
        return passwords;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setDataType(Class<T> dataType) {
        this.dataType = dataType;
    }

    public void setMethod(DecoderMethod method) {
        this.method = method;
    }

    public void setPasswords(List<String> passwords) {
        this.passwords = passwords;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
}

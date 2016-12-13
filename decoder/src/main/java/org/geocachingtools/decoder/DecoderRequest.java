/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.decoder;

import java.util.Collection;
import java.util.List;


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

    public DecoderRequest() {
    }

    public DecoderRequest(Class<T> dataType, T data, DecoderMethod method, List<String> passwords) {
        this.dataType = dataType;
        this.data = data;
        this.method = method;
        this.passwords = passwords;
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
}

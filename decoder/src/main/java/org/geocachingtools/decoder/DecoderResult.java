/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.decoder;

/**
 *
 * @author Simon
 */
public class DecoderResult {
    private DecoderMethod method;
    private String result;
    private Double relevance;

    public DecoderResult(DecoderMethod method, String result, Double relevance) {
        this.method = method;
        this.result = result;
        this.relevance = relevance;
    }

    public DecoderMethod getMethod() {
        return method;
    }

    public Double getRelevance() {
        return relevance;
    }

    public String getResult() {
        return result;
    }
    
    
    
}

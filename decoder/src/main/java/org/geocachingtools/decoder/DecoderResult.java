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
public class DecoderResult implements Comparable<DecoderResult> {
    private final DecoderMethod method;
    private final String result;
    private final Double relevance;

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

    @Override
    public int compareTo(DecoderResult o) {
        return relevance.compareTo(o.relevance);
    }
    
    
    
}

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
    private final String fullResult, briefResult;
    private final Double relevance;

    public DecoderResult(DecoderMethod method, String fullResult, Double relevance) {
        this(method,fullResult,null,relevance);
    }

    public DecoderResult(DecoderMethod method, String fullResult, String briefResult, Double relevance) {
        this.method = method;
        this.fullResult = fullResult;
        this.briefResult = briefResult;
        this.relevance = relevance;
    }
    

    public DecoderMethod getMethod() {
        return method;
    }

    public Double getRelevance() {
        return relevance;
    }

    public String getBriefResult() {
        return briefResult;
    }
    
    public String getFullResult() {
        return fullResult;
    }

    @Override
    public int compareTo(DecoderResult o) {
        return relevance.compareTo(o.relevance);
    }
    
    
    
}

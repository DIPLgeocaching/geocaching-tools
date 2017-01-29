/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.validator;

/**
 *
 * @author Simon
 */
public class ValidatorResult {
    public static final double THRESHOLD = 0.5;
    private double relevance;

    public ValidatorResult() {
    }

    public ValidatorResult(double relevance) {
        this.relevance = relevance;
    }

    public double getRelevance() {
        return relevance;
    }

    public void setRelevance(double relevance) {
        this.relevance = relevance;
    }
}

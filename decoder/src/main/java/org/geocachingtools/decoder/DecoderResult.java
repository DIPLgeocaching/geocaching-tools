/*
 * The MIT License (MIT)
 * 
 * Copyright (c) [2016] [Geocaching-Tools: Stefan Kurzbauer, Jakob Geringer,
 * Thomas Rapberger, Lukas Wallenböck, Simon Lehner-Dittenberger]
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
package org.geocachingtools.decoder;

/**
 * Hugo
 *
 * @author Simon
 */
public class DecoderResult implements Comparable<DecoderResult> {

    private final DecoderMethod method;
    private final String fullResult, briefResult;
    private final Double relevance;

    public DecoderResult(DecoderMethod method, String fullResult, Double relevance) {
        this(method, fullResult, null, relevance);
    }

    public DecoderResult(DecoderMethod method, String briefResult, String fullResult, Double relevance) {
        this.method = method;
        this.briefResult = briefResult;
        this.fullResult = fullResult;
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

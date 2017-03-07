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
package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author Kurzbauer
 */
@Method(name="Brainfuck",requiresPassword = false,type=String.class)
public class Brainfuck extends DecoderMethod<String> {
    private static final int LENGTH = 65535;
    private Validator validator = Validator.getInstance();
    private I18n i18n;
    
    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        i18n = new I18n(request.getLocale());
        i18n = new I18n(request.getLocale());
        String briefResult="",fullResult="";
        double relevance=0;
        
        try{
            fullResult = interpret(request.getData());
            relevance = validator.check(new ValidatorRequest(fullResult)).getRelevance();
            if(relevance >= ValidatorResult.THRESHOLD){
                briefResult=fullResult;
            }else{
                briefResult=i18n.get("LOW-VALIDATOR-RESULT");
            }
            return new DecoderResult(this, briefResult,fullResult, relevance);
        }catch(UnsupportedOperationException e){
            return new DecoderResult(this, i18n.get("BRAINFUCK-ERROR"),"", 0.0);
        }        
    }
    
    /**
     * @see https://gist.github.com/unnikked/cfad836abd9e4619a1b1
     * @param code 
     */
    public String interpret(String code) {
        StringBuilder output = new StringBuilder();
        byte[] mem = new byte[LENGTH];
        int dataPointer=0;
        
        int l = 0;
        for(int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == '>') {
                dataPointer = (dataPointer == LENGTH-1) ? 0 : dataPointer + 1;
            } else if(code.charAt(i) == '<') {
                dataPointer = (dataPointer == 0) ? LENGTH-1 : dataPointer - 1;
            } else if(code.charAt(i) == '+') {
                mem[dataPointer]++;
            } else if(code.charAt(i) == '-') {
                mem[dataPointer]--;
            } else if(code.charAt(i) == '.') {
                output.append((char) mem[dataPointer]);
            } else if(code.charAt(i) == ',') {
                throw new UnsupportedOperationException("No input for brainfuck supported");
            } else if(code.charAt(i) == '[') {
                if(mem[dataPointer] == 0) {
                    i++;
                    while(l > 0 || code.charAt(i) != ']') {
                        if(code.charAt(i) == '[') l++;
                        if(code.charAt(i) == ']') l--;
                        i++;
                    }
                }
            } else if(code.charAt(i) == ']') {
                if(mem[dataPointer] != 0) {
                    i--;
                    while(l > 0 || code.charAt(i) != '[') {
                        if(code.charAt(i) == ']') l++;
                        if(code.charAt(i) == '[') l--;
                        i--;
                    }
                    i--;
                }
            }
        }
        return output.toString();
    }
}

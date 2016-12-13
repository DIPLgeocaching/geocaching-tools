/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.DecoderMethod;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.geocachingtools.decoder.Method;

/**
 *
 * @author Simon
 */
@Method(name = "Test Method", requiresPassword = false, type=String.class)
public class DecoderMethodTest extends DecoderMethod<String>{

    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        return new DecoderResult(
                this,
                "Das ist ein Test: " + request.getData().replace("<", "").replace("\"", "").replace("'", ""),
                1.0
        );
    }
    
}

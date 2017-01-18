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
@Method(name="Template",requiresPassword = true,type=String.class)
public class Template extends DecoderMethod<String> {

    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        return new DecoderResult(this, "Short Result","Long Result, Passwörter: " + request.getPasswords().toString(), 0.0);
    }
    
}

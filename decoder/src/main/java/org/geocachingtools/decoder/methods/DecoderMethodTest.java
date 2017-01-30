package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;

/**
 *
 * @author Simon
 */
@Method(name = "Test Method", requiresPassword = false, type=String.class)
public class DecoderMethodTest extends DecoderMethod<String>{
    I18n i18n;
    
    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        i18n = new I18n(request.getLocale());
        
        return new DecoderResult(
                this,
                i18n.get("TEST"),
                "Das ist ein Testilein: " + request.getData().replace("<", "").replace("\"", "").replace("'", ""),
                1.0
        );
    }
    
}

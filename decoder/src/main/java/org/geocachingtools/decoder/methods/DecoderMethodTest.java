package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.Validator;
import org.geocachingtools.validator.ValidatorRequest;

/**
 *
 * @author Simon
 */
@Method(name = "Test Validator", requiresPassword = false, type=String.class)
public class DecoderMethodTest extends DecoderMethod<String>{
    I18n i18n;
    
    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        i18n = new I18n(request.getLocale());
        double rating = Validator.getInstance().check(new ValidatorRequest(request.getData())).getRelevance();
        String result = String.format("%.2f "+request.getData(), rating);
        return new DecoderResult(
                this,
                result,
                "Full:" +result,
                rating
        );
    }
    
}

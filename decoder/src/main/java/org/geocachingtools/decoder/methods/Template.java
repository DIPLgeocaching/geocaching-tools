package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.Validator;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name="Template",
        type=String.class,
        requiresPassword = false,
        expectedExecutionTime = DecoderMethod.ExecutionTime.FAST
        )
public class Template extends DecoderMethod<String> {
    private Validator validator = Validator.getInstance();
    private I18n i18n;
    
    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        i18n = new I18n(request.getLocale());
        String briefResult="",fullResult="";
        double relevance=0;
        
        briefResult=i18n.get("TEST");
        
        return new DecoderResult(this, briefResult,fullResult, relevance);
    }
    
}

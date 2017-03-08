package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "Template",
        type = String.class,
        requiresPassword = false
)
public class Template extends DecoderMethod<String> {

    private Validator validator = Validator.getInstance();
    private I18n i18n;

    /**
     * The Method that is called by the Decoder. Multi-threading etc. has to be
     * managed by upper layers of the software.
     *
     * @param request Object
     * @return result Object
     */
    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        i18n = new I18n(request.getLocale());
        String briefResult, fullResult;
        double relevance;

        fullResult = decode(request.getData());
        relevance = validator.check(new ValidatorRequest(fullResult)).getRelevance();

        if (relevance >= ValidatorResult.THRESHOLD) {
            briefResult = fullResult;
        } else {
            briefResult = i18n.get("LOW-VALIDATOR-RESULT");
        }
        return new DecoderResult(this, briefResult, fullResult, relevance);
    }

    /**
     * The actual decoding
     *
     * @param ciphertext
     * @return plaintext
     */
    private String decode(String cipher){
        //DECODER LOGIC HERE
        return cipher;
    }
}

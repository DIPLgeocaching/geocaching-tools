package org.geocachingtools.decoder.methods;
import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;
import java.lang.Math;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "Caesar",
        type = String.class,
        requiresPassword = false
)
public class Caesar extends DecoderMethod<String> {

    private final Validator validator = Validator.getInstance();
    private I18n i18n;
    
    /**
     * The Method that is called by the Decoder. 
     * Multi-threading etc. has to be managed by upper layers of the software.
     * @param request Object
     * @return result Object
     */
    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        i18n = new I18n(request.getLocale());
        String briefResult="", fullResult="";
        double relevance=0;

        String result;
        double partialRelevance;
        for (int key=1; key<=25; key++) {

                result = decode(request.getData(), key);
                partialRelevance = validator.check(new ValidatorRequest(result)).getRelevance();
                result = String.format("<b>ROT-%02d</b> => %s <br/>", key, result);
                if (partialRelevance >= ValidatorResult.THRESHOLD) {
                    briefResult += result;
                    relevance = Math.max(relevance, partialRelevance);
                }
                fullResult += result;
            
        }

        if (relevance < ValidatorResult.THRESHOLD) {//No suitable result found
            briefResult = i18n.get("LOW-VALIDATOR-RESULT");
        }
        return new DecoderResult(this, briefResult, fullResult, relevance);
    }

    /**
     * The actual decoding     *
     * @param ciphertext
     * @param key
     * @return plaintext
     */
    private String decode(String cipher, int key) {
        //IMPLEMENT DECODER FUNKTIONALITY HERE
        return cipher+" - "+key;
    }
}
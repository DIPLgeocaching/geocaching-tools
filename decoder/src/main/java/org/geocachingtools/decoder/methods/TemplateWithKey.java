package org.geocachingtools.decoder.methods;
import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;
import java.lang.Math;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "Template with Key",
        type = String.class,
        requiresPassword = true
)
public class TemplateWithKey extends DecoderMethod<String> {

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
        for (String key : request.getPasswords()) {
            key = key.trim();
            if (!key.isEmpty()) {
                result = decode(request.getData(), key);
                partialRelevance = validator.check(new ValidatorRequest(result)).getRelevance();
                if (partialRelevance >= ValidatorResult.THRESHOLD) {
                    briefResult += "<b>" + key + "</b> => " + result + " <br/>";
                    relevance = Math.max(relevance, partialRelevance);
                }
                fullResult += "<b>" + key + "</b> => " + result + "<br/>";
            }
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
    private String decode(String cipher, String key) {
        //IMPLEMENT DECODER FUNKTIONALITY HERE
        return cipher+" - "+key;
    }
}
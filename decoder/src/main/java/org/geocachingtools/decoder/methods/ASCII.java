package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "ASCII",
        type = String.class,
        requiresPassword = false,
        expectedExecutionTime = DecoderMethod.ExecutionTime.FAST
)
public class ASCII extends DecoderMethod<String> {

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
        String briefResult = "", fullResult = "";
        double relevance = 0;

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
    private String decode(String cipher) {
        String[] words = cipher.split("[^0-9]");//Split on all characters that are not numeric
        StringBuilder result = new StringBuilder();

        //if words contains only strings of length 2 or less, characters were already seperated
        boolean splitWords = false;
        for (String s : words) {
            if (s.length() > 2) {
                splitWords = true;
                break;
            }
        }

        for (String ch : words) {
            try{
                result.append((char) Integer.parseInt(ch));
            }catch(NumberFormatException e ){
                //NOTHING TO DO
            }
        }

        return result.toString();
    }

}

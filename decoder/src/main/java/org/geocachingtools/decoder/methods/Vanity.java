package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "Vanity",
        type = String.class,
        requiresPassword = false,
        expectedExecutionTime = DecoderMethod.ExecutionTime.FAST
)
public class Vanity extends DecoderMethod<String> {

    private Validator validator = Validator.getInstance();
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
     * @param ciphertext
     * @return plaintext
     */
    private String decode(String cipher){
        String result = cipher;
        result = result.replaceAll("9999", "z");
        result = result.replaceAll("999", "y");
        result = result.replaceAll("99", "x");
        result = result.replaceAll("9", "w");
        result = result.replaceAll("888", "v");
        result = result.replaceAll("88", "u");
        result = result.replaceAll("8", "t");
        result = result.replaceAll("7777", "s");
        result = result.replaceAll("777", "r");
        result = result.replaceAll("77", "q");
        result = result.replaceAll("7", "p");
        result = result.replaceAll("666", "o");
        result = result.replaceAll("66", "n");
        result = result.replaceAll("6", "m");
        result = result.replaceAll("555", "l");
        result = result.replaceAll("55", "k");
        result = result.replaceAll("5", "j");
        result = result.replaceAll("444", "i");
        result = result.replaceAll("44", "h");
        result = result.replaceAll("4", "g");
        result = result.replaceAll("333", "f");
        result = result.replaceAll("33", "e");
        result = result.replaceAll("3", "d");
        result = result.replaceAll("222", "c");
        result = result.replaceAll("22", "b");
        result = result.replaceAll("2", "a");
        
        result = result.replaceAll(" ", "");
        result = result.replaceAll("0", " ");//0 or 1 often used as space
        result = result.replaceAll("1", " ");
        return result;
    }

}

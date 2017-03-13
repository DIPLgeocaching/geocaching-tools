package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "ADFGX",
        type = String.class,
        requiresPassword = true
)
public class ADFGX extends DecoderMethod<String> {

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
        String briefResult="", fullResult="";
        double relevance=0;

        String result;
        double partialRelevance;
        for (String sKey : request.getPasswords()) {
            for(String tKey :request.getPasswords()) {
                result = decode(request.getData(), sKey, tKey, false);
                partialRelevance = validator.check(new ValidatorRequest(result)).getRelevance();
                result = "<b>" + sKey +", "+tKey+" A-Z</b> => " + result + " <br/>";
                if (partialRelevance >= ValidatorResult.THRESHOLD) {
                    briefResult+= result;
                    relevance = Math.max(relevance, partialRelevance);
                }
                fullResult += result;
                //same with reversed Alphabet
                result = decode(request.getData(), sKey, tKey, true);
                partialRelevance = validator.check(new ValidatorRequest(result)).getRelevance();
                result = "<b>" + sKey +", "+tKey+", Z-A</b> => " + result + " <br/>";
                if (partialRelevance >= ValidatorResult.THRESHOLD) {
                    briefResult+= result;
                    relevance = Math.max(relevance, partialRelevance);
                }
                fullResult += result;
            }
        }

        if (relevance < ValidatorResult.THRESHOLD) {//No suitable result found
            briefResult = i18n.get("LOW-VALIDATOR-RESULT");
        }
        return new DecoderResult(this, briefResult, fullResult, relevance);
    }

    /**
     * The actual decoding
     * @see www.geocachingtoolbox.com/index.php?page=adfgvxCipher
     * @param ciphertext
     * @param sKey Substitution Key
     * @param tKey Transposition Key
     * @return plaintext
     */
    private String decode(String cipher, String sKey, String tKey, boolean reverseAlphabet){
        //DECODER LOGIC HERE
        return cipher;
    }
}

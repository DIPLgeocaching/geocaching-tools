package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;
import java.lang.Math;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "Vigenere on ROT0 basis",
        type = String.class,
        requiresPassword = true,
        expectedExecutionTime = DecoderMethod.ExecutionTime.FAST
)
public class Vigenere extends DecoderMethod<String> {

    private Validator validator = Validator.getInstance();
    private I18n i18n;
    private char shiftTable[][] = new char[26][26];

    static {

    }

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

        String result;
        double partialRelevance;
        for (String key : request.getPasswords()) {
            result = decode(request.getData(), key.trim());
            partialRelevance = validator.check(new ValidatorRequest(result)).getRelevance();
            if (partialRelevance >= ValidatorResult.THRESHOLD) {
                briefResult += result + " <br/>";
                relevance = Math.max(relevance, partialRelevance);
            }
            fullResult += key + " => " + result + "<br/>";
        }

        if (relevance < ValidatorResult.THRESHOLD) {//No suitable result found
            briefResult = i18n.get("LOW-VALIDATOR-RESULT");
        }
        return new DecoderResult(this, briefResult, fullResult, relevance);
    }

    /**
     * The actual decoding
     *
     * @param ciphertext
     * @param key
     * @return plaintext
     */
    private String decode(String cipher, String key) {//TODO refactor
        String res = "";
        cipher = cipher.toUpperCase();
        key = key.toUpperCase();
        for (int i = 0, j = 0; i < cipher.length(); i++) {
            char c = cipher.charAt(i);
            if (c < 'A' || c > 'Z') {
                res += c;
            } else {
                res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
                j = ++j % key.length();
            }
        }
        return res;
    }

}

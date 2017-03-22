package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "ASCII Base2",
        type = String.class,
        requiresPassword = false
)
public class ASCIIbase2 extends DecoderMethod<String> {

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
    public String decode(String cipher) {

        String[] array = cipher.split("[^01]");
        String result = "";

        for (String s : array) {

            if (s.length() <= 8) {
                result += (char) Integer.parseInt(s, 2);

            } else {
                String[] chars = cipher.split("(?<=\\G.{8})");

                for (String ss : chars) {
                    result += (char) Integer.parseInt(ss, 2);
                }
            }

        }

        return result;
    }
}

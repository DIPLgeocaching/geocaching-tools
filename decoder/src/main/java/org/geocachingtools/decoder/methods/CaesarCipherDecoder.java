/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.decoder.methods;

import org.geocachingtools.decoder.DecoderMethod;
import static org.geocachingtools.decoder.DecoderMethod.ExecutionTime.FAST;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.geocachingtools.decoder.Method;
import org.geocachingtools.validator.Validator;
import org.geocachingtools.validator.ValidatorRequest;
import org.geocachingtools.validator.ValidatorResult;

/**
 *
 * @author Simon
 */
@Method(
        name = "Caesar Cipher",
        requiresPassword = false,
        expectedExecutionTime = FAST,
        type = String.class
)
public class CaesarCipherDecoder extends DecoderMethod<String> {


    public char shift(char ch, int n) {
        if (Character.isAlphabetic(ch)) {
            if (Character.isUpperCase(ch)) {
                return (char) ((((ch - 'A') + n) % 26) + 'A');
            }
            if (Character.isLowerCase(ch)) {
                return (char) ((((ch - 'a') + n) % 26) + 'a');
            }
        }
        return ch;
    }

    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        Validator validator = Validator.getInstance();
        String data = request.getData();
        StringBuilder result = new StringBuilder();
        StringBuilder brief = new StringBuilder();
        double relevance = 0;
        for (int n = 1; n < 26; n++) {
            StringBuilder tmp = new StringBuilder();
            for (char c : data.toCharArray()) {
                tmp.append(shift(c, n));
            }
            ValidatorResult rel = validator.check(new ValidatorRequest(tmp.toString()));
            String str = String.format("rot-%02d: \"%s\" %f<br>\n", n, tmp.toString(), rel.getRelevance());
            result.append(str);
            if (rel.getRelevance() > ValidatorResult.THRESHOLD) {
                brief.append(str);
            }
            relevance += rel.getRelevance();
        }
        return new DecoderResult(this, result.toString(),brief.toString(), relevance/25);
    }

}

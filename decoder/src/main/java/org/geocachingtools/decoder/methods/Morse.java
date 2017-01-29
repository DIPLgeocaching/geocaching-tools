package org.geocachingtools.decoder.methods;

import java.util.*;
import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "Morse",
        type = String.class,
        requiresPassword = false,
        expectedExecutionTime = DecoderMethod.ExecutionTime.FAST
)
public class Morse extends DecoderMethod<String> {

    private Validator validator = Validator.getInstance();
    private I18n i18n;

    private static Map<String, Character> code = new HashMap<>();
    private static String[] morsecodes = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
        "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
        "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
        "-.--", "--..", "-----", ".----", "..---", "...--", "....-", ".....",
        "-....", "--...", "---..", "----."};

    static {
        String[] morsecodes = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
        "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
        "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
        "-.--", "--..", "-----", ".----", "..---", "...--", "....-", ".....",
        "-....", "--...", "---..", "----."};
        int i;
        char a;
        for (a = 'a', i = 0; a <= 'z'; a++, i++) {
            code.put(morsecodes[i],a);
        }
        for (int j = 0; j < 10; j++) {
            code.put(morsecodes[i + j], (char)(j+'0'));
        }
        code.put(".-.-.-",'.');
        code.put("--..--",',');
        code.put("---...",':');
        code.put("..--..",'?');
        code.put("-....-",'-');
        code.put(".----.",'´');
        code.put("-..-.",'/');
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
        String[] signs = cipher.split("[^\\.\\-]");
        StringBuilder result = new StringBuilder();
        for(String s : signs){
            if(code.containsKey(s)){
                result.append(code.get(s));
            }else{
                result.append(" "+s+" ");//unknown characters
            }            
        }
        return result.toString();
    }
}

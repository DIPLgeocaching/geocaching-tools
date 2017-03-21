package org.geocachingtools.decoder.methods;

import java.util.HashMap;
import java.util.Map;
import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author Jakob Geringer
 */
@Method(name = "Braille",
        type = String.class,
        requiresPassword = false,
        expectedExecutionTime = DecoderMethod.ExecutionTime.FAST
)
public class Braille extends DecoderMethod<String> {

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
    private static final Map<Integer, String> brailleMap = new HashMap<>();
    private static final Map<Integer, String> brailleZahlenMap = new HashMap<>();

    static {
        brailleMap.put(1, "A");
        brailleMap.put(12, "B");
        brailleMap.put(14, "C");
        brailleMap.put(145, "D");
        brailleMap.put(15, "E");
        brailleMap.put(124, "F");
        brailleMap.put(1245, "G");
        brailleMap.put(125, "H");
        brailleMap.put(24, "I");
        brailleMap.put(245, "J");
        brailleMap.put(13, "K");
        brailleMap.put(123, "L");
        brailleMap.put(134, "M");
        brailleMap.put(1345, "N");
        brailleMap.put(135, "O");
        brailleMap.put(1234, "P");
        brailleMap.put(12345, "Q");
        brailleMap.put(1235, "R");
        brailleMap.put(234, "S");
        brailleMap.put(2345, "T");
        brailleMap.put(136, "U");
        brailleMap.put(1236, "V");
        brailleMap.put(2456, "W");
        brailleMap.put(1346, "X");
        brailleMap.put(13456, "Y");
        brailleMap.put(1356, "Z");

        brailleMap.put(345, "Ä");
        brailleMap.put(246, "Ö");
        brailleMap.put(1256, "Ü");
        brailleMap.put(2346, "ß");

        brailleMap.put(16, "AU");
        brailleMap.put(34, "ÄU");
        brailleMap.put(1456, "CH");
        brailleMap.put(146, "EI");
        brailleMap.put(126, "EU");
        brailleMap.put(346, "IE");
        brailleMap.put(156, "SCH");
        brailleMap.put(23456, "ST");

        brailleMap.put(236, "\"");
        brailleMap.put(356, "\"");
        brailleMap.put(235, "!");
        brailleMap.put(26, "?");
        brailleMap.put(36, "-");
        brailleMap.put(3, ".");
        brailleMap.put(2, ",");
        brailleMap.put(25, ":");
        brailleMap.put(23, ";");
        brailleMap.put(6, "'");
        brailleMap.put(35, "*");

        brailleZahlenMap.put(1, "1");
        brailleZahlenMap.put(12, "2");
        brailleZahlenMap.put(13, "3");
        brailleZahlenMap.put(145, "4");
        brailleZahlenMap.put(15, "5");
        brailleZahlenMap.put(145, "6");
        brailleZahlenMap.put(1245, "7");
        brailleZahlenMap.put(125, "8");
        brailleZahlenMap.put(24, "9");
        brailleZahlenMap.put(245, "0");

        brailleZahlenMap.put(236, "\"");
        brailleZahlenMap.put(356, "\"");
        brailleZahlenMap.put(235, "!");
        brailleZahlenMap.put(26, "?");
        brailleZahlenMap.put(36, "-");
        brailleZahlenMap.put(3, "'");
        brailleZahlenMap.put(2, ",");
        brailleZahlenMap.put(25, ":");
        brailleZahlenMap.put(23, ";");
        brailleZahlenMap.put(35, "*");
    }

    private String decode(String cipher) {
        cipher = cipher.replace("\n", " ");
        cipher = cipher.replace("\r", "");
        String result = "";
        boolean zahlenMode = false;
        for (String word : cipher.split(" ")) {
            for (String c : word.split(",")) {
                if (c.equals("3456")) {
                    zahlenMode = !zahlenMode;

                } else if (zahlenMode) {
                    String temp = brailleZahlenMap.get(Integer.parseInt(c));
                    if (temp != null) {
                        result += temp;
                    }
                } else {
                    result += brailleMap.get(Integer.parseInt(c));
                }
            }
            result += " ";
            zahlenMode = false;

        }
        return result;

    }
}

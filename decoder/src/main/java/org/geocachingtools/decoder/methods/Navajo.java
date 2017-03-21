package org.geocachingtools.decoder.methods;

import java.util.HashMap;
import java.util.Map;
import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author Jakob Geringer
 */
@Method(name = "Navajo",
        type = String.class,
        requiresPassword = false,
        expectedExecutionTime = DecoderMethod.ExecutionTime.FAST
)
public class Navajo extends DecoderMethod<String> {

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

    private static final Map<String, String> navajoMap = new HashMap<>();

    //Alphabet from http://www.oneofmanyfeathers.com/navajo_code_talkers_alphabet.html
    static {
        navajoMap.put("WOL-LA-CHEE", "A");
        navajoMap.put("BE-LA-SANA", "A");
        navajoMap.put("TSE-NILL", "A");
        navajoMap.put("NA-HASH-CHID", "B");
        navajoMap.put("SHUSH", "B");
        navajoMap.put("TOISH-JEH", "B");
        navajoMap.put("MOASI", "C");
        navajoMap.put("TLA-GIN", "C");
        navajoMap.put("BA-GOSHI", "C");
        navajoMap.put("BE", "D");
        navajoMap.put("CHINDI", "D");
        navajoMap.put("LHA-CHA-EH", "D");
        navajoMap.put("AH-JAH", "E");
        navajoMap.put("DZEH", "E");
        navajoMap.put("AH-NAH", "E");
        navajoMap.put("CHUO", "F");
        navajoMap.put("TSA-E-DONIN-EE", "F");
        navajoMap.put("MA-E", "F");
        navajoMap.put("AH-TAD", "G");
        navajoMap.put("KLIZZIE", "G");
        navajoMap.put("JEHA", "G");
        navajoMap.put("TSE-GAH", "H");
        navajoMap.put("CHA", "H");
        navajoMap.put("LIN", "H");
        navajoMap.put("TKIN", "I");
        navajoMap.put("YEH-HES", "I");
        navajoMap.put("A-CHI", "I");
        navajoMap.put("TKELE-CHO-G", "J");
        navajoMap.put("AH-YA-TSINNE", "J");
        navajoMap.put("YIL-DOI", "J");
        navajoMap.put("JAD-HO-LONI", "K");
        navajoMap.put("BA-AH-NE-DI-TININ", "K");
        navajoMap.put("KLIZZIE-YAZZIE", "K");
        navajoMap.put("DIBEH-YAZZIE", "L");
        navajoMap.put("AH-JAD", "L");
        navajoMap.put("NASH-DOIE-TSO", "L");
        navajoMap.put("TSIN-TLITI", "M");
        navajoMap.put("BE-TAS-TNI", "M");
        navajoMap.put("NA-AS-TSO-SI", "M");
        navajoMap.put("TSAH", "N");
        navajoMap.put("A-CHIN", "N");
        navajoMap.put("A-KHA", "O");
        navajoMap.put("TLO-CHIN", "O");
        navajoMap.put("NE-AHS-JAH", "O");
        navajoMap.put("CLA-GI-AIH", "P");
        navajoMap.put("BI-SO-DIH", "P");
        navajoMap.put("NE-ZHONI", "P");
        navajoMap.put("CA-YEILTH", "Q");
        navajoMap.put("GAH", "R");
        navajoMap.put("DAH-NES-TSA", "R");
        navajoMap.put("AH-LOSZ", "R");
        navajoMap.put("DIBEH", "S");
        navajoMap.put("KLESH", "S");
        navajoMap.put("D-AH", "T");
        navajoMap.put("A-WOH", "T");
        navajoMap.put("THAN-ZIE", "T");
        navajoMap.put("SHI-DA", "U");
        navajoMap.put("NO-DA-IH", "U");
        navajoMap.put("A-KEH-DI-GLINI", "V");
        navajoMap.put("GLOE-IH", "W");
        navajoMap.put("AL-NA-AS-DZOH", "X");
        navajoMap.put("TSAH-AS-ZIH", "Y");
        navajoMap.put("BESH-DO-TLIZ", "Z");
        navajoMap.put("BE-TKAH", " ");
    }

    /**
     * The actual decoding
     *
     * @param ciphertext
     * @return plaintext
     */
    public String decode(String cipher) {        
        String[] navajoCode = cipher.split(" ");
        String result = "";
        for(String str : navajoCode) {
            if(navajoMap.containsKey(str)) {
                result += navajoMap.get(str);
            } else {
                result += str;
            }
        }
        return result;
    }

}

package org.geocachingtools.decoder.methods;

import java.util.*;
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

    private static Map<String, Integer> substitutionMap;
    private static final String[] encryptedValues = new String[]{
        "AA", "AD", "AF", "AG", "AX",
        "DA", "DD", "DF", "DG", "DX",
        "FA", "FD", "FF", "FG", "FX",
        "GA", "GD", "GF", "GG", "GX",
        "XA", "XD", "XF", "XG", "XX"
    };

    static {
        substitutionMap = new HashMap<>();
        int c = 0;
        for (String s : encryptedValues) {
            substitutionMap.put(s, c++);
        }
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

        List<String> passwords = new ArrayList<>();
        char[] chars;        
        Set<Character> charSet = new LinkedHashSet<Character>();
        StringBuilder pwdBuilder;
        
        for (String s : request.getPasswords()) {//remove duplicates from passwords
            chars = s.toCharArray();
            charSet.clear();
            for (char c : chars) {
                charSet.add(c);
            }
            pwdBuilder = new StringBuilder();
            for (Character character : charSet) {
                pwdBuilder.append(character);
            }
            passwords.add(pwdBuilder.toString());
        }

        for (String sKey : passwords) {
            for (String tKey : passwords) {
                result = decode(request.getData(), sKey, tKey, false);
                partialRelevance = validator.check(new ValidatorRequest(result)).getRelevance();
                result = "<b>" + sKey + ", " + tKey + ", A-Z</b> => " + result + " <br/>";
                if (partialRelevance >= ValidatorResult.THRESHOLD) {
                    briefResult += result;
                    relevance = Math.max(relevance, partialRelevance);
                }
                fullResult += result;
                //same with reversed Alphabet
                result = decode(request.getData(), sKey, tKey, true);
                partialRelevance = validator.check(new ValidatorRequest(result)).getRelevance();
                result = "<b>" + sKey + ", " + tKey + ", Z-A</b> => " + result + " <br/>";
                if (partialRelevance >= ValidatorResult.THRESHOLD) {
                    briefResult += result;
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
     *
     * @param ciphertext
     * @param sKey Substitution Key
     * @param tKey Transposition Key
     * @return plaintext
     */
    public String decode(String cipher, String sKey, String tKey, boolean reverseAlphabet) {
        cipher = cipher.toUpperCase().replaceAll("[^ADFGX]", "");

        //------ Transposition ---------
        int keylen = tKey.length();
        int textlen = cipher.length();
        System.out.println("TextLEN: " + textlen + " KEYLEN: " + keylen + " mod" + textlen % keylen);

        char[] keyArray = tKey.toCharArray();
        Arrays.sort(keyArray);
        String sortedKey = new String(keyArray);
        System.out.println("SORTED: " + sortedKey);

        int rows = (int) Math.ceil((double) textlen / keylen);
        System.out.println("ROWS: " + rows);
        char[][] plain = new char[rows][keylen];

        int cipherPointer = 0;
        for (int i = 0; i < keylen; i++) {
            int targetColumn = tKey.indexOf(sortedKey.charAt(i));
            int targetColumnSize = rows - ((textlen % keylen != 0 && targetColumn + 1 > textlen % keylen) ? 1 : 0);
            for (int j = 0; j < targetColumnSize; j++, cipherPointer++) {
                //System.out.println(j +"-"+targetColumn+"-"+targetColumnSize);
                plain[j][targetColumn] = cipher.charAt(cipherPointer);
            }
        }

        //Build intermediate text
        StringBuilder result = new StringBuilder();
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < keylen; y++) {
                if (plain[x][y] != 0) {
                    result.append(plain[x][y]);
                }
            }
        }
        String tmp = result.toString();
        System.out.println("TMP: " + tmp);

        //------ Build Substitution Key ---------
        StringBuilder sKeyBuilder = new StringBuilder(sKey);
        if (reverseAlphabet) {
            for (Character c = 'z'; c >= 'a'; c--) {
                if (c == 'j') {
                    continue;
                }
                if (-1 == sKeyBuilder.indexOf(c.toString())) {
                    sKeyBuilder.append(c);
                }
            }
        } else {
            for (Character c = 'a'; c <= 'z'; c++) {
                if (c == 'j') {
                    continue;
                }
                if (-1 == sKeyBuilder.indexOf(c.toString())) {
                    sKeyBuilder.append(c);
                }
            }
        }
        sKey = sKeyBuilder.toString();
        System.out.println("SKEY: " + sKey);

        //------ Substitution ---------
        result = new StringBuilder();
        for (String c : tmp.split("(?<=\\G.{2})")) {
            System.out.println("_" + c);
            result.append(sKey.charAt(substitutionMap.get(c)));
        }
        return result.toString();
    }
}

package org.geocachingtools.decoder.methods;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    private static Map<String, Character> substitutionMap;
    private static final String[] encryptedValues = new String[]{
        "AA", "AD", "AF", "AG", "AX",
        "DA", "DD", "DF", "DG", "DX",
        "FA", "FD", "FF", "FG", "FX",
        "GA", "GD", "GF", "GG", "GX",
        "XA", "XD", "XF", "XG", "XX"
    };

    static {
        substitutionMap = new HashMap<>();
        char c = 0;
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
        for (String sKey : request.getPasswords()) {
            for (String tKey : request.getPasswords()) {
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
        System.err.println(sKey);
        StringBuilder result = new StringBuilder();
        //------ Substitution ---------
        for (String c : cipher.split("(?<=\\G.{2})")) {
            result.append(sKey.charAt(substitutionMap.get(c)));
        }
        return result.toString();
    }
    
    
    
//    //@see https://programmingcode4life.blogspot.co.at/2015/09/columnar-transposition-cipher.html
//    public static String decryptCT(String text, String key) {
//        int[] arrange = arrangeKey(key);
//        int keylen = arrange.length;
//        int textlen = text.length();
////
////        int row = (int) Math.ceil((double) lentext / lenkey);
////
////        String regex = "(?<=\\G.{" + row + "})";
////        String[] get = text.split(regex);
////        System.out.println("--------------");
////        Arrays.stream(get).forEach(System.out::println);
////        System.out.println("--------------");
////        
////
//        char[][] grid = new char[row][lenkey];
////
////        for (int x = 0; x < lenkey; x++) {
////            for (int y = 0; y < lenkey; y++) {
////                if (arrange[x] == y) {
////                    for (int z = 0; z < row; z++) {
////                        grid[z][y] = get[arrange[y]].charAt(z);
////                    }
////                }
////            }
////        }
//
//        String dec = "";
//        for (int x = 0; x < row; x++) {
//            for (int y = 0; y < lenkey; y++) {
//                dec = dec + grid[x][y];
//            }
//        }
//
//        return dec;
//    }
    
    public static int[] arrangeKey(String key) {//TODO make more beautiful
        //arrange position of grid
        String[] keys = key.split("");
        Arrays.sort(keys);
        int[] num = new int[key.length()];
        for (int x = 0; x < keys.length; x++) {
            for (int y = 0; y < key.length(); y++) {
                if (keys[x].equals(key.charAt(y) + "")) {
                    num[y] = x;
                    break;
                }
            }
        }

        return num;
    }
}

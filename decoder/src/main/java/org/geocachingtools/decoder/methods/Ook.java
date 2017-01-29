package org.geocachingtools.decoder.methods;

import java.util.Arrays;
import org.geocachingtools.decoder.*;
import org.geocachingtools.validator.*;

/**
 *
 * @author AwesomeDragon42
 */
@Method(name = "Ook & Co.",
        type = String.class,
        requiresPassword = false,
        expectedExecutionTime = DecoderMethod.ExecutionTime.FAST
)
public class Ook extends DecoderMethod<String> {

    private static final int LENGTH = 65535;
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
     * The actual decoding, parses to brainfuck and calls interpret(command)
     *
     * @param ciphertext
     * @return plaintext
     */
    private String decode(String cipher) {
        StringBuilder brainfuck = new StringBuilder();
        cipher = cipher.trim().replaceAll("[^\\!\\?\\.]", "");//remove all chars but !?.        
        String[] commands = cipher.split("(?<=\\G.{2})");//split into pairs of two
        for (String command : commands) {
            if (command.equals(".?")) {
                brainfuck.append(">");
            } else if (command.equals("?.")) {
                brainfuck.append("<");
            } else if (command.equals("..")) {
                brainfuck.append("+");
            } else if (command.equals("!!")) {
                brainfuck.append("-");
            } else if (command.equals(".!")) {
                brainfuck.append(",");
            } else if (command.equals("!.")) {
                brainfuck.append(".");
            } else if (command.equals("!?")) {
                brainfuck.append("[");
            } else if (command.equals("?!")) {
                brainfuck.append("]");
            }
        }
        return interpret(brainfuck.toString());
    }
    
    /**
     * @see https://gist.github.com/unnikked/cfad836abd9e4619a1b1
     * @param code 
     */
    public String interpret(String code) {
        StringBuilder output = new StringBuilder();
        byte[] mem = new byte[LENGTH];
        int dataPointer=0;
        
        int l = 0;
        for(int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == '>') {
                dataPointer = (dataPointer == LENGTH-1) ? 0 : dataPointer + 1;
            } else if(code.charAt(i) == '<') {
                dataPointer = (dataPointer == 0) ? LENGTH-1 : dataPointer - 1;
            } else if(code.charAt(i) == '+') {
                mem[dataPointer]++;
            } else if(code.charAt(i) == '-') {
                mem[dataPointer]--;
            } else if(code.charAt(i) == '.') {
                output.append((char) mem[dataPointer]);
            } else if(code.charAt(i) == ',') {
                throw new UnsupportedOperationException();
            } else if(code.charAt(i) == '[') {
                if(mem[dataPointer] == 0) {
                    i++;
                    while(l > 0 || code.charAt(i) != ']') {
                        if(code.charAt(i) == '[') l++;
                        if(code.charAt(i) == ']') l--;
                        i++;
                    }
                }
            } else if(code.charAt(i) == ']') {
                if(mem[dataPointer] != 0) {
                    i--;
                    while(l > 0 || code.charAt(i) != '[') {
                        if(code.charAt(i) == ']') l++;
                        if(code.charAt(i) == '[') l--;
                        i--;
                    }
                    i--;
                }
            }
        }
        System.out.println(output.toString());
        return output.toString();
    }
}

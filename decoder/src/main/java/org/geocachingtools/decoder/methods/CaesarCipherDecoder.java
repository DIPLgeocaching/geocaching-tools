/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.decoder.methods;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geocachingtools.decoder.DecoderMethod;
import static org.geocachingtools.decoder.DecoderMethod.ExecutionTime.FAST;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.geocachingtools.decoder.Method;
import org.geocachingtools.validator.DictionaryValidator;

/**
 *
 * @author Simon
 */
@Method(
        name="Caesar Cipher",
        requiresPassword = false,
        expectedExecutionTime = FAST,
        type = String.class
)
public class CaesarCipherDecoder extends DecoderMethod<String>{

    DictionaryValidator validator;

    public CaesarCipherDecoder() {
        super();
        try {
            System.out.print("Downloading word list ...");
            this.validator = new DictionaryValidator(new URL("https://raw.githubusercontent.com/dwyl/english-words/master/words.txt"));
            System.out.println(" done!");
        } catch (IOException ex) {
            Logger.getLogger(CaesarCipherDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public char shift(char ch, int n) {
        if(Character.isAlphabetic(ch)) {
            if(Character.isUpperCase(ch)) {
                return (char) ((((ch - 'A') + n) % 26) + 'A');
            }
            if(Character.isLowerCase(ch)) {
                return (char) ((((ch - 'a') + n) % 26) + 'a');
            }
        }
        return ch;
    }
    
    @Override
    public DecoderResult decode(DecoderRequest<String> request) {
        String data = request.getData();
        StringBuilder result = new StringBuilder();
        for(int n = 1; n < 26; n++) {
            StringBuilder tmp = new StringBuilder();
            for(char c : data.toCharArray()) {
                tmp.append(shift(c, n));
            }
            result.append(String.format("rot-%02d: ", n));
            result.append(tmp.toString());
            result.append("\"");
            result.append(validator.check(tmp.toString()));
            result.append("<br>\n");
            
        }
        return new DecoderResult(this, result.toString(), 0.0);
    }
    
}

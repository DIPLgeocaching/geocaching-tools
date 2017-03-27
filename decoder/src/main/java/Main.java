
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.geocachingtools.decoder.methods.OpenStegoLSB;
import org.geocachingtools.decoder.methods.OpenStegoRandomLSB;


/**
 * The MIT License (MIT)
 *
 * Copyright (c) [2016] [Geocaching-Tools: Stefan Kurzbauer, Jakob Geringer,
 * Thomas Rapberger, Lukas Wallenboeck, Simon Lehner-Dittenberger]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/**
 *
 * @author lukas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            OpenStegoRandomLSB openStego = new OpenStegoRandomLSB();
            File file = new File("C:/Users/lukas/Downloads/Ochsenburg.png");
            List<String> pwdList = new ArrayList<>();
            pwdList.add("Koenigsberg");
            pwdList.add("Franz Schubert");
            
            DecoderResult decode = openStego.decode(
                    new DecoderRequest<>(InputStream.class, new FileInputStream(file), openStego, pwdList));
            System.out.println(decode.getBriefResult());
            System.out.println(decode.getFullResult());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

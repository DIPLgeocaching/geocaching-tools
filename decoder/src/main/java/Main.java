
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.geocachingtools.decoder.methods.Braille;
import org.geocachingtools.decoder.methods.OpenStego;

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
//        try {
//            OpenStego openStego = new OpenStego();
//            File file = new File("C:/Users/lukas/Downloads/stego2.png");
        List<String> pwdList = new ArrayList<>();
        pwdList.add("1234");
        pwdList.add("12");
//
//            DecoderResult decode = openStego.decode(
//                    new DecoderRequest<>(BufferedImage.class, ImageIO.read(file), openStego, pwdList));
////            System.out.println(decode.getFullResult());

        Braille braille = new Braille();
        String dings = "145,346,234,15,1235 14,1,1456,15 24,23456 123,135,136,24,234\n"
                + "12,1235,1,24,123,123,15 1245,15,2456,24,145,134,15,2345,3\n"
                + "24,134 1,123,2345,15,1235 1236,135,1345 1236,346,1235 245,1,125,1235,15,1345\n"
                + "1236,15,1235,123,135,1235 15,1235 145,136,1235,1456 146,1345,15\n"
                + "1236,15,1235,123,15,2345,1356,136,1345,1245 12,146,145,15, 16,1245,15,1345,3\n"
                + "15,1235 15,1345,2345,2456,24,14,13,15,123,2345,15 16,234 146,1345,15,1235\n"
                + "134,24,123,24,2345,345,1235,24,156,15,1345 1245,15,125,146,134,156,1235,24,124,2345 145,346 12,1235,1,24,123,123,15,156,1235,24,124,2345,2 145,346\n"
                + "125,126,2345,15 2456,15,123,2345,2456,146,2345 1,123,234 145,346 236,12,123,24,1345,145,15,1345,156,1235,24,124,2345,356 1236,15,1235,2456,15,1345,145,15,2345 2456,24,1235,145,3\n"
                + "145,136 124,24,1345,145,15,23456 145,15,1345 14,1,1456,15 12,146,1345,3456,145,1245,3,145,125,3,15,124,245 136,1345,145 15 3456,1,14,3,245,12,3,15,124,145,3";

        DecoderResult decode = braille.decode(new DecoderRequest<>(String.class, dings, braille, pwdList));
        System.out.println(decode.getFullResult());
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}

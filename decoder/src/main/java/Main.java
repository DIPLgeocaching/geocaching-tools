
import java.util.Arrays;
import org.geocachingtools.decoder.methods.ADFGX;

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

        //System.out.println(new ADFGX().decode("behadgcfi", "geocaching", "key", true));
        System.out.println("-------------");
        String cipher="AGAAG GFAAG DDAXF GADGD DFADD FFXAXXXDDA FDAAA AADDX DFXXD DGGDF DAXDXFGAAA FDGGX FXXXD DFDGG AAAAD AFDAXFFFXF GADGF DAGAF DGFXF XFFDD FDAFAADGAA FFAAX GGDFA DFDDX FXXGG DAFDAGGFD";
        System.out.println(new ADFGX().decode(cipher, "geocahin", "travelbug", true));
        
        System.out.println("-------------");
        System.out.println(new ADFGX().decode("AADFAAAAXDDAGDADAGAADAFDDDAADDGDDAAAAGAFAD","geocahin","travelbug",true));
        
        System.out.println("-------------");
        System.out.println(new ADFGX().decode("AAADAXADDADAGDDAAAFAADADADGDFGADGDAD","geocahin","trave",true));


    }

}

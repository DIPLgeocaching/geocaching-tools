
import org.geocachingtools.validator.Validator;
import org.geocachingtools.validator.ValidatorRequest;

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
 * @author awesome
 */
public class Main {

    static Validator v = Validator.getInstance();

    public static void main(String[] args) {
        ck("38° 53′ 23″ N, 77° 00′ 32″ W");
        
        ck("N 47° 16′39.1234″ W 008° 26′45.5678″");
        ck("47° 16′39.1234″ N 8° 26′45.5678″W");
        ck("47° 16′39,1234″ -8° 26′45,5678″");
        ck("47d 16′39.1234″ N 8d 26′45.5678″ W");
        ck("47:16:39.1234″ N 8:26:45.5678″ W");
        ck("47 16’39.1234″N 8 26’45.45678″W");

        ck("N 47° 16.123′ W 8° 26.456′");
        ck("N 47° 16,123′ W 8° 26,456′");
        ck("47° 16.123′ N 8° 26.456′ W");
        ck("N 47° 16.123 W 8° 26.456");
        ck("47° 16.123′ -8° 26.456′");
        ck("47° 16.123 N 008° 26.456 W");
        
        System.out.println("DDD");
        ck("N 47.16123 W 8.264");
        ck("47.1612° N 8.26456° W");
        ck("47.1612° -8.26456°");
        ck("N 47.1612° W 008.26456°");
        
        ck("THREEHUNDREDANDTEN SIXHUNDREDANDTWENTY");
        ck("sechsundzwanzigxkommaxnullxfuenfxsiebenxfuenfunddreissigxkommaxnullxzweixeins");
        ck("dskfuze9fhkjsad fjziuefjdsklfksjdhfgpejflksdn kjsdhfioejflksdnfjsdiufewpjflsdk nsdjfpefjsfnsbfsajavas");
    }

    private static void ck(String s) {
        System.out.println(v.check(new ValidatorRequest(s)).getRelevance() + "->" + s);
    }
}


/* DMS

 N 47° 16′39″ W 8° 26′46″
 N 47° 16′39.1234″ W 008° 26′45.5678″
 47° 16′39.1234″ N 8° 26′45.5678″W
 47° 16′39,1234″ -8° 26′45,5678″
 47d 16′39.1234″ N 8d 26′45.5678″ W
 47:16:39.1234″ N 8:26:45.5678″ W
 47 16’39.1234″N 8 26’45.45678″W

 N 47° 16.123′ W 8° 26.456′
 N 47° 16,123′ W 8° 26,456′
 47° 16.123′ N 8° 26.456′ W
 N 47° 16.123 W 8° 26.456
 47° 16.123′ -8° 26.456′
 47° 16.123 N 008° 26.456 W


 N 47.16123 W 8.264
 47.1612° N 8.26456° W
 47.1612° -8.26456°
 N 47.1612° W 008.26456°

*/

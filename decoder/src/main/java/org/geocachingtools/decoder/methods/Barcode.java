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
package org.geocachingtools.decoder.methods;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.geocachingtools.decoder.DecoderMethod;
import org.geocachingtools.decoder.DecoderMethod.ExecutionTime;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.geocachingtools.decoder.Method;

/**
 *
 * @author Simon
 */
@Method(
        name = "Barcode",
        expectedExecutionTime = ExecutionTime.SLOW,
        requiresPassword = false,
        type = InputStream.class
)
public class Barcode extends DecoderMethod<InputStream> {

    Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);

    public Barcode() {
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);
    }

    @Override
    public DecoderResult decode(DecoderRequest<InputStream> request) {
        try {
            BufferedImage img = ImageIO.read(request.getData());
            MultiFormatReader reader = new MultiFormatReader();
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(img);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = reader.decode(bitmap, hints);

            BarcodeFormat barcodeFormat = result.getBarcodeFormat();
            StringBuilder fullResult = new StringBuilder();
            fullResult.append("Found Barcode of type ").append(barcodeFormat.toString()).append("\n");
            fullResult.append("Content:\n");
            fullResult.append(result.getText());

            return new DecoderResult(this, barcodeFormat.toString(), fullResult.toString(), 1.0);
        } catch (IOException | NotFoundException ex) {
            return new DecoderResult(this, "nothing found", 0.0);
        }
    }

}

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

import java.awt.PageAttributes;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimeType;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;
import org.geocachingtools.decoder.DecoderMethod;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.geocachingtools.decoder.I18n;
import org.geocachingtools.decoder.Method;

/**
 *
 * @author lukas
 */
@Method(name = "FileType",
        type = InputStream.class,
        requiresPassword = false,
        expectedExecutionTime = DecoderMethod.ExecutionTime.FAST
)
public class FileType extends DecoderMethod<InputStream> {

    private I18n i18n;
    
    @Override
    public DecoderResult decode(DecoderRequest<InputStream> request) {
        i18n = new I18n(request.getLocale());
        double releveance = 0;
        String fullResult;
        try {
            Tika t = new Tika();
            String type = t.detect(request.getData());
            if(!type.matches("\\.jpg$|\\.gif$|\\.png$")) {
                releveance = 1;
                fullResult = i18n.get("POSSIBLE-FILETYPE") + " " + type;
            } else {
                fullResult = i18n.get("LOW-VALIDATOR-RESULT");
            }
        } catch (IOException ex) {
            return new DecoderResult(this, i18n.get("DECODER-EXCEPTION"),"", 0.0);
        }
        return new DecoderResult(this, fullResult, releveance);
    }

}

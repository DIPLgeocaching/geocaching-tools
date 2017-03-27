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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sourceforge.openstego.OpenStegoConfig;
import net.sourceforge.openstego.OpenStegoException;
import net.sourceforge.openstego.OpenStegoPlugin;
import net.sourceforge.openstego.plugin.randlsb.RandomLSBPlugin;
import org.geocachingtools.decoder.DecoderMethod;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.geocachingtools.decoder.I18n;
import org.geocachingtools.decoder.Method;
import org.geocachingtools.validator.Validator;

/**
 *
 * @author lukas
 */
@Method(name = "OpenStego RandomLSB",
        type = InputStream.class,
        requiresPassword = false,
        expectedExecutionTime = DecoderMethod.ExecutionTime.SLOW
)
public class OpenStegoRandomLSB extends DecoderMethod<InputStream> {

    private I18n i18n;

    @Override
    public DecoderResult decode(DecoderRequest<InputStream> request) {
        String briefResult = "";
        String fullResult = "";

        String result;
        i18n = new I18n(request.getLocale());

        OpenStegoConfig config;
        net.sourceforge.openstego.OpenStego openStego;

        try {
            OpenStegoPlugin extractPlugin = new RandomLSBPlugin();
            config = extractPlugin.createConfig();
            openStego = new net.sourceforge.openstego.OpenStego(extractPlugin, config);
            config = openStego.getConfig();

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            ImageIO.write(ImageIO.read(request.getData()), "png", out);

            List<?> stegoOutput;
            try {
                stegoOutput = openStego.extractData(out.toByteArray(), "Image");

                String filename = (String) stegoOutput.get(0);
                byte[] output = (byte[]) stegoOutput.get(1);
                String base64 = Base64.getEncoder().encodeToString(output);
                result = "<a download=\"" + filename + "\" href=\"data:content-type;base64," + base64 + "\">" + filename + "</a>";
                briefResult += i18n.get("WITHOUT-PASSWORD") + " => " + result + "<br/>";
            } catch (OpenStegoException ex) {
                result = i18n.get("NOT-POSSIBLE");
            }
            fullResult += i18n.get("WITHOUT-PASSWORD") + " => " + result + "<br/>";

            if (!request.getPasswords().isEmpty()) {
                for (String pwd : request.getPasswords()) {
                    config.setPassword(pwd);
                    try {
                        stegoOutput = openStego.extractData(out.toByteArray(), "Image");

                        String filename = (String) stegoOutput.get(0);
                        byte[] output = (byte[]) stegoOutput.get(1);
                        String base64 = Base64.getEncoder().encodeToString(output);
                        result = "<a download=\"" + filename + "\" href=\"data:content-type;base64," + base64 + "\">" + filename + "</a>";
                        briefResult += pwd + " => " + result + "<br/>";
                    } catch (OpenStegoException ex) {
                        result = i18n.get("NOT-POSSIBLE");
                    }
                    fullResult += pwd + " => " + result + "<br/>";
                }
            }
            out.close();

            if (briefResult.isEmpty()) {//No suitable result found
                briefResult = i18n.get("LOW-VALIDATOR-RESULT");
            }
            return new DecoderResult(this, briefResult, fullResult, 0.0);
        } catch (IOException | OpenStegoException ex) {
            Logger.getLogger(OpenStegoRandomLSB.class.getName()).log(Level.SEVERE, null, ex);
            briefResult = i18n.get("DECODER-EXCEPTION");
        }
        return new DecoderResult(this, briefResult, fullResult, Double.MAX_VALUE);
    }
}

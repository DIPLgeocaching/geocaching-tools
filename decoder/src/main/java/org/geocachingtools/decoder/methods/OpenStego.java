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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.openstego.OpenStegoConfig;
import net.sourceforge.openstego.OpenStegoException;
import net.sourceforge.openstego.OpenStegoPlugin;
import net.sourceforge.openstego.util.PluginManager;
import org.geocachingtools.decoder.DecoderMethod;
import org.geocachingtools.decoder.DecoderRequest;
import org.geocachingtools.decoder.DecoderResult;
import org.geocachingtools.decoder.Method;

/**
 *
 * @author lukas
 */
@Method(name = "OpenStego",
        type = BufferedImage.class,
        requiresPassword = true,
        expectedExecutionTime = DecoderMethod.ExecutionTime.SLOW
)
public class OpenStego extends DecoderMethod<BufferedImage> {

    @Override
    public DecoderResult decode(DecoderRequest<BufferedImage> request) {
        try {
            PluginManager.loadPlugins();
            OpenStegoPlugin extractPlugin = PluginManager.getPluginByName("RandomLSB");
            OpenStegoConfig config = extractPlugin.createConfig();
            net.sourceforge.openstego.OpenStego openStego = new net.sourceforge.openstego.OpenStego(extractPlugin, config);
            config = openStego.getConfig();
//            config.setPassword("password");

            File file = new File("C:/Users/lukas/Downloads/stego.png");

            List<?> stegoOutput = openStego.extractData(file);
            String str = new String((byte[]) stegoOutput.get(1), "UTF-8");
            System.out.println(str);
            return new DecoderResult(this, "", "", 0.0);
        } catch (OpenStegoException | UnsupportedEncodingException ex) {
            Logger.getLogger(OpenStego.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

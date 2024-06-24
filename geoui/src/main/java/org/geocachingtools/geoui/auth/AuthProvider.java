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
package org.geocachingtools.geoui.auth;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 *
 * @author Simon
 */
public abstract class AuthProvider {

    private static final Properties prop = new Properties();

    static {
        try {
            prop.load(AuthProvider.class.getResourceAsStream("/oauth2.properties"));
        } catch (Exception ex) {
            Logger.getLogger(AuthProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private final OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

    public abstract OAuthClientRequest buildAuthRequest() throws OAuthSystemException;

    public abstract OAuthClientRequest buildTokenRequestByCode(String code) throws OAuthSystemException;

    public abstract OAuthClientRequest buildTokenRequestByRefreshToken(String refreshToken) throws OAuthSystemException;

    public abstract UserData loadUserData(String accessToken) throws OAuthSystemException, OAuthProblemException;

    private String get(String name) {
        return prop.get(this.getClass().getCanonicalName() + "." + name).toString();
    }

    public String getClientID() {
        return get("clientId");
    }

    public String getSecret() {
        return get("clientSecret");
    }

    public String getResponseUri() {
        return get("responseUri");
    }

    public OAuthJSONAccessTokenResponse requestToken(OAuthClientRequest request) throws OAuthProblemException, OAuthSystemException {
        request.setHeader("accept", "application/json");
        return oAuthClient.accessToken(request, "POST", OAuthJSONAccessTokenResponse.class);
    }

    public OAuthResourceResponse requestResource(OAuthClientRequest request) throws OAuthSystemException, OAuthProblemException {
        request.setHeader("accept", "application/json");
        return oAuthClient.resource(request, "GET", OAuthResourceResponse.class);
    }

    public OAuthClientRequest buildBearerRequest(String url, String accessToken) throws OAuthSystemException {
        return new OAuthBearerClientRequest(url)
                .setAccessToken(accessToken)
                .buildQueryMessage();
    }

}

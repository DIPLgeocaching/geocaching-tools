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
package org.geocachingtools.geoui.auth.provider;

import com.google.gson.Gson;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.geocachingtools.geoui.auth.AuthProvider;
import org.geocachingtools.geoui.auth.JsonUserResourceResponse;
import org.geocachingtools.geoui.models.User;

/**
 *
 * @author Simon
 */
public class GoogleAuthProvider extends AuthProvider {

    @Override
    public OAuthClientRequest buildAuthRequest() throws OAuthSystemException {
        return OAuthClientRequest
                .authorizationProvider(OAuthProviderType.GOOGLE)
                .setClientId(getClientID())
                .setResponseType("code")
                .setScope("profile email")
                .setRedirectURI(getResponseUri())
                .buildQueryMessage();
    }

    @Override
    public OAuthClientRequest buildTokenRequestByCode(String code) throws OAuthSystemException {
        return OAuthClientRequest
                .tokenProvider(OAuthProviderType.GOOGLE)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(getClientID())
                .setClientSecret(getSecret())
                .setCode(code)
                .setRedirectURI(getResponseUri())
                .buildBodyMessage();
    }

    @Override
    public OAuthClientRequest buildTokenRequestByRefreshToken(String refreshToken) throws OAuthSystemException {
        return OAuthClientRequest
                .tokenProvider(OAuthProviderType.GOOGLE)
                .setGrantType(GrantType.REFRESH_TOKEN)
                .setClientId(getClientID())
                .setClientSecret(getSecret())
                .setRedirectURI(getResponseUri())
                .setRefreshToken(refreshToken)
                .buildBodyMessage();
    }

    @Override
    public JsonUserResourceResponse loadUserData(String accessToken) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest("https://www.googleapis.com/oauth2/v1/userinfo?alt=json")
                .setAccessToken(accessToken)
                .buildQueryMessage();
        OAuthResourceResponse resource = requestResource(bearerClientRequest);
        Gson gson = new Gson();
        return gson.fromJson(resource.getBody(), JsonUserResourceResponse.class);
    }

    @Override
    public String toString() {
        return "Google";
    }

}

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
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.geocachingtools.geoui.auth.AuthProvider;
import org.geocachingtools.geoui.auth.GithubJsonUserData;
import org.geocachingtools.geoui.auth.UserData;

/**
 *
 * @author Simon
 */
public class GithubAuthProvider extends AuthProvider {

    private Gson gson = new Gson();

    @Override
    public OAuthClientRequest buildAuthRequest() throws OAuthSystemException {
        return OAuthClientRequest
                .authorizationProvider(OAuthProviderType.GITHUB)
                .setClientId(getClientID())
                .setResponseType("code")
                .setScope("user:email")
                .setRedirectURI(getResponseUri())
                .buildQueryMessage();
    }

    @Override
    public OAuthClientRequest buildTokenRequestByCode(String code) throws OAuthSystemException {
        return OAuthClientRequest
                .tokenProvider(OAuthProviderType.GITHUB)
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
                .tokenProvider(OAuthProviderType.GITHUB)
                .setGrantType(GrantType.REFRESH_TOKEN)
                .setClientId(getClientID())
                .setClientSecret(getSecret())
                .setRedirectURI(getResponseUri())
                .setRefreshToken(refreshToken)
                .buildBodyMessage();
    }

    @Override
    public UserData loadUserData(String accessToken) throws OAuthSystemException, OAuthProblemException {
        OAuthResourceResponse userResource = requestResource(buildBearerRequest("https://api.github.com/user", accessToken));
        OAuthResourceResponse userMailResource = requestResource(buildBearerRequest("https://api.github.com/user/emails", accessToken));

        GithubJsonUserData data = new GithubJsonUserData();
        data.setUser(gson.fromJson(userResource.getBody(), GithubJsonUserData.User.class));
        data.setMails(gson.fromJson(userMailResource.getBody(), GithubJsonUserData.UserEmail[].class));
        return data;
    }

    @Override
    public String toString() {
        return "Github";
    }
}

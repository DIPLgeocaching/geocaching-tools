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

/**
 *
 * @author Simon
 */
public class GithubJsonUserData  implements UserData{

    public static class User {
        public String id;
        public String login;
        public String avatar_url;
    };
    public static class UserEmail {
        public String email;
        public boolean primary;
        public boolean verified;
        public boolean visibility;
    }
    
    private User user;
    private UserEmail mail;

    public void setMails(UserEmail[] mail) {
        for(UserEmail m : mail) {
            if(m.primary)
                this.mail = m;
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String getEmail() {
        return mail.email;
    }

    @Override
    public String getId() {
        return user.id;
    }

    @Override
    public String getName() {
        return user.login;
    }

    @Override
    public String getPictureUrl() {
        return user.avatar_url;
    }
    
}

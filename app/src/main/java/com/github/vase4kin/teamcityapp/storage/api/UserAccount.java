/*
 * Copyright 2016 Andrey Tolpeev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vase4kin.teamcityapp.storage.api;

import com.github.vase4kin.teamcityapp.base.api.Jsonable;

/**
 * User account
 */
public class UserAccount implements Jsonable {

    private String userName;
    private String teamcityUrl;
    private boolean isActive;

    public UserAccount(String userName, String teamcityUrl, boolean isActive) {
        this.userName = userName;
        this.teamcityUrl = teamcityUrl;
        this.isActive = isActive;
    }

    public String getUserName() {
        return userName;
    }

    public String getTeamcityUrl() {
        return teamcityUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String getId() {
        return teamcityUrl;
    }
}

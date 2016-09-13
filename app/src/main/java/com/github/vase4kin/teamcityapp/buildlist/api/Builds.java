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

package com.github.vase4kin.teamcityapp.buildlist.api;

import android.support.annotation.VisibleForTesting;

import com.github.vase4kin.teamcityapp.api.interfaces.Collectible;

import java.util.List;

/**
 * Builds
 */
public class Builds implements Collectible<Build> {

    private int count;

    private String href;

    private String nextHref;

    private List<Build> build;

    public String getHref() {
        return href;
    }

    public String getNextHref() {
        return nextHref;
    }

    public int getCount() {
        return count;
    }

    @Override
    public List<Build> getObjects() {
        return build;
    }

    @VisibleForTesting
    public Builds(int count, List<Build> build) {
        this.count = count;
        this.build = build;
    }
}

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

package com.github.vase4kin.teamcityapp.base.tabs.data;

/**
 * Event on tab text change
 */
public class OnTextTabChangeEvent {

    private int mCount;
    private int mTabPosition;

    public OnTextTabChangeEvent(int count, int type) {
        this.mCount = count;
        this.mTabPosition = type;
    }

    /**
     * @return Number of items need to show on dedicated tab
     */
    public int getCount() {
        return mCount;
    }

    /**
     * @return Tab position where we need to show updated info
     */
    public int getTabPosition() {
        return mTabPosition;
    }
}

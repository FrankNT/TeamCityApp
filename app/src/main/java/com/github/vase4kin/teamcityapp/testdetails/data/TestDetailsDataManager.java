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

package com.github.vase4kin.teamcityapp.testdetails.data;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.tests.api.TestOccurrences;

/**
 * Data manager for managing server operations for {@link com.github.vase4kin.teamcityapp.testdetails.view.TestDetailsActivity}
 */
public interface TestDetailsDataManager {

    /**
     * Load test details
     *
     * @param loadingListener - Listener to receive server callbacks
     * @param url             - Test details url
     */
    void loadData(OnLoadingListener<TestOccurrences.TestOccurrence> loadingListener, String url);

    /**
     * Unsubscribe all server subscriptions
     */
    void unsubscribe();
}

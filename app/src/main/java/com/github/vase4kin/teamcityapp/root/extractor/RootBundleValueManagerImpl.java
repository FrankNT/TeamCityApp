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

package com.github.vase4kin.teamcityapp.root.extractor;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.vase4kin.teamcityapp.base.extractor.BundleExtractorValues;
import com.github.vase4kin.teamcityapp.base.list.extractor.BaseValueExtractorImpl;

/**
 * Impl of {@link RootBundleValueManager}
 */
public class RootBundleValueManagerImpl extends BaseValueExtractorImpl implements RootBundleValueManager {

    public RootBundleValueManagerImpl(@NonNull Bundle mBundle) {
        super(mBundle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequiredToReload() {
        return mBundle != null && mBundle.getBoolean(BundleExtractorValues.IS_REQUIRED_TO_RELOAD, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNewAccountCreated() {
        return mBundle != null && mBundle.getBoolean(BundleExtractorValues.IS_NEW_ACCOUNT_CREATED, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeIsNewAccountCreated() {
        mBundle.remove(BundleExtractorValues.IS_NEW_ACCOUNT_CREATED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeIsRequiredToReload() {
        mBundle.remove(BundleExtractorValues.IS_REQUIRED_TO_RELOAD);
    }
}

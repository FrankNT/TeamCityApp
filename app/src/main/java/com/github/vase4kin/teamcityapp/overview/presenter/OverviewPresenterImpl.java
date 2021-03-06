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

package com.github.vase4kin.teamcityapp.overview.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.base.list.extractor.BaseValueExtractor;
import com.github.vase4kin.teamcityapp.base.list.presenter.BaseListPresenterImpl;
import com.github.vase4kin.teamcityapp.base.list.view.BaseDataModel;
import com.github.vase4kin.teamcityapp.base.list.view.BaseListView;
import com.github.vase4kin.teamcityapp.navigation.api.BuildElement;
import com.github.vase4kin.teamcityapp.overview.data.OverViewDataManager;
import com.github.vase4kin.teamcityapp.overview.data.OverviewDataModelImpl;

import java.util.List;

import javax.inject.Inject;

/**
 * Presenter to handle logic of {@link com.github.vase4kin.teamcityapp.overview.view.BuildOverviewElementsFragment}
 */
public class OverviewPresenterImpl extends BaseListPresenterImpl<BaseDataModel, BuildElement, BaseListView, OverViewDataManager, BaseValueExtractor> {

    @Inject
    OverviewPresenterImpl(@NonNull BaseListView view,
                          @NonNull OverViewDataManager dataManager,
                          @Nullable BaseValueExtractor valueExtractor) {
        super(view, dataManager, null, valueExtractor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadData(@NonNull OnLoadingListener<List<BuildElement>> loadingListener) {
        mDataManager.load(mValueExtractor.getBuild().getHref(), loadingListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BaseDataModel createModel(List<BuildElement> data) {
        return new OverviewDataModelImpl(data);
    }
}

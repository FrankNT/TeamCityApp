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

package com.github.vase4kin.teamcityapp.runningbuilds.dagger;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.vase4kin.teamcityapp.R;
import com.github.vase4kin.teamcityapp.api.TeamCityService;
import com.github.vase4kin.teamcityapp.base.list.extractor.BaseValueExtractor;
import com.github.vase4kin.teamcityapp.base.list.extractor.BaseValueExtractorImpl;
import com.github.vase4kin.teamcityapp.buildlist.router.BuildListRouter;
import com.github.vase4kin.teamcityapp.buildlist.router.BuildListRouterImpl;
import com.github.vase4kin.teamcityapp.navigation.tracker.ViewTracker;
import com.github.vase4kin.teamcityapp.runningbuilds.data.RunningBuildsDataManager;
import com.github.vase4kin.teamcityapp.runningbuilds.data.RunningBuildsDataManagerImpl;
import com.github.vase4kin.teamcityapp.runningbuilds.tracker.RunningBuildsViewTrackerImpl;
import com.github.vase4kin.teamcityapp.runningbuilds.view.RunningBuildListView;
import com.github.vase4kin.teamcityapp.runningbuilds.view.RunningBuildsListViewImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class RunningListModule {

    private View mView;
    private AppCompatActivity mActivity;

    public RunningListModule(View mView, AppCompatActivity mActivity) {
        this.mView = mView;
        this.mActivity = mActivity;
    }

    @Provides
    RunningBuildsDataManager providesRunningBuildsDataManager(TeamCityService teamCityService) {
        return new RunningBuildsDataManagerImpl(teamCityService);
    }

    @Provides
    RunningBuildListView providesRunningBuildListView() {
        return new RunningBuildsListViewImpl(mView, mActivity, R.string.empty_list_message_running_builds);
    }

    @Provides
    ViewTracker providesViewTracker() {
        return new RunningBuildsViewTrackerImpl();
    }

    @Provides
    BuildListRouter providesBuildListRouter() {
        return new BuildListRouterImpl(mActivity);
    }

    @Provides
    BaseValueExtractor providesBuildListValueExtractor() {
        return new BaseValueExtractorImpl(mActivity.getIntent().getExtras());
    }

}

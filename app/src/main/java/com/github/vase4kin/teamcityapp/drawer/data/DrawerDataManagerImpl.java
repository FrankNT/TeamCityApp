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

package com.github.vase4kin.teamcityapp.drawer.data;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.agents.data.AgentsDataManagerImpl;
import com.github.vase4kin.teamcityapp.api.TeamCityService;
import com.github.vase4kin.teamcityapp.queue.data.BuildQueueDataManagerImpl;
import com.github.vase4kin.teamcityapp.runningbuilds.data.RunningBuildsDataManagerImpl;
import com.github.vase4kin.teamcityapp.storage.SharedUserStorage;
import com.github.vase4kin.teamcityapp.storage.api.UserAccount;

import java.util.List;

/**
 * Impl of {@link DrawerDataManager}
 */
public class DrawerDataManagerImpl implements DrawerDataManager {

    private TeamCityService mTeamCityService;
    protected SharedUserStorage mSharedUserStorage;

    public DrawerDataManagerImpl(TeamCityService teamCityService, SharedUserStorage sharedUserStorage) {
        this.mTeamCityService = teamCityService;
        this.mSharedUserStorage = sharedUserStorage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(OnLoadingListener<List<UserAccount>> loadingListener) {
        loadingListener.onSuccess(mSharedUserStorage.getUserAccounts());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveUser(String url) {
        mSharedUserStorage.setUserActiveWithEmail(url);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActiveUser(String url) {
        return mSharedUserStorage.getActiveUser().getTeamcityUrl().equals(url);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadRunningBuildsCount(final OnLoadingListener<Integer> loadingListener) {
        new RunningBuildsDataManagerImpl(mTeamCityService).loadCount(loadingListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadConnectedAgentsCount(final OnLoadingListener<Integer> loadingListener) {
        new AgentsDataManagerImpl(mTeamCityService, null).loadCount(loadingListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadBuildQueueCount(final OnLoadingListener<Integer> loadingListener) {
        new BuildQueueDataManagerImpl(mTeamCityService).loadCount(loadingListener);
    }
}

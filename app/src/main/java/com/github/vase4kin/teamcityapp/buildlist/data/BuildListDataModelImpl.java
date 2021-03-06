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

package com.github.vase4kin.teamcityapp.buildlist.data;

import com.github.vase4kin.teamcityapp.buildlist.api.Build;
import com.github.vase4kin.teamcityapp.buildlist.api.LoadMoreBuild;
import com.github.vase4kin.teamcityapp.utils.IconUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Impl of {@link BuildListDataModel}
 */
public class BuildListDataModelImpl implements BuildListDataModel {

    private List<Build> mBuilds;

    public BuildListDataModelImpl(List<Build> mBuilds) {
        this.mBuilds = mBuilds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoadMoreBuild(int position) {
        return mBuilds.get(position) instanceof LoadMoreBuild;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBranchName(int position) {
        return mBuilds.get(position).getBranchName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuildStatusIcon(int position) {
        Build build = mBuilds.get(position);
        return IconUtils.getBuildStatusIcon(build.getStatus(), build.getState());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStatusText(int position) {
        return mBuilds.get(position).getStatusText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuildNumber(int position) {
        String buildNumber = mBuilds.get(position).getNumber();
        return buildNumber != null
                ? String.format("#%s", buildNumber)
                : "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Build getBuild(int position) {
        return mBuilds.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Build build) {
        mBuilds.add(build);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Build build) {
        mBuilds.remove(build);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(BuildListDataModel dataModel) {
        for (Build build : dataModel) {
            mBuilds.add(build);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStartDate(int position) {
        return mBuilds.get(position).getStartDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuildTypeId(int position) {
        return mBuilds.get(position).getBuildTypeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mBuilds.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Build> iterator() {
        return mBuilds.iterator();
    }
}

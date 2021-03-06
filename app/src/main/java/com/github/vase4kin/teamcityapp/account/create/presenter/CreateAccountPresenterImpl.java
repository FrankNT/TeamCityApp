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

package com.github.vase4kin.teamcityapp.account.create.presenter;

import com.github.vase4kin.teamcityapp.account.create.data.CreateAccountDataManager;
import com.github.vase4kin.teamcityapp.account.create.data.CreateAccountDataModel;
import com.github.vase4kin.teamcityapp.account.create.data.CustomOnLoadingListener;
import com.github.vase4kin.teamcityapp.account.create.router.CreateAccountRouter;
import com.github.vase4kin.teamcityapp.account.create.tracker.CreateAccountTracker;
import com.github.vase4kin.teamcityapp.account.create.view.CreateAccountView;
import com.github.vase4kin.teamcityapp.account.create.view.OnCreateAccountPresenterListener;

import javax.inject.Inject;

/**
 * Impl of {@link CreateAccountPresenter}
 */
public class CreateAccountPresenterImpl implements CreateAccountPresenter, OnCreateAccountPresenterListener {

    private CreateAccountView mView;
    private CreateAccountDataManager mDataManager;
    private CreateAccountDataModel mDataModel;
    private CreateAccountRouter mRouter;
    private CreateAccountTracker mTracker;

    @Inject
    CreateAccountPresenterImpl(CreateAccountView view,
                               CreateAccountDataManager dataManager,
                               CreateAccountDataModel dataModel,
                               CreateAccountRouter router,
                               CreateAccountTracker tracker) {
        this.mView = view;
        this.mDataManager = dataManager;
        this.mDataModel = dataModel;
        this.mRouter = router;
        this.mTracker = tracker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleOnCreateView() {
        mView.initViews(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleOnResume() {
        mTracker.trackView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleOnDestroy() {
        mView.onDestroyView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateUrl(String url) {
        mView.showProgressDialog();
        if (mDataModel.hasAccountWithUrl(url)) {
            mView.showNewAccountExistErrorMessage();
        } else {
            mDataManager.loadData(new CustomOnLoadingListener<String>() {
                @Override
                public void onSuccess(String url) {
                    mDataManager.createNewUserAccount(url);
                    mDataManager.initTeamCityService(url);
                    mView.dismissProgressDialog();
                    mView.finish();
                    mRouter.startRootProjectActivityWhenNewAccountIsCreated();
                    mTracker.trackUserLoginSuccess();
                }

                @Override
                public void onFail(int code, String errorMessage) {
                    mView.setErrorText(errorMessage);
                    mView.dismissProgressDialog();
                    mTracker.trackUserLoginFailed(errorMessage);
                }
            }, url);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finish() {
        if (!mView.isEmailEmpty()) {
            mView.showDiscardDialog();
        } else {
            mView.finish();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick() {
        finish();
    }
}

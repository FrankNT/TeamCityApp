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

package com.github.vase4kin.teamcityapp.login.presenter;

import android.support.annotation.NonNull;

import com.github.vase4kin.teamcityapp.account.create.data.CreateAccountDataManager;
import com.github.vase4kin.teamcityapp.account.create.data.CustomOnLoadingListener;
import com.github.vase4kin.teamcityapp.login.router.LoginRouter;
import com.github.vase4kin.teamcityapp.login.tracker.LoginTracker;
import com.github.vase4kin.teamcityapp.login.view.LoginView;
import com.github.vase4kin.teamcityapp.login.view.OnLoginButtonClickListener;

import javax.inject.Inject;

/**
 * Impl for {@link LoginPresenter}
 */
public class LoginPresenterImpl implements LoginPresenter, OnLoginButtonClickListener {

    private static final int UNAUTHORIZED_STATUS_CODE = 401;

    private LoginView mView;
    private CreateAccountDataManager mDataManager;
    private LoginRouter mRouter;
    private LoginTracker mTracker;

    @Inject
    LoginPresenterImpl(@NonNull LoginView mView,
                       @NonNull CreateAccountDataManager mDataManager,
                       @NonNull LoginRouter mRouter,
                       @NonNull LoginTracker tracker) {
        this.mView = mView;
        this.mDataManager = mDataManager;
        this.mRouter = mRouter;
        this.mTracker = tracker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        mView.initViews(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        mView.unbindViews();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        mTracker.trackView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        mView.onWindowFocusChanged(hasFocus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLoginButtonClick(String serverUrl) {
        mView.showProgressDialog();
        mDataManager.loadData(new CustomOnLoadingListener<String>() {
            @Override
            public void onSuccess(String data) {
                mView.dismissProgressDialog();
                mDataManager.createNewUserAccount(data);
                mDataManager.initTeamCityService(data);
                mRouter.openProjectsRootPageForFirstStart();
                mTracker.trackUserLoginSuccess();
                mView.close();
            }

            @Override
            public void onFail(int statusCode, String errorMessage) {
                mView.dismissProgressDialog();
                mView.setError(errorMessage);
                mTracker.trackUserLoginFailed(errorMessage);
                mView.hideKeyboard();
                if (statusCode == UNAUTHORIZED_STATUS_CODE) {
                    mView.showUnauthorizedInfoDialog();
                }
            }
        }, serverUrl);
    }
}

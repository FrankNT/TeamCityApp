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

package com.github.vase4kin.teamcityapp.tests.presenter;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.base.tabs.data.OnTextTabChangeEvent;
import com.github.vase4kin.teamcityapp.buildlist.view.OnLoadMoreListener;
import com.github.vase4kin.teamcityapp.navigation.tracker.ViewTracker;
import com.github.vase4kin.teamcityapp.tests.api.TestOccurrences;
import com.github.vase4kin.teamcityapp.tests.data.TestsDataManager;
import com.github.vase4kin.teamcityapp.tests.data.TestsDataModelImpl;
import com.github.vase4kin.teamcityapp.tests.extractor.TestsValueExtractor;
import com.github.vase4kin.teamcityapp.tests.router.TestsRouter;
import com.github.vase4kin.teamcityapp.tests.view.TestsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class TestsPresenterImplTest {

    @Captor
    private ArgumentCaptor<OnTextTabChangeEvent> mEventCaptor;

    @Captor
    private ArgumentCaptor<OnLoadingListener<Integer>> mOnLoadingIntegerListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<OnLoadMoreListener> mOnLoadMoreListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<OnLoadingListener<List<TestOccurrences.TestOccurrence>>> mOnLoadingListenerArgumentCaptor;

    @Mock
    private MenuItem mMenuItem;

    @Mock
    private Menu mMenu;

    @Mock
    private MenuInflater mMenuInflater;

    @Mock
    private TestOccurrences.TestOccurrence mTestOccurrence;

    @Mock
    private OnLoadingListener<List<TestOccurrences.TestOccurrence>> mLoadingListener;

    @Mock
    private TestsView mView;

    @Mock
    private TestsDataManager mDataManager;

    @Mock
    private ViewTracker mTracker;

    @Mock
    private TestsValueExtractor mValueExtractor;

    @Mock
    private TestsRouter mRouter;

    private TestsPresenterImpl mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new TestsPresenterImpl(mView, mDataManager, mTracker, mValueExtractor, mRouter);
    }

    @Test
    public void testLoadData() throws Exception {
        when(mValueExtractor.getUrl()).thenReturn("url");
        mPresenter.loadData(mLoadingListener);
        verify(mValueExtractor).getUrl();
        verify(mDataManager).loadFailedTests(eq("url"), eq(mLoadingListener));
        verifyNoMoreInteractions(mView, mDataManager, mTracker, mValueExtractor, mRouter);
    }

    @Test
    public void testCreateModel() throws Exception {
        when(mTestOccurrence.getName()).thenReturn("name");
        assertThat(mPresenter.createModel(Collections.singletonList(mTestOccurrence)).getName(0), is(equalTo("name")));
    }

    @Test
    public void testInitViews() throws Exception {
        when(mDataManager.canLoadMore()).thenReturn(false);
        mPresenter.initViews();
        verify(mView).setOnLoadMoreListener(mOnLoadMoreListenerArgumentCaptor.capture());

        OnLoadMoreListener onLoadMoreListener = mOnLoadMoreListenerArgumentCaptor.getValue();
        assertThat(onLoadMoreListener.isLoadedAllItems(), is(true));
        verify(mDataManager).canLoadMore();

        onLoadMoreListener.loadMore();
        verify(mView).addLoadMoreItem();
        verify(mDataManager).loadMore(mOnLoadingListenerArgumentCaptor.capture());

        OnLoadingListener<List<TestOccurrences.TestOccurrence>> listOnLoadingListener = mOnLoadingListenerArgumentCaptor.getValue();
        List<TestOccurrences.TestOccurrence> tests = Collections.emptyList();
        listOnLoadingListener.onSuccess(tests);
        verify(mView).removeLoadMoreItem();
        verify(mView).addMoreBuilds(any(TestsDataModelImpl.class));

        listOnLoadingListener.onFail("error");
        verify(mView, times(2)).removeLoadMoreItem();
        verify(mView).showRetryLoadMoreSnackBar();
    }

    @Test
    public void testOnViewsCreated() throws Exception {
        when(mValueExtractor.getUrl()).thenReturn("url");
        mPresenter.onViewsCreated();
        verify(mValueExtractor, times(2)).getUrl();
        verify(mDataManager).loadTestDetails(eq("url"), mOnLoadingIntegerListenerArgumentCaptor.capture());

        OnLoadingListener<Integer> listener = mOnLoadingIntegerListenerArgumentCaptor.getValue();
        listener.onSuccess(1);
        verify(mDataManager).postChangeTabTitleEvent(eq(1));

        listener.onFail("error");
        verifyNoMoreInteractions(mValueExtractor);
    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {
        mPresenter.onCreateOptionsMenu(mMenu, mMenuInflater);
        verify(mView).onCreateOptionsMenu(eq(mMenu), eq(mMenuInflater));
        verifyNoMoreInteractions(mView, mDataManager, mTracker, mValueExtractor, mRouter, mMenu, mMenuInflater);
    }

    @Test
    public void testOnPrepareOptionsMenu() throws Exception {
        mPresenter.onPrepareOptionsMenu(mMenu);
        verify(mView).onPrepareOptionsMenu(eq(mMenu));
        verifyNoMoreInteractions(mView, mDataManager, mTracker, mValueExtractor, mRouter, mMenu);
    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {
        when(mView.onOptionsItemSelected(eq(mMenuItem))).thenReturn(true);
        assertThat(mPresenter.onOptionsItemSelected(mMenuItem), is(equalTo(true)));
        verify(mView).showProgressWheel();
        verify(mView).hideErrorView();
        verify(mView).hideEmpty();
        verify(mView).emptyRecyclerView();
        verify(mView).hideEmpty();
        verify(mView).onOptionsItemSelected(eq(mMenuItem));
        verifyNoMoreInteractions(mView, mDataManager, mTracker, mValueExtractor, mRouter, mMenuItem);
    }

    @Test
    public void testLoadFailedTests() throws Exception {
        when(mValueExtractor.getUrl()).thenReturn("url");
        mPresenter.loadFailedTests();
        verify(mValueExtractor).getUrl();
        verify(mDataManager).loadFailedTests(eq("url"));
        verifyNoMoreInteractions(mView, mDataManager, mTracker, mValueExtractor, mRouter);
    }

    @Test
    public void testLoadSuccessTests() throws Exception {
        when(mValueExtractor.getUrl()).thenReturn("url");
        mPresenter.loadSuccessTests();
        verify(mValueExtractor).getUrl();
        verify(mDataManager).loadPassedTests(eq("url"));
        verifyNoMoreInteractions(mView, mDataManager, mTracker, mValueExtractor, mRouter);
    }

    @Test
    public void testLoadIgnoredTests() throws Exception {
        when(mValueExtractor.getUrl()).thenReturn("url");
        mPresenter.loadIgnoredTests();
        verify(mValueExtractor).getUrl();
        verify(mDataManager).loadIgnoredTests(eq("url"));
        verifyNoMoreInteractions(mView, mDataManager, mTracker, mValueExtractor, mRouter);
    }

    @Test
    public void testOnFailedTestClick() throws Exception {
        mPresenter.onFailedTestClick("url");
        verify(mRouter).openFailedTest(eq("url"));
        verifyNoMoreInteractions(mView, mDataManager, mTracker, mValueExtractor, mRouter);
    }
}
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

package com.github.vase4kin.teamcityapp.buildlog.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.runner.AndroidJUnit4;

import com.github.vase4kin.teamcityapp.TeamCityApplication;
import com.github.vase4kin.teamcityapp.api.TeamCityService;
import com.github.vase4kin.teamcityapp.base.extractor.BundleExtractorValues;
import com.github.vase4kin.teamcityapp.buildlist.api.Build;
import com.github.vase4kin.teamcityapp.buildtabs.view.BuildTabsActivity;
import com.github.vase4kin.teamcityapp.dagger.components.AppComponent;
import com.github.vase4kin.teamcityapp.dagger.components.RestApiComponent;
import com.github.vase4kin.teamcityapp.dagger.modules.AppModule;
import com.github.vase4kin.teamcityapp.dagger.modules.FakeTeamCityServiceImpl;
import com.github.vase4kin.teamcityapp.dagger.modules.Mocks;
import com.github.vase4kin.teamcityapp.dagger.modules.RestApiModule;
import com.github.vase4kin.teamcityapp.helper.CustomIntentsTestRule;
import com.github.vase4kin.teamcityapp.storage.SharedUserStorage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link BuildLogFragment}
 */
@RunWith(AndroidJUnit4.class)
public class BuildLogFragmentTest {

    @Rule
    public DaggerMockRule<AppComponent> mAppComponentDaggerRule = new DaggerMockRule<>(AppComponent.class, new AppModule((TeamCityApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext()))
            .provides(SharedUserStorage.class, SharedUserStorage.init(InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext()))
            .set(new DaggerMockRule.ComponentSetter<AppComponent>() {
                @Override
                public void setComponent(AppComponent appComponent) {
                    TeamCityApplication app = (TeamCityApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
                    app.setAppInjector(appComponent);
                }
            });

    @Rule
    public DaggerMockRule<RestApiComponent> mRestComponentDaggerRule = new DaggerMockRule<>(RestApiComponent.class, new RestApiModule(Mocks.URL))
            .addComponentDependency(AppComponent.class, new AppModule((TeamCityApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext()))
            .providesMock(SharedUserStorage.class)
            .set(new DaggerMockRule.ComponentSetter<RestApiComponent>() {
                @Override
                public void setComponent(RestApiComponent restApiComponent) {
                    TeamCityApplication app = (TeamCityApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
                    app.setRestApiInjector(restApiComponent);
                }
            });

    @Rule
    public CustomIntentsTestRule<BuildTabsActivity> mActivityRule = new CustomIntentsTestRule<>(BuildTabsActivity.class);

    @Spy
    private TeamCityService mTeamCityService = new FakeTeamCityServiceImpl();

    @Spy
    private Build mBuild = Mocks.successBuild();

    @Test
    public void testUserCanSeeBuildLog() {
        // Prepare mocks
        when(mTeamCityService.build(anyString())).thenReturn(Observable.just(mBuild));

        // Prepare intent
        // <! ---------------------------------------------------------------------- !>
        // Passing build object to activity, had to create it for real, Can't pass mock object as serializable in bundle :(
        // <! ---------------------------------------------------------------------- !>
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable(BundleExtractorValues.BUILD, Mocks.successBuild());
        intent.putExtras(b);

        // Start activity
        mActivityRule.launchActivity(intent);

        // Checking build log tab title
        onView(withText("Build Log"))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        // Check web view content
        onWebView()
                .withElement(findElement(Locator.ID, "build_log"))
                .check(webMatches(getText(), containsString("Hello, this is fake build log!")));
    }

}
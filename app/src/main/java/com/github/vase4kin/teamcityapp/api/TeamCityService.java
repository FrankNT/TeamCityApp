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

package com.github.vase4kin.teamcityapp.api;

import android.support.annotation.Nullable;

import com.github.vase4kin.teamcityapp.agents.api.Agents;
import com.github.vase4kin.teamcityapp.artifact.api.Files;
import com.github.vase4kin.teamcityapp.buildlist.api.Build;
import com.github.vase4kin.teamcityapp.buildlist.api.Builds;
import com.github.vase4kin.teamcityapp.changes.api.Changes;
import com.github.vase4kin.teamcityapp.navigation.api.NavigationNode;
import com.github.vase4kin.teamcityapp.tests.api.TestOccurrences;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * TeamCity Api Service
 */
public interface TeamCityService {

    /**
     * Base header for all requests
     */
    String APPLICATION_JSON = "Accept: application/json";

    /**
     * List agents
     *
     * @param includeDisconnected - determine what agents to return
     * @param fields              - additional TC fields
     * @return {@link Observable} with {@link Agents}
     */
    @Headers(APPLICATION_JSON)
    @GET("guestAuth/app/rest/agents")
    Observable<Agents> listAgents(@Nullable @Query("includeDisconnected") Boolean includeDisconnected,
                                  @Nullable @Query("fields") String fields);

    /**
     * List build types by url
     *
     * @param url - Url of build type
     * @return @return {@link Observable} with {@link NavigationNode}
     */
    @Headers(APPLICATION_JSON)
    @GET
    Observable<NavigationNode> listBuildTypes(@Url String url);

    /**
     * Get single build details
     *
     * @param url - Build url
     * @return {@link Observable} with {@link Build}
     */
    @Headers(APPLICATION_JSON)
    @GET
    Observable<Build> build(@Url String url);

    /**
     * List builds
     *
     * @param id - BuildType id
     * @param locator - TC locator
     * @return {@link Observable} with {@link Builds}
     */
    @Headers(APPLICATION_JSON)
    @GET("guestAuth/app/rest/buildTypes/id:{id}/builds")
    Observable<Builds> listBuilds(@Path("id") String id,
                                  @Query("locator") String locator);

    /**
     * List running builds
     *
     * @param locator - TC build locator
     * @param fields - Additional TC fields
     * @return {@link Observable} with {@link Builds}
     */
    @Headers(APPLICATION_JSON)
    @GET("guestAuth/app/rest/builds")
    Observable<Builds> listRunningBuilds(@Query("locator") String locator,
                                         @Query("fields") String fields);

    /**
     * List queued builds
     *
     * @param fields - Additional TC fields
     * @return {@link Observable} with {@link Builds}
     */
    @Headers(APPLICATION_JSON)
    @GET("guestAuth/app/rest/buildQueue")
    Observable<Builds> listQueueBuilds(@Query("fields") String fields);

    /**
     * List more builds
     *
     * @param url - More builds url
     * @return {@link Observable} with {@link Builds}
     */
    @Headers(APPLICATION_JSON)
    @GET
    Observable<Builds> listMoreBuilds(@Url String url);

    /**
     * List build artifacts
     *
     * @param url - Artifacts url
     * @param locator - TC locator
     * @return {@link Observable} with {@link Files}
     */
    @Headers(APPLICATION_JSON)
    @GET
    Observable<Files> listArtifacts(@Url String url,
                                    @Query("locator") String locator);

    /**
     * Download artifact file by url
     *
     * @param url - Artifact file url
     * @return {@link Observable} with {@link ResponseBody}
     */
    @Headers(APPLICATION_JSON)
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);

    /**
     * List tests
     *
     * @param url - Build tests url
     * @return {@link Observable} with {@link TestOccurrences}
     */
    @Headers(APPLICATION_JSON)
    @GET
    Observable<TestOccurrences> listTestOccurrences(@Url String url);

    /**
     * Get single test info by url
     *
     * @param url - Test url
     * @return {@link Observable} with {@link TestOccurrences.TestOccurrence}
     */
    @Headers(APPLICATION_JSON)
    @GET
    Observable<TestOccurrences.TestOccurrence> testOccurrence(@Url String url);

    /**
     * List changes
     *
     * @param url - Build changes url
     * @return {@link Observable} with {@link Changes}
     */
    @Headers(APPLICATION_JSON)
    @GET
    Observable<Changes> listChanges(@Url String url);

    /**
     * Get single change
     *
     * @param url - Change url
     * @return {@link Observable} with {@link com.github.vase4kin.teamcityapp.changes.api.Changes.Change}
     */
    @Headers(APPLICATION_JSON)
    @GET
    Observable<Changes.Change> change(@Url String url);
}

/*
 * Copyright 2019 Sudhir Khanger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.sudhirkhanger.apod

import android.app.Application
import com.facebook.stetho.Stetho
import com.sudhirkhanger.apod.di.module.ContextModule
import com.sudhirkhanger.apod.di.component.AppComponent
import com.sudhirkhanger.apod.di.component.DaggerAppComponent
import com.sudhirkhanger.apod.utilities.CustomDebugTree
import timber.log.Timber

class ApodApp : Application() {

    companion object {
        @JvmStatic
        lateinit var instance: ApodApp
            private set
    }

    private val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent.inject(this)
        initTimber()
        initStetho()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(CustomDebugTree())
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    fun getApodAppComponent(): AppComponent = appComponent
}
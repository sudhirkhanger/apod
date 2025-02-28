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

package com.sudhirkhanger.apod.di.component

import com.sudhirkhanger.apod.ApodApp
import com.sudhirkhanger.apod.di.module.ApodDbModule
import com.sudhirkhanger.apod.di.module.ContextModule
import com.sudhirkhanger.apod.di.module.RetrofitModule
import com.sudhirkhanger.apod.di.module.ViewModelModule
import com.sudhirkhanger.apod.ui.detail.ApodDetailFragment
import com.sudhirkhanger.apod.ui.list.ApodListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RetrofitModule::class, ApodDbModule::class, ContextModule::class, ViewModelModule::class]
)
interface AppComponent {
    fun inject(apodApp: ApodApp)
    fun inject(apodListFragment: ApodListFragment)
    fun inject(apodDetailFragment: ApodDetailFragment)
}
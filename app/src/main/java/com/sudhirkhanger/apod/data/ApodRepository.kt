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

package com.sudhirkhanger.apod.data

import androidx.lifecycle.LiveData
import com.sudhirkhanger.apod.data.db.ApodDao
import com.sudhirkhanger.apod.data.db.ApodEntity
import com.sudhirkhanger.apod.data.network.ApodNetworkDataSource
import com.sudhirkhanger.apod.utilities.AppExecutors
import com.sudhirkhanger.apod.utilities.Utilities
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApodRepository @Inject constructor(
    private val apodDao: ApodDao,
    private val apodNetworkDataSource: ApodNetworkDataSource,
    private val executors: AppExecutors
) {
    init {
        fetchPictureByDate()
        val apodLiveData = apodNetworkDataSource.getApodMutableLiveData()
        apodLiveData.observeForever {
            executors.diskIO().execute {
                apodDao.insertPicture(it)
            }
        }
    }

    fun fetchPictureByDate(date: String) {
        apodNetworkDataSource.fetchApodData(date)
    }

    fun getAllPictures(): LiveData<List<ApodEntity>> {
        return apodDao.getAllPictures()
    }

    private fun fetchPictureByDate() {
        val cal = Calendar.getInstance()
        executors.diskIO().execute {
            val apodEntity = apodDao.getPictureEntityByDate(
                Utilities.convertStringToDate(Utilities.getCurrentDate(cal))
            )
            if (apodEntity == null) fetchPictureByDate(Utilities.getCurrentDate(cal))
        }
    }
}
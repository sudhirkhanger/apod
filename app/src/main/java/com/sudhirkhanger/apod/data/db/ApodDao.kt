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

package com.sudhirkhanger.apod.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ApodDao {

    @Query("SELECT * FROM apod")
    fun getAllPictures(): LiveData<List<ApodEntity>>

    @Query("SELECT * FROM apod WHERE date = :date")
    fun getPictureByDate(date: String): LiveData<ApodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPicture(apodEntity: ApodEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePicture(apodEntity: ApodEntity)
}
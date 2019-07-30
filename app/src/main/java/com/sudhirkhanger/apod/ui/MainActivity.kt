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

package com.sudhirkhanger.apod.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sudhirkhanger.apod.R
import com.sudhirkhanger.apod.ui.detail.ApodDetailFragment
import com.sudhirkhanger.apod.ui.list.ApodListFragment
import com.sudhirkhanger.apod.ui.list.OnPictureSelectedListener

class MainActivity : AppCompatActivity(), OnPictureSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.add(R.id.container, ApodListFragment.newInstance())
            ft.commit()
        }
    }

    override fun onPictureSelected(position: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        fragmentTransaction.replace(
            R.id.container,
            ApodDetailFragment.newInstance(position)
        )
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}

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

package com.sudhirkhanger.apod.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sudhirkhanger.apod.ApodApp
import com.sudhirkhanger.apod.R
import timber.log.Timber
import javax.inject.Inject

class ApodListFragment : Fragment() {

    companion object {
        fun newInstance() = ApodListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var apodListViewModel: ApodListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_apod_list, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        apodListViewModel = ViewModelProviders.of(this, viewModelFactory)[ApodListViewModel::class.java]

        apodListViewModel.apodPicturList.observe(viewLifecycleOwner, Observer {
            Timber.e("size %d %s", it.size, it.get(0).title)
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ApodApp.instance.getApodAppComponent().inject(this)
    }
}

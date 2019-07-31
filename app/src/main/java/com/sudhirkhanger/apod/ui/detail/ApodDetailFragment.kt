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

package com.sudhirkhanger.apod.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.sudhirkhanger.apod.ApodApp
import com.sudhirkhanger.apod.R
import com.sudhirkhanger.apod.ui.list.ApodListViewModel
import timber.log.Timber
import javax.inject.Inject

class ApodDetailFragment : Fragment() {

    companion object {
        private const val PICTURE_POS = "picture_pos"

        fun newInstance(pos: Int) = ApodDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(PICTURE_POS, pos)
            }
        }
    }


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var apodListViewModel: ApodListViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var apodStatePagerAdapter: ApodStatePagerAdapter;
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_apod_detail, container, false)
        initView(v)
        initialize()
        return v
    }

    private fun initView(view: View) {
        viewPager = view.findViewById(R.id.viewpager)
    }

    private fun initialize() {
        position = arguments?.getInt(PICTURE_POS) ?: 0
        apodStatePagerAdapter = ApodStatePagerAdapter(childFragmentManager, mutableListOf())
        viewPager.adapter = apodStatePagerAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ApodApp.instance.getApodAppComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        apodListViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(ApodListViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        apodListViewModel.apodPictureList.observe(viewLifecycleOwner, Observer {
            for (apodEntity in it) Timber.e("${apodEntity.date}")
            apodStatePagerAdapter.setPictureData(it)
            viewPager.currentItem = position
        })
    }
}

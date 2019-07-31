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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.sudhirkhanger.apod.R
import com.sudhirkhanger.apod.data.db.ApodEntity
import com.sudhirkhanger.apod.utilities.Utilities
import timber.log.Timber

class ApodFragment : Fragment() {

    companion object {
        private const val PICTURE_DETAIL = "picture_detail"

        fun newInstance(apodEntity: ApodEntity) = ApodFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PICTURE_DETAIL, apodEntity)
            }
        }
    }

    private lateinit var apodIv: ImageView
    private lateinit var apodDateTv: TextView
    private lateinit var apodTitleTv: TextView
    private lateinit var apodCopyTv: TextView
    private lateinit var apodExplainTv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_apod, container, false)
        initViews(v)
        initialize()
        return v
    }

    private fun initViews(view: View) {
        apodIv = view.findViewById(R.id.apod_iv)
        apodDateTv = view.findViewById(R.id.date_tv)
        apodTitleTv = view.findViewById(R.id.title_tv)
        apodCopyTv = view.findViewById(R.id.copyright_tv)
        apodExplainTv = view.findViewById(R.id.explain_tv)
    }

    private fun initialize() {
        val apodEntity = arguments?.getParcelable<ApodEntity>(PICTURE_DETAIL)
        Timber.e("vp %s", apodEntity.toString())
        apodEntity?.apply {

            if (!hdurl.isNullOrEmpty()) {
                apodIv.visibility = View.VISIBLE
                Picasso
                    .get()
                    .load(hdurl)
                    .fit()
                    .centerInside()
                    .noFade()
                    .placeholder(resources.getDrawable(R.drawable.ic_image_white_24dp, null))
                    .error(resources.getDrawable(R.drawable.ic_broken_image_white_24dp, null))
                    .into(apodIv)
            } else {
                apodIv.visibility = View.VISIBLE
            }

            if (date != null) {
                apodDateTv.visibility = View.VISIBLE
                apodDateTv.text = Utilities.convertDateFormat(date)
            } else {
                apodDateTv.visibility = View.GONE
            }

            if (!copyright.isNullOrEmpty()) {
                apodCopyTv.visibility = View.VISIBLE
                apodCopyTv.text = copyright
            } else {
                apodCopyTv.visibility = View.GONE
            }

            if (!title.isNullOrEmpty()) {
                apodTitleTv.visibility = View.VISIBLE
                apodTitleTv.text = title
            } else {
                apodTitleTv.visibility = View.GONE
            }

            if (!explanation.isNullOrEmpty()) {
                apodExplainTv.visibility = View.VISIBLE
                apodExplainTv.text = explanation
            } else {
                apodExplainTv.visibility = View.GONE
            }
        }
    }
}

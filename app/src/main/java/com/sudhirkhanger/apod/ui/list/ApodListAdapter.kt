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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sudhirkhanger.apod.R
import com.sudhirkhanger.apod.data.db.ApodEntity
import com.sudhirkhanger.apod.utilities.Utilities

class ApodListAdapter(private val onPictureClick: (Int) -> Unit) :
    ListAdapter<ApodEntity, ApodListAdapter.ApodListViewHolder>(ApodEntityDiffCallBack()) {

    class ApodListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val apodIv: ImageView = view.findViewById(R.id.apod_list_iv)
        val apodTv: TextView = view.findViewById(R.id.apod_list_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_apod, parent, false)
        return ApodListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApodListViewHolder, position: Int) {
        getItem(position).let { apodEntity ->
            with(holder) {

                Picasso.get()
                    .load(apodEntity.hdurl)
                    .fit()
                    .centerCrop()
                    .noFade()
                    .placeholder(holder.itemView.resources.getDrawable(R.drawable.ic_image_white_24dp, null))
                    .error(holder.itemView.resources.getDrawable(R.drawable.ic_broken_image_white_24dp, null))
                    .into(this.apodIv)

                apodTv.text = Utilities.convertDateFormat(apodEntity.date)
                itemView.setOnClickListener { onPictureClick(position) }
            }
        }
    }
}
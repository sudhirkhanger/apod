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

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import com.sudhirkhanger.apod.data.db.ApodEntity

class PicturePageAdapter : PagerAdapter() {

    private var pictureList: List<ApodEntity> = mutableListOf()

    fun setPictureData(pictureList: List<ApodEntity>) {
        this.pictureList = pictureList
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, viewObject: Any): Boolean = view == viewObject as ImageView

    override fun getCount(): Int = pictureList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        Picasso.get().load(pictureList[position].hdurl).fit().into(imageView)

        (container as ViewPager).addView(
            imageView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, viewObject: Any) {
        (container as ViewPager).removeView(viewObject as ImageView)
    }
}
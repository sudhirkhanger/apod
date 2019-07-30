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

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sudhirkhanger.apod.ApodApp
import java.util.*
import javax.inject.Inject

class ApodListFragment : Fragment(), DatePickerSelection {


    companion object {
        fun newInstance() = ApodListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var apodListViewModel: ApodListViewModel
    private lateinit var apodRv: RecyclerView
    private lateinit var apodListAdapter: ApodListAdapter
    private lateinit var pictureSelectedListener: OnPictureSelectedListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(com.sudhirkhanger.apod.R.layout.fragment_apod_list, container, false)

        apodRv = v.findViewById(com.sudhirkhanger.apod.R.id.apod_rv)
        apodListAdapter = ApodListAdapter {
            pictureSelectedListener.onPictureSelected(it)
        }
        val gridLayoutManager = GridLayoutManager(v.context, 2)
        apodRv.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            adapter = apodListAdapter
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        apodListViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(ApodListViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        apodListViewModel.apodPictureList.observe(viewLifecycleOwner, Observer {
            apodListAdapter.submitList(it)
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ApodApp.instance.getApodAppComponent().inject(this)

        if (context is OnPictureSelectedListener) {
            pictureSelectedListener = context
        } else throw ClassCastException(
            "$context must implement QuestionItemClickedFragmentInterface"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.sudhirkhanger.apod.R.menu.list_frag_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            com.sudhirkhanger.apod.R.id.action_pick_date -> {
                val newFragment = DatePickerFragment()
                newFragment.setTargetFragment(this, 100)
                newFragment.show(fragmentManager, "datePicker")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDateSelected(date: String) {
        apodListViewModel.fetchPictureByDate(date)
    }

    class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

        private lateinit var dateSelectionListener: DatePickerSelection

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            dateSelectionListener = targetFragment as DatePickerSelection
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
            datePickerDialog.datePicker.maxDate = c.timeInMillis
            return datePickerDialog
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            // Do something with the date chosen by the user
            dateSelectionListener.onDateSelected("$year-${month + 1}-$day")
        }
    }
}

interface DatePickerSelection {
    fun onDateSelected(date: String)
}

interface OnPictureSelectedListener {
    fun onPictureSelected(position: Int)
}

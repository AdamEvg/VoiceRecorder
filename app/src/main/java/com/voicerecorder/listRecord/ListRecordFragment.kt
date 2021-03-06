package com.voicerecorder.listRecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.voicerecorder.R
import com.voicerecorder.database.RecordDatabase
import com.voicerecorder.databinding.FragmentListRecordBinding
import kotlinx.android.synthetic.main.fragment_list_record.*


class ListRecordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListRecordBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_record, container, false
        )
        val application = requireNotNull(this.activity).application

        val dataSource = RecordDatabase.getInstance(application).recordDatabaseDao
        val viewModelFactory = ListRecordViewModelFactory(dataSource)

        val listRecordViewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(ListRecordViewModel::class.java)

        binding.listRecordViewModel = listRecordViewModel

        val adapter = ListRecordAdapter()
        binding.recyclerView.adapter = adapter
        listRecordViewModel.records.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.data = it }
        })

        binding.lifecycleOwner = this
        return binding.root
    }

}
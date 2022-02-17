package com.example.android.navigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.navigation.R
import com.example.android.navigation.adapter.RecyclerAdapter
import com.example.android.navigation.databinding.FragmentResultBinding
import com.example.android.navigation.references.TableData

class ResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentResultBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_result, container, false
        )
        binding.dataList.adapter = RecyclerAdapter()
        TableData.data.observe(viewLifecycleOwner, {
            binding.dataList.adapter?.notifyDataSetChanged()
        })
        return binding.root
    }

}

package com.example.studybuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studybuddy.databinding.FragmentAlarmBinding

class AlarmFragment : Fragment() {
    private var _binding : FragmentAlarmBinding? = null
    private val binding get() =_binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAlarmBinding.inflate(inflater,container,false)
        val rootView = binding.root
        return rootView
    }
}
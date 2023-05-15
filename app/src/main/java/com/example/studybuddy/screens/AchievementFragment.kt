package com.example.studybuddy.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.studybuddy.recycle.AchievementAdapter
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentAchievementBinding

class AchievementFragment : Fragment() {
    private var _binding : FragmentAchievementBinding? = null
    private val binding get() =_binding!!
    private val viewModel: TaskViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAchievementBinding.inflate(inflater,container,false)
        val rootView = binding.root
        val mAdapter = AchievementAdapter(viewModel.achievementList.value!!)
        binding.customizationRecyclerView.adapter = mAdapter
        return rootView
    }
}
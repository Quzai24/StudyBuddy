package com.example.studybuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studybuddy.databinding.FragmentMainBinding

private var _binding : FragmentMainBinding? = null
private val binding get() =_binding!!
private val tasks = listOf(Task("Do the Dishes", listOf(4,30), listOf("Saturday","Sunday")))
class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        val rootView = binding.root
        val mAdapter = TaskAdapter(tasks)
        binding.recyclerview.adapter = mAdapter

        return rootView
    }
}
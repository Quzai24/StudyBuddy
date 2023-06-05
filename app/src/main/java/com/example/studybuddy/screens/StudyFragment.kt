package com.example.studybuddy.screens

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.studybuddy.R
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentStudyBinding
import kotlinx.coroutines.delay

class StudyFragment : Fragment() {
    private var _binding : FragmentStudyBinding? = null
    private val binding get() =_binding!!
    private var numberOfPomodoros = 0
    private val viewModel: TaskViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStudyBinding.inflate(inflater,container,false)
        val rootView = binding.root

        //spinner setup
        val pomodoroAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.pomodoros, android.R.layout.simple_spinner_item)
        pomodoroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.howmany.adapter = pomodoroAdapter
        binding.howmany.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, childView: View?, position: Int, itemId: Long) {
                numberOfPomodoros = adapterView.getItemAtPosition(position).toString().toInt()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {  }
        }

        //progress bar
        binding.start.setOnClickListener {
            binding.progressBar.max = 25
            ObjectAnimator.ofInt(binding.progressBar,"progress", 25)
                .setDuration(5000).start()
            ObjectAnimator.ofInt(binding.progressBar,"progress",25).apply {
                duration=5000
                startDelay = 5001
                binding.progressBar.progress= 0 }.start()
        }
        return rootView
    }
}
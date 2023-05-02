package com.example.studybuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.studybuddy.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {
    private var _binding : FragmentStudyBinding? = null
    private val binding get() =_binding!!
    private var numberOfPomodoros = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStudyBinding.inflate(inflater,container,false)
        val rootView = binding.root
        val pomodoroAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.pomodoros, android.R.layout.simple_spinner_item)
        pomodoroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.howmany.adapter = pomodoroAdapter
        binding.howmany.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, childView: View?, position: Int, itemId: Long) {
                numberOfPomodoros = adapterView.getItemAtPosition(position).toString().toInt()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {  }
        }
        return rootView
    }
}
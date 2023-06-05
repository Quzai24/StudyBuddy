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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay

class StudyFragment : Fragment() {
    private var _binding : FragmentStudyBinding? = null
    private val binding get() =_binding!!
    private var numberOfPomodoros = 0
    private val viewModel: TaskViewModel by activityViewModels()
    private lateinit var dbRef : DatabaseReference
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
            var number : Long = 0
            if(numberOfPomodoros==1) {
                ObjectAnimator.ofInt(binding.progressBar, "progress", 25)
                    .setDuration(25*60*1000).start()
                ObjectAnimator.ofInt(binding.progressBar, "progress", 0).apply {
                    duration = 1
                    startDelay = 25*60*1000+1
                }.start()
                ObjectAnimator.ofInt(binding.progressBar, "progress", 25).apply {
                    duration = 5*60*1000
                    startDelay = 25*60*1000+3
                }.start()
                number++
            }
            if(numberOfPomodoros>1){
                val total = numberOfPomodoros
                while(numberOfPomodoros!=0){
                    ObjectAnimator.ofInt(binding.progressBar,"progress", 25).apply {
                        duration = 25*60*1000
                        if(number>0)startDelay = 30*60*1000 * number + 3 }.start()
                    ObjectAnimator.ofInt(binding.progressBar,"progress",0).apply {
                        duration = 1
                        startDelay = 25*60*1000 * number + 25*60*1000 + 1 }.start()
                    ObjectAnimator.ofInt(binding.progressBar,"progress",25).apply {
                        duration = 5*60*1000
                        startDelay = 25*60*1000 * number + 25*60*1000 + 3 }.start()
                    if(total.toLong()==number-1)
                        ObjectAnimator.ofInt(binding.progressBar, "progress", 0).apply {
                            duration = 1
                            startDelay = 30 * 60 * 1000 * number + 30 * 60 * 1000 + 1 }.start()
                    numberOfPomodoros--
                    number++
                }
            }
            viewModel.achievmentGet(false,number.toInt())
            dbRef = FirebaseDatabase.getInstance().getReference("Achievements")
            dbRef.child("Complete Sessions").setValue(viewModel.completeStudySession)
            dbRef.child("Achieve").setValue(viewModel.achievements.value)
        }
        return rootView
    }
}
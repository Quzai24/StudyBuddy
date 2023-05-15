package com.example.studybuddy.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentClosetBinding

class ClosetFragment : Fragment() {
    private var _binding : FragmentClosetBinding? = null
    private val binding get() =_binding!!
    private val viewModel: TaskViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentClosetBinding.inflate(inflater,container,false)
        val rootView = binding.root
        var skin = viewModel.getOutfit(0)
        var eye = viewModel.getOutfit(7)
        binding.personOne.setOnClickListener{ binding.person.setImageResource(viewModel.getOutfit(0).outfit); skin = viewModel.getOutfit(0) }
        binding.personTwo.setOnClickListener{ binding.person.setImageResource(viewModel.getOutfit(1).outfit); skin = viewModel.getOutfit(1) }
        binding.personThree.setOnClickListener{ binding.person.setImageResource(viewModel.getOutfit(2).outfit); skin = viewModel.getOutfit(2) }
        binding.personFour.setOnClickListener{ binding.person.setImageResource(viewModel.getOutfit(3).outfit); skin = viewModel.getOutfit(3) }
        binding.eyesOne.setOnClickListener{ binding.eyes.setImageResource(viewModel.getOutfit(4).outfit); eye = viewModel.getOutfit(4) }
        binding.eyesTwo.setOnClickListener{ binding.eyes.setImageResource(viewModel.getOutfit(5).outfit); eye = viewModel.getOutfit(5) }
        binding.eyesThree.setOnClickListener{ binding.eyes.setImageResource(viewModel.getOutfit(6).outfit); eye = viewModel.getOutfit(6) }
        binding.eyesFour.setOnClickListener{ binding.eyes.setImageResource(viewModel.getOutfit(7).outfit); eye = viewModel.getOutfit(7) }
        binding.check.setOnClickListener{
            viewModel.name = binding.enterName.text.toString()
            viewModel.setFit(skin,0)
            viewModel.setFit(eye,1)
            viewModel.stop=false
            rootView.findNavController().navigateUp()
        }
        return rootView
    }
}
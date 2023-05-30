package com.example.studybuddy.screens

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studybuddy.R
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
        val lamb: (View, Int)-> Unit = {view, num->
            binding.person.setImageResource(viewModel.getOutfit(num).outfit)
            skin = viewModel.getOutfit(num)
        }
        binding.personOne.setOnClickListener{lamb(binding.personOne,0)}
        binding.personTwo.setOnClickListener{lamb(binding.personTwo,1)}
        binding.personThree.setOnClickListener{lamb(binding.personThree,2)}
        binding.personFour.setOnClickListener{lamb(binding.personFour,3)}
        binding.eyesOne.setOnClickListener{lamb(binding.eyesOne,4)}
        binding.eyesTwo.setOnClickListener{lamb(binding.eyesTwo,5)}
        binding.eyesThree.setOnClickListener{lamb(binding.eyesThree,6)}
        binding.eyesFour.setOnClickListener{lamb(binding.eyesFour,7)}
        binding.check.setOnClickListener{
            viewModel.name = binding.enterName.text.toString()
            viewModel.setFit(skin,0)
            viewModel.setFit(eye,1)
            rootView.findNavController().navigateUp()
        }
        val lamb2: (View)-> Unit = {view->
            AlertDialog.Builder(context).setTitle("Hair Color?").setItems(R.array.haircolor,DialogInterface.OnClickListener{dialog, which ->
                if(which == 0){
                    binding.rightpart.setImageResource(R.drawable.blackrightpart)
                    binding.leftpart.setImageResource(R.drawable.blackleftpart)
                    binding.middlepart.setImageResource(R.drawable.blackmiddlepart)
                    binding.longrightpart.setImageResource(R.drawable.longblackrightpart)
                    binding.pigtails.setImageResource(R.drawable.blackpigtails)
                    binding.longmiddlepart.setImageResource(R.drawable.longblackmiddlepart)
                }
                else if(which == 1){
                    binding.rightpart.setImageResource(R.drawable.brownrightpart)
                    binding.leftpart.setImageResource(R.drawable.brownleftpart)
                    binding.middlepart.setImageResource(R.drawable.brownmiddlepart)
                    binding.longrightpart.setImageResource(R.drawable.longbrownrightpart)
                    binding.pigtails.setImageResource(R.drawable.brownpigtails)
                    binding.longmiddlepart.setImageResource(R.drawable.longbrownmiddlepart)

                }
                else if(which == 2){
                    binding.rightpart.setImageResource(R.drawable.blonderightpart)
                    binding.leftpart.setImageResource(R.drawable.blondeleftpart)
                    binding.middlepart.setImageResource(R.drawable.blondemiddlepart)
                    binding.longrightpart.setImageResource(R.drawable.longblonderightpart)
                    binding.pigtails.setImageResource(R.drawable.blondepigtails)
                    binding.longmiddlepart.setImageResource(R.drawable.longblondemiddlepart)
                }
                else if(which == 3){
                    binding.rightpart.setImageResource(R.drawable.gingerrightpart)
                    binding.leftpart.setImageResource(R.drawable.gingerleftpart)
                    binding.middlepart.setImageResource(R.drawable.gingermiddlepart)
                    binding.longrightpart.setImageResource(R.drawable.longgingerrightpart)
                    binding.pigtails.setImageResource(R.drawable.gingerpigtails)
                    binding.longmiddlepart.setImageResource(R.drawable.longgingermiddlepart)
                }
            }).create()
        }
        binding.rightpart.setOnClickListener{lamb2(binding.rightpart)}
        binding.leftpart.setOnClickListener{lamb2(binding.leftpart)}
        binding.middlepart.setOnClickListener{lamb2(binding.middlepart)}
        binding.longrightpart.setOnClickListener{lamb2(binding.longrightpart)}
        binding.pigtails.setOnClickListener{lamb2(binding.pigtails)}
        binding.longmiddlepart.setOnClickListener{lamb2(binding.longmiddlepart)}
        return rootView
    }
}
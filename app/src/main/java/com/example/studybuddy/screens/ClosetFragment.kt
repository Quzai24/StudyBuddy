package com.example.studybuddy.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studybuddy.R
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentClosetBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ClosetFragment : Fragment() {
    private var _binding : FragmentClosetBinding? = null
    private val binding get() =_binding!!
    private val viewModel: TaskViewModel by activityViewModels()
    private lateinit var dbRef : DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentClosetBinding.inflate(inflater,container,false)
        val rootView = binding.root
        binding.person.setImageResource(viewModel.getFit(0).overlay)
        binding.eyes.setImageResource(viewModel.getFit(1).overlay)
        binding.hair.setImageResource(viewModel.getFit(2).overlay)
        binding.hat.setImageResource(viewModel.getFit(3).overlay)
        binding.shirtOverlay.setImageResource(viewModel.getFit(4).overlay)
        binding.jacketOverlay.setImageResource(viewModel.getFit(5).overlay)
        var skin = viewModel.getFit(0)
        var eye = viewModel.getFit(1)
        var hair = viewModel.getFit(2)
        var haircolor = 0
        val lamb: (View, Int)-> Unit = { _, num->
            binding.person.setImageResource(viewModel.getOutfit(num).outfit)
            skin = viewModel.getOutfit(num)
        }
        binding.personOne.setOnClickListener{lamb(binding.personOne,0)}
        binding.personTwo.setOnClickListener{lamb(binding.personTwo,1)}
        binding.personThree.setOnClickListener{lamb(binding.personThree,2)}
        binding.personFour.setOnClickListener{lamb(binding.personFour,3)}
        val lamb2: (View, Int)-> Unit = { _, num->
            binding.eyes.setImageResource(viewModel.getOutfit(num).outfit)
            eye = viewModel.getOutfit(num)
        }
        binding.eyesOne.setOnClickListener{lamb2(binding.eyesOne,4)}
        binding.eyesTwo.setOnClickListener{lamb2(binding.eyesTwo,5)}
        binding.eyesThree.setOnClickListener{lamb2(binding.eyesThree,6)}
        binding.eyesFour.setOnClickListener{lamb2(binding.eyesFour,7)}
        binding.haircolor.setOnClickListener{
            activity?.let { AlertDialog.Builder(it).setTitle("Hair Color?").setSingleChoiceItems(R.array.haircolor,-1)
            { _, index ->
                when(index) {
                    0-> {
                        binding.rightpart.setImageResource(R.drawable.blackrightpart)
                        binding.leftpart.setImageResource(R.drawable.blackleftpart)
                        binding.middlepart.setImageResource(R.drawable.blackmiddlepart)
                        binding.longrightpart.setImageResource(R.drawable.longblackrightpart)
                        binding.pigtails.setImageResource(R.drawable.blackpigtails)
                        binding.longmiddlepart.setImageResource(R.drawable.longblackmiddlepart)
                    }
                    1-> {
                        binding.rightpart.setImageResource(R.drawable.brownrightpart)
                        binding.leftpart.setImageResource(R.drawable.brownleftpart)
                        binding.middlepart.setImageResource(R.drawable.brownmiddlepart)
                        binding.longrightpart.setImageResource(R.drawable.longbrownrightpart)
                        binding.pigtails.setImageResource(R.drawable.brownpigtails)
                        binding.longmiddlepart.setImageResource(R.drawable.longbrownmiddlepart)
                    }
                    2-> {
                        binding.rightpart.setImageResource(R.drawable.blonderightpart)
                        binding.leftpart.setImageResource(R.drawable.blondeleftpart)
                        binding.middlepart.setImageResource(R.drawable.blondemiddlepart)
                        binding.longrightpart.setImageResource(R.drawable.longblonderightpart)
                        binding.pigtails.setImageResource(R.drawable.blondepigtails)
                        binding.longmiddlepart.setImageResource(R.drawable.longblondemiddlepart)
                    }
                    3-> {
                        binding.rightpart.setImageResource(R.drawable.gingerrightpart)
                        binding.leftpart.setImageResource(R.drawable.gingerleftpart)
                        binding.middlepart.setImageResource(R.drawable.gingermiddlepart)
                        binding.longrightpart.setImageResource(R.drawable.longgingerrightpart)
                        binding.pigtails.setImageResource(R.drawable.gingerpigtails)
                        binding.longmiddlepart.setImageResource(R.drawable.longgingermiddlepart)
                    } }
                haircolor = index
            }.setPositiveButton("OK") { _, _ -> }.create().show()
        }?: throw IllegalStateException("Exception!! Activity is null") }
        val lamb3: (View, Int)-> Unit = { _, num->
            binding.hair.setImageResource(viewModel.getOutfit(num).overlay)
            hair = viewModel.getOutfit(num)
        }
        binding.rightpart.setOnClickListener{lamb3(binding.rightpart,8+haircolor)}
        binding.leftpart.setOnClickListener{lamb3(binding.leftpart,12+haircolor)}
        binding.middlepart.setOnClickListener{lamb3(binding.middlepart,16+haircolor)}
        binding.longrightpart.setOnClickListener{lamb3(binding.longrightpart,20+haircolor)}
        binding.pigtails.setOnClickListener{lamb3(binding.pigtails,24+haircolor)}
        binding.longmiddlepart.setOnClickListener{lamb3(binding.longmiddlepart,28+haircolor)}
        binding.check.setOnClickListener{
            dbRef = FirebaseDatabase.getInstance().getReference("Profile")
            dbRef.child("Name").setValue(binding.enterName.text.toString())
            dbRef.child("SkinColor").setValue(skin)
            dbRef.child("EyeColor").setValue(eye)
            dbRef.child("Hair").setValue(hair)
            viewModel.name = binding.enterName.text.toString()
            viewModel.setFit(skin,0)
            viewModel.setFit(eye,1)
            viewModel.setFit(hair,2)
            rootView.findNavController().navigateUp()
        }
        return rootView
    }
}
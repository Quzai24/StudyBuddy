package com.example.studybuddy.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studybuddy.objects.Achievement
import com.example.studybuddy.recycle.ShopAdapter
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() =_binding!!
    val list = mutableListOf<List<Achievement>>()
    private val viewModel: TaskViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.profilepicture.setImageResource(viewModel.getFit(0).overlay)
        binding.eye.setImageResource(viewModel.getFit(1).overlay)
        binding.hat.setImageResource(viewModel.getFit(3).overlay)
        binding.shirtOverlay.setImageResource(viewModel.getFit(4).overlay)
        binding.jacketOverlay.setImageResource(viewModel.getFit(5).overlay)
        binding.name.text = viewModel.name

        viewModel.achievementList.value!!.forEachIndexed { index, achievement ->
            if ((index + 1) % 3 == 0) {
                val achievementList = mutableListOf<Achievement>()
                achievementList.add(viewModel.achievementList.value!![index - 2])
                achievementList.add(viewModel.achievementList.value!![index - 1])
                achievementList.add(achievement)
                var isin = false
                for (row in list)
                    if (row == achievementList)
                        isin = true
                if (!isin)
                    list.add(achievementList)
            }
        }

            val mAdapter = ShopAdapter(list,viewModel)
            binding.customizationRecyclerView.adapter = mAdapter

        val lamb: (View)-> Unit = { binding.root.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToPersonalFragment()) }
        binding.profilepicture.setOnClickListener(lamb)
        binding.eye.setOnClickListener(lamb)
        binding.shirtOverlay.setOnClickListener(lamb)
        binding.jacketOverlay.setOnClickListener(lamb)
        binding.back.setOnClickListener{
            rootView.findNavController().navigateUp()
        }
        return rootView
    }
}

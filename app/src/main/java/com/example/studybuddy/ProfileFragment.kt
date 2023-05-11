package com.example.studybuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
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
        binding.name.text = viewModel.name

        if(viewModel.stop) {
            viewModel.achievementList.value!!.forEachIndexed { index, achievement ->
                if ((index + 1) % 3 == 0) {
                    val achievementList = mutableListOf<Achievement>()
                    achievementList.add(viewModel.achievementList.value!![index - 2])
                    achievementList.add(viewModel.achievementList.value!![index - 1])
                    achievementList.add(achievement)
//                    for(row in list)
//                        if(row!=achievementList)
                    list.add(achievementList)
                }
            }
        }
            val mAdapter = ShopAdapter(list)
            binding.customizationRecyclerView.adapter = mAdapter

        val lamb: (View)-> Unit = {
            val action = ProfileFragmentDirections.actionProfileFragmentToPersonalFragment()
            binding.root.findNavController().navigate(action) }
        binding.profilepicture.setOnClickListener(lamb)
        binding.eye.setOnClickListener(lamb)
        binding.shirtOverlay.setOnClickListener(lamb)
        binding.jacketOverlay.setOnClickListener(lamb)
        binding.imageView.setOnClickListener{
            viewModel.stop=true
            rootView.findNavController().navigateUp()
        }
        return rootView
    }
}

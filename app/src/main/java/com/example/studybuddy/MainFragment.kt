package com.example.studybuddy

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.studybuddy.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() =_binding!!
    private val tasks = listOf(Task("Do the Dishes", listOf(4,30), listOf("Saturday","Sunday")),Task("Take out the Trash", listOf(8,0), listOf("Thursday")))
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        val rootView = binding.root

        setHasOptionsMenu(true)

        val mAdapter = TaskAdapter(tasks)
        binding.recyclerview.adapter = mAdapter

        binding.profile.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToProfileFragment()
            binding.root.findNavController().navigate(action)
        }
        binding.study.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToStudyFragment()
            binding.root.findNavController().navigate(action)
        }
        binding.add.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToAddFragment()
            binding.root.findNavController().navigate(action)
        }
        binding.settings.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToSettingsFragment()
            binding.root.findNavController().navigate(action)
        }
        binding.achivement.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToAchievementFragment()
            binding.root.findNavController().navigate(action)
        }
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}
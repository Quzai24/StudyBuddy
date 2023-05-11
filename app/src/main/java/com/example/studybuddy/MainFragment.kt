package com.example.studybuddy

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.studybuddy.databinding.FragmentMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() =_binding!!
    private val viewModel: TaskViewModel by activityViewModels()
    private lateinit var dbRef : DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root

        dbRef = Firebase.database.reference

        setHasOptionsMenu(true)

        viewModel.tasks.observe(viewLifecycleOwner) {
            val mAdapter = TaskAdapter(it,requireContext(),viewModel)
            binding.recyclerview.adapter = mAdapter
        }

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

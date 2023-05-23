package com.example.studybuddy.screens

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.studybuddy.R
import com.example.studybuddy.recycle.TaskAdapter
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentMainBinding
import com.example.studybuddy.objects.Task
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() =_binding!!
    private val viewModel: TaskViewModel by activityViewModels()
    private lateinit var dbRef : DatabaseReference
    private lateinit var settingsdbRef : DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root

        settingsdbRef = FirebaseDatabase.getInstance().getReference("Settings")
        settingsdbRef.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("Dark Mode").getValue<Boolean>()!!) {
                        viewModel.darkMode = true
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    if(snapshot.child("Repeat Alarms").getValue<Boolean>()!!){ viewModel.repeatAlarms = true }
                    if(snapshot.child("Study Notifications").getValue<Boolean>()!!){ viewModel.studyNotifications = true }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        setHasOptionsMenu(true)

        getAlarms()

        binding.profile.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToProfileFragment()
            binding.root.findNavController().navigate(action)
        }
        binding.study.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToStudyFragment()
            binding.root.findNavController().navigate(action)
        }
        binding.add.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToAddFragment("")
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

    private fun getAlarms(){
        dbRef = FirebaseDatabase.getInstance().getReference("Alarms")
        dbRef.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(alarmSnapshot in snapshot.children){
                        val alarm = alarmSnapshot.getValue(Task::class.java)
                        if(!viewModel.isIn(alarm!!))
                            viewModel.addTask(alarm)
                    }
                    val mAdapter = TaskAdapter(viewModel.tasks.value!!,requireContext(),viewModel)
                    binding.recyclerview.adapter = mAdapter
                }
            }override fun onCancelled(error: DatabaseError) {} })
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

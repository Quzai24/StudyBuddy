package com.example.studybuddy.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentSettingsBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() =_binding!!
    private val viewModel: TaskViewModel by activityViewModels()
    private lateinit var dbRef : DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val rootView = binding.root

        dbRef = FirebaseDatabase.getInstance().getReference("Settings")
        dbRef.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) { if(snapshot.exists()){ binding.darkMode.isChecked = viewModel.darkMode } }
            override fun onCancelled(error: DatabaseError) {} })

        binding.darkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){ AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
            else{ AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
            dbRef.child("Dark Mode").setValue(isChecked)
            viewModel.darkMode = isChecked
        }

        binding.notif.setOnCheckedChangeListener { buttonView, isChecked ->
            dbRef.child("Study Notifications").setValue(isChecked)
            viewModel.repeatAlarms = isChecked
        }

        binding.rep.setOnCheckedChangeListener { buttonView, isChecked ->
            dbRef.child("Repeat Alarms").setValue(isChecked)
            viewModel.repeatAlarms = isChecked
        }

        return rootView
    }
}
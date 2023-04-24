package com.example.studybuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.studybuddy.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private var _binding : FragmentAddBinding? = null
    private val binding get() =_binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBinding.inflate(inflater,container,false)
        val rootView = binding.root

        val hourArrayAdapter = ArrayAdapter.createFromResource(requireContext(),R.array.hour,android.R.layout.simple_spinner_item)
        hourArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.hour.adapter = hourArrayAdapter
        binding.hour.onItemSelectedListener = object: AdapterView.OnItemSelectedListener { var selectedhour = ""
            override fun onItemSelected(adapterView: AdapterView<*>, childView: View?, position: Int, itemId: Long) {
                selectedhour = adapterView.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {  }
        }

        val minuteArrayAdapter = ArrayAdapter.createFromResource(requireContext(),R.array.minute,android.R.layout.simple_spinner_item)
        minuteArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.minute.adapter = minuteArrayAdapter
        binding.minute.onItemSelectedListener = object: AdapterView.OnItemSelectedListener { var selectedmin = ""
            override fun onItemSelected(adapterView: AdapterView<*>, childView: View?, position: Int, itemId: Long) {
                selectedmin = adapterView.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {  }
        }

        val amArrayAdapter = ArrayAdapter.createFromResource(requireContext(),R.array.time,android.R.layout.simple_spinner_item)
        amArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.am.adapter = amArrayAdapter
        binding.am.onItemSelectedListener = object: AdapterView.OnItemSelectedListener { var selectedtime = ""
            override fun onItemSelected(adapterView: AdapterView<*>, childView: View?, position: Int, itemId: Long) {
                selectedtime = adapterView.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {  }
        }

        return rootView
    }
}
package com.example.studybuddy.screens

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TimePicker
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studybuddy.objects.Task
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("hh:mm a", Locale.US)
    private var _binding : FragmentAddBinding? = null
    private val binding get() =_binding!!
    private var hourOfDay = 0
    private var minute = 0
    private var rep = 5
    private lateinit var dbRef : DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val rootView = binding.root
        //time picker
        binding.whattime.setOnClickListener { TimePickerDialog(requireContext(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show() }
        //repeat time
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rep = binding.seekBar.progress+5 }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {binding.repeat.text="$rep mins"}
        })
        //Setting values
        binding.confirm.setOnClickListener{
            val name = binding.enterTask.text.toString()
            val days = getWeek()
            var weekTwoWeekNo = 2
            if(binding.weekly.isChecked){weekTwoWeekNo=0}
            if(binding.biweekly.isChecked){weekTwoWeekNo=1}

            dbRef = FirebaseDatabase.getInstance().getReference("Alarms")
            val task = Task(name, listOf(hourOfDay,minute),days,weekTwoWeekNo,false,rep)
            dbRef.child(name).setValue(task)

            rootView.findNavController().navigateUp()
        }

        return rootView
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY,hourOfDay)
            set(Calendar.MINUTE,minute)
        }
        this.hourOfDay = hourOfDay
        this.minute = minute
        displayFormattedDay(calendar.timeInMillis)
    }
    private fun displayFormattedDay(timestamp:Long){
        binding.whattime.text=formatter.format(timestamp)
    }
    private fun getWeek(): List<String>{
        val daysOfTheWeek = mutableListOf<String>()
        if(binding.sunday.isChecked){daysOfTheWeek.add("Sunday")}
        if(binding.monday.isChecked){daysOfTheWeek.add("Monday")}
        if(binding.tuesday.isChecked){daysOfTheWeek.add("Tuesday")}
        if(binding.wednesday.isChecked){daysOfTheWeek.add("Wednesday")}
        if(binding.thursday.isChecked){daysOfTheWeek.add("Thursday")}
        if(binding.friday.isChecked){daysOfTheWeek.add("Friday")}
        if(binding.saturday.isChecked){daysOfTheWeek.add("Saturday")}
        return daysOfTheWeek
    }
}
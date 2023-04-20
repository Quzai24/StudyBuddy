package com.example.studybuddy

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.studybuddy.databinding.ListItemLayoutBinding

class TaskViewHolder(val binding : ListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentTask : Task
    init {
        binding.root.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToAlarmFragment()
            binding.root.findNavController().navigate(action)
        }
    }
    fun bindTask (task: Task){
        currentTask = task
        binding.task.text = currentTask.task
        val time = "${currentTask.time[0]}: ${currentTask.time[1]}"
        binding.time.text = time
        var days = ""
        for(day in currentTask.days){
            if(day != currentTask.days[currentTask.days.size-1])
                days += "$day, "
            else
                days += "& $day"
        }
        binding.days.text = days
    }
}
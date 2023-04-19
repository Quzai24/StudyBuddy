package com.example.studybuddy

import androidx.recyclerview.widget.RecyclerView
import com.example.studybuddy.databinding.ListItemLayoutBinding

class TaskViewHolder(val binding : ListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentTask : Task
    fun bindTask (task: Task){
        currentTask = task
        binding.task.text = currentTask.task
        val time = "${currentTask.time[0]}: ${currentTask.time[1]}"
        binding.time.text = time

    }
}
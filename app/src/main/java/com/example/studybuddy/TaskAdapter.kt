package com.example.studybuddy

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.studybuddy.databinding.ListItemLayoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TaskAdapter(val taskList: List<Task>, val context: Context, val viewModel: TaskViewModel) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(val binding : ListItemLayoutBinding, val context: Context,val viewModel: TaskViewModel) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentTask : Task
        init {
            binding.root.setOnClickListener{
                val action = MainFragmentDirections.actionMainFragmentToAlarmFragment()
                binding.root.findNavController().navigate(action)
            }
            binding.delete.setOnClickListener{
                MaterialAlertDialogBuilder(context).setTitle("Delete or Dismiss?")
                    .setMessage("Delete or Dismiss, ${currentTask.task}")
                    .setPositiveButton("Dismiss") { dialog, which ->
                        currentTask.isDismissed=true
                        binding.clock.setImageResource(R.drawable.hollow_time)
                        binding.dismiss.text="Dismissed"
                    }
                    .setNegativeButton("Delete") { dialog, which ->
                        viewModel.deleteTask(currentTask)
                        notifyItemRemoved(this.position)
                    }
                    .setNeutralButton("Nahhh"){dialog, which ->
                    }.show()
            }
        }
        fun bindTask (task: Task){
            currentTask = task
            binding.task.text = currentTask.task
            val hour = if(currentTask.time[0]%12==0){"12"}else{currentTask.time[0]%12}
            val min = if(currentTask.time[1]<10){"0${currentTask.time[1]}"}else{currentTask.time[1]}
            val ampm = if(currentTask.time[0]>=12){"pm"}else{"am"}
            val time = "$hour: $min $ampm"
            binding.time.text = time
            var days = ""
            if (currentTask.days.size != 1)
                for (day in currentTask.days) {
                    if (day != currentTask.days[currentTask.days.size - 1])
                        days += "$day, "
                    else
                        days += "& $day"
                }
            else
                days = currentTask.days[0]
            binding.days.text = days
            if(currentTask.weekly==0){binding.week.text= "Weekly"}
            else if(currentTask.weekly==1){binding.week.text="Bi-Weekly"}
            if(currentTask.isDismissed){
                binding.dismiss.text="Dismissed"
                binding.clock.setImageResource(R.drawable.hollow_time)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding,context,viewModel)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentCourse = taskList[position]
        holder.bindTask(currentCourse)
    }
    override fun getItemCount(): Int {
        return taskList.size
    }
}
package com.example.studybuddy.recycle

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.studybuddy.R
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.ListItemLayoutBinding
import com.example.studybuddy.objects.Task
import com.example.studybuddy.screens.MainFragmentDirections
import com.google.firebase.database.FirebaseDatabase

class TaskAdapter(private val taskList: List<Task>, val context: Context, val viewModel: TaskViewModel) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(val binding : ListItemLayoutBinding, val context: Context,val viewModel: TaskViewModel) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentTask : Task
        init {
            binding.root.setOnClickListener{
                val action = MainFragmentDirections.actionMainFragmentToAddFragment(currentTask.task)
                binding.root.findNavController().navigate(action)
            }
            binding.delete.setOnClickListener{
                AlertDialog.Builder(context)
                    .setTitle("Delete ${currentTask.task}?")
                    .setMessage("Are you sure you want to delete ${currentTask.task}?")
                    .setPositiveButton("Delete") { _, _ ->
                        val dbRef = FirebaseDatabase.getInstance().getReference("Alarms")
                        dbRef.child(currentTask.task).removeValue()
                        viewModel.deleteTask(currentTask)
                        notifyItemRemoved(this.position)
                    }
                    .setNeutralButton("Nahhh"){ _, _ -> }.show()
                binding.delete.isChecked = false
            }
        }
        @SuppressLint("SetTextI18n")
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
                    days += if (day != currentTask.days[currentTask.days.size - 1]) "$day, "
                    else
                        "& $day"
                }
            else
                days = currentTask.days[0]
            binding.days.text = days
            if(currentTask.weekly==0){binding.week.text= "Weekly"}
            else if(currentTask.weekly==1){binding.week.text="Bi-Weekly"}
            val hours = currentTask.time[0]%12
            val minute = currentTask.time[1]/5
            when(hours){
                0-> binding.oclock.setImageResource(R.drawable.twelve)
                1-> binding.oclock.setImageResource(R.drawable.one)
                2-> binding.oclock.setImageResource(R.drawable.two)
                3-> binding.oclock.setImageResource(R.drawable.three)
                4-> binding.oclock.setImageResource(R.drawable.four)
                5-> binding.oclock.setImageResource(R.drawable.five)
                6-> binding.oclock.setImageResource(R.drawable.six)
                7-> binding.oclock.setImageResource(R.drawable.seven)
                8-> binding.oclock.setImageResource(R.drawable.eight)
                9-> binding.oclock.setImageResource(R.drawable.nine)
                10-> binding.oclock.setImageResource(R.drawable.ten)
                11-> binding.oclock.setImageResource(R.drawable.eleven)
            }
            when(minute){
                0-> binding.minute.setImageResource(R.drawable.zerominute)
                1-> binding.minute.setImageResource(R.drawable.fiveminute)
                2-> binding.minute.setImageResource(R.drawable.tenminute)
                3-> binding.minute.setImageResource(R.drawable.fifteenminute)
                4-> binding.minute.setImageResource(R.drawable.twentyminute)
                5-> binding.minute.setImageResource(R.drawable.twentyfiveminute)
                6-> binding.minute.setImageResource(R.drawable.thirtyminute)
                7-> binding.minute.setImageResource(R.drawable.thirtyfiveminute)
                8-> binding.minute.setImageResource(R.drawable.fourtyminute)
                9-> binding.minute.setImageResource(R.drawable.fourtyfiveminute)
                10-> binding.minute.setImageResource(R.drawable.fiftyminute)
                11-> binding.minute.setImageResource(R.drawable.fiftyfiveminute)
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
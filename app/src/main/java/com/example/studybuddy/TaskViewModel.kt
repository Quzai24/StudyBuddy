package com.example.studybuddy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel: ViewModel() {
    private val _tasks: MutableLiveData<MutableList<Task>> = MutableLiveData(mutableListOf(Task("Do the Dishes", listOf(16,30), listOf("Saturday","Sunday"),1,false,10),Task("Take out the trash", listOf(12,0), listOf("Thursday"),0,false,20)))
    val tasks: LiveData<MutableList<Task>>
        get()= _tasks
    fun addTask(task:Task){
        _tasks.value?.add(task)
    }
    fun deleteTask(task: Task){
        _tasks.value?.remove(task)
    }
}
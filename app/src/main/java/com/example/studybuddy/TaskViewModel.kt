package com.example.studybuddy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel: ViewModel() {
    private var _tasks: MutableLiveData<List<Task>> = MutableLiveData(listOf(
        Task("Do the Dishes", listOf(4,30), listOf("Saturday","Sunday")),Task("Take out the trash", listOf(8,0), listOf("Thursday"))))
    val tasks: LiveData<List<Task>>
        get()= _tasks
}
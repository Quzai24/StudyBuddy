package com.example.studybuddy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studybuddy.objects.Achievement
import com.example.studybuddy.objects.Outfit
import com.example.studybuddy.objects.Task

class TaskViewModel: ViewModel() {
    private val outfits = listOf(
        Outfit(R.drawable.personone,R.drawable.personone,"",""),
        Outfit(R.drawable.persontwo,R.drawable.persontwo,"",""),
        Outfit(R.drawable.personthree,R.drawable.personthree,"",""),
        Outfit(R.drawable.personfour,R.drawable.personfour,"",""),
        Outfit(R.drawable.eyesone,R.drawable.eyesone,"",""),
        Outfit(R.drawable.eyestwo,R.drawable.eyestwo,"",""),
        Outfit(R.drawable.eyesthree,R.drawable.eyesthree,"",""),
        Outfit(R.drawable.eyesfour,R.drawable.eyesfour,"",""),
        Outfit(R.drawable.hatone,R.drawable.hatoverlayone,"greenHat","hat"),
        Outfit(R.drawable.shirtone,R.drawable.shirtoverlayone,"magicShirt","shirt"),
        Outfit(R.drawable.jacketone,R.drawable.jacketoverlayone,"redJacket","jacket"))
    private val _tasks: MutableLiveData<MutableList<Task>> = MutableLiveData(mutableListOf(
        Task("Do the Dishes", listOf(16,30), listOf("Saturday","Sunday"),1,false,10),
        Task("Take out the trash", listOf(12,0), listOf("Thursday"),0,false,20)
    ))
    val tasks: LiveData<MutableList<Task>>
        get()= _tasks
    private val _achievementList: MutableLiveData<List<Achievement>> = MutableLiveData(listOf(
        Achievement("Getting Started","Complete 1 Task",false,outfits[8]),
        Achievement("It's a Start","Complete 10 Task",false,outfits[9]),
        Achievement("Progress","Complete 20 Task",false,outfits[10])))
    val achievementList: LiveData<List<Achievement>>
        get() = _achievementList
    private val _complete: MutableLiveData<Int> = MutableLiveData(0)
    val complete: LiveData<Int>
        get()= _complete
    var name = "Study Buddy"
    private val fit = mutableListOf(outfits[0],outfits[4], Outfit(0,0,"","hair"), Outfit(0,0,"","hat"), Outfit(0,0,"","shirt"), Outfit(0,0,"","jacket"))
    var stop = true
    fun setFit (fit: Outfit, num: Int){
        this.fit[num] = fit
    }
    fun getFit(num: Int): Outfit {
        return fit[num]
    }
    fun getOutfit(num: Int): Outfit {
        return outfits[num]
    }
    fun achievmentGet(){

    }
    fun addTask(task: Task){
        _tasks.value?.add(task)
    }
    fun deleteTask(task: Task){
        _tasks.value?.remove(task)
    }
}
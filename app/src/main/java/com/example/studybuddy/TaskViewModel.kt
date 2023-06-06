package com.example.studybuddy

import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studybuddy.objects.Achievement
import com.example.studybuddy.objects.Alarms
import com.example.studybuddy.objects.Outfit
import com.example.studybuddy.objects.Task

class TaskViewModel: ViewModel() {
    private val outfits = listOf(
        Outfit(R.drawable.personone,R.drawable.personone,"",""), Outfit(R.drawable.persontwo,R.drawable.persontwo,"",""), Outfit(R.drawable.personthree,R.drawable.personthree,"",""), Outfit(R.drawable.personfour,R.drawable.personfour,"",""),
        Outfit(R.drawable.eyesone,R.drawable.eyesone,"",""), Outfit(R.drawable.eyestwo,R.drawable.eyestwo,"",""), Outfit(R.drawable.eyesthree,R.drawable.eyesthree,"",""), Outfit(R.drawable.eyesfour,R.drawable.eyesfour,"",""),
        Outfit(R.drawable.blackrightpart,R.drawable.blackrightpartoverlay,"",""),Outfit(R.drawable.brownrightpart,R.drawable.brownrightpartoverlay,"",""),Outfit(R.drawable.blonderightpart,R.drawable.blonderightpartoverlay,"",""),Outfit(R.drawable.gingerrightpart,R.drawable.gingerrightpartoverlay,"",""),
        Outfit(R.drawable.blackleftpart,R.drawable.blackleftpartoverlay,"",""),Outfit(R.drawable.brownleftpart,R.drawable.brownleftpartoverlay,"",""),Outfit(R.drawable.blondeleftpart,R.drawable.blondeleftpartoverlay,"",""),Outfit(R.drawable.gingerleftpart,R.drawable.gingerleftpartoverlay,"",""),
        Outfit(R.drawable.blackmiddlepart,R.drawable.blackmiddlepartoverlay,"",""),Outfit(R.drawable.brownmiddlepart,R.drawable.brownmiddlepartoverlay,"",""),Outfit(R.drawable.blondemiddlepart,R.drawable.blondemiddlepartoverlay,"",""),Outfit(R.drawable.gingermiddlepart,R.drawable.gingermiddlepartoverlay,"",""),
        Outfit(R.drawable.longblackrightpart,R.drawable.longblackrightpartoverlay,"",""),Outfit(R.drawable.longbrownrightpart,R.drawable.longbrownrightpartoverlay,"",""),Outfit(R.drawable.longblonderightpart,R.drawable.longblonderightpartoverlay,"",""),Outfit(R.drawable.longgingerrightpart,R.drawable.longgingerrightpartoverlay,"",""),
        Outfit(R.drawable.blackpigtails,R.drawable.blackpigtailsoverlay,"",""),Outfit(R.drawable.brownpigtails,R.drawable.brownpigtailsoverlay,"",""),Outfit(R.drawable.blondepigtails,R.drawable.blondepigtailsoverlay,"",""),Outfit(R.drawable.gingerpigtails,R.drawable.gingerpigtailsoverlay,"",""),
        Outfit(R.drawable.longblackmiddlepart,R.drawable.longblackmiddlepartoverlay,"",""),Outfit(R.drawable.longbrownmiddlepart,R.drawable.longbrownmiddlepartoverlay,"",""),Outfit(R.drawable.longblondemiddlepart,R.drawable.longblondemiddlepartoverlay,"",""),Outfit(R.drawable.longgingermiddlepart,R.drawable.longgingermiddlepartoverlay,"",""),
        Outfit(R.drawable.redcap,R.drawable.redcapoverlay,"greenHat","hat"),
        Outfit(R.drawable.shirtone,R.drawable.shirtoverlayone,"magicShirt","shirt"),
        Outfit(R.drawable.jacketone,R.drawable.jacketoverlayone,"redJacket","jacket"),
        Outfit(R.drawable.pizzashirt,R.drawable.pizzashirtoverlay,"pizzashirt", "shirt"),
        Outfit(R.drawable.linkhat,R.drawable.linkhatoverlay,"linkhat", "hat"),
        Outfit(R.drawable.plaidshirt,R.drawable.plaidshirtoverlay,"flannel", "jacket"))

    private val _tasks: MutableLiveData<MutableList<Task>> = MutableLiveData(mutableListOf())
    val tasks: LiveData<MutableList<Task>>
        get()= _tasks

    val alarms = mutableListOf<Alarms>()

    private val _achievements: MutableLiveData<List<Achievement>> = MutableLiveData(listOf(
        Achievement("Getting Started","Complete 1 Task",true,outfits[32], 1, false),
        Achievement("It's a Start","Complete 10 Task",true,outfits[33], 10, false),
        Achievement("Progress","Complete 20 Task",false,outfits[34], 20, false),
        Achievement("Rise and Grind", "Complete 5 Study Sessions", true, outfits[35],5,false),
        Achievement("Getting Good", "Complete 15 Study Sessions", true, outfits[36],15,false),
        Achievement("A Star Student", "Complete 30 Study Sessions", false, outfits[37],30,false)))
    val achievements: LiveData<List<Achievement>>
        get() = _achievements

    private val _taskAchievements: MutableLiveData<List<Achievement>> = MutableLiveData(listOf(
        Achievement("Getting Started","Complete 1 Task",true,outfits[32], 1, false),
        Achievement("It's a Start","Complete 10 Task",true,outfits[33], 10, false),
        Achievement("Progress","Complete 20 Task",true,outfits[34], 20, false)))
    private val taskAchievements: LiveData<List<Achievement>>
        get() = _taskAchievements

    private val _studyAchievements: MutableLiveData<List<Achievement>> = MutableLiveData(listOf(
        Achievement("Rise and Grind", "Complete 5 Study Sessions", true, outfits[35],5,false),
        Achievement("Getting Good", "Complete 15 Study Sessions", true, outfits[36],15,false),
        Achievement("A Star Student", "Complete 30 Study Sessions", true, outfits[37],30,false)))
    private val studyAchievements: LiveData<List<Achievement>>
        get() = _studyAchievements

    var completeTasks = 0
    var completeStudySession = 0

    var name = "Study Buddy"

    private val fit = mutableListOf(outfits[0],outfits[4], outfits[8], Outfit(0,0,"","hat"), Outfit(0,0,"","shirt"), Outfit(0,0,"","jacket"))

    var darkMode = false
    var repeatAlarms = false

    fun setFit (fit: Outfit, num: Int){
        this.fit[num] = fit
    }
    fun getFit(num: Int): Outfit {
        return fit[num]
    }
    fun getOutfit(num: Int): Outfit {
        return outfits[num]
    }
    fun achievmentGet(taskOrStudy: Boolean, num: Int){
        if (taskOrStudy) {
            completeTasks += num
            for(i in taskAchievements.value!!)
                if(i.num<=completeTasks&&!i.unlocked) {
                    i.unlocked = true
                    unlockAchievement(i)
                }
        }
        else {
            completeStudySession += num
            for(i in studyAchievements.value!!)
                if(i.num<=completeStudySession&&!i.unlocked) {
                    i.unlocked == true
                    unlockAchievement(i)
                }
        }
    }
    fun addTask(task: Task){
        if(!repeatAlarms) {task.repeat=0}
        _tasks.value?.add(task)
    }
    fun deleteTask(task: Task){
        _tasks.value?.remove(task)
    }
    fun setTask(name: String,alarm: Task){
        _tasks.value?.forEachIndexed { index, task ->
            if (name == task.task) {
                _tasks.value?.set(index, alarm)
                return
            }
        }
    }

    fun removeAlarm(activity : Activity?,name : String){
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        for(i in alarms)
            if(i.name==name) {
                for (j in i.alarms)
                    alarmManager.cancel(j)
                alarms.remove(i)
            }
    }

    fun isIn(task: String): Boolean{
        for(t in _tasks.value!!)
            if(t.task == task)
                return true
        return false
    }

    private fun unlockAchievement(achievement: Achievement){
        for(i in achievements.value!!)
            if(i.name == achievement.name)
                i.unlocked = true
    }
}
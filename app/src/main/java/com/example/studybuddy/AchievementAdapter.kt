package com.example.studybuddy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studybuddy.databinding.ListAchievementLayoutBinding

class AchievementAdapter (val achievementList: List<Achievement>): RecyclerView.Adapter<AchievementViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val binding = ListAchievementLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AchievementViewHolder(binding)
    }
    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val currentAchievement = achievementList[position]
        holder.bindAchievement(currentAchievement)
    }
    override fun getItemCount(): Int {
        return achievementList.size
    }
}
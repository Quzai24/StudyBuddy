package com.example.studybuddy.recycle

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import androidx.recyclerview.widget.RecyclerView
import com.example.studybuddy.objects.Achievement
import com.example.studybuddy.databinding.ListAchievementLayoutBinding


class AchievementViewHolder(val binding: ListAchievementLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentAchievement: Achievement
    fun bindAchievement(achievement: Achievement){
        currentAchievement = achievement
        binding.achievmentName.text = currentAchievement.name
        binding.description.text = currentAchievement.description
        if(!currentAchievement.unlocked){
            val grayscaleMatrix = ColorMatrix()
            grayscaleMatrix.setSaturation(0f)
            binding.trophy.setColorFilter(ColorMatrixColorFilter(grayscaleMatrix))
        }
    }
}
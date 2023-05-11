package com.example.studybuddy

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.studybuddy.databinding.ListShopLayoutBinding

class ShopViewHolder(val binding: ListShopLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentAchievementList: List<Achievement>

    init{
        val lamb: (View)-> Unit = {view->

        }
        binding.dripone.setOnClickListener(lamb)
    }

    fun bindShop(achievementList: List<Achievement>){
        currentAchievementList=achievementList
        binding.dripone.setImageResource(currentAchievementList[0].outfit.outfit)
        binding.driptwo.setImageResource(currentAchievementList[1].outfit.outfit)
        binding.dripthree.setImageResource(currentAchievementList[2].outfit.outfit)
        val grayscaleMatrix = ColorMatrix()
        grayscaleMatrix.setSaturation(0f)
        if(!currentAchievementList[0].unlocked){
            binding.dripone.setColorFilter(ColorMatrixColorFilter(grayscaleMatrix))
            binding.dripone.isClickable = false }
        if(!currentAchievementList[1].unlocked){
            binding.driptwo.setColorFilter(ColorMatrixColorFilter(grayscaleMatrix))
            binding.driptwo.isClickable = false }
        if(!currentAchievementList[2].unlocked){
            binding.dripthree.setColorFilter(ColorMatrixColorFilter(grayscaleMatrix))
            binding.dripthree.isClickable = false }
        binding.nameone.text = currentAchievementList[0].name
        binding.nametwo.text = currentAchievementList[1].name
        binding.namethree.text = currentAchievementList[2].name
        binding.typeone.text = currentAchievementList[0].outfit.name
        binding.typetwo.text = currentAchievementList[1].outfit.name
        binding.typethree.text = currentAchievementList[2].outfit.name
    }
}
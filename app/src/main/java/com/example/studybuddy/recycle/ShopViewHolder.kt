package com.example.studybuddy.recycle

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.objects.Achievement
import com.example.studybuddy.databinding.ListShopLayoutBinding

class ShopViewHolder(val binding: ListShopLayoutBinding, val viewModel: TaskViewModel): RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentAchievementList: List<Achievement>

    init{
        val lamb: (View, Int)-> Unit = {view, num->
            if(currentAchievementList[num].unlocked){
                if(currentAchievementList[num].outfit.type == "hat")
                    viewModel.setFit(currentAchievementList[num].outfit,3)
                else if (currentAchievementList[num].outfit.type == "shirt")
                    viewModel.setFit(currentAchievementList[num].outfit,4)
                else if (currentAchievementList[num].outfit.type == "jacket")
                    viewModel.setFit(currentAchievementList[num].outfit,5) }}
        binding.dripone.setOnClickListener { lamb(binding.dripone,0) }
        binding.driptwo.setOnClickListener{ lamb(binding.driptwo,1) }
        binding.dripthree.setOnClickListener{ lamb(binding.dripthree,2) }
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
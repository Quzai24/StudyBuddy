package com.example.studybuddy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studybuddy.databinding.ListShopLayoutBinding

class ShopAdapter(val shoppingList: List<List<Achievement>>): RecyclerView.Adapter<ShopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ListShopLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val currentAchievement = shoppingList[position]
        holder.bindShop(currentAchievement)
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }
}
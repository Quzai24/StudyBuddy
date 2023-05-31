//package com.example.studybuddy.objects
//
//import android.app.AlertDialog
//import android.app.Dialog
//import android.content.DialogInterface
//import android.os.Bundle
//import androidx.fragment.app.DialogFragment
//import com.example.studybuddy.R
//import com.example.studybuddy.databinding.FragmentClosetBinding
//
//class MyDialog(val binding: FragmentClosetBinding): DialogFragment() {
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            var checkedIndex = -1
//            AlertDialog.Builder(it).setTitle("Hair Color?").setSingleChoiceItems(R.array.haircolor,-1,
//                DialogInterface.OnClickListener{ dialog, index ->
//                if(index == 0){
//                    binding.rightpart.setImageResource(R.drawable.blackrightpart)
//                    binding.leftpart.setImageResource(R.drawable.blackleftpart)
//                    binding.middlepart.setImageResource(R.drawable.blackmiddlepart)
//                    binding.longrightpart.setImageResource(R.drawable.longblackrightpart)
//                    binding.pigtails.setImageResource(R.drawable.blackpigtails)
//                    binding.longmiddlepart.setImageResource(R.drawable.longblackmiddlepart)
//                }
//                else if(index == 1){
//                    binding.rightpart.setImageResource(R.drawable.brownrightpart)
//                    binding.leftpart.setImageResource(R.drawable.brownleftpart)
//                    binding.middlepart.setImageResource(R.drawable.brownmiddlepart)
//                    binding.longrightpart.setImageResource(R.drawable.longbrownrightpart)
//                    binding.pigtails.setImageResource(R.drawable.brownpigtails)
//                    binding.longmiddlepart.setImageResource(R.drawable.longbrownmiddlepart)
//                }
//                else if(index == 2){
//                    binding.rightpart.setImageResource(R.drawable.blonderightpart)
//                    binding.leftpart.setImageResource(R.drawable.blondeleftpart)
//                    binding.middlepart.setImageResource(R.drawable.blondemiddlepart)
//                    binding.longrightpart.setImageResource(R.drawable.longblonderightpart)
//                    binding.pigtails.setImageResource(R.drawable.blondepigtails)
//                    binding.longmiddlepart.setImageResource(R.drawable.longblondemiddlepart)
//                }
//                else if(index == 3){
//                    binding.rightpart.setImageResource(R.drawable.gingerrightpart)
//                    binding.leftpart.setImageResource(R.drawable.gingerleftpart)
//                    binding.middlepart.setImageResource(R.drawable.gingermiddlepart)
//                    binding.longrightpart.setImageResource(R.drawable.longgingerrightpart)
//                    binding.pigtails.setImageResource(R.drawable.gingerpigtails)
//                    binding.longmiddlepart.setImageResource(R.drawable.longgingermiddlepart)
//                }
//                checkedIndex=index
//            }).setPositiveButton("OK",DialogInterface.OnClickListener{ dialogInterface, i -> }).create()
//        }?: throw IllegalStateException("Exception!! Activity is null")
//    }
//}
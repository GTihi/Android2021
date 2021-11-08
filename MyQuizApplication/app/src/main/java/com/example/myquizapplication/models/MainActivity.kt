package com.example.myquizapplication.models

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myquizapplication.R
import com.example.myquizapplication.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    companion object{
        val questions: MutableList<Questions> = ArrayList() //ArrayList to contain questions
        var myCounter = 0 //counter which helps to check what happens between questions
        var score = 0 //the score shown at the top of the page
        var correctAnswer = 0 //counter of correct answers, it's value is shown at the result page
        var wrongAnswer = 0 //counter of wrong answers, it's value is shown at the result page
        var name = "" //it's value is shown at the result page, which will be the players username
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //the main fragments will be the starting page
        val newFragment = MainFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainActivityLayout, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
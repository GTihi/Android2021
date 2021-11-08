package com.example.quizapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.quizapplication.databinding.ActivityMainBinding
import com.example.quizapplication.questions.Questions


class MainActivity : AppCompatActivity() {
    lateinit var nextButton : Button
    lateinit var aboutButton : Button

    lateinit var userName : EditText
    lateinit var password : EditText
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initialize()

        /*nextButton.setOnClickListener{
            goToNextQuestion()
        }*/
    }

    private fun goToNextQuestion(){
        val newFragment = QuizFragment.newInstance("Something", "Something2")
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.quiz_container, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun initialize(){
        nextButton = binding.nextButton
        aboutButton = binding.aboutButton

        userName = binding.userNameEditText
        password = binding.editPassword
    }

    fun startQuiz(){

    }

    private fun makeQuestions(): List<Questions> {
        val questions: MutableList<Questions> = ArrayList()
        questions.add(Questions("How many military casualties did  the Soviet Union suffer in WW2?",
                Questions.QuestionType.RADIO, arrayOf("400,000", "800,000", "10,000", "11,000,000"), intArrayOf(3)))
        questions.add(Questions(
                "How many different ethnic groups live in Russia?",
                Questions.QuestionType.RADIO, arrayOf("186", "45", "15", "110"), intArrayOf(0)))
        questions.add(Questions(
                "What is the national sport of Russia?",
                "Bandy"))
        questions.add(Questions(
                "Which is a list of Russia's common eaten cuisine?",
                Questions.QuestionType.RADIO, arrayOf("okroshka, shchi, kvass, pelmeni", "tiblitsa, pogaca, xleb, strudla", "broscht, perogies, kapusniak, rosolnk", "stapici, pletenice, kifle, djevreci"), intArrayOf(0)))
        questions.add(Questions(
                "What is the average yearly salary in Russia?",
                Questions.QuestionType.RADIO, arrayOf("$40,000 USD", "$20,000", "$15,000", "$8,0000"), intArrayOf(2)))
        questions.add(Questions("How many theatres are there in Saint Petersburg?",
                Questions.QuestionType.RADIO, arrayOf("55", "400", "180", "23"), intArrayOf(2)))
        questions.add(Questions("Who was Russia's last tsar?",
                Questions.QuestionType.RADIO, arrayOf("Catherine the Great", "Nicholas II", "Alexander III", "Yaroslav I"), intArrayOf(1)))
        questions.add(Questions("What is a popular Russian past time?",
                Questions.QuestionType.RADIO, arrayOf("Cooking", "Going to the movies", "Attending theater events", "the art of sorcery"), intArrayOf(2)))
        questions.add(Questions("What innovations have been invented by Russians",
                Questions.QuestionType.CHECK_BOX, arrayOf("vodka, television, film school, paratrooping", "rollercoaster, yacht club, rebar, beehive frame, AK-47", "radiator, ac transformer, headlamp, vitamins", "electric submarine, periodic table of elements, solar cell, fire fighting foam"), intArrayOf(0, 1, 2, 3)))
        questions.add(Questions("What is Russia's largest industry?",
                Questions.QuestionType.RADIO, arrayOf("Natural Gas", "Oil", "Lumber", "Vodka"), intArrayOf(1)))
        return questions

    }


}
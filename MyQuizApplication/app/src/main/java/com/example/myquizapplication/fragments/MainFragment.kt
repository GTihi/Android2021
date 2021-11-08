package com.example.myquizapplication.fragments

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myquizapplication.models.MainActivity.Companion.correctAnswer
import com.example.myquizapplication.models.MainActivity.Companion.myCounter
import com.example.myquizapplication.models.MainActivity.Companion.name
import com.example.myquizapplication.models.MainActivity.Companion.questions
import com.example.myquizapplication.models.MainActivity.Companion.score
import com.example.myquizapplication.models.MainActivity.Companion.wrongAnswer
import com.example.myquizapplication.models.Questions
import com.example.myquizapplication.R


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    //the buttons
    private lateinit var rulesButton : Button
    private lateinit var startButton: Button

    //the input elements
    private lateinit var userName: EditText
    private lateinit var password: EditText

    //the checkbox
    private lateinit var showPassword : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)

        rulesButton = rootView.findViewById(R.id.rulesButton)
        startButton = rootView.findViewById(R.id.startQuizButton)

        userName = rootView.findViewById(R.id.userNameEditText)
        password = rootView.findViewById(R.id.userPassword)

        showPassword = rootView.findViewById(R.id.showPassword)

        //give information about password - what should contain
        password.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                activity,
                "The password should contain at least 8 characters, one number, one uppercase and one lowercase letter",
                Toast.LENGTH_LONG
            ).show()
        })

        //set up what happens when the "Show password" checkbox is selected or not
        showPassword.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                // show password
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                // hide password
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        })

        //if the rules button is pressed -> we will be directed to the rules page
        rulesButton.setOnClickListener(View.OnClickListener {
            val newFragment = RulesFragment()
            val fragmentManager = fragmentManager
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainFragment, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        })

        //if the start button is pressed
        startButton.setOnClickListener(View.OnClickListener {
            //check if the username is valid -> if something is wrong a message will be shown
            if (!isValidName(userName)) {
                userName.error = "Invalid name - only letters allowed"
            } else if (userName.text.toString().isEmpty()) {
                userName.error = "Empty input - write your name"
            }
            //check if the password is valid -> if something is wrong a message will be shown
            else if (password.text.toString().isEmpty()) {
                password.error = "Empty input - write your password"
            } else if (!isValidPassword(password)) {
                password.error = "Invalid password"
                Toast.makeText(
                    activity,
                    "The password should contain at least 8 characters, one number, one uppercase and one lowercase letter",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                //reset values
                myCounter = 0
                correctAnswer = 0
                wrongAnswer = 0
                score = 0
                name = userName.text.toString()
                questions.clear()

                //message shown when everything was correct, and the game can start
                Toast.makeText(activity, "Let's start the game", Toast.LENGTH_SHORT).show()
                //create questions
                makeQuestions()
                //randomise them
                questions.shuffle()

                //check the first question's type
                //if the first question's type is RADIO -> the radio fragment will be loaded
                if(questions[myCounter].getType() == Questions.QuestionType.RADIO) {
                    val newFragment = RadioFragment()
                    val fragmentManager = fragmentManager
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.mainFragment, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                //if the first question's type is CHECK_BOX -> the checkbox fragment will be loaded
                else if(questions[myCounter].getType() == Questions.QuestionType.CHECK_BOX){
                    val newFragment = CheckboxFragment()
                    val fragmentManager = fragmentManager
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.mainFragment, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                //in any other cases -> the input fragment will be loaded
                else{
                    val newFragment = InputFragment()
                    val fragmentManager = fragmentManager
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.mainFragment, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        })

        return rootView
    }

    //check if the username is valid
    private fun isValidName(name: EditText) : Boolean{
        val regex = ("^[a-zA-Z ]+\$").toRegex()
        return regex.matches(name.text.toString())
    }

    //check if the password is correct
    private fun isValidPassword(password: EditText) : Boolean{
        val regex =("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}\$").toRegex()
        return regex.matches(password.text.toString())
    }

    //function to create questions
    private fun makeQuestions(): List<Questions> {
        questions.add(Questions("How many times has Lewis Hamilton won at the Hungaroring?",
            Questions.QuestionType.RADIO, arrayOf("4", "6", "8", "9"), arrayOf(2),"He is a 7 time world champion and his first race in Formula 1 was in 2007"))
        questions.add(Questions(
                "Which country has held at least one Formula 1 race?",
                Questions.QuestionType.CHECK_BOX, arrayOf("Denmark", "Sweden", "South Africa", "Morocco"), arrayOf(1,2,3),"One Scandinavian and two African countries"))
        questions.add(Questions(
            "Which of the following drivers has the most race wins?",
            Questions.QuestionType.RADIO, arrayOf("Michael Schumacher", "Ayrton Senna", "Alain Prost", "Lewis Hamilton"), arrayOf(3),"This driver is still racing"))
        questions.add(Questions(
            "When was the first Formula 1 race held?", Questions.QuestionType.TEXT, arrayOf(),
            arrayOf(),"In the middle of 20th century", "1950"))
        questions.add(Questions(
            "Which is an incorrect pairing (one from the pairing has not won the championship with that team)?",
            Questions.QuestionType.RADIO, arrayOf("Fernando Alonso - Renault", "Sebastian Vettel - Red Bull", "Jenson Button - McLaren", "Nico Rosberg - Mercedes"), arrayOf(2),"He has won the championship in 2009"))
        /*questions.add(Questions(
            "Which country has not held a Formula One race?",
            Questions.QuestionType.RADIO, arrayOf("Denmark", "Sweden", "South Africa", "Morocco"), arrayOf(0),"It is a Scandinavian country"))*/
        questions.add(Questions("It is true that there are teams which are competing in the championship since the first season?",
            Questions.QuestionType.RADIO, arrayOf("True", "Maybe", "False", "I don't know"), arrayOf(0),"This team is the Scuderia Ferrari"))
        questions.add(Questions("Which driver has won five consecutive titles between 2000 and 2004?",
            Questions.QuestionType.RADIO, arrayOf("Fernando Alonso", "Juan Pablo Montoya", "Mika Hakkinen", "Michael Schumacher"), arrayOf(3), "Every single title was won with the Ferrari team"))
        questions.add(Questions("In which track was held the most races(70 so far)?",
            Questions.QuestionType.RADIO, arrayOf("Monaco", "Spa-Francorchamps", "Nurburgring", "Monza"), arrayOf(3),"This track is also called 'The temple of speed'"))
        questions.add(Questions("Which team has won the constructor's champion title in 2019?",
            Questions.QuestionType.TEXT, arrayOf(), arrayOf(),"It is a successful german manufacturer","Mercedes"))
        questions.add(Questions("Which was Sebastian Vettel's first victory?",
            Questions.QuestionType.RADIO, arrayOf("2007 - USA", "2008 - Monza", "2019 - Singapore", "2015 - Australia"), arrayOf(1),"He made his debut at 2007"))
        return questions
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
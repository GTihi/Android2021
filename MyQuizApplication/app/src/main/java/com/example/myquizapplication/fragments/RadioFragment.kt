package com.example.myquizapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.myquizapplication.models.MainActivity.Companion.correctAnswer
import com.example.myquizapplication.models.MainActivity.Companion.myCounter
import com.example.myquizapplication.models.MainActivity.Companion.questions
import com.example.myquizapplication.models.MainActivity.Companion.score
import com.example.myquizapplication.models.MainActivity.Companion.wrongAnswer
import com.example.myquizapplication.models.Questions
import com.example.myquizapplication.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RadioFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    //radio buttons
    private lateinit var option1 : RadioButton
    private lateinit var option2 : RadioButton
    private lateinit var option3 : RadioButton
    private lateinit var option4 : RadioButton
    //button
    private lateinit var submitButton: Button
    //textviews
    private lateinit var questionText : TextView
    private lateinit var checkPoints : TextView
    private lateinit var questionCount : TextView
    private lateinit var hint : TextView
    //checkbox
    private lateinit var showHint : CheckBox

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
        val rootView = inflater.inflate(R.layout.fragment_radio, container, false)
        //set xml elements for variables
        //radio buttons
        option1 = rootView.findViewById(R.id.radioButton1) //first answer option
        option2 = rootView.findViewById(R.id.radioButton2) //second answer option
        option3 = rootView.findViewById(R.id.radioButton3) //third answer option
        option4 = rootView.findViewById(R.id.radioButton4) //fourth answer option
        //button
        submitButton = rootView.findViewById(R.id.submitAnswerButton)
        //textViews
        questionText = rootView.findViewById(R.id.questionText)
        questionCount = rootView.findViewById(R.id.numberOfQuestion)
        questionCount.text = "${myCounter+1}.question" //the number of question
        checkPoints = rootView.findViewById(R.id.points)
        checkPoints.text = "$score/100" //the number of points
        hint = rootView.findViewById(R.id.theHint)
        //checkbox
        showHint = rootView.findViewById(R.id.showHint)

        //giving values -> loading the question and answer options
        createQuestions()

        //set up what happens when the "Show hint" checkbox is selected or not
        showHint.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{compoundButton, isChecked ->
            //if it is checked
            if(isChecked){
                score -= 5 //5 points will be decreased
                checkPoints.text = "$score/100" //modifying the score
                hint.text = questions[myCounter].getHint() //get the hint which is connected to the question
                hint.visibility = View.VISIBLE //show the hint
            }
            //in other case
            else{
                hint.visibility = View.GONE //hide the hint
            }
        })

        //if the submit answer button is pressed
        submitButton.setOnClickListener(View.OnClickListener{
            //check the answer
            checkAnswer()

            //if we are at the last question -> the next page which will be shown will be the result page
            if((myCounter+1) == questions.size){
                val newFragment = ResultFragment()
                val fragmentManager = fragmentManager
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.radioFragment, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            //if the next question's type is RADIO -> the radio fragment will be loaded
            else if(questions[myCounter+1].getType() == Questions.QuestionType.RADIO && (myCounter+1) != questions.size){
                ++myCounter
                val newFragment = RadioFragment()
                val fragmentManager = fragmentManager
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.radioFragment, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            //if the next question's type is CHECK_BOX -> the checkbox fragment will be loaded
            else if(questions[myCounter+1].getType() == Questions.QuestionType.CHECK_BOX && (myCounter+1) != questions.size){
                ++myCounter
                val newFragment = CheckboxFragment()
                val fragmentManager = fragmentManager
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.radioFragment, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            //if the next question's type is TEXT -> the input fragment will be loaded
            else if(questions[myCounter+1].getType() == Questions.QuestionType.TEXT && (myCounter+1) != questions.size){
                ++myCounter
                val newFragment = InputFragment()
                val fragmentManager = fragmentManager
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.radioFragment, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
        return rootView
    }

    //loading the question's text, and the answer options
    private fun createQuestions(){
        if(questions[myCounter].getType() == Questions.QuestionType.RADIO){
            questionText.text = questions[myCounter].getQuestionText()
            option1.text = questions[myCounter].getQuestionChoices()[0]
            option2.text = questions[myCounter].getQuestionChoices()[1]
            option3.text = questions[myCounter].getQuestionChoices()[2]
            option4.text = questions[myCounter].getQuestionChoices()[3]
        }
    }

    //check answer
    private fun checkAnswer(){
        var answer = -1
        //the answer's variable value will be radiobutton selected by us
        when {
            option1.isChecked -> {
                answer =0
            }
            option2.isChecked -> {
                answer = 1
            }
            option3.isChecked -> {
                answer = 2
            }
            option4.isChecked  -> {
                answer = 3
            }
        }
        //if our choice was correct
        if(questions[myCounter].getRadioAnswer().contains(answer)){
            //we get the 'Correct answer' message
            Toast.makeText(activity, "Correct answer", Toast.LENGTH_SHORT).show()
            //the number of correct answers will be increased
            ++correctAnswer
            //we add 10 points to our score
            score += 10
        }
        //in other case
        else{
            //we get the 'Wrong answer' message
            Toast.makeText(activity, "Wrong answer", Toast.LENGTH_SHORT).show()
            //the number of wrong answers will be increased
            ++wrongAnswer
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RadioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
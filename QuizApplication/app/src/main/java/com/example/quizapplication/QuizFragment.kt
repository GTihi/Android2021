package com.example.quizapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.quizapplication.questions.Questions
import com.example.quizapplication.questions.Questions.QuestionType


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class QuizFragment : Fragment() {
    private lateinit var question : Questions
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var messageTextView : TextView

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
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        /*messageTextView = view.findViewById(R.id.messageTextView)
        messageTextView.text = "$param1 $param2"*/
        setupView(view)
        return view
    }

    fun setupView(view: View) {
        val questionView = view.findViewById<TextView>(R.id.questionText)
        questionView.text = question.getQuestionText()
        when (question.getType()) {
            QuestionType.TEXT -> setTextAnswers(view)
            QuestionType.RADIO -> setRadioButtonAnswers(view, question.getQuestionChoices())
            QuestionType.CHECK_BOX -> setCheckboxAnswers(view, question.getQuestionChoices())
        }
    }

    fun setRadioButtonAnswers(view: View, answers: Array<String>) {
        if (answers == null || answers.size != 4) {
            return
        }
        val group = view.findViewById<RadioGroup>(R.id.myRadiogroup)
        val answerView1 = view.findViewById<RadioButton>(R.id.radioButton1)
        answerView1.text = answers[0]
        val answerView2 = view.findViewById<RadioButton>(R.id.radioButton2)
        answerView2.text = answers[1]
        val answerView3 = view.findViewById<RadioButton>(R.id.radioButton3)
        answerView3.text = answers[2]
        val answerView4 = view.findViewById<RadioButton>(R.id.radioButton4)
        answerView4.text = answers[3]

        //Set an on Change listener

    }

    fun setCheckboxAnswers(view: View, answers: Array<String>) {
        val radioButtons = view.findViewById<RadioGroup>(R.id.myRadiogroup)
        val checkboxesLayout = view.findViewById<LinearLayout>(R.id.checkboxLayout)
        val submitButton: Button = view.findViewById(R.id.checkboxSubmitButton)
        checkboxesLayout.visibility = View.VISIBLE
        radioButtons.visibility = View.GONE
        if (answers == null || answers.size != 4) {
            return
        }
        val answerView1 = view.findViewById<CheckBox>(R.id.checkbox1)
        answerView1.text = answers[0]
        val answerView2 = view.findViewById<CheckBox>(R.id.checkbox2)
        answerView2.text = answers[1]
        val answerView3 = view.findViewById<CheckBox>(R.id.checkbox3)
        answerView3.text = answers[2]
        val answerView4 = view.findViewById<CheckBox>(R.id.checkbox4)
        answerView4.text = answers[3]
        submitButton.setOnClickListener(View.OnClickListener {
            if (answerView1.isChecked && answerView2.isChecked && answerView3.isChecked && answerView4.isChecked) {
                Toast.makeText(context, "\ud83d\udc82" + " Mолодец!" + " Good work!" + " \ud83c\udf5e", Toast.LENGTH_SHORT).show()
                // intents as rewards for getting questions right. Redirect to more Russian news sources and recipes.
                scoreManager.addToScore()
            } else {
                Toast.makeText(context, "\ud83d\ude45" + " Oй, oшибка " + " Incorrect" + " \ud83d\udeab", Toast.LENGTH_SHORT).show()
            }
        })
    }

    
    fun onCheckedChanged(radioGroup: RadioGroup, radioButtonId: Int) {
        var answer = 0
        if (radioButtonId == R.id.radioButton1) answer = 0
        else if (radioButtonId == R.id.radioButton2) answer = 1
        else if (radioButtonId == R.id.radioButton3) answer = 2
        else if (radioButtonId == R.id.radioButton4) answer = 3
        question.isCorrect(intArrayOf(answer))
        if (question.isCorrect(intArrayOf(answer)) == true) {
            Toast.makeText(context, "\ud83d\udc82" + " Mолодец!" + " Good work!" + " \ud83c\udf5e", Toast.LENGTH_SHORT).show()
            scoreManager.addToScore()
            //TODO: updateScoreView();
        } else {
            Toast.makeText(context, "\ud83d\ude45" + " Oй, oшибка " + " Incorrect" + " \ud83d\udeab", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
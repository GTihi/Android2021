package com.example.myquizapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.myquizapplication.models.MainActivity.Companion.correctAnswer
import com.example.myquizapplication.models.MainActivity.Companion.name
import com.example.myquizapplication.models.MainActivity.Companion.score
import com.example.myquizapplication.models.MainActivity.Companion.wrongAnswer
import com.example.myquizapplication.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    //textviews
    private lateinit var correctAnswerCounter : TextView
    private lateinit var wrongAnswerCounter : TextView
    private lateinit var finalScore : TextView
    private lateinit var resultName: TextView
    //button
    private lateinit var backToMenu : Button

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
        val rootView = inflater.inflate(R.layout.fragment_result, container, false)
        //set xml elements for variables
        correctAnswerCounter = rootView.findViewById(R.id.counterOfCorrectAnswers)
        wrongAnswerCounter = rootView.findViewById(R.id.counterOfWrongAnswers)
        finalScore = rootView.findViewById(R.id.finalPoints)
        resultName = rootView.findViewById(R.id.resultPage)

        //this message will be shown at the top of the result page
        resultName.text = "Good job ${name}!"

        backToMenu = rootView.findViewById(R.id.backToMain)
        //this function help to give values for the textView elements: for the number of correct answers, for number of wrong answers and for final score
        showResults()

        //if we press the back button -> we go back to the main page
        backToMenu.setOnClickListener(View.OnClickListener {
            val newFragment = MainFragment()
            val fragmentManager = fragmentManager
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.resultFragment, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        })
        return rootView
    }

    private fun showResults(){
        correctAnswerCounter.text = correctAnswer.toString()
        wrongAnswerCounter.text = wrongAnswer.toString()
        finalScore.text = "$score/100"
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
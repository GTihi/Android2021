package com.example.quizapplication.questions


class Questions (private  var questionText: String, private var type: QuestionType, private var questionChoices: Array<String>, private var radioAnswer: IntArray){
    //private  var questionText : String
    //private lateinit var type: QuestionType

    private lateinit var textAnswer: String
    //private lateinit var radioAnswer: ArrayList<Int>

    //private lateinit var questionChoices: Array<String>

    var score = 0

    enum class QuestionType {
        RADIO, CHECK_BOX, TEXT
    }


    constructor(questionText: String, textAnswer: String) {
        this.questionText = questionText
        type = QuestionType.TEXT
        this.textAnswer = textAnswer
        isCorrect(textAnswer)
    }

    fun isCorrect(answer: String): Boolean {
        if (type !== QuestionType.TEXT) {
            return false
        } else if (answer.equals("Bandy", ignoreCase = true) || answer.equals("Hockey", ignoreCase = true)) {
            return true
        }
        return answer.equals(textAnswer, ignoreCase = true)
    }

    fun isCorrect(answers: IntArray): Boolean {
        if (type === QuestionType.TEXT) {
            return false
        } else if (type === QuestionType.RADIO) {
            return answers.equals(radioAnswer)
        }
        return false
    }

    fun getQuestionText(): String {
        return questionText
    }

    fun setQuestionText(questionText: String) {
        this.questionText = questionText
    }

    fun getType(): QuestionType {
        return type
    }

    fun setType(type: QuestionType) {
        this.type = type
    }

    fun getTextAnswer(): String {
        return textAnswer
    }

    fun setTextAnswer(textAnswer: String) {
        this.textAnswer = textAnswer
    }

    fun getRadioAnswer(): IntArray {
        return radioAnswer
    }

    fun setRadioAnswer(radioAnswer: IntArray) {
        this.radioAnswer = radioAnswer
    }


    fun getQuestionChoices(): Array<String> {
        return questionChoices
    }

    fun setQuestionChoices(questionChoices: Array<String>) {
        this.questionChoices = questionChoices
    }
}
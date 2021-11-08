package com.example.myquizapplication.models

//primary constructor
class Questions (private var questionText: String, private var type: QuestionType, private var questionChoices: Array<String>, private var radioAnswer: Array<Int>, private var hints : String){

    private lateinit var textAnswer: String

    enum class QuestionType {
        RADIO, CHECK_BOX, TEXT
    }

    //secondary constructor
    constructor(questionText: String, type: QuestionType, questionChoices: Array<String>, radioAnswer: Array<Int>, hints: String, textAnswer: String) : this(questionText,type,questionChoices,radioAnswer,hints){
        this.questionText = questionText
        this.textAnswer = textAnswer
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

    fun getRadioAnswer(): Array<Int> {
        return radioAnswer
    }

    fun setRadioAnswer(radioAnswer: Array<Int>) {
        this.radioAnswer = radioAnswer
    }


    fun getQuestionChoices(): Array<String> {
        return questionChoices
    }

    fun setQuestionChoices(questionChoices: Array<String>) {
        this.questionChoices = questionChoices
    }

    fun getHint() : String{
        return hints
    }
}
package com.project.quiz_application.models

data class Question(
    val question: String,
    val categoryId: Int,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: Int,
    var selectedOption: Int = 0
)

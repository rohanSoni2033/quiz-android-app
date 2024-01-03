package com.project.quiz_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class QuizActivity : AppCompatActivity() {
    private lateinit var questionNumberRecyclerView: RecyclerView

    private lateinit var optionAButton: OptionButton
    private lateinit var optionBButton: OptionButton
    private lateinit var optionCButton: OptionButton
    private lateinit var optionDButton: OptionButton

    private lateinit var questionNumberTextView: TextView
    private lateinit var questionTextView: TextView

    private lateinit var nextQuestionButton: Button
    private lateinit var previousQuestionButton: Button

    private val questions: MutableList<Question> = mutableListOf()

    private lateinit var quizProgressSeekBar: SeekBar
    private var currentQuestionNumber = 0
    private var currentSelectedOption = 0

    private lateinit var adapter: QuestionNumberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionNumberRecyclerView = findViewById(R.id.category_recycler_view)

        optionAButton = findViewById(R.id.option_a_button)
        optionBButton = findViewById(R.id.option_b_button)
        optionCButton = findViewById(R.id.option_c_button)
        optionDButton = findViewById(R.id.option_d_button)

        questionNumberTextView = findViewById(R.id.question_number_text_view)
        questionTextView = findViewById(R.id.question_text_view)
        quizProgressSeekBar = findViewById(R.id.quiz_progress_seek_bar)
        nextQuestionButton = findViewById(R.id.next_question_button)
        previousQuestionButton = findViewById(R.id.previous_question_button)

        questions.addAll(getQuestions())

        questionNumberRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val questionNumberButtonState = arrayListOf<ButtonState>()

        for (i in 0 until questions.size) {
            questionNumberButtonState.add(i, ButtonState.DISABLED)
        }

        adapter = QuestionNumberAdapter(questionNumberButtonState)

        questionNumberRecyclerView.adapter = adapter

        optionButtonClickHandler(optionAButton)
        optionButtonClickHandler(optionBButton)
        optionButtonClickHandler(optionCButton)
        optionButtonClickHandler(optionDButton)

        displayCurrentQuestion()

        nextQuestionButton.setOnClickListener {
            currentQuestionNumber++
            displayCurrentQuestion()
        }

        previousQuestionButton.setOnClickListener {
            currentQuestionNumber--
            displayCurrentQuestion()
        }
    }

    private fun displayCurrentQuestion() {
        questionNumberRecyclerView.scrollToPosition(currentQuestionNumber)

        nextQuestionButton.isEnabled =
            questions[currentQuestionNumber].selectedOption > 0 && currentQuestionNumber != questions.size - 1

        previousQuestionButton.isEnabled = currentQuestionNumber != 0

        questionNumberTextView.text = "${currentQuestionNumber + 1} / ${questions.size}"

        val progress = (currentQuestionNumber + 1) * 100 / questions.size
        val question = questions[currentQuestionNumber]

        quizProgressSeekBar.progress = progress
        questionTextView.text = question.question

        optionAButton.setText(question.optionA)
        optionBButton.setText(question.optionB)
        optionCButton.setText(question.optionC)
        optionDButton.setText(question.optionD)

        clearOptionSelection()

        optionAButton.resetButton()
        optionBButton.resetButton()
        optionCButton.resetButton()
        optionDButton.resetButton()


        if (questions[currentQuestionNumber].selectedOption > 0) {
            val correctOption = questions[currentQuestionNumber].correctOption

            if (correctOption != questions[currentQuestionNumber].selectedOption) {
                when (questions[currentQuestionNumber].selectedOption) {
                    1 -> optionAButton.setWrongOption()
                    2 -> optionBButton.setWrongOption()
                    3 -> optionCButton.setWrongOption()
                    4 -> optionDButton.setWrongOption()
                }
            }
            when (correctOption) {
                1 -> optionAButton.setCorrectOption()
                2 -> optionBButton.setCorrectOption()
                3 -> optionCButton.setCorrectOption()
                4 -> optionDButton.setCorrectOption()
            }
            optionAButton.disableButton()
            optionBButton.disableButton()
            optionCButton.disableButton()
            optionDButton.disableButton()
        }

        adapter.selectButton(currentQuestionNumber, ButtonState.SELECTED)
    }

    private fun getQuestions(): List<Question> {
        val questionsListJson =
            applicationContext.assets.open("questions.json").bufferedReader().use {
                it.readText()
            }
        val gson = Gson()
        return gson.fromJson(questionsListJson, Array<Question>::class.java).toList()
    }

    private fun clearOptionSelection() {
        optionAButton.unSelectButton()
        optionAButton.enableButton()
        optionCButton.hideCheckButton()


        optionAButton.hideCheckButton()
        optionBButton.hideCheckButton()
        optionDButton.hideCheckButton()

        optionBButton.unSelectButton()
        optionBButton.enableButton()

        optionCButton.unSelectButton()
        optionCButton.enableButton()

        optionDButton.unSelectButton()
        optionDButton.enableButton()
    }

    private fun optionButtonClickHandler(btn: OptionButton) {
        btn.setCheckButtonClickListener {
            val correctOption = questions[currentQuestionNumber].correctOption
            questions[currentQuestionNumber].selectedOption = currentSelectedOption
            nextQuestionButton.isEnabled = currentQuestionNumber != questions.size - 1 && true

            if (correctOption == currentSelectedOption) {
                btn.setCorrectOption()
                adapter.selectButton(currentQuestionNumber, ButtonState.CORRECT)
            } else {
                adapter.selectButton(currentQuestionNumber, ButtonState.WRONG)
                btn.setWrongOption()
                when (correctOption) {
                    1 -> optionAButton.setCorrectOption()
                    2 -> optionBButton.setCorrectOption()
                    3 -> optionCButton.setCorrectOption()
                    4 -> optionDButton.setCorrectOption()
                }
            }

            optionAButton.disableButton()
            optionBButton.disableButton()
            optionCButton.disableButton()
            optionDButton.disableButton()
        }

        btn.setOptionButtonClickListener {
            clearOptionSelection()

            val selected = when (btn.id) {
                R.id.option_a_button -> {
                    currentSelectedOption = 1
                    optionAButton
                }

                R.id.option_b_button -> {
                    currentSelectedOption = 2
                    optionBButton
                }

                R.id.option_c_button -> {
                    currentSelectedOption = 3
                    optionCButton
                }

                R.id.option_d_button -> {
                    currentSelectedOption = 4
                    optionDButton
                }

                else -> {
                    null
                }
            }

            selected?.selectButton()
            selected?.disableButton()
            selected?.showCheckButton()
        }
    }
}
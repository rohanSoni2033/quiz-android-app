package com.project.quiz_application.activities

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.quiz_application.ButtonState
import com.project.quiz_application.OptionButton
import com.project.quiz_application.models.Question
import com.project.quiz_application.R
import com.project.quiz_application.Utils
import com.project.quiz_application.adapters.QuestionNumberAdapter

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

        questionNumberRecyclerView = findViewById(R.id.categories_recycler_view)
        optionAButton = findViewById(R.id.option_a_button)
        optionBButton = findViewById(R.id.option_b_button)
        optionCButton = findViewById(R.id.option_c_button)
        optionDButton = findViewById(R.id.option_d_button)
        questionNumberTextView = findViewById(R.id.question_number_text_view)
        questionTextView = findViewById(R.id.question_text_view)
        quizProgressSeekBar = findViewById(R.id.quiz_progress_seek_bar)
        nextQuestionButton = findViewById(R.id.next_question_button)
        previousQuestionButton = findViewById(R.id.previous_question_button)


        val selectedCategoryId = intent.getIntExtra("categoryId", 1)
        questions.addAll(getQuestions(selectedCategoryId))

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

        optionAButton.text = question.optionA
        optionBButton.text = question.optionB
        optionCButton.text = question.optionC
        optionDButton.text = question.optionD

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
            optionAButton.buttonEnabled = true
            optionBButton.buttonEnabled = true
            optionCButton.buttonEnabled = true
            optionDButton.buttonEnabled = true
        }

        adapter.selectButton(currentQuestionNumber, ButtonState.SELECTED)
    }

    private fun getQuestions(categoryId: Int): List<Question> {
        val questions = Utils.loadJsonFile<Question>(this, "questions.json")

        return questions.filter {
            it.categoryId == categoryId
        }
    }

    private fun clearOptionSelection() {
        optionAButton.buttonSelected = false
        optionBButton.buttonSelected = false
        optionCButton.buttonSelected = false
        optionDButton.buttonSelected = false

        optionAButton.buttonEnabled = true
        optionBButton.buttonEnabled = true
        optionCButton.buttonEnabled = true
        optionDButton.buttonEnabled = true

        optionAButton.visibility = false
        optionBButton.visibility = false
        optionCButton.visibility = false
        optionDButton.visibility = false

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

            optionAButton.buttonEnabled = false
            optionBButton.buttonEnabled = false
            optionCButton.buttonEnabled = false
            optionDButton.buttonEnabled = false
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

            selected?.buttonSelected = true
            selected?.buttonEnabled = false
            selected?.visibility = true
        }
    }
}
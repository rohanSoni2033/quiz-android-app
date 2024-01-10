package com.project.quiz_application

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout

class OptionButton(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private var optionButton: Button
    private var checkButton: Button
    private var correctImageView: ImageView
    private var wrongImageView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.option_button, this, true)

        optionButton = findViewById(R.id.option_button)
        checkButton = findViewById(R.id.check_button)
        correctImageView = findViewById(R.id.correct_answer_image_view)
        wrongImageView = findViewById(R.id.wrong_answer_image_view)
    }

    fun setOptionButtonClickListener(callback: (btn: View) -> Unit) {
        optionButton.setOnClickListener {
            callback(it)
        }
    }

    var buttonSelected = false
        set(value) {
            optionButton.isSelected = value
            field = value
        }

    var buttonEnabled = true
        set(value) {
            optionButton.isEnabled = value
            field = value
        }

    var visibility = false
        set(value) {
            if (value) {
                checkButton.visibility = View.VISIBLE
            } else {
                checkButton.visibility = View.GONE
            }
            field = value
        }


    fun setCheckButtonClickListener(callback: (id: Int) -> Unit) {
        checkButton.setOnClickListener {
            callback(it.id)
        }
    }

    fun resetButton() {
        wrongImageView.visibility = View.GONE
        correctImageView.visibility = View.GONE
        optionButton.background =
            AppCompatResources.getDrawable(context, R.drawable.option_button_selector)
        optionButton.isSelected = false
    }


    var text: String = ""
        set(value) {
            optionButton.text = value
            field = value
        }

    fun setCorrectOption() {
        optionButton.background =
            AppCompatResources.getDrawable(context, R.drawable.correct_background)
        optionButton.isSelected = true

        correctImageView.visibility = View.VISIBLE
        checkButton.visibility = View.GONE
    }

    fun setWrongOption() {
        optionButton.background =
            AppCompatResources.getDrawable(context, R.drawable.wrong_background)
        optionButton.isSelected = true

        wrongImageView.visibility = View.VISIBLE
        checkButton.visibility = View.GONE
    }
}
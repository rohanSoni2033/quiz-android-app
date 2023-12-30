package com.project.quiz_application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView


class QuestionNumberAdapter(
    private val totalQuestion: Int, private val callback: (currentQuestion: Int) -> Unit
) : RecyclerView.Adapter<QuestionNumberAdapter.QuestionNumberViewHolder>() {

    private var currentQuestionState: ButtonState = ButtonState.DISABLED

    class QuestionNumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var questionNumberButton: Button

        init {
            questionNumberButton = itemView.findViewById(R.id.question_number_button)
        }

        fun bind(
            questionNumber: Int, state: ButtonState, callback: (currentQuestion: Int) -> Unit
        ) {
            questionNumberButton.text = questionNumber.toString()

            when (state) {
                ButtonState.SELECTED -> questionNumberButton.isSelected = true
                ButtonState.CORRECT -> {
                    questionNumberButton.background = AppCompatResources.getDrawable(
                        itemView.context, R.drawable.correct_option_button
                    )
                    questionNumberButton.isSelected = true
                }
                ButtonState.WRONG -> {
                    questionNumberButton.background = AppCompatResources.getDrawable(
                        itemView.context, R.drawable.wrong_option_button
                    )
                    questionNumberButton.isSelected = true
                }
                ButtonState.DISABLED -> questionNumberButton.isSelected = false
            }
            questionNumberButton.setOnClickListener {
                callback(questionNumber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionNumberViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.question_number, parent, false)
        return QuestionNumberViewHolder(view)
    }

    override fun getItemCount(): Int {
        return totalQuestion
    }

    override fun onBindViewHolder(holder: QuestionNumberViewHolder, position: Int) {
        holder.bind(position + 1, currentQuestionState, callback)
    }

    fun selectButton(position: Int, state: ButtonState) {
        currentQuestionState = state
        notifyItemChanged(position)
    }
}
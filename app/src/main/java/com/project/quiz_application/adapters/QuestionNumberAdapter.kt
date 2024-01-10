package com.project.quiz_application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.project.quiz_application.ButtonState
import com.project.quiz_application.R


class QuestionNumberAdapter(
    private val questionNumberStates: ArrayList<ButtonState>
) : RecyclerView.Adapter<QuestionNumberAdapter.QuestionNumberViewHolder>() {

    class QuestionNumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var questionNumberButton: Button

        init {
            questionNumberButton = itemView.findViewById(R.id.question_number_button)
        }

        fun bind(
            questionNumber: Int, state: ButtonState
        ) {
            questionNumberButton.text = "${questionNumber + 1}"

            when (state) {
                ButtonState.SELECTED -> {
                    questionNumberButton.background = AppCompatResources.getDrawable(
                        itemView.context, R.drawable.option_button_selector
                    )
                    questionNumberButton.isSelected = true
                }

                ButtonState.DISABLED -> {
                    questionNumberButton.background = AppCompatResources.getDrawable(
                        itemView.context, R.drawable.option_button_selector
                    )
                    questionNumberButton.isSelected = false
                }

                ButtonState.CORRECT -> {
//                    Log.d("selectedButton", "state = $state and position = $questionNumber")
                    questionNumberButton.background = AppCompatResources.getDrawable(
                        itemView.context, R.drawable.correct_background
                    )
                    questionNumberButton.isSelected = true
                }

                ButtonState.WRONG -> {
                    questionNumberButton.background = AppCompatResources.getDrawable(
                        itemView.context, R.drawable.wrong_background
                    )
                    questionNumberButton.isSelected = true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionNumberViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.question_number, parent, false)
        return QuestionNumberViewHolder(view)
    }

    override fun getItemCount(): Int = questionNumberStates.size

    override fun onBindViewHolder(holder: QuestionNumberViewHolder, position: Int) {
        holder.bind(position, questionNumberStates[holder.absoluteAdapterPosition])
    }

    fun selectButton(position: Int, state: ButtonState) {
//        Log.d("selectedButton", "position = $position state = $state")
        if (questionNumberStates[position] == ButtonState.DISABLED || questionNumberStates[position] == ButtonState.SELECTED) {
            questionNumberStates[position] = state
            notifyItemChanged(position)
        }
    }
}
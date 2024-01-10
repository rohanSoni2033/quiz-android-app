package com.project.quiz_application.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.project.quiz_application.models.Category
import com.project.quiz_application.R

class QuizCategoryAdapter(
    private val categories: List<Category>
) : RecyclerView.Adapter<QuizCategoryAdapter.QuizCategoryViewHolder>() {

    private var selectedCategoryId: Int? = null
    private var selectedPosition = RecyclerView.NO_POSITION

    inner class QuizCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var quizCategoryName: TextView
        private var quizCategoryImage: ImageView
        private var quizCategoryCard: CardView

        init {
            quizCategoryName = itemView.findViewById(R.id.quiz_category_name)
            quizCategoryImage = itemView.findViewById(R.id.quiz_category_image)
            quizCategoryCard = itemView.findViewById(R.id.quiz_category_card)
        }

        fun bind(position: Int) {
            val category = categories[position]

            quizCategoryName.text = category.name

            val categoryImage = itemView.context.assets.open(category.image)
            val d = Drawable.createFromStream(categoryImage, null)
            quizCategoryImage.setImageDrawable(d)

            quizCategoryCard.setOnClickListener {
                selectedCategoryId = categories[position].id
                notifyItemChanged(selectedPosition)
                notifyItemChanged(position)
                selectedPosition = position
            }

            quizCategoryCard.isSelected = position == selectedPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizCategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.quiz_category_card, parent, false)
        return QuizCategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: QuizCategoryViewHolder, position: Int) {
        holder.bind(holder.absoluteAdapterPosition)
    }

    fun getSelectedCategory() = selectedCategoryId
}
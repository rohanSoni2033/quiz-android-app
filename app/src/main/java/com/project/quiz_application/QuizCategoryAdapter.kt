package com.project.quiz_application

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class QuizCategoryAdapter(
    private val categories: List<Category>
) : RecyclerView.Adapter<QuizCategoryAdapter.QuizCategoryViewHolder>() {

    private var selectedCategoryId: Int? = null

    // this holds the selected position it will change when user click on any card
    private var selectedPosition = RecyclerView.NO_POSITION

    inner class QuizCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nameTextView: TextView
        private var imageImageView: ImageView
        private var quizCategoryCard: CardView

        init {
            nameTextView = itemView.findViewById(R.id.quiz_category_name)
            imageImageView = itemView.findViewById(R.id.quiz_category_image)
            quizCategoryCard = itemView.findViewById(R.id.quiz_category_card)
        }

        fun bind(adapterPosition: Int) {
            val category = categories[adapterPosition]

            val categoryImage = itemView.context.assets.open(category.image)
            val d = Drawable.createFromStream(categoryImage, null)

            nameTextView.text = category.name
            imageImageView.setImageDrawable(d)

            quizCategoryCard.setOnClickListener {
                selectedCategoryId = categories[adapterPosition].id
                notifyItemChanged(selectedPosition)
                notifyItemChanged(adapterPosition)
                selectedPosition = adapterPosition
            }
            quizCategoryCard.isSelected = adapterPosition == selectedPosition
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
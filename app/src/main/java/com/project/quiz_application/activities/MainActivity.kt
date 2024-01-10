package com.project.quiz_application.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.quiz_application.models.Category
import com.project.quiz_application.R
import com.project.quiz_application.Utils
import com.project.quiz_application.adapters.QuizCategoryAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var startQuizButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryRecyclerView = findViewById(R.id.categories_recycler_view)

        val categories = Utils.loadJsonFile<Category>(this, "categories.json")

        categoryRecyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = QuizCategoryAdapter(categories)

        categoryRecyclerView.adapter = adapter
        startQuizButton = findViewById(R.id.start_quiz_button)

        startQuizButton.isSelected = true
        startQuizButton.setOnClickListener {
            quizButtonClickListener(adapter)
        }
    }

    private fun quizButtonClickListener(adapter: QuizCategoryAdapter) {
        val intent = Intent(this, QuizActivity::class.java)

        if (adapter.getSelectedCategory() == null) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show()
            return
        }
        intent.putExtra("categoryId", adapter.getSelectedCategory())
        startActivity(intent)
    }
}
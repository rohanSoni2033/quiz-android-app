package com.project.quiz_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var startQuizButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryRecyclerView = findViewById(R.id.category_recycler_view)

        val categoriesJson = applicationContext.assets.open("categories.json")
            .bufferedReader()
            .use {
                it.readText()
            }

        val gson = Gson()
        val categories = gson.fromJson(categoriesJson, Array<Category>::class.java).toList()

        categoryRecyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = QuizCategoryAdapter(categories)

        categoryRecyclerView.adapter = adapter
        startQuizButton = findViewById(R.id.start_quiz_button)

        startQuizButton.isSelected = true
        startQuizButton.setOnClickListener {
            Intent(this, QuizActivity::class.java).apply {
                if (adapter.getSelectedCategory() == null) {
                    Toast.makeText(
                        applicationContext,
                        "Please select a category",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    putExtra("categoryId", adapter.getSelectedCategory())
                    startActivity(this)
                }
            }
        }
    }
}
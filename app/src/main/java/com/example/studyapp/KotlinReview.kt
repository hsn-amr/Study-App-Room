package com.example.studyapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class KotlinReview : AppCompatActivity() {

    private val kotlinDBHelper by lazy { KotlinDBHelper(applicationContext) }
    lateinit var adapter: KotlinRecyclerViewAdapter
    private lateinit var rvKotlinReview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_review)
        this.title = "Kotlin Review"

        val topics = kotlinDBHelper.getAllTopics()
        rvKotlinReview = findViewById(R.id.rvKotlinReview)
        adapter = KotlinRecyclerViewAdapter(topics,this)
        rvKotlinReview.adapter = adapter
        rvKotlinReview.layoutManager =LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_topic -> {
                showAddAlert()
            }
            R.id.android_review -> {
                val androidIntent = Intent(this, AndroidReview::class.java)
                startActivity(androidIntent)
            }
            R.id.main_page -> {
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddAlert(){
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val etTitle = EditText(this)
        val etInfo = EditText(this)
        val etExplain = EditText(this)

        etTitle.hint = "Title"
        etInfo.hint = "Information"
        etExplain.hint = "Explanation"

        layout.addView(etTitle)
        layout.addView(etInfo)
        layout.addView(etExplain)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add New Topic")
            .setView(layout)
            .setPositiveButton("Add"){_,_ ->
                val title = etTitle.text.toString()
                val info = etInfo.text.toString()
                val explain = etExplain.text.toString()
                val topic = Topic(0,title,info,explain)
                kotlinDBHelper.addTopic(topic)
                adapter.updateAdapter()
            }
            .setNegativeButton("Cancel"){dialogFace,_ -> dialogFace.cancel()}
            .create()
        dialog.show()
    }
}
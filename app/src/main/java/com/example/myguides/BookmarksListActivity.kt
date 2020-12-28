package com.example.myguides

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookmarksListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_search)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val searchInput: EditText = findViewById(R.id.search_plainText)
        val searchButton: Button = findViewById(R.id.search_button)
        val searchLineView: View = findViewById(R.id.search_line_view)

        searchInput.visibility = View.GONE
        searchButton.visibility = View.GONE
        searchLineView.visibility = View.GONE

        val recyclerView: RecyclerView = findViewById(R.id.slides_list_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = GuideSearchActivity.Adapter(generateFakeValues())

        // TODO: client get liked guides
    }

    // TODO: DELETE ME!!!
    private fun generateFakeValues(): List<GuideSearchActivity.GuideShortInfo> {
        val values = mutableListOf<GuideSearchActivity.GuideShortInfo>()

        for (i in 0..100) {
            values.add(GuideSearchActivity.GuideShortInfo("$i element", i.toString()))
        }

        return values
    }


}
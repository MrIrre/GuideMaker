package com.example.myguides

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookmarksListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_search)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        recyclerView = findViewById(R.id.slides_list_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val searchInput: EditText = findViewById(R.id.search_plainText)
        val searchButton: Button = findViewById(R.id.search_button)
        val searchLineView: View = findViewById(R.id.search_line_view)

        searchInput.visibility = View.GONE
        searchButton.visibility = View.GONE
        searchLineView.visibility = View.GONE

        recyclerView.adapter = GuideSearchActivity.Adapter(generateFakeValues())

        // TODO: client get liked guides
    }

    fun showGuideFromBookmarks(view: View) {
        val position = recyclerView.getChildLayoutPosition(view.parent as View)
        val guideItem = (recyclerView.adapter as GuideSearchActivity.Adapter).getByIndex(position)


        val intent = Intent(this, GuideActivity::class.java)
        intent.putExtra("current_guide_id", guideItem.id)
        intent.putExtra("current_guide_name", guideItem.name)
        startActivity(intent)
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
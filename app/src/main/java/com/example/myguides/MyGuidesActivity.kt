package com.example.myguides

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myguides.common.AlertWindow
import com.example.myguides.common.ApiClient
import com.example.myguides.common.GuideDescription
import com.example.myguides.common.TokenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyGuidesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    val client: ApiClient =
        ApiClient(
            "http://10.0.2.2:5000",
            TokenHelper.getToken()
        )

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

        val recyclerView: RecyclerView = findViewById(R.id.slides_list_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.Default) {
            val guidesResult = client.getMyGuides()
            GlobalScope.launch(Dispatchers.Main) {
                if (!guidesResult.isSuccessful())
                {
                    val error = guidesResult.error
                    AlertWindow.show(this@MyGuidesActivity, "Could not get your guides, sorry.\nError: $error")
                    finish()
                }
                else
                {
                    recyclerView.adapter = GuideListItemAdapter(guidesResult.value as List<GuideDescription>,
                        R.layout.bookmarks_guide_list_item,
                        R.id.bookmarks_guide_list_item_text)
                }
            }
        }
    }

    fun showMyGuide(view: View) {
        val position = recyclerView.getChildLayoutPosition(view.parent as View)
        val guideItem = (recyclerView.adapter as GuideListItemAdapter).getByIndex(position)


        val intent = Intent(this, GuideActivity::class.java)
        intent.putExtra("current_guide_id", guideItem.id)
        startActivity(intent)
    }
}
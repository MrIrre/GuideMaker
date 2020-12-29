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

class MyGuidesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    val client: ApiClient = ApiClient("http://10.0.2.2:5000", TokenHelper.getToken())

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

        val guidesResult = client.getMyGuides()
        if (!guidesResult.isSuccessful())
        {
            val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this)
            val error = guidesResult.error as String
            dlgAlert.setMessage("Could not get your guides, sorry.\n Error: $error")
            dlgAlert.setTitle("Error Message...")
            dlgAlert.setPositiveButton("OK", null)
            dlgAlert.setCancelable(true)
            dlgAlert.create().show()

            finish()
            return
        }

        recyclerView.adapter = GuideListItemAdapter(guidesResult.value as List<GuideDescription>, R.layout.bookmarks_guide_list_item, R.id.bookmarks_guide_list_item_text)
    }

    fun showMyGuide(view: View) {
        val position = recyclerView.getChildLayoutPosition(view.parent as View)
        val guideItem = (recyclerView.adapter as GuideListItemAdapter).getByIndex(position)


        val intent = Intent(this, GuideActivity::class.java)
        intent.putExtra("current_guide_id", guideItem.id)
        startActivity(intent)
    }
}
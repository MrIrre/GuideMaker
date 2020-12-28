package com.example.myguides

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookmarksListActivity : AppCompatActivity() {
    val client: ApiClient = ApiClient("http://10.0.2.2:5000", TokenHelper.getToken())

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


        val guidesResult = client.getLikedGuides()
        if (!guidesResult.isSuccessful())
        {
            val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this)
            val error = guidesResult.error as String
            dlgAlert.setMessage("Could not get liked guides, sorry.\n Error: $error")
            dlgAlert.setTitle("Error Message...")
            dlgAlert.setPositiveButton("OK", null)
            dlgAlert.setCancelable(true)
            dlgAlert.create().show()

            dlgAlert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which -> })

            return
        }
        recyclerView.adapter = GuideSearchActivity.Adapter(guidesResult.value as List<GuideDescription>)

        // TODO: client get liked guides
    }


}
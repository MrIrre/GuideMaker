package com.example.myguides

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GuideSearchActivity : AppCompatActivity() {
    private lateinit var searchPlainText: EditText
    private lateinit var recyclerView: RecyclerView
    val client: ApiClient = ApiClient("http://10.0.2.2:5000", TokenHelper.getToken())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_search)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        searchPlainText = findViewById(R.id.search_plainText)
        recyclerView = findViewById(R.id.slides_list_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    fun search(view: View) {
        val textToSearch = searchPlainText.text.toString()


        val guides = client.searchGuides(textToSearch)

        if (!guides.isSuccessful())
        {
            val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this)

            dlgAlert.setMessage("Could not find guides, sorry")
            dlgAlert.setTitle("Error Message...")
            dlgAlert.setPositiveButton("OK", null)
            dlgAlert.setCancelable(true)
            dlgAlert.create().show()

            dlgAlert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which -> })

            return
        }

        recyclerView.adapter = GuideListItemAdapter(guides.value as List<GuideDescription>, R.layout.search_guide_list_item, R.id.search_guide_list_item_text)
    }

    fun showGuide(view: View) {
        val position = recyclerView.getChildLayoutPosition(view.parent as View)
        val guideItem = (recyclerView.adapter as GuideListItemAdapter).getByIndex(position)


        val intent = Intent(this, GuideActivity::class.java)
        intent.putExtra("current_guide_id", guideItem.id)
        startActivity(intent)
    }
}



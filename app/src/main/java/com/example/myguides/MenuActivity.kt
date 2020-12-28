package com.example.myguides

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun createGuide(view: View) {
        val intent = Intent(this, GuideCreatorActivity::class.java)
        startActivity(intent)
    }

    fun searchGuide(view: View) {
        val intent = Intent(this, GuideSearchActivity::class.java)
        startActivity(intent)
    }

    fun toLikedGuides(view: View) {
        val intent = Intent(this, LikedGuidesListActivity::class.java)
        startActivity(intent)
    }
}
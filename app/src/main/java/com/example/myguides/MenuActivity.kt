package com.example.myguides

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
        val intent = Intent(this, BookmarksListActivity::class.java)
        startActivity(intent)
    }

    fun toMyGuides(view: View) {
        val intent = Intent(this, MyGuidesActivity::class.java)
        startActivity(intent)
    }
}
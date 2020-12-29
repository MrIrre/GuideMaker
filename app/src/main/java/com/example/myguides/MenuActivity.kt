package com.example.myguides

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature( Window.FEATURE_NO_TITLE );

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN );

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

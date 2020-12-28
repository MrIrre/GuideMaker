package com.example.myguides

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.IOException


class GuideCreatorActivity : AppCompatActivity() {
    private val guideSlides: MutableList<Slide> = mutableListOf()

    lateinit var slideImageView: ImageView
    lateinit var slideDescriptionEditText: EditText
    lateinit var emptyImageViewBitMap: Drawable

    //Image request code
    private val PICK_IMAGE_REQUEST = 1

    private val STORAGE_PERMISSION_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_creator)

        //Requesting storage permission
        requestStoragePermission()

        slideImageView = findViewById(R.id.slide_image_imageView)
        emptyImageViewBitMap = slideImageView.drawable

        slideDescriptionEditText = findViewById(R.id.slide_description_editText)

//        //actionbar
//        val actionbar = supportActionBar
//        //set actionbar title
//        actionbar!!.title = "Guide Creator"
//        //set back button
//        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.title_for_creator, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.send_guide_button -> sendGuide()
        }

        return true
    }

    private fun sendGuide() {
        println("Client upload HERE!") // TODO!
        finish()
    }


    fun addSlideImage(v: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select slide picture"), 1)
    }

    fun addSlide(v: View) {
        val bitmap = (slideImageView.drawable as BitmapDrawable).bitmap
        val description = slideDescriptionEditText.text.toString()

        guideSlides.add(Slide(bitmap, description))

//        val byteStream = ByteArrayOutputStream()
//        lateinit var byteArray: ByteArray
//
//        byteStream.use { byteStream ->
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteStream)
//            byteArray = byteStream.toByteArray()
//        }
//
//        val baseString = Base64.encodeToString(byteArray, Base64.DEFAULT)
//        println(description)
//        println(guideSlides)

        slideImageView.setImageDrawable(emptyImageViewBitMap)
        slideDescriptionEditText.setText("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val filePath = data.data

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                slideImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    //Requesting permission
    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) return
        //And finally ask for the permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    data class Slide(val image: Bitmap, val description: String)
}


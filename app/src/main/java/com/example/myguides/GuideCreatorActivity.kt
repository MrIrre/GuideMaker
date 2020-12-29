package com.example.myguides

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import com.example.myguides.common.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class GuideCreatorActivity : AppCompatActivity() {
    private val guideSlides: MutableList<Slide> = mutableListOf()

    lateinit var slideImageView: ImageView
    lateinit var guideNameEditText: EditText
    lateinit var slideDescriptionEditText: EditText
    lateinit var guideDescriptionEditText: EditText
    lateinit var emptyImageViewBitMap: Drawable

    private val client: ApiClient =
        ApiClient(
            "http://10.0.2.2:5000",
            TokenHelper.getToken()
        )

    //Image request code
    private val PICK_IMAGE_REQUEST = 1

    private val STORAGE_PERMISSION_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_creator)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Requesting storage permission
        requestStoragePermission()

        slideImageView = findViewById(R.id.slide_image_imageView)
        emptyImageViewBitMap = slideImageView.drawable

        slideDescriptionEditText = findViewById(R.id.slide_description_editText)
        guideNameEditText = findViewById(R.id.guide_name_plainText)
        guideDescriptionEditText = findViewById(R.id.guide_description_editText)
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
        val uuid = UUID.randomUUID().toString()

        AsyncRunner.runAsync(
            { client.getUserId() },
            {
                val guide = Guide(
                    GuideDescription(
                        uuid,
                        guideNameEditText.text.toString(),
                        guideDescriptionEditText.text.toString()
                    ), guideSlides, it, listOf(), listOf()
                )

                val saveGuideResult = client.saveGuide(guide)

                if (!saveGuideResult.isSuccessful()) {
                    val error = saveGuideResult.error
                    AlertWindow.show(
                        this,
                        "Could not save guide, sorry.\n Error: $error"
                    )
                } else
                    finish()
            }
        )
    }


    fun addSlideImage(v: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select slide picture"), 1)
    }

    fun addSlide(v: View) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        (slideImageView.drawable as BitmapDrawable).bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            byteArrayOutputStream
        )
        val byteArray = byteArrayOutputStream.toByteArray()
        val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        val description = slideDescriptionEditText.text.toString()

        val slide = Slide(encoded, description)

        guideSlides.add(slide)

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
}

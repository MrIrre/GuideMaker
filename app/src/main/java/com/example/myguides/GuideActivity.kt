package com.example.myguides

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GuideActivity : AppCompatActivity() {
    private lateinit var guide: Guide

    //     private lateinit var guideInfo: GuideInfo
    private lateinit var guideId: String
    private lateinit var guideNameTextView: TextView
    private lateinit var guideDescriptionTextView: TextView
    private lateinit var bookmarkButton: ImageButton
    private lateinit var slidesListRecyclerView: RecyclerView
    val client: ApiClient = ApiClient("http://10.0.2.2:5000", TokenHelper.getToken())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        guideId = intent.getStringExtra("current_guide_id") as String
        val guideResult = client.getFullGuide(guideId)
        if (!guideResult.isSuccessful()) {
            val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this)
            val error = guideResult.error as String
            dlgAlert.setMessage("Could not get guide $guideId, sorry.\n Error: $error")
            dlgAlert.setTitle("Error Message...")
            dlgAlert.setPositiveButton("OK", null)
            dlgAlert.setCancelable(true)
            dlgAlert.create().show()

            dlgAlert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which -> })

            return
        }


        guideNameTextView = findViewById(R.id.guide_name_textView)
        guideDescriptionTextView = findViewById(R.id.full_guide_description_textView)
        bookmarkButton = findViewById(R.id.bookmark_button)
        slidesListRecyclerView = findViewById(R.id.slides_list_recyclerView)
        slidesListRecyclerView.layoutManager = LinearLayoutManager(this)

        guide = guideResult.value as Guide

        guideNameTextView.text = guide.description.name
        guideDescriptionTextView.text = guide.description.description // TODO: guide description
        slidesListRecyclerView.adapter = SlideAdapter(guide.slides)

        // TODO: check bookmark

        if (guide.likes.contains(client.getUserId()))
            bookmarkButton.setImageResource(android.R.drawable.star_big_on)

    }

    private fun tryLike() : Boolean {
        val likeResult = client.likeGuide(guideId)
        if (!likeResult.isSuccessful())
        {
            val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this)
            val error = likeResult.error as String
            dlgAlert.setMessage("Could not like guide $guideId, sorry.\n Error: $error")
            dlgAlert.setTitle("Error Message...")
            dlgAlert.setPositiveButton("OK", null)
            dlgAlert.setCancelable(true)
            dlgAlert.create().show()

            dlgAlert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which -> })

            return false
        }

        guide = client.getFullGuide(guideId).value as Guide
        return true
    }

    private fun tryDislike() : Boolean {
        val likeResult = client.dislikeGuide(guideId)
        if (!likeResult.isSuccessful())
        {
            val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this)
            val error = likeResult.error as String
            dlgAlert.setMessage("Could not like guide $guideId, sorry.\n Error: $error")
            dlgAlert.setTitle("Error Message...")
            dlgAlert.setPositiveButton("OK", null)
            dlgAlert.setCancelable(true)
            dlgAlert.create().show()

            dlgAlert.setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which -> })

            return false
        }

        guide = client.getFullGuide(guideId).value as Guide
        return true
    }


    fun clickBookmark(view: View) {
        if (!guide.likes.contains(client.getUserId()))
        {
            if (tryLike())
                bookmarkButton.setImageResource(android.R.drawable.star_big_on)
        }
        else
        {
            if (tryDislike())
                bookmarkButton.setImageResource(android.R.drawable.star_big_off)
        }
    }

    class SlideAdapter(private val values: List<Slide>): RecyclerView.Adapter<SlideAdapter.ViewHolder>() {
        override fun getItemCount() = values.size

//        fun getByIndex(position: Int): Slide {
//            return values[position]
//        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.guide_slide_view_list_item, parent,false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val decodedString: ByteArray = Base64.decode(values[position].base64Image, Base64.DEFAULT)
            val decodedByte =
                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            holder.image.setImageBitmap(decodedByte)
            holder.description.text = values[position].text
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var image: ImageView = itemView.findViewById(R.id.guide_slide_view_list_item_image)
            var description: TextView = itemView.findViewById(R.id.guide_slide_view_list_item_description)
        }
    }
}




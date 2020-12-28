package com.example.myguides

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GuideActivity : AppCompatActivity() {
//     private lateinit var guideInfo: GuideInfo
    private lateinit var guideId: String
    private lateinit var guideNameTextView: TextView
    private lateinit var guideDescriptionTextView: TextView
    private lateinit var bookmarkButton: ImageButton
    private lateinit var slidesListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)


        guideId = intent.getStringExtra("current_guide_id") as String
        // TODO: send client request (get guide by id Route)


        guideNameTextView = findViewById(R.id.guide_name_textView)
        guideDescriptionTextView = findViewById(R.id.full_guide_description_textView)
        bookmarkButton = findViewById(R.id.bookmark_button)
        slidesListRecyclerView = findViewById(R.id.slides_list_recyclerView)
        slidesListRecyclerView.layoutManager = LinearLayoutManager(this)

        guideNameTextView.text = "Guide №$guideId" // TODO: guide name
        guideDescriptionTextView.text = "$guideId $guideId + $guideId = $guideId" // TODO: guide description
        slidesListRecyclerView.adapter = SlideAdapter(listOf( // TODO: guide slides
            Slide(getResources().getDrawable( R.drawable.black_rectangle).toBitmap(), "Interesting!"),
            Slide(getResources().getDrawable( R.drawable.red_rectangle ).toBitmap(), "Shit!")
        ))

    }

//    fun clickBookmark() {
//        guideInfo
//    }

//    data class GuideInfo(val guide)


    data class Slide(val image: Bitmap, val description: String)

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
            holder.image.setImageBitmap(values[position].image)
            holder.description.text = values[position].description
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var image: ImageView = itemView.findViewById(R.id.guide_slide_view_list_item_image)
            var description: TextView = itemView.findViewById(R.id.guide_slide_view_list_item_description)
        }
    }
}




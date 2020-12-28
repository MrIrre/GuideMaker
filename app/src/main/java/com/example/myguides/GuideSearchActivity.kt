package com.example.myguides

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GuideSearchActivity : AppCompatActivity() {
    lateinit var searchPlainText: EditText
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_search)

        searchPlainText = findViewById(R.id.search_plainText)
        recyclerView = findViewById(R.id.slides_list_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    // TODO: DELETE ME!!!
    private fun generateFakeValues(): List<GuideShortInfo> {
        val values = mutableListOf<GuideShortInfo>()

        for (i in 0..100) {
            values.add(GuideShortInfo("$i element", i.toString()))
        }

        return values
    }

    fun search(view: View) {
        val textToSearch = searchPlainText.text.toString()
        // TODO: send client request (search Route)
        // TODO: parse response???

        val guides: List<GuideShortInfo> = listOf() // TODO: list guides from response


        recyclerView.adapter = Adapter(generateFakeValues()) // Change to "Adapter(guides.map(guide => guide.name))"
    }

    fun showGuide(view: View) {
        val position = recyclerView.getChildLayoutPosition(view.parent as View)
        val guideItem = (recyclerView.adapter as Adapter).getByIndex(position)


        val intent = Intent(this, GuideActivity::class.java)
        intent.putExtra("current_guide_id", guideItem.id)
        intent.putExtra("current_guide_name", guideItem.name)
        startActivity(intent)
    }

    data class GuideShortInfo(val name: String, val id: String)

    class Adapter(private val values: List<GuideShortInfo>): RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun getItemCount() = values.size

        fun getByIndex(position: Int): GuideShortInfo {
            return values[position]
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_guide_list_item, parent,false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = values[position].name
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textView: TextView = itemView.findViewById(R.id.search_guide_list_item)
        }
    }
}



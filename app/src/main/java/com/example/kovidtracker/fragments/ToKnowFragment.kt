package com.example.kovidtracker.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kovidtracker.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.StringReader
import java.net.URL


class ToKnowAdapter(private val dataSet: List<List<String>>): RecyclerView.Adapter<ToKnowAdapter.ToKnowRowViewHolder>() {

    class ToKnowRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context
        val img: ImageView = view.findViewById(R.id.to_know_img)
        val title: TextView = view.findViewById(R.id.to_know_title)
        val content: TextView = view.findViewById(R.id.to_know_content)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ToKnowRowViewHolder {
        return ToKnowRowViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_row_to_know, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ToKnowRowViewHolder, position: Int) {
        viewHolder.apply {
            title.text = dataSet[position][0]
            content.text = dataSet[position][1]
            Picasso.get().load(dataSet[position][2]).into(img)
        }
    }

    override fun getItemCount() = dataSet.size
}
class ToKnowFragment(val json: String) : Fragment() {

    lateinit var adapter: ToKnowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_know, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.to_know_rc)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var data = mutableListOf<List<String>>()
        JsonReader(StringReader(json)).use {
            it.beginArray()
            while(it.hasNext()){
                it.beginObject()
                var d = mutableListOf(it.nextName(), it.nextString())
                it.nextName()
                d.add(it.nextString())
                data.add(d)
                it.endObject()
            }
            it.endArray()
        }
        adapter = ToKnowAdapter(data)
        recyclerView.adapter = adapter
    }
}
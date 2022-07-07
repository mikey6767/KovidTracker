package com.example.kovidtracker.fragments

import android.content.Context
import android.os.Bundle
import android.util.JsonReader
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kovidtracker.R
import java.io.StringReader

class ToDoAdapter(private val dataSet: List<List<String>>): RecyclerView.Adapter<ToDoAdapter.ToDoRowViewHolder>() {

    class ToDoRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.to_do_title)
        val content: TextView = view.findViewById(R.id.to_do_content)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ToDoRowViewHolder {
        return ToDoRowViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_row_to_do, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ToDoRowViewHolder, position: Int) {
        viewHolder.apply {
            title.text = dataSet[position][0]
            var contentStr = ""
            for(i in 1 until (dataSet[position].count() - 1)){
                contentStr += "    - " + dataSet[position][i] + "\n\n"
            }
            contentStr += "    - " + dataSet[position][dataSet[position].count() - 1]
            content.text = contentStr
        }
    }

    override fun getItemCount() = dataSet.size
}
class TodoFragment(val json: String) : Fragment() {

    lateinit var adapter: ToDoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.to_do_rc)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var data = mutableListOf<List<String>>()
        JsonReader(StringReader(json)).use {
            it.beginObject()
            while(it.hasNext()){
                var d = mutableListOf(it.nextName())
                it.beginArray()
                while(it.hasNext()){
                    d.add(it.nextString())
                }
                it.endArray()
                data.add(d)
            }
            it.endObject()
        }
        adapter = ToDoAdapter(data)
        recyclerView.adapter = adapter
    }
}
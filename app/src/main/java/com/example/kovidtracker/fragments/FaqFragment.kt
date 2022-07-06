package com.example.kovidtracker.fragments

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.JsonReader
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kovidtracker.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.StringReader

private const val ARG_PARAM1 = "user"

/**
 * A simple [Fragment] subclass.
 * Use the [FaqFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class FAQAdapter(private val dataSet: List<String>, private val context: Context): RecyclerView.Adapter<FAQAdapter.FAQRowViewHolder>() {

    class FAQRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var view = view
        val title: Button = view.findViewById(R.id.faq_title)
        val content: TextView = view.findViewById(R.id.faq_content)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FAQRowViewHolder {
        return FAQRowViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_row_faq, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: FAQRowViewHolder, position: Int) {
        viewHolder.apply {
            title.setOnClickListener{
                when(content.visibility){
                    View.VISIBLE -> content.visibility = View.GONE
                    View.GONE -> content.visibility = View.VISIBLE
                }
            }
            var contentStr: String = ""
            JsonReader(StringReader(dataSet[position])).use(fun(r: JsonReader) {
                r.beginObject()
                while (r.hasNext()) {
                    when (r.nextName()) {
                        "Q" -> title.text = r.nextString()
                        "A" -> {
                            r.beginObject()
                            while (r.hasNext()) {
                                r.nextName()
                                r.beginObject()
                                r.nextName()
                                when (r.nextString()) {
                                    "para" -> {
                                        r.nextName()
                                        contentStr += r.nextString() + "\n\n"
                                    }
                                    "ls" -> {
                                        r.nextName()
                                        r.beginArray()
                                        while(r.hasNext()){
                                            contentStr += "    - " + r.nextString() + "\n"
                                        }
                                        contentStr += "\n"
                                        r.endArray()
                                    }
                                }
                                r.endObject()
                            }
                            r.endObject()
                        }
                    }
                }
                r.endObject()
            })
            content.setText(contentStr)
        }
    }

    override fun getItemCount() = dataSet.size
}

class FaqFragment : Fragment() {
    private var user: String? = null
    private lateinit var adapter: FAQAdapter
    private val db = Firebase.firestore
    private var json = mutableListOf<String>()
    //private lateinit var promise: Promise<

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db.collection("common")
            .document("FAQ").get().addOnSuccessListener(OnSuccessListener(fun(it: DocumentSnapshot) {
                for(i in 0 until it.getLong("len")!!){
                    json.add(i.toInt(), it[i.toString()].toString())
                }
                val recyclerView: RecyclerView = view.findViewById(R.id.hs_rv)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                adapter = FAQAdapter(json, requireContext())
                recyclerView.adapter = adapter
            }))
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Failed to load FAQ from database, please try again later",Toast.LENGTH_LONG).show()
            }
        //TODO proper data, proper layout
        //val data = arrayOf(arrayOf("Question 1", "Answer 1"), arrayOf("Question 2", "Answer 2"), arrayOf("Question 3", "Answer 3"))
    }

    companion object {
        @JvmStatic
        fun newInstance(user: String) =
            FaqFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, user)
                }
            }
    }
}
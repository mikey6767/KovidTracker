package com.example.kovidtracker.fragments

import android.os.Bundle
import android.util.JsonReader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kovidtracker.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.StringReader

private const val ARG_PARAM1 = "user"
private var answer: ArrayList<Boolean?> = ArrayList()
/**
 * A simple [Fragment] subclass.
 * Use the [HealthStatusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class HealthStatusAdapter(private val dataSet: ArrayList<String>): RecyclerView.Adapter<HealthStatusAdapter.HealthStatusRowViewHolder>() {
    private var yesListener = View.OnClickListener {
        answer[it.tag as Int] = true
    }
    private var noListener = View.OnClickListener {
        answer[it.tag as Int] = false
    }
    class HealthStatusRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val question: TextView = view.findViewById(R.id.hs_question)
        val yes: RadioButton = view.findViewById(R.id.hs_selection_yes)
        val no: RadioButton = view.findViewById(R.id.hs_selection_no)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HealthStatusRowViewHolder {
        return HealthStatusRowViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_row_hs, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: HealthStatusRowViewHolder, position: Int) {
        viewHolder.apply {
            yes.tag = position
            no.tag = position
            question.text = dataSet[position]
            yes.setOnClickListener(yesListener)
            no.setOnClickListener(noListener)
        }
    }

    override fun getItemCount() = dataSet.size
}

class HealthStatusFragment : Fragment() {
    private lateinit var adapter: HealthStatusAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_health_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO proper data, proper layout

        //val data = arrayOf("Q1", "Q2", "Q3")
        val data = arrayListOf<String>()
        FirebaseFirestore.getInstance().collection("common").document("healthStatus").get().addOnSuccessListener {
            JsonReader(StringReader(it["json"].toString())).use {
                it.beginObject()
                while(it.hasNext()){
                    answer.add(null)
                    var contentStr = ""
                    it.nextName()
                    it.beginObject()
                    while(it.hasNext()){
                        it.nextName()
                        it.beginObject()
                        it.nextName()
                        when(it.nextString()){
                            "para" -> {
                                it.nextName()
                                contentStr += it.nextString() + "\n\n"
                            }
                            "ls" -> {
                                it.nextName()
                                it.beginArray()
                                while(it.hasNext()){
                                    contentStr += "    - " + it.nextString() + "\n"
                                }
                                contentStr += "\n"
                                it.endArray()
                            }
                        }
                        it.endObject()
                    }
                    it.endObject()
                    data.add(contentStr)
                }
                it.endObject()
            }
            val recyclerView: RecyclerView = view.findViewById(R.id.hs_rv)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = HealthStatusAdapter(data)
            recyclerView.adapter = adapter
        }

        //val radioButtonNo =  arrayOf<RadioButton>(view.findViewById(R.id.hs_q1_n), view.findViewById(R.id.hs_q2_n), view.findViewById(R.id.hs_q3_n), view.findViewById(R.id.hs_q4_n), view.findViewById(R.id.hs_q5_n), view.findViewById(R.id.hs_q6_n))
        //val radioButtonYes =  arrayOf<RadioButton>(view.findViewById(R.id.hs_q1_y), view.findViewById(R.id.hs_q2_y), view.findViewById(R.id.hs_q3_y), view.findViewById(R.id.hs_q4_y), view.findViewById(R.id.hs_q5_y), view.findViewById(R.id.hs_q6_y))

        view.findViewById<Button>(R.id.hs_submit).setOnClickListener({
            if(null in answer){
                Toast.makeText(requireContext(), "Please answer all questions", Toast.LENGTH_LONG).show()
            }else{
                //beautiful
                FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).update("healthStatus", answer.count{it == true})
                Toast.makeText(requireContext(), "Health status updated!", Toast.LENGTH_LONG).show()
            }
        })
    }
    //TODO no longer used
    companion object {
        @JvmStatic
        fun newInstance(user: String) =
            HealthStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, user)
                }
            }
    }
}
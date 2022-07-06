package com.example.kovidtracker.fragments

import android.os.Bundle
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "user"

/**
 * A simple [Fragment] subclass.
 * Use the [HealthStatusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
/*
class HealthStatusAdapter(private val dataSet: Array<String>): RecyclerView.Adapter<HealthStatusAdapter.HealthStatusRowViewHolder>() {

    class HealthStatusRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val question: TextView = view.findViewById(R.id.hs_question)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HealthStatusRowViewHolder {
        return HealthStatusRowViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_row_hs, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: HealthStatusRowViewHolder, position: Int) {
        viewHolder.apply {
            question.text = dataSet[position]
        }
    }

    override fun getItemCount() = dataSet.size
}
*/

class HealthStatusFragment : Fragment() {
    //private lateinit var adapter: HealthStatusAdapter
    var answer = arrayOf<Boolean?>(null, null, null, null, null, null)
    val radioButtonNo =  arrayOf(R.id.hs_q1_n, R.id.hs_q2_n, R.id.hs_q3_n, R.id.hs_q4_n, R.id.hs_q5_n, R.id.hs_q6_n)
    val radioButtonYes =  arrayOf(R.id.hs_q1_y, R.id.hs_q2_y, R.id.hs_q3_y, R.id.hs_q4_y, R.id.hs_q5_y, R.id.hs_q6_y)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_health_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO proper data, proper layout
        /*
        val data = arrayOf("Q1", "Q2", "Q3")
        val recyclerView: RecyclerView = view.findViewById(R.id.hs_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HealthStatusAdapter(data)
        recyclerView.adapter = adapter
        */

        //val radioButtonNo =  arrayOf<RadioButton>(view.findViewById(R.id.hs_q1_n), view.findViewById(R.id.hs_q2_n), view.findViewById(R.id.hs_q3_n), view.findViewById(R.id.hs_q4_n), view.findViewById(R.id.hs_q5_n), view.findViewById(R.id.hs_q6_n))
        //val radioButtonYes =  arrayOf<RadioButton>(view.findViewById(R.id.hs_q1_y), view.findViewById(R.id.hs_q2_y), view.findViewById(R.id.hs_q3_y), view.findViewById(R.id.hs_q4_y), view.findViewById(R.id.hs_q5_y), view.findViewById(R.id.hs_q6_y))
        for(nbtn in radioButtonNo){
            view.findViewById<RadioButton>(nbtn).setOnClickListener {
                answer[radioButtonNo.indexOf(it.id)] = false
            }
        }
        for(ybtn in radioButtonYes){
            view.findViewById<RadioButton>(ybtn).setOnClickListener {
                answer[radioButtonYes.indexOf(it.id)] = true
            }
        }

        view.findViewById<Button>(R.id.hs_submit).setOnClickListener(View.OnClickListener {
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
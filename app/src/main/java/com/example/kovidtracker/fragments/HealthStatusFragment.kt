package com.example.kovidtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kovidtracker.R

private const val ARG_PARAM1 = "user"

/**
 * A simple [Fragment] subclass.
 * Use the [HealthStatusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

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


class HealthStatusFragment : Fragment() {
    private var user: String? = null
    private lateinit var adapter: HealthStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_health_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO proper data, proper layout
        val data = arrayOf("Q1", "Q2", "Q3")
        val recyclerView: RecyclerView = view.findViewById(R.id.hs_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HealthStatusAdapter(data)
        recyclerView.adapter = adapter
    }

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
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val ARG_PARAM1 = "user"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

data class DateLoc(val loc: String, val date: String)

class HistoryAdapter(private val dataSet: Array<DateLoc>): RecyclerView.Adapter<HistoryAdapter.HistoryRowViewHolder>() {

    class HistoryRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val num: TextView = view.findViewById(R.id.his_numbering)
        val loc: TextView = view.findViewById(R.id.his_location)
        val date: TextView = view.findViewById(R.id.his_datetime)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HistoryRowViewHolder {
        return HistoryRowViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_row_history, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: HistoryRowViewHolder, position: Int) {
        viewHolder.apply {
            num.text = position.toString()
            loc.text = dataSet[position].loc
            date.text = dataSet[position].date
        }
    }

    override fun getItemCount() = dataSet.size
}


class HistoryFragment : Fragment() {
    private var user: String? = null
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO proper data, proper layout
        val d = "2022-01-01 12:34"
        val data = arrayOf(DateLoc("Test1", d), DateLoc("Test2", d), DateLoc("Test3", d))
        val recyclerView: RecyclerView = view.findViewById(R.id.his_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HistoryAdapter(data)
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(user: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, user)
                }
            }
    }
}
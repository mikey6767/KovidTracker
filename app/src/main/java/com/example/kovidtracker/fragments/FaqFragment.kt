package com.example.kovidtracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kovidtracker.R

private const val ARG_PARAM1 = "user"

/**
 * A simple [Fragment] subclass.
 * Use the [FaqFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class FAQAdapter(private val dataSet: Array<Array<String>>): RecyclerView.Adapter<FAQAdapter.FAQRowViewHolder>() {

    class FAQRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
            title.text = dataSet[position][0]
            content.text = dataSet[position][1]
        }
    }

    override fun getItemCount() = dataSet.size
}

class FaqFragment : Fragment() {
    private var user: String? = null
    private lateinit var adapter: FAQAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO proper data, proper layout
        val data = arrayOf(arrayOf("Question 1", "Answer 1"), arrayOf("Question 2", "Answer 2"), arrayOf("Question 3", "Answer 3"))
        val recyclerView: RecyclerView = view.findViewById(R.id.hs_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FAQAdapter(data)
        recyclerView.adapter = adapter
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
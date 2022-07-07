package com.example.kovidtracker.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kovidtracker.R
import com.example.kovidtracker.RetrofitApi
import com.example.kovidtracker.Statistics
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    lateinit var totalconfirmedcases: TextView
    lateinit var totalrecoverycases: TextView
    lateinit var totalcriticalcases: TextView
    lateinit var totaldeathcases: TextView
    lateinit var covidnowbutton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        totalconfirmedcases = view.findViewById<TextView>(R.id.tv_statstotalconfirmedcases)
        totalrecoverycases = view.findViewById<TextView>(R.id.tv_statstotalrecoverycases)
        totalcriticalcases = view.findViewById<TextView>(R.id.tv_statstotalcriticalcases)
        totaldeathcases = view.findViewById<TextView>(R.id.tv_statstotaldeathcases)
        covidnowbutton = view.findViewById<Button>(R.id.btn_covidnow)

        covidnowbutton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://covidnow.moh.gov.my/"))
            startActivity(intent)
        }

        // Creating a method to fetch data using Retrofit2
        fetchApiDataUsingRetrofit()
    }

    public fun fetchApiDataUsingRetrofit() {

        // Set up the Retrofit
        val baseUrl = "https://api.apify.com/v2/key-value-stores/6t65lJVfs3d8s6aKc/records/"
        val gson = GsonBuilder().setLenient().create()
        // Create a Retrofit builder and pass the gson in
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        // Implement the Retrofit Client interface function
        val retrofitApi: RetrofitApi = retrofit.create(RetrofitApi::class.java)
        val statisticsCall: Call<Statistics> = retrofitApi.getStatistics()
        statisticsCall.enqueue(object : Callback<Statistics> {
            // success
            override fun onResponse(call: Call<Statistics>, response: Response<Statistics>) {
                // bind the data from the API to the views
                totalconfirmedcases.setText(response.body()?.getTestedPositive()) // return cases value
                totalrecoverycases.setText(response.body()?.getRecovered())
                totalcriticalcases.setText(response.body()?.getCritical())
                totaldeathcases.setText(response.body()?.getDeceased())
            }

            //failure
            override fun onFailure(call: Call<Statistics>, t: Throwable) {
                Log.e("ERROR: ", t.message!!)
                Toast.makeText(activity, "Fail to get the data.." + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
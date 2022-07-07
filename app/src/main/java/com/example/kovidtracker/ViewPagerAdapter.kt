package com.example.kovidtracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.kovidtracker.fragments.ToKnowFragment
import com.example.kovidtracker.fragments.TodoFragment

class ViewPagerAdapter(fm: FragmentManager, val toKnow:String, val toDo: String) : FragmentPagerAdapter(fm) {
    val knowFrag = ToKnowFragment(toKnow)
    val doFrag = TodoFragment(toDo)
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> knowFrag
            1-> doFrag
            else -> knowFrag
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Things To Know"
            1 -> "Things to Do"
            else -> "Things To Know"
        }
        return super.getPageTitle(position)
    }
}
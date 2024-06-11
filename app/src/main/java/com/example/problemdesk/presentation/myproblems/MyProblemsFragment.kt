package com.example.problemdesk.presentation.myproblems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.problemdesk.databinding.FragmentMyProblemsBinding
import com.example.problemdesk.presentation.myproblems.pagersubfragments.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


//TODO do we need swipe function??

class MyProblemsFragment : Fragment() {

    private var _binding: FragmentMyProblemsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProblemsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initiating viewPager
        viewPager = binding.myProblemsPager
        val fragmentList = arrayListOf(
            InWorkFragment.newInstance(),
            CompletedFragment.newInstance(),
            CancelledFragment.newInstance()
        )
        viewPager.adapter = MyProblemsPagerAdapter(this, fragmentList)

        //initiating tabLayout
        tabLayout = binding.myProblemsTabLayout
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        //sync tabLayout with viewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "В работе"
                1 -> tab.text = "Исполнены"
                2 -> tab.text = "Отклонены"
            }
        }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
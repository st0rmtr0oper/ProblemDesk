package com.example.problemdesk.presentation.mytasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.problemdesk.databinding.FragmentMyTasksBinding
import com.example.problemdesk.presentation.PagerAdapter
import com.example.problemdesk.presentation.mytasks.pagersubfragments.NewTasksFragment
import com.example.problemdesk.presentation.mytasks.pagersubfragments.PickedTasksFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyTasksFragment : Fragment() {

    private var _binding: FragmentMyTasksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initiating viewPager
        viewPager = binding.myTasksPager
        val fragmentList = arrayListOf(
            NewTasksFragment.newInstance(),
            PickedTasksFragment.newInstance()
        )
        viewPager.adapter = PagerAdapter(this, fragmentList)

        //initiating tabLayout
        tabLayout = binding.myTasksTabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
                0 -> tab.text = "Не назначенные"
                1 -> tab.text = "Мои задания"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
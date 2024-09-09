package com.example.problemdesk.presentation.mastermenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.problemdesk.databinding.FragmentMasterBinding
import com.example.problemdesk.presentation.general.PagerAdapter
import com.example.problemdesk.presentation.mastermenu.pagersubfragments.MasterApproveFragment
import com.example.problemdesk.presentation.mastermenu.pagersubfragments.MasterMonitorFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MasterFragment : Fragment() {

    private var _binding: FragmentMasterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMasterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSubFragments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpSubFragments() {
        //initiating viewPager
        viewPager = binding.masterPager
        val fragmentList = arrayListOf(
            MasterApproveFragment.newInstance(),
            MasterMonitorFragment.newInstance()
        )
        viewPager.adapter = PagerAdapter(this, fragmentList)

        //initiating tabLayout
        tabLayout = binding.masterTabLayout
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
                0 -> tab.text = "На рассмотрение"
                1 -> tab.text = "Мониторинг"
            }
        }.attach()
    }
}
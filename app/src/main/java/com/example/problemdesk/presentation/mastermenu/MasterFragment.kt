package com.example.problemdesk.presentation.mastermenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.problemdesk.databinding.FragmentMasterBinding
import com.example.problemdesk.presentation.PagerAdapter
import com.example.problemdesk.presentation.mastermenu.pagersubfragments.MasterApproveFragment
import com.example.problemdesk.presentation.mastermenu.pagersubfragments.MasterMonitorFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


//class MasterFragment : Fragment() {
//
//    private var _binding: FragmentMasterBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        _binding = FragmentMasterBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//        return root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        //::handleCardClick binding RV click logic with fragment
//        binding.masterRv.adapter = CardRecyclerViewAdapter(::handleCardClick)
//
//
//        //TODO delete mocking
////        val cards = listOf(
////            Card("Принято", "01.07.24", "Инструменты", "№1", "сломалась болгарка"),
////            Card("В работе", "30.06.24", "Безопасность", "№2", "проблема с проводкой"),
////            Card("Отменено", "01.07.24", "Документооборот", "№3", "проблема с документами"),
////            Card("На рассмотрении", "02.07.24", "Санитарно-бытовые условия", "№4", "закончилось мыло"),
////            Card("Выполнено", "27.06.24", "Санитарно-бытовые условия", "№4", "поменять лампочку")
//
////            Card(Status.APPROVED, "01.07.24", Specialization.INSTRUMENTS, Workplace.N1, "сломалась болгарка"),
////            Card(Status.IN_PROGRESS, "30.06.24", Specialization.SAFETY, Workplace.N2, "проблема с проводкой"),
////            Card(Status.CANCELLED, "01.07.24", Specialization.DOCUMENTS, Workplace.N3, "проблема с документами"),
////            Card(Status.UNCHECKED, "02.07.24", Specialization.SANITARY_CONDITIONS, Workplace.N4, "закончилось мыло"),
////            Card(Status.COMPLETED, "27.06.24", Specialization.SANITARY_CONDITIONS, Workplace.N4, "поменять лампочку")
////        )
////        (binding.masterRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
//    }
//
//    private fun handleCardClick(card: Card) {
//        //TODO delete mocking
//        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
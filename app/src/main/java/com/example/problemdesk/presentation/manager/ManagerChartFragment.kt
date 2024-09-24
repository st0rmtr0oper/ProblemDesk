package com.example.problemdesk.presentation.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.data.models.BossRequest
import com.example.problemdesk.databinding.FragmentManagerChartBinding
import com.example.problemdesk.domain.models.Card
import kotlinx.coroutines.launch

class ManagerChartFragment : Fragment() {
    private var _binding: FragmentManagerChartBinding? = null
    private val binding get() = _binding!!
    private val managerChartViewModel: ManagerChartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagerChartBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var request: BossRequest? = null
//        arguments?.let {
//            val fromDate = ManagerFilterFragmentArgs.fromBundle(it).dateStart
//            val untilDate = ManagerFilterFragmentArgs.fromBundle(it).dateEnd
//            val status = ManagerFilterFragmentArgs.fromBundle(it).status
//            val requestType = ManagerFilterFragmentArgs.fromBundle(it).type
//            val areaId = ManagerFilterFragmentArgs.fromBundle(it).area
//            request = BossRequest(fromDate, untilDate, status, requestType, areaId)
//        }
        setUpObservers()
        loadChart(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            chart.isGone = true
//            plug.isGone = true
        }
    }

    private fun showContent() {
        with(binding) {
            progressBar.isGone = true
            chart.isVisible = true
//            plug.isGone = true
        }
    }

    //    private fun showPlug() {
//        with(binding) {
//
//        }
//    }
    private fun setUpObservers() {
        //TODO implement
        managerChartViewModel.chartData.observe(viewLifecycleOwner) { chartData: List<Card> ->
//            if (RESP == "") {
            //                TODO implement
//            } else {
            showContent()
//            }
        }
    }

    private fun loadChart(request: BossRequest?) {
        showLoading()
        lifecycleScope.launch {
            if (request != null) {
                managerChartViewModel.loadChartData(request)
            }
        }
    }
}
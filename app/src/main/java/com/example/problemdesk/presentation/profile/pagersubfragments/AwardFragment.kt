package com.example.problemdesk.presentation.profile.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.problemdesk.databinding.FragmentSubAwardBinding
import com.example.problemdesk.domain.OLDMODELSrefactor.AwardData

class AwardFragment : Fragment() {
    private var _binding: FragmentSubAwardBinding? = null
    private val binding get() = _binding!!

    private val awardViewModel: AwardViewModel by viewModels()

    companion object {
        fun newInstance() = AwardFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubAwardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        awardViewModel.awardData.observe(viewLifecycleOwner, Observer { awardData: AwardData ->
            with(binding) {
                awardTokens.text = awardData.tokens.toString()
                awardCreatedRequests.text = awardData.createdRequests.toString()
                awardCompletedRequests.text = awardData.completedRequests.toString()
                awardLastCompletedDate.text = awardData.latestCompletedDate
            }
        })
        awardViewModel.loadInfo()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO award fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.problemdesk.presentation.profile.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.data.models.MyAwardsResponse
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.databinding.FragmentSubAwardBinding
import kotlinx.coroutines.launch

class AwardFragment : Fragment() {
    private var _binding: FragmentSubAwardBinding? = null
    private val binding get() = _binding!!

    private val awardViewModel: AwardViewModel by viewModels()

    companion object {
        fun newInstance() = AwardFragment()
    }

    //TODO need to add a loading shit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubAwardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        awardViewModel.awardData.observe(viewLifecycleOwner, Observer { awardData: MyAwardsResponse ->
            with(binding) {
                awardTokens.text = awardData.tokens.toString()
                awardCreatedRequests.text = awardData.numCreated.toString()
                awardCompletedRequests.text = awardData.numCompleted.toString()
                awardLastCompletedDate.text = awardData.lastCompleted
            }
        })

        val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
        val userId = sharedPreferences?.getInt("user_id", 0)

        lifecycleScope.launch {
            if (userId != null) {
                awardViewModel.loadInfo(userId)
            }
        }

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
package com.example.problemdesk.presentation.profile.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.problemdesk.databinding.FragmentSubProfileInfoBinding

class ProfileInfoFragment : Fragment() {
    private var _binding: FragmentSubProfileInfoBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileInfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubProfileInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root
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
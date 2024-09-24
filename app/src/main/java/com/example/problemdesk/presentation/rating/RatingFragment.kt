package com.example.problemdesk.presentation.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.data.models.RatingResponse
import com.example.problemdesk.databinding.FragmentRatingBinding
import kotlinx.coroutines.launch

class RatingFragment : Fragment() {

    private var _binding: FragmentRatingBinding? = null
    private val binding get() = _binding!!

    private val ratingViewModel: RatingViewModel by viewModels()

    companion object {
        fun newInstance() = RatingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        loadInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            contentLayout.isGone = true
        }
    }

    private fun showContent() {
        with(binding) {
            progressBar.isGone = true
            contentLayout.isVisible = true
        }
    }

    private fun setUpObservers() {
        ratingViewModel.ratingData.observe(viewLifecycleOwner) { ratingData: RatingResponse ->
//            with(binding) {
//                profileEmployeeLogin.text = profileData.username
//                profileEmploymentDate.text = profileData.hireDate
//                profileFullName.text = buildString {
//                    append(profileData.name)
//                    append(" ")
//                    append(profileData.surname)
//                    append(" ")
//                    append(profileData.middleName)
//                }
//                profileContactPhone.text = profileData.phoneNumber
//                profileDateOfBirth.text = profileData.birthDate
//                profileEmail.text = profileData.email

            showContent()
        }
    }


    private fun loadInfo() {
        showLoading()
//        val userId = context?.let { getSharedPrefsUserId(it) }
        lifecycleScope.launch {
//            if (userId != null) {
                ratingViewModel.loadRating()
//            }
        }
    }
}
package com.example.problemdesk.presentation.profile.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.data.models.MyDataResponse
import com.example.problemdesk.data.sharedprefs.getSharedPrefsUserId
import com.example.problemdesk.databinding.FragmentSubProfileInfoBinding
import kotlinx.coroutines.launch

class ProfileInfoFragment : Fragment() {
    private var _binding: FragmentSubProfileInfoBinding? = null
    private val binding get() = _binding!!

    private val profileInfoViewModel: ProfileInfoViewModel by viewModels()

    companion object {
        fun newInstance() = ProfileInfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubProfileInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        setUpObservers()
        val userId = context?.let { getSharedPrefsUserId(it) }
        lifecycleScope.launch {
            if (userId != null) {
                profileInfoViewModel.loadInfo(userId)
            }
        }
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
        profileInfoViewModel.profileData.observe(viewLifecycleOwner) { profileData: MyDataResponse ->
            showContent()
            with(binding) {
                profileEmployeeLogin.text = profileData.username
                profileEmploymentDate.text = profileData.hireDate
                profileFullName.text = buildString {
                    append(profileData.name)
                    append(" ")
                    append(profileData.surname)
                    append(" ")
                    append(profileData.middleName)
                }
                profileContactPhone.text = profileData.phoneNumber
                profileDateOfBirth.text = profileData.birthDate
                profileEmail.text = profileData.email
            }
        }
    }
}
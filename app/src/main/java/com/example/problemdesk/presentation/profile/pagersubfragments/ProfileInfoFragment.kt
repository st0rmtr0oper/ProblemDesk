package com.example.problemdesk.presentation.profile.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.problemdesk.databinding.FragmentSubProfileInfoBinding
import com.example.problemdesk.domain.OLDMODELSrefactor.ProfileData
import androidx.lifecycle.Observer

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
    ): View? {
        _binding = FragmentSubProfileInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileInfoViewModel.profileData.observe(
            viewLifecycleOwner,
            Observer { profileData: ProfileData ->
                with(binding) {
                    profileEmployeeLogin.text = profileData.login
                    profileEmploymentDate.text = profileData.employmentDate
                    profileFullName.text = profileData.fullName
                    profileContactPhone.text = profileData.contactPhone
                    profileDateOfBirth.text = profileData.dateOfBirth
                    profileEmail.text = profileData.email
                    profilePosition.text = profileData.position
                }
            })
        profileInfoViewModel.loadInfo()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
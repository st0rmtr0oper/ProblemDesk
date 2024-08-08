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
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.data.models.MyDataResponse
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileInfoFragment : Fragment() {
    private var _binding: FragmentSubProfileInfoBinding? = null
    private val binding get() = _binding!!

    private val profileInfoViewModel: ProfileInfoViewModel by viewModels()

    companion object {
        fun newInstance() = ProfileInfoFragment()
    }

    //TODO need to add a loading shit?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubProfileInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileInfoViewModel.profileData.observe(
            viewLifecycleOwner,
            Observer { profileData: MyDataResponse ->





                    //TODO need to remake this screen for new data structure
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
                    profilePosition.text = profileData.specId.toString()
                }
            })


        val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
        val userId = sharedPreferences?.getInt("user_id", 0)

        lifecycleScope.launch {
            if (userId != null) {
                profileInfoViewModel.loadInfo(userId)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
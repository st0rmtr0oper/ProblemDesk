package com.example.problemdesk.presentation.rating

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.problemdesk.MainActivity
import com.example.problemdesk.R
import com.example.problemdesk.data.models.LogOutRequest
import com.example.problemdesk.data.models.RatingResponse
import com.example.problemdesk.data.sharedprefs.OLD_FCM
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.data.sharedprefs.USER_ID
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
        setUpLogOutButton()
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





        ratingViewModel.logoutStatus.observe(viewLifecycleOwner) { status ->
            if (status) {
                findNavController().navigate(RatingFragmentDirections.actionNavigationRatingToNavigationLogin())
                val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
                sharedPreferences?.edit()?.clear()?.apply()
            } else {
                showErrorDialog()
                findNavController().navigate(RatingFragmentDirections.actionNavigationRatingToNavigationLogin())
                val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
                sharedPreferences?.edit()?.clear()?.apply()
            }
        }
    }

    private fun setUpLogOutButton() {
        val exitMenuItem = (activity as MainActivity).binding.toolbar.menu.findItem(R.id.action_exit)
        exitMenuItem?.setOnMenuItemClickListener {

            //i dont know is this a good way to use SP, cause it have troubles with context inside ViewModel
            val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
            val userId = sharedPreferences?.getInt(USER_ID, 0)
            val oldFcm = sharedPreferences?.getString(OLD_FCM, "")

            if (userId != null && oldFcm != null && userId != 0 && oldFcm != "") {
                val request = LogOutRequest(userId, oldFcm)
                showLogOutConfirmationDialog(request)
            }
            true
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






    private fun showLogOutConfirmationDialog(request: LogOutRequest) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Выход из аккаунта")
            setMessage("Вы хотите выйти из своей учетной записи?")
            setPositiveButton("Да") { _, _ ->
                ratingViewModel.logOut(request)
                val exitMenuItem = (activity as MainActivity).binding.toolbar.menu.findItem(R.id.action_exit)
                exitMenuItem.isVisible = false
            }
            setNegativeButton("Нет", null)
            show()
        }
    }

    private fun showErrorDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext()).apply {
            setTitle("Выход")
            setMessage("Что-то пошло не так в сети")
            setNegativeButton("Ок", null)
            show()
        }
    }
}
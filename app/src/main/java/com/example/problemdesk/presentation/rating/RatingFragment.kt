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
import com.example.problemdesk.data.sharedprefs.OLD_FCM
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.data.sharedprefs.USER_ID
import com.example.problemdesk.databinding.FragmentRatingBinding
import com.example.problemdesk.domain.models.UserRating
import kotlinx.coroutines.launch

class RatingFragment : Fragment() {

    private var _binding: FragmentRatingBinding? = null
    private val binding get() = _binding!!

    private val ratingViewModel: RatingViewModel by viewModels()

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
        binding.ratingRv.adapter = RaitingRecyclerViewAdapter(::handleUserClick)
        setUpLogOutButton()
        loadRating()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            ratingRv.isGone = true
        }
    }

    private fun showContent() {
        with(binding) {
            progressBar.isGone = true
            ratingRv.isVisible = true
        }
    }

    private fun setUpObservers() {
        ratingViewModel.ratingData.observe(viewLifecycleOwner) { userRatings: List<UserRating> ->
            val sortedRatings = sortRatingsByTokens(userRatings) // Sort the list
            (binding.ratingRv.adapter as? RaitingRecyclerViewAdapter)?.userRatings = sortedRatings
            showContent()
        }
        ratingViewModel.logoutStatus.observe(viewLifecycleOwner) { status ->
            if (status) {
                findNavController().navigate(RatingFragmentDirections.actionNavigationRatingToNavigationLogin())
                val sharedPreferences =
                    context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
                sharedPreferences?.edit()?.clear()?.apply()
            } else {
                showErrorDialog()
                findNavController().navigate(RatingFragmentDirections.actionNavigationRatingToNavigationLogin())
                val sharedPreferences =
                    context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
                sharedPreferences?.edit()?.clear()?.apply()
            }
        }
    }

    private fun sortRatingsByTokens(userRatings: List<UserRating>): List<UserRating> {
        return userRatings.sortedByDescending { it.tokens } // Sort by rating in descending order
    }

    private fun setUpLogOutButton() {
        val exitMenuItem =
            (activity as MainActivity).binding.toolbar.menu.findItem(R.id.action_exit)
        exitMenuItem?.setOnMenuItemClickListener {

            //i dont know is this a good way to use SP, cause it have troubles with context inside ViewModel
            val sharedPreferences =
                context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
            val userId = sharedPreferences?.getInt(USER_ID, 0)
            val oldFcm = sharedPreferences?.getString(OLD_FCM, "")

            if (userId != null && oldFcm != null && userId != 0 && oldFcm != "") {
                val request = LogOutRequest(userId, oldFcm)
                showLogOutConfirmationDialog(request)
            }
            true
        }
    }

    private fun loadRating() {
        showLoading()
        lifecycleScope.launch {
            ratingViewModel.loadRating()
        }
    }

    private fun handleUserClick(userRating: UserRating) {
        //TODO i dont need this
    }

    private fun showLogOutConfirmationDialog(request: LogOutRequest) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Выход из аккаунта")
            setMessage("Вы хотите выйти из своей учетной записи?")
            setPositiveButton("Да") { _, _ ->
                ratingViewModel.logOut(request)
                val exitMenuItem =
                    (activity as MainActivity).binding.toolbar.menu.findItem(R.id.action_exit)
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



    //TODO sort button

//    private fun setUpSortButton() {
//        val sortMenuItem =
//            (activity as MainActivity).binding.toolbar.menu.findItem(R.id.???)
//        sortMenuItem?.setOnMenuItemClickListener {
//              Get the current ratings
//            val currentRatings = (binding.ratingRv.adapter as? RaitingRecyclerViewAdapter)?.userRatings
//            if (currentRatings != null) {
//                  Sort the ratings
//                val sortedRatings = sortRatingsByTokens(currentRatings)
//                (binding.ratingRv.adapter as? RaitingRecyclerViewAdapter)?.userRatings = sortedRatings
//            }
//            true
//        }
//    }
}
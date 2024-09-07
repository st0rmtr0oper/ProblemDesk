package com.example.problemdesk.presentation.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.problemdesk.MainActivity
import com.example.problemdesk.R
import com.example.problemdesk.data.models.LogOutRequest
import com.example.problemdesk.data.sharedprefs.OLD_FCM
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.data.sharedprefs.USER_ID
import com.example.problemdesk.databinding.FragmentProfileBinding
import com.example.problemdesk.presentation.general.PagerAdapter
import com.example.problemdesk.presentation.profile.pagersubfragments.AwardFragment
import com.example.problemdesk.presentation.profile.pagersubfragments.ProfileInfoFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpLogOutButton()
        setUpObservers()
        setUpSubFragments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun setUpObservers() {
        profileViewModel.logoutStatus.observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToNavigationLogin())
            } else {
                showErrorDialog()
            }
        })
    }

    private fun setUpSubFragments() {
        //initiating viewPager
        viewPager = binding.profilePager
        val fragmentList = arrayListOf(
            ProfileInfoFragment.newInstance(),
            AwardFragment.newInstance()
        )
        viewPager.adapter = PagerAdapter(this, fragmentList)

        //initiating tabLayout
        tabLayout = binding.profileTabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        //sync tabLayout with viewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Мои данные"
                1 -> tab.text = "Награды"
            }
        }.attach()
    }

    private fun showLogOutConfirmationDialog(request: LogOutRequest) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Выход из аккаунта")
            setMessage("Вы хотите выйти из своей учетной записи?")
            setPositiveButton("Да") { _, _ ->
                profileViewModel.logOut(request)
            }
            setNegativeButton("Нет", null)
            show()
        }
    }

    private fun showErrorDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext()).apply {
            setTitle("Ошибка")
            setMessage("Упс... Что-то пошло не так")
            setNegativeButton("Ок", null)
            show()
        }
    }
}
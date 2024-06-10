package com.example.problemdesk.presentation.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfilePagerAdapter(fragment: Fragment, private val fragments: List<Fragment>): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment = fragments[position]

    override fun getItemCount(): Int = fragments.size
}
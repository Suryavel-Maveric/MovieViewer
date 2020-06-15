package io.github.xvelx.movieviewer.fragments

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract val screenTitle: String

    override fun onStart() {
        super.onStart()
        activity?.title = screenTitle
    }
}
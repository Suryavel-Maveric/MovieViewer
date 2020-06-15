package io.github.xvelx.movieviewer.fragments

import androidx.fragment.app.Fragment
import io.github.xvelx.movieviewer.activities.BaseActivity

abstract class BaseFragment : Fragment() {

    abstract val screenTitle: String

    override fun onStart() {
        super.onStart()
        activity?.title = screenTitle
    }

    fun showLoading() = (activity as? BaseActivity)?.showLoading();

    fun hideLoading() = (activity as? BaseActivity)?.hideLoading();
}
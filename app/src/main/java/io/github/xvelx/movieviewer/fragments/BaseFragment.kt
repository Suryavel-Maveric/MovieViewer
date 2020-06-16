package io.github.xvelx.movieviewer.fragments

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.github.xvelx.movieviewer.activities.BaseActivity

abstract class BaseFragment : Fragment() {

    abstract val screenTitle: String

    override fun onStart() {
        super.onStart()
        activity?.title = screenTitle
    }

    fun showLoading() = (activity as? BaseActivity)?.showLoading()

    fun hideLoading() = (activity as? BaseActivity)?.hideLoading()

    fun showSnackBarWithMessage(message: String) {
        view?.run {
            Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun showSnackBarWithMessage(@StringRes stringRes: Int) {
        showSnackBarWithMessage(getString(stringRes))
    }
}
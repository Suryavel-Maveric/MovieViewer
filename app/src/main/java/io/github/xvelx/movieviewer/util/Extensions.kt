package io.github.xvelx.movieviewer.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import io.github.xvelx.movieviewer.vm.BaseViewModel
import io.reactivex.disposables.Disposable

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun <T : BaseViewModel> Disposable.addToDisposableBucket(viewModel: T) {
    viewModel.compositeDisposable.add(this)
}
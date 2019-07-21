package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
}


fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    val rootView = window.decorView
    rootView.getWindowVisibleDisplayFrame(r)
    val screenHeight = rootView.height
    var heightDiff = screenHeight - (r.bottom - r.top)
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        heightDiff -= resources.getDimensionPixelSize(resourceId)
    }
    if (heightDiff > 100) {
        return true
    }
    return false
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()
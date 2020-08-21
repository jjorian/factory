package ir.bokaeyan.factory.supporthelper

import android.content.Context
import android.graphics.Typeface
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import java.text.DecimalFormat


fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(getActivity(), message, duration).show()
}
fun Fragment.getBenyaminFont():Typeface {
    val tf = Typeface.createFromAsset(this.requireContext().assets, "fonts/Benyamin.TTF")
    return tf
}

fun AppCompatActivity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
fun Double.getCurrecncy(): String {
    val df = DecimalFormat("#,###.###")
    return df.format(this)
}

fun Fragment.setToolBarTitle(title: String?)
{
    val act = activity as AppCompatActivity?
    if (act?.supportActionBar != null) {
        act.supportActionBar?.title=title
    }

}
fun AppCompatActivity.WorkAfterTime(delay: Long, process: () -> Unit) {
    Handler().postDelayed({
        process()
    }, delay)
}
fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}
fun TabLayout.changeTabsFont() {
    val tf = Typeface.createFromAsset(this.context.assets, "fonts/Benyamin.TTF")
    val vg = this.getChildAt(0) as ViewGroup
    val tabsCount = vg.childCount
    for (j in 0 until tabsCount) {
        val vgTab = vg.getChildAt(j) as ViewGroup
        val tabChildsCount = vgTab.childCount
        for (i in 0 until tabChildsCount) {
            val tabViewChild = vgTab.getChildAt(i)
            if (tabViewChild is TextView) {
                tabViewChild.setTypeface(tf, Typeface.NORMAL)
            }
        }
    }
}
fun Toolbar.changeToolbarFont(){
    val tf = Typeface.createFromAsset(this.context.assets, "fonts/Benyamin.TTF")

    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView) {
            view.typeface = tf
            break
        }
    }
}
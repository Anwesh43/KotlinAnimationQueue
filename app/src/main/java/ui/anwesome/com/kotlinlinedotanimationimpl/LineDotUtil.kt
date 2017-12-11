package ui.anwesome.com.kotlinlinedotanimationimpl

import android.app.Activity
import ui.anwesome.com.kotlindotlineanimation.LineDotView

/**
 * Created by anweshmishra on 11/12/17.
 */
class LineDotUtil {
    companion object {
        fun create(activity: Activity):LineDotView {
            var view = LineDotView.create(activity)
            return view
        }
 }
}
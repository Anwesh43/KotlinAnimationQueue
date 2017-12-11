package ui.anwesome.com.kotlinlinedotanimationimpl

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import ui.anwesome.com.kotlindotlineanimation.LineDotView

/**
 * Created by anweshmishra on 11/12/17.
 */
class LineDotUtil {
    companion object {
        fun create(activity: AppCompatActivity):LineDotView {
            var view = LineDotView.create(activity)
            return view
        }
    }
}
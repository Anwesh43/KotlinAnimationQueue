package ui.anwesome.com.kotlinlinedotanimationimpl

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import ui.anwesome.com.colorchooserbar.ColorChooserView
import ui.anwesome.com.kotlindotlineanimation.LineDotView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val view = LineDotUtil.create(this)
//        view.addMovementListener({
//            Toast.makeText(this,"moved left",Toast.LENGTH_SHORT).show()
//        },{
//            Toast.makeText(this,"moved right",Toast.LENGTH_SHORT).show()
//        })
        val lineDot:LineDotView = findViewById(R.id.line_dot) as LineDotView
        val chooser:ColorChooserView = findViewById(R.id.chooser) as ColorChooserView
        chooser.addColorChoosenListener { color ->
            lineDot.lineDotColor = color
        }
        fullScreen()
    }
}
fun AppCompatActivity.fullScreen() {
    var actionBar = supportActionBar
    actionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

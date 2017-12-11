package ui.anwesome.com.kotlinlinedotanimationimpl

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val view = LineDotUtil.create(this)
        view.addMovementListener({
            Toast.makeText(this,"moved left",Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(this,"moved right",Toast.LENGTH_SHORT).show()
        })
    }
}

package ui.anwesome.com.colorchooserbar

/**
 * Created by anweshmishra on 12/12/17.
 */
import android.view.*
import android.content.*
import android.graphics.*
import android.util.AttributeSet

class ColorChooserView:View {
    constructor(ctx:Context):super(ctx)
    constructor(ctx: Context,attrs:AttributeSet):super(ctx,attrs)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas: Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                
            }
        }
        return true
    }
}
package ui.anwesome.com.colorchooserbar

/**
 * Created by anweshmishra on 12/12/17.
 */
import android.view.*
import android.content.*
import android.graphics.*
import android.util.AttributeSet
val colors:Array<String> = arrayOf("#673AB7","#448AFF","#FF5722","#f44336","#1A237E","#AD1457")
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
    data class ColorChooserCircle(var i:Int,var x:Float,var y:Float,var r:Float,var or:Float = r) {
        fun draw(canvas:Canvas,paint:Paint) {
            paint.color = Color.parseColor(colors[i])
            canvas.drawCircle(x,y,r,paint)
        }
        fun increaseR(scale:Float,mr:Float) {
            r = or+(mr-or)*scale
        }
        fun decreaseR(scale:Float,mr:Float) {
            r = or+(mr-or)*scale
        }
        fun handleTap(x:Float,y:Float):Boolean = x>=this.x -r && x<=this.x+r && y<=this.y -r && y>=this.y+r
    }
}
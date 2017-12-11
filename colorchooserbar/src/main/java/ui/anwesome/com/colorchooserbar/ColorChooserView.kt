package ui.anwesome.com.colorchooserbar

/**
 * Created by anweshmishra on 12/12/17.
 */
import android.view.*
import android.content.*
import android.graphics.*
import android.util.AttributeSet
import java.util.concurrent.ConcurrentLinkedQueue

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
    data class ColorChooserCircle(var i:Int,var x:Float,var y:Float,var r:Float,var or:Float = r):Comparable<ColorChooserCircle> {
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
        override fun compareTo(other:ColorChooserCircle):Int = (x-other.x).toInt()
    }
    data class ColorChooserContainer(var w:Float,var h:Float,var gap:Float = Math.min(w/(2* colors.size+1),h/2)) {
        var circles:ConcurrentLinkedQueue<ColorChooserCircle> = ConcurrentLinkedQueue()
        var prev:ColorChooserCircle?=null
        var curr:ColorChooserCircle?=null
        var state = ColorChooserState()
        init {
            if(colors.size > 0) {
                var x = 3*gap/2
                for (i in 0..colors.size - 1) {
                    circles.add(ColorChooserCircle(i,x,h/2,gap/4))
                    x+=2*gap
                }
            }
        }
        fun draw(canvas: Canvas,paint:Paint) {
            circles.forEach { circle ->
                circle.draw(canvas,paint)
            }
        }
        fun update(stopcb:(Int)->Unit) {
            state.update({ scale ->
                curr?.increaseR(scale,gap/2)
                prev?.decreaseR(scale,gap/2)
            },{
                stopcb(Color.parseColor(colors[curr?.i?:0]))
                prev = curr
            })
        }
        fun handleTap(x:Float,y:Float,startcb:()->Unit) {
            circles.forEach { circle ->
                circle.handleTap(x,y)
                curr = circle
                startcb()
            }
        }
    }
    data class ColorChooserState(var scale:Float = 0f) {
        fun update(updatecb:(Float)->Unit,stopcb:()->Unit) {
            scale+=0.1f
            updatecb(scale)
            if(scale > 1){
                scale = 1f
                stopcb()
            }
        }
    }
    class ColorChooserAnimator(var container:ColorChooserContainer,var view:ColorChooserView) {
        var animated = false
        fun update() {
            if(animated) {
                container.update { color->
                    animated = false
                }
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex:Exception) {

                }
            }
        }
        fun draw(canvas: Canvas,paint: Paint) {
            canvas.drawColor(Color.parseColor("#9E9E9E"))
            container.draw(canvas,paint)
        }
        fun handleTap(x:Float,y:Float) {
            container.handleTap(x,y,{
                animated = true
                view.postInvalidate()
            })
        }
    }
    class ColorChooserRenderer(var view:ColorChooserView,var time:Int = 0) {
        var animator:ColorChooserAnimator?=null
        fun render(canvas: Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                animator = ColorChooserAnimator(ColorChooserContainer(w,h),view)
            }
            animator?.draw(canvas,paint)
            animator?.update()
            time++
        }
        fun handleTap(x:Float,y:Float) {
            animator?.handleTap(x,y)
        }
    }
}
fun ConcurrentLinkedQueue<ColorChooserView.ColorChooserCircle>.at(j:Int):ColorChooserView.ColorChooserCircle? {
    var i = 0
    forEach {
        if(i == j) {
            return it
        }
        i++
    }
    return null
}
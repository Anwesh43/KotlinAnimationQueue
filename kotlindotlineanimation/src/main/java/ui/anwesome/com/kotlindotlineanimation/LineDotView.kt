package ui.anwesome.com.kotlindotlineanimation

/**
 * Created by anweshmishra on 11/12/17.
 */
import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*
import android.util.AttributeSet
import java.util.concurrent.ConcurrentLinkedQueue

class LineDotView:View {
    constructor(ctx:Context):super(ctx)
    constructor(ctx:Context,attrs:AttributeSet):super(ctx,attrs)
    val paint:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    var onMovementListener:OnMovementListener?=null
    override fun onDraw(canvas:Canvas) {
        canvas.drawColor(Color.parseColor("#212121"))
        renderer.render(canvas,paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.startAnimation()
            }
        }
        return true
    }
    fun addMovementListener(onLeft: () -> Unit,onRight: () -> Unit) {
        onMovementListener = OnMovementListener(onLeft,onRight)
    }
    data class LineDot(var x:Float,var y:Float,var or:Float,var r:Float=0f) {
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.drawCircle(x,y,r,paint)
        }
        var increaseR:(Float)->Unit =  {scale ->
            r = or*scale
        }
        var decreaseR:(Float)->Unit = { scale ->
            r = or*(1-scale)
        }
    }
    data class LineDotContainer(var w:Float,var h:Float) {
        var lineDots:ConcurrentLinkedQueue<LineDot> = ConcurrentLinkedQueue()
        var fx = 0f
        var lx = 0f
        var mx = 0f
        var ox = 0f
        init {
            for(i in 0..1) {
                lineDots.add(LineDot((w/2-w/4)+w/2*i,h/2,Math.min(w,h)/10))
            }
            if(lineDots.size == 2) {
                fx = lineDots.first().x
                lx = lineDots.first().x
                mx = (lineDots.last().x-fx)
                ox = fx
                lineDots.first().r = lineDots.first().or
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            paint.color = Color.parseColor("#2979FF")
            lineDots.forEach { lineDot ->
                lineDot.draw(canvas,paint)
            }
            if(lineDots.size == 2) {
                paint.strokeCap = Paint.Cap.ROUND
                var first = lineDots.first()
                var last = lineDots.last()
                paint.strokeWidth = Math.min(w,h)/50
                canvas.drawLine(fx,first.y,lx,first.y,paint)
            }
        }
        var  incrementFx:(Float)->Unit = { scale ->
            fx = ox + mx*scale
        }
        var  incrementLx:(Float)->Unit = {scale ->
            lx = ox+mx*scale
        }
        fun startAnimation(queue:AnimationQueue) {
            var first = lineDots.first()
            var last = lineDots.last()
            queue.addAnimation(first.decreaseR,incrementLx,incrementFx,last.increaseR)
        }
    }
    data class Renderer(var view:LineDotView,var time:Int = 0) {
        var container:LineDotContainer?=null
        var queue = AnimationQueue(view)
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                container = LineDotContainer(w,h)
            }
            container?.draw(canvas,paint)
            queue.update({ dir ->
                when(dir) {
                    1 -> view.onMovementListener?.onLeft?.invoke()
                    -1 -> view.onMovementListener?.onRight?.invoke()
                }
            })
            time++
        }
        fun startAnimation() {
            container?.startAnimation(queue)
        }
    }
    companion object {
        fun create(activity:Activity):LineDotView {
            var view = LineDotView(activity)
            activity.setContentView(view)
            return view
        }
    }
    data class OnMovementListener(var onLeft:()->Unit,var onRight:()->Unit)
}
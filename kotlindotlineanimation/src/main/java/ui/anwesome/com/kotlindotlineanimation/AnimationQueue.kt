package ui.anwesome.com.kotlindotlineanimation

/**
 * Created by anweshmishra on 11/12/17.
 */
import android.view.View
import java.util.concurrent.*
class AnimationQueue(var view:View) {
    var animated = false
    var container = AnimationQueueContainer()
    fun update() {
        if(animated) {
            container.update()
            if(container.stopped()) {
                animated = false
            }
            try {
                Thread.sleep(50)
                view.invalidate()
            } catch (ex: Exception) {

            }
        }
    }
    fun addAnimation(vararg cbs:(Float)->Unit) {
        if(!animated) {
            cbs.forEach {
                container.addAnimation(AnimationState.instance(it))
            }
            animated = true
            view.postInvalidate()
        }
    }
}
class AnimationQueueContainer {
    var animContainer:ConcurrentLinkedQueue<AnimationState> = ConcurrentLinkedQueue()
    fun addAnimation(state:AnimationState) {
        animContainer.add(state)
    }
    fun update() {
        animContainer.at(0)?.update()
        if(animContainer?.at(0)?.stopped()?:false) {
            animContainer.remove(animContainer?.at(0))
        }
    }
    fun stopped():Boolean = animContainer.size == 0
}
data class AnimationState(var scale:Float = 0f,var dir:Float = 0f,var cb:(Float)->Unit,var prevScale:Float = 0f){
    fun update(){
        scale += 0.1f*dir
        if(Math.abs(scale-prevScale) > 1) {
            scale = prevScale+dir
            dir = 0f
            prevScale = scale
        }
        cb(scale)
    }
    fun stopped():Boolean = dir == 0f
    companion object {
        fun instance(cb:(Float)->Unit):AnimationState {
            return AnimationState(cb=cb)
        }
    }
}
fun ConcurrentLinkedQueue<AnimationState>.at(j:Int):AnimationState? {
    var i = 0
    this.forEach {
        if(i == j) {
            return it
        }
        i++
    }
    return null
}
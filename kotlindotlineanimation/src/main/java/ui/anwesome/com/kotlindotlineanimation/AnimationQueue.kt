package ui.anwesome.com.kotlindotlineanimation

/**
 * Created by anweshmishra on 11/12/17.
 */
import android.view.View
import java.util.concurrent.*
class AnimationQueue(var view:View) {
    var animated = false
    var container = AnimationQueueContainer()
    fun update(stopcb:(Int)->Unit) {
        if(animated) {
            container.update(stopcb)
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
            if(container.animContainer.size == 0) {
                cbs.forEach {
                    container.addAnimation(AnimationState.instance(it))
                }
            }
            container.start()
            animated = true
            view.postInvalidate()
        }
    }
}
class AnimationQueueContainer {
    var j = 0
    var prevDir = 1
    var dir = 0
    var animContainer:ConcurrentLinkedQueue<AnimationState> = ConcurrentLinkedQueue()
    fun addAnimation(state:AnimationState) {
        animContainer.add(state)
    }
    fun start() {
        dir = prevDir
        animContainer?.at(j)?.startUpdating()
    }
    fun update(stopcb:(Int)->Unit) {
        animContainer.at(j)?.update()
        if(animContainer?.at(j)?.stopped()?:false) {
            j+=dir
            if(j == animContainer.size || j == -1) {
                prevDir*=-1
                j+=prevDir
                dir = 0
                stopcb(prevDir)
                if(j == 0) {
                    animContainer = ConcurrentLinkedQueue()
                }
            }
            else {
                animContainer.at(j)?.startUpdating()
            }
        }
    }
    fun stopped():Boolean = dir == 0
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
    fun startUpdating() {
        dir = 1-2*scale
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
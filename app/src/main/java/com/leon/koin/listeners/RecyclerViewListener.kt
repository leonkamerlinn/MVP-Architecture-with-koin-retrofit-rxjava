package com.leon.koin.listeners

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by Leon on 22.11.2017..
 */

open class RecyclerViewListener(private val mContext: Context, private val mRecyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {
    private val mGestureDetector: GestureDetector


    init {
        mGestureDetector = GestureDetector(mContext, GestureListener())
    }


    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        val position = rv.getChildAdapterPosition(child)
        mGestureDetector.onTouchEvent(e)

        if (e.action == MotionEvent.ACTION_MOVE) {
            onMove(child, position, e)
        }

        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }


    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            val child = mRecyclerView.findChildViewUnder(e.x, e.y)
            val position = mRecyclerView.getChildAdapterPosition(child)
            this@RecyclerViewListener.onDown(child, position, e)
            return super.onDown(e)
        }

        override fun onContextClick(e: MotionEvent): Boolean {
            return super.onContextClick(e)
        }


        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val child = mRecyclerView.findChildViewUnder(e.x, e.y)
            val position = mRecyclerView.getChildAdapterPosition(child)
            this@RecyclerViewListener.onSingleTapUp(child, position)
            return super.onSingleTapUp(e)
        }

        override fun onLongPress(e: MotionEvent) {
            val child = mRecyclerView.findChildViewUnder(e.x, e.y)
            val position = mRecyclerView.getChildAdapterPosition(child)
            this@RecyclerViewListener.onLongPress(child, position)
            super.onLongPress(e)
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                    }
                    result = true
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                }
                result = true

            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }
    }

    open fun onLongPress(child: View, position: Int) {}

    open fun onSingleTapUp(child: View, position: Int) {}

    open fun onDown(child: View, position: Int, event: MotionEvent) {}

    open fun onMove(child: View, position: Int, event: MotionEvent) {}

    open fun onSwipeRight() {}

    open fun onSwipeLeft() {}

    open fun onSwipeTop() {}

    open fun onSwipeBottom() {}

    companion object {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
    }


}

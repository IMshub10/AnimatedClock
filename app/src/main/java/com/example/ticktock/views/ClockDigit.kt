package com.example.ticktock.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.ticktock.R
import java.util.*
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class ClockDigit constructor(
    private var con: Context,
    attrs: AttributeSet?,
) : View(con, attrs) {

    constructor (con: Context) : this(con, null) {
        this.con = con
    }

    private var typedArray: TypedArray
    private var typeOfTimeUnit: Int = TIME_UNIT_SECONDS
    private var strokeColor: Int = R.color.black
    private val columnCount = 5
    private val rowCount = 6
    private var circlePaint: Paint
    private var linePaint: Paint

    companion object {
        private const val TIME_UNIT_SECONDS = 0
        private const val TIME_UNIT_MINUTES = 1
        private const val TIME_UNIT_HOURS = 2
    }

    init {
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClockDigit)
        typeOfTimeUnit = typedArray.getInteger(R.styleable.ClockDigit_time_unit, 0)
        strokeColor = typedArray.getResourceId(R.styleable.ClockDigit_strokeColor, R.color.black)
        circlePaint = Paint()
        linePaint = Paint()
        circlePaint.run {
            color = resources.getColor(strokeColor)
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
        linePaint.run {
            color = resources.getColor(strokeColor)
            style = Paint.Style.STROKE
            strokeWidth = 3f
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            (MeasureSpec.getSize(widthMeasureSpec) * 0.6).toInt()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val circleWidth: Float = (width / 20).toFloat()
        val calendar = Calendar.getInstance()
        for (column in 0 until columnCount) {
            for (row in 0 until rowCount) {
                drawTimeUnit(
                    canvas,
                    calendar,
                    circleWidth,
                    column,
                    row
                )
            }
        }
        /*val animation = ValueAnimator.ofInt(canvas!!.width, 0, canvas!!.width)
        animation.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            //Clear the canvas
            canvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            canvas.drawBitmap(chefBitmap, 0, 0, null)
            canvas!!.save()
            canvas!!.translate(value.toFloat(), 0)
            canvas.drawBitmap(starBitmap, 0, 0, null)
            canvas!!.restore()
            //Need to manually call invalidate to redraw the view
            mLittleChef.invalidate()
        }
        animation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                simpleLock = false
            }
        })
        animation.interpolator = LinearInterpolator()
        animation.duration = mShortAnimationDuration
        animation.start()*/
    }

    private fun drawTimeUnit(
        canvas: Canvas?,
        calendar: Calendar,
        circleWidth: Float,
        column: Int,
        row: Int
    ) {
        drawTimeUnitFirstDigit(
            canvas,
            calendar,
            circleWidth,
            column,
            row
        )
        drawTimeUnitSecondDigit(
            canvas,
            calendar,
            circleWidth,
            column,
            row
        )
    }

    private fun drawTimeUnitFirstDigit(
        canvas: Canvas?,
        calendar: Calendar,
        circleWidth: Float,
        column: Int,
        row: Int
    ) {
        val gridIndex: Int = column + (row * columnCount)
        val circleCenter = getCircleCenter(circleWidth, row, column, 0)
        drawCircle(canvas, circleCenter, circleWidth)
        drawFirstLine(
            canvas,
            gridIndex,
            circleCenter,
            circleWidth,
            calendar[when (typeOfTimeUnit) {
                TIME_UNIT_SECONDS -> {
                    Calendar.SECOND
                }
                TIME_UNIT_MINUTES -> {
                    Calendar.MINUTE
                }
                TIME_UNIT_HOURS -> {
                    Calendar.HOUR_OF_DAY
                }
                else -> {
                    Calendar.SECOND
                }
            }] / 10
        )
        drawSecondLine(
            canvas,
            gridIndex,
            circleCenter,
            circleWidth,
            calendar[when (typeOfTimeUnit) {
                TIME_UNIT_SECONDS -> {
                    Calendar.SECOND
                }
                TIME_UNIT_MINUTES -> {
                    Calendar.MINUTE
                }
                TIME_UNIT_HOURS -> {
                    Calendar.HOUR_OF_DAY
                }
                else -> {
                    Calendar.SECOND
                }
            }] / 10
        )
    }

    private fun drawTimeUnitSecondDigit(
        canvas: Canvas?,
        calendar: Calendar,
        circleWidth: Float,
        column: Int,
        row: Int
    ) {
        val gridIndex: Int = column + (row * columnCount)
        val circleCenter = getCircleCenter(circleWidth, row, column, 1)
        drawCircle(canvas, circleCenter, circleWidth)
        drawFirstLine(
            canvas,
            gridIndex,
            circleCenter,
            circleWidth,
            calendar[when (typeOfTimeUnit) {
                TIME_UNIT_SECONDS -> {
                    Calendar.SECOND
                }
                TIME_UNIT_MINUTES -> {
                    Calendar.MINUTE
                }
                TIME_UNIT_HOURS -> {
                    Calendar.HOUR_OF_DAY
                }
                else -> {
                    Calendar.SECOND
                }
            }] % 10
        )
        drawSecondLine(
            canvas,
            gridIndex,
            circleCenter,
            circleWidth,
            calendar[when (typeOfTimeUnit) {
                TIME_UNIT_SECONDS -> {
                    Calendar.SECOND
                }
                TIME_UNIT_MINUTES -> {
                    Calendar.MINUTE
                }
                TIME_UNIT_HOURS -> {
                    Calendar.HOUR_OF_DAY
                }
                else -> {
                    Calendar.SECOND
                }
            }] % 10
        )
    }

    /*private fun drawMinutes(
        canvas: Canvas?,
        calendar: Calendar,
        circleWidth: Float,
        column: Int,
        row: Int,
        columnCount: Int,
        circlePaint: Paint,
        linePaint: Paint
    ) {
        drawFirstMinuteDigit(
            canvas,
            calendar,
            circleWidth,
            column,
            row,
            columnCount,
            circlePaint,
            linePaint
        )
        drawSecondMinuteDigit(
            canvas,
            calendar,
            circleWidth,
            column,
            row,
            columnCount,
            circlePaint,
            linePaint
        )
    }

    private fun drawFirstMinuteDigit(
        canvas: Canvas?,
        calendar: Calendar,
        circleWidth: Float,
        column: Int,
        row: Int,
        columnCount: Int,
        circlePaint: Paint,
        linePaint: Paint
    ) {
        val gridIndex: Int = column + (row * columnCount)
        val circleCenter = getCircleCenter(circleWidth, row, column, 0)
        drawCircle(canvas, circleCenter, circleWidth, circlePaint)
        drawFirstLine(
            canvas,
            gridIndex,
            circleCenter,
            circleWidth,
            calendar[Calendar.MINUTE] / 10, linePaint
        )
        drawSecondLine(
            canvas,
            gridIndex,
            circleCenter,
            circleWidth,
            calendar[Calendar.MINUTE] / 10, linePaint
        )
    }

    private fun drawSecondMinuteDigit(
        canvas: Canvas?,
        calendar: Calendar,
        circleWidth: Float,
        column: Int,
        row: Int,
        columnCount: Int,
        circlePaint: Paint,
        linePaint: Paint
    ) {
        val gridIndex: Int = column + (row * columnCount)
        val circleCenter = getCircleCenter(circleWidth, row, column, 1)
        drawCircle(canvas, circleCenter, circleWidth, circlePaint)
        drawFirstLine(
            canvas,
            gridIndex,
            circleCenter,
            circleWidth,
            calendar[Calendar.MINUTE] % 10, linePaint
        )
        drawSecondLine(
            canvas,
            gridIndex,
            circleCenter,
            circleWidth,
            calendar[Calendar.MINUTE] % 10, linePaint
        )
    }
*/
    private fun getCircleCenter(
        circleWidth: Float,
        row: Int,
        column: Int,
        isFirstDigit: Int
    ): CircleCenter {
        return CircleCenter(
            ((column * circleWidth * 2) + (circleWidth) + (isFirstDigit * (width / 2))),
            ((row * circleWidth * 2) + circleWidth)
        )
    }

    private fun drawFirstLine(
        canvas: Canvas?,
        gridIndex: Int,
        circleCenter: CircleCenter,
        circleWidth: Float,
        digit: Int,
    ) {
        val angle = rotationData[gridIndex][digit].first.toDouble()
        val radians = Math.toRadians(angle)
        val sinValue = sin(radians)
        var cosValue = sqrt(1 - sinValue.pow(2))
        if (angle in 90.0..180.0) {
            cosValue = -cosValue
        }
        canvas?.drawLine(
            circleCenter.x,
            circleCenter.y,
            (circleCenter.x + (circleWidth * cosValue).toFloat()),
            (circleCenter.y + (circleWidth * sinValue).toFloat()),
            linePaint
        )
    }

    private fun drawSecondLine(
        canvas: Canvas?,
        gridIndex: Int,
        circleCenter: CircleCenter,
        circleWidth: Float,
        digit: Int,
    ) {
        val angle = rotationData[gridIndex][digit].second.toDouble()
        val radians = Math.toRadians(angle)
        val sinValue = sin(radians)
        var cosValue = sqrt(1 - sinValue.pow(2))
        if (angle in 90.0..180.0) {
            cosValue = -cosValue
        }
        canvas?.drawLine(
            circleCenter.x,
            circleCenter.y,
            (circleCenter.x + (circleWidth * cosValue).toFloat()),
            (circleCenter.y + (circleWidth * sinValue).toFloat()),
            linePaint
        )
    }

    private fun drawCircle(
        canvas: Canvas?,
        circleCenter: CircleCenter,
        circleWidth: Float,
    ) {
        canvas?.drawCircle(
            circleCenter.x,
            circleCenter.y,
            circleWidth,
            circlePaint
        )
    }
}

data class CircleCenter(var x: Float, var y: Float)

val rotationData = listOf(
//1
    listOf(
        Pair(90f, 0f),
        Pair(135f, 135f),
        Pair(90f, 0f),
        Pair(90f, 0f),
        Pair(90f, 0f),
        Pair(90f, 0f),
        Pair(90f, 0f),
        Pair(90f, 0f),
        Pair(90f, 0f),
        Pair(90f, 0f),
    ),
    listOf(
        Pair(180f, 0f),
        Pair(90f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(135f, 135f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(180f, 0f),
        Pair(180f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(135f, 135f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(180f, 90f),
        Pair(135f, 135f),
        Pair(180f, 90f),
        Pair(180f, 90f),
        Pair(135f, 135f),
        Pair(180f, 90f),
        Pair(180f, 90f),
        Pair(180f, 90f),
        Pair(180f, 90f),
        Pair(180f, 90f)
    ),
//2
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 0f),
        Pair(270f, 0f),
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(270f, 0f),
        Pair(270f, 90f),
        Pair(270f, 90f),
    ),
    listOf(
        Pair(90f, 0f),
        Pair(270f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 90f),
        Pair(90f, 0f),
        Pair(90f, 0f),
        Pair(180f, 0f),
        Pair(90f, 0f),
        Pair(90f, 0f)
    ),
    listOf(
        Pair(180f, 0f),
        Pair(180f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(135f, 135f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(180f, 90f),
        Pair(270f, 90f),
        Pair(180f, 90f),
        Pair(180f, 90f),
        Pair(135f, 135f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 135f),
        Pair(180f, 90f),
        Pair(180f, 90f),
    ),
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 180f),
        Pair(270f, 180f),
        Pair(270f, 135f),
        Pair(270f, 90f),
        Pair(270f, 90f)
    ),
//3
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(90f, 0f),
        Pair(90f, 0f),
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(270f, 90f),
    ),
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 90f),
        Pair(270f, 0f),
        Pair(270f, 0f),
        Pair(135f, 135f),
        Pair(270f, 0f),
        Pair(270f, 0f)
    ),
    listOf(
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(90f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(315f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(270f, 180f),
        Pair(270f, 180f),
        Pair(180f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(315f, 90f),
        Pair(270f, 180f),
        Pair(270f, 180f)
    ),
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(180f, 90f),
        Pair(180f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(270f, 90f),
    ),
//4
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(270f, 0f),
        Pair(270f, 90f),
        Pair(270f, 0f),
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(270f, 0f),
    ),
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(90f, 0f),
        Pair(180f, 0f),
        Pair(270f, 0f),
        Pair(180f, 0f),
        Pair(90f, 0f),
        Pair(135f, 135f),
        Pair(90f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 180f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(180f, 0f),
        Pair(180f, 90f),
        Pair(270f, 0f),
        Pair(180f, 90f),
        Pair(180f, 90f),
        Pair(270f, 90f),
        Pair(180f, 90f),
        Pair(180f, 90f)
    ),
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 180f),
        Pair(270f, 90f),
        Pair(180f, 90f),
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(270f, 90f),
    ),
//5
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(90f, 0f),
        Pair(270f, 0f),
        Pair(90f, 0f),
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(90f, 0f)
    ),
    listOf(
        Pair(270f, 0f),
        Pair(135f, 135f),
        Pair(270f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 0f),
        Pair(135f, 135f),
        Pair(270f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(180f, 0f),
        Pair(270f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(180f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 90f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(270f, 180f),
        Pair(270f, 90f),
        Pair(180f, 0f),
        Pair(270f, 180f),
        Pair(90f, 0f),
        Pair(270f, 180f),
        Pair(270f, 180f),
        Pair(270f, 90f),
        Pair(270f, 180f),
        Pair(270f, 180f)
    ),
    listOf(
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(180f, 90f),
        Pair(270f, 90f),
        Pair(270f, 180f),
        Pair(270f, 90f),
        Pair(270f, 90f),
        Pair(135f, 135f),
        Pair(270f, 90f),
        Pair(270f, 90f)
    ),
//6
    listOf(
        Pair(270f, 0f),
        Pair(135f, 135f),
        Pair(270f, 0f),
        Pair(270f, 0f),
        Pair(135f, 135f),
        Pair(270f, 0f),
        Pair(270f, 0f),
        Pair(135f, 135f),
        Pair(270f, 0f),
        Pair(270f, 0f)
    ),
    listOf(
        Pair(180f, 0f),
        Pair(135f, 135f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(135f, 135f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(135f, 135f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(180f, 0f),
        Pair(270f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 0f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(180f, 0f),
        Pair(270f, 180f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 180f),
        Pair(180f, 0f),
        Pair(180f, 0f),
        Pair(270f, 180f),
        Pair(180f, 0f),
        Pair(180f, 0f)
    ),
    listOf(
        Pair(270f, 180f),
        Pair(135f, 135f),
        Pair(270f, 180f),
        Pair(270f, 180f),
        Pair(135f, 135f),
        Pair(270f, 180f),
        Pair(270f, 180f),
        Pair(135f, 135f),
        Pair(270f, 180f),
        Pair(270f, 180f),
    )
)
package com.example.ttymyday.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.ttymyday.R
import kotlinx.android.synthetic.main.sample_card_view.view.*

/**
 * TODO: document your custom view class.
 */
class CardView : android.support.v7.widget.CardView {
    constructor(context:Context):super(context){
        LayoutInflater.from(context).inflate(R.layout.sample_card_view,this)
        init(null,0)
    }
    constructor(context:Context,attrs:AttributeSet?):super(context,attrs){
        LayoutInflater.from(context).inflate(R.layout.sample_card_view,this)
        init(attrs,0)
    }
    constructor(context:Context,attrs: AttributeSet?,defStype:Int):super(context,attrs,defStype){
        LayoutInflater.from(context).inflate(R.layout.sample_card_view,this)
        init(attrs,defStype)
    }

    fun init(attrs: AttributeSet?,defStype: Int)
    {
        var a = context.obtainStyledAttributes(attrs,R.styleable.CardView,defStype,0)
        widget_card_view_tbx_display_title.text = a.getString(R.styleable.CardView_display_title)
    }
}

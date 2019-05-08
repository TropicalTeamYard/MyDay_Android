package com.example.ttymyday.listener

import android.view.View

interface OnRItemClickListener: View.OnClickListener {
    fun onItemClick(v:View?,position: Int)
}
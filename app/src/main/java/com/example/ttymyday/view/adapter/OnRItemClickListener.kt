package com.example.ttymyday.view.adapter

import android.view.View

interface OnRItemClickListener: View.OnClickListener {
    fun onItemClick(v:View?,position: Int)
}
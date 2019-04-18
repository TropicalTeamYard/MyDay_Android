package com.example.ttymyday.util

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class NameLengthFilter(internal var MAX_EN: Int) : InputFilter {
    internal var regEx = "[\\u4e00-\\u9fa5]"

    override fun filter(
        source: CharSequence, start: Int, end: Int,
        dest: Spanned, dstart: Int, dend: Int
    ): CharSequence {
        val destCount = dest.toString().length + getChineseCount(dest.toString())
        val sourceCount = source.toString().length + getChineseCount(source.toString())
        if (destCount + sourceCount > MAX_EN) {
            var surplusCount = MAX_EN - destCount
            var result = ""
            var index = 0
            while (surplusCount > 0) {
                val c = source[index]
                if (isChinest(c + "")) {
                    if (sourceCount >= 2) {
                        result += c
                    }
                    surplusCount = surplusCount - 2
                } else {
                    result += c
                    surplusCount = surplusCount - 1
                }
                index++
            }
            return result
        } else {
            return source
        }
    }

    private fun getChineseCount(str: String): Int {
        var count = 0
        val p = Pattern.compile(regEx)
        val m = p.matcher(str)
        while (m.find()) {
            for (i in 0..m.groupCount()) {
                count = count + 1
            }
        }
        return count
    }

    private fun isChinest(source: String): Boolean {
        return Pattern.matches(regEx, source)
    }
}
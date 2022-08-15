package com.example.ticktock.utils

object DateUtils {
    fun getIn2Format(value: Int): String {
        if (value > 99) {
            throw  RuntimeException("Does not support more than 2 digits");
        }
        return if (value < 10) {
            "0$value";
        } else {
            value.toString()
        }
    }
}
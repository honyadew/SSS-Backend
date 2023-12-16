package com.honya.utils

fun String.isValidEmail() : Boolean {
    val containsAtSymbol = this.count { it == '@' } == 1

    if (containsAtSymbol){
        val split = this.split('@')

        val containsDot = split[1].contains(".") && split[1].indexOf(".") == split[1].lastIndexOf(".")

        if (containsDot){

            val splitParts = listOf(split[0]) + split[1].split('.')

            val isCorrect = splitParts.all { it.length in (1..30) }

            val isValidLocalPart = split[0].matches("[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+".toRegex())

            return isCorrect && isValidLocalPart
        } else {return false}
    } else {return false}
}
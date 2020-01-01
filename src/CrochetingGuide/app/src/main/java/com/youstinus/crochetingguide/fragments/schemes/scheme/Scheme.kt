package com.youstinus.crochetingguide.fragments.schemes.scheme

import android.annotation.SuppressLint

import java.io.Serializable
import java.util.Objects

class Scheme : Serializable {
    var title: String? = null
    var description: String? = null
    var sequence: String? = null
    var images: String? = null

    constructor() {}

    constructor(title: String, text: String, sequence: String) {
        this.title = title
        this.description = text
        this.sequence = sequence
    }

    @SuppressLint("NewApi")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val scheme = other as Scheme?
        return title == scheme!!.title && sequence == scheme.sequence
    }

    @SuppressLint("NewApi")
    override fun hashCode(): Int {
        return Objects.hash(title, sequence)
    }
}

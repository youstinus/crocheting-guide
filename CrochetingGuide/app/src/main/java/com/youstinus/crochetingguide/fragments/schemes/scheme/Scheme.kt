package com.youstinus.crochetingguide.fragments.schemes.scheme

class Scheme(
        var title: String = "",
        var description: String = "",
        var sequence: String = "",
        var images: String = "",
        var descriptions: ArrayList<String> = arrayListOf()
)
/*{
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
}*/

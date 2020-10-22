package com.tapcrew.rickmorty.alfonso_sotelo.base

interface PrimaryKeyObject {
    fun getPrimaryKey(): Long?

    override fun equals(other: Any?): Boolean
}
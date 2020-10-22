package com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.objects

import com.google.gson.annotations.SerializedName
import com.tapcrew.rickmorty.alfonso_sotelo.base.PrimaryKeyObject

class Character: PrimaryKeyObject {

    @SerializedName("id")
    var id: Long? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("species")
    var species: String? = null
    @SerializedName("image")
    var image: String? = null


    override fun getPrimaryKey(): Long? {
        return id
    }

    override fun equals(other: Any?): Boolean {
        return hashCode() == other?.hashCode()
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (species?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        return result
    }

}
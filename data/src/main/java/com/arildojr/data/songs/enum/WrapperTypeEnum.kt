package com.arildojr.data.songs.enum

enum class WrapperTypeEnum(private val value: String?) {
    TRACK("track"),
    ARTIST("artist"),
    NONE("nome");

    fun getValue() : String? {
        return this.value
    }

    companion object {
        fun getEnumValue(value: String?) : WrapperTypeEnum {
            WrapperTypeEnum.values().forEach {
                if (it.value == value) {
                    return it
                }
            }
            return NONE
        }
    }
}
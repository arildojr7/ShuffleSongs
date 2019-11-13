package com.arildojr.shufflesongs.core.util

interface CommandProvider {

    fun getCommand(): SingleLiveEvent<GenericCommand>
}
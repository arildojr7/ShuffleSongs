package com.arildojr.shufflesongs.core.util

object CommandInjector : CommandProvider {

    override fun getCommand(): SingleLiveEvent<GenericCommand> = SingleLiveEvent()
}
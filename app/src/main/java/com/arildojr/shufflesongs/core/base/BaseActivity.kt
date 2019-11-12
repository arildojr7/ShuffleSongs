package com.arildojr.shufflesongs.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.arildojr.shufflesongs.core.util.ActivityBindingProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes val resId: Int
) : AppCompatActivity(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val binding by activityBinding<T>(resId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        subscribeUi()
    }


    /**
     * Override this method to observe livedata objects (optional)
     */
    open fun subscribeUi() {}

    private fun <T : ViewDataBinding> activityBinding(@LayoutRes resId: Int) =
        ActivityBindingProperty<T>(resId)
}
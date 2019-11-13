package com.arildojr.shufflesongs.splash

import android.content.Intent
import android.os.Bundle
import com.arildojr.shufflesongs.R
import com.arildojr.shufflesongs.core.base.BaseActivity
import com.arildojr.shufflesongs.databinding.ActivitySplashBinding
import com.arildojr.shufflesongs.songs.SongsActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch {
            delay(500)
            startActivity(Intent(this@SplashActivity, SongsActivity::class.java))
            finish()
        }
    }

}

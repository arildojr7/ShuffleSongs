package com.arildojr.shufflesongs.songs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arildojr.shufflesongs.R
import com.arildojr.shufflesongs.databinding.ActivitySongsBinding

class SongsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_songs)
    }
}

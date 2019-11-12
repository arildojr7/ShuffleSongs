package com.arildojr.shufflesongs.core.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.arildojr.shufflesongs.core.extension.convertDpToPx
import com.arildojr.shufflesongs.core.util.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("bind:imageUrl", "bind:cornerSize", requireAll = false)
fun ImageView.imageFromUrl(imageUrl: String?, cornerSize: Int?) {
    if (cornerSize != null) {
        loadRoundedImageView(
            this,
            imageUrl,
            cornerSize,
            RoundedCornersTransformation.CornerType.ALL
        )
    } else {
        loadImageView(this, imageUrl)
    }
}

fun loadImageView(image: ImageView, imageUrl: String?) {
    Glide.with(image.context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(RequestOptions().dontTransform())
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(image)
}

fun loadRoundedImageView(
    image: ImageView,
    imageUrl: String?,
    round: Int,
    cornerType: RoundedCornersTransformation.CornerType?
) {
    Glide.with(image.context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(RequestOptions().dontTransform())
        .transform(
            CenterCrop(), RoundedCornersTransformation(
                round.convertDpToPx(image.resources.displayMetrics).toFloat(),
                0f, cornerType
            )
        )
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(image)
}
package com.example.linews.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.linews.R
import org.w3c.dom.Text

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, imageUrl: String?){
    imageView.load(imageUrl){
        crossfade(true)
        crossfade(500)
        transformations(RoundedCornersTransformation(20f))
        placeholder(R.drawable.placeholder)
        fallback(R.drawable.error_image)
    }
}

@BindingAdapter("setHasData")
fun setHasData(view: TextView, hasData: Boolean?){
    view.visibility = if(hasData != null){
        if(!hasData) View.VISIBLE else View.GONE
    }
    else{
        View.GONE
    }
}

@BindingAdapter("setFormattedDateText")
fun setFormattedDateText(view:TextView, unformattedDate : String?){
    view.text = unformattedDate?.convertDate()
}

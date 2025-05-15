package com.websarva.wings.android.power

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class After_gacha : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultName = intent.getStringExtra("name")
        val resultImage = intent.getIntExtra("image", R.drawable.default_capsule)

        val imageView = findViewById<ImageView>(R.id.result_image)
        val textView = findViewById<TextView>(R.id.result_text)

        imageView.setImageResource(resultImage)
        textView.text = "あなたの引いたのは：\n\n$resultName"
    }
}

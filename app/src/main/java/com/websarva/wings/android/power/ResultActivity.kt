package com.websarva.wings.android.power

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var handleImage: ImageView
    private lateinit var gachaButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gacha)

        handleImage = findViewById(R.id.image_handle)
        gachaButton = findViewById(R.id.button_gacha)

        gachaButton.setOnClickListener {
            rotateHandleAndShowResult()
        }
    }

    private fun rotateHandleAndShowResult() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.gacha_turn)
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            it.release()
        }

        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            fillAfter = true
            interpolator = LinearInterpolator()

            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    showGachaResult()
                }
            })
        }

        handleImage.startAnimation(rotate)
    }

    private fun showGachaResult() {
        val results = listOf(
            Triple("レア景品", R.drawable.rare_item, 5),     // 5%
            Triple("赤カプセル", R.drawable.red_capsule, 25),   // 25%
            Triple("青カプセル", R.drawable.blue_capsule, 30),  // 30%
            Triple("緑カプセル", R.drawable.green_capsule, 20),  // 20%
            Triple("白カプセル", R.drawable.white_capsule, 20)   // 20%
        )

        val selected = weightedRandom(results)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, After_gacha::class.java).apply {
                putExtra("name", selected.first)
                putExtra("image", selected.second)
            }
            startActivity(intent)
        }, 1000)
    }

    private fun weightedRandom(items: List<Triple<String, Int, Int>>): Pair<String, Int> {
        val total = items.sumOf { it.third }
        val rand = (1..total).random()
        var acc = 0
        for ((name, imageId, weight) in items) {
            acc += weight
            if (rand <= acc) {
                return name to imageId
            }
        }
        return items.last().first to items.last().second
    }
}

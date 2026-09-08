package com.rootslab.firstproject

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {
    private val loopingAnimators = mutableListOf<Animator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val hero = findViewById<View>(R.id.hero)
        val helloBadge = findViewById<View>(R.id.helloBadge)
        val helloLabel = findViewById<View>(R.id.helloLabel)
        val helloWorld = findViewById<View>(R.id.helloWorld)
        val accentLine = findViewById<View>(R.id.accentLine)
        val helloSubtitle = findViewById<View>(R.id.helloSubtitle)
        val titleGlow = findViewById<View>(R.id.titleGlow)
        val orbTop = findViewById<View>(R.id.orbTop)
        val orbBottom = findViewById<View>(R.id.orbBottom)
        val orbSide = findViewById<View>(R.id.orbSide)

        playEntrance(helloBadge, helloLabel, helloWorld, accentLine, helloSubtitle)
        pulse(titleGlow)
        floatOrb(orbTop, 18f, 4200L)
        floatOrb(orbBottom, 24f, 5200L)
        floatOrb(orbSide, 16f, 3600L)

        helloWorld.setOnClickListener { bounce(it) }
        hero.setOnClickListener { bounce(helloWorld) }
    }

    override fun onDestroy() {
        loopingAnimators.forEach { it.cancel() }
        loopingAnimators.clear()
        super.onDestroy()
    }

    private fun playEntrance(vararg views: View) {
        views.forEachIndexed { index, view ->
            view.alpha = 0f
            view.translationY = 40f
            view.scaleX = 0.88f
            view.scaleY = 0.88f
            view.animate()
                .alpha(1f)
                .translationY(0f)
                .scaleX(1f)
                .scaleY(1f)
                .setStartDelay(140L + index * 95L)
                .setDuration(700L)
                .setInterpolator(OvershootInterpolator(1.15f))
                .start()
        }
    }

    private fun floatOrb(view: View, distance: Float, duration: Long) {
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -distance, distance).apply {
            this.duration = duration
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
            loopingAnimators += this
        }
    }

    private fun pulse(view: View) {
        ObjectAnimator.ofFloat(view, View.ALPHA, 0.32f, 0.78f).apply {
            duration = 2200L
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
            loopingAnimators += this
        }
        ObjectAnimator.ofFloat(view, View.SCALE_X, 0.9f, 1.08f).apply {
            duration = 2600L
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
            loopingAnimators += this
        }
        ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.9f, 1.08f).apply {
            duration = 2600L
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
            loopingAnimators += this
        }
    }

    private fun bounce(view: View) {
        view.animate().cancel()
        view.animate()
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(130L)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(260L)
                    .setInterpolator(OvershootInterpolator(2.2f))
                    .start()
            }
            .start()
    }
}

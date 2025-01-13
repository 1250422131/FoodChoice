package com.imcys.foodchoice.weight

import android.content.res.Resources
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.onGloballyPositioned
import nl.dionsegijn.konfetti.compose.draw
import nl.dionsegijn.konfetti.compose.getTotalTimeRunning
import nl.dionsegijn.konfetti.core.Particle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.emitter.BaseEmitter
import nl.dionsegijn.konfetti.core.emitter.Confetti
import nl.dionsegijn.konfetti.core.emitter.PartyEmitter
import nl.dionsegijn.konfetti.core.toParticle


interface OnParticleSystemUpdateListener {
    fun onParticleSystemEnded(system: WaifuBoostPartySystem, activeSystems: Int)
    fun onPartySystemsEnded()
}


class WaifuBoostPartySystem(
    val party: Party,
    val createdAt: Long = System.currentTimeMillis(),
    pixelDensity: Float = Resources.getSystem().displayMetrics.density,
    val onPartySystemsEnded: (() -> Unit)? = null
) {

    var enabled = true

    private var emitter: BaseEmitter = PartyEmitter(party.emitter, pixelDensity)

    private val activeParticles = mutableListOf<Confetti>()

    // Called every frame to create and update the particles state
    // returns a list of particles that are ready to be rendered
    fun render(deltaTime: Float, drawArea: Rect): List<Particle> {
        if (enabled) {
            activeParticles.addAll(emitter.createConfetti(deltaTime, party, drawArea))
        }

        activeParticles.forEach {
            it.render(deltaTime, drawArea)
        }

        activeParticles.removeAll {
            onPartySystemsEnded?.invoke()
            it.isDead()
        }

        return activeParticles.filter { it.drawParticle }.map { it.toParticle() }
    }

    /**
     * When the emitter is done emitting.
     * @return true if the emitter is done emitting or false when it's still busy or needs to start
     * based on the delay
     */
    fun isDoneEmitting(): Boolean =
        (emitter.isFinished() && activeParticles.size == 0) || (!enabled && activeParticles.size == 0)

    fun getActiveParticleAmount() = activeParticles.size
}


@Composable
fun WaifuBoostKonfettiView(
    modifier: Modifier = Modifier,
    parties: List<Party>,
    updateListener: OnParticleSystemUpdateListener? = null
) {

    lateinit var partySystems: List<WaifuBoostPartySystem>

    /**
     * Particles to draw
     */
    val particles = remember { mutableStateOf(emptyList<Particle>()) }

    /**
     * Latest stored frameTimeMilliseconds
     */
    val frameTime = remember { mutableStateOf(0L) }

    /**
     * Area in which the particles are being drawn
     */
    val drawArea = remember { mutableStateOf(Rect()) }


    val allHandleParticles = remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {
        partySystems = parties.map { WaifuBoostPartySystem(
            party = it,
            onPartySystemsEnded = {
                updateListener?.onPartySystemsEnded()
            }
        ) }
        while (true) {
            withFrameMillis { frameMs ->
                // Calculate time between frames, fallback to 0 when previous frame doesn't exist
                val deltaMs = if (frameTime.value > 0) (frameMs - frameTime.value) else 0
                frameTime.value = frameMs

                particles.value = partySystems.map { particleSystem ->

                    val totalTimeRunning = getTotalTimeRunning(particleSystem.createdAt)
                    // Do not start particleSystem yet if totalTimeRunning is below delay
                    if (totalTimeRunning < particleSystem.party.delay) return@map listOf()

                    if (particleSystem.isDoneEmitting()) {
                        updateListener?.onParticleSystemEnded(
                            system = particleSystem,
                            activeSystems = partySystems.count { !it.isDoneEmitting() }
                        )
                    }


                    particleSystem.render(deltaMs.div(1000f), drawArea.value)
                }.flatten()
            }
        }
    }

    Canvas(
        modifier = modifier
            .onGloballyPositioned {
                drawArea.value = Rect(0, 0, it.size.width, it.size.height)
            },
        onDraw = {
            particles.value.forEach { particle ->
                withTransform({
                    rotate(
                        degrees = particle.rotation,
                        pivot = Offset(
                            x = particle.x + (particle.width / 2),
                            y = particle.y + (particle.height / 2)
                        )
                    )
                    scale(
                        scaleX = particle.scaleX,
                        scaleY = 1f,
                        pivot = Offset(particle.x + (particle.width / 2), particle.y)
                    )
                }) {
                    particle.shape.draw(this, particle)
                }
            }
        }
    )
}

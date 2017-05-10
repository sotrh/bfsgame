package io.sotrh.games.bfs

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class BFSGame : ApplicationAdapter() {

    private lateinit var texture: Texture
    private lateinit var batch: SpriteBatch

    override fun create() {
        Pixmap(1, 1, Pixmap.Format.RGB888).using {
            it.setColor(Color.WHITE)
            it.fill()
            texture = Texture(it)
        }

        batch = SpriteBatch()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.batched {
            it.draw(texture, 10f, 10f, 100f, 100f)
        }
    }

    override fun dispose() {
        texture.dispose()
        batch.dispose()
    }
}

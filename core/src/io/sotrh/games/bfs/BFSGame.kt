package io.sotrh.games.bfs

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.sotrh.games.bfs.grid.Grid
import io.sotrh.games.bfs.grid.gridFromPixmap

class BFSGame : ApplicationAdapter() {

    private lateinit var texture: Texture
    private lateinit var batch: SpriteBatch
    private lateinit var grid: Grid
    private var offsetX = 0f
    private var offsetY = 0f

    private lateinit var camera: OrthographicCamera
    private var gridWidth = 0
    private var gridHeight = 0

    companion object {
        private var SCALE = 50f
    }

    override fun create() {
        Pixmap(1, 1, Pixmap.Format.RGB888).using {
            it.setColor(Color.WHITE)
            it.fill()
            texture = Texture(it)
        }

        Pixmap(Gdx.files.internal("maze-1.png")).using {
            gridWidth = it.width
            gridHeight = it.height
            centerGrid()
            grid = gridFromPixmap(it)
        }

        batch = SpriteBatch()

        camera = OrthographicCamera()
    }

    private fun centerGrid() {
        offsetX = (Gdx.graphics.width - gridWidth * SCALE) * 0.5f
        offsetY = (Gdx.graphics.height - gridHeight * SCALE) * 0.5f
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        centerGrid()
        camera.setToOrtho(true, width.toFloat(), height.toFloat())
        camera.update()
    }

    override fun render() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
            return
        }

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = camera.combined
        batch.batched { batch ->
            grid.forEachIndexed { x, list ->
                list.forEachIndexed { y, cell ->
                    batch.color = Color(cell)
                    batch.draw(
                            texture,
                            x * SCALE + offsetX,
                            y * SCALE + offsetY,
                            SCALE,
                            SCALE
                    )
                }
            }
        }
    }

    override fun dispose() {
        texture.dispose()
        batch.dispose()
    }
}

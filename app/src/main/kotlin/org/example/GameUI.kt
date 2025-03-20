package org.example

import javafx.animation.FadeTransition
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.Duration

class GameUI : Application() {
    private var game = Game()

    override fun start(primaryStage: Stage) {
        // Create a GridPane to display the board.
        val boardGrid = GridPane().apply {
            alignment = Pos.CENTER
            hgap = 10.0
            vgap = 10.0
        }
        updateGrid(boardGrid)

        // Create buttons for moving the board.
        val upButton = Button("Up").apply {
            setOnAction {
                game.move(Direction.UP)
                updateGrid(boardGrid)
            }
        }
        val downButton = Button("Down").apply {
            setOnAction {
                game.move(Direction.DOWN)
                updateGrid(boardGrid)
            }
        }
        val leftButton = Button("Left").apply {
            setOnAction {
                game.move(Direction.LEFT)
                updateGrid(boardGrid)
            }
        }
        val rightButton = Button("Right").apply {
            setOnAction {
                game.move(Direction.RIGHT)
                updateGrid(boardGrid)
            }
        }

        // Reset
        val resetButton = Button("Reset").apply {
            setOnAction {
                game = Game()
                updateGrid(boardGrid)
            }
        }

        // Arrange buttons in an HBox.
        val controls = HBox(10.0, leftButton, upButton, downButton, rightButton).apply {
            alignment = Pos.CENTER
        }
        val resetControls = HBox(15.0, resetButton).apply {
            alignment = Pos.CENTER
        }

        // Arrange the board and buttons in a VBox.
        val root = VBox(20.0, boardGrid, controls, resetControls).apply {
            alignment = Pos.CENTER
        }

        // Create the scene and add key event handling.
        val scene = Scene(root, 400.0, 400.0)
        scene.setOnKeyPressed { event ->
            when (event.code) {
                KeyCode.UP -> { game.move(Direction.UP); updateGrid(boardGrid) }
                KeyCode.DOWN -> { game.move(Direction.DOWN); updateGrid(boardGrid) }
                KeyCode.LEFT -> { game.move(Direction.LEFT); updateGrid(boardGrid) }
                KeyCode.RIGHT -> { game.move(Direction.RIGHT); updateGrid(boardGrid) }
                else -> {}
            }
        }

        primaryStage.title = "1024"
        primaryStage.scene = scene
        primaryStage.show()
        
        // Request focus so key events are captured.
        root.requestFocus()
    }

    private fun updateGrid(grid: GridPane) {
        // Clear any previous content
        grid.children.clear()

        // Get the current game board
        val board = game.getBoard()

        // Update the grid with Labels representing each cell.
        for (row in board.indices) {
            for (col in board[row].indices) {
                val value = board[row][col]
                val text = if (value != 0) value.toString() else ""
                val label = Label(text).apply {
                    style = "-fx-border-color: black; -fx-padding: 10; -fx-font-size: 18;"
                    minWidth = 50.0
                    alignment = Pos.CENTER
                    opacity = 0.0
                }
                grid.add(label, col, row)

                // Animate the appearance of the cell using FadeTransition.
                val fadeTransition = FadeTransition(Duration.millis(300.0), label).apply {
                    fromValue = 0.0
                    toValue = 1.0
                }
                fadeTransition.play()
            }
        }
    }
}

fun main() {
    Application.launch(GameUI::class.java)
}

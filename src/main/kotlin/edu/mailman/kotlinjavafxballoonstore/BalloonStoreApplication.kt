package edu.mailman.kotlinjavafxballoonstore

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class BalloonStoreApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader =
            FXMLLoader(
                BalloonStoreApplication::class.java.getResource("balloon-store-view.fxml")
            )
        val scene = Scene(fxmlLoader.load(), 800.0, 650.0)
        stage.title = "Bob's Balloons"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(BalloonStoreApplication::class.java)
}
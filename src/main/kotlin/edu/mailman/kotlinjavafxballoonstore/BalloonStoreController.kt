package edu.mailman.kotlinjavafxballoonstore

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.RadioButton
import javafx.scene.control.Spinner
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import java.util.Optional

class Balloon(val color: Color,
              var inflated: Boolean,
              var sold: Boolean) {
    override fun toString(): String {
        return "Color: $color / Inflated: $inflated / Sold: $sold"
    }
}

class BalloonStoreController {
    // The controls on the form
    lateinit var gridLeft: GridPane
    lateinit var gridRight: GridPane
    lateinit var rbRed: RadioButton
    lateinit var rbOrange: RadioButton
    lateinit var rbBlue: RadioButton
    lateinit var rbGreen: RadioButton
    lateinit var rbStockDeflated: RadioButton
    lateinit var rbInflateStock: RadioButton
    lateinit var rbSellInflated: RadioButton
    lateinit var rbSellDeflated: RadioButton
    lateinit var spinQty: Spinner<Any>

    // Create toggle groups from radio buttons
    private val tgColors = ToggleGroup()
    private val tgActions = ToggleGroup()

    // Store the color and action choices
    private var colorChosen: Color? = null
    private var actionChosen: String? = null

    // Create a mutable list of balloons
    private var balloons = mutableListOf<Balloon>()

    fun initialize() {
        // Create containers for balloons in left grid (10 X 10)
        for (i in 0..9)
            for (j in 0..9)
                gridLeft.add(Circle(5.0, 15.0, 15.0, Color.BLACK), i, j)

        // Create containers for balloons in right grid (5 X 5)
        for (i in 0..4)
            for (j in 0..4)
                gridRight.add(Circle(5.0, 30.0, 30.0, Color.BLACK), i, j)

        // Create a toggle group for the color radio buttons
        rbRed.toggleGroup = tgColors
        rbOrange.toggleGroup = tgColors
        rbBlue.toggleGroup = tgColors
        rbGreen.toggleGroup = tgColors

        // Create a toggle group for the action radio buttons
        rbStockDeflated.toggleGroup = tgActions
        rbInflateStock.toggleGroup = tgActions
        rbSellInflated.toggleGroup = tgActions
        rbSellDeflated.toggleGroup = tgActions
    }

    fun onColorSelected() {
        // Find the color chosen in the colors toggle group
        // and store its value in a variable
        val chosenColor = tgColors.selectedToggle.toString()
         colorChosen =
             if ("Green" in chosenColor) Color.GREEN
             else if ("Red" in chosenColor) Color.RED
             else if ("Orange" in chosenColor) Color.ORANGE
             else if ("Blue" in chosenColor) Color.BLUE
             else Color.BLACK
    }

    fun onActionSelected() {
        // Find the action chosen in the action toggle group
        // and store its value in a variable
        val chosenAction = tgActions.selectedToggle.toString()
        actionChosen =
            if ("Stock Deflated" in chosenAction) "Stock Deflated"
            else if ("Inflate Stock" in chosenAction) "Inflate Stock"
            else if ("Sell Inflated" in chosenAction) "Sell Inflated"
            else if ("Sell Deflated" in chosenAction) "Sell Deflated"
            else "Unknown"
    }

    private fun stockDeflated() {
        // Add stock to the balloons list
        for (i in 0 until spinQty.value.toString().toInt())
            colorChosen?.
            let{ Balloon(color = it, inflated = false, sold = false) }?.
            let { balloons.add(it) }

        for (balloon in balloons)
            println(balloon)

        clearGrid(gridLeft)

        // Get all the locations in the left grid
        val gridLocations = gridLeft.children

        // Get the number of balloons in the list
        val qty = balloons.size

        // Fill the correct number of locations in the grid
        var i = 0
        while (i < qty){
            for (node in gridLocations) {
                node as Circle
                node.fill = balloons[i].color
                i += 1
                if (i == qty)
                    break
            }
        }
    }

    private fun clearGrid(gridPane: GridPane) {
        val gridLocations = gridPane.children
        for (node in gridLocations) {
            node as Circle
            node.fill = Color.BLACK
        }
    }

    private fun inflateStock() {
        println("Inflate Stock")
    }

    private fun sellInflated() {
        println("Sell Inflated")
    }

    private fun sellDeflated() {
        println("Sell Deflated")
    }

    private fun colorCodeToString(color: Color): String {
        return when (color.toString()) {
            "0xff0000ff" -> "Red"
            "0xffa500ff" -> "Orange"
            "0x0000ffff" -> "Blue"
            "0x008000ff" -> "Green"
            else -> "Unknown"
        }
    }

    fun onProcessButtonClicked() {
        if ((actionChosen != null) and (colorChosen != null)) {
            val colorString = colorChosen?.let { colorCodeToString(it) }
            val alert = Alert(Alert.AlertType.CONFIRMATION)
            alert.title = "Bob's Balloon Store"
            alert.headerText = null
            alert.contentText =
                "OK to $actionChosen $colorString balloons: quantity ${spinQty.value}"
            val result: Optional<ButtonType> = alert.showAndWait()
            if (result.get() == ButtonType.OK) {
                when (actionChosen) {
                    "Stock Deflated" -> stockDeflated()
                    "Inflate Stock" -> inflateStock()
                    "Sell Inflated" -> sellInflated()
                    "Sell Deflated" -> sellDeflated()
                    else -> println("Something bad happened")
                }
            }
        } else {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.title = "Bob's Balloon Store"
            alert.headerText = null
            alert.contentText = "Must Choose Color and Action"
            alert.show()
        }
    }
}
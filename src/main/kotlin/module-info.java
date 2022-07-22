module edu.mailman.kotlinjavafxballoonstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens edu.mailman.kotlinjavafxballoonstore to javafx.fxml;
    exports edu.mailman.kotlinjavafxballoonstore;
}
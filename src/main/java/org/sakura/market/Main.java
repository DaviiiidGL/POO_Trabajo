package org.sakura.market;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // ✅ IR DIRECTO AL DASHBOARD DE SAKURA (DUEÑA)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin-cuenta/main-adminCuentas.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Sakura Market - Dashboard Ejecutivo");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();

            System.out.println("✅ Dashboard Ejecutivo (Sakura) cargado");

        } catch (Exception e) {
            System.err.println("❌ Error al cargar dashboard de Sakura");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

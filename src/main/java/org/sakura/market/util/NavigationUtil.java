package org.sakura.market.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase utilitaria para navegación entre vistas en JavaFX.
 * Todas las ventanas se abren maximizadas por defecto.
 */
public class NavigationUtil {

    /**
     * Navega a una nueva vista FXML, reemplazando la ventana actual.
     * La ventana se maximiza automáticamente.
     *
     * @param stage Stage actual que será reemplazado
     * @param fxmlPath Ruta al archivo FXML (ej: "/view/login.fxml")
     * @param title Título de la nueva ventana
     */
    public static void navegarA(Stage stage, String fxmlPath, String title) {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Crear la nueva escena
            Scene scene = new Scene(root);

            // Configurar el Stage
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setMaximized(true); // Maximizar automáticamente
            stage.show();

            System.out.println("✅ Navegado a: " + fxmlPath);

        } catch (Exception e) {
            System.err.println("❌ Error al cargar vista: " + fxmlPath);
            e.printStackTrace();
        }
    }

    /**
     * Navega a una nueva vista sin maximizar (ventana de tamaño específico).
     *
     * @param stage Stage actual
     * @param fxmlPath Ruta al archivo FXML
     * @param title Título de la ventana
     * @param width Ancho de la ventana
     * @param height Alto de la ventana
     */
    public static void navegarA(Stage stage, String fxmlPath, String title, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root, width, height);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.setMaximized(false);
            stage.show();

            System.out.println("✅ Navegado a: " + fxmlPath + " (" + width + "x" + height + ")");

        } catch (Exception e) {
            System.err.println("❌ Error al cargar vista: " + fxmlPath);
            e.printStackTrace();
        }
    }

    /**
     * Abre una nueva ventana (sin cerrar la actual).
     *
     * @param fxmlPath Ruta al archivo FXML
     * @param title Título de la nueva ventana
     * @return El nuevo Stage creado
     */
    public static Stage abrirNuevaVentana(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlPath));
            Parent root = loader.load();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);

            newStage.setScene(scene);
            newStage.setTitle(title);
            newStage.setMaximized(true);
            newStage.show();

            System.out.println("✅ Nueva ventana abierta: " + fxmlPath);
            return newStage;

        } catch (Exception e) {
            System.err.println("❌ Error al abrir nueva ventana: " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }
}

package org.sakura.market.controller.Sakura;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.sakura.market.util.NavigationUtil;

public class MainSakuraController {

    @FXML private Label lblUsuario;
    @FXML private Label lblVentasTotales;
    @FXML private Label lblPedidosActivos;
    @FXML private Label lblClientesActivos;
    @FXML private Label lblProductos;
    @FXML private Button btnCerrarSesion;

    private int clickCount = 0; // Contador para easter egg

    @FXML
    public void initialize() {
        System.out.println("✅ Dashboard Ejecutivo (Sakura) inicializado");
        cargarMetricas();
    }

    private void cargarMetricas() {
        // TODO: Cargar métricas reales desde el modelo/servicio
        lblVentasTotales.setText("COP 15.500.000");
        lblPedidosActivos.setText("48");
        lblClientesActivos.setText("324");
        lblProductos.setText("156");
    }

    @FXML
    private void handleCerrarSesion() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/login.fxml", "Sakura Market - Login");
    }

    @FXML
    private void handleClicBajo() {
        clickCount++;

        if (clickCount == 5) {
            System.out.println("🎉 Easter Egg activado!");

            Alert secreto = new Alert(Alert.AlertType.INFORMATION);
            secreto.setTitle("🌸 Vista Secreta Desbloqueada");
            secreto.setHeaderText("Eres Sakura");
            secreto.setContentText(
                    "⚠️⚠️⚠️ ALERTA ⚠️⚠️⚠️\n\n" +
                            "Cuando entres no tendras salida\n" +
                            "El pasado no sirve, debemos mejorar, no ver hacia atrás\n\n" +
                            "POO es mi salvación, nada me faltara\n" +
                            "Sakura, que nos hciciste???\n\n" +
                            "No veas nada que no te interese\n"
            );
            secreto.showAndWait();

            clickCount = 0; // Resetear contador
        }
    }

    @FXML
    private void handleGestionProductos() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage,
                "/view/admin-contenido/productosCrud.fxml",
                "Sakura Market - Gestión de Productos");
    }

    @FXML
    private void handleGestionPedidos() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage,
                "/view/admin-contenido/pedidos.fxml",
                "Sakura Market - Gestión de Pedidos");
    }

    @FXML
    private void handleGestionClientes() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage,
                "/view/admin-cuenta/editarClientes.fxml",
                "Sakura Market - Gestión de Clientes");
    }

    private void mostrarEnDesarrollo(String titulo, String mensaje) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(titulo);
        info.setHeaderText("Funcionalidad en desarrollo");
        info.setContentText(mensaje);
        info.showAndWait();
    }
}

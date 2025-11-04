package org.sakura.market.controller.Consejo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.sakura.market.util.NavigationUtil;

public class MainConsejoController {

    @FXML private Label lblUsuario;
    @FXML private Label lblProductos;
    @FXML private Label lblPedidos;
    @FXML private Label lblUsuarios;
    @FXML private Button btnCerrarSesion;

    private int clickCount = 0;

    @FXML
    public void initialize() {
        System.out.println("✅ Dashboard Consejo Sombrío inicializado");
        cargarMetricas();
    }

    private void cargarMetricas() {
        // TODO: Cargar métricas reales desde el modelo
        lblProductos.setText("156");
        lblPedidos.setText("48");
        lblUsuarios.setText("350");
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

    // ==================== GESTIÓN DE CONTENIDO ====================

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

    // ==================== GESTIÓN DE CUENTAS ====================

    @FXML
    private void handleGestionClientes() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage,
                "/view/admin-cuenta/editarClientes.fxml",
                "Sakura Market - Gestión de Clientes");
    }

    /**
     * Ver administradores en SOLO LECTURA (no puede editar)
     */
    @FXML
    private void handleVerAdministradores() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage,
                "/view/consejo-sombrio/verAdmins.fxml", // ✅ Vista de solo lectura
                "Sakura Market - Administradores (Solo Lectura)");
    }



}

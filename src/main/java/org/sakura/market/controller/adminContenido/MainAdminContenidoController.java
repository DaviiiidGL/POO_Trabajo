package org.sakura.market.controller.adminContenido;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.sakura.market.util.NavigationUtil;

public class MainAdminContenidoController {

    @FXML private Label lblUsuario;
    @FXML private Label lblTotalProductos;
    @FXML private Label lblPedidosActivos;
    @FXML private Label lblStockBajo;
    @FXML private Label lblProductosBiotec;
    @FXML private Button btnCerrarSesion;

    @FXML
    public void initialize() {
        System.out.println("✅ Dashboard AdminContenido inicializado");
        cargarMetricas();
    }

    private void cargarMetricas() {
        // TODO: Cargar métricas reales desde el modelo
        lblTotalProductos.setText("156");
        lblPedidosActivos.setText("48");
        lblStockBajo.setText("12");
        lblProductosBiotec.setText("24");
    }

    @FXML
    private void handleCerrarSesion() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/login.fxml", "Sakura Market - Login");
    }

    /**
     * Navega a la gestión de productos (CRUD)
     */
    @FXML
    private void handleGestionProductos() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage,
                "/view/admin-contenido/productosCrud.fxml",
                "Sakura Market - Gestión de Productos");
    }

    /**
     * Navega a la gestión de pedidos
     */
    @FXML
    private void handleGestionPedidos() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage,
                "/view/admin-contenido/pedidos.fxml",
                "Sakura Market - Gestión de Pedidos");
    }
}

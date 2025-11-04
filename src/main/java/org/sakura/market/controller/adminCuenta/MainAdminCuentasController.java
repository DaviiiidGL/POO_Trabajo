package org.sakura.market.controller.adminCuenta;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.sakura.market.util.NavigationUtil;

public class MainAdminCuentasController {

    @FXML private Label lblUsuario;
    @FXML private Label lblTotalUsuarios;
    @FXML private Label lblClientes;
    @FXML private Label lblAdministradores;
    @FXML private Label lblInactivos;
    @FXML private Button btnVolver;
    @FXML private Button btnCerrarSesion;
    @FXML private VBox cardAdministradores; // Card de gestión de admins

    private boolean esSakura = false; // Flag para detectar si es Sakura

    @FXML
    public void initialize() {
        System.out.println("✅ Dashboard AdminCuentas inicializado");

        // TODO: Detectar si el usuario actual es Sakura
        // esSakura = SessionManager.getUsuarioActual() instanceof Duena;

        cargarEstadisticas();
        configurarPermisos();
    }

    private void cargarEstadisticas() {
        // TODO: Cargar estadísticas reales desde el modelo
        lblTotalUsuarios.setText("350");
        lblClientes.setText("324");
        lblAdministradores.setText("6"); // AdminContenido + AdminCuentas
        lblInactivos.setText("12");
    }

    /**
     * Configura qué acciones están disponibles según el rol.
     * Si NO es Sakura, oculta la gestión de administradores.
     */
    private void configurarPermisos() {
        if (!esSakura) {
            // Si es AdminCuentas normal (Consejo Sombrío), ocultar gestión de admins
            cardAdministradores.setVisible(false);
            cardAdministradores.setManaged(false);
            System.out.println("⚠️ Gestión de admins oculta (solo Sakura puede acceder)");
        } else {
            System.out.println("✅ Sakura detectada: Acceso completo");
        }
    }

    @FXML
    private void handleVolver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/sakura/main-sakura.fxml", "Sakura Market - Dashboard Ejecutivo");
    }

    @FXML
    private void handleCerrarSesion() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/login.fxml", "Sakura Market - Login");
    }

    /**
     * Navega a la gestión de clientes (CRUD)
     * Disponible para: AdminCuentas y Sakura
     */
    @FXML
    private void handleGestionClientes() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        NavigationUtil.navegarA(stage,
                "/view/admin-cuenta/editarClientes.fxml",
                "Sakura Market - Gestión de Clientes");
    }

    /**
     * Navega a la gestión de administradores (CRUD)
     * Disponible para: SOLO Sakura
     */
    @FXML
    private void handleGestionAdministradores() {
        if (!esSakura) {
            mostrarError("Acceso Denegado",
                    "Solo Sakura puede gestionar roles de administradores");
            return;
        }

        Stage stage = (Stage) btnVolver.getScene().getWindow();
        NavigationUtil.navegarA(stage,
                "/view/admin-cuentas/administradores-crud.fxml",
                "Sakura Market - Gestión de Administradores");
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(titulo);
        error.setHeaderText(titulo);
        error.setContentText(mensaje);
        error.showAndWait();
    }
}

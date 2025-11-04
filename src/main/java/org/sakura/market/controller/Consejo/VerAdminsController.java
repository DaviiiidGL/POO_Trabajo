package org.sakura.market.controller.Consejo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.sakura.market.util.NavigationUtil;

public class VerAdminsController {

    @FXML private TextField txtBuscar;
    @FXML private ComboBox<String> cmbFiltroRol;
    @FXML private TableView<AdministradorTabla> tablaAdministradores;
    @FXML private Button btnVolver;
    @FXML private Button btnCerrarSesion;

    private ObservableList<AdministradorTabla> listaAdministradores;
    private ObservableList<AdministradorTabla> listaOriginal; // ✅ Lista completa sin filtrar

    @FXML
    public void initialize() {
        System.out.println("✅ Vista de Administradores (Solo Lectura) inicializada");

        configurarTabla();
        configurarFiltros();
        cargarAdministradores();
        configurarBusquedaEnTiempoReal(); // ✅ Activar búsqueda instantánea
    }

    private void configurarTabla() {
        // Configurar columnas de la tabla
        TableColumn<AdministradorTabla, Integer> colId = (TableColumn<AdministradorTabla, Integer>) tablaAdministradores.getColumns().get(0);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<AdministradorTabla, String> colNombre = (TableColumn<AdministradorTabla, String>) tablaAdministradores.getColumns().get(1);
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));

        TableColumn<AdministradorTabla, String> colEmail = (TableColumn<AdministradorTabla, String>) tablaAdministradores.getColumns().get(2);
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<AdministradorTabla, String> colRol = (TableColumn<AdministradorTabla, String>) tablaAdministradores.getColumns().get(3);
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        TableColumn<AdministradorTabla, String> colEstado = (TableColumn<AdministradorTabla, String>) tablaAdministradores.getColumns().get(4);
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<AdministradorTabla, String> colFecha = (TableColumn<AdministradorTabla, String>) tablaAdministradores.getColumns().get(5);
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));

        // Columna de acciones (solo Ver Detalles)
        TableColumn<AdministradorTabla, Void> colAcciones = (TableColumn<AdministradorTabla, Void>) tablaAdministradores.getColumns().get(6);
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnVer = new Button("👁️ Ver");

            {
                btnVer.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: #FFB3C1; -fx-padding: 5 12; -fx-background-radius: 4; -fx-border-color: #FFB3C1; -fx-border-width: 1; -fx-border-radius: 4; -fx-font-size: 11;");
                btnVer.setOnAction(event -> {
                    AdministradorTabla admin = getTableView().getItems().get(getIndex());
                    verDetalles(admin);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnVer);
                }
            }
        });
    }

    private void configurarFiltros() {
        // Llenar ComboBox de roles
        cmbFiltroRol.getItems().addAll("Todos", "AdminContenido", "AdminCuentas", "Dueña");
        cmbFiltroRol.setValue("Todos");

        // Listener para filtrar al cambiar rol
        cmbFiltroRol.setOnAction(e -> aplicarFiltros());
    }

    /**
     * ✅ BÚSQUEDA EN TIEMPO REAL
     * Configura un listener en el TextField para filtrar mientras se escribe
     */
    private void configurarBusquedaEnTiempoReal() {
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltros();
        });

        System.out.println("✅ Búsqueda en tiempo real activada");
    }

    private void cargarAdministradores() {
        // TODO: Cargar administradores reales desde el modelo
        listaOriginal = FXCollections.observableArrayList(
                new AdministradorTabla(1, "Carlos Mendoza", "cmendoza@sakura.com", "AdminContenido", "Activo", "2024-01-15"),
                new AdministradorTabla(2, "Ana García", "agarcia@sakura.com", "AdminContenido", "Activo", "2024-02-20"),
                new AdministradorTabla(3, "Luis Rodríguez", "lrodriguez@sakura.com", "AdminCuentas", "Activo", "2024-03-10"),
                new AdministradorTabla(4, "María Torres", "mtorres@sakura.com", "AdminCuentas", "Inactivo", "2024-04-05"),
                new AdministradorTabla(5, "Sakura Haruno", "sakura@sakura.com", "Dueña", "Activo", "2023-01-01"),
                new AdministradorTabla(6, "Pedro Martínez", "pmartinez@sakura.com", "AdminContenido", "Activo", "2024-05-12"),
                new AdministradorTabla(7, "Laura Sánchez", "lsanchez@sakura.com", "AdminCuentas", "Activo", "2024-06-08")
        );

        listaAdministradores = FXCollections.observableArrayList(listaOriginal);
        tablaAdministradores.setItems(listaAdministradores);
        System.out.println("✅ Cargados " + listaOriginal.size() + " administradores");
    }

    @FXML
    private void handleBuscar() {
        aplicarFiltros();
    }

    /**
     * Aplica filtros de búsqueda y rol
     */
    private void aplicarFiltros() {
        String busqueda = txtBuscar.getText().toLowerCase().trim();
        String rolFiltro = cmbFiltroRol.getValue();

        ObservableList<AdministradorTabla> filtrados = FXCollections.observableArrayList();

        for (AdministradorTabla admin : listaOriginal) {
            // Filtro de búsqueda (nombre, email o rol)
            boolean coincideBusqueda = busqueda.isEmpty() ||
                    admin.getNombreCompleto().toLowerCase().contains(busqueda) ||
                    admin.getRol().toLowerCase().contains(busqueda);

            // Filtro de rol
            boolean coincideRol = rolFiltro.equals("Todos") || admin.getRol().equals(rolFiltro);

            if (coincideBusqueda && coincideRol) {
                filtrados.add(admin);
            }
        }

        tablaAdministradores.setItems(filtrados);

        // Log para debugging
        if (!busqueda.isEmpty() || !rolFiltro.equals("Todos")) {
            System.out.println("🔍 Filtrados: " + filtrados.size() + "/" + listaOriginal.size() + " administradores");
        }
    }

    private void verDetalles(AdministradorTabla admin) {
        Alert detalles = new Alert(Alert.AlertType.INFORMATION);
        detalles.setTitle("Detalles del Administrador");
        detalles.setHeaderText(admin.getNombreCompleto());
        detalles.setContentText(
                "ID: " + admin.getId() + "\n" +
                        "Email: " + admin.getEmail() + "\n" +
                        "Rol: " + admin.getRol() + "\n" +
                        "Estado: " + admin.getEstado() + "\n" +
                        "Fecha Registro: " + admin.getFechaRegistro() + "\n\n" +
                        "Nota: No tienes permisos para editar esta información."
        );
        detalles.showAndWait();
    }

    @FXML
    private void handleVolver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/consejo-sombrio/main-consejo-sombrio.fxml", "Sakura Market - Consejo Sombrío");
    }

    @FXML
    private void handleCerrarSesion() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/login.fxml", "Sakura Market - Login");
    }

    // ==================== CLASE INTERNA PARA LA TABLA ====================

    /**
     * Clase para representar un administrador en la tabla.
     */
    public static class AdministradorTabla {
        private final Integer id;
        private final String nombreCompleto;
        private final String email;
        private final String rol;
        private final String estado;
        private final String fechaRegistro;

        public AdministradorTabla(Integer id, String nombreCompleto, String email, String rol, String estado, String fechaRegistro) {
            this.id = id;
            this.nombreCompleto = nombreCompleto;
            this.email = email;
            this.rol = rol;
            this.estado = estado;
            this.fechaRegistro = fechaRegistro;
        }

        public Integer getId() { return id; }
        public String getNombreCompleto() { return nombreCompleto; }
        public String getEmail() { return email; }
        public String getRol() { return rol; }
        public String getEstado() { return estado; }
        public String getFechaRegistro() { return fechaRegistro; }
    }
}

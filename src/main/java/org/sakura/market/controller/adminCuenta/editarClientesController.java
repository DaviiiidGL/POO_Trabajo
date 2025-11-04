package org.sakura.market.controller.adminCuenta;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.sakura.market.util.NavigationUtil;

import java.time.LocalDate;
import java.util.*;

public class editarClientesController {

    @FXML private TextField txtBuscar;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private TableView<ClienteTabla> tablaClientes;
    @FXML private Button btnVolver;
    @FXML private Button btnCerrarSesion;

    @FXML private Label lblTotalClientes;
    @FXML private Label lblActivos;
    @FXML private Label lblSuspendidos;
    @FXML private Label lblBloqueados;

    private ObservableList<ClienteTabla> listaClientes;
    private ObservableList<ClienteTabla> listaOriginal;

    @FXML
    public void initialize() {
        System.out.println("✅ CRUD de Clientes inicializado");

        configurarTabla();
        configurarFiltros();
        cargarClientesSimulados();
        configurarBusquedaEnTiempoReal();
        actualizarEstadisticas();
    }

    private void configurarTabla() {
        // Configurar columnas
        TableColumn<ClienteTabla, Long> colId = (TableColumn<ClienteTabla, Long>) tablaClientes.getColumns().get(0);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ClienteTabla, String> colNombre = (TableColumn<ClienteTabla, String>) tablaClientes.getColumns().get(1);
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<ClienteTabla, String> colEmail = (TableColumn<ClienteTabla, String>) tablaClientes.getColumns().get(2);
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ClienteTabla, String> colTelefono = (TableColumn<ClienteTabla, String>) tablaClientes.getColumns().get(3);
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<ClienteTabla, String> colFechaRegistro = (TableColumn<ClienteTabla, String>) tablaClientes.getColumns().get(4);
        colFechaRegistro.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));

        TableColumn<ClienteTabla, String> colEstado = (TableColumn<ClienteTabla, String>) tablaClientes.getColumns().get(5);
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoFormateado"));

        TableColumn<ClienteTabla, Integer> colCompras = (TableColumn<ClienteTabla, Integer>) tablaClientes.getColumns().get(6);
        colCompras.setCellValueFactory(new PropertyValueFactory<>("numCompras"));

        // Columna de acciones
        TableColumn<ClienteTabla, Void> colAcciones = (TableColumn<ClienteTabla, Void>) tablaClientes.getColumns().get(7);
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnVer = new Button("👁️");
            private final Button btnEditar = new Button("✏️");
            private final Button btnEstado = new Button("🔄");
            private final HBox contenedor = new HBox(6, btnVer, btnEditar, btnEstado);

            {
                btnVer.setStyle("-fx-background-color: #FFB3C1; -fx-text-fill: #1a1a1a; -fx-padding: 5 10; -fx-background-radius: 4; -fx-font-size: 12;");
                btnEditar.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: #FFB3C1; -fx-padding: 5 10; -fx-background-radius: 4; -fx-border-color: #FFB3C1; -fx-border-width: 1; -fx-border-radius: 4; -fx-font-size: 12;");
                btnEstado.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: #FFB3C1; -fx-padding: 5 10; -fx-background-radius: 4; -fx-border-color: #FFB3C1; -fx-border-width: 1; -fx-border-radius: 4; -fx-font-size: 12;");

                btnVer.setTooltip(new Tooltip("Ver detalles"));
                btnEditar.setTooltip(new Tooltip("Editar cliente"));
                btnEstado.setTooltip(new Tooltip("Cambiar estado"));

                btnVer.setOnAction(event -> {
                    ClienteTabla cliente = getTableView().getItems().get(getIndex());
                    verDetallesCliente(cliente);
                });

                btnEditar.setOnAction(event -> {
                    ClienteTabla cliente = getTableView().getItems().get(getIndex());
                    editarCliente(cliente);
                });

                btnEstado.setOnAction(event -> {
                    ClienteTabla cliente = getTableView().getItems().get(getIndex());
                    cambiarEstadoCliente(cliente);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : contenedor);
            }
        });
    }

    private void configurarFiltros() {
        cmbFiltroEstado.getItems().addAll("Todos", "ACTIVO", "INACTIVO", "SUSPENDIDO", "BLOQUEADO");
        cmbFiltroEstado.setValue("Todos");
        cmbFiltroEstado.setOnAction(e -> aplicarFiltros());
    }

    private void configurarBusquedaEnTiempoReal() {
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltros();
        });
    }

    private void cargarClientesSimulados() {
        // TODO: Reemplazar con datos reales del modelo
        listaOriginal = FXCollections.observableArrayList(
                new ClienteTabla(
                        1L, "Juan Pérez", "juan.perez@email.com", "3001234567",
                        LocalDate.of(2024, 1, 15), "ACTIVO",
                        List.of(
                                new DireccionSimple("Calle 50 #20-30", "Medellín", "050001", "Colombia"),
                                new DireccionSimple("Carrera 70 #45-10", "Medellín", "050002", "Colombia")
                        ),
                        5
                ),

                new ClienteTabla(
                        2L, "María García", "maria.garcia@email.com", "3107654321",
                        LocalDate.of(2024, 2, 20), "ACTIVO",
                        List.of(
                                new DireccionSimple("Avenida 80 #30-50", "Bogotá", "110111", "Colombia")
                        ),
                        12
                ),

                new ClienteTabla(
                        3L, "Carlos Rodríguez", "carlos.rod@email.com", "3159876543",
                        LocalDate.of(2024, 3, 10), "SUSPENDIDO",
                        List.of(
                                new DireccionSimple("Calle 100 #15-20", "Cali", "760001", "Colombia")
                        ),
                        3
                ),

                new ClienteTabla(
                        4L, "Ana Torres", "ana.torres@email.com", "3201238765",
                        LocalDate.of(2024, 4, 5), "ACTIVO",
                        List.of(
                                new DireccionSimple("Carrera 43A #1-50", "Medellín", "050021", "Colombia")
                        ),
                        8
                ),

                new ClienteTabla(
                        5L, "Luis Martínez", "luis.martinez@email.com", "3008765432",
                        LocalDate.of(2023, 12, 1), "BLOQUEADO",
                        List.of(
                                new DireccionSimple("Calle 10 #5-25", "Barranquilla", "080001", "Colombia")
                        ),
                        1
                ),

                new ClienteTabla(
                        6L, "Sofia López", "sofia.lopez@email.com", "3156543210",
                        LocalDate.of(2024, 5, 12), "ACTIVO",
                        List.of(
                                new DireccionSimple("Avenida El Poblado #14-106", "Medellín", "050021", "Colombia")
                        ),
                        15
                ),

                new ClienteTabla(
                        7L, "Pedro Sánchez", "pedro.sanchez@email.com", "3209871234",
                        LocalDate.of(2024, 6, 8), "INACTIVO",
                        List.of(),
                        0
                )
        );

        listaClientes = FXCollections.observableArrayList(listaOriginal);
        tablaClientes.setItems(listaClientes);
        System.out.println("✅ Cargados " + listaOriginal.size() + " clientes simulados");
    }

    private void aplicarFiltros() {
        String busqueda = txtBuscar.getText().toLowerCase().trim();
        String estadoFiltro = cmbFiltroEstado.getValue();

        ObservableList<ClienteTabla> filtrados = FXCollections.observableArrayList();

        for (ClienteTabla cliente : listaOriginal) {
            // Filtro de búsqueda
            boolean coincideBusqueda = busqueda.isEmpty() ||
                    cliente.getNombre().toLowerCase().contains(busqueda) ||
                    cliente.getEmail().toLowerCase().contains(busqueda) ||
                    cliente.getTelefono().contains(busqueda);

            // Filtro de estado
            boolean coincideEstado = estadoFiltro.equals("Todos") || cliente.getEstado().equals(estadoFiltro);

            if (coincideBusqueda && coincideEstado) {
                filtrados.add(cliente);
            }
        }

        tablaClientes.setItems(filtrados);
        actualizarEstadisticas();
    }

    private void actualizarEstadisticas() {
        int total = listaOriginal.size();
        int activos = (int) listaOriginal.stream().filter(c -> c.getEstado().equals("ACTIVO")).count();
        int suspendidos = (int) listaOriginal.stream().filter(c -> c.getEstado().equals("SUSPENDIDO")).count();
        int bloqueados = (int) listaOriginal.stream().filter(c -> c.getEstado().equals("BLOQUEADO")).count();

        lblTotalClientes.setText(String.valueOf(total));
        lblActivos.setText(String.valueOf(activos));
        lblSuspendidos.setText(String.valueOf(suspendidos));
        lblBloqueados.setText(String.valueOf(bloqueados));
    }

    @FXML
    private void handleNuevoCliente() {
        mostrarDialogoCliente(null);
    }

    private void editarCliente(ClienteTabla cliente) {
        mostrarDialogoCliente(cliente);
    }

    private void mostrarDialogoCliente(ClienteTabla clienteEditar) {
        Dialog<ClienteTabla> dialog = new Dialog<>();
        dialog.setTitle(clienteEditar == null ? "Nuevo Cliente" : "Editar Cliente");
        dialog.setHeaderText(clienteEditar == null ? "Registrar nuevo cliente" : "Modificar información del cliente");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        // Crear formulario
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));

        TextField txtNombre = new TextField(clienteEditar != null ? clienteEditar.getNombre() : "");
        txtNombre.setPromptText("Nombre completo");

        TextField txtEmail = new TextField(clienteEditar != null ? clienteEditar.getEmail() : "");
        txtEmail.setPromptText("correo@ejemplo.com");

        TextField txtTelefono = new TextField(clienteEditar != null ? clienteEditar.getTelefono() : "");
        txtTelefono.setPromptText("3001234567");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText(clienteEditar != null ? "Dejar vacío para no cambiar" : "Contraseña");

        ComboBox<String> cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("ACTIVO", "INACTIVO", "SUSPENDIDO", "BLOQUEADO");
        cmbEstado.setValue(clienteEditar != null ? clienteEditar.getEstado() : "ACTIVO");

        grid.add(new Label("Nombre:*"), 0, 0);
        grid.add(txtNombre, 1, 0);

        grid.add(new Label("Email:*"), 0, 1);
        grid.add(txtEmail, 1, 1);

        grid.add(new Label("Teléfono:*"), 0, 2);
        grid.add(txtTelefono, 1, 2);

        grid.add(new Label("Contraseña:" + (clienteEditar != null ? "" : "*")), 0, 3);
        grid.add(txtPassword, 1, 3);

        grid.add(new Label("Estado:*"), 0, 4);
        grid.add(cmbEstado, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    String nombre = txtNombre.getText().trim();
                    String email = txtEmail.getText().trim();
                    String telefono = txtTelefono.getText().trim();
                    String password = txtPassword.getText();
                    String estado = cmbEstado.getValue();

                    if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                        throw new IllegalArgumentException("Nombre, email y teléfono son obligatorios");
                    }

                    if (clienteEditar == null && password.isEmpty()) {
                        throw new IllegalArgumentException("La contraseña es obligatoria para nuevos clientes");
                    }

                    Long id = clienteEditar != null ? clienteEditar.getId() : (long) (listaOriginal.size() + 1);
                    LocalDate fechaRegistro = clienteEditar != null ? clienteEditar.getFechaRegistroDate() : LocalDate.now();
                    List<DireccionSimple> direcciones = clienteEditar != null ? clienteEditar.getDirecciones() : new ArrayList<>();
                    int numCompras = clienteEditar != null ? clienteEditar.getNumCompras() : 0;

                    return new ClienteTabla(id, nombre, email, telefono, fechaRegistro, estado, direcciones, numCompras);

                } catch (Exception e) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error de Validación");
                    error.setHeaderText("Datos inválidos");
                    error.setContentText(e.getMessage());
                    error.showAndWait();
                    return null;
                }
            }
            return null;
        });

        Optional<ClienteTabla> resultado = dialog.showAndWait();
        resultado.ifPresent(cliente -> {
            if (clienteEditar != null) {
                int index = listaOriginal.indexOf(clienteEditar);
                listaOriginal.set(index, cliente);
                System.out.println("✅ Cliente actualizado: " + cliente.getNombre());
            } else {
                listaOriginal.add(cliente);
                System.out.println("✅ Cliente creado: " + cliente.getNombre());
            }

            aplicarFiltros();
            actualizarEstadisticas();

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Éxito");
            exito.setHeaderText(null);
            exito.setContentText("Cliente guardado exitosamente");
            exito.showAndWait();
        });
    }

    private void verDetallesCliente(ClienteTabla cliente) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Detalles del Cliente");
        dialog.setHeaderText("Información completa de " + cliente.getNombre());

        ButtonType btnCerrar = new ButtonType("Cerrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(btnCerrar);

        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));

        // Información básica
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);

        grid.add(crearLabel("ID:", true), 0, 0);
        grid.add(crearLabel(String.valueOf(cliente.getId()), false), 1, 0);

        grid.add(crearLabel("Nombre:", true), 0, 1);
        grid.add(crearLabel(cliente.getNombre(), false), 1, 1);

        grid.add(crearLabel("Email:", true), 0, 2);
        grid.add(crearLabel(cliente.getEmail(), false), 1, 2);

        grid.add(crearLabel("Teléfono:", true), 0, 3);
        grid.add(crearLabel(cliente.getTelefono(), false), 1, 3);

        grid.add(crearLabel("Fecha Registro:", true), 0, 4);
        grid.add(crearLabel(cliente.getFechaRegistro(), false), 1, 4);

        grid.add(crearLabel("Estado:", true), 0, 5);
        grid.add(crearLabel(cliente.getEstadoFormateado(), false), 1, 5);

        grid.add(crearLabel("Total Compras:", true), 0, 6);
        grid.add(crearLabel(String.valueOf(cliente.getNumCompras()), false), 1, 6);

        contenido.getChildren().add(grid);

        // Direcciones
        if (!cliente.getDirecciones().isEmpty()) {
            Label lblDirecciones = crearLabel("Direcciones Registradas:", true);
            contenido.getChildren().add(lblDirecciones);

            VBox boxDirecciones = new VBox(8);
            boxDirecciones.setStyle("-fx-background-color: #2d2d2d; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #FFB3C1; -fx-border-width: 1; -fx-border-radius: 8;");

            for (DireccionSimple dir : cliente.getDirecciones()) {
                VBox dirBox = new VBox(3);
                dirBox.getChildren().addAll(
                        crearLabel("📍 " + dir.getCalle(), false),
                        crearLabel("   " + dir.getCiudad() + ", " + dir.getCodigoPostal() + " - " + dir.getPais(), false)
                );
                boxDirecciones.getChildren().add(dirBox);
            }

            contenido.getChildren().add(boxDirecciones);
        } else {
            contenido.getChildren().add(crearLabel("Sin direcciones registradas", false));
        }

        dialog.getDialogPane().setContent(contenido);
        dialog.showAndWait();
    }

    private Label crearLabel(String texto, boolean negrita) {
        Label label = new Label(texto);
        label.setStyle("-fx-text-fill: " + (negrita ? "#FFB3C1" : "#FFF5E1") +
                "; -fx-font-size: 13px" +
                (negrita ? "; -fx-font-weight: bold;" : ";"));
        return label;
    }

    private void cambiarEstadoCliente(ClienteTabla cliente) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(cliente.getEstado(),
                "ACTIVO", "INACTIVO", "SUSPENDIDO", "BLOQUEADO");
        dialog.setTitle("Cambiar Estado");
        dialog.setHeaderText("Cambiar estado de cuenta de " + cliente.getNombre());
        dialog.setContentText("Nuevo estado:");

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(nuevoEstado -> {
            if (!nuevoEstado.equals(cliente.getEstado())) {
                int index = listaOriginal.indexOf(cliente);
                ClienteTabla clienteActualizado = new ClienteTabla(
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        cliente.getTelefono(),
                        cliente.getFechaRegistroDate(),
                        nuevoEstado,
                        cliente.getDirecciones(),
                        cliente.getNumCompras()
                );

                listaOriginal.set(index, clienteActualizado);
                aplicarFiltros();
                actualizarEstadisticas();

                Alert exito = new Alert(Alert.AlertType.INFORMATION);
                exito.setTitle("Estado Actualizado");
                exito.setHeaderText(null);
                exito.setContentText("El estado de la cuenta ha sido actualizado a: " + nuevoEstado);
                exito.showAndWait();

                System.out.println("✅ Estado actualizado: " + cliente.getNombre() + " -> " + nuevoEstado);
            }
        });
    }

    @FXML
    private void handleVolver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/admin-cuentas/main-admin-cuentas.fxml", "Sakura Market - AdminCuentas");
    }

    @FXML
    private void handleCerrarSesion() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/login.fxml", "Sakura Market - Login");
    }

    // ==================== CLASES INTERNAS ====================

    public static class ClienteTabla {
        private final Long id;
        private final String nombre;
        private final String email;
        private final String telefono;
        private final LocalDate fechaRegistro;
        private final String estado;
        private final List<DireccionSimple> direcciones;
        private final int numCompras;

        public ClienteTabla(Long id, String nombre, String email, String telefono,
                            LocalDate fechaRegistro, String estado,
                            List<DireccionSimple> direcciones, int numCompras) {
            this.id = id;
            this.nombre = nombre;
            this.email = email;
            this.telefono = telefono;
            this.fechaRegistro = fechaRegistro;
            this.estado = estado;
            this.direcciones = direcciones != null ? direcciones : new ArrayList<>();
            this.numCompras = numCompras;
        }

        public Long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getEmail() { return email; }
        public String getTelefono() { return telefono; }
        public LocalDate getFechaRegistroDate() { return fechaRegistro; }
        public String getFechaRegistro() { return fechaRegistro.toString(); }
        public String getEstado() { return estado; }
        public String getEstadoFormateado() {
            String emoji = switch (estado) {
                case "ACTIVO" -> "✅";
                case "INACTIVO" -> "⚪";
                case "SUSPENDIDO" -> "⏸️";
                case "BLOQUEADO" -> "🚫";
                default -> "❓";
            };
            return emoji + " " + estado;
        }
        public List<DireccionSimple> getDirecciones() { return direcciones; }
        public int getNumCompras() { return numCompras; }
    }

    public static class DireccionSimple {
        private final String calle;
        private final String ciudad;
        private final String codigoPostal;
        private final String pais;

        public DireccionSimple(String calle, String ciudad, String codigoPostal, String pais) {
            this.calle = calle;
            this.ciudad = ciudad;
            this.codigoPostal = codigoPostal;
            this.pais = pais;
        }

        public String getCalle() { return calle; }
        public String getCiudad() { return ciudad; }
        public String getCodigoPostal() { return codigoPostal; }
        public String getPais() { return pais; }
    }
}

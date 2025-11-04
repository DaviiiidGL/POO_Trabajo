package org.sakura.market.controller.adminContenido;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.sakura.market.util.NavigationUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class productosCrudController {

    @FXML private TextField txtBuscar;
    @FXML private ComboBox<String> cmbFiltroTipo;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private TableView<ProductoTabla> tablaProductos;
    @FXML private Button btnVolver;
    @FXML private Button btnCerrarSesion;

    @FXML private Label lblTotalProductos;
    @FXML private Label lblProductosBiotec;
    @FXML private Label lblStockBajo;
    @FXML private Label lblDeshabilitados;

    private ObservableList<ProductoTabla> listaProductos;
    private ObservableList<ProductoTabla> listaOriginal;

    @FXML
    public void initialize() {
        System.out.println("✅ CRUD de Productos inicializado");

        configurarTabla();
        configurarFiltros();
        cargarProductosSimulados();
        configurarBusquedaEnTiempoReal();
        actualizarEstadisticas();
    }

    private void configurarTabla() {
        // Configurar columnas
        TableColumn<ProductoTabla, String> colId = (TableColumn<ProductoTabla, String>) tablaProductos.getColumns().get(0);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ProductoTabla, String> colTipo = (TableColumn<ProductoTabla, String>) tablaProductos.getColumns().get(1);
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<ProductoTabla, String> colNombre = (TableColumn<ProductoTabla, String>) tablaProductos.getColumns().get(2);
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<ProductoTabla, String> colPrecio = (TableColumn<ProductoTabla, String>) tablaProductos.getColumns().get(3);
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<ProductoTabla, Integer> colStock = (TableColumn<ProductoTabla, Integer>) tablaProductos.getColumns().get(4);
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<ProductoTabla, String> colFecha = (TableColumn<ProductoTabla, String>) tablaProductos.getColumns().get(5);
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaLanzamiento"));

        TableColumn<ProductoTabla, String> colEstado = (TableColumn<ProductoTabla, String>) tablaProductos.getColumns().get(6);
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Columna de acciones
        TableColumn<ProductoTabla, Void> colAcciones = (TableColumn<ProductoTabla, Void>) tablaProductos.getColumns().get(7);
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("✏️ Editar");
            private final Button btnEliminar = new Button("🗑️");
            private final HBox contenedor = new HBox(8, btnEditar, btnEliminar);

            {
                btnEditar.setStyle("-fx-background-color: #FFB3C1; -fx-text-fill: #1a1a1a; -fx-padding: 5 12; -fx-background-radius: 4; -fx-font-size: 11; -fx-font-weight: bold;");
                btnEliminar.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: #FFB3C1; -fx-padding: 5 10; -fx-background-radius: 4; -fx-border-color: #FFB3C1; -fx-border-width: 1; -fx-border-radius: 4; -fx-font-size: 11;");

                btnEditar.setOnAction(event -> {
                    ProductoTabla producto = getTableView().getItems().get(getIndex());
                    handleEditarProducto(producto);
                });

                btnEliminar.setOnAction(event -> {
                    ProductoTabla producto = getTableView().getItems().get(getIndex());
                    handleEliminarProducto(producto);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });
    }

    private void configurarFiltros() {
        // Filtro de tipo
        cmbFiltroTipo.getItems().addAll("Todos", "Normal", "Biotecnológico");
        cmbFiltroTipo.setValue("Todos");
        cmbFiltroTipo.setOnAction(e -> aplicarFiltros());

        // Filtro de estado
        cmbFiltroEstado.getItems().addAll("Todos", "Habilitado", "Deshabilitado");
        cmbFiltroEstado.setValue("Todos");
        cmbFiltroEstado.setOnAction(e -> aplicarFiltros());
    }

    private void configurarBusquedaEnTiempoReal() {
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltros();
        });
    }

    private void cargarProductosSimulados() {
        // TODO: Reemplazar con datos reales del modelo
        listaOriginal = FXCollections.observableArrayList(
                new ProductoTabla(UUID.randomUUID().toString().substring(0, 8), "Normal", "Laptop HP",
                        new BigDecimal("2500000"), 15, LocalDate.of(2024, 1, 15), true, null, null, null, null, null),

                new ProductoTabla(UUID.randomUUID().toString().substring(0, 8), "Normal", "Mouse Logitech",
                        new BigDecimal("45000"), 50, LocalDate.of(2024, 2, 20), true, null, null, null, null, null),

                new ProductoTabla(UUID.randomUUID().toString().substring(0, 8), "Biotecnológico", "Suero Regenerador X1",
                        new BigDecimal("350000"), 8, LocalDate.of(2024, 3, 10), true,
                        "PAT-2024-001", LocalDate.of(2026, 3, 10), "Regeneración celular acelerada",
                        List.of("Péptidos sintéticos", "Colágeno tipo II"), "Uso exclusivo médico"),

                new ProductoTabla(UUID.randomUUID().toString().substring(0, 8), "Normal", "Teclado Mecánico",
                        new BigDecimal("180000"), 3, LocalDate.of(2024, 4, 5), true, null, null, null, null, null),

                new ProductoTabla(UUID.randomUUID().toString().substring(0, 8), "Biotecnológico", "Vacuna Antiviral BT-9",
                        new BigDecimal("120000"), 25, LocalDate.of(2024, 5, 12), false,
                        "PAT-2024-002", LocalDate.of(2025, 5, 12), "Inmunización contra virus tipo B",
                        List.of("Vector viral atenuado", "Adyuvante inmune"), "Refrigeración a 2-8°C"),

                new ProductoTabla(UUID.randomUUID().toString().substring(0, 8), "Normal", "Monitor 4K Samsung",
                        new BigDecimal("850000"), 12, LocalDate.of(2024, 6, 8), true, null, null, null, null, null)
        );

        listaProductos = FXCollections.observableArrayList(listaOriginal);
        tablaProductos.setItems(listaProductos);
        System.out.println("✅ Cargados " + listaOriginal.size() + " productos simulados");
    }

    private void aplicarFiltros() {
        String busqueda = txtBuscar.getText().toLowerCase().trim();
        String tipoFiltro = cmbFiltroTipo.getValue();
        String estadoFiltro = cmbFiltroEstado.getValue();

        ObservableList<ProductoTabla> filtrados = FXCollections.observableArrayList();

        for (ProductoTabla producto : listaOriginal) {
            // Filtro de búsqueda
            boolean coincideBusqueda = busqueda.isEmpty() ||
                    producto.getNombre().toLowerCase().contains(busqueda) ||
                    producto.getTipo().toLowerCase().contains(busqueda);

            // Filtro de tipo
            boolean coincideTipo = tipoFiltro.equals("Todos") || producto.getTipo().equals(tipoFiltro);

            // Filtro de estado
            boolean coincideEstado = estadoFiltro.equals("Todos") ||
                    (estadoFiltro.equals("Habilitado") && producto.isHabilitado()) ||
                    (estadoFiltro.equals("Deshabilitado") && !producto.isHabilitado());

            if (coincideBusqueda && coincideTipo && coincideEstado) {
                filtrados.add(producto);
            }
        }

        tablaProductos.setItems(filtrados);
        actualizarEstadisticas();
    }

    private void actualizarEstadisticas() {
        int total = listaOriginal.size();
        int biotec = (int) listaOriginal.stream().filter(p -> p.getTipo().equals("Biotecnológico")).count();
        int stockBajo = (int) listaOriginal.stream().filter(p -> p.getStock() < 10).count();
        int deshabilitados = (int) listaOriginal.stream().filter(p -> !p.isHabilitado()).count();

        lblTotalProductos.setText(String.valueOf(total));
        lblProductosBiotec.setText(String.valueOf(biotec));
        lblStockBajo.setText(String.valueOf(stockBajo));
        lblDeshabilitados.setText(String.valueOf(deshabilitados));
    }

    @FXML
    private void handleNuevoProducto() {
        mostrarDialogoProducto(null);
    }

    private void handleEditarProducto(ProductoTabla producto) {
        mostrarDialogoProducto(producto);
    }

    private void handleEliminarProducto(ProductoTabla producto) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Eliminar producto?");
        confirmacion.setContentText(
                "¿Estás seguro de eliminar el producto:\n" +
                        producto.getNombre() + "\n\n" +
                        "Esta acción no se puede deshacer."
        );

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            listaOriginal.remove(producto);
            aplicarFiltros();
            actualizarEstadisticas();

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Producto Eliminado");
            exito.setHeaderText(null);
            exito.setContentText("El producto ha sido eliminado exitosamente.");
            exito.showAndWait();

            System.out.println("✅ Producto eliminado: " + producto.getNombre());
        }
    }

    private void mostrarDialogoProducto(ProductoTabla productoEditar) {
        Dialog<ProductoTabla> dialog = new Dialog<>();
        dialog.setTitle(productoEditar == null ? "Nuevo Producto" : "Editar Producto");
        dialog.setHeaderText(productoEditar == null ? "Crear nuevo producto" : "Modificar producto existente");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        // Crear formulario
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));

        // Tipo de producto
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Normal", "Biotecnológico");
        cmbTipo.setValue(productoEditar != null ? productoEditar.getTipo() : "Normal");

        // Campos comunes
        TextField txtNombre = new TextField(productoEditar != null ? productoEditar.getNombre() : "");
        txtNombre.setPromptText("Nombre del producto");

        TextArea txtDescripcion = new TextArea(productoEditar != null ? "" : "");
        txtDescripcion.setPromptText("Descripción del producto");
        txtDescripcion.setPrefRowCount(3);

        TextField txtPrecio = new TextField(productoEditar != null ? productoEditar.getPrecio().toString() : "");
        txtPrecio.setPromptText("Precio (COP)");

        TextField txtStock = new TextField(productoEditar != null ? String.valueOf(productoEditar.getStock()) : "");
        txtStock.setPromptText("Cantidad en stock");

        DatePicker dpFechaLanzamiento = new DatePicker(productoEditar != null ? productoEditar.getFechaLanzamientoDate() : LocalDate.now());

        CheckBox chkHabilitado = new CheckBox("Producto habilitado");
        chkHabilitado.setSelected(productoEditar == null || productoEditar.isHabilitado());

        // Campos específicos para ProductoBiotec
        TextField txtPatente = new TextField();
        txtPatente.setPromptText("Número de patente");

        DatePicker dpFechaCaducidad = new DatePicker();

        TextField txtEfectoEspecial = new TextField();
        txtEfectoEspecial.setPromptText("Efecto especial");

        TextArea txtIngredientes = new TextArea();
        txtIngredientes.setPromptText("Ingredientes activos (uno por línea)");
        txtIngredientes.setPrefRowCount(3);

        TextArea txtRestricciones = new TextArea();
        txtRestricciones.setPromptText("Restricciones de uso");
        txtRestricciones.setPrefRowCount(2);

        // Layout básico
        grid.add(new Label("Tipo:"), 0, 0);
        grid.add(cmbTipo, 1, 0);

        grid.add(new Label("Nombre:*"), 0, 1);
        grid.add(txtNombre, 1, 1);

        grid.add(new Label("Descripción:"), 0, 2);
        grid.add(txtDescripcion, 1, 2);

        grid.add(new Label("Precio (COP):*"), 0, 3);
        grid.add(txtPrecio, 1, 3);

        grid.add(new Label("Stock:*"), 0, 4);
        grid.add(txtStock, 1, 4);

        grid.add(new Label("Fecha Lanzamiento:*"), 0, 5);
        grid.add(dpFechaLanzamiento, 1, 5);

        grid.add(chkHabilitado, 1, 6);

        // Mostrar/ocultar campos biotec según el tipo
        int rowBiotec = 7;
        cmbTipo.setOnAction(e -> {
            boolean esBiotec = cmbTipo.getValue().equals("Biotecnológico");

            if (esBiotec && !grid.getChildren().contains(txtPatente)) {
                grid.add(new Label("--- Datos Biotecnológicos ---"), 0, rowBiotec);
                grid.add(new Label("Patente:"), 0, rowBiotec + 1);
                grid.add(txtPatente, 1, rowBiotec + 1);
                grid.add(new Label("Fecha Caducidad:"), 0, rowBiotec + 2);
                grid.add(dpFechaCaducidad, 1, rowBiotec + 2);
                grid.add(new Label("Efecto Especial:"), 0, rowBiotec + 3);
                grid.add(txtEfectoEspecial, 1, rowBiotec + 3);
                grid.add(new Label("Ingredientes Activos:"), 0, rowBiotec + 4);
                grid.add(txtIngredientes, 1, rowBiotec + 4);
                grid.add(new Label("Restricciones:"), 0, rowBiotec + 5);
                grid.add(txtRestricciones, 1, rowBiotec + 5);
            } else if (!esBiotec) {
                grid.getChildren().removeIf(node ->
                        node == txtPatente || node == dpFechaCaducidad ||
                                node == txtEfectoEspecial || node == txtIngredientes || node == txtRestricciones ||
                                (node instanceof Label && GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) >= rowBiotec)
                );
            }
        });

        // Si estamos editando un producto biotec, mostrar campos
        if (productoEditar != null && productoEditar.getTipo().equals("Biotecnológico")) {
            txtPatente.setText(productoEditar.getPatente());
            dpFechaCaducidad.setValue(productoEditar.getFechaCaducidadDate());
            txtEfectoEspecial.setText(productoEditar.getEfectoEspecial());
            txtIngredientes.setText(String.join("\n", productoEditar.getIngredientesActivos()));
            txtRestricciones.setText(productoEditar.getRestriccionesUso());
            cmbTipo.fireEvent(new javafx.event.ActionEvent());
        }

        dialog.getDialogPane().setContent(grid);

        // Validación y resultado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    String id = productoEditar != null ? productoEditar.getId() : UUID.randomUUID().toString().substring(0, 8);
                    String tipo = cmbTipo.getValue();
                    String nombre = txtNombre.getText().trim();
                    BigDecimal precio = new BigDecimal(txtPrecio.getText().trim());
                    int stock = Integer.parseInt(txtStock.getText().trim());
                    LocalDate fechaLanzamiento = dpFechaLanzamiento.getValue();
                    boolean habilitado = chkHabilitado.isSelected();

                    if (nombre.isEmpty()) {
                        throw new IllegalArgumentException("El nombre es obligatorio");
                    }

                    if (tipo.equals("Biotecnológico")) {
                        String patente = txtPatente.getText().trim();
                        LocalDate fechaCaducidad = dpFechaCaducidad.getValue();
                        String efectoEspecial = txtEfectoEspecial.getText().trim();
                        List<String> ingredientes = List.of(txtIngredientes.getText().split("\n"));
                        String restricciones = txtRestricciones.getText().trim();

                        return new ProductoTabla(id, tipo, nombre, precio, stock, fechaLanzamiento, habilitado,
                                patente, fechaCaducidad, efectoEspecial, ingredientes, restricciones);
                    } else {
                        return new ProductoTabla(id, tipo, nombre, precio, stock, fechaLanzamiento, habilitado,
                                null, null, null, null, null);
                    }

                } catch (Exception e) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error de Validación");
                    error.setHeaderText("Datos inválidos");
                    error.setContentText("Por favor verifica los campos:\n" + e.getMessage());
                    error.showAndWait();
                    return null;
                }
            }
            return null;
        });

        Optional<ProductoTabla> resultado = dialog.showAndWait();
        resultado.ifPresent(producto -> {
            if (productoEditar != null) {
                // Actualizar producto existente
                int index = listaOriginal.indexOf(productoEditar);
                listaOriginal.set(index, producto);
                System.out.println("✅ Producto actualizado: " + producto.getNombre());
            } else {
                // Agregar nuevo producto
                listaOriginal.add(producto);
                System.out.println("✅ Producto creado: " + producto.getNombre());
            }

            aplicarFiltros();
            actualizarEstadisticas();

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Éxito");
            exito.setHeaderText(null);
            exito.setContentText("Producto guardado exitosamente");
            exito.showAndWait();
        });
    }

    @FXML
    private void handleVolver() {
        // TODO: Detectar de dónde vino (Sakura o AdminContenido o Consejo Sombrío)
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/sakura/main-sakura.fxml", "Sakura Market - Dashboard Ejecutivo");
    }

    @FXML
    private void handleCerrarSesion() {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        NavigationUtil.navegarA(stage, "/view/login.fxml", "Sakura Market - Login");
    }

    // ==================== CLASE INTERNA PARA LA TABLA ====================

    public static class ProductoTabla {
        private final String id;
        private final String tipo;
        private final String nombre;
        private final BigDecimal precio;
        private final int stock;
        private final LocalDate fechaLanzamiento;
        private final boolean habilitado;

        // Campos específicos de ProductoBiotec (null si es Producto normal)
        private final String patente;
        private final LocalDate fechaCaducidad;
        private final String efectoEspecial;
        private final List<String> ingredientesActivos;
        private final String restriccionesUso;

        public ProductoTabla(String id, String tipo, String nombre, BigDecimal precio, int stock,
                             LocalDate fechaLanzamiento, boolean habilitado, String patente,
                             LocalDate fechaCaducidad, String efectoEspecial,
                             List<String> ingredientesActivos, String restriccionesUso) {
            this.id = id;
            this.tipo = tipo;
            this.nombre = nombre;
            this.precio = precio;
            this.stock = stock;
            this.fechaLanzamiento = fechaLanzamiento;
            this.habilitado = habilitado;
            this.patente = patente;
            this.fechaCaducidad = fechaCaducidad;
            this.efectoEspecial = efectoEspecial;
            this.ingredientesActivos = ingredientesActivos != null ? ingredientesActivos : new ArrayList<>();
            this.restriccionesUso = restriccionesUso;
        }

        // Getters
        public String getId() { return id; }
        public String getTipo() { return tipo; }
        public String getNombre() { return nombre; }
        public BigDecimal getPrecio() { return precio; }
        public int getStock() { return stock; }
        public LocalDate getFechaLanzamientoDate() { return fechaLanzamiento; }
        public String getFechaLanzamiento() { return fechaLanzamiento.toString(); }
        public boolean isHabilitado() { return habilitado; }
        public String getEstado() { return habilitado ? "✅ Habilitado" : "❌ Deshabilitado"; }

        // Getters ProductoBiotec
        public String getPatente() { return patente; }
        public LocalDate getFechaCaducidadDate() { return fechaCaducidad; }
        public String getFechaCaducidad() { return fechaCaducidad != null ? fechaCaducidad.toString() : ""; }
        public String getEfectoEspecial() { return efectoEspecial; }
        public List<String> getIngredientesActivos() { return ingredientesActivos; }
        public String getRestriccionesUso() { return restriccionesUso; }
    }
}

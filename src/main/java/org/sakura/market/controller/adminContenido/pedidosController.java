package org.sakura.market.controller.adminContenido;

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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class pedidosController {

    @FXML private TextField txtBuscar;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private TableView<PedidoTabla> tablaPedidos;
    @FXML private Button btnVolver;
    @FXML private Button btnCerrarSesion;

    @FXML private Label lblTotalPedidos;
    @FXML private Label lblPendientes;
    @FXML private Label lblEnviados;
    @FXML private Label lblEntregados;
    @FXML private Label lblCancelados;

    private ObservableList<PedidoTabla> listaPedidos;
    private ObservableList<PedidoTabla> listaOriginal;

    @FXML
    public void initialize() {
        System.out.println("✅ Gestión de Pedidos inicializada");

        configurarTabla();
        configurarFiltros();
        cargarPedidosSimulados();
        configurarBusquedaEnTiempoReal();
        actualizarEstadisticas();
    }

    private void configurarTabla() {
        // Configurar columnas
        TableColumn<PedidoTabla, String> colId = (TableColumn<PedidoTabla, String>) tablaPedidos.getColumns().get(0);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PedidoTabla, String> colCliente = (TableColumn<PedidoTabla, String>) tablaPedidos.getColumns().get(1);
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        TableColumn<PedidoTabla, String> colFecha = (TableColumn<PedidoTabla, String>) tablaPedidos.getColumns().get(2);
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<PedidoTabla, String> colTotal = (TableColumn<PedidoTabla, String>) tablaPedidos.getColumns().get(3);
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<PedidoTabla, String> colEstado = (TableColumn<PedidoTabla, String>) tablaPedidos.getColumns().get(4);
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoDisplay"));

        TableColumn<PedidoTabla, String> colMetodoPago = (TableColumn<PedidoTabla, String>) tablaPedidos.getColumns().get(5);
        colMetodoPago.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));

        TableColumn<PedidoTabla, String> colFechaEntrega = (TableColumn<PedidoTabla, String>) tablaPedidos.getColumns().get(6);
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));

        // Columna de acciones
        TableColumn<PedidoTabla, Void> colAcciones = (TableColumn<PedidoTabla, Void>) tablaPedidos.getColumns().get(7);
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnVer = new Button("👁️");
            private final Button btnCambiarEstado = new Button("🔄");
            private final Button btnCancelar = new Button("❌");
            private final HBox contenedor = new HBox(5, btnVer, btnCambiarEstado, btnCancelar);

            {
                btnVer.setStyle("-fx-background-color: #FFB3C1; -fx-text-fill: #1a1a1a; -fx-padding: 5 10; -fx-background-radius: 4; -fx-font-size: 12;");
                btnCambiarEstado.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: #FFB3C1; -fx-padding: 5 10; -fx-background-radius: 4; -fx-border-color: #FFB3C1; -fx-border-width: 1; -fx-border-radius: 4; -fx-font-size: 12;");
                btnCancelar.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: #FFB3C1; -fx-padding: 5 10; -fx-background-radius: 4; -fx-border-color: #FFB3C1; -fx-border-width: 1; -fx-border-radius: 4; -fx-font-size: 12;");

                btnVer.setTooltip(new Tooltip("Ver detalles del pedido"));
                btnCambiarEstado.setTooltip(new Tooltip("Cambiar estado"));
                btnCancelar.setTooltip(new Tooltip("Cancelar pedido"));

                btnVer.setOnAction(event -> {
                    PedidoTabla pedido = getTableView().getItems().get(getIndex());
                    handleVerDetalles(pedido);
                });

                btnCambiarEstado.setOnAction(event -> {
                    PedidoTabla pedido = getTableView().getItems().get(getIndex());
                    handleCambiarEstado(pedido);
                });

                btnCancelar.setOnAction(event -> {
                    PedidoTabla pedido = getTableView().getItems().get(getIndex());
                    handleCancelarPedido(pedido);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    PedidoTabla pedido = getTableView().getItems().get(getIndex());
                    // Deshabilitar botones si está cancelado o entregado
                    btnCambiarEstado.setDisable(pedido.getEstado().equals("CANCELADA") || pedido.getEstado().equals("ENTREGADA"));
                    btnCancelar.setDisable(pedido.getEstado().equals("CANCELADA") || pedido.getEstado().equals("ENTREGADA"));
                    setGraphic(contenedor);
                }
            }
        });
    }

    private void configurarFiltros() {
        cmbFiltroEstado.getItems().addAll("Todos", "PENDIENTE", "PAGADA", "ENVIADA", "ENTREGADA", "CANCELADA");
        cmbFiltroEstado.setValue("Todos");
        cmbFiltroEstado.setOnAction(e -> aplicarFiltros());
    }

    private void configurarBusquedaEnTiempoReal() {
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltros();
        });
    }

    private void cargarPedidosSimulados() {
        // TODO: Reemplazar con datos reales del modelo
        listaOriginal = FXCollections.observableArrayList(
                new PedidoTabla(
                        UUID.randomUUID().toString().substring(0, 8),
                        "Juan Pérez",
                        LocalDate.of(2024, 10, 15),
                        new BigDecimal("450000"),
                        "PENDIENTE",
                        "Tarjeta Crédito",
                        null,
                        List.of(
                                new LineaCompraSimulada("Laptop HP", 1, new BigDecimal("2500000")),
                                new LineaCompraSimulada("Mouse Logitech", 2, new BigDecimal("45000"))
                        )
                ),

                new PedidoTabla(
                        UUID.randomUUID().toString().substring(0, 8),
                        "María García",
                        LocalDate.of(2024, 10, 20),
                        new BigDecimal("680000"),
                        "PAGADA",
                        "PSE",
                        LocalDate.of(2024, 10, 25),
                        List.of(
                                new LineaCompraSimulada("Suero Regenerador X1", 2, new BigDecimal("350000"))
                        )
                ),

                new PedidoTabla(
                        UUID.randomUUID().toString().substring(0, 8),
                        "Carlos Rodríguez",
                        LocalDate.of(2024, 10, 22),
                        new BigDecimal("180000"),
                        "ENVIADA",
                        "Tarjeta Débito",
                        LocalDate.of(2024, 10, 28),
                        List.of(
                                new LineaCompraSimulada("Teclado Mecánico", 1, new BigDecimal("180000"))
                        )
                ),

                new PedidoTabla(
                        UUID.randomUUID().toString().substring(0, 8),
                        "Ana Martínez",
                        LocalDate.of(2024, 10, 18),
                        new BigDecimal("850000"),
                        "ENTREGADA",
                        "Efectivo",
                        LocalDate.of(2024, 10, 22),
                        List.of(
                                new LineaCompraSimulada("Monitor 4K Samsung", 1, new BigDecimal("850000"))
                        )
                ),

                new PedidoTabla(
                        UUID.randomUUID().toString().substring(0, 8),
                        "Luis Torres",
                        LocalDate.of(2024, 10, 25),
                        new BigDecimal("540000"),
                        "CANCELADA",
                        "Tarjeta Crédito",
                        null,
                        List.of(
                                new LineaCompraSimulada("Laptop HP", 1, new BigDecimal("2500000")),
                                new LineaCompraSimulada("Teclado Mecánico", 1, new BigDecimal("180000"))
                        )
                ),

                new PedidoTabla(
                        UUID.randomUUID().toString().substring(0, 8),
                        "Laura Sánchez",
                        LocalDate.of(2024, 11, 1),
                        new BigDecimal("120000"),
                        "PAGADA",
                        "PSE",
                        LocalDate.of(2024, 11, 5),
                        List.of(
                                new LineaCompraSimulada("Vacuna Antiviral BT-9", 1, new BigDecimal("120000"))
                        )
                )
        );

        listaPedidos = FXCollections.observableArrayList(listaOriginal);
        tablaPedidos.setItems(listaPedidos);
        System.out.println("✅ Cargados " + listaOriginal.size() + " pedidos simulados");
    }

    private void aplicarFiltros() {
        String busqueda = txtBuscar.getText().toLowerCase().trim();
        String estadoFiltro = cmbFiltroEstado.getValue();

        ObservableList<PedidoTabla> filtrados = FXCollections.observableArrayList();

        for (PedidoTabla pedido : listaOriginal) {
            // Filtro de búsqueda
            boolean coincideBusqueda = busqueda.isEmpty() ||
                    pedido.getId().toLowerCase().contains(busqueda) ||
                    pedido.getCliente().toLowerCase().contains(busqueda);

            // Filtro de estado
            boolean coincideEstado = estadoFiltro.equals("Todos") || pedido.getEstado().equals(estadoFiltro);

            if (coincideBusqueda && coincideEstado) {
                filtrados.add(pedido);
            }
        }

        tablaPedidos.setItems(filtrados);
        actualizarEstadisticas();
    }

    private void actualizarEstadisticas() {
        int total = listaOriginal.size();
        int pendientes = (int) listaOriginal.stream().filter(p -> p.getEstado().equals("PENDIENTE")).count();
        int enviados = (int) listaOriginal.stream().filter(p -> p.getEstado().equals("ENVIADA")).count();
        int entregados = (int) listaOriginal.stream().filter(p -> p.getEstado().equals("ENTREGADA")).count();
        int cancelados = (int) listaOriginal.stream().filter(p -> p.getEstado().equals("CANCELADA")).count();

        lblTotalPedidos.setText(String.valueOf(total));
        lblPendientes.setText(String.valueOf(pendientes));
        lblEnviados.setText(String.valueOf(enviados));
        lblEntregados.setText(String.valueOf(entregados));
        lblCancelados.setText(String.valueOf(cancelados));
    }

    private void handleVerDetalles(PedidoTabla pedido) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Detalles del Pedido");
        dialog.setHeaderText("Pedido #" + pedido.getId());

        // Contenido del diálogo
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        contenido.setStyle("-fx-background-color: #1a1a1a;");

        // Información del pedido
        GridPane gridInfo = new GridPane();
        gridInfo.setHgap(15);
        gridInfo.setVgap(10);

        gridInfo.add(crearLabel("Cliente:", true), 0, 0);
        gridInfo.add(crearLabel(pedido.getCliente(), false), 1, 0);

        gridInfo.add(crearLabel("Fecha:", true), 0, 1);
        gridInfo.add(crearLabel(pedido.getFecha(), false), 1, 1);

        gridInfo.add(crearLabel("Estado:", true), 0, 2);
        gridInfo.add(crearLabel(pedido.getEstadoDisplay(), false), 1, 2);

        gridInfo.add(crearLabel("Método de Pago:", true), 0, 3);
        gridInfo.add(crearLabel(pedido.getMetodoPago(), false), 1, 3);

        gridInfo.add(crearLabel("Fecha Entrega:", true), 0, 4);
        gridInfo.add(crearLabel(pedido.getFechaEntrega(), false), 1, 4);

        // Lista de productos
        Label lblProductos = crearLabel("Productos:", true);
        lblProductos.setStyle(lblProductos.getStyle() + "-fx-font-size: 16px;");

        VBox vboxProductos = new VBox(8);
        vboxProductos.setStyle("-fx-background-color: #2d2d2d; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #FFB3C1; -fx-border-width: 1; -fx-border-radius: 8;");

        for (LineaCompraSimulada linea : pedido.getLineasCompra()) {
            HBox producto = new HBox(15);
            producto.setStyle("-fx-padding: 8; -fx-background-color: rgba(255, 179, 193, 0.05); -fx-background-radius: 6;");

            Label lblNombre = crearLabel(linea.getProducto(), false);
            lblNombre.setStyle(lblNombre.getStyle() + "-fx-font-weight: bold;");

            Label lblCantidad = crearLabel("x" + linea.getCantidad(), false);
            Label lblPrecio = crearLabel("COP " + linea.getPrecioUnitario(), false);
            Label lblSubtotal = crearLabel("= COP " + linea.getSubtotal(), false);
            lblSubtotal.setStyle(lblSubtotal.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #FFB3C1;");

            producto.getChildren().addAll(lblNombre, new Label("-"), lblCantidad, new Label("@"), lblPrecio, lblSubtotal);
            vboxProductos.getChildren().add(producto);
        }

        // Total
        HBox hboxTotal = new HBox(10);
        hboxTotal.setStyle("-fx-padding: 10; -fx-background-color: rgba(255, 179, 193, 0.15); -fx-background-radius: 6;");
        Label lblTotalTexto = crearLabel("TOTAL:", true);
        lblTotalTexto.setStyle(lblTotalTexto.getStyle() + "-fx-font-size: 18px;");
        Label lblTotalValor = crearLabel("COP " + pedido.getTotal(), false);
        lblTotalValor.setStyle(lblTotalValor.getStyle() + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #FFB3C1;");
        hboxTotal.getChildren().addAll(lblTotalTexto, lblTotalValor);

        contenido.getChildren().addAll(gridInfo, new Separator(), lblProductos, vboxProductos, hboxTotal);
        dialog.getDialogPane().setContent(contenido);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private Label crearLabel(String texto, boolean esTitulo) {
        Label label = new Label(texto);
        if (esTitulo) {
            label.setStyle("-fx-text-fill: #FFB3C1; -fx-font-weight: bold; -fx-font-size: 14px;");
        } else {
            label.setStyle("-fx-text-fill: #FFF5E1; -fx-font-size: 14px;");
        }
        return label;
    }

    private void handleCambiarEstado(PedidoTabla pedido) {
        List<String> estadosDisponibles = new ArrayList<>();

        switch (pedido.getEstado()) {
            case "PENDIENTE":
                estadosDisponibles.addAll(List.of("PAGADA", "CANCELADA"));
                break;
            case "PAGADA":
                estadosDisponibles.addAll(List.of("ENVIADA", "CANCELADA"));
                break;
            case "ENVIADA":
                estadosDisponibles.add("ENTREGADA");
                break;
            default:
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Estado Final");
                info.setHeaderText("No se puede cambiar el estado");
                info.setContentText("Este pedido está en un estado final (ENTREGADA o CANCELADA)");
                info.showAndWait();
                return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(estadosDisponibles.get(0), estadosDisponibles);
        dialog.setTitle("Cambiar Estado del Pedido");
        dialog.setHeaderText("Pedido #" + pedido.getId());
        dialog.setContentText("Selecciona el nuevo estado:");

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(nuevoEstado -> {
            // Actualizar estado
            int index = listaOriginal.indexOf(pedido);
            PedidoTabla pedidoActualizado = new PedidoTabla(
                    pedido.getId(),
                    pedido.getCliente(),
                    pedido.getFechaDate(),
                    pedido.getTotalBigDecimal(),
                    nuevoEstado,
                    pedido.getMetodoPago(),
                    nuevoEstado.equals("ENVIADA") || nuevoEstado.equals("ENTREGADA") ?
                            LocalDate.now().plusDays(5) : pedido.getFechaEntregaDate(),
                    pedido.getLineasCompra()
            );

            listaOriginal.set(index, pedidoActualizado);
            aplicarFiltros();
            actualizarEstadisticas();

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Estado Actualizado");
            exito.setHeaderText(null);
            exito.setContentText("El estado del pedido ha sido actualizado a: " + nuevoEstado);
            exito.showAndWait();

            System.out.println("✅ Estado actualizado: " + pedido.getId() + " -> " + nuevoEstado);
        });
    }

    private void handleCancelarPedido(PedidoTabla pedido) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cancelar Pedido");
        confirmacion.setHeaderText("¿Estás seguro de cancelar este pedido?");
        confirmacion.setContentText(
                "Pedido #" + pedido.getId() + "\n" +
                        "Cliente: " + pedido.getCliente() + "\n" +
                        "Total: " + pedido.getTotal() + "\n\n" +
                        "Esta acción no se puede deshacer."
        );

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Actualizar a CANCELADA
            int index = listaOriginal.indexOf(pedido);
            PedidoTabla pedidoActualizado = new PedidoTabla(
                    pedido.getId(),
                    pedido.getCliente(),
                    pedido.getFechaDate(),
                    pedido.getTotalBigDecimal(),
                    "CANCELADA",
                    pedido.getMetodoPago(),
                    null,
                    pedido.getLineasCompra()
            );

            listaOriginal.set(index, pedidoActualizado);
            aplicarFiltros();
            actualizarEstadisticas();

            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Pedido Cancelado");
            exito.setHeaderText(null);
            exito.setContentText("El pedido ha sido cancelado exitosamente.");
            exito.showAndWait();

            System.out.println("✅ Pedido cancelado: " + pedido.getId());
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

    // ==================== CLASES INTERNAS ====================

    public static class PedidoTabla {
        private final String id;
        private final String cliente;
        private final LocalDate fecha;
        private final BigDecimal total;
        private final String estado;
        private final String metodoPago;
        private final LocalDate fechaEntrega;
        private final List<LineaCompraSimulada> lineasCompra;

        public PedidoTabla(String id, String cliente, LocalDate fecha, BigDecimal total,
                           String estado, String metodoPago, LocalDate fechaEntrega,
                           List<LineaCompraSimulada> lineasCompra) {
            this.id = id;
            this.cliente = cliente;
            this.fecha = fecha;
            this.total = total;
            this.estado = estado;
            this.metodoPago = metodoPago;
            this.fechaEntrega = fechaEntrega;
            this.lineasCompra = lineasCompra;
        }

        // Getters
        public String getId() { return id; }
        public String getCliente() { return cliente; }
        public LocalDate getFechaDate() { return fecha; }
        public String getFecha() { return fecha.toString(); }
        public BigDecimal getTotalBigDecimal() { return total; }
        public String getTotal() { return "COP " + total; }
        public String getEstado() { return estado; }
        public String getEstadoDisplay() {
            return switch (estado) {
                case "PENDIENTE" -> "⏳ Pendiente";
                case "PAGADA" -> "💳 Pagada";
                case "ENVIADA" -> "📦 Enviada";
                case "ENTREGADA" -> "✅ Entregada";
                case "CANCELADA" -> "❌ Cancelada";
                default -> estado;
            };
        }
        public String getMetodoPago() { return metodoPago; }
        public LocalDate getFechaEntregaDate() { return fechaEntrega; }
        public String getFechaEntrega() { return fechaEntrega != null ? fechaEntrega.toString() : "N/A"; }
        public List<LineaCompraSimulada> getLineasCompra() { return lineasCompra; }
    }

    public static class LineaCompraSimulada {
        private final String producto;
        private final int cantidad;
        private final BigDecimal precioUnitario;

        public LineaCompraSimulada(String producto, int cantidad, BigDecimal precioUnitario) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
        }

        public String getProducto() { return producto; }
        public int getCantidad() { return cantidad; }
        public BigDecimal getPrecioUnitario() { return precioUnitario; }
        public BigDecimal getSubtotal() { return precioUnitario.multiply(BigDecimal.valueOf(cantidad)); }
    }
}

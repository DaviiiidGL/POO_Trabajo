import java.time.LocalDate;
import java.util.*;
import models.users.*;
import models.products.*;
import models.shopping.*;
import utils.IO;
import exceptions.*;

public class MainGlowUp {
    static List<Usuario> usuarios = new ArrayList<>();
    static List<Cliente> clientes = new ArrayList<>();
    static List<Producto> productos = new ArrayList<>();
    static List<AdminCuentas> adminsCuentas = new ArrayList<>();
    static List<AdminContenidos> adminsContenido = new ArrayList<>();
    static List<Sakura> sakuras = new ArrayList<>();
    static List<Compra> compras = new ArrayList<>();

    public static void main(String[] args) {
        seed();
        Usuario usuarioActual = null;

        IO.println("==== GlowUp | Login ====");
        while (usuarioActual == null) {
            IO.print("Correo: "); var correo = IO.readln();
            IO.print("Contraseña: "); var pass = IO.readln();
            usuarioActual = login(correo, pass);
            if (usuarioActual == null) IO.println("Credenciales inválidas. Intenta de nuevo.");
        }
        IO.println("Hola " + usuarioActual.getNombre() + " | " + usuarioActual.getRol());

        boolean salir = false;
        while (!salir) {
            salir = switch (usuarioActual.getRol()) {
                case CLIENTE -> menuCliente((Cliente) usuarioActual);
                case ADMIN_CUENTAS -> menuAdminCuentas((AdminCuentas) usuarioActual);
                case ADMIN_CONTENIDOS -> menuAdminContenidos((AdminContenidos) usuarioActual);
                case SAKURA -> menuSakura((Sakura) usuarioActual);
                default -> true;
            };
        }
        IO.println("¡Hasta pronto!");
    }

    static Usuario login(String correo, String password) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(correo) && u.getPasswordHash().equals(password))
                .findFirst().orElse(null);
    }

    static boolean menuCliente(Cliente cliente) {
        IO.println("""
            Menú Cliente
            1. Ver catálogo habilitado
            2. Añadir producto al carrito
            3. Ver carrito
            4. Eliminar producto del carrito
            5. Comprar productos del carrito
            6. Ver perfil
            7. Agregar método de pago
            8. Agregar dirección
            9. Ver historial de compras
            0. Salir
        """);
        try {
            var op = IO.readln();
            switch (op) {
                case "1" -> {
                    var disponibles = productos.stream().filter(Producto::isHabilitado).toList();
                    if (disponibles.isEmpty()) {
                        IO.println("No hay productos disponibles en este momento.");
                    } else {
                        disponibles.forEach(p -> {
                            IO.println("===================");
                            p.mostrarInfo();
                        });
                    }
                }
                case "2" -> {
                    var disponibles = productos.stream().filter(Producto::isHabilitado).toList();
                    if (disponibles.isEmpty()) {
                        IO.println("No hay productos disponibles para agregar.");
                        return false;
                    }
                    for (int i = 0; i < disponibles.size(); i++)
                        IO.println(i + " - " + disponibles.get(i).getNombre() + " ($" + disponibles.get(i).getPrecio() + ")");
                    IO.print("Elige producto #: ");
                    int idx = Integer.parseInt(IO.readln());
                    if (idx < 0 || idx >= disponibles.size()) {
                        IO.println("Número de producto inválido.");
                        return false;
                    }
                    Producto prod = disponibles.get(idx);
                    IO.print("Cantidad a añadir: ");
                    int cantidad = Integer.parseInt(IO.readln());
                    if (cantidad <= 0) {
                        IO.println("La cantidad debe ser positiva.");
                        return false;
                    }
                    if (cantidad > prod.getStock()) {
                        IO.println("No hay suficiente stock. Stock disponible: " + prod.getStock());
                        return false;
                    }
                    cliente.getCarrito().agregarProducto(prod, cantidad);
                }
                case "3" -> {
                    if (cliente.getCarrito().getLineasCarrito().isEmpty()) {
                        IO.println("El carrito está vacío.");
                    } else {
                        double total = 0;
                        for (LineaCarrito lc : cliente.getCarrito().getLineasCarrito()) {
                            IO.println(lc.getProducto().getNombre() + " x" + lc.getCantidad() + 
                                     " = $" + (lc.getProducto().getPrecio() * lc.getCantidad()));
                            total += lc.getProducto().getPrecio() * lc.getCantidad();
                        }
                        IO.println("Total del carrito: $" + total);
                    }
                }
                case "4" -> {
                    var lineas = cliente.getCarrito().getLineasCarrito();
                    if (lineas.isEmpty()) {
                        IO.println("El carrito está vacío.");
                        return false;
                    }
                    for (int i = 0; i < lineas.size(); i++) {
                        var lc = lineas.get(i);
                        IO.println(i + " - " + lc.getProducto().getNombre() + " x" + lc.getCantidad());
                    }
                    IO.print("Elige producto a eliminar #: ");
                    int idx = Integer.parseInt(IO.readln());
                    if (idx >= 0 && idx < lineas.size()) {
                        cliente.getCarrito().eliminarProducto(lineas.get(idx).getProducto());
                    } else {
                        IO.println("Número de producto inválido.");
                    }
                }
                case "5" -> {
                    if (cliente.getCarrito().getLineasCarrito().isEmpty()) {
                        IO.println("El carrito está vacío.");
                    } else if (cliente.getMetodosPago().isEmpty()) {
                        IO.println("Necesitas agregar al menos un método de pago antes de comprar.");
                    } else if (cliente.getDirecciones().isEmpty()) {
                        IO.println("Necesitas agregar al menos una dirección de envío antes de comprar.");
                    } else {
                        var compra = cliente.comprarCarrito();
                        compras.add(compra);
                        IO.println("¡Compra realizada con éxito!");
                        IO.println("Fecha: " + compra.getFechaCompra());
                        IO.println("Total: $" + compra.getTotal());
                    }
                }
                case "6" -> cliente.mostrarInfo();
                case "7" -> {
                    IO.println("Tipos de método de pago disponibles:");
                    for (TipoMetodoPago tipo : TipoMetodoPago.values()) {
                        IO.println(tipo.ordinal() + " - " + tipo);
                    }
                    IO.print("Elige tipo (#): ");
                    int tipoIdx = Integer.parseInt(IO.readln());
                    if (tipoIdx < 0 || tipoIdx >= TipoMetodoPago.values().length) {
                        IO.println("Tipo de método de pago inválido.");
                        return false;
                    }
                    IO.print("Número/Identificador: ");
                    String numero = IO.readln();
                    var metodo = new MetodoPago(System.currentTimeMillis(), TipoMetodoPago.values()[tipoIdx], numero);
                    cliente.agregarMetodoDePago(metodo);
                    IO.println("Método de pago agregado con éxito.");
                }
                case "8" -> {
                    IO.print("Calle: ");
                    String calle = IO.readln();
                    IO.print("Número: ");
                    String numero = IO.readln();
                    IO.print("Ciudad: ");
                    String ciudad = IO.readln();
                    IO.print("Código Postal: ");
                    String cp = IO.readln();
                    var direccion = new Direccion(System.currentTimeMillis(), calle, numero, ciudad, cp);
                    cliente.agregarDireccion(direccion);
                    IO.println("Dirección agregada con éxito.");
                }
                case "9" -> {
                    var historial = cliente.getHistorial();
                    if (historial.isEmpty()) {
                        IO.println("No hay compras en el historial.");
                    } else {
                        historial.forEach(c -> {
                            IO.println("=== Compra " + c.getId() + " ===");
                            IO.println("Fecha: " + c.getFechaCompra());
                            IO.println("Total: $" + c.getTotal());
                            IO.println("Estado: " + c.getEstado());
                            IO.println("Productos:");
                            c.getLineasCompra().forEach(lc -> 
                                IO.println("- " + lc.getProducto().getNombre() + " x" + lc.getCantidad())
                            );
                            IO.println(" ");
                        });
                    }
                }
                case "0" -> { return true; }
                default -> IO.println("Opción inválida.");
            }
        } catch (NumberFormatException e) {
            IO.println("Error: Entrada numérica inválida.");
        } catch (ValidacionException e) {
            IO.println("Error de validación: " + e.getMessage());
        } catch (StockException e) {
            IO.println("Error de stock: " + e.getMessage());
        } catch (Exception e) {
            IO.println("Error inesperado: " + e.getMessage());
        }
        return false;
    }

    static boolean menuAdminCuentas(AdminCuentas admin) {
        IO.println("""
            Menú AdminCuentas
            1. Editar cliente
            2. Suspender cliente
            3. Activar cliente
            4. Eliminar cliente
            5. Ver clientes
            0. Salir
        """);
        var op = IO.readln();
        switch (op) {
            case "1" -> {
                var c = seleccionarCliente();
                IO.print("Nuevo nombre: "); var n = IO.readln();
                IO.print("Nuevo email: "); var e = IO.readln();
                IO.print("Nuevo tel: "); var t = IO.readln();
                admin.editarCliente(c, n, e, t);
            }
            case "2" -> admin.suspenderCliente(seleccionarCliente());
            case "3" -> admin.activarCliente(seleccionarCliente());
            case "4" -> admin.eliminarCliente(seleccionarCliente());
            case "5" -> clientes.forEach(Cliente::mostrarInfo);
            case "0" -> { return true; }
            default -> IO.println("Opción inválida.");
        }
        return false;
    }

    static boolean menuAdminContenidos(AdminContenidos admin) {
        IO.println("""
            Menú AdminContenidos
            1. Editar producto
            2. Habilitar producto
            3. Deshabilitar producto
            4. Ver productos
            0. Salir
        """);
        var op = IO.readln();
        switch (op) {
            case "1" -> {
                var p = seleccionarProducto();
                IO.print("Nuevo nombre: "); var n = IO.readln();
                IO.print("Descripción: "); var d = IO.readln();
                IO.print("Precio: "); var precio = Double.parseDouble(IO.readln());
                IO.print("Stock: "); var stock = Integer.parseInt(IO.readln());
                admin.editarProducto(p, n, d, precio, stock);
            }
            case "2" -> admin.habilitarProducto(seleccionarProducto());
            case "3" -> admin.deshabilitarProducto(seleccionarProducto());
            case "4" -> productos.forEach(Producto::mostrarInfo);
            case "0" -> { return true; }
            default -> IO.println("Opción inválida.");
        }
        return false;
    }

    static boolean menuSakura(Sakura sakura) {
        IO.println("""
            Menú Sakura
            1. Tomar control total
            2. Eliminar usuario
            3. Editar cualquier usuario
            4. Editar cualquier producto
            5. Ver historial de compras
            6. Info Util
            0. Salir
        """);
        var op = IO.readln();
        switch (op) {
            case "1" -> sakura.tomarControlTotal();
            case "2" -> sakura.eliminarUsuario(seleccionarUsuario());
            case "3" -> {
                var u = seleccionarUsuario();
                IO.print("Nuevo nombre: "); var n = IO.readln();
                IO.print("Nuevo email: "); var e = IO.readln();
                if (u instanceof Cliente cli) {
                    IO.print("Nuevo tel: "); var t = IO.readln();
                    cli.setTelefono(t);
                }
                u.setNombre(n); u.setEmail(e);
                IO.println("Editado.");
            }
            case "4" -> {
                var p = seleccionarProducto();
                IO.print("Nuevo nombre: "); var n = IO.readln();
                IO.print("Descripción: "); var d = IO.readln();
                IO.print("Precio: "); var precio = Double.parseDouble(IO.readln());
                IO.print("Stock: "); var stock = Integer.parseInt(IO.readln());
                p.setNombre(n); p.setDescripcion(d); p.setPrecio(precio); p.setStock(stock);
                IO.println("Editado.");
            }
            case "5" -> compras.forEach(Compra::mostrarInfo);
            case "6" -> IO.println("Si alguien encuentra esta nota, sabrán al fin lo que todos se negaron a creer: Isis de ED no desapareció por arte de magia. Fui yo, Sakura, la cabrita que todos creyeron inocente y curiosa. La verdad es que mi alma lleva demasiado tiempo manchada con su nombre.\n" +
                    "\n" +
                    "Todo comenzó una noche en que el viento soplaba diferente, trayendo rumores del bosque. Isis había descubierto algo. Decía haber visto luces moviéndose bajo la tierra, en los túneles prohibidos detrás del molino. Me lo contó con una emoción que rozaba la locura. Y cuando me pidió que la acompañara, supe que no debía hacerlo… pero lo hice.\n" +
                    "\n" +
                    "Descendimos por las raíces torcidas, con solo una lámpara encendida. Aquel lugar parecía respirar. Las sombras jugaban con nuestras siluetas y las paredes susurraban en ecos inhumanos. Fue entonces cuando ella lo dijo: “Debemos contarlo todo, Sakura. Lo que esconde el Establo Dorado no puede seguir oculto”.\n" +
                    "\n" +
                    "No podía permitirlo. Yo guardaba el secreto más oscuro de todos: ese lugar dependía de mí. Era mi pacto con los que moran en la tierra fría, los que me prometieron fuerza, lealtad y eternidad a cambio de silencio. Si Isis hablaba, el pacto se rompería.\n" +
                    "\n" +
                    "La empujé. No fue planeado, solo un impulso. Su cuerpo cayó en un vacío sin fondo, y el eco de su voz todavía me persigue. Intenté hallarla, pero lo único que encontré fue su lazo púrpura enredado en una raíz.\n" +
                    "\n" +
                    "Desde entonces, cada noche escucho su balido lejano, como una campana perdida. A veces creo verla entre las sombras del molino, observándome, esperando…\n" +
                    "Tal vez no murió. Tal vez ahora forma parte del mismo pacto que me consume.\n" +
                    "\n" +
                    "Isis de ED no desapareció. Fue entregada. Por mí.\n" +
                    "\n" +
                    "Firmado,\n" +
                    "Sakura");
            case "0" -> { return true; }
            default -> IO.println("Opción inválida.");
        }
        return false;
    }

    static Cliente seleccionarCliente() {
        for (int i = 0; i < clientes.size(); i++)
            IO.println(i + " - " + clientes.get(i).getNombre());
        IO.print("Elige cliente (#): ");
        int idx = Integer.parseInt(IO.readln());
        return clientes.get(idx);
    }

    static Producto seleccionarProducto() {
        for (int i = 0; i < productos.size(); i++)
            IO.println(i + " - " + productos.get(i).getNombre());
        IO.print("Elige producto (#): ");
        int idx = Integer.parseInt(IO.readln());
        return productos.get(idx);
    }

    static Usuario seleccionarUsuario() {
        for (int i = 0; i < usuarios.size(); i++)
            IO.println(i + " - " + usuarios.get(i).getNombre() + " [" + usuarios.get(i).getRol() + "]");
        IO.print("Elige usuario (#): ");
        int idx = Integer.parseInt(IO.readln());
        return usuarios.get(idx);
    }

    static void seed() {
        // Crear clientes de ejemplo
        var c1 = new Cliente(1, "Juan Pérez", "juan@demo.com", "1234", LocalDate.now(), "3000011222");
        clientes.add(c1); usuarios.add(c1);
        c1.agregarMetodoDePago(new MetodoPago(1001, TipoMetodoPago.TARJETA_CREDITO, "4532-XXXX-XXXX-1234"));
        c1.agregarMetodoDePago(new MetodoPago(1002, TipoMetodoPago.PAYPAL, "juan.perez@mail.com"));
        c1.agregarDireccion(new Direccion(2001, "Av. Principal", "123", "Ciudad Demo", "12345"));
        c1.agregarDireccion(new Direccion(2002, "Calle Trabajo", "789", "Ciudad Demo", "12345"));

        var c2 = new Cliente(2, "María García", "maria@demo.com", "5678", LocalDate.now(), "3000033444");
        clientes.add(c2); usuarios.add(c2);
        c2.agregarMetodoDePago(new MetodoPago(1003, TipoMetodoPago.DEBITO, "5678-XXXX-XXXX-5678"));
        c2.agregarMetodoDePago(new MetodoPago(1004, TipoMetodoPago.MERCADO_PAGO, "maria.garcia@mail.com"));
        c2.agregarDireccion(new Direccion(2003, "Calle Secundaria", "456", "Ciudad Demo", "12345"));

        var c3 = new Cliente(3, "Carlos Rodríguez", "carlos@demo.com", "9012", LocalDate.now(), "3000055666");
        clientes.add(c3); usuarios.add(c3);
        c3.agregarMetodoDePago(new MetodoPago(1005, TipoMetodoPago.EFECTIVO, "Pago en tienda"));
        c3.agregarDireccion(new Direccion(2004, "Av. Central", "1010", "Ciudad Demo", "12345"));

        // Crear administradores
        var adminC = new AdminCuentas(4, "Ana López", "ana@demo.com", "4321", LocalDate.now());
        adminsCuentas.add(adminC); usuarios.add(adminC);

        var adminCont = new AdminContenidos(5, "Carla Martínez", "carla@demo.com", "5678", LocalDate.now());
        adminsContenido.add(adminCont); usuarios.add(adminCont);

        // Crear Sakura (super admin)
        var sakura = new Sakura(6, "Sakura Admin", "sakura@demo.com", "0000", LocalDate.now(), "superclave", LocalDate.now());
        sakuras.add(sakura); usuarios.add(sakura);

        // Crear productos de ejemplo
        var productos_demo = Arrays.asList(
            // Productos para el cuidado del cabello
            new Producto(100, "Shampoo Reparador Plus", "Shampoo con keratina para cabello dañado", 20.0, 100, LocalDate.now(), Categoria.CABELLO),
            new Producto(101, "Acondicionador Nutritivo", "Acondicionador con aceite de argán", 22.0, 100, LocalDate.now(), Categoria.CABELLO),
            new Producto(102, "Mascarilla Capilar", "Tratamiento profundo para cabello maltratado", 35.0, 50, LocalDate.now(), Categoria.CABELLO),
            
            // Productos faciales
            new Producto(103, "Serum Vitamina C", "Serum facial antioxidante con 20% vitamina C", 45.0, 50, LocalDate.now(), Categoria.FACIAL),
            new Producto(104, "Crema Hidratante", "Crema facial hidratante para todo tipo de piel", 25.0, 75, LocalDate.now(), Categoria.FACIAL),
            new Producto(105, "Protector Solar SPF50", "Protector solar de amplio espectro", 35.0, 40, LocalDate.now(), Categoria.FACIAL),
            new Producto(106, "Limpiador Facial", "Gel limpiador suave para uso diario", 18.0, 80, LocalDate.now(), Categoria.FACIAL),
            
            // Productos de maquillaje
            new Producto(107, "Máscara de Pestañas", "Máscara voluminizadora waterproof", 15.0, 60, LocalDate.now(), Categoria.MAQUILLAJE),
            new Producto(108, "Base de Maquillaje", "Base líquida de alta cobertura", 40.0, 30, LocalDate.now(), Categoria.MAQUILLAJE),
            new Producto(109, "Paleta de Sombras", "Paleta de 12 colores mate y brillantes", 50.0, 25, LocalDate.now(), Categoria.MAQUILLAJE),
            
            // Productos corporales
            new Producto(110, "Aceite Corporal", "Aceite hidratante con vitamina E", 28.0, 45, LocalDate.now(), Categoria.CORPORAL),
            new Producto(111, "Crema Anticelulitis", "Crema reafirmante corporal", 45.0, 35, LocalDate.now(), Categoria.CORPORAL),
            new Producto(112, "Exfoliante Corporal", "Exfoliante con sales del mar muerto", 32.0, 40, LocalDate.now(), Categoria.CORPORAL),
            
            // Productos biotecnológicos
            new ProductoBiotec(113, "Sérum Anti-Edad", "Sérum con péptidos y factores de crecimiento", 120.0, 20, LocalDate.now(), Categoria.FACIAL, "Péptidos bioactivos"),
            new ProductoBiotec(114, "Crema Regeneradora", "Crema con células madre vegetales", 150.0, 15, LocalDate.now(), Categoria.FACIAL, "Células madre de argán")
        );
        productos.addAll(productos_demo);

        // Agregar algunos productos al carrito de los clientes
        try {
            // Carrito de Juan
            c1.getCarrito().agregarProducto(productos.get(0), 2); // Shampoo
            c1.getCarrito().agregarProducto(productos.get(1), 1); // Acondicionador
            c1.getCarrito().agregarProducto(productos.get(2), 1); // Mascarilla

            // Carrito de María
            c2.getCarrito().agregarProducto(productos.get(3), 1); // Serum
            c2.getCarrito().agregarProducto(productos.get(4), 1); // Crema
            c2.getCarrito().agregarProducto(productos.get(8), 2); // Base de maquillaje

            // Simular algunas compras anteriores
            var compraJuan = new Compra(1001, LocalDate.now().minusDays(5), c1);
            compraJuan.agregarLineaCompra(new LineaCompra(productos.get(5), 1)); // Protector solar
            compraJuan.agregarLineaCompra(new LineaCompra(productos.get(6), 2)); // Limpiador
            compraJuan.setEstado(EstadoCompra.ENTREGADO);
            compras.add(compraJuan);
            c1.getHistorial().add(compraJuan);

            var compraMaria = new Compra(1002, LocalDate.now().minusDays(3), c2);
            compraMaria.agregarLineaCompra(new LineaCompra(productos.get(13), 1)); // Sérum Anti-Edad
            compraMaria.agregarLineaCompra(new LineaCompra(productos.get(9), 1)); // Paleta
            compraMaria.setEstado(EstadoCompra.EN_CAMINO);
            compras.add(compraMaria);
            c2.getHistorial().add(compraMaria);

        } catch (Exception e) {
            System.err.println("Error al inicializar datos de ejemplo: " + e.getMessage());
        }
    }
}
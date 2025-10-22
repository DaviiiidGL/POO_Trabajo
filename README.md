Idea de Orden Segun IA

22 oct:

Estudia bien el diagrama. Enumera todas las clases y sus relaciones de herencia/asociación/agregación.

Crea los paquetes base: dominio/modelo, DAO, servicios, utilidades.

Implementa las clases principales de la jerarquía: Usuario, Cliente, Producto, Categoria, Compra, Carrito, etc.

Haz especial énfasis en la herencia (extiende correctamente) y encapsulamiento (privado/protected, getters/setters).

23 oct:

Termina las clases (métodos, constructores, atributos, Enum si es necesario).

Implementa métodos internos de lógica (validaciones, cálculos como subtotal, total).

Prueba la relación entre usuarios/clientes y carritos/compras.

24 oct:

Agrega clases con detalle especial del diagrama (ConsejoSombrio, AdminContenido, etc).

Implementa clases de utilidad y métodos de las asociaciones (por ejemplo, agregarProducto al carrito, calcularTotal en Compra, validarPermiso en Dueña, etc).

25 oct:

Prueba toda la lógica “en memoria”: crea objetos, simula llamadas, asegúrate que los métodos funcionen.

Refactoriza nombres, asegura coherencia y elimina código duplicado.

2. Persistencia y conexión a BBDD (26–30 oct)
26 oct:

Elige gestor de BD (MySQL recomendado por facilidad, aunque Derby/H2 sirve para pruebas rápidas).

Diseña la estructura de tablas mapeando los atributos del diagrama (¡ojo con claves foráneas!), crea script SQL en Workbench o DBeaver.

Instala el driver JDBC de tu BD en la dependencia Maven.

27–28 oct:

Implementa clases DAO para cada entidad principal (ProductoDAO, UsuarioDAO, etc).

Programa métodos CRUD básicos: insertar, consultar, actualizar, eliminar registros.

Prueba conexión, inserción y consulta.

29 oct:

Relaciona tablas por claves foráneas (usuario-cliente, cliente-compras, producto-categoría).

Prueba operaciones multitabla: consulta todo el carrito de un cliente, etc.

30 oct:

Valida bien errores, ajusta tipos de datos (Date, boolean, etc).

Documenta el string de conexión, credenciales y procedimiento para levantar la base.

3. Interfaz gráfica con JavaFX (31 oct – 3 nov)
31 oct:

Dibuja sketch/mockups: pantallas para login, módulo productos, carrito, compra.

Crea el proyecto base JavaFX, configura Maven y prueba ventana principal.

Define los archivos FXML y Controladores para cada pantalla.

1 nov:

Implementa pantalla principal (menú, navegación básica).

Realiza la vista, carga datos de prueba (sin BD real aún).

Conecta botones y cajas de texto a las clases lógicas.

2 nov:

Conecta la GUI (JavaFX) con los DAOs.

Implementa registrar producto, añadir al carrito, mostrar info de usuario/compra en la interfaz.

3 nov:

Pulir diseño: validación de campos, mensajes de feedback, confirmaciones.

Prueba CRUD desde la interfaz (alta/baja/modificación/consulta).

4. Integración, pruebas y documentación (4–5 nov)
4 nov:

Haz pruebas de extremo a extremo: desde la GUI → modelo → BBDD.

Corrige bugs, mejora usabilidad final.

Prepara la documentación (manual, diagrama de arquitectura y breve guía de uso).

5 nov:

Última revisión y testeo.

Prepara entrega: empaqueta proyecto, README, scripts y credenciales ejemplos.

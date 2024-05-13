    package com.example.tap2024.vistas;
    
    import com.example.tap2024.componentes.ButtonCellOrdenDetalle;
    import com.example.tap2024.modelos.Conexion;
    import com.example.tap2024.modelos.OrdenDetalleDao;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.event.EventHandler;
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.Node;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.BorderPane;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;
    import javafx.scene.layout.GridPane;
    import javafx.util.Callback;
    import org.kordamp.bootstrapfx.BootstrapFX;
    import org.kordamp.bootstrapfx.scene.layout.Panel;
    
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    
    import static javafx.application.Application.launch;
    
    public class Taqueria extends Stage {
    
        private String[] arSignos = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        private Panel pnlPrincipal;
        private BorderPane bnpPrincipal;
        private ToolBar tlbMenu;
        private TableView<OrdenDetalleDao> tbvEmpleados;
        private Button btnAgregarEmplado;
        private Scene escena;
        private Map<String, List<String>> categoriaImagenes = new HashMap<>();
        HBox hboxCategorias = new HBox(10);
        HBox hboxBotonesComida = new HBox(20);
        private String categoriaSeleccionada;
        private String comidaSeleccionada;
        TextField tfCantidad = new TextField();
        TextField tfArticulo = new TextField();
        TextField tfPrecio = new TextField();
        private Label lblArticuloo = new Label("'Comida'");
        private Label lblPrecioo = new Label("$");
        private Label lblCantidadd = new Label("0");
        TextField lblTotall = new TextField("$");
        private Map<Integer, Integer> mesaOrdenMap;
        private int proximoIdOrden = 1; // Variable para generar nuevos idOrden
    
        public Taqueria() {
            setTitle("Taqueria Los de Suaperro :)");
            mesaOrdenMap = new HashMap<>();

            VBox pnlPrincipal = new VBox(20);
            pnlPrincipal.setPadding(new Insets(5));
            pnlPrincipal.setAlignment(Pos.CENTER);
            pnlPrincipal.setId("root");
            setupCategoriaImagenes();
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
            // Agregar el Label para las categorías y los VBox al VBox principal
            Label lblCategorias = new Label("CATEGORÍAS");
            lblCategorias.getStyleClass().add("categorias");
    
            Label lblProductos = new Label("PRODUCTOS");
            lblProductos.getStyleClass().add("categorias");
    
            Label lblOrdenes = new Label("ORDENES");
            lblOrdenes.getStyleClass().add("categorias");
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
    
            hboxBotonesComida.setAlignment(Pos.CENTER);
            hboxBotonesComida.getStyleClass().add("boton-adicionales");
    
            for (int i = 1; i <= 5; i++) {
                Button btnAdicional = createStyledButton("Botón " + i, "boton-adicional");
                hboxBotonesComida.getChildren().add(btnAdicional);
            }
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
    
            VBox vboxBotones = new VBox(10);
            Label Menu = new Label("Menu");
            Menu.getStyleClass().add("categorias");
            Button btnMesa = createStyledButton("Mesa", "boton-estilo");
            Button btnCocina = createStyledButton("Cocina", "boton-estilo");
            Button btnTelefono = createStyledButton("Telefono", "boton-estilo");
            Button btnCaja = createStyledButton("Caja", "boton-estilo");
            Button btnLogin = createStyledButton("Login", "boton-estilo");
            btnLogin.setOnAction(e -> {
                // Crear una instancia del formulario de inicio de sesión
                Login loginForm = new Login();
                // Mostrar el formulario de inicio de sesión
                loginForm.show();
            });
    
            vboxBotones.getChildren().addAll(Menu,btnMesa, btnCocina, btnTelefono, btnCaja, btnLogin);
            vboxBotones.getStyleClass().add("Menu");
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
    
            HBox hboxBotonesRegistro = new HBox(20);
            hboxBotonesRegistro.setAlignment(Pos.CENTER);
            hboxBotonesRegistro.getStyleClass().add("registro");
    
            // Crear botón con símbolo "+"
            Button btnMas = createSymbolButton("+");
            btnMas.getStyleClass().add("mas");
            // Crear botón con símbolo "-"
            Button btnMenos = createSymbolButton("-");
            btnMenos.getStyleClass().add("menos");
    
    
            // Event handler para el botón de incremento
            btnMas.setOnAction(event -> {
                // Obtener el valor actual del label y convertirlo a entero
                int cantidadActual = Integer.parseInt(lblCantidadd.getText());
                // Incrementar la cantidad
                cantidadActual++;
                // Actualizar el texto del label con la nueva cantidad
                lblCantidadd.setText(String.valueOf(cantidadActual));
            });
    
            // Event handler para el botón de decremento
            btnMenos.setOnAction(event -> {
                // Obtener el valor actual del label y convertirlo a entero
                int cantidadActual = Integer.parseInt(lblCantidadd.getText());
                // Decrementar la cantidad si es mayor que 0
                if (cantidadActual > 0) {
                    cantidadActual--;
                    // Actualizar el texto del label con la nueva cantidad
                    lblCantidadd.setText(String.valueOf(cantidadActual));
                }
            });
    
            // Crear label "Cantidad"
            Label lblCantidad = new Label("Cantidad:");
            // Crear textField para cantidad
            TextField tfCantidad = new TextField();
            // Crear label "Artículo"
            Label lblArticulo = new Label("Producto:");
            // Crear textField para artículo
            TextField tfArticulo = new TextField();
            // Crear label "Precio"
            Label lblPrecio = new Label("Precio:");
            // Crear textField para precio
            TextField tfPrecio = new TextField();
            // Crear label "Total"
            Label lblTotal = new Label("Total:");
            // Crear textField para total
            TextField tfTotal = new TextField();
            // Crear botón con símbolo de paloma
            Button btnPaloma = createSymbolButton("✔");
            btnPaloma.getStyleClass().add("mas");
    
    
            hboxBotonesRegistro.getChildren().addAll(
                    btnMas, btnMenos,
                    lblCantidad, lblCantidadd,
                    lblArticulo, lblArticuloo,
                    lblPrecio, lblPrecioo,
                    lblTotal, lblTotall,
                    btnPaloma
            );
    
            // Agregar un ChangeListener al campo lblCantidadd
            lblCantidadd.textProperty().addListener((observable, oldValue, newValue) -> {
                // Verificar que el nuevo valor no esté vacío y sea un número
                if (!newValue.isEmpty() && newValue.matches("\\d+")) {
                    // Obtener la cantidad del campo lblCantidadd
                    int cantidad = Integer.parseInt(newValue);
                    // Obtener el precio del campo lblPrecioo y eliminar el signo "$" antes de convertirlo a un número de punto flotante
                    float precio = Float.parseFloat(lblPrecioo.getText().replace("$", ""));
                    // Calcular el total multiplicando la cantidad por el precio
                    float total = cantidad * precio;
                    // Actualizar el texto del campo lblTotall con el resultado de la multiplicación
                    lblTotall.setText("$" + total);
                }
            });
    
            // Agregar un ChangeListener al campo lblPrecioo
            lblPrecioo.textProperty().addListener((observable, oldValue, newValue) -> {
                // Verificar que el nuevo valor no esté vacío y sea un número
                if (!newValue.isEmpty() && newValue.matches("[\\d.]+")) {
                    // Obtener el precio del campo lblPrecioo y eliminar el signo "$" antes de convertirlo a un número de punto flotante
                    float precio = Float.parseFloat(newValue.replace("$", ""));
                    // Verificar que la cantidad en lblCantidadd sea un número válido
                    if (!lblCantidadd.getText().isEmpty() && lblCantidadd.getText().matches("\\d+")) {
                        // Obtener la cantidad del campo lblCantidadd
                        int cantidad = Integer.parseInt(lblCantidadd.getText());
                        // Calcular el total multiplicando la cantidad por el precio
                        float total = cantidad * precio;
                        // Actualizar el texto del campo lblTotall con el resultado de la multiplicación
                        lblTotall.setText("$" + total);
                    }
                }
            });
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
            ImageView imvEmp = new ImageView(getClass().getResource("/images/mas.png").toString());
            btnAgregarEmplado = new Button();
            imvEmp.setFitHeight(50);
            imvEmp.setFitWidth(50);
            btnAgregarEmplado = new Button();
            btnAgregarEmplado.setOnAction(event -> new OrdenDetalleForm(tbvEmpleados, null));
            btnAgregarEmplado.setPrefSize(30,30);
            btnAgregarEmplado.setGraphic(
                    imvEmp);
            tlbMenu = new ToolBar(btnAgregarEmplado);
            CrearTable();
            bnpPrincipal = new BorderPane();
            bnpPrincipal.setTop(tlbMenu);
            bnpPrincipal.setCenter(tbvEmpleados);
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
            VBox opciones = new VBox(10);
            opciones.getStyleClass().add("Centro");
    
            opciones.getChildren().addAll(
                    lblProductos,hboxBotonesComida,hboxBotonesRegistro,lblOrdenes,bnpPrincipal
            );
    
            GridPane mesaGrid = new GridPane();
            mesaGrid.setHgap(10); // Espacio horizontal entre las celdas
            mesaGrid.setVgap(10); // Espacio vertical entre las celdas

// Dentro del constructor Taqueria()

            for (int i = 0; i < 12; i++) {
                Button mesaButton = createStyledButton("Mesa " + (i + 1), "boton-mesa");
                final int mesaNumero = i + 1; // Almacenar el número de la mesa para usarlo en el evento de clic
                mesaButton.setOnAction(event -> {
                    // Llamar al método handleMesaButtonClick() pasando el número de mesa
                    handleMesaButtonClick(mesaNumero);
                });
                mesaGrid.add(mesaButton, i % 4, i / 4); // Colocar el botón en la cuadrícula
            }

    
            Button btncobrar = createSymbolButton("COBRAR");
            btncobrar.getStyleClass().add("cobrar");
    
            Button btnGuardar = createSymbolButton("✔");
            btnGuardar.getStyleClass().add("guardar");
    
            Button btnBorrar = createSymbolButton("X");
            btnBorrar.getStyleClass().add("borrar");
    
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
    
            HBox botones = new HBox(10);
            botones.getChildren().addAll(
                    btncobrar,btnGuardar,btnBorrar
            );
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
    
            VBox crud = new VBox(10);
            crud.getStyleClass().add("crud");
    
            crud.getChildren().addAll(
                    mesaGrid,botones
            );
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
    
            HBox Centro = new HBox(10);
            Centro.getStyleClass().add("Centro");
    
            Centro.getChildren().addAll(
                    vboxBotones,crud,opciones
            );
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
    
            hboxCategorias.setAlignment(Pos.CENTER);
            hboxCategorias.getStyleClass().add("cat");
            String[] categorias = {
                    "Tacos", "Quesadillas", "Burritos", "Tortas",
                    "Tostadas", "Aperitivos y guarniciones", "Bebidas"
            };
    
            for (String categoria : categorias) {
                Button btnCategoria = createStyledButton(categoria, "botones-grupo2");
                btnCategoria.setMinWidth(120);
                hboxCategorias.getChildren().add(btnCategoria);
            }
    
    
            /*-------------------------------------------------------------------------------------------------------------------------------------*/
    
    
            pnlPrincipal.getChildren().addAll(
                    lblCategorias, hboxCategorias, Centro
            );
    
            // Configurar la escena y establecerla en esta misma instancia de Stage
            Scene scene = new Scene(pnlPrincipal, 1920, 1000);
            // Cargar el archivo CSS para aplicar los estilos
            scene.getStylesheets().add(getClass().getResource("/estilos/styles.css").toExternalForm());
            setupCategoriaButtonEvents();
    
            setScene(scene);
            show();
        }
    
        // Método para crear un botón con un estilo CSS específico
        private Button createStyledButton(String text, String styleClass) {
            Button button = new Button(text);
            button.getStyleClass().add(styleClass);
            return button;
        }
    
        // Método para crear un botón con un símbolo específico (como "+", "-")
        private Button createSymbolButton(String symbol) {
            Button button = new Button(symbol);
            button.setMinWidth(40); // Ancho mínimo para el botón
            button.getStyleClass().add("boton-simbolo"); // Agregar estilo de símbolo
            return button;
        }
    
        private void CrearUI() {
            ImageView imvEmp = new ImageView(getClass().getResource("/images/human.png").toString());
            btnAgregarEmplado = new Button();
            imvEmp.setFitHeight(50);
            imvEmp.setFitWidth(50);
            btnAgregarEmplado = new Button();
            btnAgregarEmplado.setOnAction(event -> new OrdenDetalleForm(tbvEmpleados, null));
            btnAgregarEmplado.setPrefSize(30,30);
            btnAgregarEmplado.setGraphic(
                    imvEmp);
            tlbMenu = new ToolBar(btnAgregarEmplado);
            CrearTable();
            bnpPrincipal = new BorderPane();
            bnpPrincipal.setTop(tlbMenu);
            bnpPrincipal.setCenter(tbvEmpleados);
            pnlPrincipal = new Panel("Taqueria");
            pnlPrincipal.getStyleClass().add("panel-info");
            pnlPrincipal.setBody(bnpPrincipal);
            escena = new Scene(pnlPrincipal,700,500);
            escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        }
    
        private void CrearTable(){
            OrdenDetalleDao objEmp = new OrdenDetalleDao();
            tbvEmpleados = new TableView<OrdenDetalleDao>();
    
            TableColumn<OrdenDetalleDao,String> tbcMesa = new TableColumn<>("ID Mesa");
            tbcMesa.setCellValueFactory(new PropertyValueFactory<>("id_mesa"));
            TableColumn<OrdenDetalleDao,Float> tbcProducto = new TableColumn<>("ID Producto");
            tbcProducto.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
            TableColumn<OrdenDetalleDao,String> tbcCant = new TableColumn<>("Cantidad");
            tbcCant.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            TableColumn<OrdenDetalleDao,String> tbcTotal = new TableColumn<>("Total");
            tbcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    
            TableColumn<OrdenDetalleDao,String> tbcEditar= new TableColumn<OrdenDetalleDao,String>("Editar");
            tbcEditar.setCellFactory(
                    new Callback<TableColumn<OrdenDetalleDao, String>, TableCell<OrdenDetalleDao, String>>() {
                        @Override
                        public TableCell<OrdenDetalleDao, String> call(TableColumn<OrdenDetalleDao, String> empleadosDAOStringTableColumn) {
                            return new ButtonCellOrdenDetalle(1);
                        }
                    }
            );
            TableColumn<OrdenDetalleDao,String> tbcEliminar= new TableColumn<OrdenDetalleDao,String>("Eliminar");
            tbcEliminar.setCellFactory(
                    new Callback<TableColumn<OrdenDetalleDao, String>, TableCell<OrdenDetalleDao, String>>() {
                        @Override
                        public TableCell<OrdenDetalleDao, String> call(TableColumn<OrdenDetalleDao, String> empleadosDAOStringTableColumn) {
                            return new ButtonCellOrdenDetalle(2);
                        }
                    }
            );
    
            tbvEmpleados.getColumns().addAll(tbcMesa,tbcProducto,tbcCant,tbcTotal,tbcEditar,tbcEliminar);
            tbvEmpleados.setItems(objEmp.CONSULTAR());
        }
    
        private void setupCategoriaImagenes() {
            // Definir imágenes asociadas a cada categoría
            categoriaImagenes.put("Tacos", List.of("visteck.jpg", "campechanos.jpg", "res.jpg", "cabeza.jpg", "pastor.jpg"));
            categoriaImagenes.put("Quesadillas", List.of("qbistec.jpeg", "qcampechano.jpg", "qres.jpeg", "qcabeza.jpg", "qpastor.jpeg"));
            categoriaImagenes.put("Burritos", List.of("bvisteck.jpg", "bcampechanos.jpeg", "bres.jpg", "bcabeza.jpg", "bpastor.jpeg"));
            categoriaImagenes.put("Tortas", List.of("tvisteck.jpg", "tcampechana.jpg", "tres.jpg", "tcabeza.jpg", "tpastor.jpg"));
            categoriaImagenes.put("Tostadas", List.of("aguachile.jpg", "tostada.jpeg", "tartara.jpeg", "totopos.jpg", "flautas.jpeg"));
            categoriaImagenes.put("Aperitivos y guarniciones", List.of("verde.jpeg", "roja.jpg", "totopos.jpg", "cebolla.jpg", "guacamole.jpg"));
            categoriaImagenes.put("Bebidas", List.of("pepsi.png", "coca.png", "fanta.png", "delawer.png", "sprite.png"));
            // Agregar otras categorías con sus imágenes asociadas
        }
    
    
        private void setupCategoriaButtonEvents() {
            // Obtener los botones de categoría y añadir eventos de clic
            for (String categoria : categoriaImagenes.keySet()) {
                for (Node node : hboxCategorias.getChildren()) {
                    if (node instanceof Button && ((Button) node).getText().equals(categoria)) {
                        ((Button) node).setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                categoriaSeleccionada = categoria;
                                comidaSeleccionada = null; // Reiniciar comida seleccionada al cambiar de categoría
                                actualizarBotonesComida(categoria);
                            }
                        });
                    }
                }
            }
    
            // Obtener los botones de comida y añadir eventos de clic
            ObservableList<Node> botonesComida = hboxBotonesComida.getChildren();
            for (Node node : botonesComida) {
                if (node instanceof Button) {
                    ((Button) node).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // Obtener el ID del producto asociado al botón de comida
                            int idProducto = Integer.parseInt(((Button) node).getId());
                            // Llamar a un método para consultar la información del producto en la base de datos
                            consultarProducto(idProducto);
                        }
                    });
                }
            }
        }
    
        private void actualizarBotonesComida(String categoriaSeleccionada) {
            // Obtener la lista de imágenes asociadas a la categoría seleccionada
            List<String> imagenes = categoriaImagenes.get(categoriaSeleccionada);
    
            // Tamaño deseado para las imágenes dentro de los botones
            double imagenWidth = 100;
            double imagenHeight = 100;
    
            // Obtener el ID de la categoría seleccionada
            int idCategoria = getIdCategoria(categoriaSeleccionada);
            if (idCategoria != -1) {
                System.out.println("Categoría: " + categoriaSeleccionada + ", ID: " + idCategoria);
            } else {
                System.out.println("No se pudo encontrar el ID de la categoría.");
            }
    
            // Actualizar los botones de comida con las nuevas imágenes y asignar el id_producto como identificador
            ObservableList<Node> botonesComida = hboxBotonesComida.getChildren();
            for (int i = 0; i < Math.min(botonesComida.size(), imagenes.size()); i++) {
                Button btnComida = (Button) botonesComida.get(i);
                if (i < imagenes.size()) {
                    // Obtener la imagen correspondiente
                    String imagenPath = imagenes.get(i);
                    Image imagen = new Image(getClass().getResource("/images/" + imagenPath).toExternalForm());
    
                    // Crear un ImageView con la imagen y establecer tamaño deseado
                    ImageView imageView = new ImageView(imagen);
                    imageView.setFitWidth(imagenWidth);
                    imageView.setFitHeight(imagenHeight);
    
                    // Establecer el ImageView como gráfico del botón y eliminar el texto del botón
                    btnComida.setGraphic(imageView);
                    btnComida.setText(""); // Eliminar el texto del botón
    
                    // Asignar el id_producto como identificador del botón
                    int idProducto = obtenerIdProducto(categoriaSeleccionada, i); // Implementa este método
                    btnComida.setId(String.valueOf(idProducto));
    
                    // Imprimir la información en la consola
                    System.out.println("Producto: " + btnComida.getText() + ", ID: " + idProducto);
                } else {
                    // Si no hay imagen correspondiente, eliminar cualquier imagen existente del botón
                    btnComida.setGraphic(null);
                    btnComida.setText(""); // Eliminar el texto del botón
                    btnComida.setId(""); // Limpiar el identificador del botón
                }
    
                // Establecer tamaño fijo para el botón (opcional)
                btnComida.setMinWidth(imagenWidth + 10); // Ajusta el ancho del botón según el tamaño de la imagen
                btnComida.setMinHeight(imagenHeight + 10); // Ajusta el alto del botón según el tamaño de la imagen
            }
        }
    
        private void consultarProducto(int idProducto) {
            // Obtener la información del producto basado en el idProducto
            String query = "SELECT nombre, precio FROM productos WHERE id_producto = ?";
            try {
                PreparedStatement preparedStatement = Conexion.connection.prepareStatement(query);
                preparedStatement.setInt(1, idProducto);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // Obtener los valores de las columnas
                    String nombre = resultSet.getString("nombre");
                    int precio = resultSet.getInt("precio");
                    // Imprimir información de depuración
                    System.out.println("Nombre: " + nombre + ", Precio: " + precio);
                    // Actualizar los campos de texto
                    lblArticuloo.setText(nombre);
                    lblPrecioo.setText("$"+String.valueOf(precio));
                } else {
                    // No se encontraron resultados para el idProducto
                    tfArticulo.setText(""); // Campo vacío si no se encontraron resultados
                    tfPrecio.setText(""); // Campo vacío si no se encontraron resultados
                    System.out.println("No se encontraron productos para el idProducto proporcionado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        private int getIdCategoria(String nombreCategoria) {
            // Realiza una consulta a la base de datos para obtener el ID de la categoría en función de su nombre
            int idCategoria = -1; // Valor predeterminado en caso de no encontrar la categoría
            try {
                String query = "SELECT id_categoria FROM categoria WHERE nombre = ?";
                PreparedStatement preparedStatement = Conexion.connection.prepareStatement(query);
                preparedStatement.setString(1, nombreCategoria);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    idCategoria = resultSet.getInt("id_categoria");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return idCategoria;
        }
    
        private int obtenerIdProducto(String categoriaSeleccionada, int indice) {
            // Esta es una implementación de ejemplo para asignar un ID de producto único
            // Puedes personalizar este método según la lógica de tu base de datos o estructura de datos
            int idBase = 3; // Número base para empezar a asignar IDs
            int idProducto = 0;
    
            // Asignar un ID de producto único basado en la categoría y el índice
            switch (categoriaSeleccionada) {
                case "Tacos":
                    idProducto = idBase + indice;
                    break;
                case "Quesadillas":
                    idProducto = idBase + 5 + indice;
                    break;
                case "Burritos":
                    idProducto = idBase + 10 + indice;
                    break;
                case "Tortas":
                    idProducto = idBase + 15 + indice;
                    break;
                case "Tostadas":
                    idProducto = idBase + 20 + indice;
                    break;
                case "Aperitivos y guarniciones":
                    idProducto = idBase + 25 + indice;
                    break;
                case "Bebidas":
                    idProducto = idBase + 30 + indice;
                    break;
                // Agregar más casos según sea necesario para otras categorías
                default:
                    idProducto = -1; // Valor predeterminado si la categoría no está definida
                    break;
            }
            return idProducto;
        }

        private int generarNuevoIdOrden() {
            return proximoIdOrden++;
        }

        // Método para manejar el evento de clic en el botón de mesa
        private void handleMesaButtonClick(int mesaNumero) {
            // Verificar si ya hay una orden activa para esta mesa
            if (!mesaOrdenMap.containsKey(mesaNumero)) {
                // Si no hay una orden activa, generar un nuevo idOrden
                int idOrden = generarNuevoIdOrden();
                // Asociar este idOrden con la mesa y guardar la relación en el mapa
                mesaOrdenMap.put(mesaNumero, idOrden);
                // Imprimir información de depuración
                System.out.println("Se generó un nuevo idOrden " + idOrden + " para la mesa " + mesaNumero);
            } else {
                // Si ya hay una orden activa, obtener el idOrden asociado
                int idOrden = mesaOrdenMap.get(mesaNumero);
                // Imprimir información de depuración
                System.out.println("La mesa " + mesaNumero + " ya tiene una orden activa con idOrden " + idOrden);
            }
        }


        public static void main(String[] args) {
            launch(args);
        }

    }
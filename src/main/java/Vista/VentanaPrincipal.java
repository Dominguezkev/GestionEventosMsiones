package Vista;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class VentanaPrincipal extends Application {

    @Override
    public void start(Stage primaryStage) {

        // 1. Contenedor Principal
        BorderPane layoutPrincipal = new BorderPane();

        // 2. Crear el Menú Lateral (Izquierda)
        VBox menuLateral = new VBox(15);
        menuLateral.setPadding(new Insets(20));
        menuLateral.setStyle("-fx-background-color: #2c3e50;");

        Label tituloMenu = new Label("Menú Principal");
        tituloMenu.setTextFill(javafx.scene.paint.Color.WHITE);
        tituloMenu.setFont(new Font("Arial", 18));

        Button btnEventos = new Button("Gestión de Eventos");
        Button btnPersonas = new Button("Gestión de Personas");
        Button btnSalir = new Button("Salir del Sistema");

        btnEventos.setMaxWidth(Double.MAX_VALUE);
        btnPersonas.setMaxWidth(Double.MAX_VALUE);
        btnSalir.setMaxWidth(Double.MAX_VALUE);

        // Al hacer clic en Personas, muestra su formulario
        btnPersonas.setOnAction(e -> layoutPrincipal.setCenter(crearFormularioPersonas()));

        // ¡AGREGÁ ESTA LÍNEA! Al hacer clic en Eventos, muestra este nuevo formulario
        btnEventos.setOnAction(e -> layoutPrincipal.setCenter(crearFormularioEventos()));

        // Agregamos todo al menú lateral
        menuLateral.getChildren().addAll(tituloMenu, btnEventos, btnPersonas, btnSalir);

        // 3. Crear el Área de Trabajo (Centro)
        StackPane areaTrabajo = new StackPane();
        Label mensajeBienvenida = new Label("Seleccione una opción del menú lateral para comenzar.");
        mensajeBienvenida.setFont(new Font("Arial", 16));
        areaTrabajo.getChildren().add(mensajeBienvenida);

        // 4. Ensamblar el BorderPane
        layoutPrincipal.setLeft(menuLateral);
        layoutPrincipal.setCenter(areaTrabajo);

        // 5. Configurar la Escena y mostrar
        Scene escena = new Scene(layoutPrincipal, 800, 600);
        primaryStage.setTitle("Gestión de Eventos Culturales - Misiones");
        primaryStage.setScene(escena);
        primaryStage.show();

        // Acción básica para el botón salir
        btnSalir.setOnAction(e -> primaryStage.close());

    } // <--- ¡AQUÍ TERMINA EL MÉTODO START!

    // --- AQUÍ EMPIEZA EL NUEVO MÉTODO ---
    private GridPane crearFormularioPersonas() {
        GridPane formulario = new GridPane();
        formulario.setPadding(new Insets(40));
        formulario.setVgap(15);
        formulario.setHgap(10);
        formulario.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        Label lblTitulo = new Label("Registrar Nueva Persona");
        lblTitulo.setFont(new Font("Arial", 22));
        lblTitulo.setStyle("-fx-font-weight: bold;");
        formulario.add(lblTitulo, 0, 0, 2, 1);

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ej: Kevin Dominguez");

        TextField txtDni = new TextField();
        txtDni.setPromptText("Sin puntos ni espacios");

        TextField txtTelefono = new TextField();
        TextField txtCorreo = new TextField();

        formulario.add(new Label("Nombre Completo:"), 0, 1);
        formulario.add(txtNombre, 1, 1);

        formulario.add(new Label("DNI:"), 0, 2);
        formulario.add(txtDni, 1, 2);

        formulario.add(new Label("Teléfono:"), 0, 3);
        formulario.add(txtTelefono, 1, 3);

        formulario.add(new Label("Correo Electrónico:"), 0, 4);
        formulario.add(txtCorreo, 1, 4);

        Button btnGuardar = new Button("Guardar Persona");
        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        formulario.add(btnGuardar, 1, 5);

        // EVENTO: ¿Qué pasa al hacer clic en Guardar?
        btnGuardar.setOnAction(e -> {
            // 1. Capturamos los datos de las cajitas de texto
            String nombre = txtNombre.getText();
            String dni = txtDni.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();

            // 2. Creamos el objeto de tu Modelo (Asegurate de que importe la clase Persona correcta)
            Modelo.Persona nuevaPersona = new Modelo.Persona(nombre, dni, telefono, correo);

            // 3. Conectamos a la base de datos (Igual que hicimos en el Main)
            jakarta.persistence.EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("EventosPU");
            jakarta.persistence.EntityManager em = emf.createEntityManager();

            try {
                em.getTransaction().begin();
                em.persist(nuevaPersona); // ¡Guardamos el objeto!
                em.getTransaction().commit();

                System.out.println("¡Éxito Total! " + nombre + " se guardó en la base de datos.");

                // 4. Limpiamos los campos para poder cargar otro
                txtNombre.clear();
                txtDni.clear();
                txtTelefono.clear();
                txtCorreo.clear();

            } catch (Exception ex) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                System.err.println("Error al guardar en la base de datos:");
                ex.printStackTrace();
            } finally {
                em.close();
                emf.close();
            }
        });

        return formulario;
    } // <--- AQUÍ TERMINA EL MÉTODO DEL FORMULARIO

    private GridPane crearFormularioEventos() {
        GridPane formulario = new GridPane();
        formulario.setPadding(new Insets(40));
        formulario.setVgap(15);
        formulario.setHgap(10);
        formulario.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        Label lblTitulo = new Label("Registrar Nuevo Evento");
        lblTitulo.setFont(new Font("Arial", 22));
        lblTitulo.setStyle("-fx-font-weight: bold;");
        formulario.add(lblTitulo, 0, 0, 2, 1);

        // --- LA MAGIA PARA LA HERENCIA ---
        // Usamos un ComboBox (lista desplegable) para que el municipio elija qué clase instanciar
        javafx.scene.control.ComboBox<String> cmbTipoEvento = new javafx.scene.control.ComboBox<>();
        cmbTipoEvento.getItems().addAll("Feria", "Concierto", "Exposición", "Taller", "Ciclo de Cine");
        cmbTipoEvento.setPromptText("Seleccione el tipo");

        // --- CAMPOS DE LA CLASE PADRE (Evento) ---
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del evento");

        // DatePicker es un widget genial de JavaFX que te abre un calendardio
        javafx.scene.control.DatePicker dpFecha = new javafx.scene.control.DatePicker();

        TextField txtDuracion = new TextField();
        txtDuracion.setPromptText("Duración en horas");

        // --- AGREGAMOS TODO A LA GRILLA ---
        formulario.add(new Label("Tipo de Evento:"), 0, 1);
        formulario.add(cmbTipoEvento, 1, 1);

        formulario.add(new Label("Nombre:"), 0, 2);
        formulario.add(txtNombre, 1, 2);

        formulario.add(new Label("Fecha de Inicio:"), 0, 3);
        formulario.add(dpFecha, 1, 3);

        formulario.add(new Label("Duración (hs):"), 0, 4);
        formulario.add(txtDuracion, 1, 4);

        // Botón (Por ahora dice Continuar, porque luego mostraremos los campos específicos)
        Button btnContinuar = new Button("Continuar");
        btnContinuar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        formulario.add(btnContinuar, 1, 5);

        // EVENTO DEL BOTÓN
        btnContinuar.setOnAction(e -> {
            String tipoSeleccionado = cmbTipoEvento.getValue();
            System.out.println("El usuario quiere crear un/a: " + tipoSeleccionado);
            System.out.println("Nombre: " + txtNombre.getText());
            // Después del partido o mañana, haremos que este botón muestre los campos de las clases hijas
        });

        return formulario;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
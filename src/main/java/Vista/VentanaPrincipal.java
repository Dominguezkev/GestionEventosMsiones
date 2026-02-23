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

        // ¡AGREGA ESTA LÍNEA! Al hacer clic en Eventos, muestra este nuevo formulario
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

        // --- CAMPOS BASE ---
        javafx.scene.control.ComboBox<String> cmbTipoEvento = new javafx.scene.control.ComboBox<>();
        cmbTipoEvento.getItems().addAll("Feria", "Concierto", "Exposición", "Taller", "Ciclo de Cine");
        cmbTipoEvento.setPromptText("Seleccione el tipo");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del evento");
        javafx.scene.control.DatePicker dpFecha = new javafx.scene.control.DatePicker();
        TextField txtDuracion = new TextField();
        txtDuracion.setPromptText("Duración en horas (Ej: 4)");

        // --- PREPARAMOS LOS CAMPOS ESPECÍFICOS (Invisibles por ahora) ---
        // Feria
        TextField txtStands = new TextField(); txtStands.setPromptText("Cantidad de Stands");
        javafx.scene.control.CheckBox chkAireLibre = new javafx.scene.control.CheckBox("¿Es al aire libre?");
        // Concierto
        TextField txtArtistas = new TextField(); txtArtistas.setPromptText("Artistas (Separados por coma)");
        javafx.scene.control.CheckBox chkGratis = new javafx.scene.control.CheckBox("¿Es gratuito?");
        // Exposición
        TextField txtArte = new TextField(); txtArte.setPromptText("Tipo de Arte");
        TextField txtCurador = new TextField(); txtCurador.setPromptText("Curador");
        // Taller
        TextField txtCupo = new TextField(); txtCupo.setPromptText("Cupo Máximo");
        TextField txtInstructor = new TextField(); txtInstructor.setPromptText("Instructor");
        // Ciclo de Cine
        javafx.scene.control.CheckBox chkCharla = new javafx.scene.control.CheckBox("¿Incluye charla posterior?");

        formulario.add(new Label("Tipo de Evento:"), 0, 1);
        formulario.add(cmbTipoEvento, 1, 1);
        formulario.add(new Label("Nombre:"), 0, 2);
        formulario.add(txtNombre, 1, 2);
        formulario.add(new Label("Fecha de Inicio:"), 0, 3);
        formulario.add(dpFecha, 1, 3);
        formulario.add(new Label("Duración (hs):"), 0, 4);
        formulario.add(txtDuracion, 1, 4);

        VBox panelDinamico = new VBox(10);
        formulario.add(panelDinamico, 0, 5, 2, 1);

        // --- LÓGICA DE CAMBIO EN VIVO ---
        cmbTipoEvento.setOnAction(e -> {
            panelDinamico.getChildren().clear();
            String tipo = cmbTipoEvento.getValue();

            if ("Feria".equals(tipo)) panelDinamico.getChildren().addAll(new Label("Campos de Feria:"), txtStands, chkAireLibre);
            else if ("Concierto".equals(tipo)) panelDinamico.getChildren().addAll(new Label("Campos de Concierto:"), txtArtistas, chkGratis);
            else if ("Exposición".equals(tipo)) panelDinamico.getChildren().addAll(new Label("Campos de Exposición:"), txtArte, txtCurador);
            else if ("Taller".equals(tipo)) panelDinamico.getChildren().addAll(new Label("Campos de Taller:"), txtCupo, txtInstructor);
            else if ("Ciclo de Cine".equals(tipo)) panelDinamico.getChildren().addAll(new Label("Campos de Ciclo de Cine:"), chkCharla);
        });

        // --- BOTÓN GUARDAR Y CONEXIÓN A BASE DE DATOS ---
        Button btnGuardar = new Button("Guardar Evento");
        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        formulario.add(btnGuardar, 1, 6);

        btnGuardar.setOnAction(e -> {
            // Validaciones básicas
            if (cmbTipoEvento.getValue() == null || txtNombre.getText().isEmpty() || dpFecha.getValue() == null || txtDuracion.getText().isEmpty()) {
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alerta.setContentText("Complete los campos base antes de continuar.");
                alerta.showAndWait();
                return;
            }

            try {
                // Capturamos datos del padre
                String tipo = cmbTipoEvento.getValue();
                String nombre = txtNombre.getText();
                java.time.LocalDateTime fecha = dpFecha.getValue().atStartOfDay(); // Convierte la fecha del calendario
                int duracion = Integer.parseInt(txtDuracion.getText());

                // Declaramos la variable padre vacía
                Modelo.Evento nuevoEvento = null;

                // HERENCIA EN ACCIÓN: Instanciamos a la hija correspondiente
                if ("Feria".equals(tipo)) {
                    int stands = Integer.parseInt(txtStands.getText());
                    nuevoEvento = new Modelo.Feria(nombre, fecha, duracion, stands, chkAireLibre.isSelected());
                } else if ("Concierto".equals(tipo)) {
                    // Convertimos el texto separado por comas en una Lista de Strings
                    java.util.List<String> artistas = java.util.Arrays.asList(txtArtistas.getText().split(","));
                    nuevoEvento = new Modelo.Concierto(nombre, fecha, duracion, artistas, chkGratis.isSelected());
                } else if ("Exposición".equals(tipo)) {
                    nuevoEvento = new Modelo.Exposicion(nombre, fecha, duracion, txtArte.getText(), txtCurador.getText());
                } else if ("Taller".equals(tipo)) {
                    int cupo = Integer.parseInt(txtCupo.getText());
                    nuevoEvento = new Modelo.Taller(nombre, fecha, duracion, cupo, txtInstructor.getText(), true); // Asumo true como presencial por ahora
                } else if ("Ciclo de Cine".equals(tipo)) {
                    nuevoEvento = new Modelo.CicloCine(nombre, fecha, duracion, chkCharla.isSelected());
                }

                // --- PERSISTENCIA JPA ---
                jakarta.persistence.EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("EventosPU");
                jakarta.persistence.EntityManager em = emf.createEntityManager();

                em.getTransaction().begin();
                em.persist(nuevoEvento); // JPA sabe en qué tabla guardarlo gracias a la herencia
                em.getTransaction().commit();

                // Mensaje de Éxito
                javafx.scene.control.Alert alertaExito = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alertaExito.setContentText("¡El evento " + nombre + " se guardó en la base de datos exitosamente!");
                alertaExito.showAndWait();

                em.close();
                emf.close();

                // Limpiamos los campos (Opcional, para que quede prolijo)
                txtNombre.clear(); txtDuracion.clear(); txtStands.clear(); txtArtistas.clear();

            } catch (NumberFormatException ex) {
                // Por si el usuario escribe letras en donde van números
                javafx.scene.control.Alert alertaNum = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
                alertaNum.setContentText("Cuidado: En duración, stands o cupos debes ingresar solo números.");
                alertaNum.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace(); // Imprime el error real en la consola de IntelliJ
            }
        });

        return formulario;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
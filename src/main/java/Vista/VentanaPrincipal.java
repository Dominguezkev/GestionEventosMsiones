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

        Button btnEventos = new Button("Registrar Evento");
        Button btnListadoEventos = new Button("Ver Listado de Eventos"); // ¡Nuevo botón!
        Button btnPersonas = new Button("Registrar Persona");
        Button btnAsignar = new Button("Asignar Organizadores");
        Button btnEstado = new Button("Cambiar Estado");
        Button btnInscripcion = new Button("Inscribir Participante");
        Button btnSalir = new Button("Salir del Sistema");


        btnEventos.setMaxWidth(Double.MAX_VALUE);
        btnListadoEventos.setMaxWidth(Double.MAX_VALUE); // Expandir el botón
        btnPersonas.setMaxWidth(Double.MAX_VALUE);
        btnAsignar.setMaxWidth(Double.MAX_VALUE);
        btnEstado.setMaxWidth(Double.MAX_VALUE);
        btnInscripcion.setMaxWidth(Double.MAX_VALUE);
        btnSalir.setMaxWidth(Double.MAX_VALUE);


        // Conectamos los clics
        btnEventos.setOnAction(e -> layoutPrincipal.setCenter(crearFormularioEventos()));
        btnListadoEventos.setOnAction(e -> layoutPrincipal.setCenter(crearPanelListadoEventos())); // ¡Conectamos la tabla!
        btnPersonas.setOnAction(e -> layoutPrincipal.setCenter(crearFormularioPersonas()));
        btnAsignar.setOnAction(e -> layoutPrincipal.setCenter(crearPanelAsignacion()));
        btnEstado.setOnAction(e -> layoutPrincipal.setCenter(crearPanelCambioEstado()));
        btnInscripcion.setOnAction(e -> layoutPrincipal.setCenter(crearPanelInscripcion()));
        btnSalir.setOnAction(e -> primaryStage.close());


        // Acordate de agregarlo al VBox del menú lateral:
        menuLateral.getChildren().addAll(tituloMenu, btnEventos, btnListadoEventos, btnPersonas, btnAsignar, btnEstado, btnInscripcion, btnSalir);

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
        txtNombre.setPromptText("Ej: Juan Perez");

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

    // Método para crear la pantalla con la tabla de eventos
    private VBox crearPanelListadoEventos() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(40));
        panel.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        Label lblTitulo = new Label("Listado de Eventos Registrados");
        lblTitulo.setFont(new Font("Arial", 22));
        lblTitulo.setStyle("-fx-font-weight: bold;");

        // 1. Crear la Tabla
        javafx.scene.control.TableView<Modelo.Evento> tabla = new javafx.scene.control.TableView<>();

        // 2. Crear las Columnas (El texto entre comillas del PropertyValueFactory DEBE coincidir con el nombre exacto de tus variables en la clase Evento)
        javafx.scene.control.TableColumn<Modelo.Evento, Long> colId = new javafx.scene.control.TableColumn<>("ID");
        colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        javafx.scene.control.TableColumn<Modelo.Evento, String> colNombre = new javafx.scene.control.TableColumn<>("Nombre del Evento");
        colNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));

        javafx.scene.control.TableColumn<Modelo.Evento, Integer> colDuracion = new javafx.scene.control.TableColumn<>("Duración (hs)");
        colDuracion.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("duracionHoras"));

        // --- LA NUEVA COLUMNA DE ORGANIZADORES ---
        javafx.scene.control.TableColumn<Modelo.Evento, String> colOrganizadores = new javafx.scene.control.TableColumn<>("Organizadores");
        colOrganizadores.setPrefWidth(200);
        colOrganizadores.setCellValueFactory(cellData -> {
            // Obtenemos la lista de organizadores de ese evento en particular
            java.util.List<Modelo.Persona> orgs = cellData.getValue().getOrganizadores();

            // Si no tiene a nadie asignado, mostramos un mensaje
            if (orgs == null || orgs.isEmpty()) {
                return new javafx.beans.property.SimpleStringProperty("Sin asignar");
            }

            // Si tiene, extraemos los nombres y los unimos con una coma
            StringBuilder nombres = new StringBuilder();
            for (int i = 0; i < orgs.size(); i++) {
                // ATENCIÓN: Si tu getter se llama distinto, cambialo acá (ej: getNombreCompleto())
                nombres.append(orgs.get(i).getNombreCompleto());
                if (i < orgs.size() - 1) {
                    nombres.append(", ");
                }
            }
            return new javafx.beans.property.SimpleStringProperty(nombres.toString());
        });

        // Hacemos que las columnas ocupen un buen ancho y la tabla se expanda
        colNombre.setPrefWidth(250);
        javafx.scene.layout.VBox.setVgrow(tabla, javafx.scene.layout.Priority.ALWAYS);

        // 3. Agregamos las columnas a la tabla
        tabla.getColumns().addAll(colId, colNombre, colDuracion, colOrganizadores);

        // 4. ¡LA CONEXIÓN A LA BASE DE DATOS! (Traemos la lista con JPA)
        jakarta.persistence.EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("EventosPU");
        jakarta.persistence.EntityManager em = emf.createEntityManager();

        try {
            // JPQL Mejorado: Trae los eventos y "pre-carga" la lista de organizadores en el mismo viaje
            java.util.List<Modelo.Evento> listaEventosBD = em.createQuery(
                    "SELECT DISTINCT e FROM Evento e LEFT JOIN FETCH e.organizadores", Modelo.Evento.class
            ).getResultList();

            // JavaFX necesita un tipo de lista especial llamada ObservableList
            javafx.collections.ObservableList<Modelo.Evento> datosTabla = javafx.collections.FXCollections.observableArrayList(listaEventosBD);

            // Inyectamos los datos en la tabla
            tabla.setItems(datosTabla);

        } catch (Exception ex) {
            System.err.println("Error al cargar los eventos desde H2: ");
            ex.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

        panel.getChildren().addAll(lblTitulo, tabla);

        return panel;
    }

    // Método para crear el panel de vinculación
    private VBox crearPanelAsignacion() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(40));
        panel.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        Label lblTitulo = new Label("Asignar Organizador a Evento");
        lblTitulo.setFont(new Font("Arial", 22));
        lblTitulo.setStyle("-fx-font-weight: bold;");

        // 1. Creamos las listas desplegables
        javafx.scene.control.ComboBox<String> cmbEventos = new javafx.scene.control.ComboBox<>();
        cmbEventos.setPromptText("Seleccione un Evento");
        cmbEventos.setPrefWidth(300);

        javafx.scene.control.ComboBox<String> cmbPersonas = new javafx.scene.control.ComboBox<>();
        cmbPersonas.setPromptText("Seleccione una Persona");
        cmbPersonas.setPrefWidth(300);

        // 2. Cargamos los datos desde la Base de Datos
        jakarta.persistence.EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("EventosPU");
        jakarta.persistence.EntityManager em = emf.createEntityManager();

        try {
            // Traemos eventos y llenamos la lista con formato "ID - Nombre"
            java.util.List<Modelo.Evento> eventos = em.createQuery("SELECT e FROM Evento e", Modelo.Evento.class).getResultList();
            for (Modelo.Evento e : eventos) {
                cmbEventos.getItems().add(e.getId() + " - " + e.getNombre());
            }

            // Traemos personas y llenamos la otra lista
            java.util.List<Modelo.Persona> personas = em.createQuery("SELECT p FROM Persona p", Modelo.Persona.class).getResultList();
            for (Modelo.Persona p : personas) {
                cmbPersonas.getItems().add(p.getId() + " - " + p.getNombreCompleto());
                // Nota: Si tu variable en Persona se llama 'nombre' a secas, cambiá getNombreCompleto() por getNombre()
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }

        // 3. Botón para confirmar la asignación
        Button btnAsignar = new Button("Vincular Organizador");
        btnAsignar.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-font-weight: bold;");

        btnAsignar.setOnAction(e -> {
            if (cmbEventos.getValue() == null || cmbPersonas.getValue() == null) {
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alerta.setContentText("Por favor seleccione un Evento y una Persona.");
                alerta.showAndWait();
                return;
            }

            // Extraemos solo el ID (el número antes del guion) para buscarlo en la BD
            Long idEvento = Long.parseLong(cmbEventos.getValue().split(" - ")[0]);
            Long idPersona = Long.parseLong(cmbPersonas.getValue().split(" - ")[0]);

            jakarta.persistence.EntityManager emUpdate = emf.createEntityManager();
            try {
                emUpdate.getTransaction().begin();

                // Buscamos los objetos reales en la BD usando sus IDs
                Modelo.Evento evento = emUpdate.find(Modelo.Evento.class, idEvento);
                Modelo.Persona persona = emUpdate.find(Modelo.Persona.class, idPersona);

                // ¡LA MAGIA RELACIONAL! Añadimos la persona a la lista del evento
                evento.getOrganizadores().add(persona);
                emUpdate.merge(evento); // Merge actualiza el evento en la BD

                emUpdate.getTransaction().commit();

                javafx.scene.control.Alert exito = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                exito.setContentText("¡" + persona.getNombreCompleto() + " fue asignado/a exitosamente como organizador del evento!");
                exito.showAndWait();

                // Limpiamos la selección
                cmbEventos.getSelectionModel().clearSelection();
                cmbPersonas.getSelectionModel().clearSelection();

            } catch(Exception ex) {
                System.err.println("Error al vincular en la BD:");
                ex.printStackTrace();
            } finally {
                emUpdate.close();
            }
        });

        panel.getChildren().addAll(lblTitulo, new Label("Evento:"), cmbEventos, new Label("Persona:"), cmbPersonas, btnAsignar);
        return panel;
    }


    // Método para cambiar el estado de los eventos
    private VBox crearPanelCambioEstado() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(40));
        panel.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        Label lblTitulo = new Label("Actualizar Estado del Evento");
        lblTitulo.setFont(new Font("Arial", 22));
        lblTitulo.setStyle("-fx-font-weight: bold;");

        // 1. ComboBox de Eventos
        javafx.scene.control.ComboBox<String> cmbEventos = new javafx.scene.control.ComboBox<>();
        cmbEventos.setPromptText("Seleccione un Evento");
        cmbEventos.setPrefWidth(350);

        // 2. ComboBox de Estados (Usa tu enum 'Estado')
        javafx.scene.control.ComboBox<Modelo.Estado> cmbEstados = new javafx.scene.control.ComboBox<>();
        cmbEstados.getItems().addAll(Modelo.Estado.values());
        cmbEstados.setPromptText("Seleccione el nuevo estado");
        cmbEstados.setPrefWidth(350);

        // 3. Traer los eventos de la BD
        jakarta.persistence.EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("EventosPU");
        jakarta.persistence.EntityManager em = emf.createEntityManager();

        try {
            java.util.List<Modelo.Evento> eventos = em.createQuery("SELECT e FROM Evento e", Modelo.Evento.class).getResultList();
            for (Modelo.Evento e : eventos) {
                // Mostramos ID, Nombre y el Estado Actual
                cmbEventos.getItems().add(e.getId() + " - " + e.getNombre() + " (" + e.getEstado() + ")");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }

        // 4. Botón de Actualización
        Button btnActualizar = new Button("Guardar Nuevo Estado");
        btnActualizar.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");

        btnActualizar.setOnAction(e -> {
            if (cmbEventos.getValue() == null || cmbEstados.getValue() == null) {
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alerta.setContentText("Por favor, seleccione un evento y un estado.");
                alerta.showAndWait();
                return;
            }

            // Extraemos el ID del ComboBox
            Long idEvento = Long.parseLong(cmbEventos.getValue().split(" - ")[0]);
            Modelo.Estado nuevoEstado = cmbEstados.getValue();

            jakarta.persistence.EntityManager emUpdate = emf.createEntityManager();
            try {
                emUpdate.getTransaction().begin();
                Modelo.Evento evento = emUpdate.find(Modelo.Evento.class, idEvento);

                // Usamos el setter que tenés en tu clase Evento
                evento.setEstado(nuevoEstado);

                emUpdate.merge(evento); // Actualizamos en la BD
                emUpdate.getTransaction().commit();

                javafx.scene.control.Alert exito = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                exito.setContentText("El estado del evento '" + evento.getNombre() + "' se actualizó a: " + nuevoEstado);
                exito.showAndWait();

                cmbEventos.getSelectionModel().clearSelection();
                cmbEstados.getSelectionModel().clearSelection();

            } catch(Exception ex) {
                System.err.println("Error al actualizar el estado:");
                ex.printStackTrace();
            } finally {
                emUpdate.close();
            }
        });

        panel.getChildren().addAll(lblTitulo, new Label("Evento a modificar:"), cmbEventos, new Label("Nuevo Estado:"), cmbEstados, btnActualizar);
        return panel;
    }

    // Método para Inscribir Participantes
    private VBox crearPanelInscripcion() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(40));
        panel.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        Label lblTitulo = new Label("Inscribir Participante a Evento");
        lblTitulo.setFont(new Font("Arial", 22));
        lblTitulo.setStyle("-fx-font-weight: bold;");

        // 1. Listas desplegables
        javafx.scene.control.ComboBox<String> cmbEventos = new javafx.scene.control.ComboBox<>();
        cmbEventos.setPromptText("Seleccione un Evento");
        cmbEventos.setPrefWidth(350);

        javafx.scene.control.ComboBox<String> cmbPersonas = new javafx.scene.control.ComboBox<>();
        cmbPersonas.setPromptText("Seleccione una Persona (Participante)");
        cmbPersonas.setPrefWidth(350);

        // 2. Traer datos de la BD
        jakarta.persistence.EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("EventosPU");
        jakarta.persistence.EntityManager em = emf.createEntityManager();

        try {
            java.util.List<Modelo.Evento> eventos = em.createQuery("SELECT e FROM Evento e", Modelo.Evento.class).getResultList();
            for (Modelo.Evento e : eventos) {
                cmbEventos.getItems().add(e.getId() + " - " + e.getNombre() + " (" + e.getEstado() + ")");
            }

            java.util.List<Modelo.Persona> personas = em.createQuery("SELECT p FROM Persona p", Modelo.Persona.class).getResultList();
            for (Modelo.Persona p : personas) {
                // Asumo que tenés p.getNombre() o p.getNombreCompleto()
                cmbPersonas.getItems().add(p.getId() + " - " + p.getNombreCompleto());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }

        // 3. Botón de Inscripción
        Button btnInscribir = new Button("Inscribir Participante");
        btnInscribir.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold;");

        btnInscribir.setOnAction(e -> {
            if (cmbEventos.getValue() == null || cmbPersonas.getValue() == null) {
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alerta.setContentText("Seleccione un evento y una persona.");
                alerta.showAndWait();
                return;
            }

            Long idEvento = Long.parseLong(cmbEventos.getValue().split(" - ")[0]);
            Long idPersona = Long.parseLong(cmbPersonas.getValue().split(" - ")[0]);

            jakarta.persistence.EntityManager emUpdate = emf.createEntityManager();
            try {
                emUpdate.getTransaction().begin();

                Modelo.Evento evento = emUpdate.find(Modelo.Evento.class, idEvento);
                Modelo.Persona persona = emUpdate.find(Modelo.Persona.class, idPersona);

                // ¡ACÁ ESTÁ LA MAGIA! Llamamos a tu método de negocio
                evento.inscribirParticipante(persona);

                emUpdate.merge(evento);
                emUpdate.getTransaction().commit();

                javafx.scene.control.Alert exito = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                exito.setContentText("¡Inscripción exitosa! " + persona.getNombreCompleto() + " participará en " + evento.getNombre());
                exito.showAndWait();

                cmbEventos.getSelectionModel().clearSelection();
                cmbPersonas.getSelectionModel().clearSelection();

            } catch(Exception ex) {
                // SI EL EVENTO NO ESTÁ CONFIRMADO, CAE ACÁ Y MUESTRA TU MENSAJE DE ERROR
                if(emUpdate.getTransaction().isActive()) {
                    emUpdate.getTransaction().rollback(); // Deshacemos por si acaso
                }
                javafx.scene.control.Alert errorNegocio = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
                errorNegocio.setTitle("Regla de Negocio");
                errorNegocio.setHeaderText("Inscripción Rechazada");
                errorNegocio.setContentText(ex.getMessage()); // Imprime literalmente lo que pusiste en el 'throw new Exception(...)'
                errorNegocio.showAndWait();

            } finally {
                emUpdate.close();
            }
        });

        panel.getChildren().addAll(lblTitulo, new Label("Evento:"), cmbEventos, new Label("Participante:"), cmbPersonas, btnInscribir);
        return panel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
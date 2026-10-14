package com.cerp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cerp.Controlador.InicioControlador;
import com.cerp.Modelo.Pregunta;
import com.cerp.Vista.InicioVista;


/**
 * @file com.cerp.Main.java
 * @brief Clase principal del programa que permite crear, modificar y eliminar preguntas.
 * @date 20/03/2023
 * @version 1.0
 * @author Domingo E. Pérez
 */

/**
 * @class com.cerp.Main
 * @brief Clase principal que permite interactuar con la aplicación de preguntas.
 * @Author Domingo Perez
 */
public class Main {
    
    /**
     * @brief Método principal que inicia la ejecución del programa.
     * @param args Parámetros de línea de comandos (no se utilizan).
     * @throws Exception
     */
    public static void main(String[] args) {
        
        // Crear instancia de FileHandler para cargar las preguntas desde preguntas.data
       FileHandler<Pregunta> fileHandler = new FileHandler<Pregunta>("C:\\Users\\camif\\OneDrive\\Documentos\\Proyecto\\Curso-JAVA\\Proyectos\\PreguntasDelCursoMVC\\preguntas.data");
        // D:\\_VSCode\\_Programacon3-JAVA\\Curso-JAVA\\Proyectos\\PreguntasDelCursoMVC\\preguntas.data
        

        List<Pregunta> modelo = new ArrayList<>();        

        try {
            modelo = fileHandler.fileToList();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("El tamanio es " + modelo.size());

        /*InicioVista vistaInicio = new InicioVista();
        vistaInicio.setVisible(true);*/

         
        InicioVista vistaInicio = new InicioVista(fileHandler);
        InicioControlador controlador = new InicioControlador(modelo, vistaInicio);
        vistaInicio.setControlador(controlador);

        /* 
        // Crear instancia de AdminControlador y mostrar vista AdminVista
        AdminVista vistaAdmin = new AdminVista(fileHandler);
        AdminControlador controladorAdmin = new AdminControlador(vistaAdmin);
        // Asociar el controlador como listener de los componentes de la vista
        vistaAdmin.getConfirmarButton().addActionListener(controladorAdmin);
        vistaAdmin.getAtrasButton().addActionListener(controladorAdmin);
        vistaAdmin.getLimpiarButton().addActionListener(controladorAdmin);*/

        
    }
}

package com.cerp.Controlador;


import com.cerp.Randomizer;
import com.cerp.Modelo.Pregunta;
import com.cerp.Vista.InicioVista;
import com.cerp.Vista.PreguntaVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * @file PreguntaControlador.java
 * @brief Clase que define la interfaz gráfica de usuario para mostrar preguntas y opciones de respuesta.
 * @date 2023-03-22
 * @version 1.0
 */

/**
 * Clase para representar el controlador de la interfaz gráfica de preguntas.
 */
public class PreguntaControlador implements ActionListener {
    private List<Pregunta> modelo;
    private PreguntaVista vista;
    private InicioVista vistaInicio;
    private int correctIndex;

    public PreguntaControlador(List<Pregunta> modelo, PreguntaVista vista, InicioVista vistaInicio) {
        this.modelo = modelo;
        this.vista = vista;
        this.vistaInicio = vistaInicio;

        vista.getConfirmarButton().addActionListener(this);
        vista.getAtrasButton().addActionListener(this);
        vista.getSiguienteButton().addActionListener(this);

        vista.getIdLabel().setText("ID de pregunta: Numero");
        vista.getPreguntaLabel().setText("aca otra cosa");
        for (int i = 0; i < vista.getRespuestaButtons().length; i++) {
            vista.getRespuestaButtons()[i].setText("aca otra opcion");  
        }

        this.correctIndex = cargarPregunta();

        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                vistaInicio.getControlador().guardarPreguntas();
                System.exit(0);
            }
        });
    }

    private int cargarPregunta(){
        //Primer pregunta
        // Crear un objeto com.cerp.Randomizer para obtener un elemento aleatorio de la lista
        Randomizer<Pregunta> randomizer = new Randomizer<>();
        // Obtener un elemento aleatorio de la lista de preguntas
        Pregunta preguntaVisible = randomizer.getRandomElement(modelo);
        
        vista.getIdLabel().setText("ID de pregunta: " + preguntaVisible.getIdPregunta());
        String correctAnswer = preguntaVisible.getCorrecta();
        vista.getPreguntaLabel().setText(preguntaVisible.getPregunta());
        int correctButtonIndex = Randomizer.getRandomIndex(0, vista.getRespuestaButtons().length - 1);
        //int correctButtonIndex = (int) (Math.random() * (respuestaButtons.length));
        vista.getRespuestaButtons()[correctButtonIndex].setText(correctAnswer);
        int optionIndex = 0;
        for (int i = 0; i < vista.getRespuestaButtons().length; i++) {
            if (i == correctButtonIndex) {
                continue;
            }
            String option = preguntaVisible.getOpciones()[optionIndex];
            vista.getRespuestaButtons()[i].setText(option);  

            optionIndex++;
        }

        return correctButtonIndex;
    }
        

    /**
     * Acción a realizar cuando se presiona un botón en la interfaz.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getSiguienteButton()) {
            this.correctIndex = cargarPregunta();

            /*int numPregunta = com.cerp.Randomizer.getRandomIndex(0, modelo.size() - 1);
            Pregunta preguntaVisible = modelo.get(numPregunta);
            String correctAnswer = preguntaVisible.getCorrecta();
            vista.getIdLabel().setText("ID de pregunta: " + preguntaVisible.getIdPregunta());
            vista.getPreguntaLabel().setText(preguntaVisible.getPregunta());
            int correctButtonIndex = com.cerp.Randomizer.getRandomIndex(0, vista.getRespuestaButtons().length - 1);
            this.correctIndex = correctButtonIndex;
            //int correctButtonIndex = (int) (Math.random() * (respuestaButtons.length));
            vista.getRespuestaButtons()[correctButtonIndex].setText(correctAnswer);
            int optionIndex = 0;
            for (int i = 0; i < vista.getRespuestaButtons().length; i++) {
                if (i == correctButtonIndex) {
                    continue;
                }
                String option = preguntaVisible.getOpciones()[optionIndex];
                if (option.equals(correctAnswer)) {
                    optionIndex++;
                }
                vista.getRespuestaButtons()[i].setText(option);
                optionIndex++;
            }*/
                
            vista.getButtonGroup().clearSelection();

        } else if (e.getSource() == vista.getConfirmarButton()) {
            int selectedAnswer = Integer.parseInt(vista.getButtonGroup().getSelection().getActionCommand());
                if (selectedAnswer==this.correctIndex){
                    vista.mostrarMensajeConfirmacion("Respuesta correcta.");
                    //JOptionPane.showMessageDialog(vista, "¡Correcto!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    vista.mostrarMensajeError("Respuesta incorrecta, vuelve a intentarlo!");
                    //JOptionPane.showMessageDialog(vista, "¡Incorrecto!" + selectedAnswer + this.correctIndex , "Resultado", JOptionPane.ERROR_MESSAGE);
                }
            
        } else if (e.getSource() == vista.getAtrasButton()) {
            vista.getParentWindow().setVisible(true);
            vista.dispose();
        }
        //pack(); // Ajustar tamaño al contenido
        //setResizable(false); // No permitir redimensionar
    }   
}



/*package PreguntasDelCursoMVC;

import javax.swing.*;

import PreguntasDelCursoMVC.Modelo.Pregunta;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PreguntaVista extends JFrame implements ActionListener {
    private JLabel idLabel;
    private JLabel preguntaLabel;
    private JLabel opcionesLabel;
    private JLabel correctaLabel;
    private JButton siguienteButton;
    private JButton confirmarButton;
    private JButton atrasButton;

    private List<Pregunta> modelo;
    // Agregar atributo InicioVista llamado parentWindow
    private InicioVista parentWindow;


    private JRadioButton[] respuestaButtons;
    private ButtonGroup buttonGroup;

    private int correctIndex;


    public PreguntaVista(FileHandler<Pregunta> fileHandler, InicioVista parentWindow) {
        List<Pregunta> modelo = new ArrayList<Pregunta>();
        
        // Cargar las preguntas desde preguntas.txt
        try {
            modelo = fileHandler.fileToList();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        this.modelo = modelo;
        this.parentWindow = parentWindow; // Asignar el objeto recibido al atributo parentWindow


        // Configuración de la ventana
        setTitle("Juego de preguntas para Programación 3 - CeRP del Suroeste - Prof. Domingo Pérez");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Panel para mostrar la pregunta
        JPanel preguntaPanel = new JPanel();
        preguntaPanel.setLayout(new BoxLayout(preguntaPanel, BoxLayout.Y_AXIS));
        preguntaPanel.setPreferredSize(new Dimension(600, 50)); // especificar una altura fija

        // Configuración de los componentes
        idLabel = new JLabel("ID de pregunta: " + "num");
        idLabel.setBounds(20, 20, 200, 20);
        preguntaPanel.add(idLabel, BorderLayout.CENTER);

        preguntaLabel = new JLabel("Pregunta:");
        preguntaLabel.setBounds(20, 50, 80, 20);
        preguntaPanel.add(preguntaLabel, BorderLayout.CENTER);


        opcionesLabel = new JLabel("Opciones:");
        opcionesLabel.setBounds(20, 80, 80, 20);
        preguntaPanel.add(opcionesLabel, BorderLayout.CENTER);

        correctaLabel = new JLabel("Respuesta correcta:");
        correctaLabel.setBounds(20, 110, 140, 20);
        preguntaPanel.add(correctaLabel, BorderLayout.CENTER);


        add(preguntaPanel, BorderLayout.CENTER);

        // Crear el panel de respuestas
        JPanel answerPanel = new JPanel();
        answerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // agregar márgenes
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));

        respuestaButtons = new JRadioButton[5];
        buttonGroup = new ButtonGroup();

        for (int i = 0; i < respuestaButtons.length; i++) {
            respuestaButtons[i] = new JRadioButton("Opción " + (i + 1));
            respuestaButtons[i].setActionCommand(Integer.toString(i));
            buttonGroup.add(respuestaButtons[i]);
            answerPanel.add(respuestaButtons[i]);
        }

        add(answerPanel, BorderLayout.SOUTH);

        //Primer pregunta
        // Crear un objeto com.cerp.Randomizer para obtener un elemento aleatorio de la lista
        com.cerp.Randomizer<Pregunta> randomizer = new com.cerp.Randomizer<>();
        // Obtener un elemento aleatorio de la lista de preguntas
        Pregunta preguntaVisible = randomizer.getRandomElement(modelo);
        
        idLabel.setText("ID de pregunta: " + preguntaVisible.getIdPregunta());
        String correctAnswer = preguntaVisible.getCorrecta();
        preguntaLabel.setText(preguntaVisible.getPregunta());
        int correctButtonIndex = com.cerp.Randomizer.getRandomIndex(0, respuestaButtons.length - 1);
        //int correctButtonIndex = (int) (Math.random() * (respuestaButtons.length));
        respuestaButtons[correctButtonIndex].setText(correctAnswer);
        int optionIndex = 0;
        for (int i = 0; i < respuestaButtons.length; i++) {
            if (i == correctButtonIndex) {
                continue;
            }
            String option = preguntaVisible.getOpciones()[optionIndex];
            respuestaButtons[i].setText(option);  

            optionIndex++;
        }

        
        // Panel para mostrar los botones de acción
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        siguienteButton = new JButton("Siguiente");
        siguienteButton.setBounds(150, 170, 100, 30);
        siguienteButton.addActionListener(this);

        confirmarButton = new JButton("Confirmar");
        confirmarButton.setBounds(260, 170, 100, 30);
        confirmarButton.addActionListener(this);

        atrasButton = new JButton("Atrás");
        atrasButton.setBounds(20, 170, 100, 30);
        atrasButton.addActionListener(this);

        actionPanel.add(siguienteButton);
        actionPanel.add(confirmarButton);
        actionPanel.add(atrasButton);

        add(actionPanel, BorderLayout.SOUTH);


    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == siguienteButton) {
            int numPregunta = com.cerp.Randomizer.getRandomIndex(0, modelo.size() - 1);
            Pregunta preguntaVisible = modelo.get(numPregunta);
            String correctAnswer = preguntaVisible.getCorrecta();
            idLabel.setText("ID de pregunta: " + preguntaVisible.getIdPregunta());
            preguntaLabel.setText(preguntaVisible.getPregunta());
            int correctButtonIndex = com.cerp.Randomizer.getRandomIndex(0, respuestaButtons.length - 1);
            this.correctIndex = correctButtonIndex;
            //int correctButtonIndex = (int) (Math.random() * (respuestaButtons.length));
            respuestaButtons[correctButtonIndex].setText(correctAnswer);
            int optionIndex = 0;
            for (int i = 0; i < respuestaButtons.length; i++) {
                if (i == correctButtonIndex) {
                    continue;
                }
                String option = preguntaVisible.getOpciones()[optionIndex];
                if (option.equals(correctAnswer)) {
                    optionIndex++;
                }
                respuestaButtons[i].setText(option);
                optionIndex++;
            }
                
            buttonGroup.clearSelection();

        } else if (e.getSource() == confirmarButton) {
            int selectedAnswer = Integer.parseInt(buttonGroup.getSelection().getActionCommand());
                if (selectedAnswer==this.correctIndex){
                    JOptionPane.showMessageDialog(PreguntaVista.this, "¡Correcto!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(PreguntaVista.this, "¡Incorrecto!" + selectedAnswer + this.correctIndex , "Resultado", JOptionPane.ERROR_MESSAGE);
                }
            
        } else if (e.getSource() == atrasButton) {
            parentWindow.setVisible(true); // Hacer visible la ventana InicioVista
            dispose();
        }
        //pack(); // Ajustar tamaño al contenido
        //setResizable(false); // No permitir redimensionar
    }

        
}*/
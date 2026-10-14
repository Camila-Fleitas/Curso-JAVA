/**
 * @file FileHandler.java
 * @brief Clase para manejar archivos de preguntas
 * @date 20/03/2023
 * @version 1.0
 * @author Domingo Pérez
 */
package com.cerp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
 
 /**
  * @class FileHandler
  * @brief Clase para manejar archivos de preguntas
  */
public class FileHandler<T> {
    
    private String filename;
    
    /**
     * Constructor de la clase FileHandler.
     * 
     * @param filename nombre del archivo a manejar.
     */
    public FileHandler(String filename) {
        this.filename = filename;
    }
    
    /**
     * Lee las preguntas desde el archivo.
     * 
     * @return una lista de preguntas.
     * @throws IOException si ocurre un error de entrada/salida.
     * @throws ClassNotFoundException si la clase de algún objeto serializado no puede ser encontrada.
     */
    

    @SuppressWarnings("unchecked")
    public List<T> fileToList() throws IOException, ClassNotFoundException {
        List<T> miLista = new ArrayList<T>();
        
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        
        miLista = (List<T>) objectInputStream.readObject();
        
        objectInputStream.close();
        fileInputStream.close();
        
        return miLista;
    }
    
    /**
     * Escribe las preguntas en el archivo.
     * 
     * @param miLista la lista de preguntas a escribir.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public void listToFile(List<T> miLista) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        
        objectOutputStream.writeObject(miLista);
        
        objectOutputStream.close();
        fileOutputStream.close();
    }

    /**
     * Limpia el contenido del archivo especificado.
     * 
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public void limpiarArchivo() throws IOException {
        FileWriter fileWriter = new FileWriter(filename, false);
        fileWriter.write("limpiezaaaaaa!!!");
        fileWriter.close();
    }

}

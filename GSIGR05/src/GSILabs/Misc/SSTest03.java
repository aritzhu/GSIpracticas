/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GSILabs.Misc;

import java.io.File;
import java.io.IOException;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.spreadsheet.Sheet;

/**
 *
 * @author alumno
 */
public class SSTest03 {
    public static void main(String[] args) throws IOException {
        try{
            File archivoODS = new File("test02.ods");

            SpreadSheet libro = SpreadSheet.createFromFile(archivoODS);
            Sheet hoja = libro.getSheet(0);

            int filas = 4;
            int columnas = 6;
            int filaInicio = 5;
            int columnaInicio = 3;

            int[][] matrizLeida = new int[filas][columnas];

            System.out.println("Leyendo datos desde test02.ods:\n");

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    Object valor = hoja.getCellAt(columnaInicio + j, filaInicio + i).getValue();
                    if (valor instanceof Number) {
                        matrizLeida[i][j] = ((Number) valor).intValue();
                    } else {
                        matrizLeida[i][j] = 0;
                    }
                    System.out.print(matrizLeida[i][j] + "\t");
                }
                System.out.println();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al leer el archivo ODS.");
        }
    }
}

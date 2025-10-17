package GSIlabs.Misc;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.spreadsheet.Sheet;

public class SSTest01 {

    public static void main(String[] args) {
        int filas = 4;
        int columnas = 6;
        int[][] matriz = new int[filas][columnas];
        Random rnd = new Random();

        // Rellenar la matriz con datos aleatorios
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = rnd.nextInt(10);
            }
        }

        try {
            // Crear una hoja con 10 filas, 10 columnas y 1 hoja
            SpreadSheet hoja = SpreadSheet.create(1, 10, 10);
            Sheet sheet = hoja.getSheet(0);

            // Insertar los datos de la matriz en la hoja
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    sheet.setValueAt(matriz[i][j], i, j);
                }
            }

            // Guardar el archivo ODS
            File archivoODS = new File("test01.ods");
            hoja.saveAs(archivoODS);

            System.out.println("Archivo 'test01.ods' creado correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al guardar el archivo ODS.");
        }
    }
}

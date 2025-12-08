package GSILabs.Misc;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.MutableCell;

public class SSTest02 {

    public static void main(String[] args) throws IOException {
        try{
            int filas = 4;
            int filasVacias = 5;
            int columnas = 6;
            int columnasVacias = 3;
            SpreadSheet libro = SpreadSheet.create(1, 10, 10);
            Sheet hoja = libro.getSheet(0);

            Random rnd = new Random();

            for (int i = filasVacias; i < filas+filasVacias; i++) {
                for (int j = columnasVacias; j < columnas+columnasVacias; j++) {
                    int valor = rnd.nextInt(20);
                    MutableCell<?> celda = hoja.getCellAt(j, i);
                    celda.setValue(valor);
                    if (valor > 10) {
                        celda.setBackgroundColor(Color.BLUE);
                    } else {
                        celda.setBackgroundColor(Color.RED);
                    }
                }
            }

            libro.saveAs(new File("test02.ods"));
            System.out.println("Archivo 'test02.ods' guardado correctamente.");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al guardar el archivo ODS.");
        }
    }
}

package Codigo;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

//CLASE QUE GENERA LA TABLA PARA MOSTRAR Y CREA UNA VENTANA PARA MOSTRARLA

public class Display extends JFrame{
    String[] columnNames;
    Object[][] data;
    
    public Display(Listas ltokens, String titulo, boolean simbolos) {
        super(titulo);

        if(simbolos){
            columnNames = new String[]{"Lexema", "Atributo", "Linea", "Repeticiones", "Tipo de Dato", "Categoría"};
        }else{
            columnNames = new String[]{"Lexema", "Atributo", "Linea", "Repeticiones", "Categoría"};
        }

        GenerarDatos(ltokens, simbolos);

        JTable table = new JTable(data, columnNames);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        
        // Establecer ancho predeterminado para cada columna
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100); // Ancho predeterminado para la primera columna
            }else if (i == table.getColumnCount() - 1) {
                DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
                leftRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
                column.setCellRenderer(leftRenderer);
                column.setPreferredWidth(300); // Ancho predeterminado para las demás columnas
            } else {
                column.setPreferredWidth(50); // Ancho predeterminado para las demás columnas
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        setSize(650, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void GenerarDatos(Listas ltokens, boolean simbolos){
        if(simbolos){
            int c = ltokens.CuentaElementos();
            Nodo item;
            data = new Object[c][6];
            for(int i = 0; i < c; i++){
                item = ltokens.getNodo(i);
                data[i][0] = item.dato ;
                data[i][1] = item.atributo;
                data[i][2] = item.linea;
                data[i][3] = item.repeticiones;
                data[i][4] = item.tipoDato;
                if(item.value == null){
                    data[i][5] = "";
                }else{
                    System.out.println("Valor Asignado: " + item.value);
                    data[i][5] = item.value;   
                }                
            }
        }else{
            int c = ltokens.CuentaElementos();
            Nodo item;
            data = new Object[c][5];
            for(int i = 0; i < c; i++){
                item = ltokens.getNodo(i);
                data[i][0] = item.dato ;
                data[i][1] = item.atributo;
                data[i][2] = item.linea;
                data[i][3] = item.repeticiones;
                data[i][4] = item.descripcion;
            }
        }
    }
}
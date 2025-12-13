package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class metadatosMySQL {

    public static void metadatosMySQL(Connection conexion) {

        try {
            DatabaseMetaData meta = conexion.getMetaData();

            System.out.println("=== INFORMACIÓN GENERAL BD MySQL ===");
            System.out.println("Nombre BD:  " + meta.getDatabaseProductName());
            System.out.println("Driver:     " + meta.getDriverName());
            System.out.println("URL:        " + meta.getURL());
            System.out.println("Usuario:    " + meta.getUserName());
            System.out.println();

            String schema = conexion.getCatalog();

            System.out.println("=== TABLAS DEL ESQUEMA " + schema + " ===");

            try (ResultSet tablas = meta.getTables(null, schema, "%", new String[]{"TABLE"})) {

                while (tablas.next()) {

                    String tabla = tablas.getString("TABLE_NAME");
                    System.out.println("\nTABLA: " + tabla);

                    // COLUMNAS
                    System.out.println(" - Columnas:");
                    try (ResultSet cols = meta.getColumns(null, schema, tabla, "%")) {
                        while (cols.next()) {
                            System.out.printf(
                                    "   %s | %s | Tamaño=%d | Null=%s%n",
                                    cols.getString("COLUMN_NAME"),
                                    cols.getString("TYPE_NAME"),
                                    cols.getInt("COLUMN_SIZE"),
                                    cols.getString("IS_NULLABLE")
                            );
                        }
                    }

                    // PRIMARY KEYS
                    System.out.println(" - Clave primaria:");
                    boolean tienePK = false;

                    try (ResultSet pk = meta.getPrimaryKeys(null, schema, tabla)) {
                        while (pk.next()) {
                            System.out.println("   " + pk.getString("COLUMN_NAME"));
                            tienePK = true;
                        }
                    }
                    if (!tienePK) System.out.println("   (no tiene PK)");


                    // FOREIGN KEYS IMPORTADAS
                    System.out.println(" - FKs importadas:");
                    boolean tieneFKImp = false;

                    try (ResultSet fkImp = meta.getImportedKeys(null, schema, tabla)) {
                        while (fkImp.next()) {
                            System.out.printf(
                                    "   %s → %s(%s)%n",
                                    fkImp.getString("FKCOLUMN_NAME"),
                                    fkImp.getString("PKTABLE_NAME"),
                                    fkImp.getString("PKCOLUMN_NAME")
                            );
                            tieneFKImp = true;
                        }
                    }
                    if (!tieneFKImp) System.out.println("   (ninguna)");


                    // FOREIGN KEYS EXPORTADAS
                    System.out.println(" - FKs exportadas:");
                    boolean tieneFKExp = false;

                    try (ResultSet fkExp = meta.getExportedKeys(null, schema, tabla)) {
                        while (fkExp.next()) {
                            System.out.printf(
                                    "   %s(%s) ← %s%n",
                                    fkExp.getString("PKTABLE_NAME"),
                                    fkExp.getString("PKCOLUMN_NAME"),
                                    fkExp.getString("FKTABLE_NAME")
                            );
                            tieneFKExp = true;
                        }
                    }
                    if (!tieneFKExp) System.out.println("   (ninguna)");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void metadatosResultSet(Connection conexion) {

        Scanner sc = new Scanner(System.in);

        try {
            DatabaseMetaData metaBD = conexion.getMetaData();
            String catalog = conexion.getCatalog(); // Base de datos activa

            System.out.println("=== TABLAS DISPONIBLES ===");
            List<String> misTablas = new ArrayList<>();

            // Obtener todas las tablas del catálogo actual
            try (ResultSet tablas = metaBD.getTables(catalog, null, "%", new String[]{"TABLE"})) {
                while (tablas.next()) {
                    String tabla = tablas.getString("TABLE_NAME");
                    System.out.println(" - " + tabla);
                    misTablas.add(tabla);
                }
            }

            if (misTablas.isEmpty()) {
                System.out.println("No tienes tablas.");
                return;
            }

            // Pedir tabla al usuario
            System.out.print("\nIntroduce el nombre de la tabla: ");
            String tablaElegida = sc.nextLine().trim();

            boolean existe = false;
            for (String t : misTablas) {
                if (t.equalsIgnoreCase(tablaElegida)) {
                    tablaElegida = t;
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                System.out.println("Esa tabla no existe.");
                return;
            }

            // Ejecutar consulta y mostrar metadatos
            String sql = "SELECT * FROM " + tablaElegida + " LIMIT 1"; // LIMIT 1 para no traer demasiados datos
            try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {

                ResultSetMetaData meta = rs.getMetaData();
                int numCols = meta.getColumnCount();

                System.out.println("\n=== METADATOS DEL RESULTSET ===");
                System.out.println("Número de columnas: " + numCols + "\n");

                for (int i = 1; i <= numCols; i++) {
                    System.out.printf(
                            "Columna %d:\n" +
                                    "   Nombre: %s\n" +
                                    "   Tipo: %s\n" +
                                    "   Tamaño: %d\n" +
                                    "   Nullable: %s\n\n",
                            i,
                            meta.getColumnName(i),
                            meta.getColumnTypeName(i),
                            meta.getColumnDisplaySize(i),
                            meta.isNullable(i) != ResultSetMetaData.columnNoNulls ? "SI" : "NO"
                    );
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
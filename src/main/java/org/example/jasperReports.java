package org.example;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class jasperReports {

    public static void listarClientesJP_SQL (Connection conexion) {

        // Asignamos la ruta de la plantilla, y la ruta de llegada de los informes
        String Plantilla = "InformesJasperReports/Plantillas/plantillaClientes.jrxml";

        // Recogemos la fecha actual
        LocalDate fecha =  LocalDate.now();

        // Sacamos parametros para llevar al Informe
        Map<String, Object> parametros = new HashMap<>();

        // Asignamos el valor a los parametros
        parametros.put("Titulo", "LISTADO DE LOS CLIENTES");
        parametros.put("Autor", "Jowy & Carmen");
        parametros.put("Fecha", fecha.getDayOfMonth() + " / " + fecha.getMonthValue() + " / " + fecha.getYear());

        try {

            Path outputDir = Paths.get("informes"); // carpeta REAL

            Files.createDirectories(outputDir);

            String InformePDF  = outputDir.resolve("InformeClientes.pdf").toString();
            String InformeHTML = outputDir.resolve("InformeClientes.html").toString();
            String InformeXML  = outputDir.resolve("InformeClientes.xml").toString();


            // Vamos casteando la plantilla a JasperReports, y lo compilamos
            InputStream is = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(Plantilla);

            if (is == null) {
                throw new RuntimeException("No se encontró la plantilla JRXML");
            }

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(is);

            // Generamos el Informe a raiz del JasperReports, los parametros asignados y la conexion
            JasperPrint Informe =  JasperFillManager.fillReport(jasperReport, parametros, conexion);

            JasperExportManager.exportReportToPdfFile(Informe, InformePDF);
            JasperExportManager.exportReportToHtmlFile(Informe, InformeHTML);
            JasperExportManager.exportReportToXmlFile(Informe, InformeXML, false);

            System.out.println("Informes generados correctamente");

            // Esta linea nos permitira ver el informe por pantalla
            JasperViewer.viewReport(Informe, false);

        } catch (JRException | IOException e) {

            System.err.println("Error al generar el Reporte de los clientes");
            throw new RuntimeException(e);

        }

    }

}

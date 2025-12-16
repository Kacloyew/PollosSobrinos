package org.example;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class jasperReports {

    public static void listarClientesJP_SQL (Connection conexion) {

        // Asignamos la ruta de la plantilla, y la ruta de llegada de los informes
        String Plantilla = "InformesJasperReports/Plantillas/plantillaClientes.jrxml";

        // Informes
        String InformePDF = "InformesJasperReports/Informes/InformeClientes.pdf";
        String InformeXML = "InformesJasperReports/Informes/InformeClientes.xml";
        String InformeHTML = "InformesJasperReports/Informes/InformeClientes.html";

        // Recogemos la fecha actual
        LocalDate fecha =  LocalDate.now();

        // Sacamos parametros para llevar al Informe
        Map<String, Object> parametros = new HashMap<>();

        // Asignamos el valor a los parametros
        parametros.put("Titulo", "LISTADO DE LOS CLIENTES");
        parametros.put("Autor", "Jowy & Carmen");
        parametros.put("Fecha", fecha.getDayOfMonth() + " / " + fecha.getMonthValue() + " / " + fecha.getYear());

        try {

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

            // Esta linea nos permitira ver el informe por pantalla
            JasperViewer.viewReport(Informe, false);

            JasperExportManager.exportReportToPdfFile(Informe, InformePDF);
            JasperExportManager.exportReportToHtmlFile(Informe, InformeHTML);
            JasperExportManager.exportReportToXmlFile(Informe, InformeXML, false);

            System.out.println("Informes generados correctamente");

        } catch (JRException e) {

            System.err.println("Error al generar el Reporte de los clientes");
            throw new RuntimeException(e);

        }

    }

}

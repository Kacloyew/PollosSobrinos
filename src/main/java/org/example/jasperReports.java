package org.example;

import net.sf.jasperreports.engine.*;

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

            Path outputDir = Paths.get("Informes"); // carpeta REAL

            Files.createDirectories(outputDir);

            String InformePDF  = outputDir.resolve("InformeClientesMySQL.pdf").toString();
            String InformeHTML = outputDir.resolve("InformeClientesMySQL.html").toString();
            String InformeXML  = outputDir.resolve("InformeClientesMySQL.xml").toString();


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
            //JasperViewer.viewReport(Informe, false);

        } catch (JRException | IOException e) {

            System.err.println("Error al generar el Reporte de los clientes");
            throw new RuntimeException(e);

        }

    }

    public static void listarPedidosJP_SQL (Connection conexion) {

        // Asignamos la ruta de la plantilla, y la ruta de llegada de los informes
        String Plantilla = "InformesJasperReports/Plantillas/plantillaEmpleados.jrxml";

        // Recogemos la fecha actual
        LocalDate fecha =  LocalDate.now();

        // Sacamos parametros para llevar al Informe
        Map<String, Object> parametros = new HashMap<>();

        // Asignamos el valor a los parametros
        parametros.put("Titulo", "LISTADO DE LOS EMPLEADOS");
        parametros.put("Autor", "Jowy & Carmen");
        parametros.put("Fecha", fecha.getDayOfMonth() + " / " + fecha.getMonthValue() + " / " + fecha.getYear());

        try {

            Path outputDir = Paths.get("Informes"); // carpeta REAL

            Files.createDirectories(outputDir);

            String InformePDF  = outputDir.resolve("InformeEmpleadosMySQL.pdf").toString();
            String InformeHTML = outputDir.resolve("InformeEmpleadosMySQL.html").toString();
            String InformeXML  = outputDir.resolve("InformeEmpleadosMySQL.xml").toString();


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
            //JasperViewer.viewReport(Informe, false);

        } catch (JRException | IOException e) {

            System.err.println("Error al generar el Reporte de los clientes");
            throw new RuntimeException(e);

        }

    }

        public static void listarClientesJP_Oracle(Connection conexion) {

            String plantilla = "InformesJasperReports/Plantillas/plantillaClientes.jrxml";

            LocalDate fecha = LocalDate.now();

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Titulo", "LISTADO DE LOS CLIENTES");
            parametros.put("Autor", "Jowy & Carmen");
            parametros.put("Fecha",
                    fecha.getDayOfMonth() + " / " +
                            fecha.getMonthValue() + " / " +
                            fecha.getYear());

            try {

                Path outputDir = Paths.get("Informes");
                Files.createDirectories(outputDir);

                String informePDF  = outputDir.resolve("InformeClientesOracle.pdf").toString();
                String informeHTML = outputDir.resolve("InformeClientesOracle.html").toString();
                String informeXML  = outputDir.resolve("InformeClientesOracle.xml").toString();

                InputStream is = Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream(plantilla);

                if (is == null) {
                    throw new RuntimeException("No se encontró la plantilla JRXML");
                }

                JasperReport jasperReport =
                        JasperCompileManager.compileReport(is);

                JasperPrint informe =
                        JasperFillManager.fillReport(jasperReport, parametros, conexion);

                JasperExportManager.exportReportToPdfFile(informe, informePDF);
                JasperExportManager.exportReportToHtmlFile(informe, informeHTML);
                JasperExportManager.exportReportToXmlFile(informe, informeXML, false);

                System.out.println("Informes de clientes generados correctamente");

            } catch (JRException | IOException e) {
                System.err.println("Error al generar el reporte de clientes");
                throw new RuntimeException(e);
            }
        }

        public static void listarPedidosJP_Oracle(Connection conexion) {

            String plantilla = "InformesJasperReports/Plantillas/plantillaEmpleados.jrxml";

            LocalDate fecha = LocalDate.now();

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Titulo", "LISTADO DE LOS EMPLEADOS");
            parametros.put("Autor", "Jowy & Carmen");
            parametros.put("Fecha",
                    fecha.getDayOfMonth() + " / " +
                            fecha.getMonthValue() + " / " +
                            fecha.getYear());

            try {

                Path outputDir = Paths.get("Informes");
                Files.createDirectories(outputDir);

                String informePDF  = outputDir.resolve("InformeEmpleadosOracle.pdf").toString();
                String informeHTML = outputDir.resolve("InformeEmpleadosOracle.html").toString();
                String informeXML  = outputDir.resolve("InformeEmpleadosOracle.xml").toString();

                InputStream is = Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream(plantilla);

                if (is == null) {
                    throw new RuntimeException("No se encontró la plantilla JRXML");
                }

                JasperReport jasperReport =
                        JasperCompileManager.compileReport(is);

                JasperPrint informe =
                        JasperFillManager.fillReport(jasperReport, parametros, conexion);

                JasperExportManager.exportReportToPdfFile(informe, informePDF);
                JasperExportManager.exportReportToHtmlFile(informe, informeHTML);
                JasperExportManager.exportReportToXmlFile(informe, informeXML, false);

                System.out.println("Informes de empleados generados correctamente");

            } catch (JRException | IOException e) {
                System.err.println("Error al generar el reporte de empleados");
                throw new RuntimeException(e);
            }
        }
    }




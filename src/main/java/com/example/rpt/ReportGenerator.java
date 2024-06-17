package com.example.rpt;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;


/**
 * The ReportGenerator class is responsible for generating a report in different formats based on a given JasperReport template.
 * It connects to a database, retrieves data, fills the report template, and exports it to a specified output path.
 */
public class ReportGenerator {
    /**
     *
     */
    public static void main(String[] args) {
        Connection conn = null;
        try {

            String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String jdbcUrl= "jdbc:sqlserver://localhost:1433;databaseName=master;user=sa;password=<YourPassword>;encrypt=true;trustServerCertificate=true";

            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(jdbcUrl);

            File reportFile = new File("C:\\Users\\devel\\IdeaProjects\\rpt\\src\\main\\resources\\report\\test.jrxml");

            JasperDesign jasperDesign = JRXmlLoader.load(reportFile);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

//            Passing parameters to be used in the pdf. If you want to use large amounts of data, using DB query is more optimal.
//            Map<String, Object> parameters = new HashMap<>();
//            // Adding param on map
//            parameters.put("Param1", "Val1");
//            parameters.put("Param", "Val2");
//            ...
//            //
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);

            String format = "pdf";
            String outputPath = "C:\\output\\report." + format;

            switch (format) {
                case "pdf":
                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
                    break;
                case "docx":
                    JRDocxExporter docxExporter = new JRDocxExporter();
                    docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    docxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath));
                    docxExporter.exportReport();
                    break;
                case "xlsx":
                    JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                    xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath));
                    xlsxExporter.exportReport();
                    break;
                default:
                    System.out.println("Formato no soportado");
                    return;
            }

            System.out.println(format.toUpperCase() + " report has been generated and saved at: " + outputPath);

        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
        }
    }
}


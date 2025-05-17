/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.report;

import java.sql.Connection;
import mx.itson.spotify.db.ConnectionDB;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author emili
 */
public class Report {

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        try {
            Connection conexion = new ConnectionDB().getConnection();
            String reporteRuta = "ReporteContactos.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reporteRuta);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            JasperViewer.viewReport(jasperPrint);
            conexion.close();
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println(ex.toString());
        }
    }
}

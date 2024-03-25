package com.example.controle_de_pagamentos;

import android.os.Environment;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;

public class GerarECarregarPdf {

    public static void gerarPdf(String nomeDoCliente, BigDecimal valorPago, BigDecimal valorRestante) {
        Document document = new Document();

        try {
            File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File pdfFile = new File(downloadsDirectory, "Faturas.pdf");

            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            document.open();

            Paragraph paragraph = new Paragraph();
            paragraph.add(nomeDoCliente + "\n");
            paragraph.add("Valor Pago: " + valorPago.toString() + "\n");
            paragraph.add("Parcelas restantes: " + valorRestante.toString() + "\n");

            document.add(paragraph);

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}

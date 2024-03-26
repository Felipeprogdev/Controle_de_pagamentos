package com.example.controle_de_pagamentos;

import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import android.os.Environment;
import android.content.Context;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

// Classe responsável por gerar e carregar o PDF
public class GerarECarregarPdf {

    // Método para gerar o PDF
    public static void gerarPdf(String nomeDoCliente, BigDecimal total,  BigDecimal valorPago, BigDecimal parcela, BigDecimal valorRestante, LocalDate data, LocalTime hora, Context context) {
        // Cria um novo documento com as margens definidas
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);

        // Declarando a variável pdfFile fora do bloco try-catch
        File pdfFile = null;

        try {
            // Define o diretório de downloads
            File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            // Cria um novo arquivo PDF no diretório de downloads
            pdfFile = new File(downloadsDirectory, nomeDoCliente + "-" + data + ".pdf");

            // Inicializa o PdfWriter com o documento e o arquivo de saída
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            // Abre o documento para edição
            document.open();

            // Define a fonte em negrito
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            // Cria um separador de linha
            LineSeparator separator = new LineSeparator();

            // Cria um novo parágrafo com o nome do cliente, define o alinhamento e adiciona ao documento
            Paragraph paragraph = new Paragraph(nomeDoCliente + "\n", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(10f);
            document.add(paragraph);
            document.add(separator);

            // Cria um novo parágrafo com a parcela paga, define o alinhamento e adiciona ao documento
            paragraph = new Paragraph("Valor total: " + total.toString() +"\n", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(10f);
            document.add(paragraph);
            document.add(separator);

            // Cria um novo parágrafo com a parcela paga, define o alinhamento e adiciona ao documento
            paragraph = new Paragraph("Parcela paga: " + parcela.toString() + "ª" +"\n", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(10f);
            document.add(paragraph);
            document.add(separator);

            // Cria um novo parágrafo com o valor pago, define o alinhamento e adiciona ao documento
            paragraph = new Paragraph("Valor pago: " + valorPago.toString() + "\n", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(10f);
            document.add(paragraph);
            document.add(separator);

            // Cria um novo parágrafo com o valor restante, define o alinhamento e adiciona ao documento
            paragraph = new Paragraph("Valor restante: " + valorRestante.toString() + "\n", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(10f);
            document.add(paragraph);
            document.add(separator);

            // Cria um novo parágrafo com a data, define o alinhamento e adiciona ao documento
            paragraph = new Paragraph("Data: " + data.toString() +" "+ hora.toString(), font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(10f);
            document.add(paragraph);
            document.add(separator);

        } catch (DocumentException | FileNotFoundException e) {
            // Imprime a pilha de exceções se algo der errado
            e.printStackTrace();
        } finally {
            // Fecha o documento
            document.close();
        }
        // Verifica se o arquivo foi criado corretamente
        if (pdfFile != null) {
            // Abrindo o arquivo PDF
            Uri pdfUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", pdfFile);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(pdfUri, "application/pdf");
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // Inicia a atividade para visualizar o PDF
            context.startActivity(pdfIntent);
        }
    }
}


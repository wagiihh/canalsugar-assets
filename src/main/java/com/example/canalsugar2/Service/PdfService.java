package com.example.canalsugar2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.example.canalsugar2.Models.Asset;
import com.lowagie.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] generatePdfFromHtml(String htmlContent) throws IOException, DocumentException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }

    public String generateHtmlContent(List<Asset> assets) {
        Context context = new Context();
        context.setVariable("assets", assets); // Add data to the context
        return templateEngine.process("assets_report", context); // "assets_report" is your Thymeleaf template name
    }
}

// package com.example.canalsugar2.Controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.ByteArrayResource;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.canalsugar2.Models.Asset;
// import com.example.canalsugar2.Repositories.AssetRepository;
// import com.example.canalsugar2.Service.PdfService;

// import java.io.IOException;
// import java.util.List;

// @RestController
// @RequestMapping("/pdf")
// public class PdfController {

//     @Autowired
//     private PdfService pdfService;

//     @Autowired
//     private AssetRepository assetRepository;

//     @GetMapping("/export")
//     public ResponseEntity<ByteArrayResource> exportPdf() {
//         List<Asset> assets = assetRepository.findAll(); // Fetch your assets from the database or service

//         String htmlContent = pdfService.generateHtmlContent(assets);

//         try {
//             byte[] pdfBytes = pdfService.generatePdfFromHtml(htmlContent);
//             ByteArrayResource resource = new ByteArrayResource(pdfBytes);

//             return ResponseEntity.ok()
//                     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=assets_report.pdf")
//                     .contentType(MediaType.APPLICATION_PDF)
//                     .contentLength(pdfBytes.length)
//                     .body(resource);
//         } catch (IOException e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//         }
//     }
// }

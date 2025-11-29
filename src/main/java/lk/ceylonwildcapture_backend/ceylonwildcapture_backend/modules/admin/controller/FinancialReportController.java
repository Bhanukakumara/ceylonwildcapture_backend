package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.controller;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.FinancialReportDto;
import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service.FinancialReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST controller for financial report generation.
 */
@RestController
@RequestMapping("/api/v1/admin/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class FinancialReportController {

    private final FinancialReportService financialReportService;

    @GetMapping("/financial")
    public ResponseEntity<FinancialReportDto> generateFinancialReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        FinancialReportDto report = financialReportService.generateFinancialReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/financial/monthly")
    public ResponseEntity<FinancialReportDto> generateMonthlyReport(
            @RequestParam int year,
            @RequestParam int month) {
        FinancialReportDto report = financialReportService.generateMonthlyReport(year, month);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/financial/yearly")
    public ResponseEntity<FinancialReportDto> generateYearlyReport(@RequestParam int year) {
        FinancialReportDto report = financialReportService.generateYearlyReport(year);
        return ResponseEntity.ok(report);
    }

    @PostMapping("/financial/export/pdf")
    public ResponseEntity<byte[]> exportToPdf(@RequestBody FinancialReportDto report) {
        byte[] pdfBytes = financialReportService.exportToPdf(report);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "financial-report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @PostMapping("/financial/export/excel")
    public ResponseEntity<byte[]> exportToExcel(@RequestBody FinancialReportDto report) {
        byte[] excelBytes = financialReportService.exportToExcel(report);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "financial-report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

    @PostMapping("/financial/export/csv")
    public ResponseEntity<byte[]> exportToCsv(@RequestBody FinancialReportDto report) {
        byte[] csvBytes = financialReportService.exportToCsv(report);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "financial-report.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvBytes);
    }
}

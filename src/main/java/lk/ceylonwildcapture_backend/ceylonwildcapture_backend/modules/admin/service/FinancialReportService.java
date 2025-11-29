package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.service;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.admin.dto.FinancialReportDto;

import java.time.LocalDate;

/**
 * Service interface for generating financial reports.
 */
public interface FinancialReportService {

    /**
     * Generate a comprehensive financial report for a date range.
     *
     * @param startDate start date
     * @param endDate end date
     * @return financial report DTO
     */
    FinancialReportDto generateFinancialReport(LocalDate startDate, LocalDate endDate);

    /**
     * Generate monthly financial report.
     *
     * @param year year
     * @param month month
     * @return financial report DTO
     */
    FinancialReportDto generateMonthlyReport(int year, int month);

    /**
     * Generate yearly financial report.
     *
     * @param year year
     * @return financial report DTO
     */
    FinancialReportDto generateYearlyReport(int year);

    /**
     * Export financial report to PDF.
     *
     * @param report financial report DTO
     * @return PDF file as byte array
     */
    byte[] exportToPdf(FinancialReportDto report);

    /**
     * Export financial report to Excel.
     *
     * @param report financial report DTO
     * @return Excel file as byte array
     */
    byte[] exportToExcel(FinancialReportDto report);

    /**
     * Export financial report to CSV.
     *
     * @param report financial report DTO
     * @return CSV file as byte array
     */
    byte[] exportToCsv(FinancialReportDto report);
}

/*
 * File: Report.java
 * Package: com.smartpark.utils
 * Purpose: Report generation with varargs methods
 * Requirements: #11 (Varargs methods - multiple overloaded varargs)
 */

package com.smartpark.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * REQUIREMENT #11: Varargs overloading - multiple methods with variable-length arguments.
 * Report class demonstrates varargs usage for generating flexible reports.
 */
public class Report {
    private String reportId;
    private LocalDateTime generatedTime;
    private DateTimeFormatter formatter;

    public Report(String reportId) {
        this.reportId = reportId;
        this.generatedTime = LocalDateTime.now();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    // REQUIREMENT #11: Varargs overloading (Case 1 - generate report with headers and data rows)
    public String generateReport(String title, String... dataRows) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title).append(" ===\n");
        sb.append("Report ID: ").append(reportId).append("\n");
        sb.append("Generated: ").append(generatedTime.format(formatter)).append("\n");
        sb.append("---\n");
        for (String row : dataRows) {
            sb.append(row).append("\n");
        }
        sb.append("---\n");
        return sb.toString();
    }

    // REQUIREMENT #11: Varargs overloading (Case 2 - generate table with columns and data)
    public String generateTableReport(String title, String[] columns, String... rows) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title).append(" ===\n");
        sb.append("Report ID: ").append(reportId).append("\n");
        sb.append("Generated: ").append(generatedTime.format(formatter)).append("\n");
        sb.append("---\n");

        // Print column headers
        for (String col : columns) {
            sb.append(String.format("%-20s", col));
        }
        sb.append("\n");
        sb.append("-".repeat(columns.length * 20)).append("\n");

        // Print rows
        for (String row : rows) {
            sb.append(row).append("\n");
        }
        sb.append("---\n");
        return sb.toString();
    }

    // REQUIREMENT #11: Varargs overloading (Case 3 - generate report with metrics)
    public String generateMetricsReport(String title, Object... metrics) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title).append(" ===\n");
        sb.append("Report ID: ").append(reportId).append("\n");
        sb.append("Generated: ").append(generatedTime.format(formatter)).append("\n");
        sb.append("---\n");
        for (Object metric : metrics) {
            sb.append(metric.toString()).append("\n");
        }
        sb.append("---\n");
        return sb.toString();
    }

    // REQUIREMENT #11: Varargs overloading (Case 4 - summary report with key-value pairs)
    public String generateSummaryReport(String title, String... keyValuePairs) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title).append(" ===\n");
        sb.append("Report ID: ").append(reportId).append("\n");
        sb.append("Generated: ").append(generatedTime.format(formatter)).append("\n");
        sb.append("---\n");

        // Process key-value pairs (expecting alternating keys and values)
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            if (i + 1 < keyValuePairs.length) {
                sb.append(String.format("%-25s: %s\n", keyValuePairs[i], keyValuePairs[i + 1]));
            }
        }
        sb.append("---\n");
        return sb.toString();
    }

    public String getReportId() {
        return reportId;
    }

    public LocalDateTime getGeneratedTime() {
        return generatedTime;
    }
}

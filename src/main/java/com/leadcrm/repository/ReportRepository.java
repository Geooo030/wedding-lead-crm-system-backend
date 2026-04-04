package com.leadcrm.repository;

import com.leadcrm.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByReportTypeAndReportDate(Report.ReportType reportType, LocalDate reportDate);
    Optional<Report> findTopByReportTypeOrderByReportDateDesc(Report.ReportType reportType);
}
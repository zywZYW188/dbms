package com.example.dbms.service;


import com.example.dbms.domain.po.ImagingReport;

public interface ImagingReportService {
    int insertImagingReport(ImagingReport imagingReport);
    int updateImagingReport(ImagingReport imagingReport, int patient_id);
    ImagingReport findById(int patient_id);
}

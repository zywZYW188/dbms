package com.example.dbms.service.impl;


import com.example.dbms.dao.ImagingReportMapper;
import com.example.dbms.domain.po.ImagingReport;
import com.example.dbms.service.ImagingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ImagingReportService")
public class ImagingReportServiceImpl implements ImagingReportService {
    @Autowired
    private ImagingReportMapper imagingReportMapper;
    @Override
    public int insertImagingReport(ImagingReport imagingReport)
    {
        return imagingReportMapper.insertImagingReport(imagingReport);
    }
    @Override
    public int updateImagingReport(ImagingReport imagingReport, int patient_id)
    {
       return imagingReportMapper.updateImagingReport(imagingReport,patient_id);
    }
    @Override
    public ImagingReport findById(int patient_id)
    {
        return imagingReportMapper.findById(patient_id);
    }
}

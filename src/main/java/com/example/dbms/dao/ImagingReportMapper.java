package com.example.dbms.dao;

import com.example.dbms.domain.po.ImagingReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ImagingReportMapper {
    int insertImagingReport(ImagingReport imagingReport);
    int updateImagingReport(@Param("imagingReport") ImagingReport imagingReport, @Param("patient_id") int patient_id);
    ImagingReport findById(int patient_id);
}

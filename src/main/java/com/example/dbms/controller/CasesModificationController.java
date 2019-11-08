package com.example.dbms.controller;

import com.example.dbms.api.ErrorCode;
import com.example.dbms.api.ResponseMessage;
import com.example.dbms.api.UpdateResponseMessage;
import com.example.dbms.dao.*;
import com.example.dbms.domain.po.*;
import com.example.dbms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CasesModificationController {
    Logger LOGGER= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatientsService patientsService;

    @Autowired
    private BasicCaseService basicCaseService;

    @Autowired
    private ComprehensiveCaseService comprehensiveCaseService;

    @Autowired
    private ImagingReportService imagingReportService;

    @Autowired
    private CtService ctService;

    @Autowired
    private DiagnoseInfoMapper diagnoseInfoMapper;
    @GetMapping("/CasesModification/getPatients")
    public Patients findPatients(@RequestParam("admissionnumber") String admissionnumber) {
        Patients p = patientsService.findPatients(admissionnumber);
        if (p == null) {
            LOGGER.info("patients is not exist.");
        }
        return p;
    }
    @GetMapping("/CasesModification/getBasiccase")
    public Basiccase getbasiccase(@RequestParam("patientId") int id) {

        Basiccase p = basicCaseService.findById(id);

        if (p == null) {
            LOGGER.info("basiccase is not exist.");
        }
        return p;
    }
    @GetMapping("/CasesModification/getComprehensiveCase")
    public ComprehensiveCase getComprehensiveCase(@RequestParam("patientId") int id) {
        ComprehensiveCase p =comprehensiveCaseService.findById(id);
        if (p == null) {
            LOGGER.info("comprehensiveCase is not exist.");
        }
        return p;
    }
    @GetMapping("/CasesModification/getRadiography")
    public ImagingReport getRadiography(@RequestParam("patient_id") int patient_id) {
        ImagingReport p= imagingReportService.findById(patient_id);
        if (p == null) {
            LOGGER.info("ImagingReport is not exist.");
        }
        return p;
    }
    @GetMapping("/CasesModification/getCT")
    public Ct_report getCT(@RequestParam("patient_id") int patient_id) {
        Ct_report p= ctService.findById(patient_id);
        if (p == null) {
            LOGGER.info("CTReport is not exist.");
        }
        return p;
    }
    @GetMapping("/CasesModification/getDiagnoseInfo")
    public Diagnose_info getDiagnoseInfo(@RequestParam("patient_id") int patient_id) {
        Diagnose_info p = diagnoseInfoMapper.findById(patient_id);
        if (p == null) {
            LOGGER.info("diagnose_info is not exist.");
        }
        return p;
    }
    @PostMapping("/CasesModification/updatePatients")
    public ResponseMessage updatePatients(@RequestBody Patients patients, @RequestParam("id") int id) {
        patientsService.updatePatients(patients, id);
        LOGGER.info("Patients表更新成功");
        return new UpdateResponseMessage(id, ErrorCode.SUCCESS, "Update patients success.");
    }

    @PostMapping("/CasesModification/updateBasicCase")
    public ResponseMessage updateBasicCase(@RequestBody Basiccase basiccase, @RequestParam("id") int patient_id) {
        basicCaseService.updateBasiccase(basiccase, patient_id);
        LOGGER.info("Basiccase表更新成功");
        return new UpdateResponseMessage(patient_id, ErrorCode.SUCCESS, "Update Basiccase success.");
    }
    @PostMapping("/CasesModification/updateComprehensive_case")
    public ResponseMessage updateComprehensive_case(@RequestBody ComprehensiveCase comprehensiveCase, @RequestParam("id") int patient_id) {
        comprehensiveCaseService.updateComprehensiveCase(comprehensiveCase, patient_id);
        LOGGER.info("comprehensiveCase表更新成功");
        return new UpdateResponseMessage(patient_id, ErrorCode.SUCCESS, "Update comprehensiveCase success.");
    }
    @PostMapping("/CasesModification/updateImagingReport")
    public ResponseMessage updateImagingReport(@RequestBody ImagingReport imagingReport, @RequestParam("id") int patient_id) {
        imagingReportService.updateImagingReport(imagingReport, patient_id);
        LOGGER.info("ImagingReport表更新成功");
        return new UpdateResponseMessage(patient_id, ErrorCode.SUCCESS, "Update ImagingReport success.");
    }

    @PostMapping("/CasesModification/updateCT")
    public ResponseMessage updateCT(@RequestBody Ct_report ct_report, @RequestParam("id") int patient_id) {
        ctService.updateCT(ct_report, patient_id);
        LOGGER.info("CT表更新成功");
        return new UpdateResponseMessage(patient_id, ErrorCode.SUCCESS, "Update CT success.");
    }

    @PostMapping("/CasesModification/updateDiagnoseInfo")
    public ResponseMessage updateDiagnoseInfo(@RequestBody Diagnose_info diagnose_info, @RequestParam("id") int patient_id) {
        diagnoseInfoMapper.updateDiagnoseInfo(diagnose_info, patient_id);
        LOGGER.info("DiagnoseInfo表更新成功");
        return new UpdateResponseMessage(patient_id, ErrorCode.SUCCESS, "Update DiagnoseInfo success.");
    }
}

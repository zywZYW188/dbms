package com.example.dbms.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.dbms.api.ErrorCode;
import com.example.dbms.api.ResponseMessage;
import com.example.dbms.api.StoreCDGResponseMessage;
import com.example.dbms.domain.DTO.Point;
import com.example.dbms.domain.po.*;
import com.example.dbms.domain.vo.PatientsInfo;
import com.example.dbms.domain.vo.QueryParam;
import com.example.dbms.service.*;
import com.example.dbms.util.FileConstants;
import com.example.dbms.util.FileOperations;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class CasesSearchController {
    private static final String HADOOP_SUCCESS = "_SUCCESS";
    private static final String COMMA = ",";

    @Autowired
    private PatientsinfoService patientsinfoService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private CasesService casesService;

    @Autowired
    private  EcgService ecgService;

    @Autowired
    private CdgService cdgService;

    @Autowired
    private PatientsService patientsService;

    @GetMapping ("/CasesSearch/patientsinfo")
    @ResponseBody
    public List<PatientsInfo> queryPatientsInfo(@RequestParam("type_tag") int type_tag, @RequestParam("admissionnumber") String admissionnumber,
                                                @RequestParam("name") String name, @RequestParam("sex") String sex,
                                                @RequestParam("ecg_result") int ecg_result, @RequestParam("imaging_result") int imaging_result,
                                                @RequestParam("cdg_result") int cdg_result, @RequestParam("cdg_Index1") float cdg_Index1,
                                                @RequestParam("cdg_Index2") float cdg_Index2)
    {
       Users user = (Users) SecurityUtils.getSubject().getPrincipal();
       QueryParam param1=new QueryParam(type_tag,admissionnumber,name,sex,ecg_result,imaging_result,cdg_result,user.gethospital(),cdg_Index1,cdg_Index2);
       return patientsinfoService.queryPatientsInfo(param1);
    }

    @GetMapping ("/CasesSearch/diagnosis")
    @ResponseBody
    public List<Diagnosis> queryDiagnosis(@RequestParam("admissionnumber") String admissionnumber)
    {
        return diagnosisService.queryDiagnosis(admissionnumber);
    }

    @GetMapping ("/CasesSearch/cases")
    @ResponseBody
    public Cases queryCases(@RequestParam("admissionnumber") String admissionnumber)
    {
        return casesService.findCasesByAdmissionnumber(admissionnumber);
    }

    @GetMapping ("/CasesSearch/getEcg")
    @ResponseBody
    public String findEcgByTestId(@RequestParam("testId") String testId)
    {
        //Ecg ecg=ecgMapper.findECGData(testId);
        //String ecgdata = ecg.getEcg_data();
        String ecgdata=ecgService.findECGData(testId);
        int i = 0;
        int j = 0;
        int frequency = 500;
        double pixcell = 5;
        double perpix = 15 * pixcell/frequency;
        double wt = 20 * pixcell;
        double[][] height = new double[12][3];

        double heightTitle = 82;
        double ht = 5 * pixcell + heightTitle;
        String[] column = ecgdata.split("],");
        double[][] ecgdata12 = new double[20000][12];
        double[] cutecgdata12 = new double[20000];
        JSONObject resultObj = new JSONObject();
        for(j = 0;j < 12;j++)
        {
            String[] row = column[j].replace("[", "").replace("]","").replace("?", "").split(",");
            if(j == 0){
                //LOGGER.info(Arrays.deepToString(row));
                //LOGGER.info("/////");
            }

            //此处i<3000
            //for(i = 0; i < 20000; i++)
            for(i = 0;i < row.length;i++)
            {
                double number ;
                if (i == 0){
                    number = 15.2843;
                }
                else {
                    number = Double.parseDouble(row[i]);
                }
                //number = Double.parseDouble(row[i]);

                if (j >= 7){
                    cutecgdata12[i] = number/1500;
                }
                else if(j == 1){
                    cutecgdata12[i] = number/500;
                }
                else{
                    cutecgdata12[i] = number/1000;
                }
            }
            //此处i<3000
            for(i = 0;i < 20000;i++)
            {
                ecgdata12[i][j] = (-cutecgdata12[i]) * pixcell * 10;
            }
        }//读取数据，改变数据格式
        //LOGGER.info(Arrays.toString(cutecgdata12));
        for(int m = 0; m < 12;m ++)
        {
            for(int n = 0;n < 20000;n ++)
            {
                height[m][0] = Math.min(height[m][0], ecgdata12[n][m]);
                height[m][1] = Math.max(height[m][1], ecgdata12[n][m]);
                height[m][2] = height[m][1] - height[m][0];
            }
        }
        for(j = 0;j < 12;j ++)
        {
            List<Point> pointArray = new ArrayList<Point>();
            if(0 != j)
            {
                ht = ht + 15 * pixcell;
                //ht = ht + 110 + 5 * pixcell;
            }
            for(i = 0; i < 20000; i ++)
            {
                Point point = new Point();
                point.setX(String.format("%.2f", perpix * i + 2*(wt/3)));
                point.setY(String.format("%.2f", ecgdata12[i][j] + ht));
                pointArray.add(point);
            }
            resultObj.put("ecgdata" + (j + 1),pointArray);
        }
        return resultObj.toString();
    }

    @GetMapping ("/CasesSearch/getCdg")
    @ResponseBody
    public Cdg findCdgByTestId(@RequestParam("testId") String testId)
    {
        return cdgService.findByTestId(testId);
    }
    @GetMapping ("/CasesSearch/hadoop")
    @ResponseBody
    public ResponseMessage hadoop(@RequestParam("threadId") String threadId) throws IOException, InterruptedException {

        String inputDir = FileConstants.HADOOP_INPUT_DIR + threadId;
        String outputDir = FileConstants.HADOOP_OUTPUT_DIR + threadId;
        FileOperations.makeDir(outputDir);
        //创建调用Python脚本的Shell命令
        StringBuilder runHadoop = new StringBuilder();
        runHadoop.append("python cardio.py ").append(inputDir).append(" ").append(outputDir);
        //执行Shell命令，并等待程序运行完成
        Process process = Runtime.getRuntime().exec(runHadoop.toString());
        process.waitFor();
        ResponseMessage response = storeCDG(inputDir, outputDir);

        return response;

    }
    private ResponseMessage storeCDG(String inputDir, String outputDir) throws IOException {
        List<File> cdgFiles = listHadoopOutputFile(outputDir);
        if (cdgFiles.isEmpty()) {
            return new ResponseMessage(ErrorCode.GENERATE_CDG_ERROR, "Hadoop generate CDG error.");
        }

        List<String> errorCDG = new ArrayList<String>();
        List<String> cdgArray = new ArrayList<String>();
        String cdgResults = null;
        for (File file : cdgFiles) {
            //Hadoop的输出文件中，如果成功则生成文件_SUCCESS，内容为空，因此跳过
            if (file.getName().equals(HADOOP_SUCCESS)) {
                continue;
            }
            //LOGGER.info("Starting calculate the cdg.");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            // 文件名为住院号_测试号{.txt}的形式，利用正则表达式以 _和. 为分隔符，分割文件名信息
            String[] fileNameStrings = reader.readLine().trim().split("[_\\.]");
            String[] params = reader.readLine().trim().split("[\\s+]");

            // 将三维的CDG数据转成可被javascript解析的json字符串
            String line;
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            while ((line = reader.readLine()) != null) {
                builder.append("[");
                builder.append(line.trim().replaceAll("\\s+", COMMA));
                builder.append("],");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("]");
            String admissionnumber = fileNameStrings[0].length() >= 7 ? fileNameStrings[0].substring(0, 7) : fileNameStrings[0];
            //LOGGER.info("admissionnumber= {}.", admissionnumber);
            int patient_id = patientsService.findId(admissionnumber);
            //LOGGER.info("patient_id {}.", patient_id);
            String testId = fileNameStrings[1];
            //LOGGER.info("testId {}.", testId);
            int ecg_id=ecgService.findId(patient_id,testId);
            //LOGGER.info("ecg_id {}.", ecg_id);
            String cdgData = builder.toString();
            double thi = Double.parseDouble(params[0]);
            double shi = Double.parseDouble(params[1]);
            double di = Double.parseDouble(params[2]);
            int result_id = diagnosis(thi, shi, di);
            if(result_id==0)
            {
                cdgResults="阴性";
            }
            else
            {
                if(result_id==2)
                {
                    cdgResults="阳性";
                }
                else
                {
                    cdgResults="可疑阳性";
                }
            }
            try {
                //LOGGER.info("Updateing the cdg,ecg_Id = {}, testId = {}, cdgResults = {}, paraFft = {}, paraLya = {}.",ecg_id, testId, cdgResults, thi, shi);
                Cdg cdg = new Cdg(ecg_id,patient_id,cdgData,thi, shi,di, result_id);
                //cdgDAO.update(cdg);
                cdgArray.add(cdgData);
                //LOGGER.info("Updated the cdg.");
            } catch (Exception e) {
               // LOGGER.info("Fail to update the cdg.");
                errorCDG.add(file.getName());
            }

            reader.close();
        }

        //清空Hadoop 输入文件夹和输出文件夹。
       // LOGGER.info("Cleaning the temp ECG files in {}.", inputDir);
        FileOperations.clearDirectory(inputDir);
        //LOGGER.info("Cleaned the temp ECG files in {}.", inputDir);
        //LOGGER.info("Cleaning the temp CDG files in {}.", outputDir);
        FileOperations.clearDirectory(outputDir);
        //LOGGER.info("Cleaned the temp CDG files in {}.", outputDir);

        return new StoreCDGResponseMessage(errorCDG, cdgArray, cdgResults, ErrorCode.SUCCESS,
                "Storing CDG files in" + outputDir + " finished.");
    }
    private List<File> listHadoopOutputFile(String dirPath) {
        File directory = new File(dirPath);
        List<File> files = new ArrayList<File>();
        boolean isSuccess = false;

        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.getName().equals(HADOOP_SUCCESS)) {
                    isSuccess = true;
                }
                files.add(file);
            }
        } else {
            //LOGGER.error("List files from {} error.", dirPath);
        }
        if (!isSuccess) {
           // LOGGER.error("Calculate CDG through Hadoop error.");
        }

        return files;
    }
    private int diagnosis(double paraFft, double paraLya, double paraAll) {
        double LeftToCenter = (-1) * (9 * 0 - 5000 * 0.4 + 459) / Math.sqrt(9 * 9 + 5000 * 5000);
        double DistantOfTwoLine = (-1) * (9 * 198 - 5000 * 0.46 + 459) / Math.sqrt(9 * 9 + 5000 * 5000);
        double Condition = DistantOfTwoLine * 10 / LeftToCenter;
        if (paraAll > Condition) {
            return 2;
        } else if (paraAll < ((-1) * Condition)) {
            return 0;
        } else {
            return 1;
        }
    }
}

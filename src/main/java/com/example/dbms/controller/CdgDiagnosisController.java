package com.example.dbms.controller;

import com.example.dbms.api.ErrorCode;
import com.example.dbms.api.ResponseMessage;
import com.example.dbms.api.StoreCDGResponseMessage;
import com.example.dbms.api.UploadFileResponseMessage;
import com.example.dbms.domain.po.*;
import com.example.dbms.service.*;
import com.example.dbms.util.FileConstants;
import com.example.dbms.util.FileOperations;
import com.example.dbms.util.Xml2Txt;
import com.example.dbms.util.xml2object;
import jwave.Transform;
import jwave.transforms.AncientEgyptianDecomposition;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.wavelets.coiflet.Coiflet4;
import jwave.transforms.wavelets.haar.Haar1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class CdgDiagnosisController {
    private static final int ECG_LEADS_SIZE = 12;
    private static final String COMMA = ",";
    private static final String HADOOP_SUCCESS = "_SUCCESS";
    private Logger LOGGER= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatientsService patientsService;

    @Autowired
    private EcgService ecgService;

    @Autowired
    private CdgService cdgService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private BasicCaseService basicCaseService;

    @Autowired
    private ComprehensiveCaseService comprehensiveCaseService;

    @Autowired
    private CtService ctService;

    @Autowired
    private ImagingReportService imagingReportService;

    @Autowired
    private  DiagnosisInfoService diagnosisInfoService;

    @PostMapping("/CdgDiagnosis/UploadEcg")
    @ResponseBody
    public ResponseMessage uploadFile(@RequestParam("files") MultipartFile multipartFile, @RequestParam("param1") String ecginfo,
                                      @RequestParam("param2") int surgery, @RequestParam("param3") int ecg_result) throws IOException{

        /**
         * 为了在多线程的情况下避免Hadoop输入文件夹的冲突，每一个线程根据其线程号创建一个输入文件夹。ECG
         * 再上传到此文件夹中作为Hadoop的输入文件夹。
         */
        //生成一个随机数，加在线程号号后
        String filename = multipartFile.getOriginalFilename();
        String threadUUID = Thread.currentThread().getId() + "-" + UUID.randomUUID().toString();
        String inputDir = FileConstants.HADOOP_INPUT_DIR + threadUUID + FileConstants.FILE_SEPARATOR;
        FileOperations.makeDir(inputDir);
        LOGGER.info("Received file {}.", inputDir + filename);
        LOGGER.info("Received file {},{},{}.", ecginfo,surgery,ecg_result);
        String uploadedFileLocation = inputDir + filename;
        String bakFileLocation = FileConstants.BAK_DIR + filename;
        InputStream uploadedInputStream=multipartFile.getInputStream();
        FileOperations.writeToFile(uploadedInputStream, uploadedFileLocation);
        FileOperations.writeToFile(uploadedInputStream, bakFileLocation);
        uploadedInputStream.close();

        // 如果是zip格式的压缩包，则对其解压，并在解压后删除压缩包
      if (filename.contains(FileConstants.ZIP_FILE_SUFFIX)) {
            FileOperations.unzip(inputDir, filename);
            FileOperations.deleteFile(inputDir + filename);
        }
//		else if(filename.contains(FileConstants.XML_FILE_SUFFIX)){
//			Xml2Txt.xml2txt(uploadedFileLocation);
//			FileOperations.deleteFile(uploadedFileLocation);
//			filename = filename.substring(0, filename.indexOf(".", 2)) + ".txt";
//		}

        //批量转换xml为txt
        List<File> ecgFiles = FileOperations.listFile(inputDir);
        for (File file : ecgFiles) {
            String tmp_filename = file.getName(); //临时文件名
            //将xml文件转换为txt文件
            if(tmp_filename.contains(FileConstants.XML_FILE_SUFFIX)){
                String xmlinputfile = inputDir + tmp_filename;
                Xml2Txt.xml2txt(xmlinputfile);
                FileOperations.deleteFile(xmlinputfile);
            }
        }
        //写入ecg数据
       List<String> errorECG = null;
        errorECG = storeECG(inputDir,surgery,ecg_result,ecginfo);///*怎么判断是ECG数据

        //写入未计算的cdg数据
        ecgFiles.clear();
        ecgFiles = FileOperations.listFile(inputDir);
        for (File file : ecgFiles) {
            String[] fileNameStrings = file.getName().split("[_\\.]");
            String admissionnumber = fileNameStrings[0].length() >= 7 ? fileNameStrings[0].substring(0, 7) : fileNameStrings[0];
            int patient_id = patientsService.findPatient_id(admissionnumber);
            String test_id = fileNameStrings[1];
            int ecg_id =ecgService.findId(patient_id,test_id);
            List ecg_ids=cdgService.findByPatientId(patient_id);
            String cdg_data = threadUUID; ///*数据为什么是线程Id
            double thi = 0.0;
            double shi = 0.0;
            double di = 0.0;
            int result_id = 3;
            LOGGER.info("Storing filename={}, testid={} Not-Calculated CDG data into MySQL.", file.getName(), test_id);
            if(ecg_ids.contains(ecg_id))
            {
                try {
                    cdgService.updateCdg(ecg_id,patient_id, cdg_data, thi, shi, di,result_id);
                    LOGGER.info("Stored1 Not-Calculated CDG data into MySQL.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    LOGGER.info("Fail1 to store Not-Calculated CDG data into MySQL.");
                }
            }
            else
            {
                try {
                    Cdg cdg = new Cdg(ecg_id,patient_id, cdg_data, thi, shi, di,result_id);
                    cdgService.insertCdg(cdg);
                    LOGGER.info("Stored Not-Calculated CDG data into MySQL.");
                } catch (Exception e) {
                    LOGGER.info("Fail to store Not-Calculated CDG data into MySQL.");
                }
            }

        }

        return new UploadFileResponseMessage(threadUUID, errorECG, ErrorCode.SUCCESS,
                "Upload file " + multipartFile.getOriginalFilename() + " successfully.");
    }
    /**
     * 将对应输入目录中的ECG文件存进数据库中
     *
     * @param inputDir
     * @return 无法存入数据库的ECG文件名
     * @throws IOException
     */
    private List<String> storeECG(String inputDir,int surgery,int ecg_result,String ecg_info) throws IOException {
        List<File> ecgFiles = FileOperations.listFile(inputDir);
        List<String> errorEcg = new ArrayList<String>();
        for (File file : ecgFiles) {
            // fileNameStrings[0]是住院号，fileNameStrings[1]是测试号，fileNameStrings[2]是文件名后缀[如果有的话]。
            String[] fileNameStrings = file.getName().split("[_\\.]");
            String admissionnumber = fileNameStrings[0].length() >= 7 ? fileNameStrings[0].substring(0, 7) : fileNameStrings[0];
            int patient_id= patientsService.findPatient_id(admissionnumber);
            String test_id = fileNameStrings[1];
            String ecg_date=test_id.substring(0,8);
            List test_ids=ecgService.findTest_id(patient_id);
            LOGGER.info("ECG1( patient_id:{}).",test_ids.contains(test_id));
            if(test_ids.contains(test_id))
            {
                String ecg_data = convertECGToJsonString(file);
                LOGGER.info("ECG2( testId:{}).", test_id);
                try {
                    LOGGER.info("Storing2 ECG data into MySQL in storeECG.");
                    int id=ecgService.findId(patient_id,test_id);
                    ecgService.updateEcg(id,ecg_data,surgery,ecg_result,ecg_info);
                    LOGGER.info("Stored2 ECG data into MySQL in storeECG.");
                } catch (Exception e) {
                    //System.out.println(e.getMessage());
                    LOGGER.info("Failed2 to store ECG data into MySQL in storeECG.");
                    errorEcg.add(file.getName());
                }
            }
            else{
                String ecg_data = convertECGToJsonString(file);
                LOGGER.info("ECG1( testId:{}, patientId:{}).", test_id, patient_id);
                try {
                    LOGGER.info("Storing1 ECG data into MySQL in storeECG.");
                    LOGGER.info("Storing1 ECG {},{},{},{},{},{}.",patient_id,test_id,ecg_result,ecg_info,surgery,ecg_date);
                    ecgService.insertEcg(new Ecg(patient_id,test_id,ecg_data,ecg_result,ecg_info,surgery,ecg_date));

                    LOGGER.info("Stored1 ECG data into MySQL in storeECG.");
                } catch (Exception e) {
                    LOGGER.info("Failed1 to store ECG data into MySQL in storeECG.");
                    errorEcg.add(file.getName());
                }
            }
        }
        return errorEcg;
    }
    /**
     * ECG文件的格式为每行分别对应12导联的一个时刻的幅值，该函数将ECG文件转为javascript可解释的JSON格式
     * [[1],[2],...,[12]]
     * @param ecgFile
     *            ECG文件对象
     * @return ECG数据JSON字符串
     * @throws IOException
     */
    private String convertECGToJsonString(File ecgFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ecgFile));
        ArrayList<StringBuilder> ecgLeads = new ArrayList<StringBuilder>();
        ArrayList<StringBuilder> ecgLeadsFilter = new ArrayList<StringBuilder>();
        StringBuilder ecgJson = new StringBuilder();
        String line;
//		int l = 12;//长度为2^13
//		int datalength = (int)Math.pow(2, l);
//		double[] cutecgdata12 = new double[datalength];
//		double[] cutecgdata12_temp = new double[datalength];
//		double[][] ecgdata12 = new double[datalength][ECG_LEADS_SIZE];

        for (int i = 0; i < ECG_LEADS_SIZE; i++) {
            ecgLeads.add(new StringBuilder());
            ecgLeads.get(i).append("[");
        }

        for (int i = 0; i < ECG_LEADS_SIZE; i++) {
            ecgLeadsFilter.add(new StringBuilder());
            ecgLeadsFilter.get(i).append("[");
        }

        while ((line = reader.readLine()) != null) {
            String[] leads = line.trim().split("\\s+");
            if (leads.length != ECG_LEADS_SIZE) {
                throw new IOException("ECG file format error, it is not the 12 leads ecg format.");
            }
            for (int i = 0; i < leads.length; i++) {
                ecgLeads.get(i).append(String.format("%.4f", Double.parseDouble(leads[i]))).append(COMMA);
            }
        }

        //获取数据长度
        String[] temp_row = ecgLeads.get(0).delete(0, 1).toString().split(COMMA);
        int datalength = temp_row.length;
        double[] cutecgdata12 = new double[datalength];
        double[] cutecgdata12_temp = new double[datalength];
        double[][] ecgdata12 = new double[datalength][ECG_LEADS_SIZE];


        for(int j = 0; j < ECG_LEADS_SIZE;j++){
            String[] row = ecgLeads.get(j).delete(0, 1).toString().split(COMMA);

            for(int i = 0;i < datalength;i++)
            {
                cutecgdata12[i] = Double.parseDouble(row[i]);
            }

            cutecgdata12_temp = midfilter(cutecgdata12, 200);
            cutecgdata12_temp = midfilter(cutecgdata12_temp, 600);

            for (int i = 0; i < datalength; i++){
                cutecgdata12[i] = cutecgdata12[i] - cutecgdata12_temp[i];
            }

            double[] arrHilb= dwt( cutecgdata12, 7);
            double thr=ddencmp(cutecgdata12);
            double[] arrReco=wdencmp(arrHilb, thr, 7);

            for(int i = 0;i < datalength;i++)
            {
                ecgdata12[i][j] = arrReco[i];
            }
        }

        reader.close();

        for(int j=0; j < ECG_LEADS_SIZE; j++){
            for(int i=0; i < datalength; i++){
                ecgLeadsFilter.get(j).append(String.format("%.4f",ecgdata12[i][j])).append(COMMA);
            }
        }

        ecgJson.append("[");

        for (int i = 0; i < ECG_LEADS_SIZE; i++) {
            ecgLeadsFilter.get(i).deleteCharAt(ecgLeadsFilter.get(i).length() - 1).append("]");
            ecgJson.append(ecgLeadsFilter.get(i)).append(COMMA);
        }

        ecgJson.deleteCharAt(ecgJson.length() - 1).append("]");
        return ecgJson.toString();
    }
    /**
     * midfilter滤波
     * [[1],[2],...,[12]]
     * @param ecgArrCut：滤波数据，n：窗口宽度
     *            ECG文件对象
     * @return ECG数据
     * @throws
     */
    private double[] midfilter(final double[] ecgArrCut,int n){
        //注意：不能对ecgArrCut做原地修改
        int tmpnum = n/2;
        double[] tempdata = new double[ecgArrCut.length];
        double[] tempdataecg = new double[ecgArrCut.length + n + 1]; //+1是为了适应n为奇数
        for(int i = tmpnum; i < ecgArrCut.length + tmpnum; i++){
            tempdataecg[i] = ecgArrCut[i - tmpnum];
        }
        for (int p = tmpnum; p < ecgArrCut.length + tmpnum; p++){
            double[] arrSelect = new double[n];

            for (int q = 0; q < n ; q++){
                arrSelect[q] = tempdataecg[p + q - tmpnum];
            }
            Arrays.sort(arrSelect);
            if((n & 1) == 0){
                tempdata[p - tmpnum] = (arrSelect[tmpnum]+arrSelect[tmpnum+1])/2; //n为偶数
            }else{
                tempdata[p - tmpnum] = arrSelect[tmpnum + 1];//n为奇数
            }

        }
        return tempdata;
    }
    /**
     * dwt
     *
     * @param
     *
     * @return
     * @throws
     */

    private double[] dwt(double[] signal , int J){
        Transform t = new Transform(
                new AncientEgyptianDecomposition(
                        new FastWaveletTransform(new Coiflet4( ))));
        double[] dwt_output=t.forward(signal);
        return dwt_output;
    }
    /**
     * dwt
     *
     * @param
     *
     * @return
     * @throws
     */
    //包jwave
    private double ddencmp(double[] signal){
        int n=signal.length;
        double thr=Math.sqrt(2.*Math.log(n));
        Transform t = new Transform(
                new AncientEgyptianDecomposition(
                        new FastWaveletTransform(new Haar1( ))));

        double[] arrHilb=t.forward(signal);
        double[] det_sig=new double[arrHilb.length/2];
        System.arraycopy(arrHilb, arrHilb.length/2, det_sig, 0, arrHilb.length/2);
        for (int i = 0; i < det_sig.length; i++){
            det_sig[i]=Math.abs(det_sig[i]);
        }
        Arrays.sort(det_sig);
        double normaliz = 0;
        if (det_sig.length % 2 != 0)
        {
            normaliz = det_sig[(det_sig.length - 1) / 2];
        }
        else
        {
            normaliz = (det_sig[(det_sig.length - 2) / 2] + det_sig[det_sig.length/ 2]) / 2;
        }

        thr = thr*normaliz / 0.6745;
        return thr;
    }


    /**
     * dwt
     *
     * @param
     *
     * @return
     * @throws
     */

    private double[] wdencmp(double[] data, double thr,int n){
        double[] cxc=new double[data.length];
        System.arraycopy(data, 0, cxc, 0, data.length);

        for (int i= (int)Math.pow(2, 4); i < (int)Math.pow(2, 5); i++){
            cxc[i]=0;
        }

        for (int i=(int)Math.pow(2, 5); i<data.length; i++){
            if (Math.abs(data[i])< thr){
                cxc[i]=0;
            }else if(data[i]<0){
                cxc[i]=thr-Math.abs(data[i]);
            }
            else {
                cxc[i]=Math.abs(data[i])-thr;
            }
        }

        Transform t = new Transform(
                new AncientEgyptianDecomposition(
                        new FastWaveletTransform(new Coiflet4( ))));
        double[] arrReco=t.reverse(cxc);
        return arrReco;
    }
    @PostMapping("/api/ecg/dataUpload")
    @ResponseBody
    public ResponseMessage dataupload(@RequestParam("username") String username,
                                      @RequestParam("password") String password, @RequestParam("file") MultipartFile multipartFile
    ) throws IOException {
        Users users = usersService.findByUsername(username);
        if (users == null) {
            return new ResponseMessage(ErrorCode.QUERY_DATA_ERROR, "username error.");
        }
        if (!users.getPassword().equals(password)) {
            return new ResponseMessage(ErrorCode.QUERY_DATA_ERROR, "password error.");
        }
        String zipFilePreix = UUID.randomUUID().toString();
        String threadUUID = Thread.currentThread().getId() + "-" + zipFilePreix;
        String inputDir = FileConstants.HADOOP_INPUT_DIR + threadUUID + FileConstants.FILE_SEPARATOR;
        FileOperations.makeDir(inputDir);
        String filename = multipartFile.getOriginalFilename().substring(0, multipartFile.getOriginalFilename().length() - 4) + ".zip";
        String zipFileName = zipFilePreix + ".zip";
        String uploadedFileLocation = inputDir + zipFileName;
        InputStream uploadedInputStream=multipartFile.getInputStream() ;
        FileOperations.zipwriteToFile(uploadedInputStream, filename, uploadedFileLocation);
        FileOperations.unzipfile(inputDir, zipFileName);
        FileOperations.deleteFile(inputDir + zipFileName);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());
        String bakFileLocation = FileConstants.BAK_DIR + dateStr;
        File bakFile = new File(bakFileLocation);
        if(!bakFile.exists()) {
            FileOperations.makeDir(bakFileLocation);
        }
        FileOperations.copy(inputDir, bakFileLocation);
        // 如果是zip格式的压缩包，则对其解压，并在解压后删除压缩包
        List<File> listFiles = FileOperations.listFile(inputDir);
        for(File file : listFiles) {
            filename = file.getName();
            if (filename.contains(FileConstants.ZIP_FILE_SUFFIX)) {
                //FileOperations.unzip(inputDir, filename);
                FileOperations.decompressZip(inputDir+filename,inputDir);
                FileOperations.deleteFile(inputDir + filename);
            }
        }
        List<File> Files = FileOperations.walk(inputDir).files;
        // 循环插入病例文件
        Iterator<File> iter = Files.iterator();
        String ecg_info=null;
        while(iter.hasNext()) {
            File caseFile = iter.next();
            String case_filename = caseFile.getName(); // 临时文件名
            if (case_filename.contains(FileConstants.XML_FILE_SUFFIX)) {
                LOGGER.info("Starting insert");
                String caseinputfile = caseFile.toString();
                xml2object myxml2object = new xml2object();
                Patients myPatient = myxml2object.xml2Patient(caseinputfile);
                if (myPatient.getAdmissionnumber() != null) {
                    Patients p = patientsService.findPatients(myPatient.getAdmissionnumber());
                    LOGGER.info("Admissionnumber {}.",myPatient.getAdmissionnumber());
                    if (p == null) {
                        try {
                            LOGGER.info("Starting insert Patients.");
                            patientsService.insertPatients(myPatient);
                            int patientId = patientsService.findId(myPatient.getAdmissionnumber());
                            Basiccase basiccase=new Basiccase(patientId,0, 0, 0, 0,null,null);
                            basicCaseService.insertBasicCase(basiccase);

                            ComprehensiveCase comprehensiveCase=new ComprehensiveCase(patientId,0,0,0,
                                    0,0,0,0,0,0,0,0,
                                    0,0,0,0,0,0,0,0);
                            comprehensiveCaseService.insertComprehensiveCase(comprehensiveCase);

                            ImagingReport imagingReport=new ImagingReport(patientId,"暂缺",0,null);
                            imagingReportService.insertImagingReport(imagingReport);

                            Ct_report ct_report=new Ct_report(patientId,"暂缺",0,null);
                            ctService.insertCt(ct_report);

                            Diagnose_info diagnose_info=new Diagnose_info(patientId,"暂缺","暂缺");
                            diagnosisInfoService.insertDiagnoseInfo(diagnose_info);

                            ecg_info = myxml2object.getEcgInfo(caseinputfile);
                            LOGGER.info("Successfully insert atients of {}.", myPatient.getAdmissionnumber());
                        } catch (Exception e) {
                            LOGGER.error("Failed insert Patients of {}.", myPatient.getAdmissionnumber());
                        }
                    } else if(p.getName().equals(p.getAdmissionnumber())){
                        try {
                            LOGGER.info("Starting alter Patients.");
                            patientsService.updatePatients(myPatient, p.getId());
                            ecg_info = myxml2object.getEcgInfo(caseinputfile);
                            //int patientId = patientsDAO.findId(myPatient.getAdmissionnumber());
                            //Cases myCase = myxml2object.xml2Case(caseinputfile, patientId);
                            //LOGGER.info("Starting alter Cases.");
                            //casesDAO.update(myCase, casesDAO.findByPatientsId(patientId).getId());
                            LOGGER.info("Successfully alter Patients of {}.", myPatient.getAdmissionnumber());
                        } catch (Exception e) {
                            LOGGER.error("Failed alter  Patients of {}.", myPatient.getAdmissionnumber());
                        }
                    } else {
                        LOGGER.info("patients {} alreadly exists.", myPatient.getAdmissionnumber());
                        ecg_info = myxml2object.getEcgInfo(caseinputfile);
                    }
                    FileOperations.deleteFile(caseinputfile);
                    iter.remove();
                }
            }
        }

		/*String path1=fileDetail.getFileName().substring(0, fileDetail.getFileName().length() - 4);
		String path=inputDir+path1+"/" ;
		List<File> ecgFiles = FileOperations.listFile(path);
		for (File file : ecgFiles) {
			String tmp_filename = file.getName(); //临时文件名
			//将xml文件转换为txt文件
			if(tmp_filename.contains(FileConstants.XML_FILE_SUFFIX)){
				String xmlinputfile = path + tmp_filename;
				Xml2Txt.xml2txt2(xmlinputfile,inputDir);
				FileOperations.deleteFile(xmlinputfile);
			}
		}*/
        List<File> ecgFiles = FileOperations.listFile(inputDir);
        for (File file : ecgFiles) {
            String tmp_filename = file.getName(); //临时文件名
            //将xml文件转换为txt文件
            if(tmp_filename.contains(FileConstants.XML_FILE_SUFFIX)){
                String xmlinputfile = inputDir + tmp_filename;
                Xml2Txt.xml2txt(xmlinputfile);
                FileOperations.deleteFile(xmlinputfile);
            }
        }
        // 删除无关的文件夹
        Files.clear();
        Files = FileOperations.walk(inputDir).dirs;
        iter = Files.iterator();
        while (iter.hasNext()) {
            FileOperations.deleteFile(iter.next());
        }
        Files.clear();
        // 插入ECG数据
        LOGGER.info("ecg_info： {}.", ecg_info);
        int ecg_result = 0;//默认正常
        if(!ecg_info.contains("正常心电图")){
            ecg_result = 1;
        }
        LOGGER.info("ecg_result： {}.", ecg_result);
        List<String> errorECG = null;
        errorECG = storeECG(inputDir,0,ecg_result,ecg_info);///*怎么判断是ECG数据

        //写入未计算的cdg数据
        ecgFiles.clear();
        ecgFiles = FileOperations.listFile(inputDir);
        HashSet test_ids=new HashSet();
        for (File file : ecgFiles) {
            //保存一个未计算的CDG进入mysql数据库
            // String tmp_filename = file.getName(); //临时文件名
            String[] fileNameStrings = file.getName().split("[_\\.]");
            String admissionnumber = fileNameStrings[0].length() >= 7 ? fileNameStrings[0].substring(0, 7) : fileNameStrings[0];
            int patient_id = patientsService.findId(admissionnumber);
            String test_id = fileNameStrings[1];
            test_ids.add(test_id);
            int ecg_id=ecgService.findId(patient_id,test_id);
            List ecg_ids=cdgService.findByPatientId(patient_id);
            String cdg_Data = threadUUID; ///*数据为什么是线程Id
            double thi = 0.0;
            double shi = 0.0;
            double di = 0.0;
            int result_id = 3;
            LOGGER.info("Storing filename={}, testid={} Not-Calculated CDG data into MySQL.", file.getName(), test_id);
            if(ecg_ids.contains(ecg_id))
            {
                try {
                    cdgService.updateCdg(ecg_id,patient_id, cdg_Data, thi, shi, di,result_id);
                    LOGGER.info("Stored1 Not-Calculated CDG data into MySQL.");
                } catch (Exception e) {
                    LOGGER.info("Fail1 to store Not-Calculated CDG data into MySQL.");
                }
            }
            else
            {
                try {
                    Cdg cdg = new Cdg(ecg_id,patient_id, cdg_Data, thi, shi, di,result_id);
                    cdgService.insertCdg(cdg);
                    LOGGER.info("Stored Not-Calculated CDG data into MySQL.");
                } catch (Exception e) {
                    LOGGER.info("Fail to store Not-Calculated CDG data into MySQL.");
                }
            }

        }
        Iterator it=test_ids.iterator();
        while(it.hasNext())
        {
            Cdg cdg=cdgService.findByTestId(it.next().toString());
            if(cdg.getResult_id()==3)
            {
                try{
                    hadoop(cdg.getCdg_data());
                }catch (InterruptedException e)
                {
                    return new ResponseMessage(ErrorCode.GENERATE_CDG_ERROR, "ERROR.");
                }
            }

        }
        return new ResponseMessage(ErrorCode.SUCCESS, "successfully.");
    }
    public ResponseMessage hadoop(String threadId) throws IOException, InterruptedException {

        String inputDir = FileConstants.HADOOP_INPUT_DIR + threadId;
        String outputDir = FileConstants.HADOOP_OUTPUT_DIR + threadId;
        LOGGER.info("Generating CDG using Hadoop0, threadId {}.", threadId);
        FileOperations.makeDir(outputDir);
        LOGGER.info("Generating CDG using Hadoop1, threadId {}.", threadId);
        //创建调用Python脚本的Shell命令
        StringBuilder runHadoop = new StringBuilder();
        LOGGER.info("Generating CDG using Hadoop2, threadId {}.", threadId);
        runHadoop.append("python cardio.py ").append(inputDir).append(" ").append(outputDir);
        LOGGER.info("Generating CDG using Hadoop3, threadId {}.", threadId);
        //执行Shell命令，并等待程序运行完成
        Process process = Runtime.getRuntime().exec(runHadoop.toString());
        LOGGER.info("Generating CDG using Hadoop, input directory {}, output directory {}.", inputDir, outputDir);
        process.waitFor();
        LOGGER.info("Generated CDG using Hadoop, input directory {}, output directory {}.", inputDir, outputDir);

        //存储CDG及相关数据，storeCDG函数在后面有定义
        LOGGER.info("Storing CDG data into MySQL..");
        ResponseMessage response = storeCDG(inputDir, outputDir);
        LOGGER.info("Stored CDG data into MySQL..");

        return response;

    }
    /**
     * 解析Hadoop程序计算出来的CDG文件，其中第一行为对应的ECG文件名，第二行为指标参数{Para_fft，Para_lya，Para_all}
     * , 最后的三行为CDG的三维数组数据。
     *
     * @param outputDir
     *            hadoop输出文件夹
     * @return 返回相应信息类
     * @throws IOException
     */
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
            LOGGER.info("Starting calculate the cdg.");
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
            LOGGER.info("admissionnumber= {}.", admissionnumber);
            int patient_id = patientsService.findId(admissionnumber);
            LOGGER.info("patient_id {}.", patient_id);
            String testId = fileNameStrings[1];
            LOGGER.info("testId {}.", testId);
            int ecg_id=ecgService.findId(patient_id,testId);
            LOGGER.info("ecg_id {}.", ecg_id);
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
                LOGGER.info("Updateing the cdg,ecg_Id = {}, testId = {}, cdgResults = {}, paraFft = {}, paraLya = {}.",ecg_id, testId, cdgResults, thi, shi);
                cdgService.updateCdg(ecg_id,patient_id,cdgData,thi, shi,di, result_id);
                cdgArray.add(cdgData);
                LOGGER.info("Updated the cdg.");
            } catch (Exception e) {
                LOGGER.info("Fail to update the cdg.");
                errorCDG.add(file.getName());
            }

            reader.close();
        }

        //清空Hadoop 输入文件夹和输出文件夹。
        LOGGER.info("Cleaning the temp ECG files in {}.", inputDir);
        FileOperations.clearDirectory(inputDir);
        LOGGER.info("Cleaned the temp ECG files in {}.", inputDir);
        LOGGER.info("Cleaning the temp CDG files in {}.", outputDir);
        FileOperations.clearDirectory(outputDir);
        LOGGER.info("Cleaned the temp CDG files in {}.", outputDir);

        return new StoreCDGResponseMessage(errorCDG, cdgArray, cdgResults, ErrorCode.SUCCESS,
                "Storing CDG files in" + outputDir + " finished.");
    }
    /**
     * 返回hadoop输出文件夹的文件列表
     *
     * @param dirPath
     *            输出目录
     * @return
     */
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
            LOGGER.error("List files from {} error.", dirPath);
        }
        if (!isSuccess) {
            LOGGER.error("Calculate CDG through Hadoop error.");
        }

        return files;
    }
    /**
     * 根据CDG得出来的指标参数得出诊断结果
     *
     * @param paraFft
     *            量化指标
     * @param paraLya
     *            量化指标
     * @param paraAll
     *            根据上面两个得出的判断指标
     * @return
     */
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


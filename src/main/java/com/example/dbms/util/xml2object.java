package com.example.dbms.util;



import com.example.dbms.domain.po.Cases;
import com.example.dbms.domain.po.Patients;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class xml2object {

	public xml2object(){}
	//获取patient对象
	public Patients xml2Patient(final String xmlfile){

		Patients myPatient = null;

		try {
			File f = new File(xmlfile);
			int index = xmlfile.indexOf(".");
			String outfile = xmlfile.substring(0, index)+".txt";
			File fout = new File(outfile);
			if(!fout.exists()){
				fout.createNewFile();
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);

			//获取患者姓名
			String name = null;
			NodeList namenode = doc.getElementsByTagName("PatName");
			if(namenode.getLength() > 0){
				name = namenode.item(0).getFirstChild().getNodeValue();
			}

			//获取患者性别
			NodeList sex = doc.getElementsByTagName("Gender");
			String sexvalue = "男";
			if(sex.getLength() > 0){
				String str = sex.item(0).getFirstChild().getNodeValue();
				if(str.equals("2")){
					sexvalue = "女";
				}
			}

			//获取患者年龄
			int age = 0;
			NodeList agenode = doc.getElementsByTagName("PatientAge");
			if(agenode.getLength() > 0){
				age = Integer.parseInt(agenode.item(0).getFirstChild().getNodeValue());
			}

			//获取患者的病历号
			String Id = null;
			NodeList Idnode = doc.getElementsByTagName("HISpatientID");
			if(Idnode.getLength() > 0){
				String temp = Idnode.item(0).getFirstChild().getNodeValue();
				Id = temp.substring(0, 7);//0-6?
			}
			String birth_place=null;
			NodeList birth_placenode = doc.getElementsByTagName("Address");
			if(birth_placenode.getLength()>0)
			{
				birth_place = birth_placenode.item(0).getFirstChild().getNodeValue();
			}
			String time=null;
			NodeList timenode = doc.getElementsByTagName("CreateDate");
			if(timenode.getLength()>0)
			{
				String createTime = timenode.item(0).getFirstChild().getNodeValue();
				time=createTime.substring(0,10);
			}
			String ownner="北京阜外医院";
			NodeList ownnerNode = doc.getElementsByTagName("HospitalName");

			if(ownnerNode.getLength()>0)
			{
				String hospital= ownnerNode.item(0).getFirstChild().getNodeValue();
				if(hospital.contains("石河子"))
				{
					ownner="石河子医院";
				}
			}

			//此处修改，待core 完成后添加20181102
			myPatient = new Patients(name, sexvalue,age,Id,57,0,birth_place,time,time,ownner);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return myPatient;
	}

	public  String getEcgInfo(final String xmlfile)
	{
		String ECGResult = null;
		try {
			File f = new File(xmlfile);
			int index = xmlfile.indexOf(".");
			String outfile = xmlfile.substring(0, index)+".txt";
			File fout = new File(outfile);
			if(!fout.exists()){
				fout.createNewFile();
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);

			//获取患者的ECG结果
			NodeList ECGnode = doc.getElementsByTagName("RepDiagnose");
			if(ECGnode.getLength() > 0){
				ECGResult = ECGnode.item(0).getFirstChild().getNodeValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ECGResult;
	}

	public Cases xml2Case(final String xmlfile, int patientId){
		Cases myCase = null;
		try {
			File f = new File(xmlfile);
			int index = xmlfile.indexOf(".");
			String outfile = xmlfile.substring(0, index)+".txt";
			File fout = new File(outfile);
			if(!fout.exists()){
				fout.createNewFile();
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);

			//获取患者的测试日期
			String hosttime = null;
			NodeList datenode = doc.getElementsByTagName("RepCreateDate");
			if(datenode.getLength() > 0){
				String[] tepdate = datenode.item(0).getFirstChild().getNodeValue().split(" ");
				hosttime = tepdate[0];
			}

			//获取患者的ECG结果
			NodeList ECGnode = doc.getElementsByTagName("RepDiagnose");
			String ECGResult = null;
			if(ECGnode.getLength() > 0){
				ECGResult = ECGnode.item(0).getFirstChild().getNodeValue();
			}
			//ECG诊断结果
			int ecgtag = 1;//默认正常
			if(!ECGResult.contains("正常心电图")){
				ecgtag = 2;
			}


			//获取患者的入院初诊
			String ClincDiagnose = null;
			NodeList Clincnode = doc.getElementsByTagName("ClincDiagnose");
			if(Clincnode.getLength() > 0){
				ClincDiagnose = Clincnode.item(0).getFirstChild().getNodeValue();
			}
			String ct = "暂缺";
			int ctTag = 0;
			String complaint = "暂缺";
			String radiography = "暂缺";
			int radiographyTag = 0;
			String radiographyTime = "暂缺";
			String ctTime = "暂缺";
			String dischargedTime = " ";
			String remarks = "暂缺";
			int disease = 0;//默认心肌缺血0

		    /*myCase = new Cases(patientId, ClincDiagnose, ECGResult, ecgtag, ct, ctTag, complaint,
					radiography, radiographyTag, hosttime, radiographyTime, ctTime,
					dischargedTime, remarks, disease);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myCase;
	}

}
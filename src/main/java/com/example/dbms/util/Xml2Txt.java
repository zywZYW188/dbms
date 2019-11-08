package com.example.dbms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Xml2Txt {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileOperations.class);
	public static void xml2txt(final String xmlfile){
		try {
			File f = new File(xmlfile);
			LOGGER.error("intfile {}.", xmlfile);
			int index = xmlfile.indexOf(".", 2);//这里是为了解决linux系统下./的问题
			String outfile = xmlfile.substring(0, index)+".txt";
			LOGGER.error("outfolder {}.", outfile);
			File fout = new File(outfile);
			if(!fout.exists()){
				fout.createNewFile();
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			LOGGER.error("reslove {} xml.", xmlfile);
			NodeList nl = doc.getElementsByTagName("digits");




			int lencol = 0;
			int lenrow = 0;
			if(nl.getLength() > 0){
				lencol = nl.item(0).getFirstChild().getNodeValue().split(" ").length;
				lenrow = nl.getLength()/2;
			}
			String[][] strsub = new String[lenrow][lencol];
			for (int i = 0; i < nl.getLength()/2; i++) {
				String str = nl.item(i).getFirstChild().getNodeValue();
				strsub[i] = str.split(" ");
			}
			//降采样到500HZ
			String[][] temp = new String[lenrow][lencol/2];
			for (int i = 0; i < lenrow; i++)
				for (int j = 0; j < lencol/2; j++){
					temp[i][j] = strsub[i][j * 2];
				}

			//写入到文件中
			PrintWriter pw=new PrintWriter(new FileWriter(fout));
			LOGGER.error("Starting to write data to {}.", outfile);
//		   int tempstart = 0;
//		   int tempstop = lencol/2;
//		   if (lencol/2 > 10000){
//			   tempstart = 501;
//			   tempstop = lencol/2 - 500 + 1;
//		   }

			for (int i = 0; i < lencol/2; i++){
				for (int j = 0; j < lenrow; j++){
					pw.write(temp[j][i]);
					pw.write(" ");
				}
				pw.write("\n");
			}
			LOGGER.error("Fished data to {}.", outfile);
			//关闭文件
			pw.close();
			LOGGER.error("close xml {}.", xmlfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void xml2txt2(final String xmlfile, final String inputDir) {
		try {
			File f = new File(xmlfile);
			LOGGER.info("intfile {}.", xmlfile);
			int left = xmlfile.lastIndexOf("/");
			int right = xmlfile.indexOf(".", 2);// 这里是为了解决linux系统下./的问题
			String outfile = inputDir + xmlfile.substring(left + 1, right) + ".txt";
			LOGGER.info("outfolder {}.", outfile);
			File fout = new File(outfile);
			if (!fout.exists()) {
				fout.createNewFile();
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			LOGGER.info("reslove {} xml.", xmlfile);
			NodeList nl = doc.getElementsByTagName("digits");
			int lencol = 0;
			int lenrow = 0;
			if (nl.getLength() > 0) {
				lencol = nl.item(0).getFirstChild().getNodeValue().split(" ").length;
				lenrow = nl.getLength() / 2;
			} else {
				return;
			}
			String[][] strsub = new String[lenrow][lencol];
			for (int i = 0; i < nl.getLength() / 2; i++) {
				String str = nl.item(i).getFirstChild().getNodeValue();
				strsub[i] = str.split(" ");
			}
			// 降采样到500HZ
			String[][] temp = new String[lenrow][lencol / 2];
			for (int i = 0; i < lenrow; i++)
				for (int j = 0; j < lencol / 2; j++) {
					temp[i][j] = strsub[i][j * 2];
				}

			// 写入到文件中
			PrintWriter pw = new PrintWriter(new FileWriter(fout));
			LOGGER.info("Starting to write data to {}.", outfile);
			// int tempstart = 0;
			// int tempstop = lencol/2;
			// if (lencol/2 > 10000){
			// tempstart = 501;
			// tempstop = lencol/2 - 500 + 1;
			// }
			for (int i = 0; i < lencol / 2; i++) {
				for (int j = 0; j < lenrow; j++) {
					pw.write(temp[j][i]);
					pw.write(" ");
				}
				pw.write("\n");
			}
			LOGGER.info("Fished data to {}.", outfile);
			// 关闭文件
			pw.close();
			LOGGER.info("close xml {}.", xmlfile);
		} catch (Exception e) {
			LOGGER.error("Fail to write data to {}.", xmlfile);
			e.printStackTrace();
		}
	}
}
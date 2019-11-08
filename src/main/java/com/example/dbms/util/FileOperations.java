package com.example.dbms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileOperations {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileOperations.class);

	public static String readFile(String filePath) {
		File file = new File(filePath);
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String tempContent;
			while ((tempContent = bufferedReader.readLine()) != null) {
				stringBuilder.append(tempContent);
				stringBuilder.append("\n");
			}
		} catch (Exception exception) {
			LOGGER.error("Read file {} error.", filePath);
			exception.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ioException) {
					LOGGER.error("Close bufferedReader error.");
					ioException.printStackTrace();
				}
			}
		}

		return stringBuilder.toString();
	}

	public static String loadECG(File file) {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String tempContent;
			while ((tempContent = bufferedReader.readLine()) != null) {
				stringBuilder.append(tempContent);
				stringBuilder.append(";");
			}
		} catch (Exception exception) {
			LOGGER.error("Read file {} error.", file.getName());
			exception.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ioException) {
					LOGGER.error("Close bufferedReader error.");
					ioException.printStackTrace();
				}
			}
		}

		return stringBuilder.toString();
	}

	public static List<File> listFile(String dirPath) {
		File directory = new File(dirPath);
		List<File> files = new ArrayList<File>();
		/*
		 * if (directory.isFile()) { files.add(directory); return files; } else
		 * if (directory.isDirectory()) { File[] fileArr =
		 * directory.listFiles(); for (int i = 0; i < fileArr.length; i++) {
		 * files.addAll(listFile(fileArr[i].getName())); } }
		 */
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				files.add(file);
			}
		} else {
			LOGGER.error("List files from {} error.", dirPath);
		}
		return files;
	}

	public static void saveFile(String filename, String content) {
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			bufferedWriter.write(content);
			LOGGER.info("Write file {} successfully", filename);
		} catch (IOException ioException) {
			LOGGER.error("Write file {} error.", filename);
			ioException.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException ioException) {
					LOGGER.error("Close bufferedWriter error.");
					ioException.printStackTrace();
				}
			}
		}
	}

	public static void makeDir(String directory) {
		File dir = new File(directory);
		if (!dir.exists()) {
			if (dir.mkdirs()) {
				LOGGER.info("Create directory: {}", directory);
			} else {
				LOGGER.error("Create directory {} error.", directory);
			}
		}
	}

	public static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
		int read;
		final int BUFFER_LENGTH = 1024;
		final byte[] buffer = new byte[BUFFER_LENGTH];
		OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
		while ((read = uploadedInputStream.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		out.flush();
		out.close();
	}
	public static boolean decompressZip(String zipPath, String descDir) {
		File zipFile = new File(zipPath);
		boolean flag = false;
		File pathFile = new File(descDir);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		ZipFile zip = null;
		try {
			zip = new ZipFile(zipFile, Charset.forName("gbk"));//防止中文目录，乱码
			for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
				ZipEntry entry = (ZipEntry)entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zip.getInputStream(entry);
				//指定解压后的文件夹+当前zip文件的名称
				String outPath = (descDir+zipEntryName).replace("/", File.separator);
				//判断路径是否存在,不存在则创建文件路径
				File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
				if(!file.exists()){
					file.mkdirs();
				}
				//判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
				if(new File(outPath).isDirectory()){
					continue;
				}
				//保存文件路径信息（可利用md5.zip名称的唯一性，来判断是否已经解压）
				System.err.println("当前zip解压之后的路径为：" + outPath);
				OutputStream out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[2048];
				int len;
				while((len=in.read(buf1))>0){
					out.write(buf1,0,len);
				}
				in.close();
				out.close();
			}
			flag = true;
			//必须关闭，要不然这个zip文件一直被占用着，要删删不掉，改名也不可以，移动也不行，整多了，系统还崩了。
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	//解压指定zip文件
	public static void unzip(String srcZipPath, String srcZipFile) {
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcZipPath + srcZipFile));
			ZipInputStream zis = new ZipInputStream(bis);

			BufferedOutputStream bos = null;

			// byte[] b = new byte[1024];
			ZipEntry entry = null;
			while ((entry = zis.getNextEntry()) != null) {
				String entryName = entry.getName();
				bos = new BufferedOutputStream(new FileOutputStream(srcZipPath + entryName));
				int b = 0;
				while ((b = zis.read()) != -1) {
					bos.write(b);
				}
				bos.flush();
				bos.close();
			}
			zis.close();
			LOGGER.info("Unzip zip file {} successfully.", srcZipFile);
		} catch (IOException e) {
			LOGGER.error("Unzip zip file {} error: {}", srcZipFile, e.getMessage());
		}
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			if (file.delete()) {
				LOGGER.info("Delete file {} successfully.", filePath);
			} else {
				LOGGER.error("Delete file {} failed.", filePath);
			}
		} else {
			LOGGER.error("File {} is not exists.", filePath);
		}
	}
	public static void deleteFile(File dirFile) {
		if (!dirFile.exists()) {
			return;
		}
		if (dirFile.isFile()) {
			dirFile.delete();
			LOGGER.info("Delete file {} successfully.", dirFile);
		} else {
			for (File file : dirFile.listFiles()) {
				deleteFile(file);
				LOGGER.info("Delete file {} successfully.", file);
			}
		}
		dirFile.delete();
		LOGGER.info("Delete file {} successfully.", dirFile);
	}

	public static void clearDirectory(String directory) {
		List<File> files = listFile(directory);
		for (File file : files) {
			if (!file.delete()) {
				LOGGER.error("Delete temp file {} failed.", file.getName());
			}
		}
	}
	//开始添加
	public static void zipwriteToFile(InputStream uploadedInputStream, String filename, String uploadedFileLocation)
			throws IOException {
		byte[] buffer = new byte[1024];
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(uploadedFileLocation);
			zos = new ZipOutputStream(fos);
			// begin writing a new ZIP entry, positions the stream to the start of the entry data
			zos.putNextEntry(new ZipEntry(filename));
			int length;
			while ((length = uploadedInputStream.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}
			zos.closeEntry();
			if (zos != null) {
				zos.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (uploadedInputStream != null) {
				uploadedInputStream.close();
			}
		} catch (IOException e) {
			LOGGER.error("zipwriteToFile {} error: {}", uploadedFileLocation + filename, e.getMessage());
		}
	}

	public static void unzipfile(String srcZipPath, String srcZipFile) {
		try {
			File file = new File(srcZipPath + srcZipFile);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
			BufferedOutputStream bos = null;
			File fout;
			ZipEntry entry;
			String entryName;
			while ((entry = zis.getNextEntry()) != null) {
				entryName = entry.getName();
				fout = new File(srcZipPath + entryName);
				if (entryName.endsWith("/")) {
					if (!fout.exists()) {
						fout.mkdirs();
					}
					continue;
				} else {
					if (!fout.exists()) {
						fout.createNewFile();
					}
				}
				bos = new BufferedOutputStream(new FileOutputStream(fout));
				int len = -1;
				byte[] buffer = new byte[1024];
				while ((len = zis.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				bos.flush();
			}
			if(bos != null) {
				bos.close();
			}
			if(zis != null) {
				zis.close();
			}
			LOGGER.info("Unzip zip file {} successfully.", srcZipFile);
		} catch (IOException e) {
			LOGGER.error("Unzip zip file {} error: {}", srcZipFile, e.getMessage());
		}
	}
	public static void copy(String src, String des) {
		File file1 = new File(src);
		File[] fs = file1.listFiles();
		File file2 = new File(des);
		if (!file2.exists()) {
			file2.mkdirs();
		}
		for (File f : fs) {
			if (f.isFile()) {
				fileCopy(f.getPath(), des + "/" + f.getName()); //调用文件拷贝的方法
			} else if (f.isDirectory()) {
				copy(f.getPath(), des + "/" + f.getName());
			}
		}
	}
	private static void fileCopy(String src, String des) {
		int len = 0;
		byte[] buffer = new byte[1024];
		try {
			FileInputStream fis = new FileInputStream(new File(src));
			FileOutputStream fos = new FileOutputStream(new File(des));
			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			if(fis != null) {
				fis.close();
			}
			if(fos != null) {
				fos.close();
			}
		}catch (IOException e) {
			LOGGER.error("fileCopy {} error: {}", src, e.getMessage());
		}
	}
	public static class TreeInfo implements Iterable<File> {
		public List<File> files = new ArrayList<File>();
		public List<File> dirs = new ArrayList<File>();
		// The default iterable element is the file list:
		public Iterator<File> iterator() {
			return files.iterator();
		}
		void addAll(TreeInfo other) {
			files.addAll(other.files);
			dirs.addAll(other.dirs);
		}
	}
	public static TreeInfo walk(String start, String regex) { // Begin recursion
		return recurseDirs(new File(start), regex);
	}

	public static TreeInfo walk(File start, String regex) { // Overloaded
		return recurseDirs(start, regex);
	}

	public static TreeInfo walk(File start) { // Everything
		return recurseDirs(start, ".*");
	}

	public static TreeInfo walk(String start) {
		return recurseDirs(new File(start), ".*");
	}
	static TreeInfo recurseDirs(File startDir, String regex) {
		TreeInfo result = new TreeInfo();
		for (File item : startDir.listFiles()) {
			if (item.isDirectory()) {
				result.dirs.add(item);
				result.addAll(recurseDirs(item, regex));
			} else // Regular file
				if (item.getName().matches(regex))
					result.files.add(item);
		}
		return result;
	}
}

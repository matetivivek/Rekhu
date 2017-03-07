package hello.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class FileAttributes implements Serializable {
  
	private String fileName;
	private String mimeType;
	private Date creationTime;
	private Date lastModifiedTime;
	private boolean isDirectory,isOther, isRegularFile,isSymbolicLink;
	private long size;
	public FileAttributes(Path path) {
		try {
			File file=new File(path.toString());
			fileName=file.getName();
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			mimeType = fileNameMap.getContentTypeFor("alert.gif");
			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
			creationTime= getDate(attr.creationTime());
			lastModifiedTime= getDate(attr.lastModifiedTime());
			isDirectory=attr.isDirectory();
			isOther=attr.isOther();
			isRegularFile=attr.isRegularFile();
			isSymbolicLink=attr.isSymbolicLink();
			size=attr.size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveToFile(InputStream inStream, String target)
			throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
	
	public static void createFolderIfNotExists(String dirName)
			throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	
	public Date getDate(FileTime ft){
		Calendar atime = Calendar.getInstance();
		atime.setTimeInMillis(ft.toMillis());
		return atime.getTime();
	}

	public String getFileName() {
		return fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public boolean isOther() {
		return isOther;
	}

	public boolean isRegularFile() {
		return isRegularFile;
	}

	public boolean isSymbolicLink() {
		return isSymbolicLink;
	}

	public long getSize() {
		return size;
	}
	
}

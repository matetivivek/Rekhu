package hello.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "filemetadata")
public class FileMetaData implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private String fileName;
	@NotNull
	private Date createdTime;

	private String mimeType;
	private Date lastModifiedTime;
	private boolean isDirectory, isOther, isRegularFile, isSymbolicLink;
	private long size;

	public FileMetaData() {
	}

	public FileMetaData(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public boolean isOther() {
		return isOther;
	}

	public void setOther(boolean isOther) {
		this.isOther = isOther;
	}

	public boolean isRegularFile() {
		return isRegularFile;
	}

	public void setRegularFile(boolean isRegularFile) {
		this.isRegularFile = isRegularFile;
	}

	public boolean isSymbolicLink() {
		return isSymbolicLink;
	}

	public void setSymbolicLink(boolean isSymbolicLink) {
		this.isSymbolicLink = isSymbolicLink;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FileMetaData [id=" + id + ", fileName=" + fileName + ", createdTime=" + createdTime + ", mimeType="
				+ mimeType + ", lastModifiedTime=" + lastModifiedTime + ", isDirectory=" + isDirectory + ", isOther="
				+ isOther + ", isRegularFile=" + isRegularFile + ", isSymbolicLink=" + isSymbolicLink + ", size=" + size
				+ "]";
	}
	

}

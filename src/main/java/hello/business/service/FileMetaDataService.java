package hello.business.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.dao.FileMetadataDAO;
import hello.entity.FileMetaData;
import hello.util.FileAttributes;

@Service
public class FileMetaDataService {
	@Autowired
	FileMetadataDAO fmd;
	
	public FileMetaData save(FileAttributes doc){
		FileMetaData data=new FileMetaData();
		try{
			if(doc!=null){
				data.setFileName(doc.getFileName());
				data.setCreatedTime(new Date());
				data=fmd.save(data);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
		
	}
	
	public FileMetaData save(FileMetaData doc){
		FileMetaData data=new FileMetaData();
		try{
			data=fmd.save(doc);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
		
	}
	
	
	public String delete(long id){
		
		try{
			FileMetaData data=new FileMetaData(id);
			fmd.delete(data);		
		}catch(Exception e){
			 return "Error deleting the file: " + e.toString();
		}
		return "File succesfully deleted!";
		
	}	
		
	public List<FileMetaData>  allFiles() {
		List<FileMetaData> data=null;
	    try {
	    	data = (List<FileMetaData>) fmd.findAll();
	    }
	    catch (Exception ex) {
	    	ex.printStackTrace();
	    }
	    return data;
	  }
	
	public FileMetaData update(FileAttributes meta,long id) {
		FileMetaData md=null;
	    try {
	    	md = fmd.findOne(id);
	    	md.setLastModifiedTime(meta.getLastModifiedTime());
			md.setMimeType(meta.getMimeType());
			md.setRegularFile(meta.isRegularFile());
			md.setDirectory(meta.isDirectory());
			md.setSymbolicLink(meta.isSymbolicLink());
			md.setSize(meta.getSize());
			fmd.save(md);
			return md;
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return md;
	  }
	public FileMetaData getFile(long id) {
		FileMetaData md=null;
	    try {
	    	md = fmd.findOne(id);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return md;
	}
	
	public List<FileMetaData> fileFiles(String fileName){
		return (List<FileMetaData>) fmd.findByFileNameIgnoreCaseContainingOrderByCreatedTime(fileName);
	}
	
	public List<FileMetaData> getLatestFilesUploaded(int hours){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.HOUR, hours);
		return (List<FileMetaData>) fmd.findByCreatedTimeGreaterThanOrderByCreatedTime(cal.getTime());
	}
}

package hello.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hello.entity.FileMetaData;

public interface FileMetadataDAO extends CrudRepository<FileMetaData, Long>{

	public List<FileMetaData> findByFileNameIgnoreCaseContainingOrderByCreatedTime(String fileName);
	
	public List<FileMetaData> findByCreatedTimeGreaterThanOrderByCreatedTime(Date createdTime);
	
	
}

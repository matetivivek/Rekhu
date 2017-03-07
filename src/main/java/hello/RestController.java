package hello;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hello.business.service.FileMetaDataService;
import hello.entity.FileMetaData;
import hello.util.FileAttributes;

@Controller
public class RestController {

	private static final String UPLOAD_FOLDER = System.getProperty("user.home") + "/uploadedFiles/";

	@Autowired
	FileMetaDataService fds;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody FileMetaData handleFileUpload(
			@RequestParam(value = "file", required = true) MultipartFile file) {
		FileMetaData md = new FileMetaData();
		if (file != null) {
			try {
				FileAttributes.createFolderIfNotExists(UPLOAD_FOLDER);
				md.setFileName(file.getOriginalFilename());
				md.setCreatedTime(new Date());
				md = fds.save(md);
				FileAttributes.saveToFile(file.getInputStream(), UPLOAD_FOLDER + "/" + md.getId());
				FileAttributes document = new FileAttributes(Paths.get(UPLOAD_FOLDER + "/" + md.getId()));
				md = fds.update(document, md.getId());
				return null;// document.getMetadata();
			} catch (Exception e) {
				e.printStackTrace();
				md.setRegularFile(false);
			}
		}
		return md;
	}

	

	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public HttpEntity<List<FileMetaData>> handleTest(
			@RequestParam(value = "fileName", required = false) String fileName) {
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			if(fileName !=null && !fileName.equalsIgnoreCase(""))
				return new ResponseEntity<List<FileMetaData>>(fds.fileFiles(fileName), httpHeaders, HttpStatus.OK);
			else
				return new ResponseEntity<List<FileMetaData>>(fds.allFiles(), httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/document/{id}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getDocument(@PathVariable long id) throws IOException {
		FileMetaData md = fds.getFile(id);
		File document = new File(UPLOAD_FOLDER + "/" + id);
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.IMAGE_JPEG);
		respHeaders.setContentLength(12345678);
		respHeaders.setContentDispositionFormData("attachment", md.getFileName());

		InputStreamResource isr = new InputStreamResource(new FileInputStream(document));
		return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/metaData/{id}", method = RequestMethod.GET)
	public FileMetaData getDocumentMetaData(@PathVariable long id) throws IOException {		
		return fds.getFile(id);
	}

}

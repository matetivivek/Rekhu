package hello.scheduling;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import hello.business.service.FileMetaDataService;
import hello.entity.FileMetaData;
import hello.util.EmailService;

@Configuration
@EnableScheduling
public class BatchScheduling {
	@Autowired
	FileMetaDataService fmd;
	
	@Scheduled(fixedRate = 2000)
	public void triggerMails() {
		List<FileMetaData> list = fmd.getLatestFilesUploaded(-1);
		if(list!=null && list.size()>0){
		String to="mateti.vivek@gmail.com";
		String subject ="Latest files uploaded in last one hour";
		StringBuffer sbf=new StringBuffer();
		for (FileMetaData md : list) {
			sbf.append(md+"<br/>");
			
		}
		System.out.println(sbf.toString());
		
		EmailService.sendMail(to, subject, sbf.toString());
		}

	}

}

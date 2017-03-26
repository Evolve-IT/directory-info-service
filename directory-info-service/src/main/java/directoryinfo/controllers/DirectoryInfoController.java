package directoryinfo.controllers;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import directoryinfo.logic.DirectoryBrowser;
import directoryinfo.models.DirectoryListingResult;

@RestController
@RequestMapping(DirectoryInfoController.DIRECTORY_INFO_BASE_URI)
public class DirectoryInfoController {

	public static final String DIRECTORY_INFO_BASE_URI = "svc/v1/directoryinfo";
	
	static{
	    System.out.println("Static init DirectoryInfoController");
	}
	
	@RequestMapping(value = "/getDirectoryInfoJson", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DirectoryListingResult getDirectoryInfoJson(@RequestParam(value = "directory") final String directory)
	{		
		DirectoryBrowser directoryBrowser = new DirectoryBrowser(directory);
		return directoryBrowser.getFullDirectoryListing();
	}
	
	@RequestMapping(value = "/getDirectoryInfoXml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.ALL_VALUE)
	public DirectoryListingResult getDirectoryInfoXml(@RequestParam(value = "directory") final String directory)
	{		
		DirectoryBrowser directoryBrowser = new DirectoryBrowser(directory);
		return directoryBrowser.getFullDirectoryListing();
	}
	
	 public DirectoryInfoController(){
	        super();
	}
}

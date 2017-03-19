package directoryinfo.controllers;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import directoryinfo.logic.DirectoryBrowser;
import directoryinfo.models.DirectoryInfo;

@RestController
@RequestMapping(DirectoryInfoController.DIRECTORY_INFO_BASE_URI)
public class DirectoryInfoController {

	public static final String DIRECTORY_INFO_BASE_URI = "svc/v1/directoryinfo";
	
	static{
	    System.out.println("Static init DirectoryInfoController");
	}
	
	@RequestMapping(value = "/getDirectoryInfoOld/{directory}")
	public DirectoryInfo getDirectoryInfoOld(@PathVariable final String directory)
	{
	 	System.out.println("starting servlet (getDirectoryInfoOld)");
		
		/*DirectoryInfo directoryInfo = new DirectoryInfo();		
		directoryInfo.setDirectory(directory);
		*/
		
		DirectoryBrowser directoryBrowser = new DirectoryBrowser(directory);
		return directoryBrowser.getFullDirectoryListing();
	}
	
	
	@RequestMapping(value = "/getDirectoryInfo", method = RequestMethod.GET)
	public DirectoryInfo getDirectoryInfo(@RequestParam(value = "directory") final String directory)
	{
	 	System.out.println("starting servlet (getDirectoryInfo)");
		
		/*DirectoryInfo directoryInfo = new DirectoryInfo();		
		directoryInfo.setDirectory(directory);
		*/
		
		DirectoryBrowser directoryBrowser = new DirectoryBrowser(directory);
		return directoryBrowser.getFullDirectoryListing();
	}
	
	  public DirectoryInfoController(){
	        super();



	}
}

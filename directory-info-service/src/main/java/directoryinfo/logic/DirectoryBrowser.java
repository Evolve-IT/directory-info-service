package directoryinfo.logic;

import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;


import directoryinfo.models.DirectoryInfo;
import directoryinfo.models.DirectoryListingResult;

public class DirectoryBrowser {
	
	
	private String _rootDirectory = "";
	private int DirectorySize;
	
	
	public DirectoryBrowser(String rootDirectory)
	{
		_rootDirectory = rootDirectory;
	}
	
	public DirectoryListingResult getFullDirectoryListing()
	{
		DirectoryListingResult result = new DirectoryListingResult();
		try
		{
			DirectorySize = 0;
			Path path = Paths.get(_rootDirectory);
			
			if ((path == null) || (!Files.exists(path)))
			{
				result.setAsFailed(String.format("Cannot find directory '%1s'", _rootDirectory));
			}
			else
			{
				result.assignDirectoryInfo(getDirectoryInfo(path));
			}
		}
		catch(Exception exception)
		{
			System.out.println(String.format("An error occured during getFullDirectoryListing.\r\nException:\r\n%1s", exception.getMessage()));
			exception.printStackTrace();
			result.setAsFailed("An unknown error occurred.");
		}
		
		result.setDirectorySize(DirectorySize);
		
		return result;
	}
	
	private DirectoryInfo getDirectoryInfo(Path path)
	{
		DirectoryInfo directoryInfo = new DirectoryInfo();
		
		try
		{
			DirectorySize = DirectorySize + 1;
						
			System.out.println(String.format("Node: %1s Directory: %2s.", DirectorySize, path.normalize().toString()));
			
			directoryInfo.setFullPath(path.normalize().toString());
			directoryInfo.setFileSize(Files.size(path));
			
			BasicFileAttributes fileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
			
			directoryInfo.addAttribute("LastAccessTime", fileAttributeView.lastAccessTime());
			directoryInfo.addAttribute("LastModifiedTime", fileAttributeView.lastModifiedTime());
			directoryInfo.addAttribute("CreationTime", fileAttributeView.creationTime());
			
		
			if (Files.isDirectory(path)) {
				directoryInfo.setType("Directory");
				
				DirectoryStream<Path> stream = Files.newDirectoryStream(path);
				
				for (Path entry : stream) {
		           
		            DirectoryInfo child = getDirectoryInfo(entry);
		            directoryInfo.addChild(child);
		        }
	        }
	        else
	        {
	        	directoryInfo.setType("File");
	        }
	        
		
		} 
		catch (NoSuchFileException ex) {
			directoryInfo.setErrorMessage("File not found.");
	    }
		catch (AccessDeniedException e) {
			directoryInfo.setErrorMessage("Access denied.");
	    }
		catch(Exception exception)
		{
			System.out.println(String.format("An error occured during getDirectoyInfo.\r\nException:\r\n%1s", exception.getMessage()));
			exception.printStackTrace();
			directoryInfo.setErrorMessage("An unknown error occurred.");
		}
		
		return directoryInfo;
	}

}

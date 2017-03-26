package directoryinfo.logic;

import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

import directoryinfo.models.DirectoryInfo;
import directoryinfo.models.DirectoryInfoType;
import directoryinfo.models.DirectoryListingResult;

public class DirectoryBrowser {
	
	//Private Variables
	private String RootDirectory = "";
	private int DirectorySize;
	
	
	//Constructor
	public DirectoryBrowser(String rootDirectory)
	{
		RootDirectory = rootDirectory;
	}
	
	//Public Methods
	
	//Recursively get the directory listing for the root directory which was passed in to the constructor
	public DirectoryListingResult getFullDirectoryListing()
	{
		DirectoryListingResult result = new DirectoryListingResult();
		try
		{
			DirectorySize = 0;
			Path path = Paths.get(RootDirectory);
			
			if ((path == null) || (!Files.exists(path)))
			{
				result.setAsFailed(String.format("Cannot find directory '%1s'", RootDirectory));
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
			
			//Read the attributes without following symbolic links (prevents "too many levels of symbolic links" exception)
			BasicFileAttributes attributes = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).readAttributes();
			
			
			directoryInfo.setLastAccessedTime(attributes.lastAccessTime());
			directoryInfo.setLastModifiedTime(attributes.lastModifiedTime());
			directoryInfo.setCreationTime(attributes.creationTime());
			
		
			//If it is a symbolic link, don't attempt to get child items as it results in a "too many levels of symbolic links" exception
			if (attributes.isSymbolicLink())
			{
				directoryInfo.setType(DirectoryInfoType.SymbolicLink);
			}
			else
			{
				
				//If the item is a directory, recursively build up the child items
				if (Files.isDirectory(path)) {
					directoryInfo.setType(DirectoryInfoType.Directory);
					
					DirectoryStream<Path> stream = Files.newDirectoryStream(path);
					
					for (Path entry : stream) {
			           
			            DirectoryInfo child = getDirectoryInfo(entry);
			            directoryInfo.addChild(child);
			        }
		        }
		        else if (attributes.isOther())
		        {
		        	directoryInfo.setType(DirectoryInfoType.Other);
		        	directoryInfo.setFileSize(Files.size(path));
		        }
		        else
		        {
		        	directoryInfo.setType(DirectoryInfoType.File);
		        	directoryInfo.setFileSize(Files.size(path));
		        }
			}
		} 
		catch (NoSuchFileException ex) {
			directoryInfo.setErrorMessage("File not found.");
	    }
		catch (AccessDeniedException e) {
			directoryInfo.setErrorMessage("Access denied.");
	    }
		catch (FileSystemException ex) {
			directoryInfo.setErrorMessage(ex.getReason());
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

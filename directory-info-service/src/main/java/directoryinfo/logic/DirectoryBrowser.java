package directoryinfo.logic;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.util.List;

import directoryinfo.models.DirectoryInfo;

public class DirectoryBrowser {
	
	
	private String _rootDirectory = "";
	
	
	public DirectoryBrowser(String rootDirectory)
	{
		_rootDirectory = rootDirectory;
	}
	
	public DirectoryInfo getFullDirectoryListing()
	{
		
		try
		{
			Path path = Paths.get(_rootDirectory);
			
			if (path == null)
			{
				throw new Exception(String.format("Cannot find directory \"%1s\"", _rootDirectory));
			}
			else
			{
				System.out.println(String.format("Directory \"%1s\" found.", _rootDirectory));
			}
			
			return getDirectoryInfo(path);
		}
		catch(Exception exception)
		{
			System.out.println(String.format("An error occured during getFullDirectoryListing.\r\nException:\r\n%1s", exception.getMessage()));
			/*TODO: Add error logging here*/
			
			exception.printStackTrace();
			
			return null;
		}
	}
	
	private DirectoryInfo getDirectoryInfo(Path path)
	{
		DirectoryInfo directoryInfo = new DirectoryInfo();
		
		try
		{
			
			System.out.println(String.format("FullPath = %1s", path.normalize().toString()));
			
			directoryInfo.setFullPath(path.normalize().toString());
			directoryInfo.setFileSize(Files.size(path));
			
			BasicFileAttributes fileAttributeView = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
			
			Object a = fileAttributeView.lastAccessTime();
			
			
			
			directoryInfo.addAttribute("LastAccessTime", a);
			
			
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
		catch(Exception exception)
		{
			System.out.println(String.format("An error occured during getDirectoyInfo.\r\nException:\r\n%1s", exception.getMessage()));
			exception.printStackTrace();
			/*TODO: Add logging here */
			directoryInfo = null;
		}
		
		return directoryInfo;
	}

}

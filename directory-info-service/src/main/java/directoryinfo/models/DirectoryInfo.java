package directoryinfo.models;

import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryInfo {
	
	//Private Variables
	private String FullPath;
	private String Type;
	private List<Attribute> Attributes;
	private List<DirectoryInfo> Children;
	
	
	//Constructors
	public DirectoryInfo()
	{
		Attributes = new ArrayList<Attribute>();
		Children = new ArrayList<DirectoryInfo>();
		setType(DirectoryInfoType.Unknown);
	}
	
	
	//Public Getters and Setters
	public String getFullPath() {
		return FullPath;
	}

	public void setFullPath(String fullPath) {
		FullPath = fullPath;
	}

	public String getType() {
		return Type;
	}

	public void setType(DirectoryInfoType type) {
		Type = type.toString();
	}

	public List<Attribute> getAttributes() {
		return Attributes;
	}

	public List<DirectoryInfo> getChildren() {
		return Children;
	}
	
	
	public void setFileSize(long fileSize) {
		final String FILE_SIZE = "File Size";
		addAttribute(FILE_SIZE, fileSize);
	}
	
	public void setLastAccessedTime(FileTime lastAccessTime) {
		final String LAST_ACCESSED_TIME = "LastAccessTime";
		addAttribute(LAST_ACCESSED_TIME, lastAccessTime);
	}

	public void setLastModifiedTime(FileTime lastModifiedTime) {
		final String LAST_MODIFIED_TIME = "LastModifiedTime";
		addAttribute(LAST_MODIFIED_TIME, lastModifiedTime);
	}

	public void setCreationTime(FileTime creationTime) {
		final String CREATION_TIME = "CreationTime";
		addAttribute(CREATION_TIME, creationTime);
	}
	
	
	//Public Methods
	public void addAttribute(String name, Object value)
	{
		Attributes.add(new Attribute(name, value));
	}
	
	public void addChild(DirectoryInfo childToAdd)
	{
		Children.add(childToAdd);
	}
	
	public void addErrorMessageAttribute(String value)
	{
		final String ERROR_MESSAGE = "Error Message";
		addAttribute(ERROR_MESSAGE, value);
	}
}

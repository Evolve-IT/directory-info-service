package directoryinfo.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryInfo {
	
	//Private Variables
	private String FullPath;
	private long FileSize;
	private String Type;
	private String ErrorMessage;
	private List<Attribute> Attributes;
	private List<DirectoryInfo> Children;
	
	
	//Constructors
	public DirectoryInfo()
	{
		Attributes = new ArrayList<Attribute>();
		Children = new ArrayList<DirectoryInfo>();
	}
	
	
	//Public Getters and Setters
	public String getFullPath() {
		return FullPath;
	}

	public void setFullPath(String fullPath) {
		FullPath = fullPath;
	}

	public long getFileSize() {
		return FileSize;
	}

	public void setFileSize(long fileSize) {
		FileSize = fileSize;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public List<Attribute> getAttributes() {
		return Attributes;
	}

	public List<DirectoryInfo> getChildren() {
		return Children;
	}
	
	public String getErrorMessage() {
		return ErrorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
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
}

package directoryinfo.models;

public class DirectoryListingResult {
	private boolean Success;
	private String ErrorMessage;
	private DirectoryInfo DirectoryInfo;
	private int DirectorySize;
	
	public boolean isSuccess() {
		return Success;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public DirectoryInfo getDirectoryInfo() {
		return DirectoryInfo;
	}
	
	public int getDirectorySize() {
		return DirectorySize;
	}
	
	public void setDirectorySize(int directorySize) {
		DirectorySize = directorySize;
	}
	
	public void setAsFailed(String errorMessage)
	{
		ErrorMessage = errorMessage;
		Success = false;
		DirectoryInfo = null;
	}
	
	public void assignDirectoryInfo(DirectoryInfo directoryInfo)
	{
		ErrorMessage = null;
		Success = true;
		DirectoryInfo = directoryInfo;
	}
}
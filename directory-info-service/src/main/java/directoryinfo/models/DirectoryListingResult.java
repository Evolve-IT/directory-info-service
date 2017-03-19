package directoryinfo.models;

public class DirectoryListingResult {
	private boolean Success;
	private String ErrorMessage;
	private DirectoryInfo DirectoryInfo;
	public boolean isSuccess() {
		return Success;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public DirectoryInfo getDirectoryInfo() {
		return DirectoryInfo;
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
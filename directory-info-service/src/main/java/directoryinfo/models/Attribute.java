package directoryinfo.models;

public class Attribute {
	private String Name;
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	private String Value;
	
	public String getValue() {
		return Value;
	}

	public void setValue(Object value) {
		Value = value.toString();
	}
	
	public Attribute(String name, Object value)
	{
		setName(name);
		setValue(value);
	}
}
package lang.mocha.utilclasses;

public class MochaVariable {

	private String name, value, vis, type; 
	private boolean isStatic;
	
	public MochaVariable(String name, String type, String value, String vis, boolean isStatic){
		
		this.name = name;
		this.type = type;
		this.value = value;
		this.vis = vis;
		this.isStatic = isStatic;
	}
	
	public String getName(){
		
		return name;
	}
	
	public String getType(){
		
		return type;
	}
	
	public String getValue(){
		
		return value;
	}
	
	public String getVisibility(){
		
		return vis;
	}
	
	public boolean isStatic(){
		
		return isStatic;
	}

	public void setValue(String value) {

		this.value = value;
	}
}

package StoryGenerator;

public class PlanningVariable {
	String name;
	public enum VariableType{ CHARACTER, ITEM, LOCATION }
	VariableType type;
	
	public PlanningVariable(String n, VariableType t){
		name = n; type = t;
	}
	
	public String GetName(){
		return name;
	}
	
	public VariableType GetType(){
		return type;
	}
}

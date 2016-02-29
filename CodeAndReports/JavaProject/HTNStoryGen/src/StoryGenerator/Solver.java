package StoryGenerator;

import HardCodedDomain.DomainDebug;
import HardCodedDomain.DomainLoader;
import HardCodedDomain.HCMethods.Method;

public class Solver {
	
	Operator[] plan; 
	static State initialState;
	static State preservedState;
	static Operator[] availableTasks;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Setup(args);
		DomainDebug.PrintDomainFacts(initialState);
		Operator story = FindOperator(Method.STORY);
		Operator[] completeStory = story.Execute(initialState, null);
		PrintStory.PrintFinalStory(completeStory[0], preservedState);
	}
	
	Operator[] GetPlan(){
		
		
		return null;
	}
	
	static void Setup(String[] args){
		int worldSet = 0;
		if (args.length > 0) {
		    try {
		        worldSet = Integer.parseInt(args[0]);
		    } 
		    catch (NumberFormatException e) {
		        System.err.println("Argument" + args[0] + " must be an integer.");
		        System.exit(1);
		    }
		}
		else
		{
			System.err.println("Selected a preset world by providing argument between 1 and 3.");
	        System.exit(1);
		}
		
		switch(worldSet){
			case 1:
				initialState = DomainLoader.Preset1();
				preservedState = DomainLoader.Preset1();
				break;
			case 2:
				DomainLoader.Preset2();
				break;
			case 3:
				DomainLoader.Preset3();
				break;
			default:
				System.err.println("Selected a preset world by providing argument between 1 and 3.");
		        System.exit(1);
		        break;
		}
		
		//DomainDebug.PrintDomainFacts(initialState);
		availableTasks = DomainLoader.LoadOperators();
		DomainDebug.PrintAvailableOperators(availableTasks);
	}
	
	static Operator FindOperator(Method m){
		for(Operator o : availableTasks){
			if(o.GetMethodId() == m){
				return o;
			}
		}
		
		return null;
	}
}

package StoryGenerator;

import HardCodedDomain.HCMethods;
import StoryGenerator.PlanningVariable.VariableType;

public class PrintStory {
	
	static void PrintFinalStory(Operator s, State i){
		//Intro stuff - ideally this would be made more domain independent
		System.out.println("\n");
		PlanningVariable h = HCMethods.GetVariable("Hero", VariableType.CHARACTER, i);
		PlanningVariable hL = HCMethods.GetLocationHelper(h, i);
		PlanningVariable p = HCMethods.GetVariable("Princess", VariableType.CHARACTER, i);
		PlanningVariable pL = HCMethods.GetLocationHelper(p, i);
		PlanningVariable v = HCMethods.GetVariable("Villian", VariableType.CHARACTER, i);
		PlanningVariable vL = HCMethods.GetLocationHelper(v, i);
		PlanningVariable d = HCMethods.GetVariable("Dragon", VariableType.CHARACTER, i);
		PlanningVariable dL = HCMethods.GetLocationHelper(d, i);
		
		System.out.println("There once was a " + h.GetName() + " who lived in the " + hL.GetName());
		System.out.println("There once was a " + p.GetName() + " who lived in the " + pL.GetName());
		System.out.println("There once was a " + v.GetName() + " who lived in the " + vL.GetName());
		System.out.println("There once was a " + d.GetName() + " who lived in the " + dL.GetName());
		System.out.println("\n One day...");
		DecomposeCompound(s);
	}
	
	static void DecomposeCompound(Operator s){
		if(s.GetSubTasks() == null){
			PrintPrimative(s);
		}
		else
		{
			PlanningVariable[] parameter = s.GetParameters();
			
			switch(s.GetMethodId()){
				case GETITEMFROMCHAR:
					System.out.println("The " + parameter[0].GetName() + " went to get the " + parameter[2].GetName() 
							+ " from the " + parameter[1].GetName());
					break;
				case GETITEMFROMLOC:
					System.out.println("The " + parameter[0].GetName() + " went to get the " + parameter[2].GetName() 
							+ " from the " + parameter[1].GetName());
					break;
				case GETKEY:
					System.out.println("The " + parameter[0].GetName() + " needed to get the " + parameter[1].GetName());
					break;
				case KIDNAP:
					System.out.println("The " + parameter[0].GetName() + " kidnapped the " + parameter[1].GetName() 
							+ " and enprisoned them in the " + parameter[2].GetName());
					break;
				case LOOTANDKILL:
					System.out.println("The " + parameter[0].GetName() + " decided to kill the " + parameter[1].GetName() 
							+ " to get the " + parameter[2].GetName());
					break;
				case RESCUE:
					System.out.println("The " + parameter[0].GetName() + " decided to rescue the " + parameter[1].GetName() 
							+ " from the " + parameter[2].GetName());
					break;
				case STEAL:
					System.out.println("The " + parameter[0].GetName() + " decided to steal the " + parameter[3].GetName());
					break;
				case STORY:
					break;
				default:
					break;
			}
		
			for(Operator sT: s.GetSubTasks()){
				DecomposeCompound(sT);
			}
		}
	}
	
	static void PrintPrimative(Operator s){
		PlanningVariable[] parameter = s.GetParameters();
		
		switch(s.GetMethodId()){
			case FREE:
				System.out.println("The " + parameter[2].GetName() + " freed the " + parameter[0].GetName() + " from the " + parameter[1].GetName() +
						" using the " + parameter[3].GetName());
				break;
			case KILL:
				System.out.println("The " + parameter[0].GetName() + " killed the " + parameter[1].GetName() + " with the "
							+ parameter[3].GetName());
				break;
			case MOVE:
				//This should really been handled at the planning stage - currently no way to no-op without breaking the plan
				if(parameter[1] != parameter[2]){
					System.out.println("The " + parameter[0].GetName() + " walked from the " + parameter[1].GetName() + " to the " + parameter[2].GetName());
				}
				break;
			case PICKUP:
				
				System.out.println("The " + parameter[0].GetName() + " took the " + parameter[1].GetName() + " from the " + parameter[2].GetName());
				break;
			case PICKUPFROMC:
				System.out.println("The " + parameter[0].GetName() + " took the " + parameter[1].GetName() + " from the " + parameter[3].GetName());
				break;
			case TRAP:
				System.out.println("The " + parameter[0].GetName() + " was trapped at the " + parameter[1].GetName());
				break;
			default:
				break;
		}
	}
}

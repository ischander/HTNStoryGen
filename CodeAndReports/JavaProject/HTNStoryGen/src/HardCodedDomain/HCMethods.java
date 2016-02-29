package HardCodedDomain;

import java.util.ArrayList;
import java.util.Random;

import HardCodedDomain.HCActions.Condition;
import StoryGenerator.Operator;
import StoryGenerator.PlanningVariable;
import StoryGenerator.PlanningVariable.VariableType;
import StoryGenerator.Predicate;
import StoryGenerator.Prover;
import StoryGenerator.State;

//Decomposition methods for compound tasks
public class HCMethods {
	public enum Method { MOVE, PICKUP, KILL, PICKUPFROMC, TRAP, FREE, KIDNAP, STEAL, GETITEMFROMLOC, LOOTANDKILL,
		GETITEMFROMCHAR, GETKEY, RESCUE, STORY }
	
	//Operators declared in the domain loader have placeholder variables that need to be redefined in here
	//before being added to the state
	
	public static Operator[] Primative(State s, Operator o, PlanningVariable[] p){
		return new Operator[]{TestGroundedOp(p, s, o)};
	}
	
	//public static Operator[] Kidnap(State s, Operator o, PlanningVariable kidnapper, PlanningVariable victim, PlanningVariable prisonLoc){
	public static Operator[] Kidnap(State s, Operator o, PlanningVariable[] p){	
		Operator kidnapGrounded = TestGroundedOp(p,s,o);
		if(kidnapGrounded == null)
			return null;
		
		Operator[] sT = o.GetSubTasks();
		Operator[] gST = new Operator[sT.length];
		
		if(sT[0].GetMethodId() == Method.MOVE){
			PlanningVariable[] moveParams = new PlanningVariable[3];
			moveParams[0] = p[1]; moveParams[1] = GetLocationHelper(moveParams[0], s); moveParams[2] = p[2];
			//As this is a primitive it will always be an array with one entry
			gST[0] = sT[0].Execute(s, moveParams)[0];
		}
		
		if(sT[1].GetMethodId() == Method.TRAP){
			PlanningVariable[] trapParams = new PlanningVariable[2];
			trapParams[0] = p[1]; trapParams[1] = p[2];
			//As this is a primitive it will always be an array with one entry
			gST[1] = sT[1].Execute(s, trapParams)[0];
		}
		
		for(Operator gO : gST){
			if(gO == null)
			{
				return null;
			}
		}
		
		kidnapGrounded.SetSubTasks(gST);
		
		return new Operator[]{kidnapGrounded};
	}
	
	//public static Operator[] Steal(State s, Operator o, PlanningVariable thief, PlanningVariable victim, PlanningVariable loc, PlanningVariable item){
	public static Operator[] Steal(State s, Operator o, PlanningVariable[] p){
		//Checks the precondition of this task
		Operator stealGrounded = TestGroundedOp(p,s,o);
		if(stealGrounded == null)
			return null;
		
		Operator[] sT = o.GetSubTasks();
		Operator[] gST = new Operator[sT.length];
		
		if(sT[0].GetMethodId() == Method.MOVE){
			PlanningVariable[] moveParams = new PlanningVariable[3];
			moveParams[0] = p[0]; moveParams[1] = GetLocationHelper(moveParams[0], s); moveParams[2] = p[2];
			//As this is a primitive it will always be an array with one entry
			gST[0] = sT[0].Execute(s, moveParams)[0];
		}
		
		if(sT[1].GetMethodId() == Method.PICKUPFROMC){
			PlanningVariable[] pickupParams = new PlanningVariable[4];
			pickupParams[0] = p[0]; pickupParams[1] = p[3]; pickupParams[2] = p[2]; pickupParams[3] = p[1];
			//As this is a primitive it will always be an array with one entry
			gST[1] = sT[1].Execute(s, pickupParams)[0];
		}
		
		for(Operator gO : gST){
			if(gO == null)
			{
				return null;
			}
		}
		
		stealGrounded.SetSubTasks(gST);
		
		return new Operator[]{stealGrounded};
	}
	
	//public static Operator[] GetItemFromLoc(State s, Operator o, PlanningVariable thief, PlanningVariable loc, PlanningVariable item){
	public static Operator[] GetItemFromLoc(State s, Operator o, PlanningVariable [] p){	
		Operator getItemGrounded = TestGroundedOp(p,s,o);
		if(getItemGrounded == null)
			return null;
		
		Operator[] sT = o.GetSubTasks();
		Operator[] gST = new Operator[sT.length];
		
		if(sT[0].GetMethodId() == Method.MOVE){
			PlanningVariable[] moveParams = new PlanningVariable[3];
			moveParams[0] = p[0]; moveParams[1] = GetLocationHelper(moveParams[0], s); moveParams[2] = p[1];
			//As this is a primitive it will always be an array with one entry
			gST[0] = sT[0].Execute(s, moveParams)[0];
		}
		
		if(sT[1].GetMethodId() == Method.PICKUP){
			//PlanningVariable c, PlanningVariable item, PlanningVariable location
			PlanningVariable[] pickupParams = new PlanningVariable[3];
			pickupParams[0] = p[0]; pickupParams[1] = p[2]; pickupParams[2] = p[1];
			//As this is a primitive it will always be an array with one entry
			gST[1] = sT[1].Execute(s, pickupParams)[0];
		}
		
		for(Operator gO : gST){
			if(gO == null)
			{
				return null;
			}
		}
		
		getItemGrounded.SetSubTasks(gST);
		
		return new Operator[]{getItemGrounded};
	}
	
	//thief, victim, item, victim location
	public static Operator[] GetItemFromChar(State s, Operator o, PlanningVariable [] p){	
		Operator getItemGrounded = TestGroundedOp(p,s,o);
		if(getItemGrounded == null)
			return null;
		
		Operator[] sT = o.GetSubTasks();
		Operator[] gST = new Operator[sT.length];
		
		if(sT[0].GetMethodId() == Method.LOOTANDKILL){
			PlanningVariable[] moveParams = new PlanningVariable[7];
			moveParams[0] = p[0]; moveParams[1] = p[1]; 
			moveParams[2] = p[2]; moveParams[3] = GetVariable("magic sword", VariableType.ITEM, s);
			moveParams[4] = GetLocationHelper(moveParams[3], s); moveParams[5] = GetLocationHelper(moveParams[0], s);
			moveParams[6] = p[3];
			//As this is a primitive it will always be an array with one entry
			gST[0] = TestGroundedOp(moveParams, s, sT[0]);
		}
		
		if(sT[1].GetMethodId() == Method.STEAL){
			PlanningVariable[] stealParams = new PlanningVariable[4];
			stealParams[0] = p[0]; stealParams[1] = p[1]; stealParams[2] = p[3]; stealParams[3] =p[2];
			
			//As this is a primitive it will always be an array with one entry
			gST[1] = TestGroundedOp(stealParams, s, sT[1]);
		}
		
		ArrayList<Operator> validTasks = new ArrayList<Operator>();
		
		for(Operator gO : gST){
			if(gO != null)
			{
				validTasks.add(gO);
			}
		}
		
		if(validTasks.size() < 1)
		{
			return null;
		}
		else
		{
			Random rn = new Random();
			Operator selected = validTasks.get(rn.nextInt(validTasks.size()));
			Operator groundedSelected = selected.Execute(s, selected.GetParameters())[0];
			getItemGrounded.SetSubTasks(new Operator[]{groundedSelected});
			return new Operator[]{getItemGrounded};
		}
	}
	
	/*public static Operator[] LootandKill(State s, Operator o, PlanningVariable killer, PlanningVariable victim, PlanningVariable item,
			PlanningVariable weapon, PlanningVariable weaponLoc, PlanningVariable killerLoc, PlanningVariable victimLoc){*/
	public static Operator[] LootandKill(State s, Operator o, PlanningVariable[] p){
		Operator groundedLandK = TestGroundedOp(p,s,o);
		if(groundedLandK == null)
			return null;
		
		Operator[] sT = o.GetSubTasks();
		Operator[] gST = new Operator[2];
		
		if(sT[0].GetMethodId() == Method.GETITEMFROMLOC){
			//thief, location, item
			PlanningVariable[] moveParams = new PlanningVariable[3];
			moveParams[0] = p[0]; moveParams[1] = p[4]; moveParams[2] = p[3];
			//As this is a primitive it will always be an array with one entry
			gST[0] = TestGroundedOp(moveParams, s, sT[0]);
		}
		
		if(sT[1].GetMethodId() == Method.GETITEMFROMCHAR){
			//thief, victim, item, victim location
			PlanningVariable[] stealParams = new PlanningVariable[4];
			stealParams[0] = p[0]; stealParams[1] = p[1]; stealParams[2] = p[3]; stealParams[3] = p[4];
			
			//As this is a primitive it will always be an array with one entry
			gST[1] = TestGroundedOp(stealParams, s, sT[1]);
		}
		
		ArrayList<Operator> validTasks = new ArrayList<Operator>();
		
		for(Operator gO : gST){
			if(gO != null)
			{
				validTasks.add(gO);
			}
		}
		
		if(validTasks.size() < 1)
		{
			return null;
		}

		Random rn = new Random();
		Operator selected = validTasks.get(rn.nextInt(validTasks.size()));
		gST = new Operator[4];
		gST[0] = selected.Execute(s, selected.GetParameters())[0];
		
		if(sT[2].GetMethodId() == Method.MOVE){
			PlanningVariable[] stealParams = new PlanningVariable[3];
			stealParams[0] = p[0]; stealParams[1] = GetLocationHelper(p[0], s); stealParams[2] = p[6]; 
			
			//As this is a primitive it will always be an array with one entry
			gST[1] = sT[2].Execute(s, stealParams)[0];
		}
		
		if(sT[3].GetMethodId() == Method.KILL){
			//PlanningVariable killer, PlanningVariable killee, PlanningVariable location, PlanningVariable weapon
			PlanningVariable[] stealParams = new PlanningVariable[4];
			stealParams[0] = p[0]; stealParams[1] = p[1]; stealParams[2] = p[6]; stealParams[3] = p[3];
			
			//As this is a primitive it will always be an array with one entry
			gST[2] = sT[3].Execute(s, stealParams)[0];
		}
		
		if(sT[4].GetMethodId() == Method.PICKUPFROMC){
			//PlanningVariable c, PlanningVariable item, PlanningVariable l, PlanningVariable corpse
			PlanningVariable[] stealParams = new PlanningVariable[4];
			stealParams[0] = p[0]; stealParams[1] = p[2]; stealParams[2] = p[6]; stealParams[3] = p[1];
			
			//As this is a primitive it will always be an array with one entry
			gST[3] = sT[4].Execute(s, stealParams)[0];
		}
		
		for(Operator gO : gST){
			if(gO == null)
			{
				return null;
			}
		}
		
		groundedLandK.SetSubTasks(gST);
		
		return new Operator[]{groundedLandK};
	}
	
	//public static Operator[] GetKey(State s, Operator o, PlanningVariable thief,PlanningVariable item){
	public static Operator[] GetKey(State s, Operator o, PlanningVariable[] p){	
		Operator groundedGetKey = TestGroundedOp(p,s,o);
		if(groundedGetKey == null)
			return null;
		
		Operator[] sT = o.GetSubTasks();
		Operator[] gST = new Operator[sT.length];
		
		if(sT[0].GetMethodId() == Method.GETITEMFROMCHAR){
			System.out.println("Grounding get item from char");
			//DO PARAMETERS
			//thief, victim, item, victim location
			PlanningVariable[] charParams = new PlanningVariable[4];
			charParams[0] = p[0]; charParams[2] = p[1]; charParams[1] = GetHolderHelper(charParams[2], s); charParams[3] = GetLocationHelper(charParams[1], s);
			gST[0] = TestGroundedOp(charParams, s, sT[0]);
		}
		
		if(sT[1].GetMethodId() == Method.GETITEMFROMLOC){
			System.out.println("Grounding get item from loc");
			//DO PARAMETERS - PlanningVariable thief, PlanningVariable loc, PlanningVariable item
			PlanningVariable[] locParams = new PlanningVariable[3];
			locParams[0]= p[0]; locParams[1] = GetLocationHelper(p[1], s); locParams[2] = p[1];
			gST[1] = TestGroundedOp(locParams, s, sT[1]);
		}
				
		ArrayList<Operator> validTasks = new ArrayList<Operator>();
		
		for(Operator gO : gST){
			if(gO != null)
			{
				validTasks.add(gO);
			}
		}
		
		if(validTasks.size() < 1)
		{
			return null;
		}
		else
		{
			Random rn = new Random();
			Operator selected = validTasks.get(rn.nextInt(validTasks.size()));
			Operator groundedSelected = selected.Execute(s, selected.GetParameters())[0];
			groundedGetKey.SetSubTasks(new Operator[]{groundedSelected});
			return new Operator[]{groundedGetKey};
		}
	}
	
	//public static Operator[] Rescue(State s, Operator o, PlanningVariable hero, PlanningVariable victim, PlanningVariable loc, PlanningVariable key){
	public static Operator[] Rescue(State s, Operator o, PlanningVariable[] p){	
		Operator groundedRescue = TestGroundedOp(p, s, o);
		if(groundedRescue == null)
			return null;
		
		Operator[] sT = o.GetSubTasks();
		Operator[] gST = new Operator[3];
		
		if(sT[0].GetMethodId() == Method.GETKEY){
			PlanningVariable[] getKeyParams = new PlanningVariable[2];
			getKeyParams[0] = p[0]; getKeyParams[1] = p[3];
			gST[0] = sT[0].Execute(s, getKeyParams)[0];
		}
		
		if(sT[1].GetMethodId() == Method.MOVE){
			PlanningVariable[] moveParams = new PlanningVariable[3];
			moveParams[0]= p[0]; moveParams[1] = GetLocationHelper(p[0], s); moveParams[2] = p[2];
			gST[1] = sT[1].Execute(s, moveParams)[0];
		}
		
		if(sT[2].GetMethodId() == Method.FREE){
			//DO PARAMETERS - PlanningVariable trappedChar, PlanningVariable location, PlanningVariable rescuer
			PlanningVariable[] locParams = new PlanningVariable[4];
			locParams[0]= p[1]; locParams[1] = GetLocationHelper(p[1], s); locParams[2] = p[0]; locParams[3] = p[3];
			gST[2] = sT[2].Execute(s, locParams)[0];
		}
		
		for(Operator gO : gST){
			if(gO == null)
			{
				return null;
			}
		}
		
		groundedRescue.SetSubTasks(gST);
		
		return new Operator[]{ groundedRescue };
	}
	
	public static Operator[] Story(State s, Operator o){
		Operator groundedStory = TestGroundedOp(null, s, o);
		if(groundedStory == null)
			return null;
		
		Operator[] sT = o.GetSubTasks();
		Operator[] gST = new Operator[2];
		
		if(sT[0].GetMethodId() == Method.KIDNAP){
			//PlanningVariable kidnapper, PlanningVariable victim, PlanningVariable prisonLoc
			PlanningVariable[] kidnapParams = new PlanningVariable[3];
			kidnapParams[0] = GetVariable("Villian", VariableType.CHARACTER, s); 
			kidnapParams[1] = GetVariable("Princess", VariableType.CHARACTER, s);
			kidnapParams[2] = GetVariable("Tower", VariableType.LOCATION, s);
			gST[0] = sT[0].Execute(s, kidnapParams)[0];
		}
		
		if(sT[1].GetMethodId() == Method.RESCUE){
			//PlanningVariable hero, PlanningVariable victim, PlanningVariable loc, PlanningVariable key
			PlanningVariable[] rescueParams = new PlanningVariable[4];
			rescueParams[0] = GetVariable("Hero", VariableType.CHARACTER, s); 
			rescueParams[1] = GetVariable("Princess", VariableType.CHARACTER, s);
			rescueParams[2] = GetVariable("Tower", VariableType.LOCATION, s);
			rescueParams[3] = GetVariable("key", VariableType.ITEM, s);
			gST[1] = sT[1].Execute(s, rescueParams)[0];
		}
		
		for(Operator gO : gST){
			if(gO == null)
			{
				return null;
			}
		}
		
		groundedStory.SetSubTasks(gST);
		
		return new Operator[]{groundedStory};
	}
	
	//Used to bind location parameters in predicates
	public static PlanningVariable GetLocationHelper(PlanningVariable c, State cS){
		for(Predicate p: cS.GetFacts()){
			if(p.GetAction() == Condition.AT){
				PlanningVariable[] para = p.GetParameters();
				if(para[0] == c){
					return para[1];
				}
			}
			else if(p.GetAction() == Condition.HOLDS){
				PlanningVariable[] para = p.GetParameters();
				if(para[1] == c){
					return GetLocationHelper(para[0], cS);
				}
			}
		}
		
		return null;
	}
	
	//Used to bind location parameters in predicates
		public static PlanningVariable GetHolderHelper(PlanningVariable c, State cS){
			for(Predicate p: cS.GetFacts()){
				if(p.GetAction() == Condition.HOLDS){
					PlanningVariable[] para = p.GetParameters();
					if(para[1] == c){
						return para[0];
					}
				}
			}
			
			return null;
		}
	
	//Used to bind location parameters in predicates
	public static PlanningVariable GetVariable(String variableName, VariableType t, State cS){
		for(PlanningVariable p: cS.GetVariables()){
			if(p.GetName().equals(variableName) && p.GetType() == t){
				return p;
			}
		}
			
		return null;
	}
	
	static Operator TestGroundedOp(PlanningVariable[] p, State s, Operator o){
		Operator groundedOp = o.BindVariables(p);
		if(groundedOp == null){
			System.err.println("Can't ground op");
		}
		
		if(Prover.MeetsPrecondition(s, groundedOp.GetPreconditions())){
			return groundedOp;
		}
		else
		{
			return null;
		}
	}
}

package StoryGenerator;
import java.util.ArrayList;

import HardCodedDomain.DomainDebug;
import HardCodedDomain.HCMethods;
import HardCodedDomain.HCMethods.Method;

public class Operator {
	
	Method headId;
	
	//If this operator needs to be performed as part of a sequence on the same level
	int order;
	
	PlanningVariable[] parameters;
	Predicate[] preconditions;
	
	Operator[] subTasks;
	boolean grounded = false;
	
	Predicate[] add;
	Predicate[] del;
	
	public Operator(Method hId, PlanningVariable[] para, int o, Predicate[] pre, Operator[] sT, Predicate[] posEffects, Predicate[] negEffects){
		headId= hId; parameters = para; order = o; preconditions = pre; subTasks = sT; add = posEffects; del = negEffects;
	}
	
	public Operator(Method hId, PlanningVariable[] para, int o, Predicate[] pre, Operator[] sT, Predicate[] posEffects, Predicate[] negEffects, boolean g){
		headId= hId; parameters = para; order = o; preconditions = pre; subTasks = sT; add = posEffects; del = negEffects; grounded = g;
	}
	
	public Operator[] Execute(State curState, PlanningVariable[] para){
		System.out.print("\nExecuting: ");
		DomainDebug.OperatorPrintSwitch(this);
		System.out.print("\n");
		if(subTasks == null){
			Operator[] gO = HCMethods.Primative(curState, this, para);
			
			if(gO != null){
				curState.Apply(gO[0]);
			}
			
			DomainDebug.PrintDomainFacts(curState);
			
			return gO;
		}
		
		switch(headId){
			case GETITEMFROMCHAR:
				return HCMethods.GetItemFromChar(curState, this, para);
			case GETITEMFROMLOC:
				return HCMethods.GetItemFromLoc(curState, this , para);
			case GETKEY:
				return HCMethods.GetKey(curState, this, para);
			case KIDNAP:
				return HCMethods.Kidnap(curState, this, para);
			case LOOTANDKILL:
				return HCMethods.LootandKill(curState, this, para);
			case RESCUE:
				return HCMethods.Rescue(curState, this, para);
			case STEAL:
				return HCMethods.Steal(curState, this, para);
			case STORY:
				return HCMethods.Story(curState, this);
			default:
				break;
		}
		
		return null;
	}
	
	/*void TotallyOrderedDecompose(State curState){
		//Implementation for methods with no options
		ArrayList<Operator[]> taskId = new ArrayList<Operator[]>();
		
		for(Operator o : subTasks){
			taskId.add(o.Execute(curState));
		}
		
		return GetOrderedTasks(taskId);
		
	}*/
	
	/*boolean ValidTask(State curState){
		return Prover.MeetsPrecondition(curState, preconditions);
	}*/
	
	public int GetOrder(){
		return order;
	}
	
	Method[] GetOrderedTasks(ArrayList<Method[]> taskIdList){
		ArrayList<Method> taskId = new ArrayList<Method>();
		
		for(Method[] tasks : taskIdList){
			for(Method t : tasks){
				taskId.add(t);
			}
		}
		
		return (Method[]) taskId.toArray();
		//return ListUtil.ConvertIntegerList(taskId);
	}
	
	public Operator BindVariables(PlanningVariable[] p){
		if(parameters == null){
			return new Operator(headId, null, 1, null, subTasks, null, null);
		}
		
		if(parameters.length == p.length){
			for(int i =0; i < parameters.length; i++){
				if(parameters[i].GetType() != p[i].GetType()){
					System.err.println("Incorrect variable type when binding parameter.");
					return null;
				}
			}
			
			Predicate[] newPre = MakeNewPredicateList(preconditions, p);
			Predicate[] newAdd = MakeNewPredicateList(add, p);
			Predicate[] newDel = MakeNewPredicateList(del, p);
			
			return new Operator(headId, p, 1, newPre, subTasks, newAdd, newDel);
		}

		System.err.println("Incorrect number of variables when binding parameters");
		return null;	
	}
	
	Predicate[] MakeNewPredicateList(Predicate[] pL, PlanningVariable[] p){
		if(pL != null){
			Predicate[] newPList = new Predicate[pL.length];
			
			for(int j = 0; j < newPList.length; j++){
				newPList[j] = BindVariablesInPreds(pL[j], p);
			}
			
			return newPList;
		}
		else
		{
			return null;
		}
	}
	
	Predicate BindVariablesInPreds(Predicate pred, PlanningVariable[] pV){
		int[] predParamsPos = pred.GetParameterPositions();
		
		if(predParamsPos != null){
			PlanningVariable[] predParams = new PlanningVariable[pred.GetParameters().length];
			for(int i = 0; i < predParamsPos.length; i++){
				predParams[i] = pV[predParamsPos[i]];
			}
			
			return pred.GetBoundPredicate(predParams);
		}
		else
		{
			return pred;
		}
	}
	
	public PlanningVariable[] GetParameters(){
		return parameters;
	}
	
	public Predicate[] GetPreconditions(){
		return preconditions;	
	}
	
	public Operator[] GetSubTasks(){
		return subTasks;
	}
	
	public Predicate[] GetAddList(){
		return add;
	}
	
	public Predicate[] GetDelList(){
		return del;
	}
	
	public Method GetMethodId(){
		return headId;
	}
	
	public void SetSubTasks(Operator[] sub){
		subTasks = sub;
	}
}

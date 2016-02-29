package Utilities;

import java.util.ArrayList;

import StoryGenerator.Operator;
import StoryGenerator.Predicate;

public final class ListUtil {
	
	//Made private so that this class can't be instantiated. 
	private ListUtil(){}
	
	public static int[] ConvertIntegerList(ArrayList<Integer> integerList){
		
		int[] intList = new int[integerList.size()];
		
		for(int i = 0; i < intList.length; i++){
			intList[i] = integerList.get(i).intValue();
		}
		
		return intList;
	}
	
	public static Predicate[] ConvertPredicateList(ArrayList<Predicate> predicateList){
		
		Predicate[] pList = new Predicate[predicateList.size()];
		
		for(int i = 0; i < pList.length; i++){
			pList[i] = predicateList.get(i);
		}
		
		return pList;
	}
	
	public static Operator[] ConvertOperatorList(ArrayList<Operator> opList){
		
		Operator[] oList = new Operator[opList.size()];
		
		for(int i = 0; i < oList.length; i++){
			oList[i] = opList.get(i);
		}
		
		return oList;
	}

}

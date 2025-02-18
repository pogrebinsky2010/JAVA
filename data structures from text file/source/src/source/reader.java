package hw5;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

@SuppressWarnings("serial")
public class reader extends JFrame {
	

	public reader(){
		GUI.makeGui(1200, 1200);
	}

	public static void main(String[] args){
		new reader();
	
	}  
	
	
	public static ArrayList<String> readFile(){
		ArrayList<String> Name = new ArrayList<String>();
	
		ArrayList<String> phoneNumber = new ArrayList<String>();
	
	    File file = new File("phonebook.txt");
		 try{
			
				 Scanner input = new Scanner(file);
				 int count = 0;
				 while(input.hasNext()){
					
					 phoneNumber.add(input.next());
					 Name.add(input.next().replace(",", "")+ " " + input.next().replace(",", "") + " " +  phoneNumber.get(count));
					 count++;
					 
				 }
				 input.close();
				 
		 }catch (FileNotFoundException ex){
			 System.out.print("file not found");
		 }	
	
		 return Name; 
	}


	public static ArrayList<String>selectionSort(ArrayList<String> array)
	{
		ArrayList<String> sortedArray = new ArrayList<String>();
		String minString = " "; // i initialize this to empty b/c it is assigned its 'initial value' inside a conditional..(compile error)
		String compareString;
		int firstLoopSize = array.size();
		int arrayIndex = 0; // use this so i can keep track of which array item to delete from the old array when making a new one;
		                  // i tried just deleting the minString instead but it took a couple sec.s longer.
		for(int k = 0; k< firstLoopSize; k++)
		{
			for(int i = 0; i< array.size(); i++)
			{
			
				if(i == 0)
				{
					minString = array.get(i);
				}
				compareString = array.get(i);
				if(compareString.compareTo(minString) <= 0)
				{
					minString = compareString;
					arrayIndex = i;
				}
		
		}
		sortedArray.add(minString);
		array.remove(arrayIndex);
		
	}
		
	return sortedArray;	
	}
	public static double getMergeNanoTime(){
		
		ArrayList<String> mergeArray = new ArrayList<String>();
		mergeArray = readFile();
		
		  long timeStart = System.nanoTime();
		  mergeSort(mergeArray);
		  long timeStop = System.nanoTime();
		  long mergeTime = timeStop - timeStart;
		  
		  return mergeTime;
	}
	public static double getSelectionNanoTime(){
		
		ArrayList<String>selectionArray = new ArrayList<String>();
		
		selectionArray = readFile();
		
		  long timeStart = System.nanoTime();
		  selectionSort(selectionArray);
		  long timeStop = System.nanoTime();
		  long selectionTime = timeStop - timeStart;
		  
		  return selectionTime;
	}
	public static ArrayList<String> mergeSort(ArrayList<String> userArray){
		
		ArrayList<String> newArray = userArray;
		if (userArray.size() > 1){
			int arraySize = newArray.size();
			ArrayList<String> left = new ArrayList<String>();
			ArrayList<String> right = new ArrayList<String>();
			int middle = arraySize / 2;
			
			for (int i = 0; i < middle; i++){
				left.add(newArray.get(i));
			}
			
			for (int i = middle; i < arraySize; i++){
				right.add(newArray.get(i));
			}
			
			left = mergeSort(left);
			right = mergeSort(right);
			
			ArrayList<String> result = mergeSubArrays(left, right);
			newArray = result;
		}
	
		return newArray;
	}
	private static ArrayList<String> mergeSubArrays(ArrayList<String> left, ArrayList<String> right)
	{
		ArrayList<String> mergedArray = new ArrayList<String>();
			
			while (left.size() > 0 || right.size() > 0 )
			{
				int lsize = left.size(), rsize = right.size();
				
				if (lsize > 0 && rsize > 0)
				{
					
					if (left.get(0).compareTo(right.get(0)) < 0)
					{
						mergedArray.add(left.get(0));
						left.remove(0);
					} 
					else 
					{
						mergedArray.add(right.get(0));
						right.remove(0);
				    }
				}
				else if (lsize > 0)
				{
					mergedArray.add(left.get(0));
					left.remove(0);
				}
				else if (rsize > 0)
				{
					mergedArray.add(right.get(0));
					right.remove(0);
				}
			}
			
			return mergedArray;
		}
	public static boolean isSorted(ArrayList<String>array)
	{
		
		String minimum = " ", comparable;
		int count = 0;
		for(int i = 0; i < array.size(); i++)
		{
			comparable = array.get(i);
			if(count <1)
			{
				minimum = comparable;
				
				count++;
			}
			if(comparable.compareTo(minimum) < 0)
			{
				return false;
			}
			else{
				
				count++;
			}	
		}
		return true;
	}
		
}

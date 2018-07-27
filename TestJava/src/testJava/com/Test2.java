package testJava.com;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Test2 {

	public static void main(String[] args) {
		 ArrayList<String> al=new ArrayList<String>();  
		  al.add("Ravi");  
		  al.add("Vijay");  
		  al.add("Ajay");  
		  ArrayList<String> al2=new ArrayList<String>();  
		  al2.add("Sonoo");  
		  al2.add("Hanumat");  
		  al.addAll(al2);//adding second list in first list  
		  java.util.Iterator<String> itr=al.iterator();  
		  while(itr.hasNext()){  
		   System.out.println(itr.next());  
		  }  
		 }  
	}



package testJava.com;

import java.util.ArrayList;
import java.util.Iterator;

public class Test1 {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("Welcome ");
		list.add("Test ");
		list.add("Java ");
		list.add("Test33 ");
		list.add("Test123 ");
		Iterator itr =list.iterator();
		while(itr.hasNext()){
			
					System.out.println(itr.next());
		}
	}

}

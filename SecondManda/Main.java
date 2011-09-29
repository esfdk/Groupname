package SecondManda;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args){
		Producer p = new Producer();
		Consumer c = new Consumer();
		Scanner in = new Scanner(System.in);
		
		String msg;
		do
		{
			System.out.println("Enter message:");
			msg = in.nextLine();
			System.out.println("Sending: " + msg);
			if (msg != "") p.raise(msg);
		} while (msg != "");	
		p.raise("herpderp");
		while (true){	
			System.out.println(c.receive());
		}
	}
}

package SecondManda;

public class Main {
	
	public static void main(String[] args){
		Producer p = new Producer();
		System.out.println("p");
		p.raise("herpderp");
		System.out.println("raise");
		Consumer c = new Consumer();
		System.out.println("c");
		c.receive();
		System.out.println("receive");
	}
}

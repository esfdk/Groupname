package ThirdManda;

import java.util.*; 
import java.io.*;

public class MessageBody implements Serializable {

	public HashMap<String,Integer> VectorTimestamp;
	 
	 public String Message;
	 public String Sender;

	 // added for the clock synchronizer
	 public String Header;
}

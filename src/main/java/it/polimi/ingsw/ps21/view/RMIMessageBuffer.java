package it.polimi.ingsw.ps21.view;

import java.rmi.Remote;
import java.util.ArrayDeque;
import java.util.Queue;

public class RMIMessageBuffer implements Remote{

	private Queue<String> buffer ;
	
	public RMIMessageBuffer() {
		buffer = new ArrayDeque<>();
	}
	
	public String nextLine(){
		if (buffer.isEmpty()) return "";
		else return buffer.poll();
	}
	
	public void write(String output){
		if (output.compareTo("")!=0){
			buffer.add(output);
		}
	}
}

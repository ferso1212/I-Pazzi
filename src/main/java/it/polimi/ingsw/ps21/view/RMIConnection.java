package it.polimi.ingsw.ps21.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Stream;

public class RMIConnection implements Connection, Runnable {

	private String name;
	private Queue<String> input;
	private Queue<String> output;
	public RMIConnection(String inputName) {
		name = inputName;
		input = new ArrayDeque<>();
		output = new ArrayDeque<>();
	}

	@Override
	public void run() {
		
		
	}

	@Override
	public void sendMessage(String mess) {
		input.add(mess);
	}
	
	public String getMessageFromClient(){
		while(input.peek()==null);
		return input.poll();
	}
	public String getMessageFromServer(){
		while(output.peek()==null);
		return output.poll();
	}

	@Override
	public String getName() {
		return name;
	}

}

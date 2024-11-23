package main;

import java.util.Scanner;

import music.songOrchestrator.Orchestrator;
import utilitaries.MealyMachine;

public class Main {
    public static void main(String[] args) {

    	Scanner s = new Scanner(System.in);
    	Orchestrator o = new Orchestrator(new MealyMachine(null, null));
    	o.getControl().setBpm(240);
    	
    	while(true) {
    		String action = s.next();
    		System.out.println(action);
    		if(action.equals("stop")) {
    			o.stop();
    			System.out.println("Stopped!");
    		}
    		if(action.equals("pause")) {
    			o.pause();
    			System.out.println("Paused!");
    		}
    		if(action.equals("play")) {
    			o.play();
    			System.out.println("Playing!");
    		}
    	}
    	
    	
    }
}
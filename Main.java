/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Jihwan Lee
 * jl54387
 * 16445
 * Kevin Liang
 * kgl392
 * 16445
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4; // cannot be in default package
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        //System.out.println("GLHF");
        
        int stepCount;
        String className;
        String firstCommand;
        boolean commandError = false;
        boolean processError = false;
        
        System.out.print("critters> ");
        String nextCommand = kb.nextLine();
        String[] inputList = nextCommand.split("\\s");
        if (inputList.length != 0){
        	firstCommand = inputList[0];
        }
        else{
        	firstCommand = " ";
        }
        while(!(firstCommand.equals("quit") && inputList.length == 1)){
        	switch(firstCommand){
        		case "show": // display the world
        			if (inputList.length >= 2){
        				processError = true;
        				break;
        			}
        			Critter.displayWorld();
        			break;
        		case "seed": // invoke setSeed
        			if (inputList.length >= 3){
        				processError = true;
        				break;
        			}
        			long input = Integer.parseInt(inputList[1]);
        			Critter.setSeed(input);
        			break;
        		case "step": // for step
        			try{
        				if (inputList.length >= 3){
        					processError = true;
        					break;
        				}
        				stepCount = 1;
        				if(inputList.length > 1){
        					stepCount = Integer.parseInt(inputList[1]);
        				}

        				for(int i = 0; i < stepCount; i++){
        					Critter.worldTimeStep();
        				}
        			}
        			catch(Exception e){
        				processError = true;
        			}
        			break;
        		case "make": // make critter command
        			try{
        				if (inputList.length >= 4){
        					processError = true;
        					break;
        				}
        				stepCount = 1;
        				className = inputList[1];
        				if (inputList.length > 2){
        					stepCount = Integer.parseInt(inputList[2]);
        				}

        				for(int i = 0; i < stepCount; i++){
        					Critter.makeCritter(className);
        				}
        			}
        			catch(Exception e){
        				processError = true;
        			}
        			break;
        		case "stats": // calls class stats
        			try{
        				if (inputList.length >= 2 && inputList.length < 3){
        					className = inputList[1];
        					String fullName = myPackage + "." + className;
        					Class critterClass = Class.forName(fullName);
        					java.util.List<Critter> critterClassList = Critter.getInstances(className);
        					Class<?>[] types = {List.class};
        					Method runStatsMethod = critterClass.getMethod("runStats", types);
        					runStatsMethod.invoke(null, critterClassList);
        				}
        				else{
            				processError = true;
            				break;
            			}
        			}
        			catch(Exception e){
        				processError = true;
        			}
        			break;
        		default:
        			commandError = true;
        	}
        	if (commandError){
        		System.out.println("invalid command: " + nextCommand);
        	}
        	if (processError){
        		System.out.println("error processing: " + nextCommand);
        	}
        	System.out.print("critters> ");
        	nextCommand = kb.nextLine();
        	inputList = nextCommand.split("\\s");
        	if (inputList.length != 0){
            	firstCommand = inputList[0];
            }
            else{
            	firstCommand = " ";
            }
        	commandError = false;
        	processError = false;
        }
        
        
        /* Write your code above */
        //System.out.flush();
    }
}

/**
 * Java program used for project 7 of Nand2Tetris. Translates virtual machine files
 * into assembly files.
 * The input can be either a single VM file or a whole directory of VM files. 
 * After translating the VM file/s to assembly code the output should be one 
 * assembly file.
 *
 * Compile Command:
 * javac VMTranslator.java 
 * 
 * run command:
 * java VMTranslator INPUT_FILE_NAME 
 * or 
 * java VMTranslator INPUT_FILE_DIRECTORY <-(is not implemented)
 * 
 * @author AAbouseada
 * @version 03182020
 */
import java.io.*;
import java.util.*;

public class VMTranslator {
    
    /**
     * list of arithmetic logic commands
     */
    private static final Set<String> arithmeticLogicComm = new HashSet<>();
    
    /**
     * BufferedWriter used to created .asm file
     */
    private static BufferedWriter fileOut;
   
    /**
     * position is used to give each label a unique factor
     * Example: 
     * "@FALSE_EQ" + position
     * "(FINAL_EQ" + position +")"
     * would both print in the same iteration giving that label (FINAL_EQ#)
     * a unique number so that if the command is repeated again in it would
     * have a unique different label
     */
    private static int position;
    
    /**
     * prints out the input string and attaches a new line character at the end
     * @param line input string
     */
    private static void writeToFile(String line) {
        try {
            fileOut.write(line + "\r\n");
        } catch (IOException ex) {
            System.out.println("Error writing to file");
        }
    }
    
    /**
     * code for incrementing SP
     */
    private static void incrementSP() {
        writeToFile("// Increment SP");
        writeToFile("@SP");
        writeToFile("AM=M+1");
    }
    
    /**
     * code for decrementing SP
     */
    private static void decrementSP() {
        writeToFile("// Decrement SP");
        writeToFile("@SP");
        writeToFile("AM=M-1");
    }
    
    /**
     * Handles the parsing of a single .vm file and encapsulates access to the 
     * input code. It reads VM commands, parses them, and provides convenient 
     * access to their components. It removes all white space and comments.
     * 
     * @param fileInput The input file that contains the VM code.
     * @param commands a queue of arrays that will contain the commands 
     * after they have been parsed.
     */
    public static void parser(BufferedReader fileInput,Queue<ArrayList<String>> commands) {
        try {
            String line = fileInput.readLine();
            while(line != null) {
                if(line.contains("//")) {
                    line = line.substring(0,line.indexOf("//"));
                    line = line.trim();
                }
                if(!line.isEmpty()) {
                    ArrayList<String> temp = new ArrayList<>();
                    if(line.contains("pop") || line.contains("push")) {
                        // commmand segement value
                        
                        // command
                        temp.add(line.substring(0,line.indexOf(" ")));
                        line = line.substring(line.indexOf(" ") + 1);
                        // segement
                        temp.add(line.substring(0,line.indexOf(" ")));
                        line = line.substring(line.indexOf(" ") + 1);
                        // value
                        temp.add(line);
                        commands.add(temp);
                    } else if(arithmeticLogicComm.contains(line)) {
                        // Arithmetic Logic Command
                        temp.add(line);
                        commands.add(temp);
                    }
                }
                line = fileInput.readLine();
            }
        }catch(IOException e) {
            System.out.println("ERROR: Failed Reading From File!");
            try {
		fileInput.close();
		} 
            catch (IOException e1) {
                System.out.println("ERROR: Could Not Close Input File!");
		}
        }
    }
    
    /**
     * memoryAccessCommand translates a push or pop command from VM language to 
     * Assembly language
     * @param mac 
     */
    private static void memoryAccessCommands(ArrayList<String> mac) {
        int ramLoc;
        switch(mac.get(0)) {
            case "push" :
            {
                switch(mac.get(1)) {
                    case "constant" :
                        writeToFile("// push constant " + mac.get(2));
                        writeToFile("// on to the stack");
                        writeToFile("@" + mac.get(2));
                        writeToFile("D=A");
                        writeToFile("@SP");
                        writeToFile("A=M");
                        writeToFile("M=D");
                        break;
                    case "local" :
                        writeToFile("// push local[" + mac.get(2) + "]");
                        writeToFile("// on to the stack");
                        writeToFile("@LCL");
                        writeToFile("D=M");
                        writeToFile("@"+mac.get(2));
                        writeToFile("D=D+A");
                        writeToFile("A=D");
                        writeToFile("D=M");
                        writeToFile("@SP");
                        writeToFile("A=M");
                        writeToFile("M=D");
                        break;
                    case "argument" :
                        writeToFile("// push argument[" + mac.get(2) + "]");
                        writeToFile("// on to the stack");
                        writeToFile("@ARG");
                        writeToFile("D=M");
                        writeToFile("@"+mac.get(2));
                        writeToFile("D=D+A");
                        writeToFile("A=D");
                        writeToFile("D=M");
                        writeToFile("@SP");
                        writeToFile("A=M");
                        writeToFile("M=D");
                        break;
                    case "this" :
                        writeToFile("// push this[" + mac.get(2) + "]");
                        writeToFile("// on to the stack");
                        writeToFile("@THIS");
                        writeToFile("D=M");
                        writeToFile("@"+mac.get(2));
                        writeToFile("D=D+A");
                        writeToFile("A=D");
                        writeToFile("D=M");
                        writeToFile("@SP");
                        writeToFile("A=M");
                        writeToFile("M=D");                       
                        break;
                    case "that" :
                        writeToFile("// push that[" + mac.get(2) + "]");
                        writeToFile("// on to the stack");
                        writeToFile("@THAT");
                        writeToFile("D=M");
                        writeToFile("@"+mac.get(2));
                        writeToFile("D=D+A");
                        writeToFile("A=D");
                        writeToFile("D=M");
                        writeToFile("@SP");
                        writeToFile("A=M");
                        writeToFile("M=D");
                        break;
                    case "temp" :
                        writeToFile("// push temp[" + mac.get(2) + "]");
                        writeToFile("// on to the stack");
                        ramLoc = 5 + Integer.parseInt(mac.get(2));
                        writeToFile("@" + ramLoc);
                        writeToFile("D=M");
                        writeToFile("@SP");
                        writeToFile("A=M");
                        writeToFile("M=D");
                        break;
                    case "static" :
                        writeToFile("// push static[" + mac.get(2) + "]");
                        writeToFile("// on to the stack");
                        ramLoc = 16 + Integer.parseInt(mac.get(2));
                        writeToFile("@" + ramLoc);
                        writeToFile("D=M");
                        writeToFile("@SP");
                        writeToFile("A=M");
                        writeToFile("M=D");
                        break;
                    case "pointer" :
                        writeToFile("// push pointer[" + mac.get(2) + "]");
                        writeToFile("// on to the stack");
                        ramLoc = 3 + Integer.parseInt(mac.get(2));
                        writeToFile("@" + ramLoc);
                        writeToFile("D=M");
                        writeToFile("@SP");
                        writeToFile("A=M");
                        writeToFile("M=D");
                        break;                        
                }
                incrementSP();
            }
                break;
            case "pop" :
            {
                 switch(mac.get(1)) {
                    case "constant" :

                    case "local" :
                        writeToFile("// pop the last item off the stack");
                        writeToFile("// and input it at local[" + mac.get(2) + "]");
                        writeToFile("@LCL");
                        writeToFile("D=M");
                        writeToFile("@"+mac.get(2));
                        writeToFile("D=D+A");
                        writeToFile("@R13");
                        writeToFile("M=D");
                        decrementSP();                        
                        writeToFile("D=M");
                        writeToFile("@R13");
                        writeToFile("A=M");
                        writeToFile("M=D");
                        break;
                    case "argument" :
                        writeToFile("// pop the last item off the stack");
                        writeToFile("// and input it at argument[" + mac.get(2) + "]");
                        writeToFile("@ARG");
                        writeToFile("D=M");
                        writeToFile("@"+mac.get(2));
                        writeToFile("D=D+A");
                        writeToFile("@R13");
                        writeToFile("M=D");
                        decrementSP();                        
                        writeToFile("D=M");
                        writeToFile("@R13");
                        writeToFile("A=M");
                        writeToFile("M=D");
                        break;
                    case "this" :
                         writeToFile("// pop the last item off the stack");
                        writeToFile("// and input it at this[" + mac.get(2) + "]");
                        writeToFile("@THIS");
                        writeToFile("D=M");
                        writeToFile("@"+mac.get(2));
                        writeToFile("D=D+A");
                        writeToFile("@R13");
                        writeToFile("M=D");
                        decrementSP();                        
                        writeToFile("D=M");
                        writeToFile("@R13");
                        writeToFile("A=M");
                        writeToFile("M=D");                      
                        break;
                    case "that" :
                        writeToFile("// pop the last item off the stack");
                        writeToFile("// and input it at that[" + mac.get(2) + "]");
                        writeToFile("@THAT");
                        writeToFile("D=M");
                        writeToFile("@"+mac.get(2));
                        writeToFile("D=D+A");
                        writeToFile("@R13");
                        writeToFile("M=D");
                        decrementSP();                        
                        writeToFile("D=M");
                        writeToFile("@R13");
                        writeToFile("A=M");
                        writeToFile("M=D");    
                        break;
                    case "temp" :
                        writeToFile("// pop the last item off the stack");
                        writeToFile("// and input it at temp[" + mac.get(2) + "]");
                        ramLoc = 5 + Integer.parseInt(mac.get(2));
                        writeToFile("@" + ramLoc);
                        writeToFile("D=M");
                        decrementSP();                        
                        writeToFile("A=M");
                        writeToFile("M=D");   
                        break;
                    case "static" :
                        writeToFile("// pop the last item off the stack");
                        writeToFile("// and input it at static[" + mac.get(2) + "]");
                        decrementSP();  
                        writeToFile("D=M");
                        ramLoc = 16 + Integer.parseInt(mac.get(2));
                        writeToFile("@" + ramLoc);
                        writeToFile("M=D");
                        break;
                    case "pointer" :
                        writeToFile("// pop the last item off the stack");
                        writeToFile("// and input it at pointer[" + mac.get(2) + "]");
                        decrementSP();  
                        writeToFile("D=M");
                        ramLoc = 3 + Integer.parseInt(mac.get(2));
                        writeToFile("@" + ramLoc);
                        writeToFile("M=D");
                        break;
                }
            }
        } 
    }
    
    /**
     * arithmaticLogicCommmands translates VM arithmetic commands into 
     * hack assembly language.
     * @param alc is an array where each arithmetic logic commands is 
     * separated by individual words into separate elements
     * example ["add"]
     */
    private static void arithmeticLogicCommands(ArrayList<String> alc ) {
        switch(alc.get(0)) {
            case "add" :
                    writeToFile("// Add the last two things together");
                    decrementSP();
                    writeToFile("D=M");
                    decrementSP();
                    writeToFile("M=M+D");
                break;
            case "sub" :
                    writeToFile("// Subtract the last two things together");
                    decrementSP();
                    writeToFile("D=M");
                    decrementSP();
                    writeToFile("M=M-D");
                break;
            case "neg" :
                    writeToFile("// negate the last thing inputed");
                    writeToFile("// M[SP]=-M[SP]");
                    decrementSP();
                    writeToFile("M=-M");
                break; 
            case "eq" :
                    writeToFile("// Check if M[SP] == M[SP-1]");
                    writeToFile("// If true M[SP-1] = -1");
                    writeToFile("// else false M[SP-1] = 0");
                    decrementSP();
                    writeToFile("D=M");
                    decrementSP();
                    writeToFile("D=M-D");
                    writeToFile("// Are they Equal?");
                    writeToFile("@FALSE_EQ" + position);
                    writeToFile("D;JNE");
                    writeToFile("// They are equal, assign -1");
                    writeToFile("D=-1");
                    writeToFile("@FINAL_EQ" + position);
                    writeToFile("0;JMP");
                    writeToFile("(FALSE_EQ" + position +")");
                    writeToFile("// They are not equal, assign 0");
                    writeToFile("D=0");
                    writeToFile("(FINAL_EQ" + position +")");
                    writeToFile("@SP");
                    writeToFile("A=M");
                    writeToFile("M=D");
                break;
            case "gt" :
                    writeToFile("// Check if M[SP-1] > M[SP]");
                    writeToFile("// If true M[SP-1] = -1");
                    writeToFile("// else false M[SP-1] = 0");
                    decrementSP();
                    writeToFile("D=M");
                    decrementSP();
                    writeToFile("D=M-D");
                    writeToFile("// is M[SP-1] > M[SP]?");
                    writeToFile("@FALSE_GT" + position);
                    writeToFile("D;JLE");
                    writeToFile("// M[SP-1] is greater, assign -1");
                    writeToFile("D=-1");
                    writeToFile("@FINAL_GT" + position);
                    writeToFile("0;JMP");
                    writeToFile("(FALSE_GT" + position + ")");
                    writeToFile("// M[SP-1] is not greater, assign 0");
                    writeToFile("D=0");
                    writeToFile("(FINAL_GT" + position + ")");
                    writeToFile("@SP");
                    writeToFile("A=M");
                    writeToFile("M=D");
                break;
            case "lt" :
                    writeToFile("// Check if M[SP-1] < M[SP]");
                    writeToFile("// If true M[SP-1] = -1");
                    writeToFile("// else false M[SP-1] = 0");
                    decrementSP();
                    writeToFile("D=M");
                    decrementSP();
                    writeToFile("D=M-D");
                    writeToFile("// is M[SP-1] < M[SP]?");
                    writeToFile("@FALSE_LT" + position);
                    writeToFile("D;JGE");
                    writeToFile("// M[SP-1] is less than M[SP], assign -1");
                    writeToFile("D=-1");
                    writeToFile("@FINAL_LT" + position);
                    writeToFile("0;JMP");
                    writeToFile("(FALSE_LT" + position + ")");
                    writeToFile("// M[SP-1] is greater than M[SP], assign 0");
                    writeToFile("D=0");
                    writeToFile("(FINAL_LT" + position + ")");
                    writeToFile("@SP");
                    writeToFile("A=M");
                    writeToFile("M=D");
                break;
            case "and" :
                    writeToFile("// Calculate bitwise and");
                    writeToFile("// M[SP-1] = M[SP-1] & M[SP]");
                    decrementSP();
                    writeToFile("D=M");
                    decrementSP();
                    writeToFile("D=D&M");
                    writeToFile("M=D");
                break;
            case "or" :
                    writeToFile("// Calculate bitwise or");
                    writeToFile("// M[SP-1] = M[SP-1] | M[SP]");
                    decrementSP();
                    writeToFile("D=M");
                    decrementSP();
                    writeToFile("D=D|M");
                    writeToFile("M=D");
                break;
            case "not" :
                    writeToFile("// Calculate bitwise not");
                    writeToFile("// M[SP] = !M[SP] ");
                    decrementSP();
                    writeToFile("M=!M");
                break;
        }
        incrementSP();
                
    }
    /**
     * Translates VM commands into hack assemble language and writes it out to
     * a file.
     * @param commands
     */
    public static void codeWriter(Queue<ArrayList<String>> commands) {
        while(commands.size() > 0){
            ArrayList<String> temp;
            temp = commands.remove();
            if(temp.get(0).equals("push") || temp.get(0).equals("pop")) {
                memoryAccessCommands(temp);
            }
            if(arithmeticLogicComm.contains(temp.get(0))) {
                arithmeticLogicCommands(temp);
            }
            position ++;
        }
    }
    
    
    /**
     * Constructs a Parser to parse the CM input file and a codeWriter to 
     * generate code into the corresponding output file.
     * It should also march through the VM in the input file and generate 
     * assembly code for each one of them.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        arithmeticLogicComm.addAll(Arrays.asList(new String[] {"add","sub","neg","eq","gt","lt","and","or", "not"}));
        Queue<ArrayList<String>> commands = new LinkedList<>();
        position = 0;
        String fileNameIn = args[0];
        // only works if there is one file
        String fileNameOut = fileNameIn.substring(0,fileNameIn.indexOf(".")) + ".asm";
        BufferedReader fileInput;
        try {
            fileInput = new BufferedReader(new FileReader(fileNameIn));
            fileOut = new BufferedWriter(new FileWriter(fileNameOut));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Input File Not Found!");
                return;
        }catch (IOException e) {
                System.out.println("ERROR: Creating output File!");
                return;
            }
        parser(fileInput,commands);
        codeWriter(commands);
        try {
            fileOut.close();
            fileInput.close();
	} 
        catch (IOException e1) {
            System.out.println("ERROR: Could Not Close Input or Output File!");
	}
    }
}

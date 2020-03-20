import java.io.*;
import java.util.*;
/**
 * Java program used for project 6 of Nand2Tetris. Translates assembly files
 * into hack files (binary code)
 *
 * Compile Command:
 * javac Assembler.java 
 * 
 * run command:
 * java Assembler INPUT_FILE_NAME OUTPUT_FILE_NAME
 * 
 * @author AAbouseada
 * @version 03182020
 */
public class Assembler
{
    /**
     * compute decodes a compute assembly instruction into a binary instruction.
     * two different sets of decoding depending on whether the a bit is a 1 or 0
     * @param line current assembly instruction
     * @return (a + c) binary which make up the 7 bit compute instruction.
     */
    public static String compute(String line) {
        int a = 0;
        String compute = "";
        if(line.contains("=")) { 
            compute = line.substring(line.indexOf("=") + 1);
            if(compute.contains("M")) {
                a = 1;
            }
        } else if(line.contains(";")) {
            compute = line.substring(0,line.indexOf(";"));
        }
        String cbinary = "";
        if(a == 0){
            switch(compute) {
                case "0" :
                    cbinary = "101010";
                    break;
                case "1" :
                    cbinary = "111111";
                    break;
                case "-1" :
                    cbinary = "111010";
                    break;
                case "D" :
                    cbinary = "001100";
                    break;
                case "A" :
                    cbinary = "110000";
                    break;
                case "!D" :
                    cbinary = "001101";
                    break;
                case "!A" :
                    cbinary = "110001";
                    break;
                case "-D" :
                    cbinary = "001111";
                    break;
                case "-A" :
                    cbinary = "110011";
                    break;
                case "D+1" :
                    cbinary = "011111";
                    break;
                case "A+1" :
                    cbinary = "110111";
                    break;
                case "D-1" :
                    cbinary = "001110";
                    break;
                case "A-1" :
                    cbinary = "110010";
                    break;
                case "D+A" :
                    cbinary = "000010";
                    break;
                case "D-A" :
                    cbinary = "010011";
                    break;
                case "A-D" :
                    cbinary = "000111";
                    break;
                case "D&A" :
                    cbinary = "000000";
                    break;
                case "D|A" :
                    cbinary = "010101";
                    break;
                default :
                    System.out.println("Error reading compute instruction when a = 0");
            }
        } else {
            // if a == 1
            switch(compute) {
                 case "M" :
                    cbinary = "110000";
                    break;
                 case "!M" :
                     cbinary = "110001";
                     break;
                 case "-M" :
                     cbinary = "110011";
                     break;
                 case "M+1" :
                     cbinary = "110111";
                     break;
                 case "M-1" :
                     cbinary = "110010";
                     break;
                 case "D+M" :
                     cbinary = "000010";
                     break;
                 case "D-M" :
                     cbinary = "010011";
                     break;
                 case "M-D" :
                     cbinary = "000111";
                     break;
                 case "D&M" :
                     cbinary = "000000";
                     break;
                 case "D|M" :
                     cbinary =  "010101";
                     break;
                 default :
                     System.out.println("Error reading compute instruction when a = 1");  
            }
            
        }
        return a + cbinary;
    }
    
    /**
     * destination returns the binary instruction for the 
     * @param line current assembly instruction
     * @return dbinary 3 bit binary destination instruction
     */
    public static String destination(String line) {
        // binary value of the destination
        String dbinary = "";
        // destination of where to store ALU output
        String destination = "null";
        if(line.contains("=")) {
            destination = line.substring(0, line.indexOf("="));
        }

        switch(destination) {
            case "null" :
                dbinary = "000";
                break;
            case "M" :
                dbinary = "001";
                break;
            case "D" :
                dbinary = "010";
                break;
            case "MD" :
                dbinary = "011";
                break;
            case "A" :
                dbinary = "100";
                break;
            case "AM" :
                dbinary = "101";
                break;
            case "AD" :
                dbinary = "110";
                break;
            case "AMD" :
                dbinary = "111";
                break;
            default :
                System.out.println("Error reading destination");
        }
        return dbinary;
    }
    
    /**
     * jump decodes a jump instruction in assembly code and
     * return the binary instruction for it.
     * @param line current assembly instruction
     * @return jbinary a 3 bit binary jump instruction
     */
    public static String jump(String line) {
        // jump instruction if any
        String jump = "null";
        // jump binary instruction
        String jbinary = "";
        if(line.contains(";")) {
            jump = line.substring(line.indexOf(";")+1);

        }            
        switch(jump) {
            case "null" :
                jbinary = "000";
                break;
            case "JGT" :
                jbinary = "001";
                break;
            case "JEQ" :
                jbinary = "010";
                break;
            case "JGE" :
                jbinary = "011";
                break;
            case "JLT" :
                jbinary = "100";
                break;
            case "JNE" :
                jbinary = "101";
                break;
            case "JLE" :
                jbinary = "110";
                break;
            case "JMP" :
                jbinary = "111";
                break;
            default :
                System.out.println("Error Reading jump instruction");
        }
        return jbinary;
    }
    /**
     * cInstruction takes in a string converts it into a binary c instruction
     * and returns that binary instruction as a string
     *
     * @param  line  a sample parameter for a method
     * @return the binary output for a C-Instruction
     */
    public static String cInstruction(String line)
    {
        // Binary format: 111a c1c2c3c4c5c6 d1d2d3 j1j2j3
        String binary;
        binary = "111";
        //compute instruction
        binary = binary.concat(compute(line));
        //destination instruction
        binary = binary.concat(destination(line));
        //jump instruction
        binary = binary.concat(jump(line));
        return binary;
    }
    
    /**
     * aInstruction takes in a string and converts it into a binary a-instruction
     * and returns that binary instruction as a string. The exception is if its
     * a user constant or a symbol its ignore until replaceAndWrite
     *
     * @param  line  a sample parameter for a method
     * @return binary version of the A-Instruction
     */
    public static String aInstruction(String line)
    {
        // Binary format: 0vvv vvvv vvvv vvvv
        // n is the number to be converted into a 16-bit binary #
        int n;
        String newLine = line.substring(1);
        if(newLine.charAt(0)=='R' && Character.isDigit(newLine.charAt(1))) {
            // its a register call
            newLine = newLine.substring(1);
            n = Integer.parseInt(newLine);
        }else if(newLine.matches("\\d+")) {
            // its a decimal
            n = Integer.parseInt(newLine);
        }else{
            // its a undefined label
            // its ignored until the replaceAndWrite method is called
            return newLine;
        }
        newLine = String.format("%16s", Integer.toBinaryString(n)).replaceAll(" ", "0");
        return newLine;
    }

    /**
     * replaceAndWrite decodes any constants and symbols into binary instructions.
     * It then prints to a file, one line per binary instruction.
     * @param fileOut the file chosen by the user
     * @param binaryInstructions the binary instructions
     * @param symbols current symbols
     */
    public static void replaceAndWrite(BufferedWriter fileOut, Queue<String> binaryInstructions, Map<String,Integer> symbols) {
        String line;
        Map<String,Integer> userConstants = new HashMap<>();
        while(binaryInstructions.size()>0){
            line = binaryInstructions.remove();
            if(symbols.containsKey(line)) {
                //convert the symbol to binary instructions
                int n = symbols.get(line);
                line = String.format("%16s", Integer.toBinaryString(n)).replaceAll(" ", "0");
            } else if(!line.matches("\\d+")){
                int n;
                if(!userConstants.containsKey(line)){
                    userConstants.put(line, 16+userConstants.size());
                }
                n = userConstants.get(line);
                line = String.format("%16s", Integer.toBinaryString(n)).replaceAll(" ", "0");
            }
                
            try {
                fileOut.write(line);
                fileOut.newLine();
            }catch(IOException ex) {
                System.out.println("Error writing to file");
            }
        }
        
    }
    /**
     * Main method of the program
     * @param args args[0] contains the file input name including the extension.
     * args[1] contains the file output name including the file extension
     */
    public static void main (String[] args) {
        int lineNum=0;
        //map that holds all known symbols
        Map<String,Integer> symbols = new HashMap<>();
        // Queue that holds binary instruction before they are written to file
        Queue<String> binaryInstructions = new LinkedList<>();
        String fileNameIn = args[0];
        String fileNameOut = args[1];
        BufferedReader fileInput;
        BufferedWriter fileOut;
        try {
            fileInput = new BufferedReader(new FileReader(fileNameIn));
            fileOut = new BufferedWriter(new FileWriter(fileNameOut));
            } catch (FileNotFoundException e) {
                System.out.println("ERROR: Input File Not Found!");
                return;
            }
            catch (IOException e) {
                System.out.println("ERROR: Creating output File!");
                return;
            }
        String line; 
        // Predfined Symbols
        symbols.put("SP",0);
        symbols.put("LCL",1);
        symbols.put("ARG", 2);
        symbols.put("THIS",3);
        symbols.put("THAT", 4);
        symbols.put("SCREEN", 16384);
        symbols.put("KBD", 24576);
        try {
            line = fileInput.readLine();
            while(line != null){
                boolean writeOut = true;
                // removing comments and white space
                if(line.contains("//")) {
                    line = line.substring(0,line.indexOf("//"));
                }
                line = line.trim();
                if(!line.isEmpty()){
                    if(line.contains("@")){
                        // Found an A-Instruction
                        line = aInstruction(line);
                    } else if(line.contains("=") || line.contains(";")) {
                        // Found a C-Instruction
                        line = cInstruction(line);
                    } else if(line.contains("(") && line.contains(")")){
                        // Found a symbol
                        writeOut = false;
                        line = line.substring(1,line.length()-1); 
                        symbols.put(line, lineNum);
                    }
                    if(writeOut) {
                        binaryInstructions.add(line);
                        lineNum++;
                    }
                    // add the line to the queue
                }
                // read the next line
                line = fileInput.readLine();
            }
        } 
        catch (IOException e) {
            System.out.println("ERROR: Failed Reading From File!");
            try {
		fileInput.close();
		} 
            catch (IOException e1) {
                System.out.println("ERROR: Could Not Close Input File!");
		}
            return;
        }
       	try {
            fileInput.close();
	} 
        catch (IOException e1) {
            System.out.println("ERROR: Could Not Close Input File!");
            return;
	}
        replaceAndWrite(fileOut,binaryInstructions,symbols);
        try {
            fileOut.close();
	} 
        catch (IOException e1) {
            System.out.println("ERROR: Could Not Close Input File!");
	}
    }
}
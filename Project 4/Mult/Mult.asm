// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Multiplies RAM[1] * RAM[0] and stores it in RAM[2]

// set R2=0
@R2
M=0		

// set D=R0 and check if its 0
// if its 0 go to END
@R0
D=M 		// D=R0
@END
D;JLE		// If R0 <= 0 GOTO END

// set D=R1 and check if its 0
// if its 0 go to END
@R1
D=M 		// D=R1
@END
D;JLE		// If R1 <= 0 GOTO END

// Start of loop
(LOOP)

//  set D = R1 and add R2 = R2 + R1
    @R2
    D=M 		// D = R2
    @R1
    D=D+M		// R2 = R2 + R1

//Write the result to R2
    @R2
    M=D

// Reduce R0 by 1 and set D to R0
    @R0
    D=M 		// D=R0
    D=D-1		// D = D-1
    @R0
    M=D 		// R0 = D

// If R0 <= 0 go to end otherwise go back to loop
   @END
   D;JLE		// If R0 <= 0 GOTO END
   @LOOP
   0;JMP		// Else go back to begining of the loop
(END)
   @END
   0;JMP		//Infinite loop


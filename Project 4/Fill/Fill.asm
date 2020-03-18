// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.


(LOOP)
// set D = KBD and check if D != 0
// then jump to black else go to white
    @KBD
    D=M
    @BLACK
    D;JGT	// JMP to BLACK if D(INPUT) > 0
    @WHITE
    D;JEQ       // JMP To White if D(INPUT) == 0

// Set screen to White
    (WHITE)
    	@SCREEN
	D=A	// D = Screen Address
	@addr
	M=D	// addr = 16384
	@0
	D=M	// D = 0
	@n
	M=D	// n = RAM[0]
	@i
	M=0	// i = 0

	(WLOOP)
		@i
		D=M	// D = i
		@n
		D=D-M	//D = D - n
		@addr
		A=M
		M=0	// RAM[addr]= 0
		@i
		M=M+1	// i = i + 1
		@32
		D=A
		@addr
		M=D+M	// addr = addr + 32
		// Jump to Black or white depending on input 
    		@KBD
    		D=M
    		@BLACK
    		D;JGT	// JMP to BLACK if D(INPUT) > 0
   		@WLOOP
    		D;JEQ       // JMP To WLOOP if D(INPUT) == 0
 // Set screen to Black   
    (BLACK)
	@SCREEN
	D=A	// D = Screen Address
	@addr
	M=D	// addr = 16384
	@0
	D=M	// D = 0
	@n
	M=D	// n = RAM[0]
	@i
	M=0	// i = 0

	(BLOOP)
		@i
		D=M	// D = i
		@n
		D=D-M	// D = D - n
		@addr
		A=M
		M=-1	// RAM[addr]=111...
		@i
		M=M+1	// i = i + 1
		@32
		D=A
		@addr
		M=D+M	// addr = addr + 32
		// Jump to Black or white depending on input 
    		@KBD
    		D=M
    		@BLOOP
    		D;JGT	// JMP to BLOOP if D(INPUT) > 0
   		@WHITE
    		D;JEQ       // JMP To White if D(INPUT) == 0

(END)
    @END
    0;JMP

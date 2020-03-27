// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/07/StackArithmetic/SimpleAdd/SimpleAdd.tst

load SimpleGt1.asm,
output-file SimpleGt1.out,
compare-to SimpleGt1.cmp,
output-list RAM[0]%D2.6.2 RAM[256]%D2.6.2;

set RAM[0] 256,  // initializes the stack pointer 

repeat 60 {      // enough cycles to complete the execution
  ticktock;
}

output;          // the stack pointer and the stack base

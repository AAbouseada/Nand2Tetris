// push 510
@510
D=A
@SP
A=M
M=D
// Increment SP
@SP
AM=M+1
@11
D=A
@R13
M=D
// Decrement SP
@SP
AM=M-1
D=M
@R13
A=M
M=D

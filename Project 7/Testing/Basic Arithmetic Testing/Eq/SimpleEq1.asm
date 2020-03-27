// input 8
@8
D=A
@SP
A=M
M=D
// Increment SP
@SP
M=M+1
// input 8
@8
D=A
@SP
A=M
M=D
// Increment SP
@SP
M=M+1
// Check if M[SP] == M[SP-1]
// If true M[SP-1] = -1
// else false M[SP-1] = 0
// Decrement SP
@SP
M=M-1
A=M
D=M
// Decrement SP
@SP
M=M-1
A=M
D=M-D
// Are they Equal?
@FALSE_EQ
D;JNE
// They are equal, assign -1
D=-1
@FINAL_EQ
0;JMP
(FALSE_EQ)
// They are not equal, assign 0
D=0
(FINAL_EQ)
@SP
A=M
M=D
// Increment SP
@SP
M=M+1

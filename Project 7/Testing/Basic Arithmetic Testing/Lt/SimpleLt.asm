// input 7
@7
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
// Check if M[SP-1] < M[SP]
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
// is M[SP-1] < M[SP]?
@FALSE_LT
D;JGT
// M[SP-1] is less than M[SP], assign -1
D=-1
@FINAL_LT
0;JMP
(FALSE_LT)
// M[SP-1] is greater than M[SP], assign 0
D=0
(FINAL_LT)
@SP
A=M
M=D
// Increment SP
@SP
M=M+1

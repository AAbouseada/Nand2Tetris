// input 9
@9
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
// Check if M[SP-1] > M[SP]
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
// is M[SP-1] > M[SP]?
@FALSE_GT
D;JLT
// M[SP-1] is greater, assign -1
D=-1
@FINAL_GT
0;JMP
(FALSE_GT)
// M[SP-1] is not greater, assign 0
D=0
(FINAL_GT)
@SP
A=M
M=D
// Increment SP
@SP
M=M+1

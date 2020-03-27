// input 7
@7
D=A
@SP
A=M
M=D
// Increment SP
@SP
M=M+1
// Calculate bitwise not
// M[SP] = !M[SP] 
// Decrement SP
@SP
M=M-1
A=M
M=!M
// Increment SP
@SP
M=M+1

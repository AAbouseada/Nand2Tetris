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
// Calculate bitwise or
// M[SP-1] = M[SP-1] | M[SP]
// Decrement SP
@SP
M=M-1
A=M
D=M
// Decrement SP
@SP
M=M-1
A=M
D=D|M
M=D
// Increment SP
@SP
M=M+1

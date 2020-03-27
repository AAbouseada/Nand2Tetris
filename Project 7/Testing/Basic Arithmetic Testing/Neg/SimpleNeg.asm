// input 17
@17
D=A
@SP
A=M
M=D
// Increment SP
@SP
M=M+1
// negate the last thing inputed
// M[SP]=-M[SP]
// Decrement SP
@SP
M=M-1
A=M
M=-M
// Increment SP
@SP
M=M+1

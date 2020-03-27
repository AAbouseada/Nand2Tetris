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
// Add the last two things together
// Decrement SP
@SP
M=M-1
A=M
D=M
// Decrement SP
@SP
M=M-1
A=M
M=M+D
// Increment SP
@SP
M=M+1

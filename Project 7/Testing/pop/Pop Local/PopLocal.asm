// push 10
@10
D=A
@SP
A=M
M=D
// Increment SP
@SP
AM=M+1
// pop the last item off the stack
// and input it at local[0]
@LCL
D=M
@0
D=D+A
@R13
M=D
// Decrement SP
@SP
AM=M-1
D=M
@R13
A=M
M=D

# MicroJava compiler

Goal of this project was implementation of compiler for MicroJava. 

## Compiler parts

Implement compiler is consisted of 4 parts: 
1.) Lexer - generated using JFlex
2.) Parser - generated using CUP 
3.) Semantic checker 
4.) Code Generator 

## How to run
Pozicionirati se u direktorijum src u okviru korenog direktorijuma projekta.
1. Generating lexer:

```cmd
../lib/JFlex.jar JFlex.Main -d ./rs/ac/bg/etf/pp1 ../spec/mjlexer.lex
```

2. Generating parser:

```cmd
java -cp ../lib/cup_v10k.jar java_cup.Main -destdir rs/ac/bg/etf/pp1 -ast rs.ac.bg.etf.pp1.ast -parser MJParser -dump_states -buildtree ../spec/mjparser.cup
```

3. Compiling the project:

```cmd
javac -cp ../lib/*;. rs/ac/bg/etf/pp1/ast/*.java rs/ac/bg/etf/pp1/util/*.java rs/ac/bg/etf/pp1/*.java
```

4. Compiling your MicroJavaProgram (.mj fajl):

```cmd
java -cp ../lib/*;. rs.ac.bg.etf.pp1.MJCompiler <mj_file> <obj_file>
```

5. Disassembly of MicroJava .obj file

```cmd
java -cp ../lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.disasm ><output_file> <obj_file>
```

6. Running compiled MicroJava .obj file (optional input and output files)

```
java -cp ../lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.Run [-debug] <<input_file> ><output_file> <obj_file>
```

## Limitations
Parts of MicroJava that this compiler does not cover are:
Classes, For loops, labels, goto statements

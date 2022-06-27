package rs.ac.bg.etf.pp1;

import java.io.*;
import org.apache.log4j.Logger;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.etf.pp1.mj.runtime.Code;

public class MJCompiler {
	
	private static Logger log = Logger.getLogger(MJCompiler.class);
	
	public static void main(String args[]) {
		
		if( (args.length != 2) || (args[0]==null) || !(args[0] instanceof String) 
				|| (args[1]==null) || !(args[1] instanceof String) ) {
			
			System.out.print("Invalid arguments for compiler call. Expecting 2 arguments: source, output");
			return;
		}

		String sourcePath = args[0];
		String outputPath = args[1];
		
		File source = new File(sourcePath);
		if(!source.exists()) {
			System.out.print("Source file does not exist.");
			return;
		} else {
			System.out.print("Compiling source file: " + source.getAbsolutePath());
		}
		
		File output = new File(outputPath);
		
		Reader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(source));
			Yylex lexer = new Yylex(reader);
			System.out.println("\n\nPARSING:----------------------------------");
			MJParser parser = new MJParser(lexer);
			
			Symbol s = parser.parse();
			
			if(!parser.isSuccessful()) {
				System.out.println("\n");
				System.out.print("One or more syntax errors found! Compiling unsuccessful!");
				return;
			} else {
				System.out.println("PARSING SUCCESSFUL");
			}
			
			Program prog = (Program)s.value;
			
			System.out.println("\n\nSEMANTIC PASS:----------------------------------");
			SemanticPass semanticPass = new SemanticPass();
			prog.traverseBottomUp(semanticPass);
			
			if(!semanticPass.isSuccessful()) {
				System.out.println("\n");
				System.out.print("One or more semantic errors found! Compiling unsuccessful!");
				return;
			} else {
				System.out.println("SEMANTIC PASS SUCCESSFUL");
			}
			
			// code generation
			//
			CodeGenerator codeGenerator = new CodeGenerator();
			Code.dataSize = semanticPass.getGlobalVarCount() + 2; // 2 temp vars needed for var arg calls...
			prog.traverseBottomUp(codeGenerator);
			Code.mainPc = codeGenerator.getMainPc();
			
			if(codeGenerator.isError()) {
				System.out.println("\n");
				System.out.print("One or more errors during code generation! Compiling unsuccessful!");
				return;
			}
			
			Code.write(new FileOutputStream(output));
			System.out.println("\n");
			System.out.print("COMPILATION SUCCESSFULL\nResulting file: " + output.getAbsolutePath());
			
		} catch (Exception e) {
			//log.error(e.getMessage());
		} 
		
	}
}

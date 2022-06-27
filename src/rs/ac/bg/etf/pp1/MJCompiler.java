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
			
			log.error("Invalid arguments for compiler call. Expecting 2 arguments: source, output");
			return;
		}

		String sourcePath = args[0];
		String outputPath = args[1];
		
		File source = new File(sourcePath);
		if(!source.exists()) {
			log.error("Source file does not exist.");
			return;
		} else {
			log.info("Compiling source file: " + source.getAbsolutePath());
		}
		
		File output = new File(outputPath);
		
		Reader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(source));
			Yylex lexer = new Yylex(reader);
			MJParser parser = new MJParser(lexer);
			
			Symbol s = parser.parse();
			
			if(!parser.isSuccessful()) {
				System.out.println("\n");
				log.error("One or more syntax errors found! Compiling unsuccessful!");
				return;
			}
			
			Program prog = (Program)s.value;
			
			SemanticPass semanticPass = new SemanticPass();
			prog.traverseBottomUp(semanticPass);
			
			if(!semanticPass.isSuccessful()) {
				System.out.println("\n");
				log.error("One or more semantic errors found! Compiling unsuccessful!");
				return;
			}
			
			// code generation
			//
			CodeGenerator codeGenerator = new CodeGenerator();
			Code.dataSize = semanticPass.getGlobalVarCount();
			prog.traverseBottomUp(codeGenerator);
			Code.mainPc = codeGenerator.getMainPc();
			
			if(codeGenerator.isError()) {
				System.out.println("\n");
				log.error("One or more errors during code generation! Compiling unsuccessful!");
				return;
			}
			
			Code.write(new FileOutputStream(output));
			System.out.println("\n");
			log.info("Successful compilation. Resulting file: " + output.getAbsolutePath());
			
		} catch (Exception e) {
			log.error(e.getMessage());
		} 
		
	}
}

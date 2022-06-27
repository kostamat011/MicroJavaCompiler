# MicroJava kompajler

Ovaj projekat predstavlja implementaciju funkcionalnog kompajlera za MicroJavu. 

## Delovi kompajlera

Implementirani kompajler sastoji se iz 4 dela:  
1.) Leksera - generisan korišćenjem JFlex alata. Ova faza projekta uključuje specifikaciju jezičkih elemenata (tokena)  
2.) Parsera - generisan korišćenjem CUP alata iz napisanog .cup fajla. U ovoj fazi pisali smo specifikaciju 
gramatike jezika  
3.) Semantičkog prolaza - u ovoj fazi proveravali smo konteksne uslove jezika i formirali tabelu simbola  
4.) Generatora koda - prevođenje MicroJava koda u bajtkod za MicroJava Virtuelnu mašinu

## Uputstvo za pokretanje
Pozicionirati se u direktorijum src u okviru korenog direktorijuma projekta.
1. Generisanje leksera:

```cmd
../lib/JFlex.jar JFlex.Main -d ./rs/ac/bg/etf/pp1 ../spec/mjlexer.lex
```

2. Generisanje parsera:

```cmd
java -cp ../lib/cup_v10k.jar java_cup.Main -destdir rs/ac/bg/etf/pp1 -ast rs.ac.bg.etf.pp1.ast -parser MJParser -dump_states -buildtree ../spec/mjparser.cup
```

3. Kompajliranje projekta:

```cmd
javac -cp ../lib/*;. rs/ac/bg/etf/pp1/ast/*.java rs/ac/bg/etf/pp1/util/*.java rs/ac/bg/etf/pp1/*.java
```

4. Kompajliranje MicroJava programa (.mj fajl):

```cmd
java -cp ../lib/*;. rs.ac.bg.etf.pp1.MJCompiler <mj_file> <obj_file>
```

5. Disasembly MicroJava .obj fajla

```cmd
java -cp ../lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.disasm <obj_file>
```

6. Pokretanje prevedenog MicroJava fajla

```
java -cp ../lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.Run [-debug] <obj_file>
```

## Opis priloženih test primera
Uz javne testove, prilozen je i test primer pod nazivom testVarArgs.mj.  
Ovaj primer demonstrira korišćenje nove dodate opcije za promenljiv broj parametara funkcije.  

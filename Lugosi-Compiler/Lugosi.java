/* Generated By:JavaCC: Do not edit this line. Lugosi.java */
import java.io.*;
import java.util.ArrayList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;

class ArvoreLugosi{
  Main main;
  ArrayList<Function> functions;

  ArvoreLugosi(Main main, ArrayList<Function> functions){
    this.main = main;
    this.functions = functions;
  }
}

class Main{
  ArrayList<Comando> comandos;
  ArrayList<VarDecl> varDecls;

  Main(ArrayList<VarDecl> varDecls, ArrayList<Comando> comandos){
    this.varDecls = varDecls;
    this.comandos = comandos;
  }
}

class VarDecl{
  String tipo;
  String tokenID;

  VarDecl(String tipo, String tokenID){
    this.tipo = tipo;
    this.tokenID = tokenID;
  }
}

class Comando{}

class Atrib extends Comando{

  String tokenID;
  Exp exp;

  Atrib (String tokenID, Exp exp)
  {
    this.tokenID=tokenID;
    this.exp=exp;
  }

  /*print()
  {
    //System.out.println(this.tokenID + " = " + this.exp);
  }*/
}

class ChamadaDeFuncao extends Comando{
  String tokenID;
  ListaExp listaExp;

  ChamadaDeFuncao (String tokenID, ListaExp listaExp)
  {
    this.tokenID=tokenID;
    this.listaExp=listaExp;
  }
}

class CondicionalIF extends Comando{
  Exp exp;
  ArrayList<Comando> comandosEscopoIF;

  CondicionalIF (Exp exp, ArrayList<Comando> comandosEscopoIF){
    this.exp = exp;
    this.comandosEscopoIF = comandosEscopoIF;
  }
}

class LacoWhile extends Comando{
  Exp exp;
  ArrayList<Comando> comandosEscopoWhile;

  LacoWhile (Exp exp, ArrayList<Comando> comandosEscopoWhile){
    this.exp = exp;
    this.comandosEscopoWhile = comandosEscopoWhile;
  }
}

class LacoDoWhile extends Comando{
  Exp exp;
  ArrayList<Comando> comandosEscopoDoWhile;

  LacoDoWhile (Exp exp, ArrayList<Comando> comandosEscopoDoWhile){
    this.exp = exp;
    this.comandosEscopoDoWhile = comandosEscopoDoWhile;
  }
}

class Return extends Comando{
  Exp exp;

  Return (Exp exp){
    this.exp = exp;
  }
}

class Print extends Comando{

   Exp exp;
   Print(Exp exp)
   {this.exp = exp;}
}

class Exp{}

class ListaExp extends Exp{
  ArrayList<Exp> listaExp;

  ListaExp (ArrayList<Exp> listaExp){
    this.listaExp = listaExp;
  }
}

class Infixo extends Exp{
  Exp e1, e2;
  Op op;

  Infixo(Exp e1, Exp e2, Op op){
    this.e1 = e1;
    this.e2 = e2;
    this.op = op;
  }
}

class Fator extends Exp{}

class VariavelID extends Fator{
  String tokenID;

  VariavelID(String tokenID){
    this.tokenID = tokenID;
  }
}

class ChamadaDeFuncaoExp extends Fator{
  ChamadaDeFuncao chamadaDeFuncaoExp;

  ChamadaDeFuncaoExp(String tokenID, ListaExp listaExp){
    chamadaDeFuncaoExp = new ChamadaDeFuncao(tokenID, listaExp);
  }
}

class Num extends Fator{
  float num;

  Num(float num)
  {this.num = num;}

}

class BooleanValue extends Fator{
  String trueOrFalse;

  BooleanValue(String trueOrFalse)
  {this.trueOrFalse = trueOrFalse;}
}

class Op{
  String operation;

  Op(String operation){
    this.operation = operation;
  }
}

class Arg extends VarDecl{

  Arg(String tipo, String tokenID){
    super(tipo, tokenID);
  }
}

class Function{
  String tipo;
  String tokenID;
  ArrayList<Arg> listaArgs;
  ArrayList<VarDecl> varDecls;
  ArrayList<Comando> comandos;

  Function( String tipo, String tokenID,
            ArrayList<Arg> listaArgs,
            ArrayList<VarDecl> varDecls,
            ArrayList<Comando> comandos){
    this.tipo = tipo;
    this.tokenID = tokenID;
    this.listaArgs = listaArgs;
    this.varDecls = varDecls;
    this.comandos = comandos;
  }
}

public class Lugosi implements LugosiConstants {

  public static void main(String args[]) throws Exception{

    // abrir o arquivo passado por linha
    // de comando contendo o código em Lugosi:
    FileInputStream fs = new FileInputStream(new File(args[0]));

    // Instanciar o parser da linguagem Lugosi passando
    // como argumento o arquivo contendo o código
    //Lugosi a ser processado:
    Lugosi parser = new Lugosi(fs);

    // Chamar a primeira regra do parser que irá
    // analisar o código e devolver a árvore sintática
    ArvoreLugosi arvore =parser.Lugosi();

    // passar a árvore para o gerador de código
    // que deve gerar um arquivo .java com o mesmo
    // nome do arquivo de entrada
    geraCodigo(arvore, args[0]);
}

public static void geraCodigo(ArvoreLugosi prog, String arquivo) throws Exception{
  StringBuilder programBuilder = new StringBuilder(1000);

  char[] arquivoChars = arquivo.toCharArray();
  arquivoChars[0] = Character.toUpperCase(arquivoChars[0]);

  StringBuilder charToString = new StringBuilder(arquivo.length());

  for (int i = 0; i < arquivoChars.length; i++){
            charToString.append(arquivoChars[i]);
  }
  arquivo = charToString.toString();

  programBuilder.append("class " + arquivo.split("\u005c\u005c.")[0] + "{\u005cn");

  programBuilder.append("\u005ctpublic static void main(String args[]){\u005cn");

  for(VarDecl varDecl : prog.main.varDecls){
    geraVarDecl(programBuilder, varDecl);
  }

  programBuilder.append("\u005cn\u005cn");

  for(Comando comando : prog.main.comandos){
    geraComando(programBuilder, comando);
  }

  programBuilder.append("\u005ct}\u005cn\u005cn\u005cn");

  //gera funcoes

  for(Function function : prog.functions){
    geraFunction(programBuilder, function);
    programBuilder.append("\u005cn");
  }

  programBuilder.append("}");

  String programString = programBuilder.toString();

  System.out.println(programString);

  String currentPath = System.getProperty("user.dir");
  //System.out.println(currentPath);

  //cria pasta para arquivos .java de saida, se nao existir
  File outputFolder = new File(currentPath + "/JavaPrograms");
  if(!outputFolder.exists())
  {
    outputFolder.mkdir();
  }

  //gera arquivo de saida
  File javaProgram = new File("JavaPrograms/" + arquivo.split("\u005c\u005c.")[0] + ".java");
        FileOutputStream fileOutputStream = new FileOutputStream(javaProgram);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        byte[] codeBytes = programString.getBytes();

        bufferedOutputStream.write(codeBytes);
        bufferedOutputStream.close();
        fileOutputStream.close();

}

public static void geraVarDecl(StringBuilder programBuilder, VarDecl varDecl){
  programBuilder.append("\u005ct\u005ct" + varDecl.tipo + " " + varDecl.tokenID + ";\u005cn");
}

public static void geraComando(StringBuilder programBuilder, Comando comando){

  if(comando instanceof Atrib){
    programBuilder.append("\u005ct\u005ct");
    geraAtrib(programBuilder, (Atrib)comando);
  }

  else if(comando instanceof ChamadaDeFuncao){
    //programBuilder.append("\n");
    programBuilder.append("\u005ct\u005ct");
    geraChamadaDeFuncao(programBuilder, (ChamadaDeFuncao)comando);
    programBuilder.append(";\u005cn");
  }

  else if(comando instanceof CondicionalIF){
    //programBuilder.append("\n");
    programBuilder.append("\u005ct\u005ct");
    programBuilder.append("if( ");
    geraCondicionalIF(programBuilder, (CondicionalIF)comando);
    programBuilder.append("\u005ct\u005ct}\u005cn\u005cn");
  }

  else if(comando instanceof LacoWhile){
    //programBuilder.append("\n");
    programBuilder.append("\u005ct\u005ct");
    programBuilder.append("while( ");
    geraLacoWhile(programBuilder, (LacoWhile)comando);
    programBuilder.append("\u005ct\u005ct}\u005cn\u005cn");
  }

  else if(comando instanceof LacoDoWhile){
    //programBuilder.append("\n");
    programBuilder.append("\u005ct\u005ct");
    programBuilder.append("do\u005cn\u005ct\u005ct{\u005cn");
    geraLacoDoWhile(programBuilder, (LacoDoWhile)comando);
    programBuilder.append("\u005cn\u005cn");
  }

  else if(comando instanceof Return){
    programBuilder.append("\u005ct\u005ct");
    geraReturn(programBuilder, (Return)comando);
  }

  else if(comando instanceof Print){
    programBuilder.append("\u005ct\u005ct");
    geraPrint(programBuilder, (Print)comando);
  }
}

public static void geraAtrib(StringBuilder programBuilder, Atrib atrib){
  programBuilder.append(atrib.tokenID);
  programBuilder.append(" = ");
  geraExp(programBuilder, atrib.exp);
  programBuilder.append(";\u005cn");
}

public static void geraChamadaDeFuncao(StringBuilder programBuilder, ChamadaDeFuncao chamadaDeFuncao){
  programBuilder.append(chamadaDeFuncao.tokenID);
  programBuilder.append("( ");
  geraListaExp(programBuilder, chamadaDeFuncao.listaExp);
  programBuilder.append(" )");
}

public static void geraCondicionalIF(StringBuilder programBuilder, CondicionalIF condicionalIF){
  geraExp(programBuilder, condicionalIF.exp);
  programBuilder.append("){\u005cn");

  for(Comando comando : condicionalIF.comandosEscopoIF){
    programBuilder.append("\u005ct");
    geraComando(programBuilder, comando);
  }
}

public static void geraLacoWhile(StringBuilder programBuilder, LacoWhile lacoWhile){
  geraExp(programBuilder, lacoWhile.exp);
  programBuilder.append("){\u005cn");

  for(Comando comando : lacoWhile.comandosEscopoWhile){
    programBuilder.append("\u005ct");
    geraComando(programBuilder, comando);
  }
}

public static void geraLacoDoWhile(StringBuilder programBuilder, LacoDoWhile lacoDoWhile){
  for(Comando comando : lacoDoWhile.comandosEscopoDoWhile){
    programBuilder.append("\u005ct");
    geraComando(programBuilder, comando);
  }

  programBuilder.append("\u005ct\u005ct}while(");
  geraExp(programBuilder, lacoDoWhile.exp);
  programBuilder.append(");\u005cn");
}

public static void geraReturn(StringBuilder programBuilder, Return comandoReturn){
  programBuilder.append("return ");
  geraExp(programBuilder, comandoReturn.exp);
  programBuilder.append(";\u005cn");
}

public static void geraPrint(StringBuilder programBuilder, Print print){
  //programBuilder.append("\n");
  programBuilder.append("System.out.println( ");
  geraExp(programBuilder, print.exp);
  programBuilder.append(" );\u005cn");
}

public static void geraExp(StringBuilder programBuilder, Exp exp){

  if(exp instanceof ListaExp){
    programBuilder.append("( ");
    geraListaExp(programBuilder, (ListaExp)exp);
    programBuilder.append(")");
  }
  else if(exp instanceof Infixo){
    programBuilder.append("( ");
    geraInfixo(programBuilder, (Infixo)exp);
    programBuilder.append(" )");
  }
  else if(exp instanceof Fator){
    geraFator(programBuilder, (Fator)exp);
  }
}

public static void geraListaExp(StringBuilder programBuilder, ListaExp listaExp){
  int count = 0;

  for(Exp itemExp : listaExp.listaExp){
    geraExp(programBuilder, itemExp);

    ++count;

    if(count < listaExp.listaExp.size())
      programBuilder.append(", ");
  }
}

public static void geraInfixo(StringBuilder programBuilder, Infixo infixo){

  geraExp(programBuilder, infixo.e1);
  geraOp(programBuilder, infixo.op);
  geraExp(programBuilder, infixo.e2);

}

public static void geraFator(StringBuilder programBuilder, Fator fator){
  if(fator instanceof VariavelID){
    programBuilder.append(((VariavelID)fator).tokenID);
  }
  else if(fator instanceof ChamadaDeFuncaoExp){
    geraChamadaDeFuncao(programBuilder, ((ChamadaDeFuncaoExp)fator).chamadaDeFuncaoExp);
  }
  else if(fator instanceof Num){
    float num = ((Num)fator).num;

    if(num == Math.floor(num)){
      programBuilder.append((int)num);
    }
    else{
      programBuilder.append( num + "f" );
    }
  }
  else if(fator instanceof BooleanValue){
    programBuilder.append( ((BooleanValue)fator).trueOrFalse );
  }
}

public static void geraOp(StringBuilder programBuilder, Op op){
  programBuilder.append(" " +  op.operation + " ");
}

public static void geraFunction(StringBuilder programBuilder, Function function){
  programBuilder.append("\u005ctpublic static " + function.tipo + " " + function.tokenID + "(");
  geraArgs(programBuilder, function.listaArgs);
  programBuilder.append("){\u005cn");

  for(VarDecl varDecl : function.varDecls){
    geraVarDecl(programBuilder, varDecl);
  }

  programBuilder.append("\u005cn\u005cn");

  for(Comando comando : function.comandos){
    geraComando(programBuilder, comando);
  }

  programBuilder.append("\u005ct}\u005cn");
}

public static void geraArgs(StringBuilder programBuilder, ArrayList<Arg> listaArgs){
  int count = 0;

  for(Arg arg : listaArgs){
    programBuilder.append(arg.tipo + " " + arg.tokenID);

    ++count;
    if(count < listaArgs.size()){
      programBuilder.append(", ");
    }
  }
}

  static final public ArvoreLugosi Lugosi() throws ParseException {
 //Token t;
  ArrayList<VarDecl> varDecls = new ArrayList<VarDecl>();
  ArrayList<Comando> comandos = new ArrayList<Comando>();
  Main main = new Main(varDecls, comandos);
  ArrayList<Function> functions = new ArrayList<Function>();
    //Syntax-directed translation Parser
    
      // LUGOSI -> MAIN FUNC?
      main = Main();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case FUNCTION:
      functions = Func(new ArrayList<Function>());
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    jj_consume_token(0);
   {if (true) return new ArvoreLugosi(main, functions);}
    throw new Error("Missing return statement in function");
  }

//MAIN -> "main" "{" VARDECL SEQCOMANDOS "}"
  static final public Main Main() throws ParseException {
  ArrayList<VarDecl> varDecls = new ArrayList<VarDecl>();
  ArrayList<Comando> comandos = new ArrayList<Comando>();
    jj_consume_token(MAIN);
    jj_consume_token(ACHAVES);
    VarDecl(varDecls);
    SeqComandos(comandos);
    jj_consume_token(FCHAVES);
   {if (true) return new Main(varDecls, comandos);}
    throw new Error("Missing return statement in function");
  }

//VARDECL -> "var" TIPO TOKEN_id ";" VARDECL | vazio
  static final public ArrayList<VarDecl> VarDecl(ArrayList<VarDecl> varDecls) throws ParseException {
 Token t = null; String tipo = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VAR:
      jj_consume_token(VAR);
      tipo = Tipo();
      t = jj_consume_token(ID);
                                               varDecls.add(new VarDecl(tipo, t.image));
      jj_consume_token(PTOVIRGULA);
      VarDecl(varDecls);
      break;
    default:
      jj_la1[1] = jj_gen;
      ;
    }
   {if (true) return varDecls;}
    throw new Error("Missing return statement in function");
  }

//TIPO -> "int" | "bool" | "float" | "void"
  static final public String Tipo() throws ParseException {
 Token t;
    t = jj_consume_token(TIPOD);
    if(t.image.equals("bool"))
      {if (true) return "boolean";}
    {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

//SEQCOMANDOS -> COMANDO SEQCOMANDOS | vazio
  static final public ArrayList<Comando> SeqComandos(ArrayList<Comando> comandos) throws ParseException {
 Comando comando;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PRINT:
    case RETURN:
    case IF:
    case DO:
    case WHILE:
    case ID:
      comando = Comando();
                        comandos.add(comando);
      SeqComandos(comandos);
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
   {if (true) return comandos;}
    throw new Error("Missing return statement in function");
  }

/*COMANDO -> TOKEN_id TOKEN_idL
| "if" "(" EXP ")" "{" SEQCOMANDOS "}" ";"
| "while" "(" EXP ")" "do" "{" SEQCOMANDOS "}"
| "do" "{" SEQCOMANDOS "}" "while" "(" EXP ")"
| "return" EXP ";"
| "print" "(" EXP ")" ";"
*/
  static final public Comando Comando() throws ParseException {
  Token t = null; Exp e = null; Comando c = null; Comando result = null;
  ArrayList<Comando> comandos = new ArrayList<Comando>();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      t = jj_consume_token(ID);
      e = TokenIDL();
    if(e instanceof ListaExp)
      {if (true) return new ChamadaDeFuncao(t.image, (ListaExp)e);}
    {if (true) return new Atrib(t.image, e);}
      break;
    case IF:
      jj_consume_token(IF);
      jj_consume_token(APAREN);
      e = Exp();
      jj_consume_token(FPAREN);
      jj_consume_token(ACHAVES);
      comandos = SeqComandos(comandos);
      jj_consume_token(FCHAVES);
      jj_consume_token(PTOVIRGULA);
   {if (true) return new CondicionalIF(e, comandos);}
      break;
    case WHILE:
      jj_consume_token(WHILE);
      jj_consume_token(APAREN);
      e = Exp();
      jj_consume_token(FPAREN);
      jj_consume_token(DO);
      jj_consume_token(ACHAVES);
      comandos = SeqComandos(comandos);
      jj_consume_token(FCHAVES);
      jj_consume_token(PTOVIRGULA);
   {if (true) return new LacoWhile(e, comandos);}
      break;
    case DO:
      jj_consume_token(DO);
      jj_consume_token(ACHAVES);
      comandos = SeqComandos(comandos);
      jj_consume_token(FCHAVES);
      jj_consume_token(WHILE);
      jj_consume_token(APAREN);
      e = Exp();
      jj_consume_token(FPAREN);
      jj_consume_token(PTOVIRGULA);
   {if (true) return new LacoDoWhile(e, comandos);}
      break;
    case RETURN:
      jj_consume_token(RETURN);
      e = Exp();
      jj_consume_token(PTOVIRGULA);
   {if (true) return new Return(e);}
      break;
    case PRINT:
      jj_consume_token(PRINT);
      jj_consume_token(APAREN);
      e = Exp();
      jj_consume_token(FPAREN);
      jj_consume_token(PTOVIRGULA);
   {if (true) return new Print(e);}
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//TOKEN_idL -> ":=" EXP ";" | "(" LISTAEXP? ")" ";"
  static final public Exp TokenIDL() throws ParseException {
 Exp e = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ATRIB:
      jj_consume_token(ATRIB);
      e = Exp();
      jj_consume_token(PTOVIRGULA);
      break;
    case APAREN:
      jj_consume_token(APAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case APAREN:
      case BVAL:
      case NUM:
      case ID:
        e = ListaExp();
        break;
      default:
        jj_la1[4] = jj_gen;
        ;
      }
      jj_consume_token(FPAREN);
      jj_consume_token(PTOVIRGULA);
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
   {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

//EXP -> "(" EXP OP EXP ")" | FATOR
  static final public Exp Exp() throws ParseException {
 Exp e1 = null, e2 = null, result = null; Op op = null; Fator f = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case APAREN:
      jj_consume_token(APAREN);
      e1 = Exp();
      op = Op();
      e2 = Exp();
      jj_consume_token(FPAREN);
   {if (true) return new Infixo(e1, e2, op);}
      break;
    case BVAL:
    case NUM:
    case ID:
      f = Fator();
   {if (true) return f;}
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

/*FATOR -> TOKEN_id TOKEN_idL2 
| TOKEN_numliteral | "true" | "false"
*/
  static final public Fator Fator() throws ParseException {
 Token t = null;  Fator result = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      t = jj_consume_token(ID);
      result = TokenIDL2(t.image);
   {if (true) return result;}
      break;
    case NUM:
      t = jj_consume_token(NUM);
               {if (true) return new Num(Float.valueOf(t.image));}
      break;
    case BVAL:
      t = jj_consume_token(BVAL);
                {if (true) return new BooleanValue(t.image);}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//TOKEN_idL2 -> "(" LISTAEXP? ")" | vazio
  static final public Fator TokenIDL2(String tokenID) throws ParseException {
  ListaExp listaExp = null;
  Fator result = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case APAREN:
      jj_consume_token(APAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case APAREN:
      case BVAL:
      case NUM:
      case ID:
        listaExp = ListaExp();
        break;
      default:
        jj_la1[8] = jj_gen;
        ;
      }
      jj_consume_token(FPAREN);
      break;
    default:
      jj_la1[9] = jj_gen;
      ;
    }
    if (listaExp == null)
      result = new VariavelID(tokenID);
    else
      result = new ChamadaDeFuncaoExp(tokenID, listaExp);
    {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

//OP -> "+" | "-" | "*" | "/" | "&&" | "||" | "<" | ">" | "=="
  static final public Op Op() throws ParseException {
 Token t = null;Op result = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ADD:
      t = jj_consume_token(ADD);
      break;
    case SUB:
      t = jj_consume_token(SUB);
      break;
    case MULT:
      t = jj_consume_token(MULT);
      break;
    case DIV:
      t = jj_consume_token(DIV);
      break;
    case AND:
      t = jj_consume_token(AND);
      break;
    case OR:
      t = jj_consume_token(OR);
      break;
    case MENOR:
      t = jj_consume_token(MENOR);
      break;
    case MAIOR:
      t = jj_consume_token(MAIOR);
      break;
    case IGUAL:
      t = jj_consume_token(IGUAL);
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
   result = new Op(t.image);
   {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

//LISTAEXP -> EXP LISTAEXPL
  static final public ListaExp ListaExp() throws ParseException {
  ArrayList<Exp> listaExp = new ArrayList<Exp>();
  Exp e;
    e = Exp();
             listaExp.add(e);
    ListaExpL(listaExp);
   {if (true) return new ListaExp(listaExp);}
    throw new Error("Missing return statement in function");
  }

//LISTAEXPL -> "," EXP LISTAEXPL | vazio
  static final public void ListaExpL(ArrayList<Exp> listaExp) throws ParseException {
  Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VIRGULA:
      jj_consume_token(VIRGULA);
      e = Exp();
                         listaExp.add(e);
      ListaExpL(listaExp);
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
  }

//FUNC -> "function" TIPO TOKEN_id "(" LISTAARG? ")" "{" VARDECL SEQCOMANDOS "}" FUNCL
  static final public ArrayList<Function> Func(ArrayList<Function> functions) throws ParseException {
  Token t = null; String tipo = null;
  ArrayList<Arg> listaArgs = new ArrayList<Arg>();
  ArrayList<VarDecl> varDecls = new ArrayList<VarDecl>();
  ArrayList<Comando> comandos = new ArrayList<Comando>();
    jj_consume_token(FUNCTION);
    tipo = Tipo();
    t = jj_consume_token(ID);
    jj_consume_token(APAREN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TIPOD:
      ListaArg(listaArgs);
      break;
    default:
      jj_la1[12] = jj_gen;
      ;
    }
    jj_consume_token(FPAREN);
    jj_consume_token(ACHAVES);
    VarDecl(varDecls);
    SeqComandos(comandos);
    functions.add(new Function(tipo, t.image, listaArgs, varDecls, comandos));
    jj_consume_token(FCHAVES);
    FuncL(functions);
   {if (true) return functions;}
    throw new Error("Missing return statement in function");
  }

//FUNCL -> "function" TIPO TOKEN_id "(" LISTAARG? ")" "{" VARDECL SEQCOMANDOS "}" FUNCL | vazio
  static final public ArrayList<Function> FuncL(ArrayList<Function> functions) throws ParseException {
  Token t = null; String tipo = null;
  ArrayList<Arg> listaArgs = new ArrayList<Arg>();
  ArrayList<VarDecl> varDecls = new ArrayList<VarDecl>();
  ArrayList<Comando> comandos = new ArrayList<Comando>();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case FUNCTION:
      jj_consume_token(FUNCTION);
      tipo = Tipo();
      t = jj_consume_token(ID);
      jj_consume_token(APAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TIPOD:
        ListaArg(listaArgs);
        break;
      default:
        jj_la1[13] = jj_gen;
        ;
      }
      jj_consume_token(FPAREN);
      jj_consume_token(ACHAVES);
      VarDecl(varDecls);
      SeqComandos(comandos);
    functions.add(new Function(tipo, t.image, listaArgs, varDecls, comandos));
      jj_consume_token(FCHAVES);
      FuncL(functions);
      break;
    default:
      jj_la1[14] = jj_gen;
      ;
    }
   {if (true) return functions;}
    throw new Error("Missing return statement in function");
  }

//LISTAARG -> TIPO TOKEN_id LISTAARGL
  static final public ArrayList<Arg> ListaArg(ArrayList<Arg> listaArgs) throws ParseException {
 Token t = null; String tipo = null;
    tipo = Tipo();
    t = jj_consume_token(ID);
                          listaArgs.add(new Arg(tipo, t.image));
    ListaArgL(listaArgs);
   {if (true) return listaArgs;}
    throw new Error("Missing return statement in function");
  }

//LISTAARGL -> "," TIPO TOKEN_id LISTAARGL | vazio
  static final public ArrayList<Arg> ListaArgL(ArrayList<Arg> listaArgs) throws ParseException {
 Token t = null; String tipo = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VIRGULA:
      jj_consume_token(VIRGULA);
      tipo = Tipo();
      t = jj_consume_token(ID);
                                      listaArgs.add(new Arg(tipo, t.image));
      ListaArgL(listaArgs);
      break;
    default:
      jj_la1[15] = jj_gen;
      ;
    }
   {if (true) return listaArgs;}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public LugosiTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[16];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x4000000,0x800000,0x3b000000,0x3b000000,0xc0000100,0x900,0xc0000100,0xc0000000,0xc0000100,0x100,0x3fe000,0x1000,0x400000,0x400000,0x4000000,0x1000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x1,0x1,0x1,0x0,0x1,0x1,0x1,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }

  /** Constructor with InputStream. */
  public Lugosi(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Lugosi(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new LugosiTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Lugosi(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new LugosiTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Lugosi(LugosiTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(LugosiTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[33];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 16; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 33; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}

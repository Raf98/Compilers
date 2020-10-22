import java.io.*;

enum TokenType {
	NUM, SOMA, MULT, APar, FPar, EOF, SUB, DIV
}

class Token {
	String lexema;
	TokenType token;

	Token(String l, TokenType t) {
		lexema = l;
		token = t;
	}

}

class AnaliseLexica {

	BufferedReader arquivo;
	// int i = 0;

	AnaliseLexica(String a) throws Exception {

		this.arquivo = new BufferedReader(new FileReader(a));

	}

	Token getNextToken() throws Exception {
		Token token;
		int eof = -1;
		char currchar;
		int currchar1;

		// System.out.println(i + "th iteration");

		// i++;

		do {
			currchar1 = arquivo.read();
			currchar = (char) currchar1;
		} while (currchar == '\n' || currchar == ' ' || currchar == '\t' || currchar == '\r');

		if (currchar1 != eof && currchar1 != 10) {// 10 stands for newline on ASCII??

			if (currchar >= '0' && currchar <= '9') {
				StringBuilder numBuffer = new StringBuilder();
				numBuffer.append(currchar);

				arquivo.mark(1);
				currchar1 = arquivo.read();

				while (currchar1 >= 48 && currchar1 <= 57) {
					numBuffer.append((char) currchar1);
					arquivo.mark(1);
					currchar1 = arquivo.read();
					//System.out.println((char) currchar1);
				}

				String numStr = numBuffer.toString();
				//System.out.println(numStr);

				arquivo.reset();

				return (new Token(numStr, TokenType.NUM));
			} else {
				//System.out.println(currchar);

				switch (currchar) {
					case '(':
						return (new Token(String.valueOf(currchar), TokenType.APar));
					case ')':
						return (new Token(String.valueOf(currchar), TokenType.FPar));
					case '+':
						return (new Token(String.valueOf(currchar), TokenType.SOMA));
					case '*':
						return (new Token(String.valueOf(currchar), TokenType.MULT));
					case '-':
						return (new Token(String.valueOf(currchar), TokenType.SUB));
					case '/':
						return (new Token(String.valueOf(currchar), TokenType.DIV));

					default:
						throw (new Exception("Caractere inválido: " + ((int) currchar)));
				}
			}
		}

		arquivo.close();

		return (new Token(String.valueOf(currchar), TokenType.EOF));

	}
}

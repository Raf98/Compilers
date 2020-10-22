import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;

class Compilador {

	public static void main(String[] args) {
		ArvoreSintatica arv = null;

		try {

			AnaliseLexica al = new AnaliseLexica(args[0]);
			Parser as = new Parser(al);

			arv = as.parseProg();

			CodeGen backend = new CodeGen();
			String codigo = backend.geraCodigo(arv);
			System.out.println(codigo);

			File instructions = new File("arquivoDeEntrada");
			FileOutputStream fileOutputStream = new FileOutputStream(instructions);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

			byte[] codeBytes = codigo.getBytes();

			bufferedOutputStream.write(codeBytes);
			bufferedOutputStream.close();
			fileOutputStream.close();
		} catch (Exception e) {
			System.out.println("Erro de compilação:\n" + e);
		}

	}
}

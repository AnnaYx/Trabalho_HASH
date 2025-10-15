import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class GeradorDados {
    public static void gerarTodos(long seed) throws IOException {
        int[] tamanhos = {10000, 100000, 1000000};
        String[] nomes = {"data_10k.txt", "data_100k.txt", "data_1m.txt"};
        Random rnd = new Random(seed);
        for (int i = 0; i < tamanhos.length; i++) {
            int n = tamanhos[i];
            String nome = nomes[i];
            System.out.println("Gerando " + nome + " com " + n + " registros (seed=" + seed + ")...");
            BufferedWriter bw = new BufferedWriter(new FileWriter(nome));
            try {
                for (int k = 0; k < n; k++) {
                    int v = rnd.nextInt(); //
                    if (v < 0) v = -v;
                    v = v % 1000000000; // garante 9 dígitos

                    // formata para 9 dígitos
                    char[] buf = new char[9];
                    int temp = v;
                    for (int p = 8; p >= 0; p--) {
                        buf[p] = (char)('0' + (temp % 10));
                        temp = temp / 10;
                    }
                    bw.write(new String(buf));
                    bw.newLine();

                }
            } finally {
                bw.close();
            }
            System.out.println("Arquivo " + nome + " criado.");
        }
    }

    public static void main(String[] args) throws Exception {
        long seed = 123456789L;
        if (args.length > 0) seed = Long.parseLong(args[0]);
        gerarTodos(seed);
        System.out.println("Geração finalizada.");
    }
}

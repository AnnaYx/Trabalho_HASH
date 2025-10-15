import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class ExecutorBenchmark {
    public static void main(String[] args) throws Exception {
        long seed = 123456789L;
        boolean generate = args.length > 0 && args[0].equalsIgnoreCase("gerar");
        if (generate) {
            GeradorDados.gerarTodos(seed);
        }

        String[] arquivos = {"data_10k.txt", "data_100k.txt", "data_1m.txt"};
        int[] datasetSizes = {10000, 100000, 1000000};
        int[] tableSizes = {100, 1000, 10000};

        Estatisticas[] resultados = new Estatisticas[64];
        int resultadosCount = 0;

        for (int di = 0; di < arquivos.length; di++) {
            String arquivo = arquivos[di];
            int datasetSize = datasetSizes[di];
            System.out.println("Processando dataset: " + arquivo + " (tamanho nominal=" + datasetSize + ")");

            for (int tsi = 0; tsi < tableSizes.length; tsi++) {
                int ts = tableSizes[tsi];


                TabelaEncadeamento encMul = new TabelaEncadeamento(ts);
                long t0 = System.currentTimeMillis();
                BufferedReader br = new BufferedReader(new FileReader(arquivo));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        Registro r = new Registro(line.trim());
                        encMul.inserir(r);
                    }
                } finally { br.close(); }
                long t1 = System.currentTimeMillis();

                long searchStart = System.currentTimeMillis();
                br = new BufferedReader(new FileReader(arquivo));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        Registro r = new Registro(line.trim());
                        boolean found = encMul.contem(r);
                    }
                } finally { br.close(); }
                long searchEnd = System.currentTimeMillis();

                Estatisticas em = new Estatisticas();
                em.metodo = "ENC_MUL"; em.tableSize = ts; em.datasetSize = datasetSize;
                em.insertTimeMs = t1 - t0; em.searchTimeMs = searchEnd - searchStart; em.colisoes = encMul.getColisoes();
                int[] topm = encMul.topChains(); em.largestChain1 = topm[0]; em.largestChain2 = topm[1]; em.largestChain3 = topm[2];
                int[] gapm = encMul.gapStats(); em.gapMin = gapm[0]; em.gapMax = gapm[1]; em.gapAvg = gapm[2];

                if (resultadosCount == resultados.length) {
                    Estatisticas[] novo = new Estatisticas[resultados.length * 2];
                    for (int i = 0; i < resultados.length; i++) novo[i] = resultados[i];
                    resultados = novo;
                }
                resultados[resultadosCount++] = em;

                // LINEAR
                TabelaLinear lin = new TabelaLinear(ts);
                t0 = System.currentTimeMillis();
                br = new BufferedReader(new FileReader(arquivo));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        Registro r = new Registro(line.trim());
                        lin.inserir(r);
                    }
                } finally { br.close(); }
                t1 = System.currentTimeMillis();

                searchStart = System.currentTimeMillis();
                br = new BufferedReader(new FileReader(arquivo));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        Registro r = new Registro(line.trim());
                        boolean found = lin.contem(r);
                    }
                } finally { br.close(); }
                searchEnd = System.currentTimeMillis();

                Estatisticas el = new Estatisticas();
                el.metodo = "LIN"; el.tableSize = ts; el.datasetSize = datasetSize;
                el.insertTimeMs = t1 - t0; el.searchTimeMs = searchEnd - searchStart; el.colisoes = lin.getColisoes();
                int[] gl = lin.gapStats(); el.gapMin = gl[0]; el.gapMax = gl[1]; el.gapAvg = gl[2];

                if (resultadosCount == resultados.length) {
                    Estatisticas[] novo = new Estatisticas[resultados.length * 2];
                    for (int i = 0; i < resultados.length; i++) novo[i] = resultados[i];
                    resultados = novo;
                }
                resultados[resultadosCount++] = el;


                TabelaDoubleHash dh = new TabelaDoubleHash(ts);
                t0 = System.currentTimeMillis();
                br = new BufferedReader(new FileReader(arquivo));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        Registro r = new Registro(line.trim());
                        dh.inserir(r);
                    }
                } finally { br.close(); }
                t1 = System.currentTimeMillis();

                searchStart = System.currentTimeMillis();
                br = new BufferedReader(new FileReader(arquivo));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        Registro r = new Registro(line.trim());
                        boolean found = dh.contem(r);
                    }
                } finally { br.close(); }
                searchEnd = System.currentTimeMillis();

                Estatisticas ed = new Estatisticas();
                ed.metodo = "DH"; ed.tableSize = ts; ed.datasetSize = datasetSize;
                ed.insertTimeMs = t1 - t0; ed.searchTimeMs = searchEnd - searchStart; ed.colisoes = dh.getColisoes();
                int[] gd = dh.gapStats(); ed.gapMin = gd[0]; ed.gapMax = gd[1]; ed.gapAvg = gd[2];

                if (resultadosCount == resultados.length) {
                    Estatisticas[] novo = new Estatisticas[resultados.length * 2];
                    for (int i = 0; i < resultados.length; i++) novo[i] = resultados[i];
                    resultados = novo;
                }
                resultados[resultadosCount++] = ed;


                BufferedWriter bw = new BufferedWriter(new FileWriter("resultados.csv"));
                try {
                    bw.write("metodo,tableSize,datasetSize,InserirMs,BuscarMs,colisoes,lc1,lc2,lc3,gapMin,gapMax,gapAvg\n");
                    for (int ri = 0; ri < resultadosCount; ri++) {
                        bw.write(resultados[ri].toString() + "\n");
                    }
                } finally { bw.close(); }
            }
        }
        System.out.println("Execução finalizada. Verifique resultados.csv");
    }
}

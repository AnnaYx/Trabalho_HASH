
public class TabelaLinear {
    private final Registro[] table;
    private int tamanho = 0;
    private long colisoes = 0;

    public TabelaLinear(int capacidade) {
        table = new Registro[capacidade];
    }

    private int hashDivisao(int chave) {
        int h = chave % table.length;
        if (h < 0) h += table.length;
        return h;
    }

    public void clearStats() { colisoes = 0; }

    public void inserir(Registro r) {
        int h = hashDivisao(r.getCodigoInt());
        int i = 0;
        while (i < table.length) {
            int pos = (h + i) % table.length;
            if (pos < 0) pos += table.length;

            if (table[pos] == null) {
                table[pos] = r;
                colisoes += i;
                tamanho++;
                return;
            } else if (table[pos].getCodigoInt() == r.getCodigoInt()) {
                colisoes += i;
                return;
            }
            i++;
        }
        // tabela cheia: ignorar
    }

    public boolean contem(Registro r) {
        int h = hashDivisao(r.getCodigoInt());
        int i = 0;
        while (i < table.length) {
            int pos = (h + i) % table.length;
            if (pos < 0) pos += table.length;
            if (table[pos] == null) return false;
            if (table[pos].getCodigoInt() == r.getCodigoInt()) return true;
            i++;
        }
        return false;
    }

    public long getColisoes() { return colisoes; }
    public int getSize() { return tamanho; }

    public int[] gapStats() {
        int min = Integer.MAX_VALUE;
        int max = 0;
        long sum = 0;
        int contador = 0;
        int cur = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) cur++;
            else {
                if (cur > 0) {
                    if (cur < min) min = cur;
                    if (cur > max) max = cur;
                    sum += cur;
                    contador++;
                    cur = 0;
                }
            }
        }
        if (cur > 0) {
            if (cur < min) min = cur;
            if (cur > max) max = cur;
            sum += cur;
            contador++;
        }
        if (contador == 0) return new int[] {0,0,0};
        int avg = (int)(sum / contador);
        return new int[] {min, max, avg};
    }
}

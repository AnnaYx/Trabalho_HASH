public class TabelaEncadeamento {
    private final No[] table;
    private int tamanho = 0;
    private long colisoes = 0;

    private static class No {
        Registro r;
        No proximo;
        No(Registro r) { this.r = r; }
    }

    public TabelaEncadeamento(int capacidade) {
        table = new No[capacidade];
    }

    private int hashMultiplicacao(int chave) {
        double A = 0.6180339887; // constante da fração áurea
        double prod = chave * A;
        long inteiro = (long) prod; // obtém parte inteira
        double frac = prod - (double) inteiro; // parte fracionária
        int idx = (int)(frac * table.length);
        if (idx < 0) idx = -idx;
        return idx;
    }

    public void clearStats() { colisoes = 0; }

    public void inserir(Registro reg) {
        int idx = hashMultiplicacao(reg.getCodigoInt());
        No atual = table[idx];
        int traversed = 0;

        while (atual != null) {
            traversed++;
            if (atual.r.getCodigoInt() == reg.getCodigoInt()) {
                colisoes += traversed;
                return;
            }
            atual = atual.proximo;
        }

        colisoes += traversed;
        No novo = new No(reg);
        novo.proximo = table[idx];
        table[idx] = novo;
        tamanho++;
    }

    public boolean contem(Registro reg) {
        int idx = hashMultiplicacao(reg.getCodigoInt());
        No atual = table[idx];
        while (atual != null) {
            if (atual.r.getCodigoInt() == reg.getCodigoInt()) return true;
            atual = atual.proximo;
        }
        return false;
    }

    public long getColisoes() { return colisoes; }
    public int getSize() { return tamanho; }

    public int[] topChains() {
        int top1 = 0, top2 = 0, top3 = 0;
        for (int i = 0; i < table.length; i++) {
            int c = 0;
            No n = table[i];
            while (n != null) {
                c++;
                n = n.proximo;
            }
            if (c > top1) { top3 = top2; top2 = top1; top1 = c; }
            else if (c > top2) { top3 = top2; top2 = c; }
            else if (c > top3) { top3 = c; }
        }
        return new int[] {top1, top2, top3};
    }

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

        if (contador == 0) return new int[] {0, 0, 0};
        int avg = (int)(sum / contador);
        return new int[] {min, max, avg};
    }
}

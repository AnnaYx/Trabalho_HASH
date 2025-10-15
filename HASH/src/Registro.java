
public class Registro {
    private int codigo;

    public Registro(String codigoStr) {
        // verifica se é nulo
        if (codigoStr == null) {
            System.out.println("Erro: código nulo");
            codigo = 0;
            return;
        }

        // calcula o tamanho
        int tamanho = 0;
        char[] chars = codigoStr.toCharArray();
        for (char c : chars) {
            tamanho++;
        }

        // Se tiver 9 caracteres, interpreta como número com zeros à esquerda
        if (tamanho == 9) {
            codigo = NoveDigitos(chars, tamanho);
        } else {
            // converte  de string para int
            int valor = 0;
            for (int i = 0; i < tamanho; i++) {
                char c = chars[i];
                if (c < '0' || c > '9') {
                    System.out.println("Erro: caractere inválido");
                    codigo = 0;
                    return;
                }
                valor = valor * 10 + (c - '0');
            }
            codigo = valor;
        }
    }

    public Registro(int codigoInt) {
        this.codigo = codigoInt;
    }

    // converte string de 9 dígitos em int
    private int NoveDigitos(char[] s, int tamanho) {
        int val = 0;
        for (int i = 0; i < tamanho; i++) {
            char c = s[i];
            if (c < '0' || c > '9') {
                System.out.println("Erro: caractere inválido no código");
                return 0;
            }
            val = val * 10 + (c - '0');
        }
        return val;
    }

    // devolve o código como string de 9 dígitos com zeros à esquerda
    public String getCodigoString() {
        char[] buf = new char[9];
        int v = codigo;

        // preencher de trás pra frente
        for (int i = 8; i >= 0; i--) {
            int digito = v % 10;
            buf[i] = (char) ('0' + digito);
            v = v / 10;
        }

        // monta string
        String resultado = "";
        for (int i = 0; i < 9; i++) {
            resultado += buf[i];
        }

        return resultado;
    }

    public int getCodigoInt() {
        return codigo;
    }

    public String toString() {
        return getCodigoString();
    }
}

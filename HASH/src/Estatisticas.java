public class Estatisticas {
    public String metodo;
    public int tableSize;
    public int datasetSize;
    public long insertTimeMs;
    public long searchTimeMs;
    public long colisoes;
    public int largestChain1, largestChain2, largestChain3;
    public int gapMin, gapMax, gapAvg;

    @Override
    public String toString() {
        return metodo + "," + tableSize + "," + datasetSize + "," + insertTimeMs + "," + searchTimeMs + "," + colisoes + "," +
                largestChain1 + "," + largestChain2 + "," + largestChain3 + "," + gapMin + "," + gapMax + "," + gapAvg;
    }
}

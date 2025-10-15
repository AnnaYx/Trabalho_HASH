# PJBL HASH

---

##  Descrição Geral
O projeto implementa e compara o desempenho de **três** técnicas de hashing em Java:
- **Rehashing Linear** (endereçamento aberto, sondagem linear)
- **Rehashing Duplo** (double hashing)
- **Encadeamento com hash por multiplicação** (listas encadeadas em cada posição)

O objetivo é medir o **tempo de inserção**, **tempo de busca**, **número de colisões**, **três maiores listas (encadeamento)** e **estatísticas de gaps** entre posições ocupadas no vetor.

---
##  Implementações incluídas

### 1. Tabela com Encadeamento (hash por multiplicação)
- Função de hash por multiplicação usando `A = 0.6180339887` 
- Encadeamento separado: cada posição do vetor guarda uma lista ligada (No interno).
- Método de inserção: `inserir(Registro)` e busca: `contem(Registro)`.
- Métodos auxiliares: `getColisoes()`, `topChains()`, `gapStats()` e `clearStats()`.

### 2. Tabela com Rehashing Linear
- Hash por divisão (`k % m`) e sondagem linear (`(h + i) % m`).
- Inserção e busca com contagem de colisões (número de tentativas até encontrar slot livre).
- Métodos auxiliares: `getColisoes()`, `gapStats()` e `clearStats()`.

### 3. Tabela com Rehashing Duplo
- Duas funções de hash: `h1(k)` e `h2(k)`; sonde com `(h1 + i * h2) % m`.
- Mantém contador de colisões e fornece `gapStats()`.

---

## Parâmetros dos testes

- **Conjuntos de dados (gerados internamente com seed fixa):**
  - `10_000` registros
  - `100_000` registros
  - `1_000_000` registros

- **Tamanhos das tabelas (vetor):**
  - `100`
  - `1_000`
  - `10_000`

- **Seed utilizada (para repetibilidade):** `123456789L` (padrão mas pode ser alterada via argumento)

- **Arquivo de saída dos resultados:** `resultados.csv` com colunas:
  `metodo,tableSize,datasetSize,InserirMs,BuscarMs,colisoes,lc1,lc2,lc3,gapMin,gapMax,gapAvg`

---
##  Resultado esperado (fotos e graficos na pasta "Resultados")

### A tabela apresenta os dados coletados em todos os testes feitos com os diferentes métodos de hashing:

- **ENC_MUL:** Encadeamento com função de hashing por multiplicação

- **LIN:** Endereçamento aberto com sondagem linear

- **DH:** Endereçamento aberto com duplo hashing

Os valores incluem o tempo de inserção e busca em milissegundos, o total de colisões, os gaps e as três maiores listas geradas no método de encadeamento.

---
## Grafico de colisões
> Esse gráfico mostra a quantidade total de colisões registradas em cada técnica de hashing para diferentes tamanhos de tabela.

O encadeamento por multiplicação (ENC_MUL) apresentou um número significativamente maior de colisões para conjuntos muito grandes, mas manteve o desempenho estável em buscas.

Os métodos LINEAR e DOUBLE HASHING tiveram menos colisões, especialmente em tabelas maiores, mostrando melhor distribuição das chaves.

---
## Gráfico de listas (encadeamento)
Esse gráfico exibe o tamanho das três maiores listas dentro das tabelas de encadeamento.

Mostra o nível de concentração de colisões em certos índices.

É possível observar que, à medida que o conjunto de dados cresce, as listas ficam mais longas, evidenciando o impacto da escolha do fator de carga e da função hash.

---

## Tempo de inserção
O gráfico de tempo de inserção compara o desempenho entre os métodos conforme aumenta o número de elementos inseridos.

- O encadeamento mantém tempos mais constantes até certo ponto, mas sofre quando há excesso de colisões.

- O linear probing tende a crescer moderadamente.

- O duplo hashing (DH) é o mais estável, mostrando eficiência mesmo em tabelas grandes.
---
## Tempo de busca
Mostra o tempo gasto para realizar buscas nos diferentes métodos e tamanhos de tabela.

O duplo hashing mantém o melhor equilíbrio entre tempo e número de colisões.

O linear apresenta um leve crescimento com o aumento da carga.

O encadeamento tem boa performance geral, mas com variação dependendo da distribuição interna das listas.

---
## Desempenho

### Desempenho de Inserção
- Para datasets pequenos (10k), os três métodos tendem a apresentar tempos iguais.  
- Em datasets grandes (1M), **encadeamento por multiplicação** pode apresentar maior custo de inserção se muitas colisões gerarem listas longas; no entanto, se a função de multiplicação dispersar bem, o encadeamento costuma se comportar de forma estável.  
- **Rehashing duplo** costuma oferecer boa dispersão, resultando em menos tentativas por inserção em média.
- **Rehashing linear** pode sofrer com clustering primário e, assim, aumentar as tentativas de sondagem em tabelas muito ocupadas.

### Desempenho de Busca
- A busca no **encadeamento** depende do tamanho médio das listas, ou seja, é diretamente proporcional à carga. 
- Busca em **rehashing** depende do número de tentativas por colisão; double hashing tende a reduzir o número médio de tentativas comparado ao linear.

### Colisões e Distribuição
- Comparando colisões entre métodos: menor valor indica melhor desempenho.  
- `gapStats()` Os gaps representam os espaços vazios entre elementos armazenados na tabela hash. Eles indicam o quão bem as chaves estão distribuídas: gaps menores significam uma dispersão mais uniforme e eficiente, enquanto gaps muito grandes mostram regiões pouco ocupadas. O rehashing duplo geralmente reduz esses espaços, melhorando a distribuição e diminuindo o risco de colisões concentradas.

---

## Conclusão 

> Após a execução dos testes com diferentes tamanhos de datasets e tabelas, nota que o método Double Hashing (DH) apresentou o melhor desempenho geral.
Esse resultado se deve principalmente ao menor número de colisões e à melhor distribuição dos elementos na tabela, o que se refletiu em tempos de inserção e busca mais estáveis mesmo em cenários com alta carga de dados.

O Encadeamento Multiplicativo **(ENC_MUL)** manteve desempenho razoável em tabelas menores, mas apresentou crescimento maior no tempo de inserção à medida que o número de elementos aumentava — consequência do aumento no tamanho médio das listas encadeadas, que aumentou o custo de manipulação.
Já o Linear **(LIN)** teve mais colisões e maior degradação de desempenho com o aumento da carga, mostrando-se menos eficiente para grandes volumes de dados.

Os gráficos de colisões, tempos de inserção e busca e tamanho das listas reforçam esses resultados, mostrando que:

O Double Hashing mantém boa dispersão e baixo custo de acesso,

O Encadeamento sofre com sobrecarga em listas longas,

E o Linear perde eficiência rapidamente conforme a tabela enche.

---



## Como rodar: 
- I. Gerar dados no gerador de dados
- II. Executar benchmark
- III. Os resultados são salvos em `resultados.csv`.


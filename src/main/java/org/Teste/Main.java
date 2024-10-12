package org.Teste;

import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.util.stream.Collectors;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvValidationException;


public class Main {
    private static List<Candidato> candidatosList = new ArrayList<>();

    public static void main(String[] args) {
        String caminhoArquivo = "Academy_Candidates.csv";
        List<Candidato> candidatos = lerCandidatosDoCSV(caminhoArquivo);

        //exibirListaCompleta(candidatos);

        //1. Proporção de candidatos por vaga (porcentagem).
        //porcentagemInscricoesPorVaga(candidatos, "QA", "Web", "Mobile");

        //2. Idade média dos candidatos de QA.
        //idadeMediaCandidatosQA(candidatos, "QA");

        //3. Idade do candidato mais velho de Mobile.
        //candidatoMaisVelhoMobile(candidatos, "Mobile");

        //4. Idade do candidato mais novo de Web.
        //candidatoMaisNovoWeb(candidatos, "Web");

        //5. Soma das idades dos candidatos de QA.
        //somaIdadesCandidatosQA(candidatos, "QA");

        //6. Número de estados distintos presentes entre os candidatos.
        //estadosDistintos(candidatos);

        //7. Criar um arquivo chamado `Sorted_Academy_Candidates.csv` contendo os candidatos ordenados.
        //organizarCsv();

        //8. O nome do instrutor de QA descoberto.
        //encontrarInstrutorQA(candidatos, "QA", "SC");

        //9. O nome do instrutor de Mobile descoberto.
        //encontrarInstrutorMobile(candidatos, "Mobile", "PI");

    }

    public static class Candidato {
        private String nome;
        private String idade;
        private String vaga;
        private String estado;


        public Candidato(String nome, String idade, String vaga, String estado) {
            this.nome = nome;
            this.idade = idade;
            this.vaga = vaga;
            this.estado = estado;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getIdade() {
            return idade;
        }

        public void setIdade(String idade) {
            this.idade = idade;
        }

        public String getVaga() {
            return vaga;
        }

        public void setVaga(String vaga) {
            this.vaga = vaga;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        @Override
        public String toString() {
            return nome + " " + idade + " " + vaga + " " + estado;
        }
    }

    public static List<Candidato> lerCandidatosDoCSV(String caminhoArquivo) {
        List<Candidato> candidatos = new ArrayList<>();

        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(caminhoArquivo)) {
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is)).build();
            String[] linha;
            while ((linha = reader.readNext()) != null) {
                try {
                    String[] campos = linha[0].split(";"); // Dividir a linha pelos pontos e vírgulas

                    if (campos.length >= 4) {
                        String nome = campos[0].trim();
                        String idade = campos[1].trim();
                        String vaga = campos[2].trim();
                        String estado = campos[3].trim();

                        // Cria um novo objeto Candidato e adiciona à lista
                        Candidato candidato = new Candidato(nome, idade, vaga, estado);
                        candidatos.add(candidato);
                    } else {
                        System.err.println("Linha com número de colunas inválido: " + Arrays.toString(linha));
                    }

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Erro ao processar linha: " + e.getMessage());
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return candidatos;
    }

    public static void exibirListaCompleta(List<Candidato> candidatosList) {
        for (Candidato candidato : candidatosList) {
            System.out.println(candidato);
        }
    }

    public static void porcentagemInscricoesPorVaga(List<Candidato> candidatosList, String vagaQA, String vagaWeb, String vagaMobile) {
        List<Candidato> candidatosQA = new ArrayList<>();
        List<Candidato> candidatosWeb = new ArrayList<>();
        List<Candidato> candidatosMobile = new ArrayList<>();
        for (Candidato candidato : candidatosList) {
            if (candidato.getVaga().equalsIgnoreCase(vagaQA)) {
                candidatosQA.add(candidato);
            } else if (candidato.getVaga().equalsIgnoreCase(vagaWeb)) {
                candidatosWeb.add(candidato);
            } else if (candidato.getVaga().equalsIgnoreCase(vagaMobile)) {
                candidatosMobile.add(candidato);
            }
        }
        int qtdTotalCandidatos = candidatosList.size();
        int qtdTotalCandidatosQA = candidatosQA.size();
        int qtdTotalCandidatosWeb = candidatosWeb.size();
        int qtdTotalCandidatosMobile = candidatosMobile.size();

        double percCandidatosQA = (double) (qtdTotalCandidatosQA * 100) / qtdTotalCandidatos;
        double percCandidatosWeb = (double) (qtdTotalCandidatosWeb * 100) / qtdTotalCandidatos;
        double percCandidatosMobile = (double) (qtdTotalCandidatosMobile * 100) / qtdTotalCandidatos;

        System.out.println("Porcentagem Candidatos QA: " + percCandidatosQA + "%");
        System.out.println("Porcentagem Candidatos Web: " + percCandidatosWeb + "%");
        System.out.println("Porcentagem Candidatos Mobile: " + percCandidatosMobile + "%");
    }

    public static void idadeMediaCandidatosQA(List<Candidato> candidatosList, String vagaQA) {
        List<Candidato> candidatosQA = new ArrayList<>();
        for (Candidato candidato : candidatosList) {
            if (candidato.getVaga().equalsIgnoreCase(vagaQA)) {
                candidatosQA.add(candidato);
            }
        }

        int somaIdades = 0;
        for (Candidato candidato : candidatosQA) {
            String idadeString = candidato.getIdade();
            String[] partes = idadeString.split(" ");
            int idade = Integer.parseInt(partes[0]);
            somaIdades += idade;

        }

        int qtdCandidatosQA = candidatosQA.size();
        double mediaIdadeCandidatosQA = (double) somaIdades / qtdCandidatosQA;
        System.out.printf("Idade Média dos Candidatos QA: %.0f anos", mediaIdadeCandidatosQA);
    }

    public static void candidatoMaisVelhoMobile(List<Candidato> candidatosList, String vagaMobile) {
        List<Candidato> candidatosMobile = new ArrayList<>();
        for (Candidato candidato : candidatosList) {
            if (candidato.getVaga().equalsIgnoreCase(vagaMobile)) {
                candidatosMobile.add(candidato);
            }
        }
        int maiorIdade = Integer.MIN_VALUE;

        for (Candidato candidato : candidatosMobile) {
            String idadeString = candidato.getIdade();
            String[] partes = idadeString.split(" ");
            int idade = Integer.parseInt(partes[0]);
            if (idade > maiorIdade) {
                maiorIdade = idade;
            }
        }

        System.out.println("Idade do Candidato mais Velho de Mobile: " + maiorIdade + " anos");
    }

    public static void candidatoMaisNovoWeb(List<Candidato> candidatosList, String vagaWeb) {
        List<Candidato> candidatosWeb = new ArrayList<>();
        for (Candidato candidato : candidatosList) {
            if (candidato.getVaga().equalsIgnoreCase(vagaWeb)) {
                candidatosWeb.add(candidato);
            }
        }
        int menorIdade = Integer.MAX_VALUE;

        for (Candidato candidato : candidatosWeb) {
            String idadeString = candidato.getIdade();
            String[] partes = idadeString.split(" ");
            int idade = Integer.parseInt(partes[0]);
            if (idade < menorIdade) {
                menorIdade = idade;
            }
        }

        System.out.println("Idade do Candidato mais Novo de Web: " + menorIdade + " anos");
    }

    public static void somaIdadesCandidatosQA(List<Candidato> candidatosList, String vagaQA) {
        List<Candidato> candidatosQA = new ArrayList<>();
        for (Candidato candidato : candidatosList) {
            if (candidato.getVaga().equalsIgnoreCase(vagaQA)) {
                candidatosQA.add(candidato);
            }
        }

        int somaIdades = 0;
        for (Candidato candidato : candidatosQA) {
            String idadeString = candidato.getIdade();
            String[] partes = idadeString.split(" ");
            int idade = Integer.parseInt(partes[0]);
            somaIdades += idade;
        }

        System.out.println("Soma das idades dos candidatos de QA: " + somaIdades);
    }

    public static void estadosDistintos(List<Candidato> candidatosList) {
        List<String> estadosRepitidos = new ArrayList<>();
        for (Candidato candidato : candidatosList) {
            estadosRepitidos.add(candidato.estado);
        }

        System.out.println(estadosRepitidos.stream().distinct().collect(Collectors.toList()));
    }

    public static void organizarCsv() {
        List<Candidato> candidatos = new ArrayList<>();
        String caminhoArquivo = "Academy_Candidates.csv";

        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(caminhoArquivo)) {
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is)).build();
            String[] linha;
            while ((linha = reader.readNext()) != null) {
                try {
                    String[] campos = linha[0].split(";");

                    if (campos.length >= 4) {
                        String nome = campos[0].trim();
                        String idade = campos[1].trim();
                        String vaga = campos[2].trim();
                        String estado = campos[3].trim();

                        Candidato candidato = new Candidato(nome, idade, vaga, estado);
                        candidatos.add(candidato);
                    } else {
                        System.err.println("Linha com número de colunas inválido: " + Arrays.toString(linha));
                    }

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Erro ao processar linha: " + e.getMessage());
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        Collections.sort(candidatos, Comparator.comparing(Candidato::getNome));

        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter("Sorted_Academy_Candidates.csv"))
                .withSeparator(';')
                .build()) {
            writer.writeNext(new String[]{"Nome", "Idade"});

            for (Candidato candidato : candidatos) {
                writer.writeNext(new String[]{candidato.getNome(), String.valueOf(candidato.getIdade())});
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void encontrarInstrutorQA(List<Candidato> candidatosList, String vagaQA, String estado) {
        for (Candidato candidato : candidatosList) {
            String nomeCompleto = candidato.getNome().toLowerCase();
            String[] partes = nomeCompleto.split(" ");
            String primeiroNome = partes[0];
            String primeiroNomeInvertido = new StringBuilder(primeiroNome).reverse().toString();

            String idadeString = candidato.getIdade();
            String[] partesIdade = idadeString.split(" ");
            int idade = Integer.parseInt(partesIdade[0]);
            double raizQuadrada = Math.sqrt(idade);
            boolean ehQuadradoPerfeito = raizQuadrada == (int) raizQuadrada;

            if (candidato.getVaga().equalsIgnoreCase(vagaQA) &&
                    candidato.getEstado().equalsIgnoreCase(estado) &&
                    primeiroNome.equalsIgnoreCase(primeiroNomeInvertido) &&
                    ehQuadradoPerfeito) {
                System.out.println(candidato);
            }
        }
    }

    public static void encontrarInstrutorMobile(List<Candidato> candidatosList, String vagaMobile, String estado) {
        for (Candidato candidato : candidatosList) {
            String nomeCompleto = candidato.getNome().toLowerCase();
            String[] partes = nomeCompleto.split(" ");
            String primeiroNome = partes[0];

            char primeiraLetraNome = primeiroNome.charAt(0);

            String idadeString = candidato.getIdade();
            String[] partesIdade = idadeString.split(" ");
            int idade = Integer.parseInt(partesIdade[0]);

            if (candidato.getVaga().equalsIgnoreCase(vagaMobile) &&
                    candidato.getEstado().equalsIgnoreCase(estado) &&
                    idade >= 30 && idade <= 40 && primeiraLetraNome == 'c')
                System.out.println(candidato);
        }
    }


    }



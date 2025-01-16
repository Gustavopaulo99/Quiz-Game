
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            String caminhoArquivo = "perguntas.txt";
            List<Perguntas> perguntas = carregarPerguntas(caminhoArquivo);

            for (Perguntas p : perguntas) {
                System.out.println("Perguntas: " + p.getPergunta());
                List<String> opcoes = p.getOpcoes();

                for (int i = 0; i < opcoes.size(); i++) {
                    System.out.println((i + 1) + ". " + opcoes.get(i));
                }
                
                System.out.println("Resposta correta: " + p.getRespostaCorreta());
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("erro ao carregar o arquivo: " + e.getMessage());
        }

    }

    public static List<Perguntas> carregarPerguntas(String caminhoArquivo) throws IOException {
        List<Perguntas> listaPerguntas = new ArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            String perguntaTexto = null;
            List<String> opcoes = new ArrayList<>();
            String respostaCorrreta = null;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.isEmpty()) {
                    continue;
                }

                if (linha.startsWith("#")) {
                    if (perguntaTexto != null) {
                        listaPerguntas.add(new Perguntas(perguntaTexto, new ArrayList<>(opcoes), respostaCorrreta));
                        opcoes.clear();
                    }
                    perguntaTexto = null;
                    respostaCorrreta = null;
                } else if (linha.startsWith("-")) {
                    opcoes.add(linha.substring(1).trim());
                } else if (linha.startsWith("*")) {
                    respostaCorrreta = linha.substring(1).trim();
                } else {
                    perguntaTexto = linha;
                }
            }

            if (perguntaTexto != null) {
                listaPerguntas.add(new Perguntas(perguntaTexto, opcoes, respostaCorrreta));
            }

        }
        return listaPerguntas;
    }
}

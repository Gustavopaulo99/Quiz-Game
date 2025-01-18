
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String caminhoArquivo = "perguntas.txt";
            List<Perguntas> perguntas = carregarPerguntas(caminhoArquivo);

            int pontos = 0;

            for (Perguntas p : perguntas) {
                System.out.println("Perguntas: " + p.getPergunta());
                List<String> opcoes = p.getOpcoes();

                for (int i = 0; i < opcoes.size(); i++) {
                    System.out.println((i + 1) + ". " + opcoes.get(i));
                }
                
                System.out.print("Digite o numero da resposta correta: ");
                int respostaUsuario;
                
                try {
                    respostaUsuario = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada invalida!");
                    continue;
                }

                if(respostaUsuario < 1 || respostaUsuario > opcoes.size()){
                    System.out.println("Resposta fora do intervalo!");
                    continue;
                }

                String respostaEscolhida = opcoes.get(respostaUsuario - 1);
                if(respostaEscolhida.equals(p.getRespostaCorreta())){
                    System.out.println("Resposta correta!");
                    pontos++;
                }else{
                    System.out.println("Errado! A resposta correta era: "+ p.getRespostaCorreta());
                }
                System.out.println();
            }

            System.out.println("Voce terminou o jogo com " + pontos + " pontos");
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

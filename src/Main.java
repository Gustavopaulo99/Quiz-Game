/**
 *  @author Gustavo Paulo
 *  @author Nicolas Ruan
 * 
 *  18/01/25
 */

import java.io.BufferedReader; // Permite ler arquivos de texto de maneira eficiente
import java.io.FileReader; // Facilita a leitura de arquivos linha por linha
import java.io.IOException; // Trata erros de entrada e saida
import java.util.ArrayList; // Implementação de uma lista dinamica
import java.util.List; // Interface para manipular coleções de elementos
import java.util.Scanner; // Permite capturar a entrada de usuarios via teclado

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {  // Scanner para capturar entrada do usuario
            String caminhoArquivo = "perguntas.txt"; // Caminho do arquivo
            
            // Chama o metodo que le o arquivo e retorna a lista de perguntas   
            List<Perguntas> perguntas = carregarPerguntas(caminhoArquivo);

            int pontos = 0; // inicializa a variavel para contar os pontos

            // Loop para percorrer cada pergunta na lista de perguntas
            for (Perguntas p : perguntas) { 
                System.out.println("Perguntas: " + p.getPergunta()); //Exibe a pergunta
                
                List<String> opcoes = p.getOpcoes(); // Obtem as opcoes da pergunta

                // Loop para exibir as opces de respostas
                for (int i = 0; i < opcoes.size(); i++) {
                    // Exibe cada opção numerada 
                    System.out.println((i + 1) + ". " + opcoes.get(i)); 
                }
                
                System.out.print("Digite o numero da resposta correta: ");
                int respostaUsuario;
                
                try { 
                    // Le a entrada do usuario e converte para inteiro 
                    respostaUsuario = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    // Captura as entradas invalidas que nao sejam numeros
                    System.out.println("Entrada invalida!");
                    continue; // Passa para a proxima pergunta
                }
                
                // Verifica se a resposta esta dentro do intervalo de opcoes
                if(respostaUsuario < 1 || respostaUsuario > opcoes.size()){
                    System.out.println("Resposta fora do intervalo!");
                    continue; // Passa para a proxima pergunta
                }

                // Obtem a opção escolhida pelo usuario
                String respostaEscolhida = opcoes.get(respostaUsuario - 1);
                
                // Compara a resposta do usuario com a correta
                if(respostaEscolhida.equals(p.getRespostaCorreta())){
                    System.out.println("Resposta correta!");
                    pontos++; // Incrementa se estiver correta
                }else{
                    // Exibe a resposta correta se o usuario errar 
                    System.out.println("Errado! A resposta correta era: "+ p.getRespostaCorreta());
                }
                System.out.println(); // Linha em branco para separar as perguntas
            }

            System.out.println("Voce terminou o jogo com " + pontos + " pontos");
        } catch (IOException e) { // Captura erro relacionado a leitura do arquivo
            // Exibe uma mensagem de erro detalhado
            System.err.println("erro ao carregar o arquivo: " + e.getMessage());
        }

    }

    // Metodo que le o arquivo e retorna uma lista de objetos Perguntas
    public static List<Perguntas> carregarPerguntas(String caminhoArquivo) throws IOException {
        
        // Inicializa a lista para armazenar perguntas
        List<Perguntas> listaPerguntas = new ArrayList();

        // Abre o arquivo por linha por linha
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha; 
            String perguntaTexto = null; // Armazena o texto da pergunta
            List<String> opcoes = new ArrayList<>(); // Lista armazena as opcoes de respostas
            String respostaCorreta = null; // Armazena a respostas correta

            // Le o arquivo linha por linha
            while ((linha = br.readLine()) != null) {
                
                // Remove espaços em branco no inicio e no fim da linha
                linha = linha.trim();

                // Ignora as linhas em brancos
                if (linha.isEmpty()) { 
                    continue;
                }

                // Detecta o inicio de uma nova pergunta
                if (linha.startsWith("#")) {

                    // Salva perguntas anterior antes de começar uma nova
                    if (perguntaTexto != null) {
                        // Adiciona a pergunta na lista
                        listaPerguntas.add(new Perguntas(perguntaTexto, new ArrayList<>(opcoes), respostaCorreta));

                        // Limpa as opcoes para uma  nova pergunta
                        opcoes.clear();
                    }

                    // Reseta os valores para uma nova pergunta
                    perguntaTexto = null;
                    respostaCorreta = null;

                
                 // Detecta uma nova opçao de resposta   
                } else if (linha.startsWith("-")) {

                    // Adiciona a opçao na lista (Removendo o "-")
                    opcoes.add(linha.substring(1).trim());

                // Detecta a resposta correta
                } else if (linha.startsWith("*")) {

                    // Adiciona a opçao na lista (Removendo o "*")
                    respostaCorreta = linha.substring(1).trim();
                    
                } else { 
                    // Qualquer outra linha considerada texto da pergunta
                    perguntaTexto = linha;
                }
            }

            if (perguntaTexto != null) {
                // Adiciona a uma pergunta na lista
                listaPerguntas.add(new Perguntas(perguntaTexto, opcoes, respostaCorreta));
            }

        }
        
        // Retorna a lista de pergunta
        return listaPerguntas;
    }
}

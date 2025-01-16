import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String caminhoArquivo = "perguntas.txt";
            List<Perguntas> perguntas = carregarPerguntas(caminhoArquivo);
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
        }
    }
}
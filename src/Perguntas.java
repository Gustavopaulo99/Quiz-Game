import java.util.List;

public class Perguntas {
    private String pergunta;
    private List<String> opcoes;
    private String respostaCorreta;

    public Perguntas(String pergunta, List<String> opcoes, String respostaCorreta) {
        this.pergunta = pergunta;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public String getRespostaCorreta() {
        return respostaCorreta;
    }
}

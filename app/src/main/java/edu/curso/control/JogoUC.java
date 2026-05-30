package edu.curso.control;

import edu.curso.model.Jogador;
import edu.curso.model.Peca;
import edu.curso.model.Tabuleiro;

public class JogoUC {

    private Tabuleiro tabuleiro;
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogadorAtual;

    public JogoUC() {
        this.tabuleiro = new Tabuleiro();
    }

    public void iniciarPartida(String nome1, String nome2) {
        jogador1 = new Jogador(nome1, new Peca('X'));
        jogador2 = new Jogador(nome2, new Peca('O'));
        jogadorAtual = jogador1;
        tabuleiro.reiniciar();
    }

    public void reiniciarPartida() {
        tabuleiro.reiniciar();
        jogadorAtual = jogador1;
    }

    public void terminarPartida() {
        // Encerra a partida
    }

    public void realizarJogada(int linha, int coluna) {
        if (tabuleiro.estaVazio(linha, coluna)) {
            tabuleiro.marcarPosicao(linha, coluna, jogadorAtual.getPeca().getTipo());
            alternarJogador();
        }
    }

    public void escolherPeca() {
        // Já definido durante inicialização
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public int[][] verificarVitoria() {
        return tabuleiro.verificarVitoria();
    }

    public boolean verificarEmpate() {
        return tabuleiro.verificarEmpate();
    }

    private void alternarJogador() {
        jogadorAtual = (jogadorAtual == jogador1) ? jogador2 : jogador1; // if ternário para alternar entre os jogadores
    }

}

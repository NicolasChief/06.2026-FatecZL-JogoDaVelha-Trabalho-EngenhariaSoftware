package edu.curso.model;

public class Tabuleiro {

    private char[][] posicoes = new char[3][3];

    public Tabuleiro(char[][] posicoes) {
        this.posicoes = posicoes;
    }

    public char[][] getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(char[][] posicoes) {
        this.posicoes = posicoes;
    }

    public void verificarVitoria() {

    }

    public void verificarEmpate() {

    }

}

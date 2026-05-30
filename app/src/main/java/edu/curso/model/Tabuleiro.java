package edu.curso.model;

public class Tabuleiro {

    private char[][] posicoes = new char[3][3];

    public Tabuleiro() {
        inicializar();
    }

    public Tabuleiro(char[][] posicoes) {
        this.posicoes = posicoes;
    }

    private void inicializar() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                posicoes[i][j] = ' ';
            }
        }
    }

    public char[][] getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(char[][] posicoes) {
        this.posicoes = posicoes;
    }

    public void marcarPosicao(int linha, int coluna, char peca) {
        if (linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3) {
            if (posicoes[linha][coluna] == ' ') {
                posicoes[linha][coluna] = peca;
            }
        }
    }

    public char getPosicao(int linha, int coluna) {
        if (linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3) {
            return posicoes[linha][coluna];
        }
        return ' ';
    }

    public boolean estaVazio(int linha, int coluna) {
        return getPosicao(linha, coluna) == ' ';
    }

    public int[][] verificarVitoria() {
        // Verificar linhas
        for (int i = 0; i < 3; i++) {
            if (posicoes[i][0] != ' ' && posicoes[i][0] == posicoes[i][1] && posicoes[i][1] == posicoes[i][2]) {
                return new int[][] { {i, 0}, {i, 1}, {i, 2} };
            }
        }

        // Verificar colunas
        for (int j = 0; j < 3; j++) {
            if (posicoes[0][j] != ' ' && posicoes[0][j] == posicoes[1][j] && posicoes[1][j] == posicoes[2][j]) {
                return new int[][] { {0, j}, {1, j}, {2, j} };
            }
        }

        // Verificar diagonal principal
        if (posicoes[0][0] != ' ' && posicoes[0][0] == posicoes[1][1] && posicoes[1][1] == posicoes[2][2]) {
            return new int[][] { {0, 0}, {1, 1}, {2, 2} };
        }

        // Verificar diagonal secundária
        if (posicoes[0][2] != ' ' && posicoes[0][2] == posicoes[1][1] && posicoes[1][1] == posicoes[2][0]) {
            return new int[][] { {0, 2}, {1, 1}, {2, 0} };
        }

        return null;
    }

    public boolean verificarEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (posicoes[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public void reiniciar() {
        inicializar();
    }

}

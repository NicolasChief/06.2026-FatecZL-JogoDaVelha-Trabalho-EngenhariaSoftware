package edu.curso.view;

import edu.curso.control.JogoUC;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JogoUI extends Application {

    private JogoUC jogo;
    private Button[][] botoes = new Button[3][3];
    private Label labelStatus;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        jogo = new JogoUC();
        jogo.iniciarPartida("Jogador X", "Jogador O");

        // Interface principal
        BorderPane principal = new BorderPane();
        principal.setStyle("-fx-background-color: #f0f0f0;");

        // Painel superior com título
        VBox topPane = criarPainelTopo();
        principal.setTop(topPane);

        // Painel central com tabuleiro
        GridPane tabuleiro = criarTabuleiro();
        principal.setCenter(tabuleiro);

        // Painel inferior com status e botão reiniciar
        VBox bottomPane = criarPainelInferior();
        principal.setBottom(bottomPane);

        //  Cena
        Scene scene = new Scene(principal, 500, 600);
        stage.setTitle("Jogo da Velha");
        stage.setScene(scene);
        stage.show();

        atualizarStatus();
    }

    private VBox criarPainelTopo() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #2c3e50;");

        Label titulo = new Label("JOGO DA VELHA");
        titulo.setFont(new Font("Arial", 28));
        titulo.setTextFill(Color.WHITE);

        vbox.getChildren().add(titulo);
        return vbox;
    }

    private GridPane criarTabuleiro() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #ecf0f1;");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button("");
                btn.setMinSize(120, 120);
                btn.setPrefSize(120, 120);
                btn.setMaxSize(120, 120);
                btn.setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-border-color: #34495e; -fx-border-width: 2; -fx-alignment: center; -fx-padding: 0;");

                int linha = i;
                int coluna = j;

                btn.setOnAction(e -> {
                    if (jogo.getTabuleiro().estaVazio(linha, coluna)) {
                        // Realizar jogada
                        jogo.realizarJogada(linha, coluna);
                        atualizarTabuleiro();

                        // Verificar vitória
                        int[][] vitoria = jogo.verificarVitoria();
                        if (vitoria != null) {
                            destacarVitoria(vitoria);
                            String vencedor = jogo.getJogador1().getPeca().getTipo() == 
                                            jogo.getTabuleiro().getPosicao(vitoria[0][0], vitoria[0][1])
                                ? jogo.getJogador1().getNome() : jogo.getJogador2().getNome();
                            mostrarAlerta("Vitória!", "Jogador " + vencedor + " venceu!");
                        } else if (jogo.verificarEmpate()) {
                            mostrarAlerta("Empate!", "Empate! Ninguém venceu.");
                        } else {
                            atualizarStatus();
                        }
                    }
                });

                botoes[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        return grid;
    }

    private VBox criarPainelInferior() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #2c3e50;");

        labelStatus = new Label();
        labelStatus.setFont(new Font("Arial", 16));
        labelStatus.setTextFill(Color.WHITE);

        Button btnReiniciar = new Button("Reiniciar Jogo");
        btnReiniciar.setFont(new Font("Arial", 14));
        btnReiniciar.setStyle("-fx-padding: 10px 30px;");
        btnReiniciar.setOnAction(e -> reiniciarJogo());

        vbox.getChildren().addAll(labelStatus, btnReiniciar);
        return vbox;
    }

    private void atualizarTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char posicao = jogo.getTabuleiro().getPosicao(i, j);
                botoes[i][j].setText(String.valueOf(posicao).equals(" ") ? "" : String.valueOf(posicao));

                if (posicao == 'X') {
                    botoes[i][j].setTextFill(Color.BLUE);
                    botoes[i][j].setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-border-color: #34495e; -fx-border-width: 2; -fx-alignment: center; -fx-padding: 0;");
                } else if (posicao == 'O') {
                    botoes[i][j].setTextFill(Color.RED);
                    botoes[i][j].setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-border-color: #34495e; -fx-border-width: 2; -fx-alignment: center; -fx-padding: 0;");
                } else {
                    botoes[i][j].setTextFill(Color.BLACK);
                    botoes[i][j].setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-border-color: #34495e; -fx-border-width: 2; -fx-alignment: center; -fx-padding: 0;");
                }
            }
        }
    }

    private void destacarVitoria(int[][] posicoes) {
        for (int[] pos : posicoes) {
            botoes[pos[0]][pos[1]].setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-border-color: #34495e; -fx-border-width: 2; -fx-background-color: #f1c40f; -fx-alignment: center; -fx-padding: 0;");
        }
    }

    private void atualizarStatus() {
        char peca = jogo.getJogadorAtual().getPeca().getTipo();
        String cor = peca == 'X' ? "azul (X)" : "vermelho (O)"; // if ternário para definir a cor do jogador
        labelStatus.setText("Turno do jogador " + cor);
    }

    private void reiniciarJogo() {
        jogo.reiniciarPartida();
        atualizarTabuleiro();
        atualizarStatus();

        // Limpar destaques
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-border-color: #34495e; -fx-border-width: 2; -fx-alignment: center; -fx-padding: 0;");
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();

        reiniciarJogo();
    }

}

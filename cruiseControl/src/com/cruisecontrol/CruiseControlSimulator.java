package com.cruisecontrol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CruiseControlSimulator extends Application {
	
	private static final int VELOCIDADE_MINIMA = 0;
	private static final int VELOCIDADE_MAXIMA = 245;


    private Semaphore semaforo = new Semaphore(1);
    private boolean cruiseControlAtivado = false;
    private int velocidadeDesejada = 30;
    private int velocidadeAtual = 0;
    private boolean wPressionado = false;
    private boolean sPressionado = false;
    private boolean qPressionado = false;
    private boolean aleatorio = true;

    private Text velocidadeText;
    private Text cruiseControlText;
    private Text velocidadeDesejadaText;
    private Text indicadorDesligadoText;
    private Circle indicadorCruiseControl;
    private Circle indicadorDesligado;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulador de Cruise Control");

        Pane root = new Pane();
        Scene scene = new Scene(root, 380, 200);

        velocidadeText = new Text(150, 50, "0 km/h");
        velocidadeText.setFont(Font.font(20));
        root.getChildren().add(velocidadeText);

        cruiseControlText = new Text(120, 100, "Cruise Control: Desativado");
        cruiseControlText.setFont(Font.font(14));
        root.getChildren().add(cruiseControlText);

        velocidadeDesejadaText = new Text(100, 150, "Velocidade Desejada: " + velocidadeDesejada + " km/h");
        velocidadeDesejadaText.setFont(Font.font(14));
        root.getChildren().add(velocidadeDesejadaText);
        
        indicadorDesligadoText = new Text(100, 150, "        Carro Desligado \nPress Q para Ligar o Carro");
        indicadorDesligadoText.setFont(Font.font(14));
        indicadorDesligadoText.setVisible(false);
        root.getChildren().add(indicadorDesligadoText);

        indicadorCruiseControl = new Circle(105, 97, 10, Color.GREEN);
        indicadorCruiseControl.setVisible(false);
        root.getChildren().add(indicadorCruiseControl);
        
        indicadorDesligado = new Circle(180, 100, 10, Color.RED);
        indicadorDesligado.setVisible(false);
        root.getChildren().add(indicadorDesligado);     

        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();

            if (keyCode == KeyCode.C) {
                toggleCruiseControl();
            } else if (keyCode == KeyCode.W) {
                wPressionado = true;
            } else if (keyCode == KeyCode.S) {
                sPressionado = true;
            } else if (keyCode == KeyCode.A) {
                // Lógica para virar para a esquerda
            } else if (keyCode == KeyCode.D) {
                // Lógica para virar para a direita
            } else if (keyCode == KeyCode.Q) {
            	toggleSimulacao();
            } else if (keyCode == KeyCode.UP) {
                aumentarVelocidadeDesejada();
            } else if (keyCode == KeyCode.DOWN) {
                diminuirVelocidadeDesejada();
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.W) {
                wPressionado = false;
            } else if (keyCode == KeyCode.S) {
                sPressionado = false;
            }
        });

        Thread simulacaoThread = new Thread(this::atualizarSimulacao);
        simulacaoThread.setDaemon(true);
        simulacaoThread.start();
        
        Thread sensorThread = new Thread(this::simuladorDeSensores);
        sensorThread.setDaemon(true);
        sensorThread.start();
    }

    private void toggleCruiseControl() {
        cruiseControlAtivado = !cruiseControlAtivado;
        if (cruiseControlAtivado && qPressionado == false) {
            cruiseControlText.setText("Cruise Control: Ativado");
            indicadorCruiseControl.setVisible(true);
        } else {
            cruiseControlText.setText("Cruise Control: Desativado");
            indicadorCruiseControl.setVisible(false);
        }
    }

    private void aumentarVelocidadeDesejada() {
        if (!cruiseControlAtivado) {
        	if(velocidadeDesejada < 230) {
        		velocidadeDesejada += 5;
        		atualizarVelocidadeDesejadaText();        		
        	}
        }
    }

    private void diminuirVelocidadeDesejada() {
        if (!cruiseControlAtivado) {
        	if(velocidadeDesejada > 30) {        		
        		velocidadeDesejada -= 5;
        		atualizarVelocidadeDesejadaText();
        	}
        }
    }

    private void atualizarVelocidadeDesejadaText() {
        velocidadeDesejadaText.setText("Velocidade Desejada: " + velocidadeDesejada + " km/h");
    }

    private void toggleSimulacao() {
        if (velocidadeAtual == 0) {
        	qPressionado = !qPressionado;
        	if(qPressionado) {
        		System.out.println("Carro Desligado " + qPressionado);
                indicadorDesligadoText.setVisible(true);
                indicadorDesligado.setVisible(true);
                velocidadeText.setVisible(false);
                velocidadeDesejadaText.setVisible(false);
                cruiseControlText.setVisible(false);
                indicadorCruiseControl.setVisible(false);
        	} else {
        		System.out.println("Carro Desligado " + qPressionado);
                indicadorDesligadoText.setVisible(false);
                indicadorDesligado.setVisible(false);
                velocidadeText.setVisible(true);
                velocidadeDesejadaText.setVisible(true);
                cruiseControlText.setVisible(true);
                indicadorCruiseControl.setVisible(false);
        	}
        	
        }
    }

    private void atualizarSimulacao() {
        while (true) {
            if (wPressionado && !sPressionado && qPressionado == false) {
                if (velocidadeAtual < VELOCIDADE_MAXIMA) {
                    try {
                        semaforo.acquire();
                        velocidadeAtual++;
                        semaforo.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!wPressionado && sPressionado && qPressionado == false) {
                if (velocidadeAtual > VELOCIDADE_MINIMA) {
                    try {
                        semaforo.acquire();
                        velocidadeAtual--;
                        semaforo.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (cruiseControlAtivado && qPressionado == false) {
                if (velocidadeAtual <= velocidadeDesejada) {
                    try {
                        semaforo.acquire();
                        velocidadeAtual++;
                        System.out.println("Acelerando " + velocidadeAtual);
                        semaforo.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (velocidadeAtual > velocidadeDesejada) {
                    try {
                        semaforo.acquire();
                        velocidadeAtual--;
                        System.out.println("Freando " + velocidadeAtual);
                        semaforo.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (sPressionado) {
                	toggleCruiseControl();
                }
            } else {
                if (wPressionado && !sPressionado && qPressionado == false) {
                    if (velocidadeAtual < VELOCIDADE_MAXIMA) {
                        try {
                            semaforo.acquire();
                            velocidadeAtual++;
                            semaforo.release();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (!wPressionado && sPressionado && qPressionado == false) {
                    if (velocidadeAtual > VELOCIDADE_MINIMA) {
                        try {
                            semaforo.acquire();
                            velocidadeAtual--;
                            semaforo.release();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // Garanta que a velocidade atual não ultrapasse os limites
            velocidadeAtual = Math.min(VELOCIDADE_MAXIMA, Math.max(VELOCIDADE_MINIMA, velocidadeAtual));

            Platform.runLater(() -> {
                velocidadeText.setText(velocidadeAtual + " km/h");
            });

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void simuladorDeSensores() {
        while (true) {
            if (!cruiseControlAtivado) {

                if (aleatorio && qPressionado == false) {
                    Random random = new Random();
                    int variacao = random.nextInt(11) - 5;
                    synchronized (this) {
                        velocidadeAtual += variacao;
                    }
                }else {
                	aleatorio = true;
                }
            }

            synchronized (this) {
                velocidadeAtual = Math.max(velocidadeAtual, 0);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
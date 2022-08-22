/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication10;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 *
 * @author Samurai
 */
public class ejerciciosJava extends JFrame implements KeyListener {

    //Matriz 24x47 caracteres individuales para el mapa
    String[][] mapaJuego = new String[24][47];
    int limiteX = mapaJuego.length;
    int limiteY = mapaJuego[0].length;
    String resultadoImpresion;

    int cImpacto = 0;
    //Elementos Logicos
    boolean impactado = false;
    boolean start = true;
    boolean generarPlayer;
    boolean enemigo1Disponible = true;
    boolean proyectilListo = false;
    //Pos jugador
    int puntuacion = 0;
    int timingEnemigos = 0;
    int posEnemigoInicial;
    String[] enemigoPlayer = new String[3];
    int[] posicionMensaje = new int[2];
    int[] posEnemigo1 = new int[2];
    int[] posJugador = new int[2];
    int[] posProyectil = new int[2];
    String nombreJugador = null;
    int puntuacionJugador, vidas, combustible;
    //Jugador in array
    String[] jugadorPlayer = new String[3];
    String tecla = "";
    //Elementos Graficos
    String explosionMensaje = "BOOOM!!";
    String[] secuenciaExplosionMensaje;
    String jugador = "<w>";
    String enemigo = ")O(";
    String proyectil = "|";
    //Secuencia de impacto
    String[] impactoDisparo = new String[3];
    boolean disparando = false;

    JTextArea textArea = new JTextArea("JUEGO JAVA 1.0");

    public ejerciciosJava() throws InterruptedException {
        //Ventana de control
        setSize(270, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(ejerciciosJava.this);
        setLayout(null);
        setVisible(true);
        textArea.setBounds(0, 0, 600, 800);
        textArea.setBorder(new LineBorder(Color.BLACK));
        add(textArea);
        repaint();
        //Asignar caracteres al mapa
        for (int x = 0; x < 24; x++) {
            for (int y = 0; y < 47; y++) {
                mapaJuego[x][y] = " ";
            }
        }
        //Caracteres del jugador en array
        jugadorPlayer = jugador.split("");
        enemigoPlayer = enemigo.split("");
        secuenciaExplosionMensaje = explosionMensaje.split("");
        //Secuencia de impacto
        impactoDisparo[0] = "( )";
        impactoDisparo[1] = "(*)";
        impactoDisparo[2] = ".";
        //Posicion inicial jugador
        posJugador[0] = 22;
        posJugador[1] = 22;
        posProyectil[0] = 0;
        posProyectil[1] = 0;
        //Ciclo principal
        while (start == true) {
            textArea.setText(null);
            if (puntuacion < 10) {
                textArea.append("---------------------------------------------------------------\n");
                textArea.append("| Jugador   : Player1 |" + "  *  " + "| Vidas      : 00 |\n");
                textArea.append("| Puntuacion: 000000" + String.valueOf(puntuacion) + " |" + "  *  " + "| Combustible: 00 |\n");
                textArea.append("---------------------------------------------------------------\n");
            } else if (puntuacion < 100 && puntuacion >= 10) {
                textArea.append("---------------------------------------------------------------\n");
                textArea.append("| Jugador   : Player1 |" + "  *  " + "| Vidas      : 00 |\n");
                textArea.append("| Puntuacion: 00000" + String.valueOf(puntuacion) + " |" + "  *  " + "| Combustible: 00 |\n");
                textArea.append("---------------------------------------------------------------\n");
            } else if (puntuacion >= 100) {
                textArea.append("---------------------------------------------------------------\n");
                textArea.append("| Jugador   : Player1 |" + "  *  " + "| Vidas      : 00 |\n");
                textArea.append("| Puntuacion: 0000" + String.valueOf(puntuacion) + " |" + "  *  " + "| Combustible: 00 |\n");
                textArea.append("---------------------------------------------------------------\n");

            }

            if (null != tecla) {
                switch (tecla) {
                    case "w":
                        for (int y = 0; y < jugadorPlayer.length; y++) {
                            mapaJuego[posJugador[0]][posJugador[1] + y] = " ";
                        }
                        posJugador[0] = posJugador[0] - 1;
                        tecla = "";
                        break;
                    case "s":
                        for (int y = 0; y < jugadorPlayer.length; y++) {
                            mapaJuego[posJugador[0]][posJugador[1] + y] = " ";
                        }
                        posJugador[0] = posJugador[0] + 1;
                        tecla = "";
                        break;
                    case "a":
                        for (int y = 0; y < jugadorPlayer.length; y++) {
                            mapaJuego[posJugador[0]][posJugador[1] + y] = " ";
                        }
                        posJugador[1] = posJugador[1] - 2;
                        tecla = "";
                        break;
                    case "d":
                        for (int y = 0; y < jugadorPlayer.length; y++) {
                            mapaJuego[posJugador[0]][posJugador[1] + y] = " ";
                        }
                        posJugador[1] = posJugador[1] + 2;
                        tecla = "";
                        break;
                    case "x":
                        proyectilListo = true;
                        impactado = false;
                        disparando = true;
                        tecla = "";
                        break;
                    default:
                        break;
                }
            }

            for (int y = 0; y < jugadorPlayer.length; y++) {
                mapaJuego[posJugador[0]][posJugador[1] + y] = String.valueOf(jugadorPlayer[y]);
            }

            if (disparando) {
                if (proyectilListo) {
                    mapaJuego[posProyectil[0]][posProyectil[1]] = " ";
                    posProyectil[0] = posJugador[0];
                    posProyectil[1] = posJugador[1] + 1;
                    proyectilListo = false;
                }
                if (posProyectil[0] == 0) {
                    mapaJuego[posProyectil[0]][posProyectil[1]] = " ";
                    disparando = false;
                    proyectilListo = false;
                } else {
                    if (posJugador[0] - 1 == posProyectil[0] - 1) {
                        posProyectil[0] = posProyectil[0] - 1;
                        mapaJuego[posProyectil[0]][posProyectil[1]] = proyectil;
                    } else {
                        mapaJuego[posProyectil[0]][posProyectil[1]] = " ";
                        posProyectil[0] = posProyectil[0] - 1;
                        mapaJuego[posProyectil[0]][posProyectil[1]] = proyectil;
                    }
                }
            }
            posEnemigoInicial = 1;

            if (enemigo1Disponible == true) {
                while (posEnemigoInicial != 2 * (posEnemigoInicial / 2)) {
                    posEnemigoInicial = (int) (Math.random() * 46 + 1);
                }

                posEnemigo1[0] = 0;
                posEnemigo1[1] = posEnemigoInicial - 1;
                enemigo1Disponible = false;
                generarPlayer = true;
            }

            if (generarPlayer == true) {
                for (int f = 0; f < enemigoPlayer.length; f++) {
                    mapaJuego[posEnemigo1[0]][posEnemigo1[1] = posEnemigo1[1] + 1] = enemigoPlayer[f];
                }
                generarPlayer = false;
            }

            if (enemigo1Disponible == false) {
                if (timingEnemigos == 15) {
                    posEnemigo1[1] = posEnemigo1[1] - 3;
                    for (int f = 0; f < enemigoPlayer.length; f++) {
                        mapaJuego[posEnemigo1[0]][posEnemigo1[1] = posEnemigo1[1] + 1] = " ";
                    }
                    posEnemigo1[1] = posEnemigo1[1] - 3;
                    posEnemigo1[0] = posEnemigo1[0] + 1;

                    for (int f = 0; f < enemigoPlayer.length; f++) {
                        mapaJuego[posEnemigo1[0]][posEnemigo1[1] = posEnemigo1[1] + 1] = enemigoPlayer[f];
                    }
                    timingEnemigos = 0;
                } else {
                    timingEnemigos++;
                }

                if (posProyectil[0] == posEnemigo1[0] && posProyectil[1] == posEnemigo1[1] - 1) {
                    posProyectil[0] = posJugador[0];
                    posProyectil[1] = posJugador[1] + 1;

                    posEnemigo1[1] = posEnemigo1[1] - 3;
                    for (int f = 0; f < enemigoPlayer.length; f++) {
                        mapaJuego[posEnemigo1[0]][posEnemigo1[1] = posEnemigo1[1] + 1] = " ";
                    }

                    puntuacion = puntuacion + 25;
                    enemigo1Disponible = true;
                    disparando = false;
                    impactado = true;
                }
            }

            if (impactado) {
                posicionMensaje[0] = posEnemigo1[0];
                posicionMensaje[1] = posEnemigo1[1] - 4;

                for (int x = 0; x < secuenciaExplosionMensaje.length; x++) {
                    //BO OM! !!
                    mapaJuego[posicionMensaje[0]][posicionMensaje[1] = posicionMensaje[1] + 1] = secuenciaExplosionMensaje[x];
                }
                cImpacto = 1;
                impactado = false;
            } else {
                if (cImpacto == 1 && disparando == true) {
                    posicionMensaje[1] = posicionMensaje[1] - 7;
                    for (int x = 0; x < secuenciaExplosionMensaje.length; x++) {
                        mapaJuego[posicionMensaje[0]][posicionMensaje[1] = posicionMensaje[1] + 1] = " ";
                    }
                    cImpacto = 0;
                } else if (cImpacto == 1 && posicionMensaje[0] == posEnemigo1[0]) {
                    posicionMensaje[1] = posicionMensaje[1] - 7;

                    for (int x = 0; x < secuenciaExplosionMensaje.length; x++) {
                        mapaJuego[posicionMensaje[0]][posicionMensaje[1] = posicionMensaje[1] + 1] = " ";
                    }
                    cImpacto = 0;
                }
            }

            resultadoImpresion = "";

            for (int x = 0; x < limiteX; x++) {
                for (int y = 0; y < limiteY; y++) {
                    resultadoImpresion = resultadoImpresion + mapaJuego[x][y];
                }
                resultadoImpresion = resultadoImpresion + "\n";
            }
            textArea.append(resultadoImpresion);
            Thread.sleep(20);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ejerciciosJava ejerciciosJava = new ejerciciosJava();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                this.tecla = "w";
                break;
            case KeyEvent.VK_S:
                this.tecla = "s";
                break;
            case KeyEvent.VK_A:
                this.tecla = "a";
                break;
            case KeyEvent.VK_D:
                this.tecla = "d";
                break;
            case KeyEvent.VK_X:
                this.tecla = "x";
                break;
            default:
                break;
        }
    }

    public static int numRandom() {
        int numRandom = (int) (Math.random() * 10 + 1);
        return numRandom;
    }

    public static String sumaVectores() {
        int cantidadNumeros = numRandom();
        int[] vector1 = new int[cantidadNumeros];
        int[] vector2 = new int[cantidadNumeros];
        int[] vector3 = new int[cantidadNumeros];

        for (int i = 0; i < cantidadNumeros; i++) {
            vector1[i] = numRandom();
            vector2[i] = numRandom();
            vector3[i] = vector1[i] + vector2[i];
        }

        return "Vector 1: " + Arrays.toString(vector1) + "\nVector 2: "
                + Arrays.toString(vector2) + "\nVector 3: " + Arrays.toString(vector3);
    }

    public static void sumaMatrices() {
        int cantidadNumeros = numRandom();
        int[][] matriz1 = new int[cantidadNumeros][cantidadNumeros];
        int[][] matriz2 = new int[cantidadNumeros][cantidadNumeros];
        int[][] matriz3 = new int[cantidadNumeros][cantidadNumeros];

        for (int x = 0; x < cantidadNumeros; x++) {
            for (int y = 0; y < cantidadNumeros; y++) {
                matriz1[x][y] = numRandom();
                matriz2[x][y] = numRandom();
                matriz3[x][y] = matriz1[x][y] + matriz2[x][y];
            }
        }
        System.out.println("Matriz 1: \n" + imprimirMatriz(matriz1) + "\n");
        System.out.println("Matriz 2: \n" + imprimirMatriz(matriz2) + "\n");
        System.out.println("Matriz 3: \n" + imprimirMatriz(matriz3) + "\n");
    }

    public static int[][] sumaMatricesM() {
        int cantidadNumeros = numRandom();
        int[][] matriz1 = new int[cantidadNumeros][cantidadNumeros];
        int[][] matriz2 = new int[cantidadNumeros][cantidadNumeros];
        int[][] matriz3 = new int[cantidadNumeros][cantidadNumeros];

        for (int x = 0; x < cantidadNumeros; x++) {
            for (int y = 0; y < cantidadNumeros; y++) {
                matriz1[x][y] = numRandom();
                matriz2[x][y] = numRandom();
                matriz3[x][y] = matriz1[x][y] + matriz2[x][y];
            }
        }
        return matriz3;
    }

    public static String imprimirMatriz(int[][] matriz) {
        int limiteX = matriz.length;
        int limiteY = matriz[0].length;
        String resultadoImpresion = "";

        for (int x = 0; x < limiteX; x++) {
            for (int y = 0; y < limiteY; y++) {
                if (y == limiteY - 1) {
                    if (matriz[x][y] < 10) {
                        resultadoImpresion = resultadoImpresion + "0" + matriz[x][y];
                    } else {
                        resultadoImpresion = resultadoImpresion + matriz[x][y];
                    }
                } else {
                    if (matriz[x][y] < 10) {
                        resultadoImpresion = resultadoImpresion + "0" + matriz[x][y] + "-";
                    } else {
                        resultadoImpresion = resultadoImpresion + matriz[x][y] + "-";
                    }
                }
            }
            resultadoImpresion = resultadoImpresion + "\n";
        }
        return resultadoImpresion;
    }

    public static String palabraMasGrande(String cadena) {
        String[] palabras = cadena.split("\\s|\n");
        String palabraMasLarga = palabras[0];

        for (String palabra : palabras) {
            if (palabra.length() >= palabraMasLarga.length()) {
                palabraMasLarga = palabra;
            }
        }
        return palabraMasLarga;
    }

    public static String invertirTexto(String texto) {
        String textoInvertido = String.valueOf(texto.charAt(texto.length() - 1));
        for (int x = texto.length() - 2; x >= 0; x--) {
            textoInvertido = textoInvertido + texto.charAt(x);
        }
        return textoInvertido;
    }

    public static String imprimirMatrizString(String[][] matriz) {
        int limiteX = matriz.length;
        int limiteY = matriz[0].length;
        String resultadoImpresion = "";

        for (int x = 0; x < limiteX; x++) {
            for (int y = 0; y < limiteY; y++) {
                resultadoImpresion = resultadoImpresion + matriz[x][y];
            }
            resultadoImpresion = resultadoImpresion + "\n";
        }
        return resultadoImpresion;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}

package ParteB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServidorClientB {

	public static final int PUERTO = 2020;

	public static void main(String[] args) throws InterruptedException {

		Scanner in = new Scanner(System.in);

		System.out.println("      APLICACI�N DE SERVIDOR      ");
		System.out.println("----------------------------------");

		InputStreamReader entrada = null;

		PrintStream salida = null;

		Socket socketAlCliente = null;

		InetSocketAddress direccion = new InetSocketAddress(PUERTO);

		try (ServerSocket serverSocket = new ServerSocket()) {
			serverSocket.bind(direccion);
			String opcionRecibida;

			int puntos1 = 0;
			int puntos2 = 0;

			boolean aux = true;

			int ronda = 0;

			do {

				ronda++;

				System.out.println("-Esperando al jugador 1...");

				socketAlCliente = serverSocket.accept();
				salida = new PrintStream(socketAlCliente.getOutputStream());

				entrada = new InputStreamReader(socketAlCliente.getInputStream());
				BufferedReader bf = new BufferedReader(entrada);

				opcionRecibida = bf.readLine();

				System.out.println("");

				System.out.println("-----Comienza la ronda " + ronda + " -----");
				System.out.println("[1]=Piedra [2]=Papel [3]=Tijeras");
				System.out.println("Jugador 2 Ingresa tu jugada:");

				boolean noValido = true;
				String miOpcion;
				do {
					miOpcion = in.nextLine();

					if (miOpcion.equals("1") || miOpcion.equals("2") || miOpcion.equals("3")) {
						salida.println(miOpcion);
						noValido = false;
					} else{
						System.out.println("Ingrese una opción válida");
					}
				} while (noValido);
				
				String j1 = opcionRecibida;
				String j2 = String.valueOf(miOpcion);

				if (j1.equals("1") && j2.equals("1")) {
					System.out.println("Empate");

				} else if (j1.equals("1") && j2.equals("2")) {
					puntos2 = puntos2 + 1;
					System.out.println("Gana la ronda el jugador 2\n");

				} else if (j1.equals("1") && j2.equals("3")) {
					puntos1++;
					System.out.println("Gana la ronda el jugador 1\n");

				} else if (j1.equals("2") && j2.equals("2")) {
					System.out.println("Empate");

				} else if (j1.equals("2") && j2.equals("1")) {
					puntos1++;
					System.out.println("Gana la ronda el jugador 1\n");

				} else if (j1.equals("2") && j2.equals("3")) {
					puntos2++;
					System.out.println("Gana la ronda el jugador 2\n");

				} else if (j1.equals("3") && j2.equals("3")) {
					System.out.println("Empate");

				} else if (j1.equals("3") && j2.equals("1")) {
					puntos1++;
					System.out.println("Gana la ronda el jugador 1\n");

				} else if (j1.equals("3") && j2.equals("2")) {
					puntos2++;
					System.out.println("Gana la ronda el jugador 2\n");

				} else {
					System.out.println("Datos introducidos NO VALIDOS");
				}

				salida = new PrintStream(socketAlCliente.getOutputStream());

				System.out.println("Marcador:");
				System.out.println("Jugador 1: " + puntos1);
				System.out.println("Jugador 2: " + puntos2 + "\n");
 
				salida.println(puntos1);
				salida.println(puntos2); 

				if (puntos1 >= 3) {
					System.out.println("------El jugador 1 ha ganado la partida a tres rondas------");
					aux = false;
					socketAlCliente.close();
				}
				if (puntos2 >= 3) {
					System.out.println("------El jugador 2 ha ganado la partida a tres rondas------");
					aux = false;
					socketAlCliente.close();

				}
			} while (aux);

		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error -> " + e);
			e.printStackTrace();
		}
	}
}

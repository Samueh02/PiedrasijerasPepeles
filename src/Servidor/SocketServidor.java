package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServidor {

	public static final int PUERTO = 2020;

	public static void main(String[] args) throws InterruptedException {
		System.out.println("      APLICACI�N DE SERVIDOR      ");
		System.out.println("----------------------------------");

		InputStreamReader entrada = null;

		PrintStream salida = null;

		Socket socketAlCliente = null;

		InetSocketAddress direccion = new InetSocketAddress(PUERTO);

		try (ServerSocket serverSocket = new ServerSocket()) {
			serverSocket.bind(direccion);
			String opcionRecibida;
			String numerosDados;
			do {

				System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);

				socketAlCliente = serverSocket.accept();

				entrada = new InputStreamReader(socketAlCliente.getInputStream());
				BufferedReader bf = new BufferedReader(entrada);

				opcionRecibida = bf.readLine();
				numerosDados = bf.readLine();
				System.out.println("SERVIDOR: Opción elegida por el cliente: " + opcionRecibida);
				System.out.println("SERVIDOR: Números introducidos por el cliente: " + numerosDados);

				String[] operadores = numerosDados.split("-");
				int numero1 = 0;
				int numero2 = 0;
				try {
					numero1 = Integer.parseInt(operadores[0]);
					numero2 = Integer.parseInt(operadores[1]);

				} catch (Exception e) {
				}
				int resultado = 0;
				switch (opcionRecibida) {

				case "1":
					resultado = numero1 + numero2;
					break;

				case "2":
					resultado = numero1 - numero2;

					break;

				case "3":
					resultado = numero1 * numero2;

					break;

				case "4":
					if (numero2 != 0) {
						resultado = numero1 / numero2;
					} else {
						resultado = 0;
					}

					break;

				case "5":
					System.out.println("Programa terminado");
					break;

				default:
					System.out.println("Error");

				}

				System.out.println("SERVIDOR: EL resultado es: " + resultado);

				salida = new PrintStream(socketAlCliente.getOutputStream());
				salida.println(resultado);

				socketAlCliente.close();
			} while (!opcionRecibida.equals("5"));
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error -> " + e);
			e.printStackTrace();
		}
	}
}

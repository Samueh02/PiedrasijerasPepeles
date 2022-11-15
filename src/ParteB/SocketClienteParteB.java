package ParteB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClienteParteB {

	// IP y Puerto a la que nos vamos a conectar
	public static final int PUERTO = 2020;
	public static final String IP_SERVER = "localhost";

	public static void main(String[] args) {
		System.out.println("        APLICACI�N CLIENTE         ");
		System.out.println("-----------------------------------");

		boolean imprimir = true;
		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);

		try (Scanner sc = new Scanner(System.in);) {

			boolean continuar = true;
			String opcion;
			do {
				Socket socketAlServidor = new Socket();
				socketAlServidor.connect(direccionServidor);

				PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());

				System.out.println(
						"Elija la opcion que quiera realizar: \n 1-Sumar \n 2-Restar \n 3-Multiplicar \n 4-Dividir \n 5-Salir");
				System.out.print("Opcion:");

				boolean valido = false;

				do {
					opcion = sc.nextLine();

					if (opcion.equals("1") || opcion.equals("2") || opcion.equals("3") || opcion.equals("4")
							|| opcion.equals("5")) {
						valido = true;
					} else {
						System.out.println("Ingrese una opción válida");
					}

				} while (valido != true);
				salida.println(opcion);

				if (!opcion.equals("5")) {

					System.out.println("Introduzca los numeros a operar:");
					String numero1 = sc.nextLine();
					String numero2 = sc.nextLine();
					String operandos = numero1 + "-" + numero2;
					salida.println(operandos);
				} else if (opcion.equals("5")) {
					continuar = false;
					String numero1 = "0";
					String numero2 = "0";
					String operandos = numero1 + "-" + numero2;
					imprimir = false;
					salida.println(operandos);
				}

				// System.out.println("CLIENTE: Esperando a que el servidor acepte la
				// conexi�n");
				// System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + " por
				// el puerto " + PUERTO);

				// System.out.println("CLIENTE: Esperando al resultado del servidor...");
				InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
				BufferedReader bf = new BufferedReader(entrada);
				String resultado = bf.readLine();
				if (imprimir) {
					System.out.println("CLIENTE: El resultado de la operacion es: " + resultado);

				}

				socketAlServidor.close();

			} while (continuar);
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la direcci�n" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}

		System.out.println("CLIENTE: Fin del programa");
	}

}

package app;

import java.awt.*;
import java.util.Random;

import javax.swing.ImageIcon;

public class Rutinas {

	public static final int IZQUIERDA = 0;
	public static final int DERECHA = 1;
	
	public static ImageIcon AjustarImagen(String ico, int Ancho, int Alto) {
		ImageIcon tmpIconAux = new ImageIcon(ico);
		ImageIcon tmpIcon = new ImageIcon(tmpIconAux.getImage().getScaledInstance(Ancho, Alto, Image.SCALE_SMOOTH));// SCALE_DEFAULT
		return tmpIcon;
	}

	static Random R = new Random();
	static String[] VN = { "Alicia", "Maria", "Sofia", "Antonio", "Nereida", "Carolina", "Rebaca", "Javier", "Luis" };
	static String[] VA = { "Garcia", "Lopez", "Perez", "Urias", "Mendoza", "Coppel", "Diaz" };
	static boolean[] Sexo = { false, false, false, true, false, false, false, true, true };

	private static String colores[] = { "Azul", "Verde", "Rojo", "Amarillo", "Morado", "Naranja", "Gris", "Café",
			"Blanco", "Rosa", "Negro" };

	public static String PonBlancos(String Texto, int Tamaño) {
		while (Texto.length() < Tamaño)
			Texto += " ";
		return Texto;
	}

	public static String PonCeros(int Valor, int Tamaño) {
		String Texto = Valor + "";
		while (Texto.length() < Tamaño)
			Texto = "0" + Texto;
		return Texto;
	}
	
	public static String rellenaCaracter(String texto, char caracter, int tam, int direccion) {
		switch (direccion) {
		case IZQUIERDA:
			while (texto.length() < tam)
				texto = caracter + texto;
			return texto;

		case DERECHA:
			while (texto.length() < tam)
				texto += caracter;
			return texto;
		default:
			return texto;
		}
	}

	public static int nextInt(int Valor) {
		return R.nextInt(Valor);
	}

	public static int nextInt(int Ini, int Fin) {
		return R.nextInt(Fin - Ini + 1) + Ini;
	}

	public static float nextFloat(float ini, float fin) {
		return R.nextFloat() * fin + ini;
	}

	public static String nextNombre(int Numero) {
		String Nom = "", NomTra = "";
		int Pos;
		boolean Genero = true;
		;

		for (int i = 0; i < Numero; i++) {
			Pos = nextInt(VN.length);

			NomTra = VN[Pos] + " ";

			if (i == 0) {
				Genero = Sexo[Pos];
			}

			if (Genero != Sexo[Pos] || i > 0 && Nom.indexOf(NomTra) > -1) {
				i--;
				continue;
			}

			Nom += NomTra + " ";

		}
		for (byte i = 0; i < 0; i++) {
			Nom += VA[nextInt(VA.length)] + " ";
		}
		return Nom;
	}

	public static String nextColor() {
		return colores[nextInt(0, 10)];
	}

	static int Contador;

	public static void Hanoi(char Inicial, char Central, char Final, int N) {
		if (N == 1) {
			Contador++;
			System.out.println(Contador + " Move disco " + N + " de la torre " + Inicial + " a la torre " + Final);
		} else {
			Hanoi(Inicial, Final, Central, N - 1);
			Contador++;
			System.out.println(Contador + " Move disco " + N + " de la torre " + Inicial + " a la torre " + Final);
			Hanoi(Central, Inicial, Final, N - 1);
		}
	}

}

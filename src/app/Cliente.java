package app;

public class Cliente extends Thread {
	private int tiempoEnCaja; //Tiempo en que el hilo se duerme cuando llega a la caja
	private int posX;
	private int posY;
	private int turno;
	public static int nivelFila = 1; //Se usa para ubicar a los clientes al principio
	public static int VELOCIDAD_EJECUCION = 20;
	private int color; //Para elegir las diferentes imágenes de clientes

	public Cliente(int x, int y, int turno, int color) {
		this.color = color;
		posX = x;
		posY = y;
		this.turno = turno;
		tiempoEnCaja = Rutinas.nextInt(2000, 5000);
	}

	public void run() {
		dormir(turno * 300); //Solo empezar, se duerme el hilo un determinado tiempo basado en su turno

		if (Ventana.clientesEnCola == 10) { //Para que se acomoden de 10 en 10
			nivelFila++; //Si ya hay 10, se pasa a la segunda fila incrementando el eje Y
			Ventana.clientesEnCola = 0; //Se reinicia el número de clientes en cola
		}

		posY = 500 - nivelFila * 50; //aquí se calcula la ubicación de cada cliente
		posX = 500 - (++Ventana.clientesEnCola * 40);

		while (true) { 
			if (turno != Ventana.turnoActual) { //Si no es su turno de pasar, se duerme 1 segundo y vuelve al inicio (continue)
				dormir(1000);
				continue;
			}

			int cajaActual = Ventana.cajaDisponible(); //Si sí es su turno, primero se espera 1 segundo
			dormir(1000);

			if (cajaActual == 0) //luego checa que haya cajas disponibles (cajaActual=0 significa que no hay cajas disponibles)
				continue;

			Ventana.turnoActual++; //Si llega aquí es porque sí hubo caja disponible

			irACaja(cajaActual); //Se hace el recorrido hacia la caja

			dormir(tiempoEnCaja); //Se duerme el respectivo tiempo

			switch (cajaActual) { //Una vez desocupe la caja, se verifica qué caja tomó para liberarla
			case 1:
				Ventana.caja1 = false;
				Ventana.siguiente1=true;
				break;
			case 2:
				Ventana.caja2 = false;
				Ventana.siguiente2=true;
				break;
			case 3:
				Ventana.caja3 = false;
				Ventana.siguiente3=true;
				break;
			}

			salir(); //se hace el recorrido de salida del Frame

			Ventana.clientesAtendidos++; //Clientes atendidos se incrementa en 1
			break;
		}
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public int getTurno() {
		return turno;
	}

	public int getTiempoEnCaja() {
		return tiempoEnCaja;
	}

	public int getColor() {
		return color;
	}

	public void setTiempoEnCaja(int milis) {
		tiempoEnCaja = milis;
	}
	
	private void salir() {
		while (posY > -30) {
			posY -= 1;
			dormir(VELOCIDAD_EJECUCION);
		}
		posX = 0;
	}

	private void irACaja(int numCaja) {
		while (posX < 530) { //Este primer recorrido es el de izquierda a derecha
			posX += 2;
			dormir(VELOCIDAD_EJECUCION);
		}
		while (posY > 190) { //Este es el de abajo hacia arriba
			posY -= 2;
			dormir(VELOCIDAD_EJECUCION);
		}

		switch (numCaja) { //Luego hay 1 recorrido dependiendo a qué caja vaya
		case 1://Caja 1
			while (posX > 280) {
				posX -= 2;
				dormir(VELOCIDAD_EJECUCION);
			}
			while (posY > 80) {
				posY -= 2;
				dormir(VELOCIDAD_EJECUCION);
			}
			Ventana.siguiente1=false;
			break;
		case 2://Caja 2
			while (posY > 80) {
				posY -= 2;
				dormir(VELOCIDAD_EJECUCION);
			}
			Ventana.siguiente2=false;
			break;
		case 3://Caja 3
			while (posX < 780) {
				posX += 2;
				dormir(VELOCIDAD_EJECUCION);
			}
			while (posY > 80) {
				posY -= 2;
				dormir(VELOCIDAD_EJECUCION);
			}
			Ventana.siguiente3=false;
		}
	}

	private void dormir(int mili) {
		try {
			sleep(mili);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
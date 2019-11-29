package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Ventana extends JFrame {
	private Graphics2D g2;
	private Image back;

	public static int clientesAtendidos, clientesEnCola;
	public static int turnoActual;
	private JPanel panelInferior;
	private JButton btnIniciar;
	public static boolean caja1, caja2, caja3, siguiente1, siguiente2, siguiente3;
	private boolean iniciado;
	private ArrayList<Cliente> clientes;

	private JSlider sliderVelocidad;

	private Image caja, cliente1, cliente2, cliente3;

	public Ventana() {
		hazInterfaz();
		ejecucion();
	}

	private void hazInterfaz() {
		setSize(1000, 570);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		caja = Rutinas.AjustarImagen("caja.png", 80, 80).getImage();
		cliente1 = Rutinas.AjustarImagen("persona.png", 50, 50).getImage();
		cliente2 = Rutinas.AjustarImagen("persona2.png", 50, 50).getImage();
		cliente3 = Rutinas.AjustarImagen("persona3.png", 50, 50).getImage();

		iniciado = false;

		turnoActual = 1;

		caja1 = false;
		caja2 = false;
		caja3 = false;
		siguiente1= true;
		siguiente2= true;
		siguiente3= true;

		sliderVelocidad = new JSlider(20, 200, 185);
		sliderVelocidad.addChangeListener(change -> {
			Cliente.VELOCIDAD_EJECUCION = 205 - sliderVelocidad.getValue();// Escuchador de la barra de velocidad
		});

		clientes = new ArrayList<Cliente>(); // Se crea el Array en el cual se guardarán los clientes

		for (int i = 0; i < 50; i++) {
			clientes.add(new Cliente(0, 0, i + 1, Rutinas.nextInt(0, 2))); // Se inicializan y se agregan al arreglo
		}

		btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(action -> { // Escuchador del botón Iniciar
			btnIniciar.setEnabled(false);
			iniciado = true;
			for (int i = 0; i < clientes.size(); i++) { // Una vez presionado, se inician todos los hilos
				clientes.get(i).start();
			}
		});

		panelInferior = new JPanel();
		panelInferior.setBounds(0, 500, 1000, 100);
		clientesAtendidos = 0;

		panelInferior.add(btnIniciar);
		panelInferior.add(sliderVelocidad);

		add(panelInferior);

		setVisible(true);
		back = createImage(getWidth(), getHeight()); // Se usó una técnica llamada "ScreenOff" para la graficación de
														// los componentes
		g2 = (Graphics2D) back.getGraphics();

		// ScreenOff consiste en crear una imagen general de todo lo que se esté
		// mostrando en la ventana activa,
		// Gracias a esto se logra hacer un mejor renderizado de los componentes
		// gráficos para que no estén parpadeando
		// o pintándose de manera desigual
	}

	private void ejecucion() {
		while (!finalizado() || !iniciado) { // Al iniciar se entra en este ciclo que se ejecutará hasta que no haya
												// ningún hilo en ejecución
			actualiza();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(this, "Simulación terminada");
		sliderVelocidad.setEnabled(false);
	}

	public void paint(Graphics g) {
		g.drawImage(back, 0, 0, null); // Se dibuja la imagen total de la ventana
	}

	private void actualiza() {
		super.paint(g2);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// Para un renderizado
																								// más suave
		g2.drawImage(Rutinas.AjustarImagen("fondo.jpg", 1000, 520).getImage(), 0, 0, null);
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, 1000, 520);

		g2.setColor(Color.black);
		g2.setFont(new Font("Impact", Font.BOLD, 16));
		g2.drawString("Clientes Atendidos: " + clientesAtendidos, 20, 50);

		g2.drawImage(caja, 200, 50, null);
		g2.drawImage(caja, 450, 50, null);
		g2.drawImage(caja, 700, 50, null);

		g2.setColor(new Color(255, 255, 255, 100));
		g2.fillRect(280, 0, 40, 200);
		g2.fillRect(530, 0, 40, 200);
		g2.fillRect(780, 0, 40, 200);
		g2.fillRect(530, 200, 40, 300);
		g2.fillRect(280, 200, 540, 40);

		g2.setColor(Color.green);//Se pintan los círculos verdes de cada caja disponible
		g2.fillOval(200, 40, 10, 10);
		g2.fillOval(450, 40, 10, 10);
		g2.fillOval(700, 40, 10, 10);

		//En esta parte se colocan o no los globitos que dicen Siguiente
		g2.setFont(new Font("Arial", Font.BOLD, 9));
		if (siguiente1) {
			g2.setColor(Color.white);
			g2.fillOval(225, 28, 55, 20);
			g2.fillPolygon(new int[] { 245, 260, 240 }, new int[] { 45, 45, 55 }, 3);
			g2.setColor(Color.black);
			g2.drawString("¡Siguiente!", 228, 41);
		}
		if (siguiente2) {
			g2.setColor(Color.white);
			g2.fillOval(475, 28, 55, 20);
			g2.fillPolygon(new int[] { 495, 510, 490 }, new int[] { 45, 45, 55 }, 3);
			g2.setColor(Color.black);
			g2.drawString("¡Siguiente!", 478, 41);
		}
		if (siguiente3) {
			g2.setColor(Color.white);
			g2.fillOval(725, 28, 55, 20);
			g2.fillPolygon(new int[] { 745, 760, 740 }, new int[] { 45, 45, 55 }, 3);
			g2.setColor(Color.black);
			g2.drawString("¡Siguiente!", 728, 41);
		}

		g2.setFont(new Font("Impact", Font.BOLD, 14));

		for (int i = 0; i < clientes.size(); i++) {
			int posX = clientes.get(i).getX();
			int posY = clientes.get(i).getY();

			if (posX != 0) { // Solo se grafican los clientes que ya hayan sido ubicados
				int color = clientes.get(i).getColor(); // Se toma el color para saber qué imagen graficar
				g2.drawImage(color == 0 ? cliente1 : color == 1 ? cliente2 : cliente3, posX, posY, null);
				int tiempoActual = clientes.get(i).getTiempoEnCaja() - 50;//Se le va restando el tiempo por cada vuelta

				g2.setColor(Color.RED);

				switch (posX) { // Dependiendo en que posición del eje X
				case 280: // Aquí significa que está en la posición X de la primera caja
					if (posY != 80)//Si está en la posición X pero no en la Y, significa que no está aún en la caja
						break;
					clientes.get(i).setTiempoEnCaja(tiempoActual);//Se le asigna el nuevo tiempo ya decrementado
					g2.drawString(tiempoActual / 1000 + "", 250, 40); //Se pinta el tiempo en segundos (por eso el tiempoActual/1000)
					g2.fillOval(200, 40, 10, 10);//Se pinta el círculo rojo que significa que la caja está ocupada
					break;
				case 530:
					if (posY != 80)
						break;
					clientes.get(i).setTiempoEnCaja(tiempoActual);
					g2.drawString(tiempoActual / 1000 + "", 500, 40);
					g2.fillOval(450, 40, 10, 10);
					break;
				case 780:
					if (posY != 80)
						break;
					clientes.get(i).setTiempoEnCaja(tiempoActual);
					g2.drawString(tiempoActual / 1000 + "", 750, 40);
					g2.fillOval(700, 40, 10, 10);
				}
			}
		}

		if (clientesAtendidos == clientes.size()) { // Cuando ya se acabaron los clientes, las cajas se ponen en
													// amarillo
			g2.setColor(Color.YELLOW);
			g2.fillOval(200, 40, 10, 10);
			g2.fillOval(450, 40, 10, 10);
			g2.fillOval(700, 40, 10, 10);
		}

		repaint(); // Se invoca al método "paint()"
	}

	private boolean finalizado() { // Verifica que haya hilos en ejecución
		for (int i = 0; i < clientes.size(); i++) {
			if (clientes.get(i).isAlive())
				return false;
		}
		return true;
	}

	public synchronized static int cajaDisponible() { // Método encargado de devolver una caja,
														// siempre y cuando esté disponible.
														// Es synchronized porque solo puede entrar un
														// hilo a la vez y así evitar que dos o más hilos
														// tomen la misma caja al mismo tiempo.
		if (!caja1) {
			caja1 = true;
			return 1;
		}
		if (!caja2) {
			caja2 = true;
			return 2;
		}
		if (!caja3) {
			caja3 = true;
			return 3;
		}
		return 0;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel()); // Establece un estilo de componentes más atractivo
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new Ventana();
	}

}

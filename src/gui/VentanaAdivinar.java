package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import excepciones.NumeroFueraDeRangoException;
import net.miginfocom.swing.MigLayout;

public class VentanaAdivinar extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumero;
	
	private int numIntentos=0;
	private int numAAcertar;
	private Random aleatorio;
	private JButton btnNuevo;
	private JLabel lblMensaje;
	
	public static final int NUMMAX=10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAdivinar frame = new VentanaAdivinar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaAdivinar() {
		aleatorio = new Random();
		iniciarJuego();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[133.00][grow]", "[][][][][][grow][]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(250, 128, 114));
		contentPane.add(panel, "cell 0 0 2 1,grow");
		
		JLabel lblNewLabel_1 = new JLabel("Juego Adivina el número");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Introduce tu número:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblNewLabel, "cell 0 2,alignx right");
		
		txtNumero = new JTextField();
		contentPane.add(txtNumero, "cell 1 2,growx");
		txtNumero.setColumns(10);
		
		lblMensaje = new JLabel("");
		lblMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblMensaje, "cell 1 3");
		
		JLabel lblNewLabel_2 = new JLabel("Intentos:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblNewLabel_2, "cell 0 4,alignx right");
		
		JLabel lblIntentos = new JLabel("");
		contentPane.add(lblIntentos, "cell 1 4");
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "cell 0 6 2 1,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[]"));
		
		JButton btnAceptar = new JButton("Aceptar");
		panel_1.add(btnAceptar, "flowx,cell 0 0,alignx center");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String numero = txtNumero.getText();
				int respuesta;
				try {
					respuesta = comprueba(numero);
				
					if (respuesta==0) {
						JOptionPane.showMessageDialog(contentPane, "El número es correcto");
						btnAceptar.setEnabled(false);
						btnNuevo.setEnabled(true);
					} else if (respuesta <0) {
						lblMensaje.setText("Tú numero es menor. Vuelve a intentarlo");
					} else {
						lblMensaje.setText("Tú numero es mayor. Vuelve a intentarlo");
					}
					lblIntentos.setText(""+numIntentos);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(contentPane, 
							"Debes introducir un número válido");
				} catch (NumeroFueraDeRangoException e1) {
					JOptionPane.showMessageDialog(contentPane, 
							"Debes introducir un número entre 1 y "+NUMMAX);
				}
				
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		btnNuevo = new JButton("Nuevo Juego");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNuevo.setEnabled(false);
				btnAceptar.setEnabled(true);
				txtNumero.setText("");
				iniciarJuego();
				lblIntentos.setText(""+numIntentos);
			}
		});
		btnNuevo.setEnabled(false);
		btnNuevo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(btnNuevo, "cell 0 0");
	}

	/**
	 * Función que compara si se ha acertado el numero
	 * @param numero número propuesto en formato String
	 * @return 0 si ha acertado, un número menor que 0 si el numero prouesto es menor
	 * y un número mayor que cero si el numpero propuesto es mayor
	 * @throws NumeroFueraDeRangoException si sale del rango especificado
	 *         NumberFormatException si no ha introducido un número válido
	 */
	protected int comprueba(String numero) throws NumeroFueraDeRangoException {
		int num = Integer.parseInt(numero);
		if (num <0 || num>NUMMAX ) {
			throw new NumeroFueraDeRangoException();
		}
		this.numIntentos++;
		return Integer.compare(num, numAAcertar);
	}

	private void iniciarJuego() {
		this.numIntentos=0;
		this.numAAcertar=aleatorio.nextInt(NUMMAX)+1;
	}

}

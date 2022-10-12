import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ShowBody {

	private JFrame frame;
	private JLabel label;
	private JButton button;
	private JButton button_1;
	private JTextField textField;
	private JLabel label_1;
	private JLabel label_2;
	String[] letrasAdivinhadas; 
	private JogoDaForca jogo;
	private String Chute;
	ArrayList<Integer> posicoes;
	String[] penalidades = {"perna1", "perna2", "bra�o1", "bra�o2", "tronco", "cabe�a"};
	private String[] imagens = {"0.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png"};
	private JLabel label_3;
	private JLabel label_4;
	private JButton button_2;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowBody window = new ShowBody();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShowBody() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);



		button = new JButton("Iniciar");
		button.setBounds(51, 215, 85, 21);
		frame.getContentPane().add(button);
		
		label = new JLabel("Dica: ");
		label.setBounds(82, 28, 174, 20);
		frame.getContentPane().add(label);

		button_1 = new JButton("Chutar");
		frame.getRootPane().setDefaultButton(button_1);

		
		button_1.setBounds(177, 110, 79, 21);
		frame.getContentPane().add(button_1);

		textField = new JTextField();
		textField.setBounds(79, 111, 57, 19);
		frame.getContentPane().add(textField);



		label_1 = new JLabel("Imagem");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(309, 28, 103, 102);
		frame.getContentPane().add(label_1);

		label_2 = new JLabel("Chances");
		label_2.setBounds(51, 172, 103, 32);
		frame.getContentPane().add(label_2);
		
		label_3 = new JLabel("Palavra:");
		label_3.setBounds(51, 72, 150, 13);
		frame.getContentPane().add(label_3);
		
		label_4 = new JLabel("Alerta: ");
		label_4.setBounds(164, 182, 230, 13);
		frame.getContentPane().add(label_4);
		
		button_2 = new JButton("sair");
		button_2.setBounds(309, 215, 85, 21);
		frame.getContentPane().add(button_2);
		
		JLabel label_5 = new JLabel("Nome Imagem");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(309, 158, 103, 14);
		frame.getContentPane().add(label_5);

		
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				}});
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					jogo = new JogoDaForca("palavras.csv");
					jogo.iniciar();
					System.out.println("dica da palavra=" + jogo.getDica());
					System.out.println("tamanho da palavra=" + jogo.getTamanho());
					letrasAdivinhadas = new String[jogo.getTamanho()];	
					Arrays.fill(letrasAdivinhadas, "_");
					
					ImageIcon icon = new ImageIcon(ShowBody.class.getResource("/imagens/" + imagens[0]));
					icon.setImage(icon.getImage().getScaledInstance(label_1.getWidth(), label_1.getHeight(), 1));
					label_1.setIcon(icon);
					label_5.setText(imagens[0]);


					label.setText("Dica: " + jogo.getDica());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}});

		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Chute = textField.getText();
				textField.setText("");
				label_4.setText("Alerta:");
				try {
					posicoes = jogo.getPosicoes(Chute);
					if (posicoes.size()>0) {
						System.out.println("posicoes encontradas="+ posicoes);
						for(int i : posicoes)
							letrasAdivinhadas[i] = Chute;
						System.out.println(Arrays.toString(letrasAdivinhadas));
						System.out.println("total de acertos="+jogo.getAcertos());
						label_3.setText(Arrays.toString(letrasAdivinhadas));
						}
						
					 
					else {
						System.out.println("voce errou - penalidade="+jogo.getPenalidade()+", retirar "+ penalidades[jogo.getPenalidade()-1]);
					}
					
					int index = jogo.getPenalidade();
					ImageIcon icon = new ImageIcon(ShowBody.class.getResource("/imagens/" + imagens[index]));
					icon.setImage(icon.getImage().getScaledInstance(label_1.getWidth(), label_1.getHeight(), 1));
					label_1.setIcon(icon);
					label_5.setText(imagens[index]);
					label_2.setText("Chances: "+(6 - jogo.getPenalidade()));
					
					if(jogo.terminou() == true ) {
						 
						Object mensagem = "\n           ---- game over ----" + 
			                      "\nresultado final = " + jogo.getResultado() + 
			                      "\nsituacao final = " + Arrays.toString(letrasAdivinhadas);;
						JOptionPane.showMessageDialog(null, mensagem);
					}
					

				}
				catch(Exception e1) {
					label_4.setText("Alerta: " + e1.getMessage());			
				}
			}
		});
//		button_3 = new JButton("Reset");
//		button_3.setBounds(171, 215, 85, 21);
//		frame.getContentPane().add(button_3);
//		button_3.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				frame.removeAll();
//				}});
		
		
	}
}
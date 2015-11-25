

 

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.nio.file.Paths;

public class SIGGEN_BP extends JFrame {
	public String id = "800411273";
	public String login = "bprieto@nmsu.edu";

	private enum WaveType {
		SINE, SQUARE, SAWTOOTH, TRIANGLE
	}

	private String wave_type = "unset";

	private JPanel mainPanel;
	private JLabel logoLabel;

	private JPanel inputsPanel;
	private JTextField freqTxField;
	private JTextField durTxField;
	private JTextField amplTxField;
	private JLabel freqLabel;
	private JLabel durLabel;
	private JLabel amplLabel;

	private JPanel buttonsPanel;
	private JButton sinButton;
	private JButton squareButton;
	private JButton sawButton;
	private JButton triangleButton;
	private JButton generateButton;

	private JTextArea outputTxAr;

	static final String first_chunk = "Frequency is ";
	static final String second_chunk = " hz. Amplitude is ";
	static final String third_chunk = ". Go for ";
	static final String fourth_chunk = " seconds.  Type of waveform is ";
	static final String fifth_chunk = ".\n";

	public SIGGEN_BP() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		Font a1 = new Font("Comic Sans MS", Font.BOLD, 14);
		Font a2 = new Font("Lucida Bright", Font.BOLD, 12);

		logoLabel = new JLabel(new ImageIcon("logo1.png"));
		inputsPanel = new JPanel();

		freqTxField = new JTextField("220");
		durTxField = new JTextField("6");
		amplTxField = new JTextField("50");
		freqLabel = new JLabel("Frequency");
		freqLabel.setFont(a2);
		durLabel = new JLabel("Duration");
		durLabel.setFont(a2);
		amplLabel = new JLabel("Amplitude");
		amplLabel.setFont(a2);
		buttonsPanel = new JPanel();
		sinButton = new JButton("Sine");
		sinButton.setBackground(Color.green);
		squareButton = new JButton("Square");
		squareButton.setBackground(Color.yellow);
		sawButton = new JButton("Sawtooth");
		sawButton.setBackground(Color.orange);
		triangleButton = new JButton("Triangle");
		triangleButton.setBackground(Color.pink);
		generateButton = new JButton("Generate!");
		generateButton.setBackground(Color.magenta);
		JScrollPane scrollPaneTxAr = new JScrollPane();
		outputTxAr = new JTextArea();
		outputTxAr.setFont(a1);

		scrollPaneTxAr.setViewportView(outputTxAr);

		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.add(sinButton);
		buttonsPanel.add(squareButton);
		buttonsPanel.add(sawButton);
		buttonsPanel.add(triangleButton);

		JPanel freqPanel = new JPanel(new BorderLayout());
		freqPanel.add(freqLabel, BorderLayout.WEST);
		freqPanel.add(freqTxField, BorderLayout.EAST);
		JPanel durPanel = new JPanel(new BorderLayout());
		durPanel.add(durLabel, BorderLayout.WEST);
		durPanel.add(durTxField, BorderLayout.EAST);
		JPanel amplPanel = new JPanel(new BorderLayout());
		amplPanel.add(amplLabel, BorderLayout.WEST);
		amplPanel.add(amplTxField, BorderLayout.EAST);

		inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
		inputsPanel.add(freqPanel);
		inputsPanel.add(durPanel);
		inputsPanel.add(amplPanel);
		inputsPanel.setBackground(Color.cyan);
		mainPanel.setBackground(Color.cyan);
		buttonsPanel.setBackground(Color.cyan);
		freqPanel.setBackground(Color.cyan);
		durPanel.setBackground(Color.cyan);
		amplPanel.setBackground(Color.cyan);

		Dimension txFieldDim = new Dimension(60, 20);
		freqTxField.setPreferredSize(txFieldDim);
		durTxField.setPreferredSize(txFieldDim);
		amplTxField.setPreferredSize(txFieldDim);

		JPanel centralPanel = new JPanel(new BorderLayout());
		centralPanel.add(buttonsPanel, BorderLayout.WEST);
		centralPanel.add(inputsPanel, BorderLayout.EAST);
		centralPanel.add(generateButton, BorderLayout.SOUTH);
		centralPanel.setBackground(Color.cyan);

		BorderLayout mainPanelLayout = new BorderLayout();
		mainPanel.setLayout(mainPanelLayout);
		mainPanel.add(logoLabel, BorderLayout.NORTH);
		mainPanel.add(centralPanel, BorderLayout.CENTER);
		mainPanel.add(scrollPaneTxAr, BorderLayout.SOUTH);
		scrollPaneTxAr.setPreferredSize(new Dimension(500, 200));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		pack();

		// private final String oscillator =
		// Paths.get(System.getProperty("user.dir"),
		// "oscillator.java").toString();
		// oscillator o = new oscillator(1000.0);

		sinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selectWave(WaveType.SINE);
			}
		});
		squareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectWave(WaveType.SQUARE);
			}
		});
		sawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectWave(WaveType.SAWTOOTH);
			}
		});
		triangleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectWave(WaveType.TRIANGLE);
			}
		});
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generate();
			}
		});

		selectWave(WaveType.SINE);

		setVisible(true);
		setTitle("Signal Generator");

	}

	private void selectWave(WaveType wave) {

		if (freqTxField.getText().equals("") || durTxField.getText().equals("")
				|| amplTxField.getText().equals("")) {
			JOptionPane
					.showMessageDialog(this, "Please fill the input fields.");
			return;
		}

		switch (wave) {
		case SINE:
			wave_type = "Sine";
			break;
		case SQUARE:
			wave_type = "Square";
			break;
		case SAWTOOTH:
			wave_type = "Saw";
			break;
		case TRIANGLE:
			wave_type = "Triangle";
			break;
		}

		outputTxAr.append(wave_type + '\n');
	}

	private void generate() {
		double freq, dur, ampl;
		try {
			freq = Double.parseDouble(freqTxField.getText());
			dur = Double.parseDouble(durTxField.getText());
			ampl = Double.parseDouble(amplTxField.getText());
		} catch (Exception e) {

			System.out.println("user input is invalid");
			JOptionPane.showMessageDialog(this, "Please only write numbers!");
			return;
		}

		if (freq < 10)
			freq = 10;
		else if (freq > 40000)
			freq = 40000;

		if (ampl < 10)
			ampl = 10;
		else if (ampl > 100)
			ampl = 100;

		String output = first_chunk + freq + second_chunk + ampl + third_chunk
				+ dur + fourth_chunk + wave_type + fifth_chunk;

		outputTxAr.append(output);

		oscillate os = new oscillate();
		try {
			os.generate((int) freq, (int) dur, (int) ampl,wave_type);
		} catch (Exception e) {

		}
	}

	public String ME = "Blancaestela Pedraza";
	public String theDate = "10/18/2015";

	public static void main(String[] args) {
		SIGGEN_BP c = new SIGGEN_BP();
	}
}

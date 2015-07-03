package hr.fer.thesis.gui;

import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.GeneticAlgorithm;
import hr.fer.thesis.ga.crossover.ICrossover;
import hr.fer.thesis.ga.crossover.NPointCrossover;
import hr.fer.thesis.ga.crossover.UniformCrossover;
import hr.fer.thesis.ga.evaluation.EvaluationFunction;
import hr.fer.thesis.ga.initialpopulation.GreedyInitialPopulation;
import hr.fer.thesis.ga.initialpopulation.IInitialPopulation;
import hr.fer.thesis.ga.initialpopulation.RandomInitialPopulation;
import hr.fer.thesis.ga.mutation.FlipMutation;
import hr.fer.thesis.ga.replacement.GenerationalReplacement;
import hr.fer.thesis.ga.replacement.IReplacement;
import hr.fer.thesis.ga.replacement.SteadyStateReplacement;
import hr.fer.thesis.ga.selection.ISelection;
import hr.fer.thesis.ga.selection.RouletteWheel;
import hr.fer.thesis.ga.selection.Tournament;
import hr.fer.thesis.ga.stoppingcriteria.AdaptiveStoppingCriteria;
import hr.fer.thesis.ga.stoppingcriteria.IStoppingCriteria;
import hr.fer.thesis.ga.stoppingcriteria.StaticStoppingCriteria;
import hr.fer.thesis.kp.KnapsackProblem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class ParameterChooser extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Hashtable<Integer, JLabel> labelTable;

	static {
		labelTable = new Hashtable<>();
		labelTable.put(0, new JLabel("0.0"));
		labelTable.put(10, new JLabel("0.1"));
		labelTable.put(20, new JLabel("0.2"));
		labelTable.put(30, new JLabel("0.3"));
		labelTable.put(40, new JLabel("0.4"));
		labelTable.put(50, new JLabel("0.5"));
		labelTable.put(60, new JLabel("0.6"));
		labelTable.put(70, new JLabel("0.7"));
		labelTable.put(80, new JLabel("0.8"));
		labelTable.put(90, new JLabel("0.9"));
		labelTable.put(100, new JLabel("1.0"));
	}

	private JLabel destLabel;
	private JLabel fileLabel;
	private JLabel populationSizeLabel;
	private JLabel initialPopulationLabel;
	private JLabel selectionLabel;
	private JLabel crossoverLabel;
	private JLabel pcLabel;
	private JLabel pmLabel;
	private JLabel replacementLabel;
	private JLabel elitismLabel;
	private JLabel stoppingCriteriaLabel;
	private JLabel numberOfIterationsLabel;

	private static String destString = "Destination path for results:";
	private static String fileString = "Problem instance:";
	private static String populationSizeString = "Population size:";
	private static String initialPopulationString = "Initial population:";
	private static String selectionString = "Selection:";
	private static String crossoverString = "Crossover:";
	private static String pcString = "Crossover probability:";
	private static String pmString = "Mutation probability:";
	private static String replacementString = "Replacement:";
	private static String elitismString = "Elitism:";
	private static String stoppingCriteriaString = "Stopping criteria:";
	private static String numberOfIterationsString = "Number of iterations:";

	private JTextField dest;
	private JComboBox<String> fileChooser;
	private JTextField populationSize;
	private JComboBox<String> initialPopulation;
	private JComboBox<String> selection;
	private JComboBox<String> crossover;
	private JSlider pc;
	private JSlider pm;
	private JComboBox<String> replacement;
	private JPanel elitism;
	private JComboBox<String> stoppingCriteria;
	private JTextField numberOfIterations;

	private JButton runButton;

	private NumberFormat numberFormat;

	private int n;

	private GeneticAlgorithm ga;
	private String destination;

	public ParameterChooser() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Genetic algorithm to solve knapsack problem");
		setLocation(300, 150);

		numberFormat = NumberFormat.getIntegerInstance();

		initGUI();
		pack();
	}

	private void initGUI() {

		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

		GridLayout gridLayout = new GridLayout(12, 2);
		JPanel parameters = new JPanel(gridLayout);
		parameters.setBorder(padding);

		destLabel = new JLabel(destString);
		dest = new JTextField();
		parameters.add(destLabel);
		parameters.add(dest);

		fileLabel = new JLabel(fileString);
		fileChooser = new JComboBox<>(new String[] { "p01", "p02", "p03",
				"p04", "p05", "p06", "p07", "p08", "p09" });
		fileChooser.setSelectedItem(null);
		parameters.add(fileLabel);
		parameters.add(fileChooser);

		populationSizeLabel = new JLabel(populationSizeString);
		populationSize = new JFormattedTextField(numberFormat);
		parameters.add(populationSizeLabel);
		parameters.add(populationSize);

		initialPopulationLabel = new JLabel(initialPopulationString);
		initialPopulation = new JComboBox<>(new String[] { "Random", "Greedy" });
		initialPopulation.setSelectedItem(null);
		parameters.add(initialPopulationLabel);
		parameters.add(initialPopulation);

		selectionLabel = new JLabel(selectionString);
		selection = new JComboBox<>(new String[] { "Roulette wheel",
				"Tournament" });
		selection.setSelectedItem(null);
		parameters.add(selectionLabel);
		parameters.add(selection);

		crossoverLabel = new JLabel(crossoverString);
		crossover = new JComboBox<>(new String[] { "N-point", "Uniform" });
		crossover.setSelectedItem(null);
		crossover.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> box = (JComboBox<String>) e.getSource();
				String crossover = (String) box.getSelectedItem();
				if (crossover == "N-point") {
					String input = JOptionPane.showInputDialog(
							ParameterChooser.this,
							"Please specify number of crossover points.");
					try {
						if (input != null) {
							if (!input.isEmpty()) {

								n = Integer.parseInt(input);
							}
						}
					} catch (NumberFormatException ex) {
						showErrorMessage("Can not parse to integer: " + input);
					}

				}
			}
		});
		parameters.add(crossoverLabel);
		parameters.add(crossover);

		pcLabel = new JLabel(pcString);
		pc = new JSlider(0, 100);
		pc.setMajorTickSpacing(1);
		pc.setMinorTickSpacing(0);
		pc.setPaintTicks(true);
		pc.setPaintTrack(true);
		pc.setLabelTable(labelTable);
		pc.setPaintLabels(true);
		parameters.add(pcLabel);
		parameters.add(pc);

		pmLabel = new JLabel(pmString);
		pm = new JSlider(0, 100);
		pm.setMajorTickSpacing(1);
		pm.setMinorTickSpacing(0);
		pm.setPaintTicks(true);
		pm.setLabelTable(labelTable);
		pm.setPaintLabels(true);
		parameters.add(pmLabel);
		parameters.add(pm);

		replacementLabel = new JLabel(replacementString);
		replacement = new JComboBox<>(new String[] { "Generational",
				"Steady-state" });
		replacement.setSelectedItem(null);
		parameters.add(replacementLabel);
		parameters.add(replacement);

		elitismLabel = new JLabel(elitismString);
		elitism = new JPanel();
		elitism.setLayout(new GridLayout(0, 2));
		JRadioButton yes = new JRadioButton("Yes");
		JRadioButton no = new JRadioButton("No");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(yes);
		buttonGroup.add(no);
		elitism.add(yes);
		elitism.add(no);
		parameters.add(elitismLabel);
		parameters.add(elitism);

		stoppingCriteriaLabel = new JLabel(stoppingCriteriaString);
		stoppingCriteria = new JComboBox<>(
				new String[] { "Static", "Adaptive" });
		stoppingCriteria.setSelectedItem(null);
		parameters.add(stoppingCriteriaLabel);
		parameters.add(stoppingCriteria);

		numberOfIterationsLabel = new JLabel(numberOfIterationsString);
		numberOfIterations = new JFormattedTextField(numberFormat);
		parameters.add(numberOfIterationsLabel);
		parameters.add(numberOfIterations);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(parameters), BorderLayout.CENTER);

		runButton = new JButton();
		runButton.setAction(run);
		runButton.setText("Run");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(runButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private Action run = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			String pathName = dest.getText().trim();
			if (pathName.isEmpty()) {
				showErrorMessage("Invalid destination path.");
				return;
			}
			Path dir = Paths.get(pathName);
			if (!Files.exists(dir)) {
				Path parent = dir.getParent();
				if (parent != null) {
					if (!Files.isDirectory(dir.getParent())) {
						showErrorMessage("Invalid destination path.");
						return;
					}
				} else {
					showErrorMessage("Invalid destination path.");
					return;
				}
			}

			destination = pathName;

			try {
				
				String file = getFile();
				GAData.file = file;
				
				int size = getPopulationSize();
				GAData.populationSize = size;
				
				IInitialPopulation initial = getInitialPopulation();
				
				ISelection selection = getSelection();
				
				ICrossover crossover = getCrossover();
				GAData.N = n;
				double pc = getPc();
				GAData.pc = pc;

				double pm = getPm();
				GAData.pm = pm;

				IReplacement replacement = getReplacement();
				
				boolean elite = getElite();
				GAData.elitism = elite;

				int it = getIterations();
				GAData.iterations = it;
				IStoppingCriteria stop = getStop();

				ga = new GeneticAlgorithm(new EvaluationFunction(), initial,
						selection, crossover, new FlipMutation(), replacement,
						stop);

			} catch (InputException ex) {
				showErrorMessage(ex.getMessage());
				return;
			}

			KnapsackProblem.solve(ga, destination);

		}

	};

	protected String getFile() {
		String file = null;
		file = (String) this.fileChooser.getSelectedItem();
		return "data/" + file + ".txt";
	}

	protected int getPopulationSize() throws InputException {
		int size;
		try {
			size = Integer.parseInt(populationSize.getText());
		} catch (NumberFormatException ex) {
			throw new InputException("Population size must be integer value.");
		}
		return size;
	}

	protected IInitialPopulation getInitialPopulation() throws InputException {
		IInitialPopulation initial = null;
		Object c = this.initialPopulation.getSelectedItem();
		if (c == null) {
			throw new InputException("Please select a strategy for initial population generation.");
		}
		switch ((String) this.initialPopulation.getSelectedItem()) {
		case "Random":
			initial = new RandomInitialPopulation();
			break;
		case "Greedy":
			initial = new GreedyInitialPopulation();
			break;

		}
		return initial;
	}

	protected ISelection getSelection() throws InputException {
		ISelection selection = null;
		Object c = this.selection.getSelectedItem();
		if (c == null) {
			throw new InputException("Please select selection strategy.");
		}
		switch ((String) c) {
		case "Roulette wheel":
			selection = new RouletteWheel();
			break;
		case "Tournament":
			selection = new Tournament();
			break;

		}
		return selection;
	}

	protected ICrossover getCrossover() throws InputException {
		ICrossover crossover = null;
		Object c = this.crossover.getSelectedItem();
		if (c == null) {
			throw new InputException("Please select crossover strategy.");
		}
		switch ((String) c) {
		case "N-point":
			crossover = new NPointCrossover();
			break;
		case "Uniform":
			crossover = new UniformCrossover();
			break;

		}
		return crossover;
	}

	protected double getPc() {
		return pc.getValue() / 100.;
	}

	protected double getPm() {
		return pm.getValue() / 100.;
	}

	protected IReplacement getReplacement() throws InputException {
		IReplacement replacement = null;
		Object c = this.replacement.getSelectedItem();
		if (c == null) {
			throw new InputException("Please select replacement strategy.");
		}
		switch ((String) c) {
		case "Generational":
			replacement = new GenerationalReplacement();
			break;
		case "Steady-state":
			replacement = new SteadyStateReplacement();
			break;

		}
		return replacement;
	}

	protected boolean getElite() {
		Component[] buttons = elitism.getComponents();
		JRadioButton selected = null;
		for (int i = 0; i < buttons.length; i++) {
			if (((JRadioButton) (buttons[i])).isSelected()) {
				selected = (JRadioButton) (buttons[i]);
			}
		}
		if (selected == null) {
			return false;
		}
		return (selected.getName() == "Yes") ? true : false;
	}

	protected IStoppingCriteria getStop() throws InputException {
		IStoppingCriteria stop = null;
		Object c = this.stoppingCriteria.getSelectedItem();
		if (c == null) {
			throw new InputException("Please select stopping criteria.");
		}
		switch ((String) c) {
		case "Static":
			stop = new StaticStoppingCriteria();
			break;
		case "Adaptive":
			stop = new AdaptiveStoppingCriteria();
			break;
		}
		return stop;
	}

	protected int getIterations() throws InputException {
		int it;
		try {
			it = Integer.parseInt(numberOfIterations.getText());
		} catch (NumberFormatException ex) {
			throw new InputException(
					"Number of iterations must be integer value.");
		}
		return it;
	}

}

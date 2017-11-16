package edu.uwec.cs.kieserjw.project2;

import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;


public class PointsView extends JFrame implements ActionListener {
	public static PointsHolder points = new PointsHolder();
	public static PointsHolder hullPoints = new PointsHolder();
	private static final long serialVersionUID = 1L;
	static boolean quickHullSet = true;
	private boolean numTyped = false;
	private JTextField numField = null;

	public PointsView(){
		this.setLayout(new BorderLayout());
		JPanel right = new BlankArea();
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS ));

		JButton genPts = new JButton("Generate Pts");
		genPts.addActionListener(this);
		JButton genHull = new JButton("Generate Hull");
		genHull.addActionListener(this);
		numField = new JTextField();
		numField.addActionListener(this);
		JLabel numPts = new JLabel("# of points");

		String quickString = "Quick Hull";
		String mergeString = "Merge Hull";
		JRadioButton quickH = new JRadioButton(quickString);
		JRadioButton mergeH = new JRadioButton(mergeString);
		quickH.setForeground(Color.RED);
		mergeH.setForeground(Color.BLUE);
		quickH.setSelected(true);
		quickH.addActionListener(this);
		mergeH.addActionListener(this);

		ButtonGroup group = new ButtonGroup();
		group.add(quickH);
		group.add(mergeH);

		left.add(Box.createRigidArea(new Dimension(0, 20)));
		left.add(numPts);
		left.add(Box.createRigidArea(new Dimension(0, 20)));
		left.add(numField);
		left.add(Box.createRigidArea(new Dimension(0, 10)));
		left.add(genPts);
		left.add(Box.createRigidArea(new Dimension(0, 30)));
		left.add(quickH);
		left.add(mergeH);
		left.add(Box.createRigidArea(new Dimension(0, 20)));
		left.add(genHull);

		this.add(left,BorderLayout.WEST);
		this.add(right,BorderLayout.CENTER);
		this.setTitle("Convex Hull Finder");
		this.setResizable(true);
		this.setSize(600,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent arg0) {

		int numberParse = 0;
		if (!numField.getText().isEmpty()){
			try {
				numberParse = Integer.parseInt(numField.getText());
				numTyped = true;
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter a number");
			}
		}else{
			numTyped = false;
		}

		if (arg0.getActionCommand().equals("Generate Pts")){
			if (!numTyped){
				points.reGen();
			}else{
				points.reGen(numberParse);
			}
			BlankArea.hullComputed = false;
			repaint();
		}

		if (arg0.getActionCommand().equals("Generate Hull")){
			if (!points.getPoints().isEmpty()){
				if (quickHullSet){
					QuickHull qh = new QuickHull((ArrayList<Point2D>) points.getPoints().clone());
					hullPoints.setPoints(qh.getPointsList());
				}else{
					MergeHull mh = new MergeHull((ArrayList<Point2D>) points.getPoints().clone());
					hullPoints.setPoints(mh.getPointsList());
				}
				BlankArea.hullComputed = true;
				repaint();
			}
		}

		if (arg0.getActionCommand().equals("Quick Hull")){
			quickHullSet =true;
		}
		if (arg0.getActionCommand().equals("Merge Hull")){
			quickHullSet = false;
		}

	}



}

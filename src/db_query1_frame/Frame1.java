package db_query1_frame;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Fenster zur Ausgabe der Umsaetze pro agent
 * 
 * @author ben
 *
 */
public class Frame1 extends JFrame {

	private static final long serialVersionUID = 1L;
	Container c;
	JLabel label1[] = new JLabel[8]; // Ueberschrift, leer, 6 agents

	public Frame1() {

		super("ResultSet"); // Anfangs-Fenster-Titel

		c = getContentPane();
		c.setLayout(new GridLayout(8, 1, 0, 0)); // Layout fuer Ueberschrift + leere Zeile + 6 labels + (agents) untereinander

		for (int i = 0; i < 8; i++) {

			if (i == 1)
				label1[i] = new JLabel("-------------------");
			else
				label1[i] = new JLabel("a0" + (i - 1) + "    $amount");

			label1[i].setHorizontalTextPosition(JLabel.CENTER);
			label1[i].setFont(new Font("Monospaced", Font.ITALIC, 14));
			c.add(label1[i]);
			label1[0].setFont(new Font("Monospaced", Font.BOLD, 14)); // formatiere Ueberschrift
			label1[0].setText("pid:");
		}

		setSize(250, 200);
		setVisible(true);
		setAlwaysOnTop(true);
		setLocation(900, 200);
		;
	}
}
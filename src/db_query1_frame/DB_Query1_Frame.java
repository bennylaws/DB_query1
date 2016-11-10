package db_query1_frame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DB_Query1_Frame {
	
	public static void main(String[] args) {

		ResultSet rs = null;
		String url_part1, url_part2, url_complete, db, usr, pw, pid = "", query_complete = "";
		String query[] = new String[5];
		Connection con;

		// String Array fuer schoener formatierbare Ausgabe
		query[0] = "SELECT aid, SUM(dollars) AS summe, pname ";
		query[1] = "FROM orders ";
		query[2] = "JOIN products ON orders.pid = products.pid ";
		query[3] = "WHERE orders.pid = '" + pid + "'";
		query[4] = "GROUP BY aid ORDER BY summe DESC;";

		url_part1 = "jdbc:mysql://";

		// versuche den MYSQL-Treiber zu laden / einzubinden
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.err.println("JDBC driver error!");
			System.err.println(e);
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("\nmysql.jdbc.Driver loaded.\n");

		// Benutzereingaben (address, name, pw, db)
		url_part2 = JOptionPane.showInputDialog("url:", "java-co.de");
		if (url_part2 == null) { System.out.println("early exit."); System.exit(-1);}

		usr = JOptionPane.showInputDialog("user:", "dbi");
		if (usr == null) { System.out.println("early exit."); System.exit(-1);}
		
		pw = JOptionPane.showInputDialog("password:", "dbi_pass");
		if (pw == null) { System.out.println("early exit."); System.exit(-1);}
		
		db = JOptionPane.showInputDialog("DB:", "cap");
		if (db == null) { System.out.println("early exit."); System.exit(-1);}

		url_complete = url_part1 + url_part2;
		
		// try to connect
		try {
			System.out.print("connecting to " + url_part2 + "......");
			
			con = DriverManager.getConnection(url_complete + "/" + db, usr, pw);
			
			Statement stmt = con.createStatement();
			
			System.out.print("connection established.\n");
			System.out.println("active db: " + db + "\n");
			
			// Ausgabe-Frame erstellen:
			Frame1 fenster = new Frame1();
			
			// erste Abfrage, hier koennte bereits beendet werden, daher (das zweite) if (..)
			pid = JOptionPane.showInputDialog("enter pid (p01-p07)\n(\"q\", \"bye\" or cancel to quit):");
			if (pid == null || pid.equals("bye") || pid.equals("q") || pid.equals("Q")) {
				System.out.println("early exit.");
				System.exit(-1);
			}

			if (!(pid.equals("bye"))) {
				
				// Abfrage-Schleife fuer pid
				do {
					// setze neue pid
					query[3] = "WHERE orders.pid = '" + pid + "'";
					
					// loesche QUERY-String
					query_complete = "";
					
					// erstelle neuen QUERY-String
					for (int i = 0; i <5; i++)
						query_complete += query[i];
					
					// Kontroll-Ausgabe der QUERY in der Console
					System.out.println("QUERY:\n");
					for (int i = 0; i < 5; i++)
						System.out.println(query[i]);	// Nur der String mit der pid muss geaendert werden
					
					// fuehre QUERY aus und befuelle ResultSet
					rs = stmt.executeQuery(query_complete);
					
					// JLabel-Array leeren -> Ausgabe-Fenster
					for (int i = 2; i < 8; i++)
						fenster.label1[i].setText("");
					
					// Gewuenschte Daten aus ResultSet in JLabel-Array schreiben (Ausgabe-Fenster)
					int i = 2;
					while (rs.next()) {
						
						// aendere Ueberschrift -> pid, pname
						fenster.label1[0].setText("pid: " + pid + " (" + rs.getString("pname") + ")");
						
						// Fuelle Fenster-Label mit Daten
						fenster.label1[i++].setText(rs.getString("aid") +
								":    $" + rs.getString("summe"));
					}
					System.out.println();
					
					// Abfrage neuer pid -> neuer Durchlauf oder Ende...
					pid = JOptionPane.showInputDialog("enter pid (p01-p07)\n(\"q\", \"bye\" or cancel to quit):");
					if (pid == null || pid.equals("bye") || pid.equals("q") || pid.equals("Q")) {
						System.out.println("\nexit on user request\n");
						System.exit(-1);
					}
					
				} while (!(pid.equals("bye")));
			}
			System.out.println("\nexit on user request\n");

			// Verbindung terminieren; if-Block falls als 1. Eingabe "bye" erfolgte -> rs ist dann == null
			if (rs != null) rs.close();
			stmt.close();
			fenster.dispose();

		} catch (SQLException e) {
			System.err.println("connection error!");
			e.printStackTrace();
			System.err.println("SQLExcecption: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
			System.exit(-1);
		}
	}
}

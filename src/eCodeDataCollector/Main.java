package eCodeDataCollector;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		try {
			Grabber grabber = new Grabber();
			ArrayList<ECodeData> data = grabber.grab();
			DatabaseHelper databaseHelper = new DatabaseHelper("cCode.db");
			databaseHelper.createNewDatabase();
			databaseHelper.createECodeInfoTable();
			databaseHelper.insertAll(data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

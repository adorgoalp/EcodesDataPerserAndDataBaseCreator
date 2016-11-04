package eCodeDataCollector;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		try {
			Grabber grabber = new Grabber();
			ArrayList<ECodeData> data = grabber.grab();
			ArrayList<ECodeData> moreData = grabber.grabMoreData();
			DatabaseHelper databaseHelper = new DatabaseHelper("eCode.db");
			databaseHelper.createNewDatabase();
			databaseHelper.createECodeInfoTable();
			moreData.addAll(data);
			databaseHelper.insertAll(moreData);
		} catch (IOException e) {
			e.printStackTrace();
		
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

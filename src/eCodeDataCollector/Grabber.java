package eCodeDataCollector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Grabber {
	ArrayList<ECodeData> allECodeData = new ArrayList<ECodeData>();

	public ArrayList<ECodeData> grab() throws IOException {
		File data = new File("data.html");
		Document document = Jsoup.parse(data, "UTF-8");
		Element table = document.getElementById("desiredDataTable");
		Elements tableRow = table.select("tr");
		Elements tableData = null;
		ECodeData eCodeData;
		String code;

		for (int i = 0; i < tableRow.size(); i++) {
			tableData = tableRow.get(i).select("td");
			code = tableData.get(0).text();
			if(!code.startsWith("E"))
			{
				code = "E" + code;
			}
			eCodeData = new ECodeData(code, tableData.get(1).text(), tableData.get(2).text(),
					tableData.get(3).text());
			allECodeData.add(eCodeData);
		}
		return allECodeData;
	}

	public ArrayList<ECodeData> grabMoreData() throws FileNotFoundException {
		ArrayList<ECodeData> data = new ArrayList<ECodeData>();
		Scanner sc = new Scanner(new FileInputStream(new File("moreData.txt")));
		String line = "";
		while (true) {
			try {
				line = sc.nextLine();
				if (!line.equals("")) {
					// System.out.println(line);
					String code = line.substring(0, line.indexOf(" "));
					String name = line.substring(line.indexOf(" ") + 1, line.indexOf("["));
					String description = line.substring(line.indexOf("[") + 1, line.lastIndexOf("]"));

					String halalStatus = line.substring(line.lastIndexOf("]") + 2, line.length());
					if (halalStatus.equals("?"))
						halalStatus = "Unknown";

//					System.out.println(code);
//					System.out.println(name);
//					System.out.println(description);
//					System.out.println(halalStatus);
					data.add(new ECodeData(code, name, description, halalStatus));
				}
			} catch (NoSuchElementException e) {
				break;
			}

		}
		return data;
	}
}

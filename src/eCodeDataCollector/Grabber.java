package eCodeDataCollector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Grabber {
	ArrayList<ECodeData> allECodeData = new ArrayList<ECodeData>();
	public ArrayList<ECodeData> grab() throws IOException
	{
		File data = new File("data.html");
		Document document = Jsoup.parse(data, "UTF-8");
		Element table = document.getElementById("desiredDataTable");
		Elements tableRow = table.select("tr");
		Elements tableData = null;
		ECodeData eCodeData;
		
		for( int i =  2 ; i < tableRow.size() ; i++)
		{
			tableData = tableRow.get(i).select("td");
			eCodeData = new ECodeData(tableData.get(0).text(),
					tableData.get(1).text(),
					tableData.get(2).text(),
					tableData.get(3).text());
			allECodeData.add(eCodeData);
		}
		return allECodeData;
	}
}

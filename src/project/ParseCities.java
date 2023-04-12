package project;


import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseCities {

	protected static boolean checkIfPropertyExists(String country, String city){
		String address = "https://www.citypopulation.de/en/" + country.toLowerCase() + "/cities/";
		String path = "C:/Users/karolsikora/eclipse-workspace/projekt/bin/project/" + country + ".properties";
		File file = new File(path);
		
		if (file.exists()) {
			
			Properties p = new Properties();
			try (FileReader fr = new FileReader(file)){
				p.load(fr);

				for (int i = 0; i<p.size(); i++) {
					if(p.getProperty(Integer.toString(i)).equals(city)) {
						return true;
					}
					
				}
				
				return false;
					
			}catch (FileAlreadyExistsException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
		
		} else {
			Properties p = new Properties();
			String [] cities;
			try {
				FileWriter fw = new FileWriter(path);
				cities = parseCity(address);
				for(int i =0; i<cities.length; i++) {
					p.put(Integer.toString(i), cities[i]);
				}
				p.store(fw,null);
				
				
			} catch(IOException e) {
				System.out.println("Problem ze strumieniem IO");
				e.printStackTrace();
			}
			
			
			try (FileReader fr = new FileReader(file)){
				p.load(fr);
				
				for (int i = 0; i<p.size(); i++) {
					if(p.getProperty(Integer.toString(i)).equals(city)) {
						return true;
					}
					
				}
				
				return false;
			} catch(IOException e) {
				e.printStackTrace();
		}
		
		}
		return false;
	}
	
	
	
	
	protected static String[] parseCity(String address) throws IOException {
		
		Document doc = Jsoup.connect(address).get();
		Elements rows = doc.select("table[id=ts][class=data]").select("td[class=rname]");
		Elements el[] = new Elements[rows.size()];
		String[] cities = new String[rows.size()];
		int i =0;
		for(Element row : rows) {
			el[i] = row.select("span[itemprop=name]");
			cities[i] = el[i].get(0).text();
			
			i++;
			
		}
		return cities;
		
	}
	
	
		
	
	

}

package project;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ParsePrices {
	
	protected static String address(String city, String fuel) {
		String address= "https://www.fuelflash.eu/en/?land=fr&suchfeld=" + city +"&entfernung=15&sorte=" + fuel + "&sortierung=preis";
		return address;
	}
	

	 protected static String[] parsePrices(String address) {
		
		try {
		Document doc = Jsoup.connect(address).get();
		
		
		Elements rows = doc.select("table[class=spritpreis_liste]").select("tr[onmouseover]").select("td[class=nowrap]").select("span[class=preis_part1]");
	
		
		String prices[] = new String[rows.size()];
		String pricesHelper[] = null;
      
        int i = 0;
        for (Element row : rows) {
        	
        	
        	prices[i] = row.text();
        	prices[i] = prices[i].replace(',', '.');
        	if(prices[i].equals("0.00")) {
            	

        		pricesHelper = new String[i+1];
        		for(int j = 0; j<i+1; j++) {
        			pricesHelper[j] = prices[j];
        			
        		}
        		break;

        	}else {
        		pricesHelper = new String[i+1];
        		for(int j = 0; j<i+1; j++) {
        			pricesHelper[j] = prices[j];
        		
        	}
        	}
        	
        	
        	i++;
        	
        }
        
        
       String meanValue = mean(pricesHelper);
       String modeValue = mode(pricesHelper);
       String medianValue = median(pricesHelper);
       String maxValue = max(pricesHelper);
       String minValue = min(pricesHelper);
       String [] values = { meanValue, modeValue, medianValue, maxValue, minValue, Integer.toString(pricesHelper.length)};
        return values;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

        
	}
	 
	 
	 protected static String mean(String[] prices) {
		 double mean;
		 double sum = 0;
		
		 for(String i : prices) {
			

			sum += Double.parseDouble(i);
		 }
		
		 mean = sum/prices.length;
		 Double.toString(mean);
		return String.format("%.2f", mean);
	 }
	 
	 protected static String mode(String[] prices) {
		 double mode = 0;
		 int counter = 0;
		 int max_counter = 0;
		 for (String i : prices) {
			 
			 for (String j : prices) {
				if ( i.equals(j)) {
					counter ++;
				}
				 
			 }
			 if(counter > max_counter) {
				 max_counter = counter;
				 mode = Double.parseDouble(i);
			 }
			 counter = 0;
		 }
		return Double.toString(mode); 
	 }
	 
	 protected static String median(String[] prices) {
		 double median = 0;
		
		 if (prices.length%2 != 0) {
			 median = Double.parseDouble(prices[prices.length/2 +1]);
			
			 return Double.toString(median);
		 }else {
			 median = (Double.parseDouble(prices[prices.length/2]) + Double.parseDouble(prices[prices.length/2 + 1]))/2;
			 Double.toString(median);
			 
			 return String.format("%.2f", median);
		 }
		 
		 
	 }
	 
	 protected static String max(String[] prices) {
		 double maxValue = Double.parseDouble(prices[0]);
		 for (int i =0; i< prices.length; i++) {
			 if(maxValue < Double.parseDouble(prices[i])) {
				 maxValue = Double.parseDouble(prices[i]);
			 }
		 }
		 return Double.toString(maxValue);
		 
		 
	 }
	 
	 protected static String min(String[] prices) {
		 double minValue = Double.parseDouble(prices[0]);
		 for (int i =0; i< prices.length; i++) {
			 if(minValue > Double.parseDouble(prices[i])) {
				 minValue = Double.parseDouble(prices[i]);
			 }
		 }
		 return Double.toString(minValue);
		 
		 
	 }
	 
	 protected static String changeCountry(String city) {
		 String pref;
		 switch(city) {
		 	case "Germany":
		 		return pref = "de";
		 	case "Spain":
		 		return pref = "es";
		 	case "France":
		 		return pref = "fr";
		 	
		 	case "Portugal":
		 		return pref = "pt";
		 	
		 	default:
		 		return null;
		 }
	 }
	 
	 
	 protected static String changeFuel(String fuel) {
		 String pref;
		 switch(fuel) {
		 	case "Diesel":
		 		return pref = "diesel";

		 	case "Premium":
		 		return pref = "super-e5";
		 		
			case "Premium Plus":
		 		return pref = "super-plus";
		 		
		 	case "Autogas":
		 		return pref = "lpg";
		 		
		 	case "Premium Diesel":
		 		return pref = "premium-diesel";
		 }
		 return null;
	 }
	 

	 
	 
	 

}

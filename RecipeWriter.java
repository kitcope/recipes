package recipes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class RecipeWriter {
	/**
	 * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 * "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	 * 
	 * <html> <head> <link rel="stylesheet" type="text/css" href="recipe.css">
	 * <!-- script src='//code.jquery.com/jquery-1.10.2.js' -->
	 * 
	 * <!-- All Working 4/2/2014 -->
	 * 
	 * 
	 * <title>Apple Crumble</title>
	 * 
	 * <script language="javascript" src="recipe.js"></script> </head>
	 * 
	 * <body onLoad=
	 * "unitFunction(0, false), unitFunction(1, false), unitFunction(2, false), timeFunction(), quantityFunction()"
	 * >
	 * 
	 * 
	 * <h2 class="recipeName">Apple Crumble</h2>
	 * 
	 * <div class="recipePrecis">Easy nostalgic seasonal dessert.</div> <br>
	 * <table>
	 * <th class="servesLabel" align="left">Serves</th>
	 * <th colspan="7" align="left">
	 * <select id="servesOption" onchange="unitFunction(0, false)"> <option
	 * value="1">1</option> <option value="2">2</option> <option
	 * value="3">3</option> <option value="4" selected>4</option> <option
	 * value="5">5</option> <option value="6">6</option> <option
	 * value="7">7</option> <option value="8">8</option> <option
	 * value="9">9</option> <option value="10">10</option> </select></th>
	 * <tr>
	 * <td id="prepTimeLabel">Preparation:</td>
	 * <td class=recipeTime id="prepTime">10</td>
	 * <td id="prepTimeUnit">minutes</td>
	 * <td></td>
	 * <td></td>
	 * <td>Start:</td>
	 * <td><div id="startTime"></div></td>
	 * <td><button onclick="timeFunction()">Reset</button></td>
	 * </tr>
	 * <tr>
	 * <td id="cookTimeLabel">Cooking:</td>
	 * <td class=recipeTime id="cookTime">0.2</td>
	 * <td id="cookTimeUnit">hours</td>
	 * <td></td>
	 * <td></td>
	 * <td>End:</td>
	 * <td><div id="endTime"></div></td>
	 * <td></td>
	 * </tr>
	 * </table>
	 * <br>
	 * 
	 * <table class="ingredients">
	 *<th class="ingredients">Ingredients </th><th  colspan="3" align="left"><button id="ingredientUnitButton" onclick="unitFunction(0, true)" >Change units</button>
	 *<button id="highlightUpButton" onclick="highlightFunction(0)" >Highlight up &#9650;</button> 
	 *<button id="highlightDownButton" onclick="highlightFunction(1)" >Highlight down &#9660;</button> </th>
	 * <tr class="ingredient" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="servesQuantity">100</td>
	 * <td class="unit">grams</td>
	 * <td class = "description">plain flour</td>
	 * </tr>
	 * <tr class="ingredient" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="servesQuantity">40</td>
	 * <td class="unit">grams</td>
	 * <td class="description">brown sugar</td>
	 * </tr>
	 * <tr class="ingredient" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="servesQuantity">40</td>
	 * <td class="unit">grams</td>
	 * <td class="description">unsalted butter ( room temperature)</td>
	 * </tr>
	 * <tr class="ingredient" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="servesQuantity">300</td>
	 * <td class="unit">grams</td>
	 * <td class="description">apples</td>
	 * </tr>
	 * <tr class="ingredient" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="servesQuantity">0.5</td>
	 * <td class="unit">tsp</td>
	 * <td class="description">ground cinnamon</td>
	 * </tr>
	 * </table>
	 * <br>
	 * 
	 * 
	 * 
	 * <table class="method">
	 * <th class="method">Method&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
	 * <th align="left"><button id="methodTempButton"
	 * onclick="unitFunction(1, true)">Change temp units</button><button
	 * id="methodMeasurementButton" onclick="unitFunction(2, true)">Change
	 * measurement units</button></th>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class = "description">Pre-heat oven to <span
	 * class="quantity">180</span>&#186;<span class="unit">C</span>.</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Sieve flour into mixing bowl.</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Add sugar and mix well.</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Cut butter into <span
	 * class="quantity">1</span>&nbsp;<span class="unit">cm</span> cubes and rub
	 * into flour mixture to form crumble.</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Peel and core apples and cut into <span
	 * class="quantity">1</span>&nbsp;<span class="unit">cm</span> pieces.</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Sautee apple in butter for 5 minutes (if using
	 * rhubarb too, add for last 2 minutes).</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Sprinkle with cinnamon and stir gently.</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Butter a <span
	 * class="quantity">24</span>&nbsp;<span class="unit">cm</span> diameter
	 * ovenproof dish.</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Spoon the fruit mixture into the dish.</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Sprinkle the crumble mixture on top.</td>
	 * </tr>
	 * <tr class="methodStep" onClick="boldFunction(this)">
	 * <td></td>
	 * <td class="description">Bake for 20 minutes or until crumble brown and
	 * filling bubbling.</td>
	 * </tr>
	 * </table>
	 * <br>
	 * <table>
	 * <tr>
	 * <td class="serviceLabel">Service:</td>
	 * <td class="serviceDescription">Serve with cream or custard.</td>
	 * </tr>
	 * <tr>
	 * <td class="attributionLabel">Attribution:&nbsp;&nbsp;</td>
	 * <td class="attributionDescription"></td>
	 * <td><form action="recipeIndex.html"><button
	 * type="submit">Index</button></form></td>
	 * </tr>
	 * </table>
	 * </body> </html>
	 * 
	 * 
	 * 
	 * 
	 * Name: Apple Crumble
	 * 
	 * Precis: Easy nostalgic seasonal dessert.
	 * 
	 * Serves: 4
	 * 
	 * Preparation time: 10 minutes
	 * 
	 * Cooking time: 20 minutes
	 * 
	 * Ingredients: Ingredients
	 * 
	 * 100 grams plain flour 40 grams brown sugar 40 grams unsalted butter (
	 * room temperature) 300 grams apples 0.5 tsp ground cinnamon
	 * 
	 * 
	 * 
	 * Method: Method
	 * 
	 * Pre-heat oven to 180 C Sieve flour into mixing bowl. Add sugar and mix
	 * well. Cut butter into 1 cm cubes and rub into flour mixture to form
	 * crumble. Peel and core apples and cut into 1 cm pieces. Sautee apple in
	 * butter for 5 minutes (if using rhubarb too, add for last 2 minutes)
	 * Sprinkle with cinnamon and stir gently. Butter a 24 cm diameter ovenproof
	 * dish. Spoon the fruit mixture into the dish. Sprinkle the crumble mixture
	 * on top. Bake for 20 minutes or until crumble brown and filling bubbling.
	 * 
	 * 
	 * 
	 * Service: Serve with cream or custard.
	 * 
	 * Attribution:
	 **/

	static String recipeSourceDirectory = "C:\\Users\\CHRIS\\Documents\\Recipes";
	static String recipeTargetDirectory = recipeSourceDirectory + "\\target";
	static String placeHolder = "**********************Placeholder*************************************";
	static String spaceHolder = "***Spaces***";
	static List <String> placeHolders = null;
	static int maxWidth = 0;
	public RecipeWriter() {
	}

	public static void main(String[] args) {
		File[] files = new File(recipeSourceDirectory).listFiles(new FilenameFilter(){

			@Override
			 public boolean accept(File directory, String fileName) {
		        return fileName.endsWith(".txt")&!fileName.startsWith("RecipeTemplate")&!fileName.startsWith("RecipeIndex");

			}});
		//File file = new File(recipeSourceDirectory + "\\AppleCrumble.txt");
		for(File file : files){
		String fileName=file.getName().replace(".txt", ".html");	
		File outFile = new File(recipeTargetDirectory + "\\" + fileName);
		BufferedReader in = null;
		PrintWriter out = null;
		System.out.println("*****************" + fileName + "******************************");
		placeHolders = new ArrayList<String>();
		maxWidth = 0;
		try {
			in = new BufferedReader(new FileReader(file));
			out = new PrintWriter(outFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		String inputLine;
		String content = "";
		
		StringBuffer output = new StringBuffer();
		try {
			while ((inputLine = in.readLine()) != null) {
				content += inputLine + "\r\n";
			}
			output.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"

					+ "<html>\r\n"
					+ "<head>\r\n"
					+ "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"recipe.css\">\r\n");

		} catch (IOException e) {
			
			e.printStackTrace();
			return;
		}
		String[] lines = content.split("\r\n");
		boolean ingredient = false;
		boolean methodStep = false;
		boolean ingredientStart = false;
		boolean methodStart = false;
		for (String s : lines) {
			
				if (s.trim().length() == 0) {
					continue; // ignore blank lines
				}
				if (s.trim().startsWith("Name:")) {

					String name = s.substring(s.indexOf("Name:") + 5).trim();
					System.out.println(s);
					output.append("\t<title>"
							+ name
							+ "</title>\r\n"
							+ "\t<script language=\"javascript\" src=\"recipe.js\"></script> \r\n"
							+ "</head>\r\n"
							+ "<body onLoad=\"findHighlightableElements(), unitFunction(0, false), unitFunction(1, false), unitFunction(2, false), timeFunction(), quantityFunction()\" >\r\n"
							+ "<h2 class=\"recipeName\">" + name + "</h2>\r\n");
				}
				if (s.trim().startsWith("Precis:")) {
					System.out.println(s);
					String precis = s.substring(s.indexOf("Precis:") + 7)
							.trim();
					output.append("<div class=\"recipePrecis\">"
							+ precis
							+ "</div>\r\n"
							+ "<br>\r\n"
							+ "<table>\r\n"
							+ "\t<th class=\"servesLabel\" align=\"left\">Serves</th>\r\n"
							+ "\t<th colspan=\"7\" align=\"left\">\r\n"
							+ "\t\t<select id=\"servesOption\" onchange=\"unitFunction(0, false)\">\r\n");
				}

				if (s.trim().startsWith("Serves:")) {
					System.out.println(s);
					int serves = Integer.parseInt(s.substring(
							s.indexOf("Serves:") + 7).trim());
					for (int i = 1; i < 11; i++) {
						output.append("\t\t\t<option value=\"" + i + "\" "
								+ (i == serves ? "selected=\"true\">" : ">") + i
								+ "</option>\r\n");
					}

					output.append("\t\t</select>\r\n" + "\t</th>\r\n" + "\t<tr>\r\n");

				}
				if (s.trim().startsWith("Preparation time:")) {
					System.out.println(s);
					String prepString = s.substring(s.indexOf("Preparation time:") + 17).trim();
					String[] prepStrings = prepString.split("\\s+");
					float prepTime = Float.parseFloat(prepStrings[0]);
					output.append("\t\t<td id=\"prepTimeLabel\">Preparation:</td>\r\n"
				+"\t\t<td class=recipeTime id=\"prepTime\">"+ prepTime + "</td>\r\n"
				
				
				+"\t\t<td id=\"prepTimeUnit\">" + prepStrings[1]+ "</td>\r\n"
				+"\t\t<td></td>\r\n"
				+"\t\t<td></td>\r\n"
				+"\t\t<td>Start:</td>\r\n"
				+"\t\t<td><div id=\"startTime\"></div></td>\r\n"
				+"\t\t<td><button onclick=\"timeFunction()\">Reset</button></td>\r\n"
				 +"\t</tr>\r\n"
				 +"\t<tr>\r\n");
				}	
					
				if (s.trim().startsWith("Cooking time:")) {
					System.out.println(s);
					String prepString = s.substring(s.indexOf("Cooking time:") + 13).trim();
					String[] cookStrings = prepString.split("\\s+");
					float cookTime = Float.parseFloat(cookStrings[0]);
					output.append("\t\t<td id=\"cookTimeLabel\">Cooking:</td>\r\n"
							+"\t\t<td class=recipeTime id=\"cookTime\">"+ cookTime + "</td>\r\n"
							+"\t\t<td id=\"cookTimeUnit\">" + cookStrings[1]+ "</td>\r\n"
							+"\t\t<td></td>\r\n"
							+"\t\t<td></td>\r\n"
							+"\t\t<td>End:</td>\r\n"
							+"\t\t<td><div id=\"endTime\"></div></td>\r\n"
							+"\t\t<td></td>\r\n"
							+"\t</tr>\r\n");
							
				
				}
				if (s.trim().startsWith("Ingredients")) {
					ingredient = true;
					System.out.println(s);
              
					String ingredients = s.replace("Ingredients:", "").trim();
					
					output.append("</table>\r\n"
							+"<br>\r\n"
							+"<table class=\"ingredients\">\r\n"
							+"\t<th class=\"ingredients\" width=\"100px\" align=\"left\">"+ ingredients + "</th>\r\n");
					if(!ingredientStart){
						// was output.append("\t<th colspan=\"3\" align=\"left\"><button id=\"ingredientUnitButton\" onclick=\"unitFunction(0, true)\" >Change units</button></th>\r\n");
						 output.append("\t<th  colspan=\"3\" align=\"left\"><button id=\"ingredientUnitButton\" onclick=\"unitFunction(0, true)\" >Change units</button>"
						+"&nbsp;Highlight&nbsp;<button id=\"highlightUpButton\" onclick=\"highlightFunction(0)\" >Up &#9650;</button>" 
						+"&nbsp;<button id=\"highlightDownButton\" onclick=\"highlightFunction(1)\" >Down &#9660;</button> </th>\r\n");
					}
					
							ingredientStart = true;
					continue;
				}
				if (s.trim().startsWith("Method")) {
					methodStep = true;
					ingredient = false;
					System.out.println(s);
					
					String method = s.replace("Method:", "").trim();
					
					output.append("</table>\r\n"
							+"<br>\r\n"
							+"<table class=\"method\">\r\n"
							+"\t<th class=\"method\"  width=\"100px\" align=\"left\">"+method+"</th>");
					if(!methodStart){
						output.append("<th align=\"left\">&nbsp;Change&nbsp;<button id=\"methodTempButton\" onclick=\"unitFunction(1, true)\">Temp units</button>&nbsp;<button id=\"methodMeasurementButton\" onclick=\"unitFunction(2, true)\">Measurement units</button></th>");
					}
					output.append("\r\n");
					methodStart=true;
				
					continue;
				}
				
				if (s.trim().startsWith("Service:")) {
					methodStep = false;
					ingredient = false;
					System.out.println(s);
					String service = s.substring(s.indexOf("Service:") + 8)
							.trim();
					output.append("</table>\r\n"
							+"<br>\r\n"
							+"<table class=\"footer\">\r\n"
							+"\t<tr>\r\n"
							+"\t\t<td class=\"serviceLabel\" width=\"100px\" align=\"left\">Service: </td>\r\n" 
							+"\t\t<td class=\"serviceDescription\">" + service + "</td>\r\n"
							+"\t</tr>\r\n");
					continue;
				}
				
				
			
				if (s.trim().startsWith("Attribution:")) {
					methodStep = false;
					ingredient = false;
					System.out.println(s);
					String attribution = s.substring(s.indexOf("Attribution:") + 12)
							.trim();
					output.append("\t<tr>\r\n"
							+"\t\t<td class=\"attributionLabel\" width=\"100px\" align=\"left\">Attribution:</td>\r\n" 
							+"\t\t<td class=\"attributionDescription\">" + attribution + "</td>\r\n"
							+"\t\t<td> <form action=\"index.html\"><button type=\"submit\">Index</button></form></td>\r\n"
							+"\t</tr>\r\n"
							+"</table>\r\n"
							+"</body>\r\n"
							+"</html>\r\n");
					break;
				}
				
				if(ingredient){
				System.out.println(s);
				String ingredientString = s.trim();
				String[] ingredientStrings = ingredientString.split("\\s+");
				float quant = 0;
				String unit = ingredientStrings[1];
				int start = 2;
				try{
				quant = Float.parseFloat(ingredientStrings[0]);
				
				}catch(Exception e){ // no quantity or unit
					unit = "";
					start = 0;
				}
				output.append("\t<tr class=\"ingredient\" onClick=\"boldFunction(this)\">\r\n"
						+"\t\t<td></td>\r\n"
						+"\t\t<td class=\"servesQuantity\">"+ quant + "</td>\r\n"
						+"\t\t<td class=\"unit\">"+ unit + "</td>\r\n"
						+"\t\t<td class = \"description\">");
				for (int j=start; j<ingredientStrings.length; j++)	{
					output.append(ingredientStrings[j] + " ");
					
				}
				output.append("</td>\r\n"
						+"\t</tr>\r\n");
				}
				
				if(methodStep){
					System.out.println(s);
					String methodString = s.trim();
					String[] methodStrings = methodString.split("\\s+");
					output.append("\t<tr class=\"methodStep\" onClick=\"boldFunction(this)\">\r\n"
							+"\t\t<td></td>\r\n"
							+"\t\t<td class = \"description\">");
					for (int j=0; j<methodStrings.length; j++)	{
						
						if(j < methodStrings.length-1){ // look for temp
							boolean isTemp = true;
							float quant  = -1;
							try{
								quant = Float.parseFloat(methodStrings[j]);
							}catch(Exception e){
								isTemp = false;
							}
							String temp = methodStrings[j+1].replace(".","");
							if(!temp.equals("C")&!temp.equals("F")){
								isTemp = false;
							}
							if(isTemp){	
								output.append("<span class=\"quantity\">"+ quant + "</span>&#186;<span class=\"unit\">"+ temp + "</span>.");
								j++;
								continue;
							}	
						}
						
						if(j < methodStrings.length-1){ // look for dimension
							boolean isDim = true;
							float quant  = -1;
							try{
								quant = Float.parseFloat(methodStrings[j]);
							}catch(Exception e){
								isDim = false;
							}
							String dim = methodStrings[j+1].replace("cms","cm").replace("inches","inch").replace("grams","gram").replace("lbs","lb").replace("kgs","kg").replace("tsps", "tsp").replace("tbsps", "tbsp");
							if(!dim.equals("cm")&!dim.equals("inch")&!dim.equals("gram")&!dim.equals("lb")&!dim.equals("kg")&!dim.equals("tsp")&!dim.equals( "tbsp")){
								isDim = false;
							}
							if(isDim){	
								output.append("<span class=\"quantity\">"+ quant + "</span>&nbsp;<span class=\"unit\">"+ dim + "</span> ");
								j++;
								continue;
							}	
						}
						output.append(methodStrings[j] + " ");
						
						}
						
					output.append("</td>\r\n"
							+"\t</tr>\r\n");
						
					}
							
		}
		       
				out.print(output.toString());
				out.flush();
				out.close();
System.out.println(output);
			

		


	}

}
	

	
	
}

var orig = new Array (true,true,true);//true;
var origQuantitys = [];
var origUnits = [];
var origServes = -1;
var changeUnit = false;
var currentMultiplier = [];
var lastEnboldened;


var elementClassNames = new Array ("ingredient","methodStep","methodStep"); // ingredient, temperature, method classes to change
var quantityClassNames = new Array ("servesQuantity","quantity","quantity"); // quantity classes to change

function unitFunction (index, changeUnit){  // Change ingredient units from Imperial to Metric and vice versa


var elems = document.querySelectorAll("." + elementClassNames[index]); // Don't use .getElementsByClassName() as pre-IE8 browsers don't support it 
var serves = document.getElementById("servesOption").selectedIndex + 1;
	for (i in elems){
			try{	
				var enboldened = false;
				var quantity = elems[i].querySelectorAll("." + quantityClassNames[index])[0].childNodes[0].nodeValue;
				if(quantity == null){
					quantity = elems[i].querySelectorAll("." + quantityClassNames[index])[0].childNodes[0].innerText;
					enboldened = true;
				}
				quantity = replaceFraction(quantity);
				var unit = elems[i].querySelectorAll('.unit')[0].childNodes[0].nodeValue;
				if(unit == null){
					unit = elems[i].querySelectorAll('.unit')[0].childNodes[0].innerText;
					enboldened = true;
				}
				var newUnit = unit;
				unit = singularUnit(unit);
				var multiplier = 1;
				if(orig[index]){
					origQuantitys[i + index*1000] = quantity;
					origUnits[i + index*1000] = unit;
					currentMultiplier[i + index*1000] = multiplier;
					continue; // Store original quantities and units but don't change units on page load
				} 
				if(changeUnit){
					if (index == 0 || index == 2){  // ingredients or method list
						
							switch (unit){
								case "gram":
									newUnit = "oz";
									multiplier = 0.0352;							
									break;
								case "kg":
									newUnit = "oz";
									multiplier = 35.273;							
									break;
								case "oz":
									newUnit = "grams";
									multiplier = 28.41;
									break;
								case "lb":
									newUnit = "grams";
									multiplier = 454.56;
									break;
								case "cm":
									multiplier = 0.3937;
									newUnit = quantity * multiplier < 2 ? "inch":"inches";
									break;
								case "inch":
									multiplier = 2.54;
									newUnit = quantity * multiplier < 2 ? "cm":"cms";
									break;
							}
						} else if (index == 1){ // method temperature
							switch (unit){
		
								case "C":
									newUnit = "F";
									multiplier = 1.8;
									break;
	
								case "F":
									newUnit = "C";
									multiplier = 0.55556;
									quantity -= 32;
									break;
							}
						}
						// If unit has changed, update current multiplier
						if(! (singularUnit(newUnit).valueOf().indexOf(unit)=== 0 && singularUnit(newUnit).length === unit.length)){
							currentMultiplier[i + index*1000] = currentMultiplier[i + index*1000] * multiplier;
						}
				 }
				// if original unit, remove any rounding errors
				if(singularUnit(newUnit).valueOf().indexOf(origUnits[i + index*1000])=== 0 && singularUnit(newUnit).length === origUnits[i +index*1000].length){
					multiplier = 1;
				}else{

					multiplier = currentMultiplier[i + index*1000];
				}
				// Always recalculate from orig values
				var temp = (unit === "C" | unit === "F");
				var quant = -1;
				if( (index == 1) & temp) { // temperature only

					quant = Math.round(origQuantitys[i + index*1000] * multiplier) + (unit === "C" ? 32 : 0);

				}else if((index != 1) & !temp){ // other ingredient list or method list 

					// Don't let serves affect method list
					if( index == 2){
						quant = Math.round(origQuantitys[i + index * 1000] * multiplier * 10 ) / 10  ;
					} else {
						quant = Math.round(origQuantitys[i + index * 1000] * serves / origServes * multiplier * 10 ) / 10  ;					
					}
				}
				if(quant > -1){	

					// refactor to appropriate magnitude units

					var preScaleUnit = newUnit;
					newUnit = reScaleUnit(newUnit, quant);
					var rescaleFactor = reScaleFactor(preScaleUnit, newUnit);
					currentMultiplier[i + index*1000] = currentMultiplier[i + index*1000] * rescaleFactor;
					quant = quantityFunction1(quant * rescaleFactor);

					quant = enboldened ? "<b>" + quant + "</b>" : quant;
					elems[i].querySelectorAll("." + quantityClassNames[index])[0].innerHTML = quant;
					elems[i].querySelectorAll('.unit')[0].innerHTML =  enboldened ? "<b>" + newUnit + "</b>" : newUnit;

				}
		}catch(ignore){
			// Do nothing
		}
	}

	if(origServes  == -1){
		origServes = serves;	
	}
	orig[index] = false;
}

function reScaleUnit(preScaleUnit, quant){ 	// returns Upscaled or downscaled unit
						
	if (preScaleUnit.indexOf("tsp") > -1 && quant > 2.9){ // if quantity greater than threshold increment unit
		return quant > 3.375 ? "tbsps" : "tbsp";
	}
	if (preScaleUnit.indexOf("tbsp") > -1 && quant < 0.9){ // if quantity less than unity decrement unit
		return quant > 0.375 ? "tsps" : "tsp";
	}
	if (preScaleUnit.indexOf("oz") > -1 && quant > 15.9){ // if quantity greater than threshold increment unit
		return quant > 18 ? "lbs" : "lb";
	}
	if (preScaleUnit.indexOf("lb") > -1 && quant < 0.9){ // if quantity less than unity decrement unit
		return "oz";
	}
	if (preScaleUnit.indexOf("gram") > -1 && quant > 900){ // if quantity greater than threshold increment unit
		return quant > 1125 ? "kgs" : "kg";
	}
	if (preScaleUnit.indexOf("kg") > -1 && quant < 0.9){ // if quantity less than unity decrement unit
		return "grams";
	}
	return preScaleUnit;
}


function reScaleFactor(preScaleUnit, newUnit){	// returns multiplier for upscaled or downscaled unit
	if (newUnit.indexOf("tsp") > -1 && preScaleUnit.indexOf("tbsp")> -1 ){
		return  3;
	}
	if (newUnit.indexOf("tbsp") > -1 && preScaleUnit.indexOf("tsp")> -1 ){
		return 0.3;
	}
	if (newUnit.indexOf("oz") > -1 && preScaleUnit.indexOf("lb")> -1 ){
		return  16;
	}
	if (newUnit.indexOf("lb") > -1 && preScaleUnit.indexOf("oz")> -1 ){
		return 0.0625;
	}
	if (newUnit.indexOf("gram") > -1 && preScaleUnit.indexOf("kg")> -1 ){
		return  1000;
	}
	if (newUnit.indexOf("kg") > -1 && preScaleUnit.indexOf("gram")> -1 ){
		return 0.001;
	}
	return 1;
}



function singularUnit(unit){	// Returns singular unit from plural unit.
	return unit.replace("cms","cm").replace("inches","inch").replace("grams","gram").replace("lbs","lb").replace("kgs","kg").replace("tsps", "tsp").replace("tbsps", "tbsp");
}


function replaceFraction(quantity){	// replace any vulgar fraction with decimal equivalent
				quantity = "" + quantity;
				var possibleFraction = quantity.charAt( quantity.length - 1); 
				var replacementString = possibleFraction;

				switch(possibleFraction){
					case String.fromCharCode(188):
						replacementString = ".25";
						break;
					case String.fromCharCode(189):
						replacementString = ".5";
						break;
					case String.fromCharCode(190):
						replacementString = ".75";
					}
				return quantity.replace(possibleFraction, replacementString);

}





function quantityFunction (){ // Display vulgar fractions rather than decimal for defined classes, rounding to nearest vulgar fraction.
	var quantityClassNames = new Array ("quantity","servesQuantity", "recipeTime"); // classes to change
   	var len = quantityClassNames.length;
	for (var i = 0; i < len; i++){
		var quants = document.querySelectorAll("." + quantityClassNames [i]);
		for (j in quants){
			try{
				var quantity = quants[j].childNodes[0].nodeValue;
				quants[j].innerHTML = quantityFunction1( quantity);
			}catch(ignore){
				// Do nothing
			}
		}
	}
}

function quantityFunction1( quantity){ // Display vulgar fractions rather than decimal for selected quantity, rounding to nearest vulgar fraction.
				var fractions = new Array("","","&frac14;","&frac14;","&frac12;","&frac12;","&frac12;","&frac34;","&frac34;","");
				quantity = replaceFraction(quantity);
				var floorQuantity = Math.floor(quantity);
				if(quantity != floorQuantity ){
					var index = Math.round(10 * (quantity - floorQuantity ));
					if(index > 8){
						floorQuantity++; // increment if > .8
					}	
					quantity = (floorQuantity == 0 ? "": floorQuantity)  + fractions[index]; // don't display zero
				} else {
					if(floorQuantity == 0 ){
						quantity = "";
					}
					quantity = quantity.replace(".0", "");

				}
				return quantity;
}


function toggleBoldFunction(element){	// toggle bold in selected table row
   	var tds = element.getElementsByTagName("td");
	for( i in tds){
		var enbolden = tds[i].innerHTML;
		if(enbolden != null){
			if(enbolden.indexOf("<b>") > -1 || enbolden.indexOf("<B>") > -1){ // clear line
				tds[i].innerHTML = enbolden.replace("<b>","").replace("</b>","").replace("<B>","").replace("</B>","");
				lastEnboldened = null;
			} else{ // enbolden line
				tds[i].innerHTML = "<b>" + enbolden + "</b>";	
				lastEnboldened = element;					
			}	
		}
	}
}

function boldFunction(element){	// enbolden selected table row, clear previous bold row
	if(lastEnboldened != null && lastEnboldened != element){
		toggleBoldFunction( lastEnboldened);
	}
	toggleBoldFunction( element);

}

function toMinutes(unit){ // return minutes multiplier for days and hours
	multiplier = 1;
	if(unit.indexOf("day") == 0){
		multiplier = 24 * 60;
	}
	if(unit.indexOf("hour") == 0){
		multiplier = 60;
	}
	return multiplier
}



function timeFunction(){ // Compute finish time in local time  not UTC
        var days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	var d = new Date();
	d.setMinutes(d.getMinutes() + d.getTimezoneOffset());
	document.getElementById("startTime").innerHTML = days[d.getDay()] + " " + (d.getHours() > 12? d.getHours() - 12 : d.getHours()) + ":" + (d.getMinutes() < 10 ? "0":"") + d.getMinutes() + (d.getHours() < 12 ? " am": " pm")  ;
	// parse minutes from prep and cook time
        var prepTimeUnit = document.getElementById("prepTimeUnit").innerText;
	var prepTime = document.getElementById("prepTime").innerText;
	var totalTime = replaceFraction(prepTime) * toMinutes(prepTimeUnit);
        var cookTimeUnit = document.getElementById("cookTimeUnit").innerText;
	var cookTime = document.getElementById("cookTime").innerText;
	var totalTime = totalTime + replaceFraction(cookTime) * toMinutes(cookTimeUnit);
	d.setMinutes(d.getMinutes() + totalTime);
	document.getElementById("endTime").innerHTML = days[d.getDay()] + " " + (d.getHours() > 12? d.getHours() - 12 : d.getHours()) + ":" + (d.getMinutes() < 10 ? "0":"") + d.getMinutes() + (d.getHours() < 12 ? " am": " pm")  ;


}


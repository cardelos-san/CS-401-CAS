function validateForm(form){
			var alertMsg = "";

			if (form.itemDescriptionPublic.value === ""){
				alertMsg = alertMsg + "Warning: Public Description is required.\n";
			}
			
			if (form.itemLocationFound.value === ""){
				alertMsg = alertMsg + "Warning: Location Found is required.\n"
			} 
			
			var isChecked = false;
			var nodeList = document.getElementsByTagName('input');
			for (var i = 0; i < nodeList.length; i++){
				var node = nodeList[i];

				if (node.getAttribute('type') == 'radio'){
					if (node.checked){
						isChecked = true;
					}
				}
			}
			if (!isChecked){
				alertMsg = alertMsg + "Warning: Category is required.\n"
			}
			
			//A lost item cannot be entered if it was found after today's date
			var todaysDate = new Date();
			//A lost item cannot be entered if it was found before this date
			var pastDate = new Date("2016-01-01")
			//The date which user stated the item was found
			var dateFound = form.itemDateFound.value;
			//If the item was 'found' after today's date
			if(Date.parse(todaysDate) < Date.parse(dateFound)){
				alertMsg = alertMsg + "Warning: Date Found is invalid."
			}
			//If the item was 'found' before '2016-01-01'
			else if(Date.parse(pastDate) > Date.parse(dateFound)){
				alertMsg = alertMsg + "Warning: Date Found is invalid (before year 2016)."
			}
			//If the item's date found has not been entered
			else if(!dateFound){
				alertMsg = alertMsg + "Warning: Date Found is required."
			}
			
			//If there were no errors, return
			if (alertMsg === ""){
				return true;
			}

			alert(alertMsg);
			return false;
		}
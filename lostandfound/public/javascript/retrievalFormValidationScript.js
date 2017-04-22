function validateRetrievalForm(form){
			var alertMsg = "";

			if (form.retrievalFirstName.value === ""){
				alertMsg = alertMsg + "Warning: First Name is required.\n";
			}
			
			if (form.retrievalLastName.value === ""){
				alertMsg = alertMsg + "Warning: Last Name is required.\n"
			} 
			
			if (form.retrievalEmail.value === ""){
				alertMsg = alertMsg + "Warning: Email is required.\n"
			} 
			
			if (form.retrievalPhone.value === ""){
				alertMsg = alertMsg + "Warning: Phone Number is required.\n"
			} 
			
			if (form.retrievalIdent.value === ""){
				alertMsg = alertMsg + "Warning: Identification is required.\n"
			} 
			
			//If there were no errors, return
			if (alertMsg === ""){
				return true;
			}

			alert(alertMsg);
			return false;
}
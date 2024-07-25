var baseUrl = "http://localhost:8080/AMS/";
$(document).ready(function(){
	$("#submit").click(function(){
		var url = $("#url").val();
		var type = $("#type").val();
		var input = {
				mobileNumber : "8019163365"
		}
		
		ajaxCall(url, type, input , function(res){
            if (res != null){
                console.log(res)
            }
		});
	});
	 function ajaxCall(url, type, input, outPut)
     {
		 var contentType =  'Application/json';
	     var data = JSON.stringify(input);
		 
		 if(type == "param"){
			 contentType = 'application/x-www-form-urlencoded; charset=UTF-8';
	    	 data = input;
		 }
			 
		  $.ajax({
				  url: baseUrl + url,
				  type: 'POST',
				  data : data,
				  contentType:contentType,		
				  success: function(res) 
				  {
					  outPut(res);
				  }
			  });    
     }
});

/*var input = {
		firstName : "keshab",
		lastName : "kumar",
		mobileNumber : "8019163365",
		deviceId : "lsjdsk"
	}*/


/*var input = {
		cpAddress1 :"keshab",
		cpAddress2 : "kdfh",
		cpPinCode : "83646",
		cpCity : 1,
		cpState : 1,
		cpCountry : 1,
		permanentAddress1 : "sd",
		permanentAddress2 : "sd",
		permanentAddressPinCode : "83646",
		ptCity : 1,
		ptState : 1,
		ptCountry : 1,
		officeAddress1 : "sd",
		officeAddress2 : "sd",
		officeAddressPinCode : "83646",
		ofCity : 1,
		ofState : 1,
		ofCountry : 1,
		citizen : 7,
		id : 1
}
*/
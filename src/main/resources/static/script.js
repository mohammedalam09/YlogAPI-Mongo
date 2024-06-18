function login() {
	
 // if (  null !==  $("#username").val() && $("#password").val() == "Admin@123") {
	  $('#loader').removeClass('d-none')
	  
	  var dataObj = {
			userName : $("#username").val(),
			password : $("#password").val(),
			userIp : typeof(userip) == "undefined" ? "" : userip
		};
	 
	var response= $.ajax({
		  url:'https://ociqaapp.ylogapp.com/YLogAPI/login',
		  method:'POST',
		  data:JSON.stringify(dataObj),
		  contentType : 'application/json; charset=utf-8',
		  dataType:'json',
		  })
		  response.done( function(data){
			  $('#loader').addClass('d-none') 
			  console.log(data.results)
		  })
	  
	  
    //alert("You are a valid user");
  /*}
   else {
    alert("You are not a valid user");
  }*/
}

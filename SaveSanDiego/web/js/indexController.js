$(document).ready(function(){
   $('#sendButton').click(function(){
      $.get("ExchangeServlet",{"command":"sendMail"},function(){
          alert("sent mail");
      }) 
   });
});
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<h2>
   Welcome

   <%
      UserProfile userProfile = (UserProfile) request.getSession().getAttribute("userInfo");
      if(userProfile != null) {
         out.println("</br>");
         out.println("Name: " + userProfile.getName());
         out.println("</br>");
         out.println("Sub: " + userProfile.getSub());
      }
      else{
         out.println("Error occurred!");
      }
   %>
</h2>
</body>
</html>

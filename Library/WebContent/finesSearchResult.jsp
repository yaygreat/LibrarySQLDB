<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="library.bean.Book"%>
<%@page import="library.bean.Borrower"%>
<%@page import="library.bean.Loan"%>
<%@page import="library.bean.Fine"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Fines Result</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
    /* Remove the navbar's default rounded borders and increase the bottom margin */ 
    .navbar {
      margin-bottom: 50px;
      border-radius: 0;
    }
    
    /* Center image and able to scale down image, but won't scale up larger than original */
    .responsive {
    	display: block;
		margin-left: auto;
		margin-right: auto;
		max-width: 100%;
		height: auto;
	}
	
	/*affix book logo to navbar*/
	.data-image{
	margin: -15px 0px;
	}
    
    /* Remove the jumbotron's default bottom margin */ 
     .jumbotron {
      margin-bottom: 0;
    }
   
    /* Add a gray background color and some padding to the footer */
    footer {
      background-color: #f2f2f2;
      padding: 25px;
    }
  </style>
</head>
<body>

<div class="jumbotron">
	<div class="container text-center">
		<a href="search.jsp"><img class="responsive" src="BookLogo.PNG" alt="abpbooks" style="width:200px"></a>
	</div>
</div>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="<%=request.getContextPath()%>/RefreshFinesController?id=finesSearch"><img class="data-image" src="BookIcon.PNG" style="width:50px;height:50px;"></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="search.jsp"><span class="glyphicon glyphicon-search"></span> Search</a></li>
        <li><a href="checkinSearch.jsp"><span class="glyphicon glyphicon-book"></span> Check In</a></li>
        <li class="active"><a href="fineSearch.jsp"><span class="glyphicon glyphicon-list-alt"></span> Fines</a></li>
        <li><a href="register.jsp"><span class="glyphicon glyphicon-user"></span> Register</a></li>
      </ul>
    </div>
  </div>
</nav>

${message}
<div style="overflow-x:auto;">
<table class="table">
  <thead>
    <tr>
      <th scope="col">Borrower ID</th>
      <th scope="col">Fine Amount</th>
    </tr>
  </thead>
  <tbody>
			<%
				List<Fine> fineList = (List<Fine>)request.getAttribute("fineList");
				Borrower borrower;
					if(fineList != null && fineList.size() > 0){
						double total = 0;
						for(Fine fine : fineList){
							borrower = fine.getBorrower();
			%>
						<tr>
							<td><%= borrower.getBorrowerID() %></td>
							<td><%= fine.getFineAmt() %></td>
							
							<td>
							<form name="loanform" action="<%=request.getContextPath()%>/FineSearchController" method="post" >
							 <div class="form-group">
								<input type="hidden" id=borrower_id name="borrower_id" value=<%=borrower.getBorrowerID()%>>
								<button type="submit" class="btn btn-primary">Pay</button>
							 </div>
						    </form>
						    </td>
								
								
						</tr>
						<%
					}
				}
				
			%>
  </tbody>
</table>
</div>


<footer class="container-fluid text-center">
	<p>
	UTD Library <br>
	800 W. Campbell Road <br>
	Richardson, Texas 75080-3021
	</p>
</footer>

</body>
</html>













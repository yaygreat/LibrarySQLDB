<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@page import="library.bean.Borrower"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Update Borrower</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="formhelper" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-formhelpers/2.3.0/css/bootstrap-formhelpers.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-formhelpers/2.3.0/js/bootstrap-formhelpers.js"></script>
  
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
		<a href="search.jsp"><img class="responsive" src="BookLogo.PNG" alt="books" style="width:200px"></a>
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
      <a class="navbar-brand" href="<%=request.getContextPath()%>/RefreshFinesController?id=register"><img class="data-image" src="BookIcon.PNG" style="width:50px;height:50px;"></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="search.jsp"><span class="glyphicon glyphicon-search"></span> Search</a></li>
        <li><a href="checkinSearch.jsp"><span class="glyphicon glyphicon-book"></span> Check In</a></li>
        <li><a href="fineSearch.jsp"><span class="glyphicon glyphicon-list-alt"></span> Fines</a></li>
        <li class="active"><a href="register.jsp"><span class="glyphicon glyphicon-user"></span> Register</a></li>
      </ul>
    </div>
  </div>
</nav>

<%--get borrower data from BorrowerController, fill in fields, and send info to Borrower Controller to update--%>
<% Borrower borrower = (Borrower)request.getAttribute("borrower"); %>

${message}<br>
<div class="container">
	<form class="form-horizontal" action="<%=request.getContextPath()%>/BorrowerController" method="post">
	<fieldset>
	<legend>Register:</legend>
		<div class="form-group">
			<label class="control-label col-sm-2" for="firstname">First Name:</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="firstname" placeholder="Enter First Name" name="firstname" >
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="lastname">Last Name:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control" id="lastname" placeholder="Enter Last Name" name="lastname" >
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="ssn">SSN:</label>
			<div class="col-sm-10">
				<p><%=borrower.getSSN()%></p>  
				<input type="hidden" id="ssn" name="ssn" value=<%=borrower.getSSN()%>>
				<input type="hidden" id="borrower_id" name="borrower_id" value=<%=borrower.getBorrowerID()%>>
				<input type="hidden" id="update" name="update" value=1>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="address">Address:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control" id="address" placeholder="Enter Address" name="address" >
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="city">City:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control" id="city" placeholder="Enter City" name="city" >
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="state">State:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control" id="state" placeholder="Enter State" name="state" >
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="phone">Phone:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control input-medium bfh-phone" id="phone" placeholder="(555) 555-5555" data-format="(ddd) ddd-dddd"  name="phone" >
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="email">Email:</label>
			<div class="col-sm-10">          
				<input type="email" class="form-control" id="email" placeholder="borrower@example.com" name="email" >
			</div>
		</div>
		<div class="form-group">        
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>
	</fieldset>
	</form>
	
</div><br><br>

<footer class="container-fluid text-center">
	<p>
	UTD <br>
	800 W. Campbell Road <br>
	Richardson, Texas 75080-3021
	</p>
</footer>

</body>
</html>
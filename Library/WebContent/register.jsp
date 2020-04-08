<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Register</title>
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

<%--create modal that grabs borrower id and sends to borrower controller--%>
${message}<br>
<div class="container">
	<form class="form-horizontal" action="<%=request.getContextPath()%>/BorrowerController" method="post">
	<fieldset>
	<legend>Register:</legend>
		<div class="form-group">
			<label class="control-label col-sm-2" for="firstname">First Name:</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="firstname" placeholder="Enter First Name" name="firstname" required>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="lastname">Last Name:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control" id="lastname" placeholder="Enter Last Name" name="lastname" required>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="ssn">SSN:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control" id="ssn" placeholder="888-88-8888" data-format="ddd-dd-dddd" maxlength="11" name="ssn" required>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="address">Address:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control" id="address" placeholder="Enter Address" name="address" required>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="city">City:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control" id="city" placeholder="Enter City" name="city" required>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="state">State:</label>
			<div class="col-sm-10">          
				<input type="text" class="form-control" id="state" placeholder="Enter State" name="state" required>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="phone">Phone:</label>
			<div class="col-sm-10">          
				<input type="tel" class="form-control input-medium bfh-phone" id="phone" placeholder="(555) 555-5555" data-format="(ddd) ddd-dddd" name="phone" required>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="email">Email:</label>
			<div class="col-sm-10">          
				<input type="email" class="form-control" id="email" placeholder="borrower@example.com" name="email" required>
			</div>
		</div>
		<div class="form-group">        
			<div class="col-sm-offset-2 col-sm-10">
				<%--<input type="hidden" id="update" name="update"> --%>
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>
	</fieldset>
	</form>
	<div class="col-sm-offset-2 col-sm-10">
	<p>--- OR ---</p>
	</div>
	<div class="col-sm-offset-2 col-sm-10">
		<!-- Trigger the modal with a button -->
		<button type="button" style="float: left" class="btn btn-secondary" data-toggle="modal" data-target="#modal">Update a Borrower</button>
		
		<!-- Modal -->
		<div id="modal" class="modal fade" role="dialog">
		  <div class="modal-dialog">
		
		    <!-- Modal content-->
		    <div class="modal-content">
		    <form name="BorrowerIDform" action="<%=request.getContextPath()%>/BorrowerController" method="post" >
		      
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="modal-title">Update Borrower</h4>
		      </div>
		      <div class="modal-body">
		        <div class="form-group">
					<div class="col-sm-10">
						<input type="number" class="form-control" id="borrower_id" placeholder="Enter Borrower ID" name="borrower_id">
					</div>
				</div>
		      </div><br>
		      <div class="modal-footer">
		      	<button type="submit" class="btn btn-primary">Verify Borrower</button> 
		      </div>
		      
		    </form>
		    </div>
		
		  </div>
		</div>
	</div>
	
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

<script type="text/javascript">
       $('#ssn').keyup(function() {
          var val = this.value.replace(/\D/g, '');
          var newVal = '';
          if(val.length > 4) {
             this.value = val;
          }
          if((val.length > 3) && (val.length < 6)) {
             newVal += val.substr(0, 3) + '-';
             val = val.substr(3);
          }
          if (val.length > 5) {
             newVal += val.substr(0, 3) + '-';
             newVal += val.substr(3, 2) + '-';
             val = val.substr(5);
           }
           newVal += val;
           this.value = newVal.substring(0, 11);
        });
    </script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="library.bean.Book"%>
<%@page import="library.bean.Borrower"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Search Result</title>
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
      <a class="navbar-brand" href="<%=request.getContextPath()%>/RefreshFinesController?id=search"><img class="data-image" src="BookIcon.PNG" style="width:50px;height:50px;"></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li class="active"><a href="search.jsp"><span class="glyphicon glyphicon-search"></span> Search</a></li>
        <li><a href="checkinSearch.jsp"><span class="glyphicon glyphicon-book"></span> Check In</a></li>
        <li><a href="fineSearch.jsp"><span class="glyphicon glyphicon-list-alt"></span> Fines</a></li>
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
    <th>Cover</th>
      <th scope="col">ISBN10</th>
      <th scope="col">ISBN13</th>
      <th scope="col">Title</th>
      <th scope="col">Authors</th>
      <th scope="col">Avl.</th>
    </tr>
  </thead>
  <tbody>
   	<tr>
			<%
				List<Book> bookList = (List<Book>)request.getAttribute("bookList");
					if(bookList != null && bookList.size() > 0){
						for(Book book : bookList){
			%>
						<tr>
							<td><img class="responsive" src="http://www.openisbn.com/cover/0452011345_72.jpg" alt="book_cover"></td>
							<td><%= book.getISBN10() %></td>
							<td><%= book.getISBN13() %></td>
							<td><%= book.getTitle() %></td>
							<td><%= book.getAuthorNames() %></td>
							<td><%= book.getAvailability() %></td>
							
							<td><!-- Trigger the modal with a button -->
								<button type="button" class="btn btn-info btn-md" data-toggle="modal" data-target="#<%= book.getISBN10() %>">Checkout</button>
								
								<!-- Modal -->
								<div id="<%= book.getISBN10() %>" class="modal fade" role="dialog">
								  <div class="modal-dialog">
								
								    <!-- Modal content-->
								    <div class="modal-content">
								    <form name="loanform" action="<%=request.getContextPath()%>/LoanController" method="post" >
								      
								      <div class="modal-header">
								        <button type="button" class="close" data-dismiss="modal">&times;</button>
								        <h4 class="modal-title">Checkout Modal</h4>
								      </div>
								      <div class="modal-body">
								      <p><%= book.getISBN10() %>, <%= book.getISBN13() %><br>
								      <%= book.getTitle() %> by <%= book.getAuthorNames() %></p>
										<input type="hidden" id=ISBN10 name="ISBN10" value=<%=book.getISBN10()%>>
								        <div class="form-group">
											<div class="col-sm-10">
												<input type="number" class="form-control" id="borrower_id" placeholder="Enter Borrower ID" name="borrower_id">
											</div>
										</div>
								      </div><br>
								      <div class="modal-footer">
								      	<button type="submit" class="btn btn-primary">Checkout</button> 
								      </div>
								      
								    </form>
								    </div>
								
								  </div>
								</div>
								
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













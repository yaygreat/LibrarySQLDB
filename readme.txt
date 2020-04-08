Book Library Webapp is composed of jsp's and java files.
SQL Script uses LOAD DATA INFILE to load the csv data directly into the database.
In order for this to work correctly, you must go to the my.ini file and set the priv var to ""
and set the .csv folders within uploads folder in mysql.

Required downlaods:
Java 8 EE
Apache Tomcat Server
MySQL

<h1>Quickstart Guide:</h1>

**Borrower Register:**  
In order to register a borrower so that they will be able to check out books, input all the information required by the fields given. A borrower with the same SSN cannot exist. A unique borrower id will be automatically created for each new borrower.

**Book Search:**  
Using the search field, search for a book, with any combination of ISBN, title, and/or Author(s). Search supports substring matching where the full text of a title, author name, etc. is not required and will return matches of the text in ISBN, title, and Author. For example, “william” returns author “William Jones”, author “Sam Williamson”, and title “Houses of Williamsburg, Virginia”). The availability of a book will be shown as a 1 for available or  0 for NOT available.

**Book Checkout:**  
Once a book has been found, it can be checked out by clicking on the checkout button and providing a borrower id in the pop-up. There will be a success message stating that the book was successfully checked out and the due date will always be set to 14 days from the checkout date.
*Possible Error Messages:*  
Book is already checked out – The selected book has already been checked out
Borrower already has 3 books out – The borrower with the inputted borrower id cannot check out more than 3 books simultaneously
Error, could not update database – There was a problem communicating with the database and the checkout was unsuccessful

**Book Check In:**  
To check in a book, type in all or some of the fields provided (ISBN, borrower id, borrower name) to search for loans. If all fields are left blank, all loans will be returned.

**Loan Fines:**  
Enter a borrower id to search for the loans belonging to that borrower or leave it blank to search for all loans. Select a fine to pay and click the pay button, a popup will appear with a message that only full payments of fines are accepted. If the borrower has paid in full, click pay. The fine will cleared from the borrower and a success message will appear. Fines are calculated at $0.25 per day until the book is checked in and no fine associated with a checked out book can be paid. All fine amounts can be recalculated/refreshed if a blank search is done.
*Possible Error Messages:*  
Error, could not update database – There was a problem communicating with the database and the payment was unsuccessful
Borrower has not checked in this book – Payment cannot be received because book is still checked out

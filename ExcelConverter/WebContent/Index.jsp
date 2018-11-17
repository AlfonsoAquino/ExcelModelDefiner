<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Model Definer</title>

<!-- bootsnipp drag&drop -->
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" type="text/css" href="modify.css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<!-- mio. -->
<script src="script.js"></script>

</head>
<body>
<%session.invalidate(); %>
	<form method="post" action="Serv" id="formSub"
		enctype="multipart/form-data">
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<div class="form-group files">
						<label>Upload file di partenza</label> <input type="file"
							class="form-control" id="starter" multiple="" name="starter"
							required>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group files color">
						<label>Upload modello</label> <input type="file"
							class="form-control" id="model" name="model" multiple="" required>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="exampleInputEmail1">inserire il numero della
							riga di intestazione</label> <input type="text" class="form-control"
							id="inteStarter" name="inteStarter" placeholder="es. 3" name=""
							required>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="exampleInputEmail1">inserire il numero della
							riga di intestazione</label> <input type="text" class="form-control"
							id="inteModel" name="inteModel" placeholder="es. 3" required>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<button type="submit" class="btn btn-success"
						onClick="return send()" value="Upload">Send</button>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
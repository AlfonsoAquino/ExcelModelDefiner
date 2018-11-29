<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.ArrayList, org.apache.poi.*, java.util.*, org.apache.poi.hssf.usermodel.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Model Config</title>

<!-- bootsnipp Table -->
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<link rel="stylesheet" type="text/css" href="modify.css">
</head>
<body>
	<%
		int starterIndex = 0;
		int modelIndex = 0;
	%>
	<form method="post" action="ModelD" id="modelForm"
		enctype="multipart/form-data">
		<div class="container">
			<div class="row">
				<div class="col-md-2">
					<%
						if (session.getAttribute("validate").equals(true)) {
					%>
					<input type="text" placeholder="Titolo del modello" name="fileName"
						id="fileName" class="form-control" required><br>

				</div>
				<div class="col-md-4">
					<button type="submit" class="btn btn-success"
						onClick="return modelConfirm()" value="Upload">Send</button>
					<%
						}
					%>
				</div>
				<div class="col-md-4"></div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Starter File</h3>
							<div class="pull-right">
								<span class="clickable filter" data-toggle="tooltip"
									title="Toggle table filter" data-container="body"> <i
									class="glyphicon glyphicon-filter"></i>
								</span>
							</div>
						</div>
						<div class="panel-body">
							<input type="text" class="form-control" id="dev-table-filter"
								data-action="filter" data-filters="#dev-table"
								placeholder="Filter Developers" />
						</div>
						<table class="table table-hover" id="dev-table">
							<thead>
								<tr>
									<th>#</th>
									<th>Intestazione</th>
								</tr>
							</thead>
							<tbody>
								<%
									if (session.getAttribute("sheetDataStarter") != null) {
										List<List<HSSFCell>> sheetDataStarter = (List<List<HSSFCell>>) session.getAttribute("sheetDataStarter");
										for (List<HSSFCell> item : sheetDataStarter) {
											for (int j = 0; j < item.size(); j++) {
												HSSFCell cell = item.get(j);
								%>
								<tr>
									<%
										starterIndex++;
									%>
									<td><%=starterIndex%></td>
									<td><%=cell.toString()%></td>
									<td></td>
								</tr>
								<%
									}
										}
									}
								%>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-md-8">
					<div class="panel panel-success">
						<div class="panel-heading">
							<h3 class="panel-title">Model file</h3>
							<div class="pull-right">
								<span class="clickable filter" data-toggle="tooltip"
									title="Toggle table filter" data-container="body"> <i
									class="glyphicon glyphicon-filter"></i>
								</span>
							</div>
						</div>
						<div class="panel-body">
							<input type="text" class="form-control" id="task-table-filter"
								data-action="filter" data-filters="#task-table"
								placeholder="Filter Tasks" />
						</div>
						<table class="table table-hover" id="task-table">
							<thead>
								<tr>
									<th>#</th>
									<th>Intestazione</th>
									<th>Collegamento</th>
									<!--<th>Ordinamento</th>-->
									<th>Limiti su caratteri</th>
								</tr>
							</thead>
							<tbody>
								<%
									if (session.getAttribute("sheetDataModel") != null) {
										List<List<HSSFCell>> sheetDataModel = (List<List<HSSFCell>>) session.getAttribute("sheetDataModel");
										for (List<HSSFCell> item : sheetDataModel) {
											for (int j = 0; j < item.size(); j++) {
												HSSFCell cell = item.get(j);
								%>
								<tr>
									<%
										modelIndex++;
									%>
									<td><%=modelIndex%></td>
									<td><%=cell.toString()%></td>
									<td>0 = non collegato <br><input type="number" min="0" max="<%=starterIndex%>"
										name="collegaModel" id="collegaModel"
										placeholder="Collega Model"  value=0 required></td>
									<!-- <td><div class="form-check">
											0 = sovrascritto; 1,2,.. = ordine accodamento <input
												type="number" min=0 name="ordineAcc" id="ordineAcc"
												placeholder="Ordine accodamento" value="0" required>
											<br>
										</div></td>-->
									<td>
										<div class="form-check">
											0 = nessun limite<br><input type="number" min="0" name="limit" id="limit"
												placeholder="Limite caratteri" value="0" required>
										</div>
									</td>
								</tr>
								<%
									}
										}
									}
								%>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<%
						if (session.getAttribute("validate").equals(true)) {
					%>
					<button type="submit" class="btn btn-success btn-block"
						onClick="return modelConfirm()" value="Upload">Send</button>
					<%
						}
					%>
				</div>
				<div class="col-md-4"></div>
			</div>
		</div>
	</form>
	<!-- search in table -->
	<script>
		(function() {
			'use strict';
			var $ = jQuery;
			$.fn
					.extend({
						filterTable : function() {
							return this
									.each(function() {
										$(this)
												.on(
														'keyup',
														function(e) {
															$(
																	'.filterTable_no_results')
																	.remove();
															var $this = $(this), search = $this
																	.val()
																	.toLowerCase(), target = $this
																	.attr('data-filters'), $target = $(target), $rows = $target
																	.find('tbody tr');

															if (search == '') {
																$rows.show();
															} else {
																$rows
																		.each(function() {
																			var $this = $(this);
																			$this
																					.text()
																					.toLowerCase()
																					.indexOf(
																							search) === -1 ? $this
																					.hide()
																					: $this
																							.show();
																		})
																if ($target
																		.find(
																				'tbody tr:visible')
																		.size() === 0) {
																	var col_count = $target
																			.find(
																					'tr')
																			.first()
																			.find(
																					'td')
																			.size();
																	var no_results = $('<tr class="filterTable_no_results"><td colspan="'+col_count+'">No results found</td></tr>')
																	$target
																			.find(
																					'tbody')
																			.append(
																					no_results);
																}
															}
														});
									});
						}
					});
			$('[data-action="filter"]').filterTable();
		})(jQuery);

		$(function() {
			// attach table filter plugin to inputs
			$('[data-action="filter"]').filterTable();

			$('.container').on('click', '.panel-heading span.filter',
					function(e) {
						var $this = $(this), $panel = $this.parents('.panel');

						$panel.find('.panel-body').slideToggle();
						if ($this.css('display') != 'none') {
							$panel.find('.panel-body input').focus();
						}
					});
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
</body>
</html>
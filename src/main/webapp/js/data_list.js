// Author: Ming Jin
// Contact: jinming0106g@gmail.com
// Created Date: 22th Aug, 2021

// data list
var dataList = [];
var state = 'normal';
var editIndex = -1;

function refreshTable() {

	let tableBodyContents = "";
	let index = 0;
	for (let record of dataList) {
		tableBodyContents += getTableRecordHtml(record, index);
		index++;
	}

	$('#table-body').html(tableBodyContents);
}

/**
 * this is called when the user clicks add button
 */
function onClickAddButton() {
	let data = $('#data').val();
	console.log('>>> new data: ', data);
	$.ajax({
		type : "POST",
		url : '/rest/data',
		data: {'data': data},
		dataType : 'json',
		success : function(record) {
			dataList.push(record);

			console.log(">>>> add api call back: ", record);
			let trHtml = getTableRecordHtml(record, dataList.length - 1);
			$('#table-body').append(trHtml);

			$('#data').val('');
		},
		error : function() {
		}
	});
}

/**
 * this is called when the user clicks edit button
 * @param index
 */
function onClickEditButton(index) {
	state = 'edit';
	editIndex = index;
	refreshTable();
	$('#input_div')[0].hidden = true;

}

function onClickConfirmButton(index) {

	console.log('>>> edit id: ', dataList[index].id);

	state = 'normal';
	editIndex = -1;

	let data = $('#edit_data').val();
	$.ajax({
		type : "PUT",
		url : '/rest/data',
		data: {'data': data, id: dataList[index].id},
		dataType : 'json',
		success : function(resp) {
			console.log('>>> edit confirm api response: ', resp)

			dataList[index].data = data;
			refreshTable();
			$('#input_div')[0].hidden = false;
		},
		error : function() {
		}
	});

}

/**
 * this is called when the user clicks delete button
 * @param index
 */
function onClickDeleteButton(index) {

	console.log('>>> delete id: ', dataList[index].id);
	$.ajax({
		type : "DELETE",
		url : '/rest/data?id=' + dataList[index].id,
		// data: {id: dataList[index].id},
		dataType : 'json',
		success : function(resp) {
			console.log('>>> delete api response: ', resp)

			dataList.splice(index, 1);
			refreshTable();
		},
		error : function() {
		}
	});

}

/**
 * this is called when the user clicks cancel button
 * @param index
 */
function onClickCancelButton(index) {

	state = 'normal';
	editIndex = -1;
	refreshTable();
	$('#input_div')[0].hidden = false;

}

/**
 * get html string of a record of table
 * @param record
 * @param index
 * @returns {string}
 */
function getTableRecordHtml(record, index) {
	let trHtml = "<tr id='record" + index + "'>";
	trHtml += 		"<td style='width: 25%'><i></i>" + (index + 1) + "</td>";

	if (state == 'edit' && editIndex == index)
		trHtml += 	"<td style='width: 25%'><input type='text' class='edit-input' id='edit_data' value='" + record.data + "'/></td>";
	else
		trHtml += 	"<td style='width: 25%'><i></i>" + record.data + "</td>";

	trHtml += 		"<td style='width: 25%'><i></i>" + record.createdAt.substring(0, record.createdAt.length - 2) + "</td>";

	trHtml +=		"<td style='width: 25%'>";

	if (state == 'edit' && editIndex == index)
		trHtml +=			"<button class='btn btn-confirm' onclick='javascript:onClickConfirmButton(" + index + ")'>Save</button>";
	else if (state == 'edit')
		trHtml +=			"<button class='btn btn-disabled' disabled>Edit</button>";
	else
		trHtml +=			"<button class='btn btn-edit' onclick='javascript:onClickEditButton(" + index + ")'>Edit</button>";

	if (state == 'edit' && editIndex == index)
		trHtml +=			"<button class='btn btn-delete' onclick='javascript:onClickCancelButton(" + index + ")'>Cancel</button>";
	else if (state == 'edit')
		trHtml +=			"<button class='btn btn-disabled' disabled>Delete</button>";
	else
		trHtml +=			"<button class='btn btn-delete' onclick='javascript:onClickDeleteButton(" + index + ")'>Delete</button>";

	trHtml +=		"</td>";
	trHtml +=	"</tr>";

	return trHtml;
}


$(document).ready(function() {
	console.log('>>> document ready');

	$.ajax({
		type : "GET",
		url : '/rest/data',
		dataType : 'json',
		success : function(data) {
			console.log(">>>> data json: ", data);
			dataList = data;

			refreshTable();
		},
		error : function() {
		}
	});
});

/* -------------------------------------------------------------------------- */

/*                           ajax call config                           */

/* -------------------------------------------------------------------------- */

function ajaxCall(target, method, url, callbackFunc)
{
	var targetID = $(this);
	var callbacks = $.Callbacks();
	var callFunction = null;

	if ( !gf_IsNull(callbackFunc) ) {
		callFunction = eval(callbackFunc);

		if ( typeof callFunction != "function" ) {
			console.log("call back function "+ callbackFunc + "is not defined!!");
			return;
		}
	}
	
	// ajax 호출
	$.ajax({
		type: method,
		url: url,
		datatype: "application/json",
		success: function(data){
			//  call callback function
			if ( !gf_IsNull(callFunction) ) {
				callbacks.add(callFunction);
				callbacks.fire(target, data);
			}
		},
		error: function (xhr, ajaxOptions, thrownError) {
			console.log(xhr.status);
			console.log(thrownError);
		},
		async : true
	});
}

function ajaxDataCall(target, method, url,  param , callbackFunc)
{
	var targetID = $(this);
	var callbacks = $.Callbacks();
	var callFunction = null;

	if ( !gf_IsNull(callbackFunc) ) {
		callFunction = eval(callbackFunc);

		if ( typeof callFunction != "function" ) {
			console.log("call back function "+ callbackFunc + "is not defined!!");
			return;
		}
	}
	// ajax 호출
	$.ajax({
		type: method,
		url: url,
		data: JSON.stringify(param),
		datatype: "application/json",
		success: function(data){
			//  call callback function
			if ( !gf_IsNull(callFunction) ) {
				callbacks.add(callFunction);
				callbacks.fire(target, data);
			}
		},
		error: function (xhr, ajaxOptions, thrownError) {
			console.log(xhr.status);
			console.log(thrownError);
		},
		async : true
	});
}

function gf_IsNull(sValue) {
  if (new String(sValue).valueOf() == "undefined") return true;
  if (sValue == null) return true;
  var v_ChkStr = new String(sValue);
  if (v_ChkStr == null) return true;
  if (v_ChkStr.toString().length == 0) return true;
  return false;
}


/* -------------------------------------------------------------------------- */

/*                           bootstrap table options                          */

/* -------------------------------------------------------------------------- */

function bootstrapTableRefresh(target, url, local,  message){
	target.bootstrapTable('refreshOptions', {
				theadClasses:'bg-200 text-900',					
				locale: local,
				sortable:"true",
				detailView:"true",
				pagination:"true",	
				url : url,
				responseHandler: responseHandler,
				detailFormatter:detailFormatter,
				onLoadError : function(status) {
					alert("[STATUS " + status + "]" + message);
				}
			})
}

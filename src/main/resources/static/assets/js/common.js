/* -------------------------------------------------------------------------- */

/*                           ajax call config                           */

/* -------------------------------------------------------------------------- */

function ajaxCall(param) {  

	var callbackFunc = "callback";

 	 var ajaxParamObj = {
	     token : $('#csrfToken').val(),
	     header : $('#csrfHeader').val(),
	     callFunction : null,
		 target : null,
		 method : "GET",
		 url : null,
		 dataType : "json",
		 async : true,
		 isLoading : true,
		 data : {}
	 }
  	
		
  	if(param == null){  		
		console.log("param is null !!");
  		return;
  	}  
  	
  	if(param.url != null){  		
		param.url = param.url.replace(/\/\//gi, "/")
  	}  
  	  
	ajaxParamObj.callFunction =  gf_IsNull(param.callbackFunc) 
								 ? eval(callbackFunc)
								 : eval(param.callbackFunc)
	
	$.each(param,function(k, v){
		ajaxParamObj[k] = v;
	});
		
	if(gf_IsNull(param.data)){
		noDataAjax(ajaxParamObj)
	}else{
		dataAjax(ajaxParamObj)	
	} 
 }  


function noDataAjax(ajaxParamObj)
{
	$.ajax({
		type: ajaxParamObj.method,
		url: ajaxParamObj.url,
		dataType : ajaxParamObj.dataType,
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("Accept", "application/json");
	        xhr.setRequestHeader("Content-Type", "application/json");
	        xhr.setRequestHeader(ajaxParamObj.header, ajaxParamObj.token);
	    },
		success: function(data){
			var callbacks = $.Callbacks();
			callbacks.add(ajaxParamObj.callFunction);
			callbacks.fire(ajaxParamObj.target, data);
		},
		error: function (xhr, textStatus, errorThrown) {
			 if (xhr.status == 403 || textStatus == 'parsererror') {
		       location.href = "/login";
		    } else{
				alertModal(xhr.responseText);
		    }
		},
		async : ajaxParamObj.async
	});
}

function dataAjax(ajaxParamObj)
{

	$.ajax({
		type: ajaxParamObj.method,
		url: ajaxParamObj.url,
		dataType : ajaxParamObj.dataType,
		data: JSON.stringify(ajaxParamObj.data),
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("Accept", "application/json");
	        xhr.setRequestHeader("Content-Type", "application/json");
	        xhr.setRequestHeader(ajaxParamObj.header, ajaxParamObj.token);
	    },
		success: function(data){
			var callbacks = $.Callbacks();
			callbacks.add(ajaxParamObj.callFunction);
			callbacks.fire(ajaxParamObj.target, data);
		},
		error: function (xhr, textStatus, errorThrown) {
			 if (xhr.status == 403 || textStatus == 'parsererror') {
		       location.href = "/login";
		    } else{
				alertModal(xhr.responseText);
		    }
		 },
		async : ajaxParamObj.async
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

function bootstrapTableRefresh(target, url, queryParams, local,  message){
	target.bootstrapTable('refreshOptions', {
		theadClasses:'bg-200 text-900',					
		locale: local,
		sortable:"true",
		queryParams:"true",
		detailView:"true",
		pagination:"true",	
		url : url,
		responseHandler: responseHandler,
		detailFormatter:detailFormatter,
		onLoadError : function(status) {
			alertModal("[STATUS " + status + "]" + message)
		}
	})
}

function detailFormatter(index, row) {
	var html = []
	$.each(row, function(key, value) {
		html.push('<p><b>' + key + ':</b> ' + value + '</p>')
	})
	return html.join('')
}

/* -------------------------------------------------------------------------- */

/*                         form                          					  */

/* -------------------------------------------------------------------------- */

function settingForm(data, $form) {
  var obj = $('input, textarea, select', $form);
  var setting_val, fld_type, check_val, obj_name;

  $.each(obj, function(i, o) {
    obj_name = o.name;
    obj_name = obj_name.replace('WE_', ''); //webeditor

    setting_val = formSearchVal(data, obj_name);

    fld_type = o.type;
    if (fld_type == 'checkbox') {
      if (setting_val != null && setting_val != undefined) {
        if (Array.isArray(setting_val)) {
          check_val = setting_val;

          for (var j = 0; j < check_val.length; j++) {
            trimVal = aFnTrim(check_val[j]);

            $('input:checkbox[name=' + o.name + ']', $form)
              .filter('input[value="' + trimVal + '"]')
              .prop('checked', true);
          }
        } else {
          if (setting_val == true || setting_val == false) check_val = String(setting_val);
          else {
            check_val = setting_val.split(',');
          }
          if (setting_val == true || setting_val == false) {
            $('input:checkbox[name=' + o.name + ']', $form)
              .filter('input[value="' + setting_val + '"]')
              .prop('checked', true);
          }
          //$("input:checkbox[name=is_notice]", $("#"+form)).filter("input[value=1]").attr("checked",true);
          for (var j = 0; j < check_val.length; j++) {
            trimVal = aFnTrim(check_val[j]);
            $('input:checkbox[name=' + o.name + ']', $form)
              .filter('input[value="' + trimVal + '"]')
              .prop('checked', true);
          }
        }
      }
    } else if (fld_type == 'radio') {
      $('input:radio[name=' + o.name + ']', $form)
        .filter('input[value=' + setting_val + ']')
        .click();
      $('input:radio[name=' + o.name + ']', $form).prop('checked', false);
      $('input:radio[name=' + o.name + ']', $form)
        .filter('input[value=' + setting_val + ']')
        .prop('checked', true);
    } else if (fld_type == 'select-one') {
      $(o).val(setting_val);
    } else {
      //필드항목에 없는 것은 값을 채우지 않느다.
      if (setting_val != null && setting_val != undefined) $(o).val(setting_val);
    }
  });
};

function formSearchVal(data, field_name) {
  var fieldnm = field_name;
  for (i in data) {
    if (i == field_name) {
      var field_set = eval('data.' + fieldnm);
      return field_set;
    }
  }
};

function formReset(form) {
  var obj = $('input, textarea, select', $(form));
  $.each(obj, function(i, o) {
    if (o.name != '') {
      fld_type = o.type;
      //달력 첫번째로
      if (fld_type == 'checkbox') {
        $('input:checkbox[name=' + o.name + ']').prop('checked', false);
        //$("input:checkbox[name="+o.name+"]", $(form)).attr("checked",false);
      } else if (fld_type == 'radio') {
        $('input:radio[name=' + o.name + ']', $(form)).prop('checked', false);
        $('input:radio[name=' + o.name + ']', $(form))
          .eq(0)
          .prop('checked', true);
        $('input:radio[name=' + o.name + ']', $(form))
          .eq(0)
          .click();
      } else if (fld_type == 'select-one') {
        //$(o).find("option").prop("selected", false);
        $(o)
          .find('option')
          .eq(0)
          .prop('selected', true);
      } else {
        $(o).val('');
      }
    }
  });
};


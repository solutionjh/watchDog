/* -------------------------------------------------------------------------- */
/*                           ajax call config                          		  */
/* -------------------------------------------------------------------------- */

function ajaxCall(param) {  

	var callbackFunc = "callback";  // default callback function 

 	 var ajaxParamObj = {
	     token : $('#csrfToken').val(),   // spring csrf
	     header : $('#csrfHeader').val(), // spring csrf
	     callFunction : null,  // ajax 호출후 callback
		 target : null,        // ajax 호출후 callback function target 
		 method : "GET",
		 url : null,
		 dataType : "json",    // json, text 
		 async : true,
		 isLoading : true,     // loading 
		 data : {}
	 }
  	
		
  	if(param == null){  		
		console.log("param is null !!");
  		return;
  	}  
  	
  	if(param.url != null){  		
		param.url = param.url.replace(/\/\//gi, "/")
  	}  
  	
  	
  	// 각화면에서 callback function 설정시 해당 callback 호출,  미 설정시 default    
	ajaxParamObj.callFunction =  gf_IsNull(param.callbackFunc) 
								 ? eval(callbackFunc)
								 : eval(param.callbackFunc)
	
	$.each(param,function(k, v){
		ajaxParamObj[k] = v;
	});
		
	if(gf_IsNull(param.data)){
		noDataAjax(ajaxParamObj)  // get
	}else{
		dataAjax(ajaxParamObj)	  
	} 
 }  

/* -------------------------------------------------------------------------- */
/*                          NoData Ajax Call config                           */
/* -------------------------------------------------------------------------- */
function noDataAjax(ajaxParamObj)
{
	$.ajax({
		type: ajaxParamObj.method,
		url: ajaxParamObj.url,
		dataType : ajaxParamObj.dataType,
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("Accept", "application/json");
	        xhr.setRequestHeader("Content-Type", "application/json");
	        xhr.setRequestHeader(ajaxParamObj.header, ajaxParamObj.token); //csrf
	    },
		success: function(data){
			var callbacks = $.Callbacks();
			callbacks.add(ajaxParamObj.callFunction);  //호출시 설정한 callback function
			callbacks.fire(ajaxParamObj.target, data); // success data
		},
		error: function (xhr, textStatus, errorThrown) {			
			if (xhr.status == 403 || textStatus == 'parsererror') {  
		       location.href = "/login";
		    } else if (xhr.status == 404){
		    	location.href = "/noPage";
		    } else if (xhr.status == 500){
		    	location.href = "/serverError";
		    }else{
		    	if(!gf_IsNull(xhr.responseText)) alertModal(xhr.responseText);
		    }
		},
		async : ajaxParamObj.async
	});
}

/* -------------------------------------------------------------------------- */
/*                         Data Ajax Call config                          	  */
/* -------------------------------------------------------------------------- */
function dataAjax(ajaxParamObj)
{	
	$.each(ajaxParamObj.data, function(idx,data){
		if(jQuery.type(data) === "string") ajaxParamObj.data[idx] = escapeHtml(data);
	})
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
		    } else if (xhr.status == 404){
		    	location.href = "/noPage";
		    } else if (xhr.status == 500){
		    	location.href = "/serverError";
		    }else{
		    	if(!gf_IsNull(xhr.responseText)) alertModal(xhr.responseText);
		    }
		 },
		async : ajaxParamObj.async
	});
}

// null check
function gf_IsNull(sValue) {
	  if (new String(sValue).valueOf() == "undefined") return true;
	  if (sValue == null) return true;
	  var v_ChkStr = new String(sValue);
	  if (v_ChkStr == null) return true;
	  if (v_ChkStr.toString().length == 0) return true;
	  return false;
}

/* -------------------------------------------------------------------------- */
/*                         특수문자 제거                          				  */
/* -------------------------------------------------------------------------- */
function escapeHtml(text){
  return text.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/\"/g, "&quot;");
};


/* -------------------------------------------------------------------------- */
/*                           bootstrap table options                          */
/* -------------------------------------------------------------------------- */
function bootstrapTableRefresh(target, url ,isPage){

	var pagination = isPage == null ? true : false

	target.bootstrapTable('refreshOptions', {
		theadClasses:'bg-200 text-900',					
		locale: "",
		sortable:true,
		queryParams:true,
		/*detailView:true,*/
		pagination:pagination,	
		paginationPreText:"◀",
  		paginationNextText:"▶",
		url : url,
		formatRecordsPerPage: function(pageNumber){
            return pageNumber ;
        },

        formatShowingRows: function(pageFrom, pageTo, totalRows){
            return  + pageFrom + ' to ' + pageTo + ' of ' + totalRows + ' ';
        },
		responseHandler: responseHandler,
		detailFormatter:detailFormatter,
		onLoadError : function(status) {
			alertModal("[STATUS " + status + "]" )
		}
	})
}

// grid cell style 
function cellStyle(value, row, index, field) {			
	    return {			    	
	      css: {
	        color: '#6666ca',
	        cursor: 'pointer',
	        'text-decoration': 'underline'
	      }
	} 
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


function initFormTooltip($infoForm){
		var obj = $('input, textarea, select', $infoForm);
		$.each(obj, function(idx, node){
			const $label = $(node).prev("span.input-group-text");
			const placeholder = $(node).attr("placeholder");
			if($label){
				$label.attr("data-bs-toggle", "tooltip");
				$label.attr("data-bs-original-title", placeholder);
				$label.attr("data-bs-placement", "top");
				$label.tooltip();
			}
		});
}


function jsonParse(data){
	if(gf_IsNull(data)) return data
	return JSON.parse(data.replace(/&quot;/g, '"'));
}


function commaCount(str){
	return (str.match(/,/g) || []).length; 
}


function getToday(){
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth() + 1;    //1월이 0으로 되기때문에 +1을 함.
    var date = now.getDate();

    //month = month >=10 ? month : "0" + month;
    //date  = date  >= 10 ? date : "0" + date;
     // ""을 빼면 year + month (숫자+숫자) 됨.. ex) 2018 + 12 = 2030이 리턴됨.

    //console.log(""+year + month + date);
    return today = ""+year + month + date; 
}


/* -------------------------------------------------------------------------- */
/*                   page 이동시 공통 form param setting                         */
/*                   이동시 csrf값이 포함 되어야함.                                 */
/*                   header.html                                              */
/* -------------------------------------------------------------------------- */
function movePageWithParam(param, url){
	$("#paramForm").prop('action', url); //  url
	$("#paramData").val(JSON.stringify(param)); // param
	$("#paramForm").submit();
}

// page 이동시 param값이 select box 
function paramValidation(data ,target){
	var cnt = 0;
	$.each(data, function() {
		if(this == target) cnt ++; 
	});	
	return cnt  ==  0 ? false : true;
}


function getCurrentDate()
    {
        var date = new Date();
        var year = date.getFullYear().toString();

        var month = date.getMonth() + 1;
        month = month < 10 ? '0' + month.toString() : month.toString();

        var day = date.getDate();
        day = day < 10 ? '0' + day.toString() : day.toString();

        var hour = date.getHours();
        hour = hour < 10 ? '0' + hour.toString() : hour.toString();

        var minites = date.getMinutes();
        minites = minites < 10 ? '0' + minites.toString() : minites.toString();

        var seconds = date.getSeconds();
        seconds = seconds < 10 ? '0' + seconds.toString() : seconds.toString();
        return year + '-'+ month + '-'+ day+" "+ hour +":" + minites+":"+ seconds;
        
    }

// 화면 하단이동
function goBottom(){
	document.body.scrollIntoView(false);
}


function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('-');
}
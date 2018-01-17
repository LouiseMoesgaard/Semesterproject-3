var xmlParser = new X2JS()

function XMLToJSON(data){
	var data = xmlParser.xml2json(data)
	var returndata = []
	var level1 = Object.keys(data)
	var level2 = Object.keys(data[level1])

	var newobj = {}
	$.each(data[level1][level2], function(k,v){
		if(typeof v == "object"){
			returndata.push(v)
		} else{
			newobj[k] = v	
		}
	})

	if(Object.keys(newobj).length !== 0){
		returndata.push(newobj)
	}

	return returndata
}

function prepareData(json){
	var xml = "<Appointment>"
	for(key in json){
		xml += "<"+key+">"+json[key]+"</"+key+">"
	}
	xml += "</Appointment>"

	return xml
}
function Appointments(cpr){
	this.cpr = cpr
	this.data = ""
	this.appointments = []

	this.getAppointments = function(_callback){
		var that = this
		$.ajax({
			url: "/api/AppointmentController?cpr="+this.cpr,
			success: function(response){
				that.appointments = XMLToJSON(response)
				that.updateCalender()
				if(_callback){
					_callback()
				}
			}
		})
	}

	this.events = function(appointments){
		var events = []
		$.each(appointments, function(k, appointment){
			var event = {}
			event.id = appointment.id
			event.start = appointment.date_time.replace(" ", "T")
			event.allDay = true
			var time = appointment.date_time.split(" ")[1].substr(0,5)
			event.title = appointment.Hospital.name+" "+time
			events.push(event)
		})
		

		return events
	}

	this.updateCalender = function(){
		const that = this
		$('#calender').fullCalendar({
	        locale: "da",
	        dayClick: function(date) {
	        	that.showAddModal(date)
	    	},
	    	eventClick: function(event){
	    		that.showModal(event)
	    	},
	    	'events': that.events(that.appointments)
		})

	}

	this.showModal = function(event){
		var appointment = appointments.getAppointment(event.id)
		console.log(appointment)
		$(".smallTitle[title='place'] span").text(appointment.Hospital.name+", "+appointment.Hospital.address+", "+appointment.Hospital.section)
		$(".smallTitle[title='date'] span").text(appointment.date_time.substr(0,16))
		$(".lightbox, .modal").addClass("show")
	}

	this.getAppointment = function(id){
		var appointment = {}
		$.each(this.appointments, function(k,v){
			if(v.id == id){
				appointment = v
			}
		})
		return appointment
	}

	this.showAddModal = function(date){

		$.ajax({
			url: "/api/HospitalController",
			success: function(response){
				let hospitals = XMLToJSON(response)
				$.each(hospitals, function(k,hospital){
					$("#hospitals").append("<option value='"+hospital.id+"'>"+hospital.name+"</option>")
				})
				$(".addModal .modalBody p + b").text(date.format())
				$("#time").timepicker({
					timeFormat: "G:i",
					minTime: "08:00",
					maxTime: "22:00"
				})
				$("#date").attr("value", date.format())
				$(".lightbox, .addModal").addClass("show")
			}
		})
	}

	this.addAppointment = function(){
		var data = $("#addForm").serializeArray()
		var json = {}
		$.each(data, function(k,v){
			if(v.name == "date"){
				json["date_time"] = v.value
			}
			if(v.name == "time"){
				json["date_time"] = json["date_time"]+" "+v.value+":00"
			}
			if(v.name = "hospitals"){
				json["Hospital_id"] = v.value
			}
		})
		json['cpr'] = this.cpr

		data = prepareData(json)
		$.ajax({
			method: "POST",
			url: "/api/AppointmentController",
			data: data,
			headers: {
				"Content-Type": "text/xml"
			},
			success: function(response){
				appointments.getAppointments(function(){
					$(".lightbox, .modal, .addModal").removeClass("show")
					location.reload();
				})
				
			},
		})
	} 
}


var appointments = new Appointments("1234567890")

$(document).ready(function(){
	appointments.getAppointments()
	$(".lightbox").click(function(){
			$(".lightbox, .modal, .addModal").removeClass("show")
	})
})

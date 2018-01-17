function Treatments(cpr){
	this.cpr = cpr
	this.treatments = []

	this.getTreatments = function(_callback){
		var that = this
		$.ajax({
			url: "/api/TreatmentController?cpr="+this.cpr,
			success: function(response){
				that.treatments = XMLToJSON(response)
				that.renderTable()
				if(_callback){
					_callback()
				}
			}
		})
	}

	this.renderTable = function(){
		tableBody = $("#treatmentTable")
		this.treatments.forEach(function(treatment){
			tableBody.append(
				"<div class='col-12'>"+
					"<div class='col-2'>"+treatment.updated.substr(0,16)+"</div>"+
					"<div class='col-2'>"+treatment.Appointment.date_time.substr(0,16)+"</div>"+
					"<div class='col-2'>"+treatment.Appointment.Hospital.name+"</div>"+
					"<div class='col-2'>"+treatment.Appointment.Hospital.section+"</div>"+
					"<div class='col-2'>"+treatment.treatment_type+"</div>"+
					"<div class='col-2'>"+treatment.diagnosis+"</div>"+
				"</div>"
			)

		})
	}
}

var treantments = new Treatments("1234567890")

$(document).ready(function(){
	treantments.getTreatments()
})
package lk.sliit.dcws;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * Root resource (exposed at "doctors" path)
 */
@Path("doctors/")
@Singleton
public class DoctorResource {

    private ArrayList<Doctor> doctors = new ArrayList<Doctor>();

    /**
     * Method handling HTTP GET requests for all Doctors. All available Doctor objects will be 
     * concatenated together and sent to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDoctors() {
        System.out.println("GET all Doctors (text)");
        String result = "";
        
        if(this.doctors.size() == 0)
            result = "none";
        
        for(Doctor doctor: this.doctors)
        {
            result += "ID = " + doctor.id + ", Name = " + doctor.name + System.getProperty("line.separator");
        }
        return result;
    }

    /**
     * Method handling HTTP GET requests for all Doctors. The returned array of Doctor objects will be sent
     * to the client as "application/json" media type.
     *
     * @return Array of Doctor objects that will be returned as an application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Doctor[] getDoctorsJson() {
        System.out.println("GET all Doctors (JSON)");
        Doctor[] result = new Doctor[1];
        return this.doctors.toArray(result);
    }
	
	/**
     * Method handling HTTP GET requests for a single object.
     */
    @Path("/{id}/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDoctors(@PathParam("id") String id) {
        System.out.println("GET Doctor " + id + " (text)");
        Doctor doctor = this.findDoctor(id);
        if(doctor == null)
            return "None";
        else
            return "ID = " + doctor.id + ", Name = " + doctor.name; 
    }

    /**
     * Method handling HTTP GET requests for a single object.
     */
    @Path("/{id}/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Doctor[] getDoctorsJson(@PathParam("id") String id) {
        System.out.println("GET Doctor " + id + " (JSON)");
        Doctor doctor = this.findDoctor(id);
        if(doctor == null)
            return new Doctor[0]; // return empty
        else
        {
            Doctor[] doctors = new Doctor[1];
            doctors[0] = doctor;

            return doctors;
        }            
    }

    /**
     * Method handling HTTP GET requests for all Doctors of the specified Specialization. 
     * All matching Doctor objects will be concatenated together and sent to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("specialization/{sp}")
    public String getDoctorsBySpecialization(@PathParam("sp") String specialization) {
        System.out.println("GET all Doctors by specialization (text): " + specialization);
        String result = "";
        
        if(this.doctors.size() == 0)
            result = "none";
        
        for(Doctor doctor: this.doctors)
        {
            if(doctor.specialization.toUpperCase().startsWith(specialization.toUpperCase()))
                result += "ID = " + doctor.id + ", Name = " + doctor.name + System.getProperty("line.separator");
        }
        return result;
    }

    /**
     * Method handling HTTP GET requests for all Doctors of the specified Specialization. 
     * All matching Doctor objects will be concatenated together and sent to the client as "application/json" media type.
     *
     * @return Array of Doctor objects that will be returned as an application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("specialization/{sp}")
    public Doctor[] getDoctorsBySpecializationJson(@PathParam("sp") String specialization) {
        System.out.println("GET all Doctors by specialization (JSON): " + specialization);
        ArrayList<Doctor> matches = new ArrayList<Doctor>();
        Doctor[] result = new Doctor[1];

        for(Doctor doctor: this.doctors)
        {
            if(doctor.specialization.toUpperCase().startsWith(specialization.toUpperCase()))
                matches.add(doctor);
        }

        return matches.toArray(result);
    }

    // TODO: implement getDoctorsByLastName() matching path /ichannel/doctor/lastname/{lastname} 
    // for both text and JSON. {lastname} should be a startsWith so that you could search for
    // ichannel/doctor/lastname/jaya and get Jayasekara, Jayaratna, Jayawardena etc.
    // 10% credit.
	
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("lastName/{lastname}")
    public String getDoctorsByLastName(@PathParam("lastname") String lastName) {
        System.out.println("GET all Doctors by lastName (text): " + lastName);
        String result = "";
        
        if(this.doctors.size() == 0)
            result = "none";
        
        for(Doctor doctor: this.doctors)
        {
            if(doctor.lastName.toUpperCase().startsWith(lastName.toUpperCase()))
                result += "ID = " + doctor.id + ", Name = " + doctor.name + System.getProperty("line.separator");
        }
        return result;
    }
	
	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("lastName/{lastname}")
    public Doctor[] getDoctorsByLastNameJson(@PathParam("lastname") String lastName) {
        System.out.println("GET all Doctors by Last Name (JSON): " + lastName);
        ArrayList<Doctor> matches = new ArrayList<Doctor>();
        Doctor[] result = new Doctor[1];

        for(Doctor doctor: this.doctors)
        {
            if(doctor.lastName.toUpperCase().startsWith(lastName.toUpperCase()))
                matches.add(doctor);
        }

        return matches.toArray(result);
    }
	

    // TODO: implement getDoctorsByHospital() matching path /ichannel/doctor/hospital/{id} 
    // for both text and JSON. {id} should be a hospital ID.
    // 5% credit.
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hospital/{id}")
    public String getDoctorsByHospital(@PathParam("id") String id) {
        System.out.println("GET all Doctors by hospital (text): " + id);
        String result = "";
        
        if(this.doctors.size() == 0)
            result = "none";
        
        for(Doctor doctor: this.doctors)
        {	 
			for(String hospital: doctor.hospitals)
            {
			   if(hospital.toUpperCase().contains(id.toUpperCase()))
                result += "ID = " + doctor.id + ", Name = " + doctor.name + System.getProperty("line.separator");
			}
        }
        return result;
    }
	
	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("hospital/{id}")
    public Doctor[] getDoctorsByHospitalJson(@PathParam("id") String hospital) {
        System.out.println("GET all Doctors by hospital (JSON): " + hospital);
        ArrayList<Doctor> matches = new ArrayList<Doctor>();
        Doctor[] result = new Doctor[1];

        for(Doctor doctor: this.doctors)
        {
                matches.add(doctor);
        }

        return matches.toArray(result);
    }
	

    // TODO: implement createDoctor() for the HTTP POST action - 5% credit
	@POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDoctor(Doctor doctor) { 
        doctor.id = this.getNextDoctorId();
        
        String message = "POST Doctor: " + doctor.name + " with new ID: " +doctor.id + " last name: " + doctor.lastName + "doctor specialization: " + doctor.specialization ;
        System.out.println(message);

        this.doctors.add(doctor);
        return Response.status(201).entity(message).build();
    }

	

    // TODO: implement updateDoctor() for the HTTP PUT action - 5% credit 
	@Path("/{id}/")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(Doctor doctor, @PathParam("id") String id) {
        System.out.println("PUT Doctor: " + id + " with " + doctor.name);
        Doctor stored = this.findDoctor(id);
        if(stored != null)
        {
            String oldName = stored.name;
            stored.name = doctor.name;
            String message = oldName + " renamed to " + doctor.name;
            System.out.println(message);
            return Response.status(200).entity(message).build();
        }
        else
        {
            return Response.status(404).entity(doctor.id + " is not found. Use PUT with a correct ID to modify or use POST to create new entry.").build();
        }
    }

    // TODO: implement deleteDoctor() for the HTTP DELETE action - 5% credit
	@Path("/{id}/")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteDoctor(@PathParam("id") String id) {
        System.out.println("DELETE Doctor: " + id);
        Doctor doctor = this.findDoctor(id);
        if(doctor != null)
        {
            this.doctors.remove(doctor);
            String message = "Deleted Doctor " + doctor.name;
            System.out.println(message);
            return Response.status(200).entity(message).build();
        }
        else
        {
            return Response.status(404).entity(id + " is not found. Use DELETE with a correct ID to delete.").build();     
        }
    }

	
    private Doctor findDoctor(String id)
    {
        for(Doctor doctor : this.doctors)
        {
            if(id.equalsIgnoreCase(doctor.id))
            {
                return doctor; 
            }
        }
        return null;
    }

    private String getNextDoctorId()
    {
        DecimalFormat formatter = new DecimalFormat("doc000"); // we generate ID numbers and send it through this formatter to generate ID's

        int test = this.doctors.size() + 1; // start with 1 when we're empty, or with the most likely next value in sequence
        
        // loop until we find the next unused ID to return
        while(true)
        {
            String testId = formatter.format(test);
            if(this.findDoctor(testId) == null)
                return testId; // this ID is not in use so it's available!
            else
                test++; // it's in use, test the next value
        }
    }

}

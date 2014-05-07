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
 * Root resource (exposed at "hospitals" path)
 */
@Path("hospitals/")
@Singleton
public class HospitalResource {

    private ArrayList<Hospital> hospitals = new ArrayList<Hospital>();

    /**
     * Method handling HTTP GET requests for all objects. All available objects will be 
     * concatenated together and sent to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHospitals() {
        System.out.println("GET all Hospitals (text)");
        String result = "";
        if(this.hospitals.size() == 0)
            result = "none";
        for(Hospital hospital: this.hospitals)
        {
            result += "ID = " + hospital.id + ", Name = " + hospital.name + System.getProperty("line.separator");
        }
        return result;
    }

    /**
     * Method handling HTTP GET requests for all objects. The returned array object will be sent
     * to the client as "application/json" media type.
     *
     * @return Array of Hospital objects that will be returned as an application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Hospital[] getHospitalsJson() {
        System.out.println("GET all Hospitals (JSON)");
        Hospital[] result = new Hospital[1];
        return this.hospitals.toArray(result);
    }

    /**
     * Method handling HTTP GET requests for a single object. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @Path("/{id}/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHospital(@PathParam("id") String id) {
        System.out.println("GET Hospital " + id + " (text)");
        Hospital hospital = this.findHospital(id);
        if(hospital == null)
            return "None";
        else
            return "ID = " + hospital.id + ", Name = " + hospital.name; 
    }

    /**
     * Method handling HTTP GET requests for a single object. The returned object will be sent
     * to the client as "application/json" media type.
     *
     * @return Hospital object that will be returned as an application/json response.
     */
    @Path("/{id}/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Hospital[] getHospitalJson(@PathParam("id") String id) {
        System.out.println("GET Hospital " + id + " (JSON)");
        Hospital hospital = this.findHospital(id);
        if(hospital == null)
            return new Hospital[0]; // return empty
        else
        {
            Hospital[] hospitals = new Hospital[1];
            hospitals[0] = hospital;

            return hospitals;
        }            
    }

    /**
     * Method handling HTTP POST requests.
     * This can be used to create a new object.
     *
     * @return Response object having the HTTP status code and additional information.
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHospital(Hospital hospital) { 
        hospital.id = this.getNextHospitalId();
        
        String message = "POST Hospital: " + hospital.name + " with new ID: " + hospital.id;
        System.out.println(message);

        this.hospitals.add(hospital);
        return Response.status(201).entity(message).build();
    }

    /**
     * Method handling HTTP PUT requests.
     * This can be used to update an existing object.
     *
     * @return Response object having the HTTP status code and additional information.
     */
    @Path("/{id}/")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHospital(Hospital hospital, @PathParam("id") String id) {
        System.out.println("PUT Hospital: " + id + " with " + hospital.name);
        Hospital stored = this.findHospital(id);
        if(stored != null)
        {
            String oldName = stored.name;
            stored.name = hospital.name;
            String message = oldName + " renamed to " + hospital.name;
            System.out.println(message);
            return Response.status(200).entity(message).build();
        }
        else
        {
            return Response.status(404).entity(hospital.id + " is not found. Use PUT with a correct ID to modify or use POST to create new entry.").build();
        }
    }

    /**
     * Method handling HTTP DELETE requests.
     * This can be used to delete an existing object.
     *
     * @return Response object having the HTTP status code and additional information.
     */
    @Path("/{id}/")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteHospital(@PathParam("id") String id) {
        //return "POST name: " + hospital.name; 
        System.out.println("DELETE Hospital: " + id);
        Hospital hospital = this.findHospital(id);
        if(hospital != null)
        {
            this.hospitals.remove(hospital);
            String message = "Deleted Hospital " + hospital.name;
            System.out.println(message);
            return Response.status(200).entity(message).build();
        }
        else
        {
            return Response.status(404).entity(id + " is not found. Use DELETE with a correct ID to delete.").build();     
        }
    }

    private Hospital findHospital(String id)
    {
        for(Hospital hospital : this.hospitals)
        {
            if(id.equalsIgnoreCase(hospital.id))
            {
                return hospital; 
            }
        }
        return null;
    }

    private String getNextHospitalId()
    {
        DecimalFormat formatter = new DecimalFormat("hos000"); // we generate ID numbers and send it through this formatter to generate ID's

        int test = this.hospitals.size() + 1; // start with 1 when we're empty, or with the most likely next value in sequence
        
        // loop until we find the next unused ID to return
        while(true)
        {
            String testId = formatter.format(test);
            if(this.findHospital(testId) == null)
                return testId; // this ID is not in use so it's available!
            else
                test++; // it's in use, test the next value
        }
    }

}
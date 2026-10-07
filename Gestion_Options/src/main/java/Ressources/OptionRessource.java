package Ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
public class OptionRessource {

    OptionBusiness optBusiness = new OptionBusiness();

    // 1) Création d'une nouvelle option - POST /options
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        if (optBusiness.addOption(option)) {
            return Response.status(Response.Status.OK).entity(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2) Récupération de la liste de toutes les options - GET /options
    // 3) Récupération de la liste des options d’un domaine spécifique - GET /options?domaine=...
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> options;
        if (domaine != null && !domaine.isEmpty()) {
            options = optBusiness.getOptionsByDomaine(domaine);
        } else {
            options = optBusiness.getListeOptions();
        }
        return Response.status(Response.Status.OK).entity(options).build();
    }

    // 6) Récupération d’une option ayant un code donné - GET /options/{code}
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = optBusiness.getOptionByCode(code);
        if (option != null) {
            return Response.status(Response.Status.OK).entity(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4) Suppression d’une option ayant un code spécifique - DELETE /options/{code}
    @DELETE
    @Path("/{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optBusiness.deleteOption(code)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) Modification d’une option ayant un code spécifique - PUT /options/{code}
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option updatedOption) {
        if (optBusiness.updateOption(code, updatedOption)) {
            return Response.status(Response.Status.OK).entity(updatedOption).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

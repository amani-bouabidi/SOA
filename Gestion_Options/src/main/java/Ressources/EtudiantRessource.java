package Ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiants")
public class EtudiantRessource {

    EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    OptionBusiness optionBusiness = new OptionBusiness();

    // 1) Création d’un nouvel étudiant - POST /etudiants
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Response.Status.OK).entity(etudiant).build();
        }
        // 404 si l’option indiquée n’existe pas
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2) Récupération de la liste de tous les étudiants - GET /etudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        List<Etudiant> etudiants = etudiantBusiness.getAllEtudiants();
        return Response.status(Response.Status.OK).entity(etudiants).build();
    }

    // 3) Récupération d’un étudiant ayant un identifiant spécifique - GET /etudiants/{id}
    @GET
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant != null) {
            return Response.status(Response.Status.OK).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4) Suppression d’un étudiant ayant un identifiant spécifique - DELETE /etudiants/{id}
    @DELETE
    @Path("/{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) Modification d’un étudiant ayant un identifiant spécifique - PUT /etudiants/{id}
    @PUT
    @Path("/{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant updatedEtudiant) {
        if (etudiantBusiness.updateEtudiant(identifiant, updatedEtudiant)) {
            return Response.status(Response.Status.OK).entity(updatedEtudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 6) Récupération de la liste des étudiants inscrits à une option donnée
    //    GET /etudiants/option?codeOption=1  → réponse en XML
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Etudiant> etudiants = etudiantBusiness.getEtudiantsByOption(option);
        EtudiantList etudiantList = new EtudiantList(etudiants);
        return Response.status(Response.Status.OK).entity(etudiantList).build();
    }
}

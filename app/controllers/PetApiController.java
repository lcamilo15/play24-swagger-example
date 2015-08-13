package controllers;

import com.wordnik.swagger.annotations.*;
import models.Pet;
import play.Logger;
import play.data.Form;
import play.mvc.Result;

import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Api(value = "/pet", description = "Operations about pets")
public class PetApiController extends BaseApiController {
    static PetData petData = new PetData();
    private static final Form<Pet> petForm = Form.form(Pet.class);

    @ApiOperation(value = "Find pet by ID",
            notes = "Returns a pet when ID < 10.  ID > 10 or nonintegers will simulate API error conditions",
            response = Pet.class, httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Pet not found")})
    public Result getPetById(
            @ApiParam(value = "ID of pet that needs to be fetched", allowableValues = "range[1,5]", required = true) @PathParam("id") String petId) {
        return JsonResponse(petData.getPetbyId(Long.parseLong(petId)));
    }

    @ApiOperation(value = "Add a new pet to the store", httpMethod = "POST")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Pet object that needs to be added to the store", required = false,  dataType = "Pet", paramType = "body"),
    })
    @ApiResponses(value = {@ApiResponse(code = 405, message = "Invalid input")})
    public Result addPet() {
        Pet pet = null;
        Object o = request().body().asJson();
        try {
            pet = (Pet) BaseApiController.mapper.readValue(o.toString(), Pet.class);
            petData.addPet(pet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResponse(pet);
    }

    @ApiOperation(value = "Update an existing pet", httpMethod = "PUT")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Pet not found"),
            @ApiResponse(code = 405, message = "Validation exception")})
    @ApiImplicitParams({@ApiImplicitParam(value = "Pet object that needs to be updated in the store", required = true, dataType = "Pet", paramType = "body")})
    public Result updatePet() {
        Object o = request().body().asJson();
        try {
            Pet pet = (Pet) BaseApiController.mapper.readValue(o.toString(), Pet.class);
            petData.addPet(pet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResponse("SUCCESS");
    }

    @ApiOperation(value = "Finds Pets by status",
            notes = "Multiple status values can be provided with comma seperated strings",
            response = Pet.class,
            responseContainer = "List", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid status value")})

    public Result findPetsByStatus(
            @ApiParam(value = "Status values that need to be considered for filter", required = true, defaultValue = "available", allowableValues = "available,pending,sold", allowMultiple = true) @QueryParam("status") String status) {
        return JsonResponse(petData.findPetByStatus(status));
    }

    @ApiOperation(value = "Finds Pets by tags",
            notes = "Muliple tags can be provided with comma seperated strings. Use tag1, tag2, tag3 for testing.",
            response = Pet.class,
            responseContainer = "List", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid tag value")})
    @Deprecated
    public Result findPetsByTags(
            @ApiParam(value = "Tags to filter by", required = true, allowMultiple = true) @QueryParam("tags") String tags) {
        return JsonResponse(petData.findPetByTags(tags));
    }



    @ApiOperation(value = "Updates a pet in the store with form data",
            consumes = MediaType.APPLICATION_FORM_URLENCODED)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Updated name of the pet", required = false, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "status", value = "Updated status of the pet", required = true, dataType = "long", paramType = "form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input")}
    )
    public Result updatePetWithForm (
            @ApiParam(value = "ID of pet that needs to be fetched", allowableValues = "range[1,5]", required = true) @PathParam("id")
            String petId
    ) {
        Logger.debug(petId);
        Form<Pet> form = petForm.bindFromRequest();

        Pet pet = petData.getPetbyId(Long.parseLong(petId));

        if (pet == null) {
            petData.addPet(pet);
        } else {
            Pet petValues = form.get();

            if (petValues.getName() != null && !petValues.getName().isEmpty())
                pet.setName(petValues.getName());
            if (petValues.getStatus() != null && !petValues.getStatus().isEmpty())
                pet.setStatus(petValues.getStatus());
        }

        return JsonResponse(petData);

    }
}
package ibf2021.assessment.csf.server.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/* Write your request hander in this file */
@RestController
@RequestMapping(path="/api/recipe/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
public class RecipeRestController{
   Recipe singleRecipe = new Recipe(){};
    List<String> ingredients = new ArrayList<String>();
    @Autowired
    private RecipeService recpSvc;

    @GetMapping
    public ResponseEntity<String> getSingleRecipe(@PathVariable String id){
        
        Optional<Recipe> opt=recpSvc.getRecipeById(id);

    
        
        if(opt.isPresent()){
            singleRecipe=opt.get();
            ingredients = singleRecipe.getIngredients();//list<string>
        }  else{
            JsonObject jo = Json.createObjectBuilder().add("h", "ERRORMESAGEOFCHOIECE").build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jo.toString());
        }    
          
        JsonArrayBuilder arrbuild = Json.createArrayBuilder();


        for( int i = 0; i<ingredients.size();i++){
            
            arrbuild.add(ingredients.get(i));
        }
           
        JsonObjectBuilder build = Json.createObjectBuilder();
        JsonObject toAngular = build.add("title", singleRecipe.getTitle())
        .add("image", singleRecipe.getImage())
        .add("instructions", singleRecipe.getInstruction())
        .add("ingredients", arrbuild).build();


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(toAngular.toString());
    }
    
    @PostMapping(path="/api/addrecipe", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postSingleRecipe(@RequestBody String payload) {
		try {
            System.out.println(payload);
			// this.recpSvc.addRecipe();
			// JsonObject ok = Json.createObjectBuilder()
			// 	.add("message", "Update on %s".formatted(new Date()))
			// 	.build();
			return ResponseEntity.status(HttpStatus.CREATED).body("hi");
		} catch (Exception ex) {
			JsonObject err = Json.createObjectBuilder()
				.add("error", ex.getMessage())
				.build();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
		}
	}
    
}
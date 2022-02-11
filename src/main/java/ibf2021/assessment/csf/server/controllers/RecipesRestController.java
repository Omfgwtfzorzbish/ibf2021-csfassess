package ibf2021.assessment.csf.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

/* Write your request hander in this file */
@RestController
@RequestMapping(path="/api/getrecipes", produces=MediaType.APPLICATION_JSON_VALUE)
public class RecipesRestController{
    
    List<Recipe> recipeList = new ArrayList<Recipe>();
    @Autowired
    private RecipeService recpSvc;

    @GetMapping
    public ResponseEntity<String> getAllRecipes(){
        recipeList=recpSvc.getAllRecipes();
            JsonArrayBuilder ab =Json.createArrayBuilder();
            JsonObjectBuilder rb = Json.createObjectBuilder();
                for( int i = 0; i<recipeList.size();i++){
                    rb.add("title",recipeList.get(i).getTitle());
                   // ab.add(recipeList.get(i).getId(),rb);
                    rb.add("id",recipeList.get(i).getId());
                    
                    ab.add(rb);
                }
          
            

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ab.build().toString());
    }
    

}
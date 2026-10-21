package kevinCHSE.GroceryStore.Spring.controller.LoginUser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kevinCHSE.GroceryStore.Spring.entities.Login.Roles;
import kevinCHSE.GroceryStore.Spring.interfaces.roleServiceInterface;
import kevinCHSE.GroceryStore.Spring.service.LoginService.rolesService;


@RestController 
@RequestMapping("/apiRoles")
public class RolesController {

    private final roleServiceInterface service;

    public RolesController(rolesService service) {
        this.service = service;
    }
    

    @PostMapping("/saveRole")
    public ResponseEntity<?> saveRole(@Valid @RequestBody Roles role, BindingResult result) {
         if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.save(role));
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors=new HashMap<>();

        result.getFieldErrors().forEach(error->{
            errors.put(error.getField(), "El campo "+error.getField()+" "+error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
    
}

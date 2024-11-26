package projeto.com.br.form.processing.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.com.br.form.processing.api.dto.userDTO.UserInputDTO;
import projeto.com.br.form.processing.api.dto.userDTO.UserOutDTO;
import projeto.com.br.form.processing.assembler.UserAssembler;
import projeto.com.br.form.processing.domain.service.UserService;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserAssembler userAssembler;
    private UserService userService;

    @PutMapping("/{userID}")
    public ResponseEntity<UserOutDTO> atualizar(@PathVariable final  @NotNull UUID userID,
                                                @RequestBody final @Valid UserInputDTO userInputDTO){
        return ResponseEntity
                .ok(userAssembler.paraUserDTO(userService.atualizar(userAssembler.paraUser(userInputDTO), userID)));
    }

    @GetMapping("/list")
    public List<UserOutDTO> list(){
        return userAssembler.paraColecaoUserDTO(userService.listar());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletar(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        userService.deletar(token);
        return ResponseEntity.noContent().build();
    }

}

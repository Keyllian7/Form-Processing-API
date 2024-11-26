package projeto.com.br.form.processing.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.com.br.form.processing.api.dto.userDTO.UserInputDTO;
import projeto.com.br.form.processing.api.dto.userDTO.UserOutDTO;
import projeto.com.br.form.processing.assembler.UserAssembler;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.service.UserService;
import projeto.com.br.form.processing.infra.security.TokenService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserAssembler userAssembler;
    private UserService userService;
    private TokenService tokenService;

    @PutMapping("/update")
    public ResponseEntity<UserOutDTO> atualizar(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid UserInputDTO userInputDTO) {
        String token = authorizationHeader.replace("Bearer ", "");
        String authenticatedEmail = tokenService.validarToken(token);

        if (authenticatedEmail.isEmpty()) {
            throw new SecurityException("Token inválido ou expirado.");
        }

        User updatedUser = userService.atualizar(userAssembler.paraUser(userInputDTO), authenticatedEmail);
        return ResponseEntity.ok(userAssembler.paraUserDTO(updatedUser));
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

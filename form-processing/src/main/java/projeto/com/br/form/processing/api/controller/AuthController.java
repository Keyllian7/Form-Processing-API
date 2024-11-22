package projeto.com.br.form.processing.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import projeto.com.br.form.processing.api.dto.userDTO.UserInputDTO;
import projeto.com.br.form.processing.api.dto.userDTO.UserLoginDTO;
import projeto.com.br.form.processing.api.dto.userDTO.UserOutDTO;
import projeto.com.br.form.processing.assembler.UserAssembler;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.service.UserService;
import projeto.com.br.form.processing.infra.security.ResponseToken;
import projeto.com.br.form.processing.infra.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody final @Valid UserLoginDTO data){
        var userPassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = authenticationManager.authenticate(userPassword);
        var token = tokenService.gerarToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new ResponseToken(token));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutDTO register(@RequestBody @Valid UserInputDTO userInputDTO) {
        User userRegister = userAssembler.paraUser(userInputDTO);
        User registerUser = userService.register(userRegister);
        return userAssembler.paraUserDTO(registerUser);
    }

}

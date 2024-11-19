package projeto.com.br.form.processing.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.com.br.form.processing.api.dto.user.AuthenticationDTO;
import projeto.com.br.form.processing.api.dto.user.LoginResponseDTO;
import projeto.com.br.form.processing.api.dto.user.RegisterDTO;
import projeto.com.br.form.processing.domain.model.user.*;
import projeto.com.br.form.processing.domain.repository.UserRepository;
import projeto.com.br.form.processing.domain.service.TokenService;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Autenticação falhou. Verifique suas credenciais e tente novamente. " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        try {
            if (this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().body(Map.of("Error","O e-mail fornecido já está em uso. "));

            if (data.name() == null || data.email() == null || data.password() == null) {
                return ResponseEntity.badRequest().body("Todos os campos obrigatórios devem ser preenchidos.");
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User newUser = new User(data.name(), data.email(), encryptedPassword, UserRole.USER);
            this.userRepository.save(newUser);

            return ResponseEntity.ok("Usuário registrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocorreu um erro ao tentar registrar seu usuário " + e.getMessage());
        }

    }
}

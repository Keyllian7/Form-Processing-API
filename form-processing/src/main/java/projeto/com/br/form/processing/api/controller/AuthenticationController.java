package projeto.com.br.form.processing.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.com.br.form.processing.domain.model.user.AuthenticationDTO;
import projeto.com.br.form.processing.domain.model.user.LoginResponseDTO;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.repository.UserRepository;
import projeto.com.br.form.processing.domain.model.user.RegisterDTO;
import projeto.com.br.form.processing.infra.security.TokenService;
import jakarta.validation.Valid;

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
            return ResponseEntity.status(401).body("Autenticação falhou" + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        try {
            if (this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().body("Email já cadastrado!");

            if (data.name() == null || data.email() == null || data.password() == null || data.role() == null) {
                return ResponseEntity.badRequest().body("Todos os campos obrigatórios devem ser preenchidos.");
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User newUser = new User(data.name(), data.email(), encryptedPassword, data.role());
            this.userRepository.save(newUser);

            return ResponseEntity.ok("Usuario registrado com sucessso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao registar usuário" + e.getMessage());
        }

    }
}
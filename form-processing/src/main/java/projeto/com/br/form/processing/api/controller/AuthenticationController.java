package projeto.com.br.form.processing.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.com.br.form.processing.api.dto.user.AuthenticationDTO;
import projeto.com.br.form.processing.api.dto.user.LoginResponseDTO;
import projeto.com.br.form.processing.api.dto.user.RegisterDTO;
import projeto.com.br.form.processing.domain.service.AuthenticationService;

import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            LoginResponseDTO response = authenticationService.login(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Autenticação falhou. Verifique suas credenciais e tente novamente. " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        try {
            Map<String, String> response = authenticationService.register(data);
            if (response.containsKey("Error")) {
                return ResponseEntity.badRequest().body(response);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocorreu um erro ao tentar registrar seu usuário " + e.getMessage());
        }
    }
}

package projeto.com.br.form.processing.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.repository.FormRepository;
import projeto.com.br.form.processing.domain.repository.UserRepository;
import projeto.com.br.form.processing.domain.service.TokenService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    FormRepository formRepository;

    @GetMapping("/list/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        User existingUser = existingUserOpt.get();

        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(updatedUser.getPassword());
            existingUser.setPassword(encryptedPassword);
        }

        userRepository.save(existingUser);
        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id, @RequestHeader("Authorization") String token) {
        try {
            String email = tokenService.validadeToken(token.replace("Bearer ", ""));
            if (email.isEmpty()) {
                return ResponseEntity.status(401).body("Token inválido ou expirado.");
            }
            User authenticatedUser = (User) userRepository.findByEmail(email);
            if (authenticatedUser == null) {
                return ResponseEntity.status(404).body("Usuário autenticado não encontrado.");
            }
            if (!authenticatedUser.getId().equals(id)) {
                return ResponseEntity.status(403).body("Você não tem permissão para excluir esta conta.");
            }

            List<Form> userForms = formRepository.findByEmissorId(id);
            if (userForms != null && !userForms.isEmpty()) {
                return ResponseEntity.status(400).body("Não é possível excluir a conta, pois existem formulários associados a ela.");
            }
            userRepository.delete(authenticatedUser);
            return ResponseEntity.ok("Conta excluída com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar a solicitação: " + e.getMessage());
        }
    }

    @GetMapping("/forms/{id}")
    public ResponseEntity listFormsByUser(@PathVariable String id) {
        List<Form> forms = formRepository.findByEmissorId(id);
        if (forms.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum formulário encontrado para o usuário.");
        }
        return ResponseEntity.ok(forms);
    }

}

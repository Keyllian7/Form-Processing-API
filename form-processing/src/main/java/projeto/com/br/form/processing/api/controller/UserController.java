package projeto.com.br.form.processing.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        String response = userService.updateUser(id, updatedUser);
        if (response.equals("Usuário atualizado com sucesso.")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String response = userService.deleteUser(id, token);
        if (response.equals("Conta excluída com sucesso.")) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(response.contains("Erro") ? 500 : 400).body(response);
    }

    @GetMapping("/forms/{id}")
    public ResponseEntity<?> listFormsByUser(@PathVariable String id) {
        List<Form> forms = userService.listFormsByUser(id);
        if (forms.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum formulário encontrado para o usuário.");
        }
        return ResponseEntity.ok(forms);
    }
}

package projeto.com.br.form.processing.api.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.api.dto.form.FormRequestDTO;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.repository.FormRepository;
import projeto.com.br.form.processing.domain.repository.UserRepository;
import projeto.com.br.form.processing.domain.service.TokenService;

@RestController
@RequestMapping("/form")
public class FormController {

    @Autowired
    FormRepository formRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity postForm(@RequestBody @Valid FormRequestDTO body, @RequestHeader("Authorization") String token) {
        String email = tokenService.validadeToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            return ResponseEntity.status(401).body("Token inválido ou expirado.");
        }

        User user = (User) userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        Form newForm = new Form(body, user);
        this.formRepository.save(newForm);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/user/{id}")
    public ResponseEntity updateForm(
        @PathVariable String id,
        @RequestBody @Valid FormRequestDTO body,
        @RequestHeader("Authorization") String token) {
    
        String email = tokenService.validadeToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
        return ResponseEntity.status(401).body("Token inválido ou expirado.");
        }

        User user = (User) userRepository.findByEmail(email);

        if (user == null) {
        return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        Form form = formRepository.findById(id).orElse(null);

        if (form == null) {
        return ResponseEntity.status(404).body("Formulário não encontrado.");
        }

        form.setMotivo(body.motivo());
        form.setSetor(body.setor());
        form.setMensagem(body.mensagem());

        formRepository.save(form);

        return ResponseEntity.ok("Formulário atualizado com sucesso.");
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity deleteForm(
        @PathVariable String id,
        @RequestHeader("Authorization") String token) {

    String email = tokenService.validadeToken(token.replace("Bearer ", ""));
    if (email.isEmpty()) {
        return ResponseEntity.status(401).body("Token inválido ou expirado.");
    }

    User user = (User)userRepository.findByEmail(email);

    if (user == null) {
        return ResponseEntity.status(404).body("Usuário não encontrado.");
    }

    Form form = formRepository.findById(id).orElse(null);

    if (form == null) {
        return ResponseEntity.status(404).body("Formulário não encontrado.");
    }

    formRepository.delete(form);

    return ResponseEntity.ok("Formulário excluído com sucesso.");
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity getFormById(
        @PathVariable String id,
        @RequestHeader("Authorization") String token) {

    String email = tokenService.validadeToken(token.replace("Bearer ", ""));
    if (email.isEmpty()) {
        return ResponseEntity.status(401).body("Token inválido ou expirado.");
    }

    User user = (User)userRepository.findByEmail(email);

    if (user == null) {
        return ResponseEntity.status(404).body("Usuário não encontrado.");
    }

    Form form = formRepository.findById(id).orElse(null);

    if (form == null) {
        return ResponseEntity.status(404).body("Formulário não encontrado.");
    }

    return ResponseEntity.ok(form);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<Form>> getAllForms(
        @RequestHeader("Authorization") String token) {

    // Validar o token e obter o email do usuário autenticado
    String email = tokenService.validadeToken(token.replace("Bearer ", ""));
    if (email.isEmpty()) {
        return ResponseEntity.status(401).body(null); // Token inválido ou expirado
    }

    User user = (User)userRepository.findByEmail(email);

    if (user == null) {
        return ResponseEntity.status(404).body(null); // Usuário não encontrado
    }

    List<Form> allForms = formRepository.findAll();
    return ResponseEntity.ok(allForms);
    }


}

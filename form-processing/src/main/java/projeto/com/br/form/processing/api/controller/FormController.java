package projeto.com.br.form.processing.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.form.FormRequestDTO;
import projeto.com.br.form.processing.domain.repository.FormRepository;

@RestController
@RequestMapping("/form")
public class FormController {

    @Autowired
    FormRepository formRepository;

    @PostMapping("/create")
    public ResponseEntity postForm(@RequestBody @Valid FormRequestDTO body){

        Form newForm = new Form(body);
        this.formRepository.save(newForm);
        return ResponseEntity.ok().build();
    }

}

package projeto.com.br.form.processing.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projeto.com.br.form.processing.api.dto.formDTO.FormInputDTO;
import projeto.com.br.form.processing.api.dto.formDTO.FormOutDTO;
import projeto.com.br.form.processing.api.dto.formDTO.StatusMensagemDTO;
import projeto.com.br.form.processing.assembler.FormAssembler;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.service.FormService;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/form")
@CrossOrigin(origins = "http://localhost:3000")
public class FormController {

    private FormAssembler formAssembler;
    private FormService formService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FormOutDTO> criarFormulario(@RequestBody final @Valid FormInputDTO formInputDTO, @AuthenticationPrincipal User user) {
        Form form = formAssembler.paraForm(formInputDTO);
        Form criado = formService.registrar(form, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(formAssembler.paraFormDTO(criado));
    }

    @GetMapping("/list")
    public List<FormOutDTO> listar() {
        return formAssembler.paraColecaoFormDTO(formService.listar());
    }

    @DeleteMapping("/delete/{formID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable final Long formID) {
        formService.deletar(formID);
    }

    @PatchMapping("/update/{formID}")
    public FormOutDTO atualizarStatusMensagem(
            @PathVariable Long formID,
            @RequestBody StatusMensagemDTO statusMensagemDTO) {
        return formAssembler.paraFormDTO(formService.atualizarFormulario(
                formID,
                statusMensagemDTO.getStatus(),
                statusMensagemDTO.getMensagem()
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<List<FormOutDTO>> listarMeusFormularios(@AuthenticationPrincipal User user) {
        List<Form> formularios = formService.listarPorUsuario(user);
        return ResponseEntity.ok(formAssembler.paraColecaoFormDTO(formularios));
    }

}

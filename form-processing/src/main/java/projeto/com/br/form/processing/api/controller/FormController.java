package projeto.com.br.form.processing.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.com.br.form.processing.api.dto.formDTO.FormInputDTO;
import projeto.com.br.form.processing.api.dto.formDTO.FormOutDTO;
import projeto.com.br.form.processing.api.dto.formDTO.StatusMensagemDTO;
import projeto.com.br.form.processing.assembler.FormAssembler;
import projeto.com.br.form.processing.domain.service.FormService;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/form")
public class FormController {

    private FormAssembler formAssembler;
    private FormService formService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public FormOutDTO criar(@RequestBody final @Valid FormInputDTO formInputDTO) {
        return formAssembler.paraFormDTO(formService.registrar(formAssembler.paraForm(formInputDTO)));
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
}

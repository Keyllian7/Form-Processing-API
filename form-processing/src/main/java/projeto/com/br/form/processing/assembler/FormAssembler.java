package projeto.com.br.form.processing.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import projeto.com.br.form.processing.api.dto.formDTO.FormInputDTO;
import projeto.com.br.form.processing.api.dto.formDTO.FormOutDTO;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.form.Status;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class FormAssembler {

    private ModelMapper modelMapper;

    public Form paraForm(final FormInputDTO formInputDTO) {
        Form form = modelMapper.map(formInputDTO, Form.class);
        form.setStatus(formInputDTO.getStatus() != null ? formInputDTO.getStatus() : Status.PENDENTE);
        form.setMensagem(formInputDTO.getMensagem() != null ? formInputDTO.getMensagem() : "Aguarde! Estamos fazendo a análise...");
        return form;
    }

    public FormOutDTO paraFormDTO(final Form form) {
        return modelMapper.map(form, FormOutDTO.class);
    }

    public List<FormOutDTO> paraColecaoFormDTO(final List<Form> forms) {
        return forms.stream().map(this::paraFormDTO).collect(Collectors.toList());
    }

}

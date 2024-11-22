package projeto.com.br.form.processing.api.dto.formDTO;

import lombok.Getter;
import lombok.Setter;
import projeto.com.br.form.processing.domain.model.form.Status;

@Getter
@Setter
public class FormOutDTO extends FormBaseDTO{

    private String motivo;
    private String setor;
    private String problema;
    private Status status;
    private String mensagem;

}

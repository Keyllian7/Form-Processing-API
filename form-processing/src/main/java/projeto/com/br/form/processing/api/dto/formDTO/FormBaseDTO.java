package projeto.com.br.form.processing.api.dto.formDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormBaseDTO {

    @NotNull
    private Long id;

}

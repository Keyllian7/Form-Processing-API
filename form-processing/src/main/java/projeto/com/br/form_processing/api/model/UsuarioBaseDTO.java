package projeto.com.br.form_processing.api.model;

import org.antlr.v4.runtime.misc.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioBaseDTO {

    @NotNull
    private long id;

}

package projeto.com.br.form.processing.api.dto.form;

import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.user.User;

import java.util.Date;

public record FormResponseDTO (String id, String motivo, String mensagem, Date dataCriacao, User emissor) {
    public FormResponseDTO(Form form) {this(form.getId(), form.getMotivo(), form.getMensagem(), form.getDataCriacao(), form.getEmissor());}
}

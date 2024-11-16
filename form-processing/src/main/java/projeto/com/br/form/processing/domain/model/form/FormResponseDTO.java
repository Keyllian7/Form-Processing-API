package projeto.com.br.form.processing.domain.model.form;

import java.util.Date;

public record FormResponseDTO (String id, String motivo, String mensagem, Date dataCriacao) {
    public FormResponseDTO(Form form) {this(form.getId(), form.getMotivo(), form.getMensagem(), form.getDataCriacao());}
}

package projeto.com.br.form.processing.domain.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.form.Status;
import projeto.com.br.form.processing.domain.repository.FormRepository;
import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class FormService {

    private FormRepository formRepository;

    @Transactional
    public Form registrar(final Form form) {
        form.setStatus(Status.PENDENTE);
        form.setMensagem("Aguarde! Estamos fazendo a análise...");
        return formRepository.save(form);
    }

    @Transactional
    public List<Form> listar() {
        return formRepository.buscarAtivos();
    }

    @Transactional
    public void deletar(final Long formId) {
        if (formRepository.existsById(formId)) {
            formRepository.marcarComoExcluido(formId, OffsetDateTime.now());
        } else {
            throw new EntityNotFoundException("Formulário não encontrado para exclusão.");
        }
    }

    @Transactional
    public Form atualizarFormulario(Long formID, Status status, String mensagem) {
        Form form = formRepository.findById(formID)
                .orElseThrow(() -> new EntityNotFoundException("Formulário não encontrado."));

        form.setStatus(status);
        form.setMensagem(mensagem);
        return formRepository.save(form);
    }

}

package projeto.com.br.form.processing.domain.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.form.Status;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.repository.FormRepository;
import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class FormService {

    private FormRepository formRepository;
    private final EmailService emailService;

    @Transactional
    public Form registrar(final Form form, User user) {
        form.setUser(user);
        form.setStatus(Status.PENDENTE);
        form.setMensagem("Aguarde! Estamos fazendo a análise...");
        Form savedForm = formRepository.save(form);

        emailService.enviarEmailConfirmacao(
                user.getEmail(),
                "Confirmação de Recebimento",
                String.format("Olá, %s! Seu formulário foi recebido e está em análise.", user.getNome())
        );

        return savedForm;
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

        Form formularioAtualizado = formRepository.save(form);

        String email = form.getUser().getEmail();
        switch (status) {
            case ANDAMENTO:
                emailService.enviarEmailConfirmacao(
                        email,
                        "Seu formulário está em andamento.",
                        String.format("Olá, %s! Estamos processando seu formulário. Em breve, você receberá mais informações.", form.getUser().getNome())
                );
                break;
            case RESOLVIDO:
                emailService.enviarEmailConfirmacao(
                        email,
                        "Seu formulário foi resolvido.",
                        String.format("Olá, %s. Seu formulário foi resolvido com sucesso! Detalhes: %s", form.getUser().getNome(), mensagem)
                );
                break;
            case PENDENTE:
                emailService.enviarEmailConfirmacao(
                        email,
                        "Seu formulário está pendente.",
                        String.format("Olá, %s. Seu formulário foi recebido e está aguardando análise. Por favor, aguarde nossa equipe.", form.getUser().getNome())
                );
                break;
        }

        return formularioAtualizado;
    }

    @Transactional
    public List<Form> listarPorUsuario(User user) {
        return formRepository.findByUser(user);
    }


}

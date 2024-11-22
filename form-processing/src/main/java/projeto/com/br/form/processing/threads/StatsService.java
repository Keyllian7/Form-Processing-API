package projeto.com.br.form.processing.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import projeto.com.br.form.processing.domain.service.FormService;
import projeto.com.br.form.processing.domain.service.UserService;

@Service
public class StatsService {

    @Autowired
    private UserService userService;

    @Autowired
    private FormService formService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 5000) // Atualiza a cada 5s
    public void sendStats() {
        var stats = new StatsDTO(
                userService.getTotalUsers(),
                formService.getTotalForms(),
                formService.getPendingForms()
        );
        messagingTemplate.convertAndSend("/topic/stats", stats);
    }
}


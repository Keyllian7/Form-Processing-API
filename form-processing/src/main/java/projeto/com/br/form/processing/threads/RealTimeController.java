package projeto.com.br.form.processing.threads;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RealTimeController {

    @Autowired
    private RealTimeService realTimeService;

    @GetMapping("/api/real-time")
    public RealTimeData getRealTimeData() {
        RealTimeData data = new RealTimeData(
                realTimeService.getTotalUsers(),
                realTimeService.getTotalForms(),
                realTimeService.getPendingForms(),
                realTimeService.getProcessedForms(),
                realTimeService.getActiveUsers(),
                realTimeService.getDeletedForms(),
                realTimeService.getInactiveUsers(),
                realTimeService.getAverageFormsPerUser(),
                realTimeService.getAverageProcessingTime(),
                realTimeService.getCompletionRate()
        );
        System.out.println("Real-time data: " + data);
        return data;
    }

    @Getter
    @Setter
    public static class RealTimeData {
        private long totalUsers;
        private long totalForms;
        private long pendingForms;
        private long processedForms;
        private long activeUsers;
        private long deletedForms;
        private long inactiveUsers;
        private double averageFormsPerUser;
        private double averageProcessingTime;
        private double completionRate;

        public RealTimeData(long totalUsers, long totalForms, long pendingForms, long processedForms, long activeUsers,
                            long deletedForms, long inactiveUsers, double averageFormsPerUser, double averageProcessingTime, double completionRate) {
            this.totalUsers = totalUsers;
            this.totalForms = totalForms;
            this.pendingForms = pendingForms;
            this.processedForms = processedForms;
            this.activeUsers = activeUsers;
            this.deletedForms = deletedForms;
            this.inactiveUsers = inactiveUsers;
            this.averageFormsPerUser = averageFormsPerUser;
            this.averageProcessingTime = averageProcessingTime;
            this.completionRate = completionRate;
        }
    }
}
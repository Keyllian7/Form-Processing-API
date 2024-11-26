package projeto.com.br.form.processing.threads;

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
                realTimeService.getPendingForms()
        );
        System.out.println("Real-time data: " + data);
        return data;
    }

    public static class RealTimeData {
        private long totalUsers;
        private long totalForms;
        private long pendingForms;

        public long getTotalUsers() {
            return totalUsers;
        }

        public void setTotalUsers(long totalUsers) {
            this.totalUsers = totalUsers;
        }

        public long getTotalForms() {
            return totalForms;
        }

        public void setTotalForms(long totalForms) {
            this.totalForms = totalForms;
        }

        public long getPendingForms() {
            return pendingForms;
        }

        public void setPendingForms(long pendingForms) {
            this.pendingForms = pendingForms;
        }

        public RealTimeData(long totalUsers, long totalForms, long pendingForms) {
            this.totalUsers = totalUsers;
            this.totalForms = totalForms;
            this.pendingForms = pendingForms;
        }
    }
}
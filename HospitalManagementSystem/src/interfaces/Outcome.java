package interfaces;

import java.time.LocalDate;

public interface Outcome {
    void setDateOfApp(LocalDate date);
    void setServiceType(String service);
    void setConsultationNotes(String notes);
    void setMedications(String[] medicines);
}
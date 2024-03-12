package tour.nonghaeng.domain.experience.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="EXPERIENCE_CLOSE_DATES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceCloseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="experience_close_date_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    private LocalDate closeDate;

    @Builder
    public ExperienceCloseDate(Experience experience, LocalDate closeDate) {
        this.experience = experience;
        this.closeDate = closeDate;
    }
}

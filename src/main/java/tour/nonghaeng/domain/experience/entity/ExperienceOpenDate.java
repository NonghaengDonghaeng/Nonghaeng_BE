package tour.nonghaeng.domain.experience.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="EXPERIENCE_OPEN_DATES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceOpenDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="experience_open_date_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    private LocalDate openDate;
}

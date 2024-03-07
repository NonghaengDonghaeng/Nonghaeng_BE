package tour.nonghaeng.domain.experience.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name="EXPERIENCE_ROUNDS")
@NoArgsConstructor
@Getter
public class ExperienceRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="experience_round_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Column(name="start_time",nullable = false)
    private LocalTime startTime;

    @Column(name="end_time",nullable = false)
    private LocalTime endTime;

    private int maxParticipant;
}

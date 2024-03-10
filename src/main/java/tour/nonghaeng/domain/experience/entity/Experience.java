package tour.nonghaeng.domain.experience.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.BaseTimeEntity;
import tour.nonghaeng.domain.etc.experience.ExperienceType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EXPERIENCES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Experience extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @OneToMany(mappedBy = "experience", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceRound> experienceRounds = new ArrayList<>();

    @OneToMany(mappedBy = "experience", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceOpenDate> experienceOpenDates = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceType experienceType;

    private String experienceName;


    private LocalDate startDate;            //시작운영시기

    private LocalDate endDate;              //종료운영시기

    private int minParticipant;             //최소체험인원

    private int maxParticipant;             //최대체험인원

    private int price;                      //가격

    private int durationHours;              //소요시간

    private String checkPoint;              //체크포인트

    private String detailIntroduction;      //상세소개

    private String summary;

    private String supplies;                //준비물

    private String precautions;             //유의사항

    @Builder
    public Experience(Seller seller, Tour tour, ExperienceType experienceType, String experienceName, LocalDate startDate, LocalDate endDate, int minParticipant, int maxParticipant, int price, int durationHours, String checkPoint, String detailIntroduction, String summary, String supplies, String precautions) {
        this.seller = seller;
        this.tour = tour;
        this.experienceType = experienceType;
        this.experienceName = experienceName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minParticipant = minParticipant;
        this.maxParticipant = maxParticipant;
        this.price = price;
        this.durationHours = durationHours;
        this.checkPoint = checkPoint;
        this.detailIntroduction = detailIntroduction;
        this.summary = summary;
        this.supplies = supplies;
        this.precautions = precautions;

    }

    public void addExperienceRound(ExperienceRound experienceRound) {
        this.experienceRounds.add(experienceRound);
    }

    public void addOpenDate(ExperienceOpenDate experienceOpenDate) {
        this.experienceOpenDates.add(experienceOpenDate);
    }

    public void deleteOpenDate(ExperienceOpenDate experienceOpenDate) {
        this.experienceOpenDates.remove(experienceOpenDate);
    }
}

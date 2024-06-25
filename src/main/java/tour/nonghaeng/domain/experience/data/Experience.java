package tour.nonghaeng.domain.experience.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.BaseTimeEntity;
import tour.nonghaeng.global.infra.enums.experience.ExperienceType;
import tour.nonghaeng.domain.like.data.ExperienceLike;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.photo.data.ExperiencePhoto;
import tour.nonghaeng.domain.photo.data.Photo;
import tour.nonghaeng.domain.tour.data.Tour;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private List<ExperienceCloseDate> experienceCloseDates = new ArrayList<>();

    @OneToMany(mappedBy = "experience", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperiencePhoto> experiencePhotoList;

    @OneToMany(mappedBy = "experience", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceLike> experienceLikes = new ArrayList<>();

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

    @Column(columnDefinition = "TEXT")
    private String checkPoint;              //체크포인트

    @Column(columnDefinition = "TEXT")
    private String detailIntroduction;      //상세소개

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String supplies;                //준비물

    @Column(columnDefinition = "TEXT")
    private String precautions;             //유의사항

    private int likes;

    @Builder
    public Experience(Tour tour, ExperienceType experienceType, String experienceName, LocalDate startDate, LocalDate endDate, int minParticipant, int maxParticipant, int price, int durationHours, String checkPoint, String detailIntroduction, String summary, String supplies, String precautions) {
        this.seller = tour.getSeller();
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
        this.likes = 0;

    }

    public void addExperienceRound(ExperienceRound experienceRound) {
        this.experienceRounds.add(experienceRound);
    }

    public void addCloseDate(ExperienceCloseDate experienceCloseDate) {
        this.experienceCloseDates.add(experienceCloseDate);
    }

    public void removeCloseDate(ExperienceCloseDate experienceCloseDate) {
        this.experienceCloseDates.remove(experienceCloseDate);
    }

    public Optional<Photo> findRepresentPhoto() {

        for (ExperiencePhoto ep : this.experiencePhotoList) {
            if (ep.isRepresentative()) {
                return Optional.ofNullable(ep);
            }
        }

        return Optional.ofNullable(null);
    }

    public void plusLikes(){
        this.likes += 1;
    }

    public void minusLikes(){
        this.likes -= 1;
    }
}

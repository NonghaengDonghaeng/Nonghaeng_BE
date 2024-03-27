package tour.nonghaeng.domain.photo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.photo.entity.ExperiencePhoto;
import tour.nonghaeng.domain.photo.entity.Photo;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperiencePhotoRepository extends JpaRepository<ExperiencePhoto, Long> {

    boolean existsById(Long experiencePhotoId);

    @Query("select count(ep) from ExperiencePhoto ep where ep.experience = :experience and ep.representative=true")
    Integer countRepresentative(@Param("experience")Experience experience);

    @Query("select count(ep) = 1 from ExperiencePhoto ep where ep.experience = :experience and ep.representative=true")
    boolean hasExactlyOneRepresentativePhoto(@Param("experience")Experience experience);

    @Query("select ep.id from ExperiencePhoto ep where ep.experience = :experience and ep.representative=true")
    Optional<Long> findRepresentativePhotoId(@Param("experience")Experience experience);

    @Query("select ep from ExperiencePhoto ep where ep.experience = :experience and ep.representative=true")
    List<Photo> findAllByExperience(@Param("experience")Experience experience);
}

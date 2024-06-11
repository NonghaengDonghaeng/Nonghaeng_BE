package tour.nonghaeng.domain.notice.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.Admin;
import tour.nonghaeng.global.infra.BaseTimeEntity;

@Entity
@Table(name = "NOTICES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean important;

    @Builder
    private Notice(Admin admin, String title, String content, boolean important) {
        this.admin = admin;
        this.title = title;
        this.content = content;
        this.important = important;
    }

    public void onImportant() {
        this.important = true;
    }

    public void offImportant() {
        this.important = false;
    }

}

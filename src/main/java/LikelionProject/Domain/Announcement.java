package LikelionProject.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.File;
import java.time.LocalDateTime;

@Entity(name="Announce")
@Table(name="Announce")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String Title;

    private String Content;

    private String Writer;

    @CreatedDate
    private LocalDateTime DateTime;

    private String FileName;

    private String FilePath;

    @Builder
    public Announcement(Long id, String Title, String Content, LocalDateTime DateTime, String FileName, String FilePath) {
        this.id = id;
        this.Title = Title;
        this.Content = Content;
        this.DateTime = DateTime;
        this.FileName = FileName;
        this.FilePath = FilePath;
    }
}

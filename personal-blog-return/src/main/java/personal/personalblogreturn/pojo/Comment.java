package personal.personalblogreturn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Ljh
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_comment")
public class Comment {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String nickName;
    private String email;
    private String avatar;
    private String content;
    private String rObject;
    private Boolean admin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;



    @ManyToOne
    private Blog blog;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> sonComment;

    @ManyToOne
    private Comment parentComment;
}

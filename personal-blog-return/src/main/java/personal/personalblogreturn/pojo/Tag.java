package personal.personalblogreturn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Ljh
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_tag")
public class Tag {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blog;
}

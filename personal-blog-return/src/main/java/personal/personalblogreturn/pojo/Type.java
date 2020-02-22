package personal.personalblogreturn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created by Ljh
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_type")
public class Type {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blog;
}

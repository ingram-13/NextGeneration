package personal.personalblogreturn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ljh
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_blog")
public class Blog {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String title;
    private String content;
    private String firstPic;
    private String flag;
    private String description;
    private Integer viewCounts;
    private boolean status;
    private boolean appreciateSwitch;
    private boolean commentSwitch;
    private boolean copyrightSwitch;
    private boolean recommend;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments;

    @Transient
    private Integer typeId;
    @Transient
    private List<Integer> tagsId;

//
//    public String getCreateTime() {
//        if (createTime==null){
//            return null;
//        }
//        return getTime(createTime);
//    }
//
//    public String getUpdateTime() {
//        if (updateTime==null){
//            return null;
//        }
//        return getTime(updateTime);
//    }
//
//
//    /**
//     * 校准时差
//     *
//     * @param date
//     * @return
//     */
//    private String getTime(Date date){
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.HOUR_OF_DAY, -8);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return simpleDateFormat.format(cal.getTime());
//    }
}

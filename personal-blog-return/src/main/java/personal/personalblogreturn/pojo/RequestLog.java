package personal.personalblogreturn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by Ljh
 * 日志信息对象
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RequestLog {
    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;

}

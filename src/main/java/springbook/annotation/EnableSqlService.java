package springbook.annotation;

import org.springframework.context.annotation.Import;
import springbook.context.SqlServiceContext;

@Import(value= SqlServiceContext.class)
public @interface EnableSqlService {
}

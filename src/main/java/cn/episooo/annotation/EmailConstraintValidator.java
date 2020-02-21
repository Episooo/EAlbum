package cn.episooo.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author ：Ep
 * @Date ：Created in 2020/2/15 1:57
 * @Description：
 */
public class EmailConstraintValidator implements ConstraintValidator<Email,String> {

    @Override
    public void initialize(Email constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null || "".equals(s))
            return false;

        Pattern pattern;
        Matcher matcher;

        String rule = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(s);
        return matcher.matches();
    }
}

package org.unibl.etf.ip.sni_projekat.validation.xss;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XSSValidator implements ConstraintValidator<XSSValidation,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {


        List<Pattern> patterns = Arrays.asList(
                Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
                Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
                Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
                Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
                Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
        );
        for(var p : patterns){
            Matcher matcher = p.matcher(s);
            if(matcher.find()){
                return false;
            }
        }
        return true;

    }
}

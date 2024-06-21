package org.unibl.etf.ip.sni_projekat.validation.sql;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLValidator implements ConstraintValidator<SQLValidation,String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<Pattern> patterns = Arrays.asList(

                Pattern.compile("(?i)(.*)(\\b)+(OR|AND)(\\s)+(true|false)(\\s)*(.*)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(?i)(.*)(\\b)+(OR|AND)(\\s)+(\\w)(\\s)*(\\=)(\\s)*(\\w)(\\s)*(.*)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)(.*)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(?i)(.*)(\\b)+DELETE(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(?i)(.*)(\\b)+(INSERT|UPDATE|UPSERT|SAVEPOINT|CALL)(\\b)+\\s.*(.*)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("(?i)(.*)(\\b)+UNION(\\b)+\\s+SELECT(\\b)+\\s.*(\\b)(.*)", Pattern.CASE_INSENSITIVE)

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import util.DBUtils;

/**
 *
 * @author aoiro_makoto
 */
public class IdentifyingAccountForm {

    /**
     * ユーザIDを表すフィールド． チェック項目は以下の通り．
     * <ul>
     * <li>長さが4文字以上，12文字以下である．</li>
     * <li>空文字ではない．</li>
     * <li>英数字のみが利用されている．</li>
     * </ul>
     */
    @Length(min = 4, max = 12, message = "ユーザIDには4から12文字の英数字のみが利用できます")
    @NotEmpty(message = "ユーザIDには4から12文字の英数字のみが利用できます")
    private String user;

    /**
     * パスワードを表すフィールド． チェック項目は以下の通り．
     * <ul>
     * <li>長さが4文字以上，12文字以下である．</li>
     * <li>空文字ではない．</li>
     * <li>英数字のみが利用されている．</li>
     * </ul>
     */
    @Length(min = 4, max = 12, message = "パスワードには4から12文字の英数字のみが利用できます")
    @NotEmpty(message = "パスワードには4から12文字の英数字のみが利用できます")
    private String pass;

    private List project;
    
    /**
     * パスワードを取得する．
     *
     * @return パスワード
     */
    public String getPass() {
        return pass;
    }

    /**
     * ユーザIDを取得する．
     *
     * @return ユーザID
     */
    public String getUser() {
        return user;
    }
    
    public List getProject(){
        return project;
    }

    /**
     * パスワードを登録する．登録の際，サニタイズを行う．
     *
     * @param pass パスワード
     */
    public void setPass(String pass) {
        this.pass = DBUtils.sanitize(pass);
    }

    /**
     * ユーザIDを登録する．登録の際，サニタイズを行う．
     *
     * @param userId ユーザID
     */
    public void setUser(String user) {
        this.user = DBUtils.sanitize(user);
    }
    
    public void setProject(List project){
        this.project = project;
    }
    
    

    public boolean validate() throws ValidationException {
        String className = getClass().getName();
        int index = className.lastIndexOf(".");
        className = className.substring(index + 1);
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info(className + ".validate");

        try {
            return IdentifyingAccountForm.<IdentifyingAccountForm>validate(this);
        } catch (ValidationException e) {
            logger.warning(className + ".validate: " + e.getMessage());
            throw e;
        }
    }

    private static <T extends IdentifyingAccountForm> boolean validate(T bean)  throws ValidationException {
        // Hibernate Validator (HB) のvalidatorインスタンス生成
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // validation
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean);

        // 制約違反発見
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();

            // 各制約違反に対する処理
            while (iterator.hasNext()) {
                // 例外をメッセージ付きで投げる．
                throw new ValidationException(iterator.next().getMessage());
            }
        }
        return true;
    }

}

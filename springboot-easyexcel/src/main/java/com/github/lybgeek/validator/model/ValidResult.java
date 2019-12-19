package com.github.lybgeek.validator.model;

import java.io.Serializable;
import java.util.List;

/**
 * 参数校验返回结果result
 *
 *
 */
public class ValidResult implements Serializable {

    private static final long serialVersionUID = 4908491004980368729L;

    /**
     * 是否通过校验
     */
    private boolean success = false;

    /**
     * 未通过的参数名(仅快速失败模式有值)
     */
    private String paramName = "";

    /**
     * 未通过参数的报错信息(注解上的message)
     * 仅快速失败模式有值
     */
    private String paramError = "";

    /**
     * 错误信息(仅全部校验模式有值)
     * 参数名:报错信息
     */
    private List<String> errorMessages;

    private ValidResult() {
    }

    public static ValidResult success() {
        ValidResult r = new ValidResult();
        r.setSuccess(true);
        return r;
    }

    public static ValidResult fail(String paramName, String paramError) {
        ValidResult r = new ValidResult();
        r.setSuccess(false);
        r.setParamName(paramName);
        r.setParamError(paramError);
        return r;
    }

    public static ValidResult fail(List<String> errorMessages) {
        ValidResult r = new ValidResult();
        r.setSuccess(false);
        r.setErrorMessages(errorMessages);
        return r;
    }

    /**
     * 校验是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * 获取完整报错信息(仅快速失败模式)
     *
     * @return
     */
    public String getFailFastMsg() {
        return String.format("%s:%s", this.paramName, this.paramError);
    }

    private void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 获取未通过的参数名
     *
     * @return
     */
    public String getParamName() {
        return paramName;
    }

    private void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 未通过参数的报错信息
     *
     * @return
     */
    public String getParamError() {
        return paramError;
    }

    private void setParamError(String paramError) {
        this.paramError = paramError;
    }

    /**
     * 获取报错信息集合(仅全部校验模式)
     *
     * @return
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

}

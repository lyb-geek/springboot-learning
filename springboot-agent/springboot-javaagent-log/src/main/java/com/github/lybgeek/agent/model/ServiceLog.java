package com.github.lybgeek.agent.model;


import java.util.Date;

public class ServiceLog {

    public static final String SUCEESS = "0";

    public static final String FAIL = "1";

    private Long id;

    private String serviceName;

    private String methodName;

    private String reqArgs;

    private String respResult;

    private long costTime;

    private String status;

    private Date createTime;

    public ServiceLog() {
    }

    public ServiceLog(String serviceName, String methodName, String reqArgs, String respResult, long costTime, String status) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.reqArgs = reqArgs;
        this.respResult = respResult;
        this.costTime = costTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReqArgs() {
        return reqArgs;
    }

    public void setReqArgs(String reqArgs) {
        this.reqArgs = reqArgs;
    }

    public String getRespResult() {
        return respResult;
    }

    public void setRespResult(String respResult) {
        this.respResult = respResult;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

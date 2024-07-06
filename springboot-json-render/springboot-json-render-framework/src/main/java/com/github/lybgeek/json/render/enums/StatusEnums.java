package com.github.lybgeek.json.render.enums;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusEnums {

    NORMAL(1,"正常"),

    LOCK(2,"锁定"),

    DELETE(3,"删除");

    //    @JsonValue
    private Integer code;

    private String desc;

    StatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static StatusEnums getByCode(Integer code){
        for(StatusEnums statusEnums : StatusEnums.values()){
            if(statusEnums.getCode().equals(code)){
                return statusEnums;
            }
        }
        return null;
    }

}

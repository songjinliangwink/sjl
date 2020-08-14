package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "express_person")
public class ExpressPerson implements Serializable {
    @Id
    private Integer id;

    @Column(name = "experss_name")
    private String experssName;

    private String sex;

    @Column(name = "experss_trait")
    private String experssTrait;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "experss_time")
    private Date experssTime;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "express_type_id")
    private Integer expressTypeId;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return experss_name
     */
    public String getExperssName() {
        return experssName;
    }

    /**
     * @param experssName
     */
    public void setExperssName(String experssName) {
        this.experssName = experssName == null ? null : experssName.trim();
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * @return experss_trait
     */
    public String getExperssTrait() {
        return experssTrait;
    }

    /**
     * @param experssTrait
     */
    public void setExperssTrait(String experssTrait) {
        this.experssTrait = experssTrait == null ? null : experssTrait.trim();
    }

    /**
     * @return experss_time
     */
    public Date getExperssTime() {
        return experssTime;
    }

    /**
     * @param experssTime
     */
    public void setExperssTime(Date experssTime) {
        this.experssTime = experssTime;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return express_type_id
     */
    public Integer getExpressTypeId() {
        return expressTypeId;
    }

    /**
     * @param expressTypeId
     */
    public void setExpressTypeId(Integer expressTypeId) {
        this.expressTypeId = expressTypeId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", experssName=").append(experssName);
        sb.append(", sex=").append(sex);
        sb.append(", experssTrait=").append(experssTrait);
        sb.append(", experssTime=").append(experssTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", expressTypeId=").append(expressTypeId);
        sb.append("]");
        return sb.toString();
    }
}
package com.xiaoshu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExpressPersonExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExpressPersonExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andExperssNameIsNull() {
            addCriterion("experss_name is null");
            return (Criteria) this;
        }

        public Criteria andExperssNameIsNotNull() {
            addCriterion("experss_name is not null");
            return (Criteria) this;
        }

        public Criteria andExperssNameEqualTo(String value) {
            addCriterion("experss_name =", value, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameNotEqualTo(String value) {
            addCriterion("experss_name <>", value, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameGreaterThan(String value) {
            addCriterion("experss_name >", value, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameGreaterThanOrEqualTo(String value) {
            addCriterion("experss_name >=", value, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameLessThan(String value) {
            addCriterion("experss_name <", value, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameLessThanOrEqualTo(String value) {
            addCriterion("experss_name <=", value, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameLike(String value) {
            addCriterion("experss_name like", value, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameNotLike(String value) {
            addCriterion("experss_name not like", value, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameIn(List<String> values) {
            addCriterion("experss_name in", values, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameNotIn(List<String> values) {
            addCriterion("experss_name not in", values, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameBetween(String value1, String value2) {
            addCriterion("experss_name between", value1, value2, "experssName");
            return (Criteria) this;
        }

        public Criteria andExperssNameNotBetween(String value1, String value2) {
            addCriterion("experss_name not between", value1, value2, "experssName");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andExperssTraitIsNull() {
            addCriterion("experss_trait is null");
            return (Criteria) this;
        }

        public Criteria andExperssTraitIsNotNull() {
            addCriterion("experss_trait is not null");
            return (Criteria) this;
        }

        public Criteria andExperssTraitEqualTo(String value) {
            addCriterion("experss_trait =", value, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitNotEqualTo(String value) {
            addCriterion("experss_trait <>", value, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitGreaterThan(String value) {
            addCriterion("experss_trait >", value, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitGreaterThanOrEqualTo(String value) {
            addCriterion("experss_trait >=", value, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitLessThan(String value) {
            addCriterion("experss_trait <", value, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitLessThanOrEqualTo(String value) {
            addCriterion("experss_trait <=", value, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitLike(String value) {
            addCriterion("experss_trait like", value, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitNotLike(String value) {
            addCriterion("experss_trait not like", value, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitIn(List<String> values) {
            addCriterion("experss_trait in", values, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitNotIn(List<String> values) {
            addCriterion("experss_trait not in", values, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitBetween(String value1, String value2) {
            addCriterion("experss_trait between", value1, value2, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTraitNotBetween(String value1, String value2) {
            addCriterion("experss_trait not between", value1, value2, "experssTrait");
            return (Criteria) this;
        }

        public Criteria andExperssTimeIsNull() {
            addCriterion("experss_time is null");
            return (Criteria) this;
        }

        public Criteria andExperssTimeIsNotNull() {
            addCriterion("experss_time is not null");
            return (Criteria) this;
        }

        public Criteria andExperssTimeEqualTo(Date value) {
            addCriterionForJDBCDate("experss_time =", value, "experssTime");
            return (Criteria) this;
        }

        public Criteria andExperssTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("experss_time <>", value, "experssTime");
            return (Criteria) this;
        }

        public Criteria andExperssTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("experss_time >", value, "experssTime");
            return (Criteria) this;
        }

        public Criteria andExperssTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("experss_time >=", value, "experssTime");
            return (Criteria) this;
        }

        public Criteria andExperssTimeLessThan(Date value) {
            addCriterionForJDBCDate("experss_time <", value, "experssTime");
            return (Criteria) this;
        }

        public Criteria andExperssTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("experss_time <=", value, "experssTime");
            return (Criteria) this;
        }

        public Criteria andExperssTimeIn(List<Date> values) {
            addCriterionForJDBCDate("experss_time in", values, "experssTime");
            return (Criteria) this;
        }

        public Criteria andExperssTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("experss_time not in", values, "experssTime");
            return (Criteria) this;
        }

        public Criteria andExperssTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("experss_time between", value1, value2, "experssTime");
            return (Criteria) this;
        }

        public Criteria andExperssTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("experss_time not between", value1, value2, "experssTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdIsNull() {
            addCriterion("express_type_id is null");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdIsNotNull() {
            addCriterion("express_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdEqualTo(Integer value) {
            addCriterion("express_type_id =", value, "expressTypeId");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdNotEqualTo(Integer value) {
            addCriterion("express_type_id <>", value, "expressTypeId");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdGreaterThan(Integer value) {
            addCriterion("express_type_id >", value, "expressTypeId");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("express_type_id >=", value, "expressTypeId");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdLessThan(Integer value) {
            addCriterion("express_type_id <", value, "expressTypeId");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("express_type_id <=", value, "expressTypeId");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdIn(List<Integer> values) {
            addCriterion("express_type_id in", values, "expressTypeId");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdNotIn(List<Integer> values) {
            addCriterion("express_type_id not in", values, "expressTypeId");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("express_type_id between", value1, value2, "expressTypeId");
            return (Criteria) this;
        }

        public Criteria andExpressTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("express_type_id not between", value1, value2, "expressTypeId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
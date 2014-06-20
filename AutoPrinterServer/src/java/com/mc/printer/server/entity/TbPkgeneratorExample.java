package com.mc.printer.server.entity;

import java.util.ArrayList;
import java.util.List;

public class TbPkgeneratorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TbPkgeneratorExample() {
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

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTargettableIsNull() {
            addCriterion("targettable is null");
            return (Criteria) this;
        }

        public Criteria andTargettableIsNotNull() {
            addCriterion("targettable is not null");
            return (Criteria) this;
        }

        public Criteria andTargettableEqualTo(String value) {
            addCriterion("targettable =", value, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableNotEqualTo(String value) {
            addCriterion("targettable <>", value, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableGreaterThan(String value) {
            addCriterion("targettable >", value, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableGreaterThanOrEqualTo(String value) {
            addCriterion("targettable >=", value, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableLessThan(String value) {
            addCriterion("targettable <", value, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableLessThanOrEqualTo(String value) {
            addCriterion("targettable <=", value, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableLike(String value) {
            addCriterion("targettable like", value, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableNotLike(String value) {
            addCriterion("targettable not like", value, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableIn(List<String> values) {
            addCriterion("targettable in", values, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableNotIn(List<String> values) {
            addCriterion("targettable not in", values, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableBetween(String value1, String value2) {
            addCriterion("targettable between", value1, value2, "targettable");
            return (Criteria) this;
        }

        public Criteria andTargettableNotBetween(String value1, String value2) {
            addCriterion("targettable not between", value1, value2, "targettable");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameIsNull() {
            addCriterion("pkcolumnname is null");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameIsNotNull() {
            addCriterion("pkcolumnname is not null");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameEqualTo(String value) {
            addCriterion("pkcolumnname =", value, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameNotEqualTo(String value) {
            addCriterion("pkcolumnname <>", value, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameGreaterThan(String value) {
            addCriterion("pkcolumnname >", value, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameGreaterThanOrEqualTo(String value) {
            addCriterion("pkcolumnname >=", value, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameLessThan(String value) {
            addCriterion("pkcolumnname <", value, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameLessThanOrEqualTo(String value) {
            addCriterion("pkcolumnname <=", value, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameLike(String value) {
            addCriterion("pkcolumnname like", value, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameNotLike(String value) {
            addCriterion("pkcolumnname not like", value, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameIn(List<String> values) {
            addCriterion("pkcolumnname in", values, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameNotIn(List<String> values) {
            addCriterion("pkcolumnname not in", values, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameBetween(String value1, String value2) {
            addCriterion("pkcolumnname between", value1, value2, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andPkcolumnnameNotBetween(String value1, String value2) {
            addCriterion("pkcolumnname not between", value1, value2, "pkcolumnname");
            return (Criteria) this;
        }

        public Criteria andInitialvalueIsNull() {
            addCriterion("initialvalue is null");
            return (Criteria) this;
        }

        public Criteria andInitialvalueIsNotNull() {
            addCriterion("initialvalue is not null");
            return (Criteria) this;
        }

        public Criteria andInitialvalueEqualTo(Long value) {
            addCriterion("initialvalue =", value, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andInitialvalueNotEqualTo(Long value) {
            addCriterion("initialvalue <>", value, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andInitialvalueGreaterThan(Long value) {
            addCriterion("initialvalue >", value, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andInitialvalueGreaterThanOrEqualTo(Long value) {
            addCriterion("initialvalue >=", value, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andInitialvalueLessThan(Long value) {
            addCriterion("initialvalue <", value, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andInitialvalueLessThanOrEqualTo(Long value) {
            addCriterion("initialvalue <=", value, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andInitialvalueIn(List<Long> values) {
            addCriterion("initialvalue in", values, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andInitialvalueNotIn(List<Long> values) {
            addCriterion("initialvalue not in", values, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andInitialvalueBetween(Long value1, Long value2) {
            addCriterion("initialvalue between", value1, value2, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andInitialvalueNotBetween(Long value1, Long value2) {
            addCriterion("initialvalue not between", value1, value2, "initialvalue");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeIsNull() {
            addCriterion("allocationsize is null");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeIsNotNull() {
            addCriterion("allocationsize is not null");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeEqualTo(Long value) {
            addCriterion("allocationsize =", value, "allocationsize");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeNotEqualTo(Long value) {
            addCriterion("allocationsize <>", value, "allocationsize");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeGreaterThan(Long value) {
            addCriterion("allocationsize >", value, "allocationsize");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeGreaterThanOrEqualTo(Long value) {
            addCriterion("allocationsize >=", value, "allocationsize");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeLessThan(Long value) {
            addCriterion("allocationsize <", value, "allocationsize");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeLessThanOrEqualTo(Long value) {
            addCriterion("allocationsize <=", value, "allocationsize");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeIn(List<Long> values) {
            addCriterion("allocationsize in", values, "allocationsize");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeNotIn(List<Long> values) {
            addCriterion("allocationsize not in", values, "allocationsize");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeBetween(Long value1, Long value2) {
            addCriterion("allocationsize between", value1, value2, "allocationsize");
            return (Criteria) this;
        }

        public Criteria andAllocationsizeNotBetween(Long value1, Long value2) {
            addCriterion("allocationsize not between", value1, value2, "allocationsize");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria addConditionSql(String addConditionSql) {
            addCriterion(addConditionSql);
            return this;
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
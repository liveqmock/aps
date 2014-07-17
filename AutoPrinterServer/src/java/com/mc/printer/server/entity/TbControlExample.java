package com.mc.printer.server.entity;

import java.util.ArrayList;
import java.util.List;

public class TbControlExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limit;

    protected Integer offset;

    public TbControlExample() {
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

    public void setLimit(Integer limit) {
        this.limit=limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset=offset;
    }

    public Integer getOffset() {
        return offset;
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

        public Criteria andBranchnameIsNull() {
            addCriterion("branchname is null");
            return (Criteria) this;
        }

        public Criteria andBranchnameIsNotNull() {
            addCriterion("branchname is not null");
            return (Criteria) this;
        }

        public Criteria andBranchnameEqualTo(String value) {
            addCriterion("branchname =", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameNotEqualTo(String value) {
            addCriterion("branchname <>", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameGreaterThan(String value) {
            addCriterion("branchname >", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameGreaterThanOrEqualTo(String value) {
            addCriterion("branchname >=", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameLessThan(String value) {
            addCriterion("branchname <", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameLessThanOrEqualTo(String value) {
            addCriterion("branchname <=", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameLike(String value) {
            addCriterion("branchname like", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameNotLike(String value) {
            addCriterion("branchname not like", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameIn(List<String> values) {
            addCriterion("branchname in", values, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameNotIn(List<String> values) {
            addCriterion("branchname not in", values, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameBetween(String value1, String value2) {
            addCriterion("branchname between", value1, value2, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameNotBetween(String value1, String value2) {
            addCriterion("branchname not between", value1, value2, "branchname");
            return (Criteria) this;
        }

        public Criteria andGuidenameIsNull() {
            addCriterion("guidename is null");
            return (Criteria) this;
        }

        public Criteria andGuidenameIsNotNull() {
            addCriterion("guidename is not null");
            return (Criteria) this;
        }

        public Criteria andGuidenameEqualTo(String value) {
            addCriterion("guidename =", value, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameNotEqualTo(String value) {
            addCriterion("guidename <>", value, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameGreaterThan(String value) {
            addCriterion("guidename >", value, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameGreaterThanOrEqualTo(String value) {
            addCriterion("guidename >=", value, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameLessThan(String value) {
            addCriterion("guidename <", value, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameLessThanOrEqualTo(String value) {
            addCriterion("guidename <=", value, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameLike(String value) {
            addCriterion("guidename like", value, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameNotLike(String value) {
            addCriterion("guidename not like", value, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameIn(List<String> values) {
            addCriterion("guidename in", values, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameNotIn(List<String> values) {
            addCriterion("guidename not in", values, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameBetween(String value1, String value2) {
            addCriterion("guidename between", value1, value2, "guidename");
            return (Criteria) this;
        }

        public Criteria andGuidenameNotBetween(String value1, String value2) {
            addCriterion("guidename not between", value1, value2, "guidename");
            return (Criteria) this;
        }

        public Criteria andButtonnameIsNull() {
            addCriterion("buttonname is null");
            return (Criteria) this;
        }

        public Criteria andButtonnameIsNotNull() {
            addCriterion("buttonname is not null");
            return (Criteria) this;
        }

        public Criteria andButtonnameEqualTo(String value) {
            addCriterion("buttonname =", value, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameNotEqualTo(String value) {
            addCriterion("buttonname <>", value, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameGreaterThan(String value) {
            addCriterion("buttonname >", value, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameGreaterThanOrEqualTo(String value) {
            addCriterion("buttonname >=", value, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameLessThan(String value) {
            addCriterion("buttonname <", value, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameLessThanOrEqualTo(String value) {
            addCriterion("buttonname <=", value, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameLike(String value) {
            addCriterion("buttonname like", value, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameNotLike(String value) {
            addCriterion("buttonname not like", value, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameIn(List<String> values) {
            addCriterion("buttonname in", values, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameNotIn(List<String> values) {
            addCriterion("buttonname not in", values, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameBetween(String value1, String value2) {
            addCriterion("buttonname between", value1, value2, "buttonname");
            return (Criteria) this;
        }

        public Criteria andButtonnameNotBetween(String value1, String value2) {
            addCriterion("buttonname not between", value1, value2, "buttonname");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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
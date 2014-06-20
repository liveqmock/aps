package com.mc.printer.server.entity;

import java.util.ArrayList;
import java.util.List;

public class TbDepartmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TbDepartmentExample() {
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

        public Criteria andDepnameIsNull() {
            addCriterion("depname is null");
            return (Criteria) this;
        }

        public Criteria andDepnameIsNotNull() {
            addCriterion("depname is not null");
            return (Criteria) this;
        }

        public Criteria andDepnameEqualTo(String value) {
            addCriterion("depname =", value, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameNotEqualTo(String value) {
            addCriterion("depname <>", value, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameGreaterThan(String value) {
            addCriterion("depname >", value, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameGreaterThanOrEqualTo(String value) {
            addCriterion("depname >=", value, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameLessThan(String value) {
            addCriterion("depname <", value, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameLessThanOrEqualTo(String value) {
            addCriterion("depname <=", value, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameLike(String value) {
            addCriterion("depname like", value, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameNotLike(String value) {
            addCriterion("depname not like", value, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameIn(List<String> values) {
            addCriterion("depname in", values, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameNotIn(List<String> values) {
            addCriterion("depname not in", values, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameBetween(String value1, String value2) {
            addCriterion("depname between", value1, value2, "depname");
            return (Criteria) this;
        }

        public Criteria andDepnameNotBetween(String value1, String value2) {
            addCriterion("depname not between", value1, value2, "depname");
            return (Criteria) this;
        }

        public Criteria andDeplevelIsNull() {
            addCriterion("deplevel is null");
            return (Criteria) this;
        }

        public Criteria andDeplevelIsNotNull() {
            addCriterion("deplevel is not null");
            return (Criteria) this;
        }

        public Criteria andDeplevelEqualTo(Long value) {
            addCriterion("deplevel =", value, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDeplevelNotEqualTo(Long value) {
            addCriterion("deplevel <>", value, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDeplevelGreaterThan(Long value) {
            addCriterion("deplevel >", value, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDeplevelGreaterThanOrEqualTo(Long value) {
            addCriterion("deplevel >=", value, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDeplevelLessThan(Long value) {
            addCriterion("deplevel <", value, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDeplevelLessThanOrEqualTo(Long value) {
            addCriterion("deplevel <=", value, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDeplevelIn(List<Long> values) {
            addCriterion("deplevel in", values, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDeplevelNotIn(List<Long> values) {
            addCriterion("deplevel not in", values, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDeplevelBetween(Long value1, Long value2) {
            addCriterion("deplevel between", value1, value2, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDeplevelNotBetween(Long value1, Long value2) {
            addCriterion("deplevel not between", value1, value2, "deplevel");
            return (Criteria) this;
        }

        public Criteria andDepfatherIsNull() {
            addCriterion("depfather is null");
            return (Criteria) this;
        }

        public Criteria andDepfatherIsNotNull() {
            addCriterion("depfather is not null");
            return (Criteria) this;
        }

        public Criteria andDepfatherEqualTo(Long value) {
            addCriterion("depfather =", value, "depfather");
            return (Criteria) this;
        }

        public Criteria andDepfatherNotEqualTo(Long value) {
            addCriterion("depfather <>", value, "depfather");
            return (Criteria) this;
        }

        public Criteria andDepfatherGreaterThan(Long value) {
            addCriterion("depfather >", value, "depfather");
            return (Criteria) this;
        }

        public Criteria andDepfatherGreaterThanOrEqualTo(Long value) {
            addCriterion("depfather >=", value, "depfather");
            return (Criteria) this;
        }

        public Criteria andDepfatherLessThan(Long value) {
            addCriterion("depfather <", value, "depfather");
            return (Criteria) this;
        }

        public Criteria andDepfatherLessThanOrEqualTo(Long value) {
            addCriterion("depfather <=", value, "depfather");
            return (Criteria) this;
        }

        public Criteria andDepfatherIn(List<Long> values) {
            addCriterion("depfather in", values, "depfather");
            return (Criteria) this;
        }

        public Criteria andDepfatherNotIn(List<Long> values) {
            addCriterion("depfather not in", values, "depfather");
            return (Criteria) this;
        }

        public Criteria andDepfatherBetween(Long value1, Long value2) {
            addCriterion("depfather between", value1, value2, "depfather");
            return (Criteria) this;
        }

        public Criteria andDepfatherNotBetween(Long value1, Long value2) {
            addCriterion("depfather not between", value1, value2, "depfather");
            return (Criteria) this;
        }

        public Criteria andDeppresentidIsNull() {
            addCriterion("deppresentid is null");
            return (Criteria) this;
        }

        public Criteria andDeppresentidIsNotNull() {
            addCriterion("deppresentid is not null");
            return (Criteria) this;
        }

        public Criteria andDeppresentidEqualTo(Long value) {
            addCriterion("deppresentid =", value, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDeppresentidNotEqualTo(Long value) {
            addCriterion("deppresentid <>", value, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDeppresentidGreaterThan(Long value) {
            addCriterion("deppresentid >", value, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDeppresentidGreaterThanOrEqualTo(Long value) {
            addCriterion("deppresentid >=", value, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDeppresentidLessThan(Long value) {
            addCriterion("deppresentid <", value, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDeppresentidLessThanOrEqualTo(Long value) {
            addCriterion("deppresentid <=", value, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDeppresentidIn(List<Long> values) {
            addCriterion("deppresentid in", values, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDeppresentidNotIn(List<Long> values) {
            addCriterion("deppresentid not in", values, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDeppresentidBetween(Long value1, Long value2) {
            addCriterion("deppresentid between", value1, value2, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDeppresentidNotBetween(Long value1, Long value2) {
            addCriterion("deppresentid not between", value1, value2, "deppresentid");
            return (Criteria) this;
        }

        public Criteria andDescmsIsNull() {
            addCriterion("descms is null");
            return (Criteria) this;
        }

        public Criteria andDescmsIsNotNull() {
            addCriterion("descms is not null");
            return (Criteria) this;
        }

        public Criteria andDescmsEqualTo(String value) {
            addCriterion("descms =", value, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsNotEqualTo(String value) {
            addCriterion("descms <>", value, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsGreaterThan(String value) {
            addCriterion("descms >", value, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsGreaterThanOrEqualTo(String value) {
            addCriterion("descms >=", value, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsLessThan(String value) {
            addCriterion("descms <", value, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsLessThanOrEqualTo(String value) {
            addCriterion("descms <=", value, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsLike(String value) {
            addCriterion("descms like", value, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsNotLike(String value) {
            addCriterion("descms not like", value, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsIn(List<String> values) {
            addCriterion("descms in", values, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsNotIn(List<String> values) {
            addCriterion("descms not in", values, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsBetween(String value1, String value2) {
            addCriterion("descms between", value1, value2, "descms");
            return (Criteria) this;
        }

        public Criteria andDescmsNotBetween(String value1, String value2) {
            addCriterion("descms not between", value1, value2, "descms");
            return (Criteria) this;
        }

        public Criteria andExt1IsNull() {
            addCriterion("ext1 is null");
            return (Criteria) this;
        }

        public Criteria andExt1IsNotNull() {
            addCriterion("ext1 is not null");
            return (Criteria) this;
        }

        public Criteria andExt1EqualTo(String value) {
            addCriterion("ext1 =", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotEqualTo(String value) {
            addCriterion("ext1 <>", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThan(String value) {
            addCriterion("ext1 >", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThanOrEqualTo(String value) {
            addCriterion("ext1 >=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThan(String value) {
            addCriterion("ext1 <", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThanOrEqualTo(String value) {
            addCriterion("ext1 <=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Like(String value) {
            addCriterion("ext1 like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotLike(String value) {
            addCriterion("ext1 not like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1In(List<String> values) {
            addCriterion("ext1 in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotIn(List<String> values) {
            addCriterion("ext1 not in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Between(String value1, String value2) {
            addCriterion("ext1 between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotBetween(String value1, String value2) {
            addCriterion("ext1 not between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(Integer value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(Integer value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(Integer value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(Integer value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(Integer value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<Integer> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<Integer> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(Integer value1, Integer value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("flag not between", value1, value2, "flag");
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
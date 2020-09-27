package com.xiuye.sharp;

import java.util.List;

import com.xiuye.util.cls.XType;

//for if else_if else;match as
//for program code 
//x extension: x1
public class X1 <RESULT>{
	
	 // ================= programming ================

    private static final String IF = "if";
    private static final String ELSE_IF = "else if";
    private static final String ELSE = "else";
    private static final String THEN = "then";
    private static final String MATCH = "match";
    private static final String AS = "as";
    private static final String DEFAULT = "default";


    private class TwoTuple{
        public String token;
        public Object value;


        private TwoTuple() {

        }

        private TwoTuple(String token, Object t) {
            this.token = token;
            this.value = t;
        }
    }

    private List<TwoTuple> tokens;
    private TwoTuple result_token;
    private Object inputValue;
    
    private int token_index = 0;
    
    
    
    //Promise的结果，在end后仍然传给 新的Promise
    //error同
    private RESULT result;
//    private Throwable error;


    protected X1() {
        this.tokens = XType.list();
    }

    protected X1(RESULT r) {
        this();
        this.result = r;

    }


//    private <R> ProgramPromise<R> programPromise(List<TwoTuple> tokens) {
//        if (tokens == null) {
//            throw new RuntimeException("please use begin() to start using promise in ef/eeseEf/else/thenDo !");
//        }
//        return new ProgramPromise<>(tokens);
//    }


    /**
     * syntax :
     * S -> if .then .T
     * T -> else if .then .T | else |ε
     * . 分隔词组
     *
     * @param t
     * @param <I>
     * @return
     */
    public <I> X1<RESULT> IF(I t) {
        addToken(IF, t);
//        return programPromise(tokens);
        return this;
    }

    public <I> X1<RESULT> ELIF(I t) {
        addToken(ELSE_IF, t);
//        return programPromise(tokens);
        return this;
    }

    /**
     * S -> match . F
     * F -> as . A . then . F | defaut | ε
     * A -> as . A | ε
     *
     * @param <I>
     * @param in
     * @return
     */
    public <I> X1<RESULT> MATCH(I in) {
        addToken(MATCH, in);
        return this;
    }

    public <I> X1<RESULT> AS(I in) {
        addToken(AS, in);
        return this;
    }

    public X1<RESULT> DEFAUT(VoidCallbackNoParam callback) {
        addToken(DEFAULT, callback);
        return this;
    }
    
    public <I> X1<RESULT> DEFAUT(VoidCallbackWithParam<I> callback) {
    	addToken(DEFAULT, callback);
    	return this;
    }
    
    public <R,I> X1<RESULT> DEFAUT(ReturnCallbackWithParam<R,I> callback) {
    	addToken(DEFAULT, callback);
    	return this;
    }
    public <R> X1<RESULT> DEFAUT(ReturnCallbackNoParam<R> callback) {
    	addToken(DEFAULT, callback);
    	return this;
    }


    public X1<RESULT> THEN(VoidCallbackNoParam callback) {
        addToken(THEN, callback);
//        return programPromise(tokens);
        return this;
    }
    
    public <I> X1<RESULT> THEN(VoidCallbackWithParam<I> callback) {
    	addToken(THEN, callback);
    	return this;
    }
    
    public <R,I> X1<RESULT> THEN(ReturnCallbackWithParam<R,I> callback) {
    	addToken(THEN, callback);
    	return this;
    }
    public <R> X1<RESULT> THEN(ReturnCallbackNoParam<R> callback) {
    	addToken(THEN, callback);
    	return this;
    }


    public X1<RESULT> ELSE(VoidCallbackNoParam callback) {
        addToken(ELSE, callback);
//        return programPromise(tokens);
        return this;
    }
    
    
    public <I> X1<RESULT> ELSE(VoidCallbackWithParam<I> callback) {
    	addToken(ELSE, callback);
    	return this;
    }
    
    public <R,I> X1<RESULT> ELSE(ReturnCallbackWithParam<R,I> callback) {
    	addToken(ELSE, callback);
    	return this;
    }
    public <R> X1<RESULT> ELSE(ReturnCallbackNoParam<R> callback) {
    	addToken(ELSE, callback);
    	return this;
    }

    private void callTokenCallback() {
        if (result_token != null) {
            if (result_token.value instanceof VoidCallbackNoParam) {
                VoidCallbackNoParam callback = XType.cast(result_token.value);
                callback.vcv();
            }
            else if(result_token.value instanceof VoidCallbackWithParam) {
            	VoidCallbackWithParam<Object> callback = XType.cast(result_token.value);
            	callback.vci(inputValue);            	
            }
            else if(result_token.value instanceof ReturnCallbackNoParam) {
            	ReturnCallbackNoParam<?> callback = XType.cast(result_token.value);
            	callback.rcv();
            }
            else if(result_token.value instanceof ReturnCallbackWithParam){
            	ReturnCallbackWithParam<RESULT,Object> callback = XType.cast(result_token.value);
            	result = callback.rci(inputValue);
            }
            
            inputValue = null;
            
            //clear the previous executed token!!! 
          
            result_token = null;
            
        }

    }

    private void nextTokenError(String token) {
        throw new RuntimeException("The next token should be " + token);
    }

    private String or(String... args) {
        StringBuffer result = new StringBuffer();
        if (args.length > 0) {
            for (int i = 0; i < args.length - 1; i++) {
                result.append(args[i] + " or ");
            }
            result.append(args[args.length - 1]);
        }

        return result.toString();
    }

    private boolean hasNextToken() {
        return token_index < tokens.size();
    }

    private TwoTuple getNextToken() {
        return getToken(token_index++);
    }

    private void tokenIndexDecrement() {
        token_index--;
    }

    private void tokenIndexIncrement() {
        token_index++;
    }

    private TwoTuple currentToken() {
        return getToken(token_index);
    }

    private TwoTuple getToken(int index) {
        return tokens.get(index);
    }

    private void  addToken(String token, Object value) {
        tokens.add(new TwoTuple(token, value));
    }

    private <I> boolean parseBoolean(I t) {
        if (t == null) {
            return false;
        } else {
            boolean b = true;
            if (t instanceof Boolean) {
                b = XType.cast(t);
            }
            return b;
        }
    }

    private void lookAheadSearchedWillRun(Runnable run, String... ts) {
        if (hasNextToken()) {
            TwoTuple next = currentToken();
            for (String t : ts) {
                if (t.equals(next.token)) {
                    run.run();
                    break;
                }
            }
        }
    }

    public interface VoidCallbackNoParam {
        void vcv();
    }
    
    public interface VoidCallbackWithParam<I>{
    	void vci(I t);
    }
    
    
    public interface ReturnCallbackNoParam<R>{
    	R rcv();
    }
    
    public interface ReturnCallbackWithParam<R,I>{
    	R rci(I i);
    }
    

    //自顶向下分析
    //主要是选择分支结构的

    /**
     * S -> S | ε
     * <p>
     * <p>
     * S -> if then T | ε
     * T -> else if then T | else | ε
     * <p>
     * <p>
     * <p>
     * S -> match . F | ε
     * F -> as . A . then . B | default
     * B -> F | ε
     * A -> as . A | ε
     * <p>
     * 为了实现错误检查 和 句型的完整性检查，在实现过程中
     * 对以上的产生式 ，微调了下，
     * 实现仍是自上而下 的
     * <p>
     * 分支语句 每次只有 一个 执行!
     * <p>
     * 现在这个实现的结构非常的复杂
     * 哎 ，我都看不懂了，嘿嘿
     * <p>
     * 先check 并且拿到 为true 的第一个执行的分支
     * check 结构没问题后，最后执行 这个 分支 得到结果!
     * 这里面涉及到超前搜索，主要为了保证执行结构的完整性
     * ε <=> 直接执行结束，结束递归 等等!
     */
    private void S_token() {
        if (hasNextToken()) {//递归的终止条件!
            //S -> if then T
            TwoTuple start_token = getNextToken();
            /**
             * S -> if then T | ε
             * T -> else if then T | else | ε
             * if then must be all in one
             *
             * 	完整性 if then / if then else if then
             * 	/if then else
             */
            if (IF.equals(start_token.token)) {
                TwoTuple then_token;
                if (hasNextToken()
                        && THEN.equals((then_token = getNextToken()).token)) {
                    // 将要执行的token装入到 result_token中！
                    if (result_token == null && parseBoolean(start_token.value)) {
                        result_token = then_token;
                        inputValue = start_token.value;                        
                    }
                    // T -> else if then T | else | ε
                    T_token();
                } else {
                    nextTokenError(or(THEN));
                }

            }
            /**
             *	顺序性的要求 
             *	 完整性的要求 if then / if else / if else if else
             *   /match as then / match as then default / match default! 
             *	这里 ε 表示 没有下一个 token 符号 输入 ，直接 S_token结束 
             *	非递归 终结符单词 才能 继续调用下一个 分支 语句？	
             *	需要 first集合？
             *	或者 follow 集合？
             *	select?
             * S -> match . F | ε
             * F -> as . A . then . B | default
             * B -> F | ε // match as then or match default !!!
             * A -> as . A | ε
             */
            else if (MATCH.equals(start_token.token)) {/* syntax check */

                F_token(start_token.value);

            } else {
                nextTokenError(or(IF, MATCH));
            }

            callTokenCallback();

            //check and execute next program fragment!
            S_token();

        }
    }

    /**
     * F handler
     * after match it should be as or default!!!
     * F -> as . A . then . B | default
     * B -> F | ε //保证了 match as then 或 match default 的完整性!
     * A -> as . A | ε
     *
     * @param matchValue
     */
    private void F_token(Object matchValue) {

        if (hasNextToken()) {

            TwoTuple f_token = getNextToken();

            if (AS.equals(f_token.token)) {
                boolean asOK = matchValue != null
                        &&
                        matchValue.equals(f_token.value)
                                |
                                A_token(matchValue);// left
                TwoTuple then_token;
                if (hasNextToken() && THEN.equals((then_token = getNextToken()).token)/* syntax check */) {
                    // 正式 匹配的有 then 然后执行!
                    if (result_token == null && asOK) {
                        result_token = then_token;
                        inputValue = matchValue;
                    }

                    //递归条件，也是超前搜索
                    //为了 match as then的完整性
                    lookAheadSearchedWillRun(() -> {
                        B_token(matchValue);
                    }, AS, DEFAULT);//开始 中间 结尾 的单词要分清楚
//					B_token(matchValue);
                } else {
                    nextTokenError(or(THEN));
                }
            } else if (DEFAULT.equals(f_token.token)) {
                if (result_token == null) {
                    result_token = f_token;
                    inputValue = matchValue;
                }
            } else {
                nextTokenError(or(AS, DEFAULT));
            }

        } else {
            nextTokenError(or(AS, DEFAULT));
        }

    }

    /**
     * match as then 必须是一个整体!
     * B -> F | ε
     * A -> as . A | ε
     *
     * @param matchValue
     */
    private void B_token(Object matchValue) {
        if (hasNextToken()) {
            F_token(matchValue);
        }
        //ε <=> 没有代码可执行，没有下一步可执行!
    }

    //右递归

    /**
     * as handler
     * A -> as . A | ε
     *
     * @param matchValue
     * @return
     */
    private boolean A_token(Object matchValue) {
        boolean matched = false;
        if (hasNextToken()) {
            TwoTuple as_token = getNextToken();
            if (AS.equals(as_token.token)) {
                if (result_token == null //说明还没有匹配的有!
                        && !matched //本次 as 的匹配 已经匹配过的话 matched = true!
                        //,可以加快速度(小优化)
                        && matchValue != null
//						&& as_token.value != null
                        //只要 matchValue 存在就可以 调用equals 了
                        //并且 都为 null 的时候 ，直接返回false （暗含）
                        && matchValue.equals(as_token.value)
                ) {
                    matched = true;
                }
                matched = matched || A_token(matchValue);
            } else {
                //多取一次未处理的话，应该回溯一个
                //也是递归终止条件
                tokenIndexDecrement();
            }

        }

        return matched;
    }

    /**
     * T -> else if then 【加入超前搜索!保证完整性】 T | else | ε
     */
    private void T_token() {

        // 需要超前遍历 才能 可以随意 的调用任何 完整结构 的语句!
        lookAheadSearchedWillRun(() -> {
            TwoTuple t_token = getNextToken();
            if (ELSE_IF.equals(t_token.token)) {
                TwoTuple then_token;
                if (hasNextToken() && THEN.equals((then_token = getNextToken()).token)) {

                    if (result_token == null && parseBoolean(t_token.value)) {
                        result_token = then_token;
                        inputValue = t_token.value;
                    }
                    // T -> else if then T | else | E
                    // recursion

                    T_token();
                } else {
                    nextTokenError(or(THEN));
                }
            } else if (ELSE.equals(t_token.token)) {
                if (result_token == null) {
                    result_token = t_token;
                    inputValue = null;
                }

            }
            //if then 检验是完整的，为了match as then 或者 下一 if then
            //不需要这一句，去check : if then else if then/
            // if then else 因为 if then在S_token中实现是完整的
//          else {
//              nextTokenError(or(ELSE_IF, ELSE));
//          }

        }, ELSE_IF, ELSE);

    }


    //不支持 if else 嵌套！
    private void analyzeTokensAndExec() {
        //分析语法结构
        //然后执行！

        S_token();


    }


    public X<RESULT> end() {

        analyzeTokensAndExec();
//        clear tokens!
        tokens = null;
        return X.of(result);
    }
    

	
}

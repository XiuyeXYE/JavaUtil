package com.xiuye.weapon.dark;

import java.util.List;

import com.xiuye.util.cls.XType;
import com.xiuye.weapon.dark.Promise.VoidCallbackNoParam;

public class ProgramPromise<RESULT> {
	
	 // ================= programming ================

    private static final String IF = "if";
    private static final String ELSE_IF = "else if";
    private static final String ELSE = "else";
    private static final String THEN = "then";
    private static final String MATCH = "match";
    private static final String AS = "as";
    private static final String DEFAULT = "default";


    private class TwoTuple {
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
    private int token_index = 0;

    protected ProgramPromise(List<TwoTuple> tokens) {
        this.tokens = tokens;
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
    public <I> ProgramPromise<RESULT> ef(I t) {
        addToken(IF, t);
//        return programPromise(tokens);
        return this;
    }

    public <I> ProgramPromise<RESULT> eeseEf(I t) {
        addToken(ELSE_IF, t);
//        return programPromise(tokens);
        return this;
    }
    
    /**
     *
     * S -> match . F
     * F -> as . A . then . F | defaut | ε
     * A -> as . A | ε
     * 
     * @param <I>
     * @param in
     * @return
     */
    public <I> ProgramPromise<RESULT> match(I in){
    	addToken(MATCH, in);
    	return this;
    }
    
    public <I> ProgramPromise<RESULT> as(I in){
    	addToken(AS, in);
    	return this;
    }
    
    public ProgramPromise<RESULT> defaut(VoidCallbackNoParam callback){
    	addToken(DEFAULT, callback);
    	return this;
    }
    
   


    public ProgramPromise<RESULT> then(VoidCallbackNoParam callback) {
        addToken(THEN, callback);
//        return programPromise(tokens);
        return this;
    }



    public ProgramPromise<RESULT> eese(VoidCallbackNoParam callback) {
        addToken(ELSE, callback);
//        return programPromise(tokens);
        return this;
    }

    private void callTokenCallback() {
    	if(result_token != null) {
    		if (result_token.value instanceof VoidCallbackNoParam) {
                VoidCallbackNoParam callback = XType.cast(result_token.value);
                callback.vcv();
            } 
    	}        

    }
    
    private void nextTokenError(String token) {
    	throw new RuntimeException("The next token should be "+token);
    }

    private String or(String ...args) {
		StringBuffer result = new StringBuffer("");
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
    
    private void addToken(String token,Object value) {
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
    
    //自顶向下分析
    //主要是选择分支结构的
    private void S_token() {
    	if(hasNextToken()) {
    		//S -> if then T
			TwoTuple start_token = getNextToken();
			if (IF.equals(start_token.token)) {
				TwoTuple then_token;
				if (hasNextToken()
						&& THEN.equals((then_token = getNextToken()).token)) {
					// 将要执行的token装入到 result_token中！
					if (result_token == null && parseBoolean(start_token.value)) {
						result_token = then_token;
					}
					// T -> else if then T | else | ε
					T_token();
				}
				else {
					nextTokenError(or(THEN));
				}
				
    		}
			/**
			 * 
			 * S -> match . F
			 * F -> as . A . then . F | defaut | ε
			 * A -> as . A | ε
			 */
			else if (MATCH.equals(start_token.token)) {/* syntax check */

				F_token(start_token.value);

			} else {
				nextTokenError(or(IF, MATCH));
			}

    	}
//    	else{
//    		receiveIt();
//    		acceptIt();
//    	}
    }

    /**
     * F handler
     * F -> as . A . then . F | defaut | ε
     * @param matchValue
     */
	private void F_token(Object matchValue) {
		
		if(hasNextToken()) {
			
			TwoTuple f_token = getNextToken();
			
			if(AS.equals(f_token.token)) {
				boolean asOK = matchValue != null
						&& matchValue.equals(f_token.value) 
						| A_token(matchValue);// left recursion
				
				TwoTuple then_token;
				if (hasNextToken() && THEN.equals((then_token = getNextToken()).token)/* syntax check */) {
					//正式 匹配的有 then 然后执行!
					if (result_token == null && asOK) {
						result_token = then_token;
					}
//					//超前判断，没有元素了就返回！递归的终止条件
//					if(!hasNextToken()) {
//						return;
//					}
					F_token(matchValue);
				}
				else {
					nextTokenError(or(THEN));
				}
				
				
			}else if(DEFAULT.equals(f_token.token)) {
				if (result_token == null) {
					result_token = f_token;
				}
			}
			else {
				nextTokenError(or(AS,DEFAULT));
			}
			
		}
		
		

	}

	//右递归
	/**
	 * as handler
	 * A -> as . A | ε
	 * @param matchValue
	 * @return
	 */
	private boolean A_token(Object matchValue) {
		boolean matched = false;
		if(hasNextToken()) {
			TwoTuple as_token = getNextToken();
			if(AS.equals(as_token.token)) {
				if(result_token == null //说明还没有匹配的有!
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
			}
			else {
				//多取一次未处理的话，应该回溯一个
				//也是递归终止条件
				tokenIndexDecrement();
			}
			
		}
		
		return matched;
	}

	/**
	 * T -> else if then T | else | ε 
	 */
	private void T_token() {
		if (hasNextToken()) {
			TwoTuple t_token = getNextToken();
			if (ELSE_IF.equals(t_token.token)) {
				TwoTuple then_token;
				if (hasNextToken() && THEN.equals((then_token = getNextToken()).token)) {

					if (result_token == null && parseBoolean(t_token.value)) {
						result_token = then_token;
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
				}
//    			S_token(it);
			} else {
				nextTokenError(or(ELSE_IF, ELSE));
			}

		}  	
    	
    			
		
	}

	//不支持 if else 嵌套！
    private void analyzeTokensAndExec() {
    	//分析语法结构
    	//然后执行！
    	
    	S_token();
    	callTokenCallback();
    	
    }


    public <R> Promise<R> end() {

        analyzeTokensAndExec();
//        clear tokens!
        tokens = null;
        return Promise.of();
    }



}

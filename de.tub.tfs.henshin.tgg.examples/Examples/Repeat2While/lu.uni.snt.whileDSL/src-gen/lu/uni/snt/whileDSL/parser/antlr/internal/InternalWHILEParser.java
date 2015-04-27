/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package lu.uni.snt.whileDSL.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import lu.uni.snt.whileDSL.services.WHILEGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalWHILEParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'while'", "'('", "')'", "'{'", "'}'", "'='", "';'", "'def'", "'/#'", "'#/'", "'&&'", "'||'", "'=='", "'!'", "'input'"
    };
    public static final int RULE_ID=4;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__19=19;
    public static final int RULE_STRING=5;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=6;
    public static final int RULE_WS=9;

    // delegates
    // delegators


        public InternalWHILEParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalWHILEParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalWHILEParser.tokenNames; }
    public String getGrammarFileName() { return "../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g"; }



     	private WHILEGrammarAccess grammarAccess;
     	
        public InternalWHILEParser(TokenStream input, WHILEGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "WProgram";	
       	}
       	
       	@Override
       	protected WHILEGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleWProgram"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:67:1: entryRuleWProgram returns [EObject current=null] : iv_ruleWProgram= ruleWProgram EOF ;
    public final EObject entryRuleWProgram() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWProgram = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:68:2: (iv_ruleWProgram= ruleWProgram EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:69:2: iv_ruleWProgram= ruleWProgram EOF
            {
             newCompositeNode(grammarAccess.getWProgramRule()); 
            pushFollow(FOLLOW_ruleWProgram_in_entryRuleWProgram75);
            iv_ruleWProgram=ruleWProgram();

            state._fsp--;

             current =iv_ruleWProgram; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleWProgram85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWProgram"


    // $ANTLR start "ruleWProgram"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:76:1: ruleWProgram returns [EObject current=null] : ( (lv_fst_0_0= ruleFgmnt_LST_Elem ) ) ;
    public final EObject ruleWProgram() throws RecognitionException {
        EObject current = null;

        EObject lv_fst_0_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:79:28: ( ( (lv_fst_0_0= ruleFgmnt_LST_Elem ) ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:80:1: ( (lv_fst_0_0= ruleFgmnt_LST_Elem ) )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:80:1: ( (lv_fst_0_0= ruleFgmnt_LST_Elem ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:81:1: (lv_fst_0_0= ruleFgmnt_LST_Elem )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:81:1: (lv_fst_0_0= ruleFgmnt_LST_Elem )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:82:3: lv_fst_0_0= ruleFgmnt_LST_Elem
            {
             
            	        newCompositeNode(grammarAccess.getWProgramAccess().getFstFgmnt_LST_ElemParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleFgmnt_LST_Elem_in_ruleWProgram130);
            lv_fst_0_0=ruleFgmnt_LST_Elem();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getWProgramRule());
            	        }
                   		set(
                   			current, 
                   			"fst",
                    		lv_fst_0_0, 
                    		"Fgmnt_LST_Elem");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWProgram"


    // $ANTLR start "entryRuleFgmnt_LST_Elem"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:106:1: entryRuleFgmnt_LST_Elem returns [EObject current=null] : iv_ruleFgmnt_LST_Elem= ruleFgmnt_LST_Elem EOF ;
    public final EObject entryRuleFgmnt_LST_Elem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFgmnt_LST_Elem = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:107:2: (iv_ruleFgmnt_LST_Elem= ruleFgmnt_LST_Elem EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:108:2: iv_ruleFgmnt_LST_Elem= ruleFgmnt_LST_Elem EOF
            {
             newCompositeNode(grammarAccess.getFgmnt_LST_ElemRule()); 
            pushFollow(FOLLOW_ruleFgmnt_LST_Elem_in_entryRuleFgmnt_LST_Elem165);
            iv_ruleFgmnt_LST_Elem=ruleFgmnt_LST_Elem();

            state._fsp--;

             current =iv_ruleFgmnt_LST_Elem; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFgmnt_LST_Elem175); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFgmnt_LST_Elem"


    // $ANTLR start "ruleFgmnt_LST_Elem"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:115:1: ruleFgmnt_LST_Elem returns [EObject current=null] : ( (this_While_0= ruleWhile | this_Var_Def_1= ruleVar_Def | this_Fn_Call_2= ruleFn_Call | this_Fn_Def_3= ruleFn_Def | this_Comment_4= ruleComment ) ( (lv_next_5_0= ruleFgmnt_LST_Elem ) )? ) ;
    public final EObject ruleFgmnt_LST_Elem() throws RecognitionException {
        EObject current = null;

        EObject this_While_0 = null;

        EObject this_Var_Def_1 = null;

        EObject this_Fn_Call_2 = null;

        EObject this_Fn_Def_3 = null;

        EObject this_Comment_4 = null;

        EObject lv_next_5_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:118:28: ( ( (this_While_0= ruleWhile | this_Var_Def_1= ruleVar_Def | this_Fn_Call_2= ruleFn_Call | this_Fn_Def_3= ruleFn_Def | this_Comment_4= ruleComment ) ( (lv_next_5_0= ruleFgmnt_LST_Elem ) )? ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:119:1: ( (this_While_0= ruleWhile | this_Var_Def_1= ruleVar_Def | this_Fn_Call_2= ruleFn_Call | this_Fn_Def_3= ruleFn_Def | this_Comment_4= ruleComment ) ( (lv_next_5_0= ruleFgmnt_LST_Elem ) )? )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:119:1: ( (this_While_0= ruleWhile | this_Var_Def_1= ruleVar_Def | this_Fn_Call_2= ruleFn_Call | this_Fn_Def_3= ruleFn_Def | this_Comment_4= ruleComment ) ( (lv_next_5_0= ruleFgmnt_LST_Elem ) )? )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:119:2: (this_While_0= ruleWhile | this_Var_Def_1= ruleVar_Def | this_Fn_Call_2= ruleFn_Call | this_Fn_Def_3= ruleFn_Def | this_Comment_4= ruleComment ) ( (lv_next_5_0= ruleFgmnt_LST_Elem ) )?
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:119:2: (this_While_0= ruleWhile | this_Var_Def_1= ruleVar_Def | this_Fn_Call_2= ruleFn_Call | this_Fn_Def_3= ruleFn_Def | this_Comment_4= ruleComment )
            int alt1=5;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt1=1;
                }
                break;
            case RULE_ID:
                {
                int LA1_2 = input.LA(2);

                if ( (LA1_2==12) ) {
                    alt1=3;
                }
                else if ( (LA1_2==16) ) {
                    alt1=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 2, input);

                    throw nvae;
                }
                }
                break;
            case 18:
                {
                alt1=4;
                }
                break;
            case 19:
                {
                alt1=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:120:5: this_While_0= ruleWhile
                    {
                     
                            newCompositeNode(grammarAccess.getFgmnt_LST_ElemAccess().getWhileParserRuleCall_0_0()); 
                        
                    pushFollow(FOLLOW_ruleWhile_in_ruleFgmnt_LST_Elem223);
                    this_While_0=ruleWhile();

                    state._fsp--;

                     
                            current = this_While_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:130:5: this_Var_Def_1= ruleVar_Def
                    {
                     
                            newCompositeNode(grammarAccess.getFgmnt_LST_ElemAccess().getVar_DefParserRuleCall_0_1()); 
                        
                    pushFollow(FOLLOW_ruleVar_Def_in_ruleFgmnt_LST_Elem250);
                    this_Var_Def_1=ruleVar_Def();

                    state._fsp--;

                     
                            current = this_Var_Def_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:140:5: this_Fn_Call_2= ruleFn_Call
                    {
                     
                            newCompositeNode(grammarAccess.getFgmnt_LST_ElemAccess().getFn_CallParserRuleCall_0_2()); 
                        
                    pushFollow(FOLLOW_ruleFn_Call_in_ruleFgmnt_LST_Elem277);
                    this_Fn_Call_2=ruleFn_Call();

                    state._fsp--;

                     
                            current = this_Fn_Call_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:150:5: this_Fn_Def_3= ruleFn_Def
                    {
                     
                            newCompositeNode(grammarAccess.getFgmnt_LST_ElemAccess().getFn_DefParserRuleCall_0_3()); 
                        
                    pushFollow(FOLLOW_ruleFn_Def_in_ruleFgmnt_LST_Elem304);
                    this_Fn_Def_3=ruleFn_Def();

                    state._fsp--;

                     
                            current = this_Fn_Def_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:160:5: this_Comment_4= ruleComment
                    {
                     
                            newCompositeNode(grammarAccess.getFgmnt_LST_ElemAccess().getCommentParserRuleCall_0_4()); 
                        
                    pushFollow(FOLLOW_ruleComment_in_ruleFgmnt_LST_Elem331);
                    this_Comment_4=ruleComment();

                    state._fsp--;

                     
                            current = this_Comment_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:168:2: ( (lv_next_5_0= ruleFgmnt_LST_Elem ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==RULE_ID||LA2_0==11||(LA2_0>=18 && LA2_0<=19)) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:169:1: (lv_next_5_0= ruleFgmnt_LST_Elem )
                    {
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:169:1: (lv_next_5_0= ruleFgmnt_LST_Elem )
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:170:3: lv_next_5_0= ruleFgmnt_LST_Elem
                    {
                     
                    	        newCompositeNode(grammarAccess.getFgmnt_LST_ElemAccess().getNextFgmnt_LST_ElemParserRuleCall_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleFgmnt_LST_Elem_in_ruleFgmnt_LST_Elem352);
                    lv_next_5_0=ruleFgmnt_LST_Elem();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFgmnt_LST_ElemRule());
                    	        }
                           		set(
                           			current, 
                           			"next",
                            		lv_next_5_0, 
                            		"Fgmnt_LST_Elem");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFgmnt_LST_Elem"


    // $ANTLR start "entryRuleWhile"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:194:1: entryRuleWhile returns [EObject current=null] : iv_ruleWhile= ruleWhile EOF ;
    public final EObject entryRuleWhile() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhile = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:195:2: (iv_ruleWhile= ruleWhile EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:196:2: iv_ruleWhile= ruleWhile EOF
            {
             newCompositeNode(grammarAccess.getWhileRule()); 
            pushFollow(FOLLOW_ruleWhile_in_entryRuleWhile389);
            iv_ruleWhile=ruleWhile();

            state._fsp--;

             current =iv_ruleWhile; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleWhile399); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWhile"


    // $ANTLR start "ruleWhile"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:203:1: ruleWhile returns [EObject current=null] : (otherlv_0= 'while' otherlv_1= '(' ( (lv_expr_2_0= ruleExpr ) ) otherlv_3= ')' otherlv_4= '{' ( (lv_fgmnt_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}' ) ;
    public final EObject ruleWhile() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_expr_2_0 = null;

        EObject lv_fgmnt_5_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:206:28: ( (otherlv_0= 'while' otherlv_1= '(' ( (lv_expr_2_0= ruleExpr ) ) otherlv_3= ')' otherlv_4= '{' ( (lv_fgmnt_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}' ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:207:1: (otherlv_0= 'while' otherlv_1= '(' ( (lv_expr_2_0= ruleExpr ) ) otherlv_3= ')' otherlv_4= '{' ( (lv_fgmnt_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}' )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:207:1: (otherlv_0= 'while' otherlv_1= '(' ( (lv_expr_2_0= ruleExpr ) ) otherlv_3= ')' otherlv_4= '{' ( (lv_fgmnt_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}' )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:207:3: otherlv_0= 'while' otherlv_1= '(' ( (lv_expr_2_0= ruleExpr ) ) otherlv_3= ')' otherlv_4= '{' ( (lv_fgmnt_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleWhile436); 

                	newLeafNode(otherlv_0, grammarAccess.getWhileAccess().getWhileKeyword_0());
                
            otherlv_1=(Token)match(input,12,FOLLOW_12_in_ruleWhile448); 

                	newLeafNode(otherlv_1, grammarAccess.getWhileAccess().getLeftParenthesisKeyword_1());
                
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:215:1: ( (lv_expr_2_0= ruleExpr ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:216:1: (lv_expr_2_0= ruleExpr )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:216:1: (lv_expr_2_0= ruleExpr )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:217:3: lv_expr_2_0= ruleExpr
            {
             
            	        newCompositeNode(grammarAccess.getWhileAccess().getExprExprParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleExpr_in_ruleWhile469);
            lv_expr_2_0=ruleExpr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getWhileRule());
            	        }
                   		set(
                   			current, 
                   			"expr",
                    		lv_expr_2_0, 
                    		"Expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,13,FOLLOW_13_in_ruleWhile481); 

                	newLeafNode(otherlv_3, grammarAccess.getWhileAccess().getRightParenthesisKeyword_3());
                
            otherlv_4=(Token)match(input,14,FOLLOW_14_in_ruleWhile493); 

                	newLeafNode(otherlv_4, grammarAccess.getWhileAccess().getLeftCurlyBracketKeyword_4());
                
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:241:1: ( (lv_fgmnt_5_0= ruleFgmnt_LST_Elem ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:242:1: (lv_fgmnt_5_0= ruleFgmnt_LST_Elem )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:242:1: (lv_fgmnt_5_0= ruleFgmnt_LST_Elem )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:243:3: lv_fgmnt_5_0= ruleFgmnt_LST_Elem
            {
             
            	        newCompositeNode(grammarAccess.getWhileAccess().getFgmntFgmnt_LST_ElemParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleFgmnt_LST_Elem_in_ruleWhile514);
            lv_fgmnt_5_0=ruleFgmnt_LST_Elem();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getWhileRule());
            	        }
                   		set(
                   			current, 
                   			"fgmnt",
                    		lv_fgmnt_5_0, 
                    		"Fgmnt_LST_Elem");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_6=(Token)match(input,15,FOLLOW_15_in_ruleWhile526); 

                	newLeafNode(otherlv_6, grammarAccess.getWhileAccess().getRightCurlyBracketKeyword_6());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWhile"


    // $ANTLR start "entryRuleVar_Def"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:271:1: entryRuleVar_Def returns [EObject current=null] : iv_ruleVar_Def= ruleVar_Def EOF ;
    public final EObject entryRuleVar_Def() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVar_Def = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:272:2: (iv_ruleVar_Def= ruleVar_Def EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:273:2: iv_ruleVar_Def= ruleVar_Def EOF
            {
             newCompositeNode(grammarAccess.getVar_DefRule()); 
            pushFollow(FOLLOW_ruleVar_Def_in_entryRuleVar_Def562);
            iv_ruleVar_Def=ruleVar_Def();

            state._fsp--;

             current =iv_ruleVar_Def; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVar_Def572); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVar_Def"


    // $ANTLR start "ruleVar_Def"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:280:1: ruleVar_Def returns [EObject current=null] : ( ( (lv_left_0_0= ruleVar ) ) otherlv_1= '=' ( (lv_right_2_0= ruleExpr ) ) otherlv_3= ';' ) ;
    public final EObject ruleVar_Def() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:283:28: ( ( ( (lv_left_0_0= ruleVar ) ) otherlv_1= '=' ( (lv_right_2_0= ruleExpr ) ) otherlv_3= ';' ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:284:1: ( ( (lv_left_0_0= ruleVar ) ) otherlv_1= '=' ( (lv_right_2_0= ruleExpr ) ) otherlv_3= ';' )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:284:1: ( ( (lv_left_0_0= ruleVar ) ) otherlv_1= '=' ( (lv_right_2_0= ruleExpr ) ) otherlv_3= ';' )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:284:2: ( (lv_left_0_0= ruleVar ) ) otherlv_1= '=' ( (lv_right_2_0= ruleExpr ) ) otherlv_3= ';'
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:284:2: ( (lv_left_0_0= ruleVar ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:285:1: (lv_left_0_0= ruleVar )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:285:1: (lv_left_0_0= ruleVar )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:286:3: lv_left_0_0= ruleVar
            {
             
            	        newCompositeNode(grammarAccess.getVar_DefAccess().getLeftVarParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleVar_in_ruleVar_Def618);
            lv_left_0_0=ruleVar();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getVar_DefRule());
            	        }
                   		set(
                   			current, 
                   			"left",
                    		lv_left_0_0, 
                    		"Var");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_16_in_ruleVar_Def630); 

                	newLeafNode(otherlv_1, grammarAccess.getVar_DefAccess().getEqualsSignKeyword_1());
                
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:306:1: ( (lv_right_2_0= ruleExpr ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:307:1: (lv_right_2_0= ruleExpr )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:307:1: (lv_right_2_0= ruleExpr )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:308:3: lv_right_2_0= ruleExpr
            {
             
            	        newCompositeNode(grammarAccess.getVar_DefAccess().getRightExprParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleExpr_in_ruleVar_Def651);
            lv_right_2_0=ruleExpr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getVar_DefRule());
            	        }
                   		set(
                   			current, 
                   			"right",
                    		lv_right_2_0, 
                    		"Expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,17,FOLLOW_17_in_ruleVar_Def663); 

                	newLeafNode(otherlv_3, grammarAccess.getVar_DefAccess().getSemicolonKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVar_Def"


    // $ANTLR start "entryRuleFn_Call"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:336:1: entryRuleFn_Call returns [EObject current=null] : iv_ruleFn_Call= ruleFn_Call EOF ;
    public final EObject entryRuleFn_Call() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFn_Call = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:337:2: (iv_ruleFn_Call= ruleFn_Call EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:338:2: iv_ruleFn_Call= ruleFn_Call EOF
            {
             newCompositeNode(grammarAccess.getFn_CallRule()); 
            pushFollow(FOLLOW_ruleFn_Call_in_entryRuleFn_Call699);
            iv_ruleFn_Call=ruleFn_Call();

            state._fsp--;

             current =iv_ruleFn_Call; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFn_Call709); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFn_Call"


    // $ANTLR start "ruleFn_Call"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:345:1: ruleFn_Call returns [EObject current=null] : ( ( (lv_nameF_0_0= RULE_ID ) ) otherlv_1= '(' otherlv_2= ')' otherlv_3= ';' ) ;
    public final EObject ruleFn_Call() throws RecognitionException {
        EObject current = null;

        Token lv_nameF_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;

         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:348:28: ( ( ( (lv_nameF_0_0= RULE_ID ) ) otherlv_1= '(' otherlv_2= ')' otherlv_3= ';' ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:349:1: ( ( (lv_nameF_0_0= RULE_ID ) ) otherlv_1= '(' otherlv_2= ')' otherlv_3= ';' )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:349:1: ( ( (lv_nameF_0_0= RULE_ID ) ) otherlv_1= '(' otherlv_2= ')' otherlv_3= ';' )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:349:2: ( (lv_nameF_0_0= RULE_ID ) ) otherlv_1= '(' otherlv_2= ')' otherlv_3= ';'
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:349:2: ( (lv_nameF_0_0= RULE_ID ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:350:1: (lv_nameF_0_0= RULE_ID )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:350:1: (lv_nameF_0_0= RULE_ID )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:351:3: lv_nameF_0_0= RULE_ID
            {
            lv_nameF_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFn_Call751); 

            			newLeafNode(lv_nameF_0_0, grammarAccess.getFn_CallAccess().getNameFIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getFn_CallRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"nameF",
                    		lv_nameF_0_0, 
                    		"ID");
            	    

            }


            }

            otherlv_1=(Token)match(input,12,FOLLOW_12_in_ruleFn_Call768); 

                	newLeafNode(otherlv_1, grammarAccess.getFn_CallAccess().getLeftParenthesisKeyword_1());
                
            otherlv_2=(Token)match(input,13,FOLLOW_13_in_ruleFn_Call780); 

                	newLeafNode(otherlv_2, grammarAccess.getFn_CallAccess().getRightParenthesisKeyword_2());
                
            otherlv_3=(Token)match(input,17,FOLLOW_17_in_ruleFn_Call792); 

                	newLeafNode(otherlv_3, grammarAccess.getFn_CallAccess().getSemicolonKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFn_Call"


    // $ANTLR start "entryRuleFn_Def"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:387:1: entryRuleFn_Def returns [EObject current=null] : iv_ruleFn_Def= ruleFn_Def EOF ;
    public final EObject entryRuleFn_Def() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFn_Def = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:388:2: (iv_ruleFn_Def= ruleFn_Def EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:389:2: iv_ruleFn_Def= ruleFn_Def EOF
            {
             newCompositeNode(grammarAccess.getFn_DefRule()); 
            pushFollow(FOLLOW_ruleFn_Def_in_entryRuleFn_Def828);
            iv_ruleFn_Def=ruleFn_Def();

            state._fsp--;

             current =iv_ruleFn_Def; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFn_Def838); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFn_Def"


    // $ANTLR start "ruleFn_Def"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:396:1: ruleFn_Def returns [EObject current=null] : (otherlv_0= 'def' ( (lv_nameF_1_0= RULE_ID ) ) otherlv_2= '(' otherlv_3= ')' otherlv_4= '{' ( (lv_body_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}' otherlv_7= ';' ) ;
    public final EObject ruleFn_Def() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_nameF_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_body_5_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:399:28: ( (otherlv_0= 'def' ( (lv_nameF_1_0= RULE_ID ) ) otherlv_2= '(' otherlv_3= ')' otherlv_4= '{' ( (lv_body_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}' otherlv_7= ';' ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:400:1: (otherlv_0= 'def' ( (lv_nameF_1_0= RULE_ID ) ) otherlv_2= '(' otherlv_3= ')' otherlv_4= '{' ( (lv_body_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}' otherlv_7= ';' )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:400:1: (otherlv_0= 'def' ( (lv_nameF_1_0= RULE_ID ) ) otherlv_2= '(' otherlv_3= ')' otherlv_4= '{' ( (lv_body_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}' otherlv_7= ';' )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:400:3: otherlv_0= 'def' ( (lv_nameF_1_0= RULE_ID ) ) otherlv_2= '(' otherlv_3= ')' otherlv_4= '{' ( (lv_body_5_0= ruleFgmnt_LST_Elem ) ) otherlv_6= '}' otherlv_7= ';'
            {
            otherlv_0=(Token)match(input,18,FOLLOW_18_in_ruleFn_Def875); 

                	newLeafNode(otherlv_0, grammarAccess.getFn_DefAccess().getDefKeyword_0());
                
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:404:1: ( (lv_nameF_1_0= RULE_ID ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:405:1: (lv_nameF_1_0= RULE_ID )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:405:1: (lv_nameF_1_0= RULE_ID )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:406:3: lv_nameF_1_0= RULE_ID
            {
            lv_nameF_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFn_Def892); 

            			newLeafNode(lv_nameF_1_0, grammarAccess.getFn_DefAccess().getNameFIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getFn_DefRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"nameF",
                    		lv_nameF_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleFn_Def909); 

                	newLeafNode(otherlv_2, grammarAccess.getFn_DefAccess().getLeftParenthesisKeyword_2());
                
            otherlv_3=(Token)match(input,13,FOLLOW_13_in_ruleFn_Def921); 

                	newLeafNode(otherlv_3, grammarAccess.getFn_DefAccess().getRightParenthesisKeyword_3());
                
            otherlv_4=(Token)match(input,14,FOLLOW_14_in_ruleFn_Def933); 

                	newLeafNode(otherlv_4, grammarAccess.getFn_DefAccess().getLeftCurlyBracketKeyword_4());
                
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:434:1: ( (lv_body_5_0= ruleFgmnt_LST_Elem ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:435:1: (lv_body_5_0= ruleFgmnt_LST_Elem )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:435:1: (lv_body_5_0= ruleFgmnt_LST_Elem )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:436:3: lv_body_5_0= ruleFgmnt_LST_Elem
            {
             
            	        newCompositeNode(grammarAccess.getFn_DefAccess().getBodyFgmnt_LST_ElemParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleFgmnt_LST_Elem_in_ruleFn_Def954);
            lv_body_5_0=ruleFgmnt_LST_Elem();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getFn_DefRule());
            	        }
                   		set(
                   			current, 
                   			"body",
                    		lv_body_5_0, 
                    		"Fgmnt_LST_Elem");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_6=(Token)match(input,15,FOLLOW_15_in_ruleFn_Def966); 

                	newLeafNode(otherlv_6, grammarAccess.getFn_DefAccess().getRightCurlyBracketKeyword_6());
                
            otherlv_7=(Token)match(input,17,FOLLOW_17_in_ruleFn_Def978); 

                	newLeafNode(otherlv_7, grammarAccess.getFn_DefAccess().getSemicolonKeyword_7());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFn_Def"


    // $ANTLR start "entryRuleComment"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:468:1: entryRuleComment returns [EObject current=null] : iv_ruleComment= ruleComment EOF ;
    public final EObject entryRuleComment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComment = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:469:2: (iv_ruleComment= ruleComment EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:470:2: iv_ruleComment= ruleComment EOF
            {
             newCompositeNode(grammarAccess.getCommentRule()); 
            pushFollow(FOLLOW_ruleComment_in_entryRuleComment1014);
            iv_ruleComment=ruleComment();

            state._fsp--;

             current =iv_ruleComment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleComment1024); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleComment"


    // $ANTLR start "ruleComment"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:477:1: ruleComment returns [EObject current=null] : (otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/' ) ;
    public final EObject ruleComment() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_comment_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:480:28: ( (otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/' ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:481:1: (otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/' )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:481:1: (otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/' )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:481:3: otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_19_in_ruleComment1061); 

                	newLeafNode(otherlv_0, grammarAccess.getCommentAccess().getSolidusNumberSignKeyword_0());
                
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:485:1: ( (lv_comment_1_0= RULE_STRING ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:486:1: (lv_comment_1_0= RULE_STRING )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:486:1: (lv_comment_1_0= RULE_STRING )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:487:3: lv_comment_1_0= RULE_STRING
            {
            lv_comment_1_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleComment1078); 

            			newLeafNode(lv_comment_1_0, grammarAccess.getCommentAccess().getCommentSTRINGTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getCommentRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"comment",
                    		lv_comment_1_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_2=(Token)match(input,20,FOLLOW_20_in_ruleComment1095); 

                	newLeafNode(otherlv_2, grammarAccess.getCommentAccess().getNumberSignSolidusKeyword_2());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComment"


    // $ANTLR start "entryRuleExpr"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:515:1: entryRuleExpr returns [EObject current=null] : iv_ruleExpr= ruleExpr EOF ;
    public final EObject entryRuleExpr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpr = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:516:2: (iv_ruleExpr= ruleExpr EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:517:2: iv_ruleExpr= ruleExpr EOF
            {
             newCompositeNode(grammarAccess.getExprRule()); 
            pushFollow(FOLLOW_ruleExpr_in_entryRuleExpr1131);
            iv_ruleExpr=ruleExpr();

            state._fsp--;

             current =iv_ruleExpr; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpr1141); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpr"


    // $ANTLR start "ruleExpr"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:524:1: ruleExpr returns [EObject current=null] : ( (lv_type_0_0= ruleExpr_T ) ) ;
    public final EObject ruleExpr() throws RecognitionException {
        EObject current = null;

        EObject lv_type_0_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:527:28: ( ( (lv_type_0_0= ruleExpr_T ) ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:528:1: ( (lv_type_0_0= ruleExpr_T ) )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:528:1: ( (lv_type_0_0= ruleExpr_T ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:529:1: (lv_type_0_0= ruleExpr_T )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:529:1: (lv_type_0_0= ruleExpr_T )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:530:3: lv_type_0_0= ruleExpr_T
            {
             
            	        newCompositeNode(grammarAccess.getExprAccess().getTypeExpr_TParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleExpr_T_in_ruleExpr1186);
            lv_type_0_0=ruleExpr_T();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExprRule());
            	        }
                   		set(
                   			current, 
                   			"type",
                    		lv_type_0_0, 
                    		"Expr_T");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpr"


    // $ANTLR start "entryRuleExpr_T"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:554:1: entryRuleExpr_T returns [EObject current=null] : iv_ruleExpr_T= ruleExpr_T EOF ;
    public final EObject entryRuleExpr_T() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpr_T = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:555:2: (iv_ruleExpr_T= ruleExpr_T EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:556:2: iv_ruleExpr_T= ruleExpr_T EOF
            {
             newCompositeNode(grammarAccess.getExpr_TRule()); 
            pushFollow(FOLLOW_ruleExpr_T_in_entryRuleExpr_T1221);
            iv_ruleExpr_T=ruleExpr_T();

            state._fsp--;

             current =iv_ruleExpr_T; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleExpr_T1231); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpr_T"


    // $ANTLR start "ruleExpr_T"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:563:1: ruleExpr_T returns [EObject current=null] : (this_Unary_0= ruleUnary | this_Binary_1= ruleBinary ) ;
    public final EObject ruleExpr_T() throws RecognitionException {
        EObject current = null;

        EObject this_Unary_0 = null;

        EObject this_Binary_1 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:566:28: ( (this_Unary_0= ruleUnary | this_Binary_1= ruleBinary ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:567:1: (this_Unary_0= ruleUnary | this_Binary_1= ruleBinary )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:567:1: (this_Unary_0= ruleUnary | this_Binary_1= ruleBinary )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_ID||(LA3_0>=24 && LA3_0<=25)) ) {
                alt3=1;
            }
            else if ( (LA3_0==12) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:568:5: this_Unary_0= ruleUnary
                    {
                     
                            newCompositeNode(grammarAccess.getExpr_TAccess().getUnaryParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleUnary_in_ruleExpr_T1278);
                    this_Unary_0=ruleUnary();

                    state._fsp--;

                     
                            current = this_Unary_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:578:5: this_Binary_1= ruleBinary
                    {
                     
                            newCompositeNode(grammarAccess.getExpr_TAccess().getBinaryParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleBinary_in_ruleExpr_T1305);
                    this_Binary_1=ruleBinary();

                    state._fsp--;

                     
                            current = this_Binary_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpr_T"


    // $ANTLR start "entryRuleUnary"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:594:1: entryRuleUnary returns [EObject current=null] : iv_ruleUnary= ruleUnary EOF ;
    public final EObject entryRuleUnary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnary = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:595:2: (iv_ruleUnary= ruleUnary EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:596:2: iv_ruleUnary= ruleUnary EOF
            {
             newCompositeNode(grammarAccess.getUnaryRule()); 
            pushFollow(FOLLOW_ruleUnary_in_entryRuleUnary1340);
            iv_ruleUnary=ruleUnary();

            state._fsp--;

             current =iv_ruleUnary; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnary1350); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnary"


    // $ANTLR start "ruleUnary"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:603:1: ruleUnary returns [EObject current=null] : (this_Neg_0= ruleNeg | this_Var_1= ruleVar | this_Input_2= ruleInput ) ;
    public final EObject ruleUnary() throws RecognitionException {
        EObject current = null;

        EObject this_Neg_0 = null;

        EObject this_Var_1 = null;

        EObject this_Input_2 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:606:28: ( (this_Neg_0= ruleNeg | this_Var_1= ruleVar | this_Input_2= ruleInput ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:607:1: (this_Neg_0= ruleNeg | this_Var_1= ruleVar | this_Input_2= ruleInput )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:607:1: (this_Neg_0= ruleNeg | this_Var_1= ruleVar | this_Input_2= ruleInput )
            int alt4=3;
            switch ( input.LA(1) ) {
            case 24:
                {
                alt4=1;
                }
                break;
            case RULE_ID:
                {
                alt4=2;
                }
                break;
            case 25:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:608:5: this_Neg_0= ruleNeg
                    {
                     
                            newCompositeNode(grammarAccess.getUnaryAccess().getNegParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleNeg_in_ruleUnary1397);
                    this_Neg_0=ruleNeg();

                    state._fsp--;

                     
                            current = this_Neg_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:618:5: this_Var_1= ruleVar
                    {
                     
                            newCompositeNode(grammarAccess.getUnaryAccess().getVarParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleVar_in_ruleUnary1424);
                    this_Var_1=ruleVar();

                    state._fsp--;

                     
                            current = this_Var_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:628:5: this_Input_2= ruleInput
                    {
                     
                            newCompositeNode(grammarAccess.getUnaryAccess().getInputParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleInput_in_ruleUnary1451);
                    this_Input_2=ruleInput();

                    state._fsp--;

                     
                            current = this_Input_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnary"


    // $ANTLR start "entryRuleBinary"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:644:1: entryRuleBinary returns [EObject current=null] : iv_ruleBinary= ruleBinary EOF ;
    public final EObject entryRuleBinary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBinary = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:645:2: (iv_ruleBinary= ruleBinary EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:646:2: iv_ruleBinary= ruleBinary EOF
            {
             newCompositeNode(grammarAccess.getBinaryRule()); 
            pushFollow(FOLLOW_ruleBinary_in_entryRuleBinary1486);
            iv_ruleBinary=ruleBinary();

            state._fsp--;

             current =iv_ruleBinary; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBinary1496); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBinary"


    // $ANTLR start "ruleBinary"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:653:1: ruleBinary returns [EObject current=null] : (otherlv_0= '(' ( (lv_fst_1_0= ruleExpr ) ) ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' ) ) ) ( (lv_snd_3_0= ruleExpr ) ) otherlv_4= ')' ) ;
    public final EObject ruleBinary() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_operator_2_1=null;
        Token lv_operator_2_2=null;
        Token lv_operator_2_3=null;
        Token otherlv_4=null;
        EObject lv_fst_1_0 = null;

        EObject lv_snd_3_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:656:28: ( (otherlv_0= '(' ( (lv_fst_1_0= ruleExpr ) ) ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' ) ) ) ( (lv_snd_3_0= ruleExpr ) ) otherlv_4= ')' ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:657:1: (otherlv_0= '(' ( (lv_fst_1_0= ruleExpr ) ) ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' ) ) ) ( (lv_snd_3_0= ruleExpr ) ) otherlv_4= ')' )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:657:1: (otherlv_0= '(' ( (lv_fst_1_0= ruleExpr ) ) ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' ) ) ) ( (lv_snd_3_0= ruleExpr ) ) otherlv_4= ')' )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:657:3: otherlv_0= '(' ( (lv_fst_1_0= ruleExpr ) ) ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' ) ) ) ( (lv_snd_3_0= ruleExpr ) ) otherlv_4= ')'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_12_in_ruleBinary1533); 

                	newLeafNode(otherlv_0, grammarAccess.getBinaryAccess().getLeftParenthesisKeyword_0());
                
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:661:1: ( (lv_fst_1_0= ruleExpr ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:662:1: (lv_fst_1_0= ruleExpr )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:662:1: (lv_fst_1_0= ruleExpr )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:663:3: lv_fst_1_0= ruleExpr
            {
             
            	        newCompositeNode(grammarAccess.getBinaryAccess().getFstExprParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleExpr_in_ruleBinary1554);
            lv_fst_1_0=ruleExpr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getBinaryRule());
            	        }
                   		set(
                   			current, 
                   			"fst",
                    		lv_fst_1_0, 
                    		"Expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:679:2: ( ( (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' ) ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:680:1: ( (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' ) )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:680:1: ( (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:681:1: (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:681:1: (lv_operator_2_1= '&&' | lv_operator_2_2= '||' | lv_operator_2_3= '==' )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 21:
                {
                alt5=1;
                }
                break;
            case 22:
                {
                alt5=2;
                }
                break;
            case 23:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:682:3: lv_operator_2_1= '&&'
                    {
                    lv_operator_2_1=(Token)match(input,21,FOLLOW_21_in_ruleBinary1574); 

                            newLeafNode(lv_operator_2_1, grammarAccess.getBinaryAccess().getOperatorAmpersandAmpersandKeyword_2_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getBinaryRule());
                    	        }
                           		setWithLastConsumed(current, "operator", lv_operator_2_1, null);
                    	    

                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:694:8: lv_operator_2_2= '||'
                    {
                    lv_operator_2_2=(Token)match(input,22,FOLLOW_22_in_ruleBinary1603); 

                            newLeafNode(lv_operator_2_2, grammarAccess.getBinaryAccess().getOperatorVerticalLineVerticalLineKeyword_2_0_1());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getBinaryRule());
                    	        }
                           		setWithLastConsumed(current, "operator", lv_operator_2_2, null);
                    	    

                    }
                    break;
                case 3 :
                    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:706:8: lv_operator_2_3= '=='
                    {
                    lv_operator_2_3=(Token)match(input,23,FOLLOW_23_in_ruleBinary1632); 

                            newLeafNode(lv_operator_2_3, grammarAccess.getBinaryAccess().getOperatorEqualsSignEqualsSignKeyword_2_0_2());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getBinaryRule());
                    	        }
                           		setWithLastConsumed(current, "operator", lv_operator_2_3, null);
                    	    

                    }
                    break;

            }


            }


            }

            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:721:2: ( (lv_snd_3_0= ruleExpr ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:722:1: (lv_snd_3_0= ruleExpr )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:722:1: (lv_snd_3_0= ruleExpr )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:723:3: lv_snd_3_0= ruleExpr
            {
             
            	        newCompositeNode(grammarAccess.getBinaryAccess().getSndExprParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleExpr_in_ruleBinary1669);
            lv_snd_3_0=ruleExpr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getBinaryRule());
            	        }
                   		set(
                   			current, 
                   			"snd",
                    		lv_snd_3_0, 
                    		"Expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,13,FOLLOW_13_in_ruleBinary1681); 

                	newLeafNode(otherlv_4, grammarAccess.getBinaryAccess().getRightParenthesisKeyword_4());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBinary"


    // $ANTLR start "entryRuleNeg"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:751:1: entryRuleNeg returns [EObject current=null] : iv_ruleNeg= ruleNeg EOF ;
    public final EObject entryRuleNeg() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNeg = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:752:2: (iv_ruleNeg= ruleNeg EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:753:2: iv_ruleNeg= ruleNeg EOF
            {
             newCompositeNode(grammarAccess.getNegRule()); 
            pushFollow(FOLLOW_ruleNeg_in_entryRuleNeg1717);
            iv_ruleNeg=ruleNeg();

            state._fsp--;

             current =iv_ruleNeg; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNeg1727); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNeg"


    // $ANTLR start "ruleNeg"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:760:1: ruleNeg returns [EObject current=null] : (otherlv_0= '!' ( (lv_expr_1_0= ruleExpr ) ) ) ;
    public final EObject ruleNeg() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_expr_1_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:763:28: ( (otherlv_0= '!' ( (lv_expr_1_0= ruleExpr ) ) ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:764:1: (otherlv_0= '!' ( (lv_expr_1_0= ruleExpr ) ) )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:764:1: (otherlv_0= '!' ( (lv_expr_1_0= ruleExpr ) ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:764:3: otherlv_0= '!' ( (lv_expr_1_0= ruleExpr ) )
            {
            otherlv_0=(Token)match(input,24,FOLLOW_24_in_ruleNeg1764); 

                	newLeafNode(otherlv_0, grammarAccess.getNegAccess().getExclamationMarkKeyword_0());
                
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:768:1: ( (lv_expr_1_0= ruleExpr ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:769:1: (lv_expr_1_0= ruleExpr )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:769:1: (lv_expr_1_0= ruleExpr )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:770:3: lv_expr_1_0= ruleExpr
            {
             
            	        newCompositeNode(grammarAccess.getNegAccess().getExprExprParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleExpr_in_ruleNeg1785);
            lv_expr_1_0=ruleExpr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getNegRule());
            	        }
                   		set(
                   			current, 
                   			"expr",
                    		lv_expr_1_0, 
                    		"Expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNeg"


    // $ANTLR start "entryRuleVar"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:794:1: entryRuleVar returns [EObject current=null] : iv_ruleVar= ruleVar EOF ;
    public final EObject entryRuleVar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVar = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:795:2: (iv_ruleVar= ruleVar EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:796:2: iv_ruleVar= ruleVar EOF
            {
             newCompositeNode(grammarAccess.getVarRule()); 
            pushFollow(FOLLOW_ruleVar_in_entryRuleVar1821);
            iv_ruleVar=ruleVar();

            state._fsp--;

             current =iv_ruleVar; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVar1831); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVar"


    // $ANTLR start "ruleVar"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:803:1: ruleVar returns [EObject current=null] : ( (lv_label_0_0= RULE_ID ) ) ;
    public final EObject ruleVar() throws RecognitionException {
        EObject current = null;

        Token lv_label_0_0=null;

         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:806:28: ( ( (lv_label_0_0= RULE_ID ) ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:807:1: ( (lv_label_0_0= RULE_ID ) )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:807:1: ( (lv_label_0_0= RULE_ID ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:808:1: (lv_label_0_0= RULE_ID )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:808:1: (lv_label_0_0= RULE_ID )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:809:3: lv_label_0_0= RULE_ID
            {
            lv_label_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleVar1872); 

            			newLeafNode(lv_label_0_0, grammarAccess.getVarAccess().getLabelIDTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getVarRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"label",
                    		lv_label_0_0, 
                    		"ID");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVar"


    // $ANTLR start "entryRuleInput"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:833:1: entryRuleInput returns [EObject current=null] : iv_ruleInput= ruleInput EOF ;
    public final EObject entryRuleInput() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInput = null;


        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:834:2: (iv_ruleInput= ruleInput EOF )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:835:2: iv_ruleInput= ruleInput EOF
            {
             newCompositeNode(grammarAccess.getInputRule()); 
            pushFollow(FOLLOW_ruleInput_in_entryRuleInput1912);
            iv_ruleInput=ruleInput();

            state._fsp--;

             current =iv_ruleInput; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleInput1922); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInput"


    // $ANTLR start "ruleInput"
    // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:842:1: ruleInput returns [EObject current=null] : ( () otherlv_1= 'input' otherlv_2= '(' otherlv_3= ')' ) ;
    public final EObject ruleInput() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;

         enterRule(); 
            
        try {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:845:28: ( ( () otherlv_1= 'input' otherlv_2= '(' otherlv_3= ')' ) )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:846:1: ( () otherlv_1= 'input' otherlv_2= '(' otherlv_3= ')' )
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:846:1: ( () otherlv_1= 'input' otherlv_2= '(' otherlv_3= ')' )
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:846:2: () otherlv_1= 'input' otherlv_2= '(' otherlv_3= ')'
            {
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:846:2: ()
            // ../lu.uni.snt.whileDSL/src-gen/lu/uni/snt/whileDSL/parser/antlr/internal/InternalWHILE.g:847:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getInputAccess().getInputAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,25,FOLLOW_25_in_ruleInput1968); 

                	newLeafNode(otherlv_1, grammarAccess.getInputAccess().getInputKeyword_1());
                
            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleInput1980); 

                	newLeafNode(otherlv_2, grammarAccess.getInputAccess().getLeftParenthesisKeyword_2());
                
            otherlv_3=(Token)match(input,13,FOLLOW_13_in_ruleInput1992); 

                	newLeafNode(otherlv_3, grammarAccess.getInputAccess().getRightParenthesisKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInput"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleWProgram_in_entryRuleWProgram75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleWProgram85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFgmnt_LST_Elem_in_ruleWProgram130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFgmnt_LST_Elem_in_entryRuleFgmnt_LST_Elem165 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFgmnt_LST_Elem175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleWhile_in_ruleFgmnt_LST_Elem223 = new BitSet(new long[]{0x00000000000C0812L});
    public static final BitSet FOLLOW_ruleVar_Def_in_ruleFgmnt_LST_Elem250 = new BitSet(new long[]{0x00000000000C0812L});
    public static final BitSet FOLLOW_ruleFn_Call_in_ruleFgmnt_LST_Elem277 = new BitSet(new long[]{0x00000000000C0812L});
    public static final BitSet FOLLOW_ruleFn_Def_in_ruleFgmnt_LST_Elem304 = new BitSet(new long[]{0x00000000000C0812L});
    public static final BitSet FOLLOW_ruleComment_in_ruleFgmnt_LST_Elem331 = new BitSet(new long[]{0x00000000000C0812L});
    public static final BitSet FOLLOW_ruleFgmnt_LST_Elem_in_ruleFgmnt_LST_Elem352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleWhile_in_entryRuleWhile389 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleWhile399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleWhile436 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleWhile448 = new BitSet(new long[]{0x0000000003001010L});
    public static final BitSet FOLLOW_ruleExpr_in_ruleWhile469 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleWhile481 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleWhile493 = new BitSet(new long[]{0x00000000000C0810L});
    public static final BitSet FOLLOW_ruleFgmnt_LST_Elem_in_ruleWhile514 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleWhile526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVar_Def_in_entryRuleVar_Def562 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVar_Def572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVar_in_ruleVar_Def618 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleVar_Def630 = new BitSet(new long[]{0x0000000003001010L});
    public static final BitSet FOLLOW_ruleExpr_in_ruleVar_Def651 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleVar_Def663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFn_Call_in_entryRuleFn_Call699 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFn_Call709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFn_Call751 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleFn_Call768 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleFn_Call780 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleFn_Call792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFn_Def_in_entryRuleFn_Def828 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFn_Def838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleFn_Def875 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFn_Def892 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleFn_Def909 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleFn_Def921 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleFn_Def933 = new BitSet(new long[]{0x00000000000C0810L});
    public static final BitSet FOLLOW_ruleFgmnt_LST_Elem_in_ruleFn_Def954 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleFn_Def966 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleFn_Def978 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComment_in_entryRuleComment1014 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComment1024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleComment1061 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleComment1078 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleComment1095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpr_in_entryRuleExpr1131 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpr1141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpr_T_in_ruleExpr1186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleExpr_T_in_entryRuleExpr_T1221 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleExpr_T1231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnary_in_ruleExpr_T1278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinary_in_ruleExpr_T1305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnary_in_entryRuleUnary1340 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnary1350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNeg_in_ruleUnary1397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVar_in_ruleUnary1424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInput_in_ruleUnary1451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBinary_in_entryRuleBinary1486 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBinary1496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_ruleBinary1533 = new BitSet(new long[]{0x0000000003001010L});
    public static final BitSet FOLLOW_ruleExpr_in_ruleBinary1554 = new BitSet(new long[]{0x0000000000E00000L});
    public static final BitSet FOLLOW_21_in_ruleBinary1574 = new BitSet(new long[]{0x0000000003001010L});
    public static final BitSet FOLLOW_22_in_ruleBinary1603 = new BitSet(new long[]{0x0000000003001010L});
    public static final BitSet FOLLOW_23_in_ruleBinary1632 = new BitSet(new long[]{0x0000000003001010L});
    public static final BitSet FOLLOW_ruleExpr_in_ruleBinary1669 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleBinary1681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNeg_in_entryRuleNeg1717 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNeg1727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleNeg1764 = new BitSet(new long[]{0x0000000003001010L});
    public static final BitSet FOLLOW_ruleExpr_in_ruleNeg1785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVar_in_entryRuleVar1821 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVar1831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleVar1872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInput_in_entryRuleInput1912 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInput1922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleInput1968 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleInput1980 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleInput1992 = new BitSet(new long[]{0x0000000000000002L});

}
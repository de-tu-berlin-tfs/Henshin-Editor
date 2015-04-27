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
package lu.uni.snt.repeat.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import lu.uni.snt.repeat.services.REPEATGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalREPEATParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'REPEAT'", "'UNTIL'", "':='", "'READ'", "'/#'", "'#/'", "'('", "'AND'", "'OR'", "'EQ'", "')'", "'NOT'"
    };
    public static final int RULE_ID=5;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__19=19;
    public static final int RULE_STRING=4;
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


        public InternalREPEATParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalREPEATParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalREPEATParser.tokenNames; }
    public String getGrammarFileName() { return "../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g"; }



     	private REPEATGrammarAccess grammarAccess;
     	
        public InternalREPEATParser(TokenStream input, REPEATGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "RProgram";	
       	}
       	
       	@Override
       	protected REPEATGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleRProgram"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:67:1: entryRuleRProgram returns [EObject current=null] : iv_ruleRProgram= ruleRProgram EOF ;
    public final EObject entryRuleRProgram() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRProgram = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:68:2: (iv_ruleRProgram= ruleRProgram EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:69:2: iv_ruleRProgram= ruleRProgram EOF
            {
             newCompositeNode(grammarAccess.getRProgramRule()); 
            pushFollow(FOLLOW_ruleRProgram_in_entryRuleRProgram75);
            iv_ruleRProgram=ruleRProgram();

            state._fsp--;

             current =iv_ruleRProgram; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRProgram85); 

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
    // $ANTLR end "entryRuleRProgram"


    // $ANTLR start "ruleRProgram"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:76:1: ruleRProgram returns [EObject current=null] : ( (lv_fst_0_0= ruleStmnt_LST_Elem ) ) ;
    public final EObject ruleRProgram() throws RecognitionException {
        EObject current = null;

        EObject lv_fst_0_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:79:28: ( ( (lv_fst_0_0= ruleStmnt_LST_Elem ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:80:1: ( (lv_fst_0_0= ruleStmnt_LST_Elem ) )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:80:1: ( (lv_fst_0_0= ruleStmnt_LST_Elem ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:81:1: (lv_fst_0_0= ruleStmnt_LST_Elem )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:81:1: (lv_fst_0_0= ruleStmnt_LST_Elem )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:82:3: lv_fst_0_0= ruleStmnt_LST_Elem
            {
             
            	        newCompositeNode(grammarAccess.getRProgramAccess().getFstStmnt_LST_ElemParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleStmnt_LST_Elem_in_ruleRProgram130);
            lv_fst_0_0=ruleStmnt_LST_Elem();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRProgramRule());
            	        }
                   		set(
                   			current, 
                   			"fst",
                    		lv_fst_0_0, 
                    		"Stmnt_LST_Elem");
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
    // $ANTLR end "ruleRProgram"


    // $ANTLR start "entryRuleStmnt_LST_Elem"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:106:1: entryRuleStmnt_LST_Elem returns [EObject current=null] : iv_ruleStmnt_LST_Elem= ruleStmnt_LST_Elem EOF ;
    public final EObject entryRuleStmnt_LST_Elem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStmnt_LST_Elem = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:107:2: (iv_ruleStmnt_LST_Elem= ruleStmnt_LST_Elem EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:108:2: iv_ruleStmnt_LST_Elem= ruleStmnt_LST_Elem EOF
            {
             newCompositeNode(grammarAccess.getStmnt_LST_ElemRule()); 
            pushFollow(FOLLOW_ruleStmnt_LST_Elem_in_entryRuleStmnt_LST_Elem165);
            iv_ruleStmnt_LST_Elem=ruleStmnt_LST_Elem();

            state._fsp--;

             current =iv_ruleStmnt_LST_Elem; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleStmnt_LST_Elem175); 

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
    // $ANTLR end "entryRuleStmnt_LST_Elem"


    // $ANTLR start "ruleStmnt_LST_Elem"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:115:1: ruleStmnt_LST_Elem returns [EObject current=null] : ( (this_Repeat_0= ruleRepeat | this_Asg_1= ruleAsg | this_Read_2= ruleRead | this_Comment_3= ruleComment ) ( (lv_next_4_0= ruleStmnt_LST_Elem ) )? ) ;
    public final EObject ruleStmnt_LST_Elem() throws RecognitionException {
        EObject current = null;

        EObject this_Repeat_0 = null;

        EObject this_Asg_1 = null;

        EObject this_Read_2 = null;

        EObject this_Comment_3 = null;

        EObject lv_next_4_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:118:28: ( ( (this_Repeat_0= ruleRepeat | this_Asg_1= ruleAsg | this_Read_2= ruleRead | this_Comment_3= ruleComment ) ( (lv_next_4_0= ruleStmnt_LST_Elem ) )? ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:119:1: ( (this_Repeat_0= ruleRepeat | this_Asg_1= ruleAsg | this_Read_2= ruleRead | this_Comment_3= ruleComment ) ( (lv_next_4_0= ruleStmnt_LST_Elem ) )? )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:119:1: ( (this_Repeat_0= ruleRepeat | this_Asg_1= ruleAsg | this_Read_2= ruleRead | this_Comment_3= ruleComment ) ( (lv_next_4_0= ruleStmnt_LST_Elem ) )? )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:119:2: (this_Repeat_0= ruleRepeat | this_Asg_1= ruleAsg | this_Read_2= ruleRead | this_Comment_3= ruleComment ) ( (lv_next_4_0= ruleStmnt_LST_Elem ) )?
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:119:2: (this_Repeat_0= ruleRepeat | this_Asg_1= ruleAsg | this_Read_2= ruleRead | this_Comment_3= ruleComment )
            int alt1=4;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt1=1;
                }
                break;
            case RULE_ID:
                {
                alt1=2;
                }
                break;
            case 14:
                {
                alt1=3;
                }
                break;
            case 15:
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:120:5: this_Repeat_0= ruleRepeat
                    {
                     
                            newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getRepeatParserRuleCall_0_0()); 
                        
                    pushFollow(FOLLOW_ruleRepeat_in_ruleStmnt_LST_Elem223);
                    this_Repeat_0=ruleRepeat();

                    state._fsp--;

                     
                            current = this_Repeat_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:130:5: this_Asg_1= ruleAsg
                    {
                     
                            newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getAsgParserRuleCall_0_1()); 
                        
                    pushFollow(FOLLOW_ruleAsg_in_ruleStmnt_LST_Elem250);
                    this_Asg_1=ruleAsg();

                    state._fsp--;

                     
                            current = this_Asg_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:140:5: this_Read_2= ruleRead
                    {
                     
                            newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getReadParserRuleCall_0_2()); 
                        
                    pushFollow(FOLLOW_ruleRead_in_ruleStmnt_LST_Elem277);
                    this_Read_2=ruleRead();

                    state._fsp--;

                     
                            current = this_Read_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:150:5: this_Comment_3= ruleComment
                    {
                     
                            newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getCommentParserRuleCall_0_3()); 
                        
                    pushFollow(FOLLOW_ruleComment_in_ruleStmnt_LST_Elem304);
                    this_Comment_3=ruleComment();

                    state._fsp--;

                     
                            current = this_Comment_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:158:2: ( (lv_next_4_0= ruleStmnt_LST_Elem ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==RULE_ID||LA2_0==11||(LA2_0>=14 && LA2_0<=15)) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:159:1: (lv_next_4_0= ruleStmnt_LST_Elem )
                    {
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:159:1: (lv_next_4_0= ruleStmnt_LST_Elem )
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:160:3: lv_next_4_0= ruleStmnt_LST_Elem
                    {
                     
                    	        newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getNextStmnt_LST_ElemParserRuleCall_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleStmnt_LST_Elem_in_ruleStmnt_LST_Elem325);
                    lv_next_4_0=ruleStmnt_LST_Elem();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getStmnt_LST_ElemRule());
                    	        }
                           		set(
                           			current, 
                           			"next",
                            		lv_next_4_0, 
                            		"Stmnt_LST_Elem");
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
    // $ANTLR end "ruleStmnt_LST_Elem"


    // $ANTLR start "entryRuleRepeat"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:184:1: entryRuleRepeat returns [EObject current=null] : iv_ruleRepeat= ruleRepeat EOF ;
    public final EObject entryRuleRepeat() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRepeat = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:185:2: (iv_ruleRepeat= ruleRepeat EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:186:2: iv_ruleRepeat= ruleRepeat EOF
            {
             newCompositeNode(grammarAccess.getRepeatRule()); 
            pushFollow(FOLLOW_ruleRepeat_in_entryRuleRepeat362);
            iv_ruleRepeat=ruleRepeat();

            state._fsp--;

             current =iv_ruleRepeat; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRepeat372); 

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
    // $ANTLR end "entryRuleRepeat"


    // $ANTLR start "ruleRepeat"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:193:1: ruleRepeat returns [EObject current=null] : (otherlv_0= 'REPEAT' ( (lv_stmnt_1_0= ruleStmnt_LST_Elem ) ) otherlv_2= 'UNTIL' ( (lv_expr_3_0= ruleLog_Expr ) ) ) ;
    public final EObject ruleRepeat() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_stmnt_1_0 = null;

        EObject lv_expr_3_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:196:28: ( (otherlv_0= 'REPEAT' ( (lv_stmnt_1_0= ruleStmnt_LST_Elem ) ) otherlv_2= 'UNTIL' ( (lv_expr_3_0= ruleLog_Expr ) ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:197:1: (otherlv_0= 'REPEAT' ( (lv_stmnt_1_0= ruleStmnt_LST_Elem ) ) otherlv_2= 'UNTIL' ( (lv_expr_3_0= ruleLog_Expr ) ) )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:197:1: (otherlv_0= 'REPEAT' ( (lv_stmnt_1_0= ruleStmnt_LST_Elem ) ) otherlv_2= 'UNTIL' ( (lv_expr_3_0= ruleLog_Expr ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:197:3: otherlv_0= 'REPEAT' ( (lv_stmnt_1_0= ruleStmnt_LST_Elem ) ) otherlv_2= 'UNTIL' ( (lv_expr_3_0= ruleLog_Expr ) )
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleRepeat409); 

                	newLeafNode(otherlv_0, grammarAccess.getRepeatAccess().getREPEATKeyword_0());
                
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:201:1: ( (lv_stmnt_1_0= ruleStmnt_LST_Elem ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:202:1: (lv_stmnt_1_0= ruleStmnt_LST_Elem )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:202:1: (lv_stmnt_1_0= ruleStmnt_LST_Elem )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:203:3: lv_stmnt_1_0= ruleStmnt_LST_Elem
            {
             
            	        newCompositeNode(grammarAccess.getRepeatAccess().getStmntStmnt_LST_ElemParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleStmnt_LST_Elem_in_ruleRepeat430);
            lv_stmnt_1_0=ruleStmnt_LST_Elem();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRepeatRule());
            	        }
                   		set(
                   			current, 
                   			"stmnt",
                    		lv_stmnt_1_0, 
                    		"Stmnt_LST_Elem");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleRepeat442); 

                	newLeafNode(otherlv_2, grammarAccess.getRepeatAccess().getUNTILKeyword_2());
                
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:223:1: ( (lv_expr_3_0= ruleLog_Expr ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:224:1: (lv_expr_3_0= ruleLog_Expr )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:224:1: (lv_expr_3_0= ruleLog_Expr )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:225:3: lv_expr_3_0= ruleLog_Expr
            {
             
            	        newCompositeNode(grammarAccess.getRepeatAccess().getExprLog_ExprParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleLog_Expr_in_ruleRepeat463);
            lv_expr_3_0=ruleLog_Expr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRepeatRule());
            	        }
                   		set(
                   			current, 
                   			"expr",
                    		lv_expr_3_0, 
                    		"Log_Expr");
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
    // $ANTLR end "ruleRepeat"


    // $ANTLR start "entryRuleAsg"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:249:1: entryRuleAsg returns [EObject current=null] : iv_ruleAsg= ruleAsg EOF ;
    public final EObject entryRuleAsg() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAsg = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:250:2: (iv_ruleAsg= ruleAsg EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:251:2: iv_ruleAsg= ruleAsg EOF
            {
             newCompositeNode(grammarAccess.getAsgRule()); 
            pushFollow(FOLLOW_ruleAsg_in_entryRuleAsg499);
            iv_ruleAsg=ruleAsg();

            state._fsp--;

             current =iv_ruleAsg; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAsg509); 

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
    // $ANTLR end "entryRuleAsg"


    // $ANTLR start "ruleAsg"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:258:1: ruleAsg returns [EObject current=null] : ( ( (lv_left_0_0= ruleSym ) ) otherlv_1= ':=' ( (lv_right_2_0= ruleSym ) ) ) ;
    public final EObject ruleAsg() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:261:28: ( ( ( (lv_left_0_0= ruleSym ) ) otherlv_1= ':=' ( (lv_right_2_0= ruleSym ) ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:262:1: ( ( (lv_left_0_0= ruleSym ) ) otherlv_1= ':=' ( (lv_right_2_0= ruleSym ) ) )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:262:1: ( ( (lv_left_0_0= ruleSym ) ) otherlv_1= ':=' ( (lv_right_2_0= ruleSym ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:262:2: ( (lv_left_0_0= ruleSym ) ) otherlv_1= ':=' ( (lv_right_2_0= ruleSym ) )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:262:2: ( (lv_left_0_0= ruleSym ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:263:1: (lv_left_0_0= ruleSym )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:263:1: (lv_left_0_0= ruleSym )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:264:3: lv_left_0_0= ruleSym
            {
             
            	        newCompositeNode(grammarAccess.getAsgAccess().getLeftSymParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleSym_in_ruleAsg555);
            lv_left_0_0=ruleSym();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAsgRule());
            	        }
                   		set(
                   			current, 
                   			"left",
                    		lv_left_0_0, 
                    		"Sym");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,13,FOLLOW_13_in_ruleAsg567); 

                	newLeafNode(otherlv_1, grammarAccess.getAsgAccess().getColonEqualsSignKeyword_1());
                
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:284:1: ( (lv_right_2_0= ruleSym ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:285:1: (lv_right_2_0= ruleSym )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:285:1: (lv_right_2_0= ruleSym )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:286:3: lv_right_2_0= ruleSym
            {
             
            	        newCompositeNode(grammarAccess.getAsgAccess().getRightSymParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleSym_in_ruleAsg588);
            lv_right_2_0=ruleSym();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAsgRule());
            	        }
                   		set(
                   			current, 
                   			"right",
                    		lv_right_2_0, 
                    		"Sym");
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
    // $ANTLR end "ruleAsg"


    // $ANTLR start "entryRuleRead"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:310:1: entryRuleRead returns [EObject current=null] : iv_ruleRead= ruleRead EOF ;
    public final EObject entryRuleRead() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRead = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:311:2: (iv_ruleRead= ruleRead EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:312:2: iv_ruleRead= ruleRead EOF
            {
             newCompositeNode(grammarAccess.getReadRule()); 
            pushFollow(FOLLOW_ruleRead_in_entryRuleRead624);
            iv_ruleRead=ruleRead();

            state._fsp--;

             current =iv_ruleRead; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRead634); 

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
    // $ANTLR end "entryRuleRead"


    // $ANTLR start "ruleRead"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:319:1: ruleRead returns [EObject current=null] : (otherlv_0= 'READ' ( (lv_param_1_0= ruleSym ) ) ) ;
    public final EObject ruleRead() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_param_1_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:322:28: ( (otherlv_0= 'READ' ( (lv_param_1_0= ruleSym ) ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:323:1: (otherlv_0= 'READ' ( (lv_param_1_0= ruleSym ) ) )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:323:1: (otherlv_0= 'READ' ( (lv_param_1_0= ruleSym ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:323:3: otherlv_0= 'READ' ( (lv_param_1_0= ruleSym ) )
            {
            otherlv_0=(Token)match(input,14,FOLLOW_14_in_ruleRead671); 

                	newLeafNode(otherlv_0, grammarAccess.getReadAccess().getREADKeyword_0());
                
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:327:1: ( (lv_param_1_0= ruleSym ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:328:1: (lv_param_1_0= ruleSym )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:328:1: (lv_param_1_0= ruleSym )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:329:3: lv_param_1_0= ruleSym
            {
             
            	        newCompositeNode(grammarAccess.getReadAccess().getParamSymParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleSym_in_ruleRead692);
            lv_param_1_0=ruleSym();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getReadRule());
            	        }
                   		set(
                   			current, 
                   			"param",
                    		lv_param_1_0, 
                    		"Sym");
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
    // $ANTLR end "ruleRead"


    // $ANTLR start "entryRuleComment"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:353:1: entryRuleComment returns [EObject current=null] : iv_ruleComment= ruleComment EOF ;
    public final EObject entryRuleComment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComment = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:354:2: (iv_ruleComment= ruleComment EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:355:2: iv_ruleComment= ruleComment EOF
            {
             newCompositeNode(grammarAccess.getCommentRule()); 
            pushFollow(FOLLOW_ruleComment_in_entryRuleComment728);
            iv_ruleComment=ruleComment();

            state._fsp--;

             current =iv_ruleComment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleComment738); 

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
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:362:1: ruleComment returns [EObject current=null] : (otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/' ) ;
    public final EObject ruleComment() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_comment_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:365:28: ( (otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/' ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:366:1: (otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/' )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:366:1: (otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/' )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:366:3: otherlv_0= '/#' ( (lv_comment_1_0= RULE_STRING ) ) otherlv_2= '#/'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_15_in_ruleComment775); 

                	newLeafNode(otherlv_0, grammarAccess.getCommentAccess().getSolidusNumberSignKeyword_0());
                
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:370:1: ( (lv_comment_1_0= RULE_STRING ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:371:1: (lv_comment_1_0= RULE_STRING )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:371:1: (lv_comment_1_0= RULE_STRING )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:372:3: lv_comment_1_0= RULE_STRING
            {
            lv_comment_1_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleComment792); 

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

            otherlv_2=(Token)match(input,16,FOLLOW_16_in_ruleComment809); 

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


    // $ANTLR start "entryRuleLog_Expr"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:400:1: entryRuleLog_Expr returns [EObject current=null] : iv_ruleLog_Expr= ruleLog_Expr EOF ;
    public final EObject entryRuleLog_Expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLog_Expr = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:401:2: (iv_ruleLog_Expr= ruleLog_Expr EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:402:2: iv_ruleLog_Expr= ruleLog_Expr EOF
            {
             newCompositeNode(grammarAccess.getLog_ExprRule()); 
            pushFollow(FOLLOW_ruleLog_Expr_in_entryRuleLog_Expr845);
            iv_ruleLog_Expr=ruleLog_Expr();

            state._fsp--;

             current =iv_ruleLog_Expr; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Expr855); 

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
    // $ANTLR end "entryRuleLog_Expr"


    // $ANTLR start "ruleLog_Expr"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:409:1: ruleLog_Expr returns [EObject current=null] : ( (lv_type_0_0= ruleLog_Expr_T ) ) ;
    public final EObject ruleLog_Expr() throws RecognitionException {
        EObject current = null;

        EObject lv_type_0_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:412:28: ( ( (lv_type_0_0= ruleLog_Expr_T ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:413:1: ( (lv_type_0_0= ruleLog_Expr_T ) )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:413:1: ( (lv_type_0_0= ruleLog_Expr_T ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:414:1: (lv_type_0_0= ruleLog_Expr_T )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:414:1: (lv_type_0_0= ruleLog_Expr_T )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:415:3: lv_type_0_0= ruleLog_Expr_T
            {
             
            	        newCompositeNode(grammarAccess.getLog_ExprAccess().getTypeLog_Expr_TParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleLog_Expr_T_in_ruleLog_Expr900);
            lv_type_0_0=ruleLog_Expr_T();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLog_ExprRule());
            	        }
                   		set(
                   			current, 
                   			"type",
                    		lv_type_0_0, 
                    		"Log_Expr_T");
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
    // $ANTLR end "ruleLog_Expr"


    // $ANTLR start "entryRuleLog_Expr_T"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:439:1: entryRuleLog_Expr_T returns [EObject current=null] : iv_ruleLog_Expr_T= ruleLog_Expr_T EOF ;
    public final EObject entryRuleLog_Expr_T() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLog_Expr_T = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:440:2: (iv_ruleLog_Expr_T= ruleLog_Expr_T EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:441:2: iv_ruleLog_Expr_T= ruleLog_Expr_T EOF
            {
             newCompositeNode(grammarAccess.getLog_Expr_TRule()); 
            pushFollow(FOLLOW_ruleLog_Expr_T_in_entryRuleLog_Expr_T935);
            iv_ruleLog_Expr_T=ruleLog_Expr_T();

            state._fsp--;

             current =iv_ruleLog_Expr_T; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Expr_T945); 

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
    // $ANTLR end "entryRuleLog_Expr_T"


    // $ANTLR start "ruleLog_Expr_T"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:448:1: ruleLog_Expr_T returns [EObject current=null] : (this_Log_Expr_Unary_0= ruleLog_Expr_Unary | this_Log_Expr_Binary_1= ruleLog_Expr_Binary ) ;
    public final EObject ruleLog_Expr_T() throws RecognitionException {
        EObject current = null;

        EObject this_Log_Expr_Unary_0 = null;

        EObject this_Log_Expr_Binary_1 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:451:28: ( (this_Log_Expr_Unary_0= ruleLog_Expr_Unary | this_Log_Expr_Binary_1= ruleLog_Expr_Binary ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:452:1: (this_Log_Expr_Unary_0= ruleLog_Expr_Unary | this_Log_Expr_Binary_1= ruleLog_Expr_Binary )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:452:1: (this_Log_Expr_Unary_0= ruleLog_Expr_Unary | this_Log_Expr_Binary_1= ruleLog_Expr_Binary )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_ID||LA3_0==22) ) {
                alt3=1;
            }
            else if ( (LA3_0==17) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:453:5: this_Log_Expr_Unary_0= ruleLog_Expr_Unary
                    {
                     
                            newCompositeNode(grammarAccess.getLog_Expr_TAccess().getLog_Expr_UnaryParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleLog_Expr_Unary_in_ruleLog_Expr_T992);
                    this_Log_Expr_Unary_0=ruleLog_Expr_Unary();

                    state._fsp--;

                     
                            current = this_Log_Expr_Unary_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:463:5: this_Log_Expr_Binary_1= ruleLog_Expr_Binary
                    {
                     
                            newCompositeNode(grammarAccess.getLog_Expr_TAccess().getLog_Expr_BinaryParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleLog_Expr_Binary_in_ruleLog_Expr_T1019);
                    this_Log_Expr_Binary_1=ruleLog_Expr_Binary();

                    state._fsp--;

                     
                            current = this_Log_Expr_Binary_1; 
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
    // $ANTLR end "ruleLog_Expr_T"


    // $ANTLR start "entryRuleLog_Expr_Unary"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:479:1: entryRuleLog_Expr_Unary returns [EObject current=null] : iv_ruleLog_Expr_Unary= ruleLog_Expr_Unary EOF ;
    public final EObject entryRuleLog_Expr_Unary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLog_Expr_Unary = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:480:2: (iv_ruleLog_Expr_Unary= ruleLog_Expr_Unary EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:481:2: iv_ruleLog_Expr_Unary= ruleLog_Expr_Unary EOF
            {
             newCompositeNode(grammarAccess.getLog_Expr_UnaryRule()); 
            pushFollow(FOLLOW_ruleLog_Expr_Unary_in_entryRuleLog_Expr_Unary1054);
            iv_ruleLog_Expr_Unary=ruleLog_Expr_Unary();

            state._fsp--;

             current =iv_ruleLog_Expr_Unary; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Expr_Unary1064); 

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
    // $ANTLR end "entryRuleLog_Expr_Unary"


    // $ANTLR start "ruleLog_Expr_Unary"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:488:1: ruleLog_Expr_Unary returns [EObject current=null] : (this_Log_Neg_0= ruleLog_Neg | this_Sym_1= ruleSym ) ;
    public final EObject ruleLog_Expr_Unary() throws RecognitionException {
        EObject current = null;

        EObject this_Log_Neg_0 = null;

        EObject this_Sym_1 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:491:28: ( (this_Log_Neg_0= ruleLog_Neg | this_Sym_1= ruleSym ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:492:1: (this_Log_Neg_0= ruleLog_Neg | this_Sym_1= ruleSym )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:492:1: (this_Log_Neg_0= ruleLog_Neg | this_Sym_1= ruleSym )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==22) ) {
                alt4=1;
            }
            else if ( (LA4_0==RULE_ID) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:493:5: this_Log_Neg_0= ruleLog_Neg
                    {
                     
                            newCompositeNode(grammarAccess.getLog_Expr_UnaryAccess().getLog_NegParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleLog_Neg_in_ruleLog_Expr_Unary1111);
                    this_Log_Neg_0=ruleLog_Neg();

                    state._fsp--;

                     
                            current = this_Log_Neg_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:503:5: this_Sym_1= ruleSym
                    {
                     
                            newCompositeNode(grammarAccess.getLog_Expr_UnaryAccess().getSymParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleSym_in_ruleLog_Expr_Unary1138);
                    this_Sym_1=ruleSym();

                    state._fsp--;

                     
                            current = this_Sym_1; 
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
    // $ANTLR end "ruleLog_Expr_Unary"


    // $ANTLR start "entryRuleLog_Expr_Binary"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:519:1: entryRuleLog_Expr_Binary returns [EObject current=null] : iv_ruleLog_Expr_Binary= ruleLog_Expr_Binary EOF ;
    public final EObject entryRuleLog_Expr_Binary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLog_Expr_Binary = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:520:2: (iv_ruleLog_Expr_Binary= ruleLog_Expr_Binary EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:521:2: iv_ruleLog_Expr_Binary= ruleLog_Expr_Binary EOF
            {
             newCompositeNode(grammarAccess.getLog_Expr_BinaryRule()); 
            pushFollow(FOLLOW_ruleLog_Expr_Binary_in_entryRuleLog_Expr_Binary1173);
            iv_ruleLog_Expr_Binary=ruleLog_Expr_Binary();

            state._fsp--;

             current =iv_ruleLog_Expr_Binary; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Expr_Binary1183); 

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
    // $ANTLR end "entryRuleLog_Expr_Binary"


    // $ANTLR start "ruleLog_Expr_Binary"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:528:1: ruleLog_Expr_Binary returns [EObject current=null] : (otherlv_0= '(' ( (lv_fst_1_0= ruleLog_Expr ) ) ( ( (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' ) ) ) ( (lv_snd_3_0= ruleLog_Expr ) ) otherlv_4= ')' ) ;
    public final EObject ruleLog_Expr_Binary() throws RecognitionException {
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
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:531:28: ( (otherlv_0= '(' ( (lv_fst_1_0= ruleLog_Expr ) ) ( ( (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' ) ) ) ( (lv_snd_3_0= ruleLog_Expr ) ) otherlv_4= ')' ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:532:1: (otherlv_0= '(' ( (lv_fst_1_0= ruleLog_Expr ) ) ( ( (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' ) ) ) ( (lv_snd_3_0= ruleLog_Expr ) ) otherlv_4= ')' )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:532:1: (otherlv_0= '(' ( (lv_fst_1_0= ruleLog_Expr ) ) ( ( (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' ) ) ) ( (lv_snd_3_0= ruleLog_Expr ) ) otherlv_4= ')' )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:532:3: otherlv_0= '(' ( (lv_fst_1_0= ruleLog_Expr ) ) ( ( (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' ) ) ) ( (lv_snd_3_0= ruleLog_Expr ) ) otherlv_4= ')'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_17_in_ruleLog_Expr_Binary1220); 

                	newLeafNode(otherlv_0, grammarAccess.getLog_Expr_BinaryAccess().getLeftParenthesisKeyword_0());
                
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:536:1: ( (lv_fst_1_0= ruleLog_Expr ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:537:1: (lv_fst_1_0= ruleLog_Expr )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:537:1: (lv_fst_1_0= ruleLog_Expr )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:538:3: lv_fst_1_0= ruleLog_Expr
            {
             
            	        newCompositeNode(grammarAccess.getLog_Expr_BinaryAccess().getFstLog_ExprParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleLog_Expr_in_ruleLog_Expr_Binary1241);
            lv_fst_1_0=ruleLog_Expr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLog_Expr_BinaryRule());
            	        }
                   		set(
                   			current, 
                   			"fst",
                    		lv_fst_1_0, 
                    		"Log_Expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:554:2: ( ( (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:555:1: ( (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' ) )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:555:1: ( (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:556:1: (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:556:1: (lv_operator_2_1= 'AND' | lv_operator_2_2= 'OR' | lv_operator_2_3= 'EQ' )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 18:
                {
                alt5=1;
                }
                break;
            case 19:
                {
                alt5=2;
                }
                break;
            case 20:
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
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:557:3: lv_operator_2_1= 'AND'
                    {
                    lv_operator_2_1=(Token)match(input,18,FOLLOW_18_in_ruleLog_Expr_Binary1261); 

                            newLeafNode(lv_operator_2_1, grammarAccess.getLog_Expr_BinaryAccess().getOperatorANDKeyword_2_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLog_Expr_BinaryRule());
                    	        }
                           		setWithLastConsumed(current, "operator", lv_operator_2_1, null);
                    	    

                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:569:8: lv_operator_2_2= 'OR'
                    {
                    lv_operator_2_2=(Token)match(input,19,FOLLOW_19_in_ruleLog_Expr_Binary1290); 

                            newLeafNode(lv_operator_2_2, grammarAccess.getLog_Expr_BinaryAccess().getOperatorORKeyword_2_0_1());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLog_Expr_BinaryRule());
                    	        }
                           		setWithLastConsumed(current, "operator", lv_operator_2_2, null);
                    	    

                    }
                    break;
                case 3 :
                    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:581:8: lv_operator_2_3= 'EQ'
                    {
                    lv_operator_2_3=(Token)match(input,20,FOLLOW_20_in_ruleLog_Expr_Binary1319); 

                            newLeafNode(lv_operator_2_3, grammarAccess.getLog_Expr_BinaryAccess().getOperatorEQKeyword_2_0_2());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLog_Expr_BinaryRule());
                    	        }
                           		setWithLastConsumed(current, "operator", lv_operator_2_3, null);
                    	    

                    }
                    break;

            }


            }


            }

            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:596:2: ( (lv_snd_3_0= ruleLog_Expr ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:597:1: (lv_snd_3_0= ruleLog_Expr )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:597:1: (lv_snd_3_0= ruleLog_Expr )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:598:3: lv_snd_3_0= ruleLog_Expr
            {
             
            	        newCompositeNode(grammarAccess.getLog_Expr_BinaryAccess().getSndLog_ExprParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleLog_Expr_in_ruleLog_Expr_Binary1356);
            lv_snd_3_0=ruleLog_Expr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLog_Expr_BinaryRule());
            	        }
                   		set(
                   			current, 
                   			"snd",
                    		lv_snd_3_0, 
                    		"Log_Expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,21,FOLLOW_21_in_ruleLog_Expr_Binary1368); 

                	newLeafNode(otherlv_4, grammarAccess.getLog_Expr_BinaryAccess().getRightParenthesisKeyword_4());
                

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
    // $ANTLR end "ruleLog_Expr_Binary"


    // $ANTLR start "entryRuleLog_Neg"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:626:1: entryRuleLog_Neg returns [EObject current=null] : iv_ruleLog_Neg= ruleLog_Neg EOF ;
    public final EObject entryRuleLog_Neg() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLog_Neg = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:627:2: (iv_ruleLog_Neg= ruleLog_Neg EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:628:2: iv_ruleLog_Neg= ruleLog_Neg EOF
            {
             newCompositeNode(grammarAccess.getLog_NegRule()); 
            pushFollow(FOLLOW_ruleLog_Neg_in_entryRuleLog_Neg1404);
            iv_ruleLog_Neg=ruleLog_Neg();

            state._fsp--;

             current =iv_ruleLog_Neg; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Neg1414); 

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
    // $ANTLR end "entryRuleLog_Neg"


    // $ANTLR start "ruleLog_Neg"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:635:1: ruleLog_Neg returns [EObject current=null] : (otherlv_0= 'NOT' ( (lv_expr_1_0= ruleLog_Expr ) ) ) ;
    public final EObject ruleLog_Neg() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_expr_1_0 = null;


         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:638:28: ( (otherlv_0= 'NOT' ( (lv_expr_1_0= ruleLog_Expr ) ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:639:1: (otherlv_0= 'NOT' ( (lv_expr_1_0= ruleLog_Expr ) ) )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:639:1: (otherlv_0= 'NOT' ( (lv_expr_1_0= ruleLog_Expr ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:639:3: otherlv_0= 'NOT' ( (lv_expr_1_0= ruleLog_Expr ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_22_in_ruleLog_Neg1451); 

                	newLeafNode(otherlv_0, grammarAccess.getLog_NegAccess().getNOTKeyword_0());
                
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:643:1: ( (lv_expr_1_0= ruleLog_Expr ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:644:1: (lv_expr_1_0= ruleLog_Expr )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:644:1: (lv_expr_1_0= ruleLog_Expr )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:645:3: lv_expr_1_0= ruleLog_Expr
            {
             
            	        newCompositeNode(grammarAccess.getLog_NegAccess().getExprLog_ExprParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleLog_Expr_in_ruleLog_Neg1472);
            lv_expr_1_0=ruleLog_Expr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLog_NegRule());
            	        }
                   		set(
                   			current, 
                   			"expr",
                    		lv_expr_1_0, 
                    		"Log_Expr");
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
    // $ANTLR end "ruleLog_Neg"


    // $ANTLR start "entryRuleSym"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:669:1: entryRuleSym returns [EObject current=null] : iv_ruleSym= ruleSym EOF ;
    public final EObject entryRuleSym() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSym = null;


        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:670:2: (iv_ruleSym= ruleSym EOF )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:671:2: iv_ruleSym= ruleSym EOF
            {
             newCompositeNode(grammarAccess.getSymRule()); 
            pushFollow(FOLLOW_ruleSym_in_entryRuleSym1508);
            iv_ruleSym=ruleSym();

            state._fsp--;

             current =iv_ruleSym; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSym1518); 

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
    // $ANTLR end "entryRuleSym"


    // $ANTLR start "ruleSym"
    // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:678:1: ruleSym returns [EObject current=null] : ( (lv_sym_0_0= RULE_ID ) ) ;
    public final EObject ruleSym() throws RecognitionException {
        EObject current = null;

        Token lv_sym_0_0=null;

         enterRule(); 
            
        try {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:681:28: ( ( (lv_sym_0_0= RULE_ID ) ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:682:1: ( (lv_sym_0_0= RULE_ID ) )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:682:1: ( (lv_sym_0_0= RULE_ID ) )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:683:1: (lv_sym_0_0= RULE_ID )
            {
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:683:1: (lv_sym_0_0= RULE_ID )
            // ../lu.uni.snt.repeat/src-gen/lu/uni/snt/repeat/parser/antlr/internal/InternalREPEAT.g:684:3: lv_sym_0_0= RULE_ID
            {
            lv_sym_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleSym1559); 

            			newLeafNode(lv_sym_0_0, grammarAccess.getSymAccess().getSymIDTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getSymRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"sym",
                    		lv_sym_0_0, 
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
    // $ANTLR end "ruleSym"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleRProgram_in_entryRuleRProgram75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRProgram85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStmnt_LST_Elem_in_ruleRProgram130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStmnt_LST_Elem_in_entryRuleStmnt_LST_Elem165 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStmnt_LST_Elem175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRepeat_in_ruleStmnt_LST_Elem223 = new BitSet(new long[]{0x000000000000C822L});
    public static final BitSet FOLLOW_ruleAsg_in_ruleStmnt_LST_Elem250 = new BitSet(new long[]{0x000000000000C822L});
    public static final BitSet FOLLOW_ruleRead_in_ruleStmnt_LST_Elem277 = new BitSet(new long[]{0x000000000000C822L});
    public static final BitSet FOLLOW_ruleComment_in_ruleStmnt_LST_Elem304 = new BitSet(new long[]{0x000000000000C822L});
    public static final BitSet FOLLOW_ruleStmnt_LST_Elem_in_ruleStmnt_LST_Elem325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRepeat_in_entryRuleRepeat362 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRepeat372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleRepeat409 = new BitSet(new long[]{0x000000000000C820L});
    public static final BitSet FOLLOW_ruleStmnt_LST_Elem_in_ruleRepeat430 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleRepeat442 = new BitSet(new long[]{0x0000000000420020L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_ruleRepeat463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAsg_in_entryRuleAsg499 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAsg509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSym_in_ruleAsg555 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleAsg567 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleSym_in_ruleAsg588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRead_in_entryRuleRead624 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRead634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleRead671 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleSym_in_ruleRead692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComment_in_entryRuleComment728 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComment738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleComment775 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleComment792 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleComment809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_entryRuleLog_Expr845 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Expr855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_T_in_ruleLog_Expr900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_T_in_entryRuleLog_Expr_T935 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Expr_T945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_Unary_in_ruleLog_Expr_T992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_Binary_in_ruleLog_Expr_T1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_Unary_in_entryRuleLog_Expr_Unary1054 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Expr_Unary1064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Neg_in_ruleLog_Expr_Unary1111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSym_in_ruleLog_Expr_Unary1138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_Binary_in_entryRuleLog_Expr_Binary1173 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Expr_Binary1183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleLog_Expr_Binary1220 = new BitSet(new long[]{0x0000000000420020L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_ruleLog_Expr_Binary1241 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_18_in_ruleLog_Expr_Binary1261 = new BitSet(new long[]{0x0000000000420020L});
    public static final BitSet FOLLOW_19_in_ruleLog_Expr_Binary1290 = new BitSet(new long[]{0x0000000000420020L});
    public static final BitSet FOLLOW_20_in_ruleLog_Expr_Binary1319 = new BitSet(new long[]{0x0000000000420020L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_ruleLog_Expr_Binary1356 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_ruleLog_Expr_Binary1368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Neg_in_entryRuleLog_Neg1404 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Neg1414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleLog_Neg1451 = new BitSet(new long[]{0x0000000000420020L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_ruleLog_Neg1472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSym_in_entryRuleSym1508 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSym1518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleSym1559 = new BitSet(new long[]{0x0000000000000002L});

}
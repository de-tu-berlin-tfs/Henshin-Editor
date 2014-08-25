package lu.uni.snt.repeat.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import lu.uni.snt.repeat.services.REPEATGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalREPEATParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'AND'", "'OR'", "'EQ'", "'REPEAT'", "'UNTIL'", "':='", "'READ'", "'/#'", "'#/'", "'('", "')'", "'NOT'"
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
    public String getGrammarFileName() { return "../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g"; }


     
     	private REPEATGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(REPEATGrammarAccess grammarAccess) {
        	this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected Grammar getGrammar() {
        	return grammarAccess.getGrammar();
        }
        
        @Override
        protected String getValueForTokenName(String tokenName) {
        	return tokenName;
        }




    // $ANTLR start "entryRuleRProgram"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:60:1: entryRuleRProgram : ruleRProgram EOF ;
    public final void entryRuleRProgram() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:61:1: ( ruleRProgram EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:62:1: ruleRProgram EOF
            {
             before(grammarAccess.getRProgramRule()); 
            pushFollow(FOLLOW_ruleRProgram_in_entryRuleRProgram61);
            ruleRProgram();

            state._fsp--;

             after(grammarAccess.getRProgramRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRProgram68); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRProgram"


    // $ANTLR start "ruleRProgram"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:69:1: ruleRProgram : ( ( rule__RProgram__FstAssignment ) ) ;
    public final void ruleRProgram() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:73:2: ( ( ( rule__RProgram__FstAssignment ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:74:1: ( ( rule__RProgram__FstAssignment ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:74:1: ( ( rule__RProgram__FstAssignment ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:75:1: ( rule__RProgram__FstAssignment )
            {
             before(grammarAccess.getRProgramAccess().getFstAssignment()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:76:1: ( rule__RProgram__FstAssignment )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:76:2: rule__RProgram__FstAssignment
            {
            pushFollow(FOLLOW_rule__RProgram__FstAssignment_in_ruleRProgram94);
            rule__RProgram__FstAssignment();

            state._fsp--;


            }

             after(grammarAccess.getRProgramAccess().getFstAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRProgram"


    // $ANTLR start "entryRuleStmnt_LST_Elem"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:88:1: entryRuleStmnt_LST_Elem : ruleStmnt_LST_Elem EOF ;
    public final void entryRuleStmnt_LST_Elem() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:89:1: ( ruleStmnt_LST_Elem EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:90:1: ruleStmnt_LST_Elem EOF
            {
             before(grammarAccess.getStmnt_LST_ElemRule()); 
            pushFollow(FOLLOW_ruleStmnt_LST_Elem_in_entryRuleStmnt_LST_Elem121);
            ruleStmnt_LST_Elem();

            state._fsp--;

             after(grammarAccess.getStmnt_LST_ElemRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleStmnt_LST_Elem128); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStmnt_LST_Elem"


    // $ANTLR start "ruleStmnt_LST_Elem"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:97:1: ruleStmnt_LST_Elem : ( ( rule__Stmnt_LST_Elem__Group__0 ) ) ;
    public final void ruleStmnt_LST_Elem() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:101:2: ( ( ( rule__Stmnt_LST_Elem__Group__0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:102:1: ( ( rule__Stmnt_LST_Elem__Group__0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:102:1: ( ( rule__Stmnt_LST_Elem__Group__0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:103:1: ( rule__Stmnt_LST_Elem__Group__0 )
            {
             before(grammarAccess.getStmnt_LST_ElemAccess().getGroup()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:104:1: ( rule__Stmnt_LST_Elem__Group__0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:104:2: rule__Stmnt_LST_Elem__Group__0
            {
            pushFollow(FOLLOW_rule__Stmnt_LST_Elem__Group__0_in_ruleStmnt_LST_Elem154);
            rule__Stmnt_LST_Elem__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getStmnt_LST_ElemAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStmnt_LST_Elem"


    // $ANTLR start "entryRuleRepeat"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:116:1: entryRuleRepeat : ruleRepeat EOF ;
    public final void entryRuleRepeat() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:117:1: ( ruleRepeat EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:118:1: ruleRepeat EOF
            {
             before(grammarAccess.getRepeatRule()); 
            pushFollow(FOLLOW_ruleRepeat_in_entryRuleRepeat181);
            ruleRepeat();

            state._fsp--;

             after(grammarAccess.getRepeatRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRepeat188); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRepeat"


    // $ANTLR start "ruleRepeat"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:125:1: ruleRepeat : ( ( rule__Repeat__Group__0 ) ) ;
    public final void ruleRepeat() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:129:2: ( ( ( rule__Repeat__Group__0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:130:1: ( ( rule__Repeat__Group__0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:130:1: ( ( rule__Repeat__Group__0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:131:1: ( rule__Repeat__Group__0 )
            {
             before(grammarAccess.getRepeatAccess().getGroup()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:132:1: ( rule__Repeat__Group__0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:132:2: rule__Repeat__Group__0
            {
            pushFollow(FOLLOW_rule__Repeat__Group__0_in_ruleRepeat214);
            rule__Repeat__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRepeatAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRepeat"


    // $ANTLR start "entryRuleAsg"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:144:1: entryRuleAsg : ruleAsg EOF ;
    public final void entryRuleAsg() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:145:1: ( ruleAsg EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:146:1: ruleAsg EOF
            {
             before(grammarAccess.getAsgRule()); 
            pushFollow(FOLLOW_ruleAsg_in_entryRuleAsg241);
            ruleAsg();

            state._fsp--;

             after(grammarAccess.getAsgRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAsg248); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAsg"


    // $ANTLR start "ruleAsg"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:153:1: ruleAsg : ( ( rule__Asg__Group__0 ) ) ;
    public final void ruleAsg() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:157:2: ( ( ( rule__Asg__Group__0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:158:1: ( ( rule__Asg__Group__0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:158:1: ( ( rule__Asg__Group__0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:159:1: ( rule__Asg__Group__0 )
            {
             before(grammarAccess.getAsgAccess().getGroup()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:160:1: ( rule__Asg__Group__0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:160:2: rule__Asg__Group__0
            {
            pushFollow(FOLLOW_rule__Asg__Group__0_in_ruleAsg274);
            rule__Asg__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAsgAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAsg"


    // $ANTLR start "entryRuleRead"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:172:1: entryRuleRead : ruleRead EOF ;
    public final void entryRuleRead() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:173:1: ( ruleRead EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:174:1: ruleRead EOF
            {
             before(grammarAccess.getReadRule()); 
            pushFollow(FOLLOW_ruleRead_in_entryRuleRead301);
            ruleRead();

            state._fsp--;

             after(grammarAccess.getReadRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRead308); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRead"


    // $ANTLR start "ruleRead"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:181:1: ruleRead : ( ( rule__Read__Group__0 ) ) ;
    public final void ruleRead() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:185:2: ( ( ( rule__Read__Group__0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:186:1: ( ( rule__Read__Group__0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:186:1: ( ( rule__Read__Group__0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:187:1: ( rule__Read__Group__0 )
            {
             before(grammarAccess.getReadAccess().getGroup()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:188:1: ( rule__Read__Group__0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:188:2: rule__Read__Group__0
            {
            pushFollow(FOLLOW_rule__Read__Group__0_in_ruleRead334);
            rule__Read__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getReadAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRead"


    // $ANTLR start "entryRuleComment"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:200:1: entryRuleComment : ruleComment EOF ;
    public final void entryRuleComment() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:201:1: ( ruleComment EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:202:1: ruleComment EOF
            {
             before(grammarAccess.getCommentRule()); 
            pushFollow(FOLLOW_ruleComment_in_entryRuleComment361);
            ruleComment();

            state._fsp--;

             after(grammarAccess.getCommentRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleComment368); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleComment"


    // $ANTLR start "ruleComment"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:209:1: ruleComment : ( ( rule__Comment__Group__0 ) ) ;
    public final void ruleComment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:213:2: ( ( ( rule__Comment__Group__0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:214:1: ( ( rule__Comment__Group__0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:214:1: ( ( rule__Comment__Group__0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:215:1: ( rule__Comment__Group__0 )
            {
             before(grammarAccess.getCommentAccess().getGroup()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:216:1: ( rule__Comment__Group__0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:216:2: rule__Comment__Group__0
            {
            pushFollow(FOLLOW_rule__Comment__Group__0_in_ruleComment394);
            rule__Comment__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getCommentAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleComment"


    // $ANTLR start "entryRuleLog_Expr"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:228:1: entryRuleLog_Expr : ruleLog_Expr EOF ;
    public final void entryRuleLog_Expr() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:229:1: ( ruleLog_Expr EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:230:1: ruleLog_Expr EOF
            {
             before(grammarAccess.getLog_ExprRule()); 
            pushFollow(FOLLOW_ruleLog_Expr_in_entryRuleLog_Expr421);
            ruleLog_Expr();

            state._fsp--;

             after(grammarAccess.getLog_ExprRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Expr428); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLog_Expr"


    // $ANTLR start "ruleLog_Expr"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:237:1: ruleLog_Expr : ( ( rule__Log_Expr__TypeAssignment ) ) ;
    public final void ruleLog_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:241:2: ( ( ( rule__Log_Expr__TypeAssignment ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:242:1: ( ( rule__Log_Expr__TypeAssignment ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:242:1: ( ( rule__Log_Expr__TypeAssignment ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:243:1: ( rule__Log_Expr__TypeAssignment )
            {
             before(grammarAccess.getLog_ExprAccess().getTypeAssignment()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:244:1: ( rule__Log_Expr__TypeAssignment )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:244:2: rule__Log_Expr__TypeAssignment
            {
            pushFollow(FOLLOW_rule__Log_Expr__TypeAssignment_in_ruleLog_Expr454);
            rule__Log_Expr__TypeAssignment();

            state._fsp--;


            }

             after(grammarAccess.getLog_ExprAccess().getTypeAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLog_Expr"


    // $ANTLR start "entryRuleLog_Expr_T"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:256:1: entryRuleLog_Expr_T : ruleLog_Expr_T EOF ;
    public final void entryRuleLog_Expr_T() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:257:1: ( ruleLog_Expr_T EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:258:1: ruleLog_Expr_T EOF
            {
             before(grammarAccess.getLog_Expr_TRule()); 
            pushFollow(FOLLOW_ruleLog_Expr_T_in_entryRuleLog_Expr_T481);
            ruleLog_Expr_T();

            state._fsp--;

             after(grammarAccess.getLog_Expr_TRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Expr_T488); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLog_Expr_T"


    // $ANTLR start "ruleLog_Expr_T"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:265:1: ruleLog_Expr_T : ( ( rule__Log_Expr_T__Alternatives ) ) ;
    public final void ruleLog_Expr_T() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:269:2: ( ( ( rule__Log_Expr_T__Alternatives ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:270:1: ( ( rule__Log_Expr_T__Alternatives ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:270:1: ( ( rule__Log_Expr_T__Alternatives ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:271:1: ( rule__Log_Expr_T__Alternatives )
            {
             before(grammarAccess.getLog_Expr_TAccess().getAlternatives()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:272:1: ( rule__Log_Expr_T__Alternatives )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:272:2: rule__Log_Expr_T__Alternatives
            {
            pushFollow(FOLLOW_rule__Log_Expr_T__Alternatives_in_ruleLog_Expr_T514);
            rule__Log_Expr_T__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getLog_Expr_TAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLog_Expr_T"


    // $ANTLR start "entryRuleLog_Expr_Unary"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:284:1: entryRuleLog_Expr_Unary : ruleLog_Expr_Unary EOF ;
    public final void entryRuleLog_Expr_Unary() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:285:1: ( ruleLog_Expr_Unary EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:286:1: ruleLog_Expr_Unary EOF
            {
             before(grammarAccess.getLog_Expr_UnaryRule()); 
            pushFollow(FOLLOW_ruleLog_Expr_Unary_in_entryRuleLog_Expr_Unary541);
            ruleLog_Expr_Unary();

            state._fsp--;

             after(grammarAccess.getLog_Expr_UnaryRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Expr_Unary548); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLog_Expr_Unary"


    // $ANTLR start "ruleLog_Expr_Unary"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:293:1: ruleLog_Expr_Unary : ( ( rule__Log_Expr_Unary__Alternatives ) ) ;
    public final void ruleLog_Expr_Unary() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:297:2: ( ( ( rule__Log_Expr_Unary__Alternatives ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:298:1: ( ( rule__Log_Expr_Unary__Alternatives ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:298:1: ( ( rule__Log_Expr_Unary__Alternatives ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:299:1: ( rule__Log_Expr_Unary__Alternatives )
            {
             before(grammarAccess.getLog_Expr_UnaryAccess().getAlternatives()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:300:1: ( rule__Log_Expr_Unary__Alternatives )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:300:2: rule__Log_Expr_Unary__Alternatives
            {
            pushFollow(FOLLOW_rule__Log_Expr_Unary__Alternatives_in_ruleLog_Expr_Unary574);
            rule__Log_Expr_Unary__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getLog_Expr_UnaryAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLog_Expr_Unary"


    // $ANTLR start "entryRuleLog_Expr_Binary"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:312:1: entryRuleLog_Expr_Binary : ruleLog_Expr_Binary EOF ;
    public final void entryRuleLog_Expr_Binary() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:313:1: ( ruleLog_Expr_Binary EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:314:1: ruleLog_Expr_Binary EOF
            {
             before(grammarAccess.getLog_Expr_BinaryRule()); 
            pushFollow(FOLLOW_ruleLog_Expr_Binary_in_entryRuleLog_Expr_Binary601);
            ruleLog_Expr_Binary();

            state._fsp--;

             after(grammarAccess.getLog_Expr_BinaryRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Expr_Binary608); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLog_Expr_Binary"


    // $ANTLR start "ruleLog_Expr_Binary"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:321:1: ruleLog_Expr_Binary : ( ( rule__Log_Expr_Binary__Group__0 ) ) ;
    public final void ruleLog_Expr_Binary() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:325:2: ( ( ( rule__Log_Expr_Binary__Group__0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:326:1: ( ( rule__Log_Expr_Binary__Group__0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:326:1: ( ( rule__Log_Expr_Binary__Group__0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:327:1: ( rule__Log_Expr_Binary__Group__0 )
            {
             before(grammarAccess.getLog_Expr_BinaryAccess().getGroup()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:328:1: ( rule__Log_Expr_Binary__Group__0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:328:2: rule__Log_Expr_Binary__Group__0
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__0_in_ruleLog_Expr_Binary634);
            rule__Log_Expr_Binary__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLog_Expr_BinaryAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLog_Expr_Binary"


    // $ANTLR start "entryRuleLog_Neg"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:340:1: entryRuleLog_Neg : ruleLog_Neg EOF ;
    public final void entryRuleLog_Neg() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:341:1: ( ruleLog_Neg EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:342:1: ruleLog_Neg EOF
            {
             before(grammarAccess.getLog_NegRule()); 
            pushFollow(FOLLOW_ruleLog_Neg_in_entryRuleLog_Neg661);
            ruleLog_Neg();

            state._fsp--;

             after(grammarAccess.getLog_NegRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLog_Neg668); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLog_Neg"


    // $ANTLR start "ruleLog_Neg"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:349:1: ruleLog_Neg : ( ( rule__Log_Neg__Group__0 ) ) ;
    public final void ruleLog_Neg() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:353:2: ( ( ( rule__Log_Neg__Group__0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:354:1: ( ( rule__Log_Neg__Group__0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:354:1: ( ( rule__Log_Neg__Group__0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:355:1: ( rule__Log_Neg__Group__0 )
            {
             before(grammarAccess.getLog_NegAccess().getGroup()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:356:1: ( rule__Log_Neg__Group__0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:356:2: rule__Log_Neg__Group__0
            {
            pushFollow(FOLLOW_rule__Log_Neg__Group__0_in_ruleLog_Neg694);
            rule__Log_Neg__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLog_NegAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLog_Neg"


    // $ANTLR start "entryRuleSym"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:368:1: entryRuleSym : ruleSym EOF ;
    public final void entryRuleSym() throws RecognitionException {
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:369:1: ( ruleSym EOF )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:370:1: ruleSym EOF
            {
             before(grammarAccess.getSymRule()); 
            pushFollow(FOLLOW_ruleSym_in_entryRuleSym721);
            ruleSym();

            state._fsp--;

             after(grammarAccess.getSymRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSym728); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSym"


    // $ANTLR start "ruleSym"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:377:1: ruleSym : ( ( rule__Sym__SymAssignment ) ) ;
    public final void ruleSym() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:381:2: ( ( ( rule__Sym__SymAssignment ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:382:1: ( ( rule__Sym__SymAssignment ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:382:1: ( ( rule__Sym__SymAssignment ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:383:1: ( rule__Sym__SymAssignment )
            {
             before(grammarAccess.getSymAccess().getSymAssignment()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:384:1: ( rule__Sym__SymAssignment )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:384:2: rule__Sym__SymAssignment
            {
            pushFollow(FOLLOW_rule__Sym__SymAssignment_in_ruleSym754);
            rule__Sym__SymAssignment();

            state._fsp--;


            }

             after(grammarAccess.getSymAccess().getSymAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSym"


    // $ANTLR start "rule__Stmnt_LST_Elem__Alternatives_0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:398:1: rule__Stmnt_LST_Elem__Alternatives_0 : ( ( ruleRepeat ) | ( ruleAsg ) | ( ruleRead ) | ( ruleComment ) );
    public final void rule__Stmnt_LST_Elem__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:402:1: ( ( ruleRepeat ) | ( ruleAsg ) | ( ruleRead ) | ( ruleComment ) )
            int alt1=4;
            switch ( input.LA(1) ) {
            case 14:
                {
                alt1=1;
                }
                break;
            case RULE_ID:
                {
                alt1=2;
                }
                break;
            case 17:
                {
                alt1=3;
                }
                break;
            case 18:
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
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:403:1: ( ruleRepeat )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:403:1: ( ruleRepeat )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:404:1: ruleRepeat
                    {
                     before(grammarAccess.getStmnt_LST_ElemAccess().getRepeatParserRuleCall_0_0()); 
                    pushFollow(FOLLOW_ruleRepeat_in_rule__Stmnt_LST_Elem__Alternatives_0792);
                    ruleRepeat();

                    state._fsp--;

                     after(grammarAccess.getStmnt_LST_ElemAccess().getRepeatParserRuleCall_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:409:6: ( ruleAsg )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:409:6: ( ruleAsg )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:410:1: ruleAsg
                    {
                     before(grammarAccess.getStmnt_LST_ElemAccess().getAsgParserRuleCall_0_1()); 
                    pushFollow(FOLLOW_ruleAsg_in_rule__Stmnt_LST_Elem__Alternatives_0809);
                    ruleAsg();

                    state._fsp--;

                     after(grammarAccess.getStmnt_LST_ElemAccess().getAsgParserRuleCall_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:415:6: ( ruleRead )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:415:6: ( ruleRead )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:416:1: ruleRead
                    {
                     before(grammarAccess.getStmnt_LST_ElemAccess().getReadParserRuleCall_0_2()); 
                    pushFollow(FOLLOW_ruleRead_in_rule__Stmnt_LST_Elem__Alternatives_0826);
                    ruleRead();

                    state._fsp--;

                     after(grammarAccess.getStmnt_LST_ElemAccess().getReadParserRuleCall_0_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:421:6: ( ruleComment )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:421:6: ( ruleComment )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:422:1: ruleComment
                    {
                     before(grammarAccess.getStmnt_LST_ElemAccess().getCommentParserRuleCall_0_3()); 
                    pushFollow(FOLLOW_ruleComment_in_rule__Stmnt_LST_Elem__Alternatives_0843);
                    ruleComment();

                    state._fsp--;

                     after(grammarAccess.getStmnt_LST_ElemAccess().getCommentParserRuleCall_0_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmnt_LST_Elem__Alternatives_0"


    // $ANTLR start "rule__Log_Expr_T__Alternatives"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:432:1: rule__Log_Expr_T__Alternatives : ( ( ruleLog_Expr_Unary ) | ( ruleLog_Expr_Binary ) );
    public final void rule__Log_Expr_T__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:436:1: ( ( ruleLog_Expr_Unary ) | ( ruleLog_Expr_Binary ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==RULE_ID||LA2_0==22) ) {
                alt2=1;
            }
            else if ( (LA2_0==20) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:437:1: ( ruleLog_Expr_Unary )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:437:1: ( ruleLog_Expr_Unary )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:438:1: ruleLog_Expr_Unary
                    {
                     before(grammarAccess.getLog_Expr_TAccess().getLog_Expr_UnaryParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleLog_Expr_Unary_in_rule__Log_Expr_T__Alternatives875);
                    ruleLog_Expr_Unary();

                    state._fsp--;

                     after(grammarAccess.getLog_Expr_TAccess().getLog_Expr_UnaryParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:443:6: ( ruleLog_Expr_Binary )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:443:6: ( ruleLog_Expr_Binary )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:444:1: ruleLog_Expr_Binary
                    {
                     before(grammarAccess.getLog_Expr_TAccess().getLog_Expr_BinaryParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleLog_Expr_Binary_in_rule__Log_Expr_T__Alternatives892);
                    ruleLog_Expr_Binary();

                    state._fsp--;

                     after(grammarAccess.getLog_Expr_TAccess().getLog_Expr_BinaryParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_T__Alternatives"


    // $ANTLR start "rule__Log_Expr_Unary__Alternatives"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:454:1: rule__Log_Expr_Unary__Alternatives : ( ( ruleLog_Neg ) | ( ruleSym ) );
    public final void rule__Log_Expr_Unary__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:458:1: ( ( ruleLog_Neg ) | ( ruleSym ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==22) ) {
                alt3=1;
            }
            else if ( (LA3_0==RULE_ID) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:459:1: ( ruleLog_Neg )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:459:1: ( ruleLog_Neg )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:460:1: ruleLog_Neg
                    {
                     before(grammarAccess.getLog_Expr_UnaryAccess().getLog_NegParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleLog_Neg_in_rule__Log_Expr_Unary__Alternatives924);
                    ruleLog_Neg();

                    state._fsp--;

                     after(grammarAccess.getLog_Expr_UnaryAccess().getLog_NegParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:465:6: ( ruleSym )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:465:6: ( ruleSym )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:466:1: ruleSym
                    {
                     before(grammarAccess.getLog_Expr_UnaryAccess().getSymParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleSym_in_rule__Log_Expr_Unary__Alternatives941);
                    ruleSym();

                    state._fsp--;

                     after(grammarAccess.getLog_Expr_UnaryAccess().getSymParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Unary__Alternatives"


    // $ANTLR start "rule__Log_Expr_Binary__OperatorAlternatives_2_0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:476:1: rule__Log_Expr_Binary__OperatorAlternatives_2_0 : ( ( 'AND' ) | ( 'OR' ) | ( 'EQ' ) );
    public final void rule__Log_Expr_Binary__OperatorAlternatives_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:480:1: ( ( 'AND' ) | ( 'OR' ) | ( 'EQ' ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt4=1;
                }
                break;
            case 12:
                {
                alt4=2;
                }
                break;
            case 13:
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
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:481:1: ( 'AND' )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:481:1: ( 'AND' )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:482:1: 'AND'
                    {
                     before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorANDKeyword_2_0_0()); 
                    match(input,11,FOLLOW_11_in_rule__Log_Expr_Binary__OperatorAlternatives_2_0974); 
                     after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorANDKeyword_2_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:489:6: ( 'OR' )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:489:6: ( 'OR' )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:490:1: 'OR'
                    {
                     before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorORKeyword_2_0_1()); 
                    match(input,12,FOLLOW_12_in_rule__Log_Expr_Binary__OperatorAlternatives_2_0994); 
                     after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorORKeyword_2_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:497:6: ( 'EQ' )
                    {
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:497:6: ( 'EQ' )
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:498:1: 'EQ'
                    {
                     before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorEQKeyword_2_0_2()); 
                    match(input,13,FOLLOW_13_in_rule__Log_Expr_Binary__OperatorAlternatives_2_01014); 
                     after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorEQKeyword_2_0_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__OperatorAlternatives_2_0"


    // $ANTLR start "rule__Stmnt_LST_Elem__Group__0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:513:1: rule__Stmnt_LST_Elem__Group__0 : rule__Stmnt_LST_Elem__Group__0__Impl rule__Stmnt_LST_Elem__Group__1 ;
    public final void rule__Stmnt_LST_Elem__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:517:1: ( rule__Stmnt_LST_Elem__Group__0__Impl rule__Stmnt_LST_Elem__Group__1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:518:2: rule__Stmnt_LST_Elem__Group__0__Impl rule__Stmnt_LST_Elem__Group__1
            {
            pushFollow(FOLLOW_rule__Stmnt_LST_Elem__Group__0__Impl_in_rule__Stmnt_LST_Elem__Group__01047);
            rule__Stmnt_LST_Elem__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Stmnt_LST_Elem__Group__1_in_rule__Stmnt_LST_Elem__Group__01050);
            rule__Stmnt_LST_Elem__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmnt_LST_Elem__Group__0"


    // $ANTLR start "rule__Stmnt_LST_Elem__Group__0__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:525:1: rule__Stmnt_LST_Elem__Group__0__Impl : ( ( rule__Stmnt_LST_Elem__Alternatives_0 ) ) ;
    public final void rule__Stmnt_LST_Elem__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:529:1: ( ( ( rule__Stmnt_LST_Elem__Alternatives_0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:530:1: ( ( rule__Stmnt_LST_Elem__Alternatives_0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:530:1: ( ( rule__Stmnt_LST_Elem__Alternatives_0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:531:1: ( rule__Stmnt_LST_Elem__Alternatives_0 )
            {
             before(grammarAccess.getStmnt_LST_ElemAccess().getAlternatives_0()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:532:1: ( rule__Stmnt_LST_Elem__Alternatives_0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:532:2: rule__Stmnt_LST_Elem__Alternatives_0
            {
            pushFollow(FOLLOW_rule__Stmnt_LST_Elem__Alternatives_0_in_rule__Stmnt_LST_Elem__Group__0__Impl1077);
            rule__Stmnt_LST_Elem__Alternatives_0();

            state._fsp--;


            }

             after(grammarAccess.getStmnt_LST_ElemAccess().getAlternatives_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmnt_LST_Elem__Group__0__Impl"


    // $ANTLR start "rule__Stmnt_LST_Elem__Group__1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:542:1: rule__Stmnt_LST_Elem__Group__1 : rule__Stmnt_LST_Elem__Group__1__Impl ;
    public final void rule__Stmnt_LST_Elem__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:546:1: ( rule__Stmnt_LST_Elem__Group__1__Impl )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:547:2: rule__Stmnt_LST_Elem__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Stmnt_LST_Elem__Group__1__Impl_in_rule__Stmnt_LST_Elem__Group__11107);
            rule__Stmnt_LST_Elem__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmnt_LST_Elem__Group__1"


    // $ANTLR start "rule__Stmnt_LST_Elem__Group__1__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:553:1: rule__Stmnt_LST_Elem__Group__1__Impl : ( ( rule__Stmnt_LST_Elem__NextAssignment_1 )? ) ;
    public final void rule__Stmnt_LST_Elem__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:557:1: ( ( ( rule__Stmnt_LST_Elem__NextAssignment_1 )? ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:558:1: ( ( rule__Stmnt_LST_Elem__NextAssignment_1 )? )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:558:1: ( ( rule__Stmnt_LST_Elem__NextAssignment_1 )? )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:559:1: ( rule__Stmnt_LST_Elem__NextAssignment_1 )?
            {
             before(grammarAccess.getStmnt_LST_ElemAccess().getNextAssignment_1()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:560:1: ( rule__Stmnt_LST_Elem__NextAssignment_1 )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_ID||LA5_0==14||(LA5_0>=17 && LA5_0<=18)) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:560:2: rule__Stmnt_LST_Elem__NextAssignment_1
                    {
                    pushFollow(FOLLOW_rule__Stmnt_LST_Elem__NextAssignment_1_in_rule__Stmnt_LST_Elem__Group__1__Impl1134);
                    rule__Stmnt_LST_Elem__NextAssignment_1();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getStmnt_LST_ElemAccess().getNextAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmnt_LST_Elem__Group__1__Impl"


    // $ANTLR start "rule__Repeat__Group__0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:574:1: rule__Repeat__Group__0 : rule__Repeat__Group__0__Impl rule__Repeat__Group__1 ;
    public final void rule__Repeat__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:578:1: ( rule__Repeat__Group__0__Impl rule__Repeat__Group__1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:579:2: rule__Repeat__Group__0__Impl rule__Repeat__Group__1
            {
            pushFollow(FOLLOW_rule__Repeat__Group__0__Impl_in_rule__Repeat__Group__01169);
            rule__Repeat__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Repeat__Group__1_in_rule__Repeat__Group__01172);
            rule__Repeat__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__Group__0"


    // $ANTLR start "rule__Repeat__Group__0__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:586:1: rule__Repeat__Group__0__Impl : ( 'REPEAT' ) ;
    public final void rule__Repeat__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:590:1: ( ( 'REPEAT' ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:591:1: ( 'REPEAT' )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:591:1: ( 'REPEAT' )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:592:1: 'REPEAT'
            {
             before(grammarAccess.getRepeatAccess().getREPEATKeyword_0()); 
            match(input,14,FOLLOW_14_in_rule__Repeat__Group__0__Impl1200); 
             after(grammarAccess.getRepeatAccess().getREPEATKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__Group__0__Impl"


    // $ANTLR start "rule__Repeat__Group__1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:605:1: rule__Repeat__Group__1 : rule__Repeat__Group__1__Impl rule__Repeat__Group__2 ;
    public final void rule__Repeat__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:609:1: ( rule__Repeat__Group__1__Impl rule__Repeat__Group__2 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:610:2: rule__Repeat__Group__1__Impl rule__Repeat__Group__2
            {
            pushFollow(FOLLOW_rule__Repeat__Group__1__Impl_in_rule__Repeat__Group__11231);
            rule__Repeat__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Repeat__Group__2_in_rule__Repeat__Group__11234);
            rule__Repeat__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__Group__1"


    // $ANTLR start "rule__Repeat__Group__1__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:617:1: rule__Repeat__Group__1__Impl : ( ( rule__Repeat__StmntAssignment_1 ) ) ;
    public final void rule__Repeat__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:621:1: ( ( ( rule__Repeat__StmntAssignment_1 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:622:1: ( ( rule__Repeat__StmntAssignment_1 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:622:1: ( ( rule__Repeat__StmntAssignment_1 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:623:1: ( rule__Repeat__StmntAssignment_1 )
            {
             before(grammarAccess.getRepeatAccess().getStmntAssignment_1()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:624:1: ( rule__Repeat__StmntAssignment_1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:624:2: rule__Repeat__StmntAssignment_1
            {
            pushFollow(FOLLOW_rule__Repeat__StmntAssignment_1_in_rule__Repeat__Group__1__Impl1261);
            rule__Repeat__StmntAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getRepeatAccess().getStmntAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__Group__1__Impl"


    // $ANTLR start "rule__Repeat__Group__2"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:634:1: rule__Repeat__Group__2 : rule__Repeat__Group__2__Impl rule__Repeat__Group__3 ;
    public final void rule__Repeat__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:638:1: ( rule__Repeat__Group__2__Impl rule__Repeat__Group__3 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:639:2: rule__Repeat__Group__2__Impl rule__Repeat__Group__3
            {
            pushFollow(FOLLOW_rule__Repeat__Group__2__Impl_in_rule__Repeat__Group__21291);
            rule__Repeat__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Repeat__Group__3_in_rule__Repeat__Group__21294);
            rule__Repeat__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__Group__2"


    // $ANTLR start "rule__Repeat__Group__2__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:646:1: rule__Repeat__Group__2__Impl : ( 'UNTIL' ) ;
    public final void rule__Repeat__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:650:1: ( ( 'UNTIL' ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:651:1: ( 'UNTIL' )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:651:1: ( 'UNTIL' )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:652:1: 'UNTIL'
            {
             before(grammarAccess.getRepeatAccess().getUNTILKeyword_2()); 
            match(input,15,FOLLOW_15_in_rule__Repeat__Group__2__Impl1322); 
             after(grammarAccess.getRepeatAccess().getUNTILKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__Group__2__Impl"


    // $ANTLR start "rule__Repeat__Group__3"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:665:1: rule__Repeat__Group__3 : rule__Repeat__Group__3__Impl ;
    public final void rule__Repeat__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:669:1: ( rule__Repeat__Group__3__Impl )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:670:2: rule__Repeat__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Repeat__Group__3__Impl_in_rule__Repeat__Group__31353);
            rule__Repeat__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__Group__3"


    // $ANTLR start "rule__Repeat__Group__3__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:676:1: rule__Repeat__Group__3__Impl : ( ( rule__Repeat__ExprAssignment_3 ) ) ;
    public final void rule__Repeat__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:680:1: ( ( ( rule__Repeat__ExprAssignment_3 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:681:1: ( ( rule__Repeat__ExprAssignment_3 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:681:1: ( ( rule__Repeat__ExprAssignment_3 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:682:1: ( rule__Repeat__ExprAssignment_3 )
            {
             before(grammarAccess.getRepeatAccess().getExprAssignment_3()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:683:1: ( rule__Repeat__ExprAssignment_3 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:683:2: rule__Repeat__ExprAssignment_3
            {
            pushFollow(FOLLOW_rule__Repeat__ExprAssignment_3_in_rule__Repeat__Group__3__Impl1380);
            rule__Repeat__ExprAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getRepeatAccess().getExprAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__Group__3__Impl"


    // $ANTLR start "rule__Asg__Group__0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:701:1: rule__Asg__Group__0 : rule__Asg__Group__0__Impl rule__Asg__Group__1 ;
    public final void rule__Asg__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:705:1: ( rule__Asg__Group__0__Impl rule__Asg__Group__1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:706:2: rule__Asg__Group__0__Impl rule__Asg__Group__1
            {
            pushFollow(FOLLOW_rule__Asg__Group__0__Impl_in_rule__Asg__Group__01418);
            rule__Asg__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Asg__Group__1_in_rule__Asg__Group__01421);
            rule__Asg__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Asg__Group__0"


    // $ANTLR start "rule__Asg__Group__0__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:713:1: rule__Asg__Group__0__Impl : ( ( rule__Asg__LeftAssignment_0 ) ) ;
    public final void rule__Asg__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:717:1: ( ( ( rule__Asg__LeftAssignment_0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:718:1: ( ( rule__Asg__LeftAssignment_0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:718:1: ( ( rule__Asg__LeftAssignment_0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:719:1: ( rule__Asg__LeftAssignment_0 )
            {
             before(grammarAccess.getAsgAccess().getLeftAssignment_0()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:720:1: ( rule__Asg__LeftAssignment_0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:720:2: rule__Asg__LeftAssignment_0
            {
            pushFollow(FOLLOW_rule__Asg__LeftAssignment_0_in_rule__Asg__Group__0__Impl1448);
            rule__Asg__LeftAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getAsgAccess().getLeftAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Asg__Group__0__Impl"


    // $ANTLR start "rule__Asg__Group__1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:730:1: rule__Asg__Group__1 : rule__Asg__Group__1__Impl rule__Asg__Group__2 ;
    public final void rule__Asg__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:734:1: ( rule__Asg__Group__1__Impl rule__Asg__Group__2 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:735:2: rule__Asg__Group__1__Impl rule__Asg__Group__2
            {
            pushFollow(FOLLOW_rule__Asg__Group__1__Impl_in_rule__Asg__Group__11478);
            rule__Asg__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Asg__Group__2_in_rule__Asg__Group__11481);
            rule__Asg__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Asg__Group__1"


    // $ANTLR start "rule__Asg__Group__1__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:742:1: rule__Asg__Group__1__Impl : ( ':=' ) ;
    public final void rule__Asg__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:746:1: ( ( ':=' ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:747:1: ( ':=' )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:747:1: ( ':=' )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:748:1: ':='
            {
             before(grammarAccess.getAsgAccess().getColonEqualsSignKeyword_1()); 
            match(input,16,FOLLOW_16_in_rule__Asg__Group__1__Impl1509); 
             after(grammarAccess.getAsgAccess().getColonEqualsSignKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Asg__Group__1__Impl"


    // $ANTLR start "rule__Asg__Group__2"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:761:1: rule__Asg__Group__2 : rule__Asg__Group__2__Impl ;
    public final void rule__Asg__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:765:1: ( rule__Asg__Group__2__Impl )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:766:2: rule__Asg__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Asg__Group__2__Impl_in_rule__Asg__Group__21540);
            rule__Asg__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Asg__Group__2"


    // $ANTLR start "rule__Asg__Group__2__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:772:1: rule__Asg__Group__2__Impl : ( ( rule__Asg__RightAssignment_2 ) ) ;
    public final void rule__Asg__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:776:1: ( ( ( rule__Asg__RightAssignment_2 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:777:1: ( ( rule__Asg__RightAssignment_2 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:777:1: ( ( rule__Asg__RightAssignment_2 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:778:1: ( rule__Asg__RightAssignment_2 )
            {
             before(grammarAccess.getAsgAccess().getRightAssignment_2()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:779:1: ( rule__Asg__RightAssignment_2 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:779:2: rule__Asg__RightAssignment_2
            {
            pushFollow(FOLLOW_rule__Asg__RightAssignment_2_in_rule__Asg__Group__2__Impl1567);
            rule__Asg__RightAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getAsgAccess().getRightAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Asg__Group__2__Impl"


    // $ANTLR start "rule__Read__Group__0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:795:1: rule__Read__Group__0 : rule__Read__Group__0__Impl rule__Read__Group__1 ;
    public final void rule__Read__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:799:1: ( rule__Read__Group__0__Impl rule__Read__Group__1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:800:2: rule__Read__Group__0__Impl rule__Read__Group__1
            {
            pushFollow(FOLLOW_rule__Read__Group__0__Impl_in_rule__Read__Group__01603);
            rule__Read__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Read__Group__1_in_rule__Read__Group__01606);
            rule__Read__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Read__Group__0"


    // $ANTLR start "rule__Read__Group__0__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:807:1: rule__Read__Group__0__Impl : ( 'READ' ) ;
    public final void rule__Read__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:811:1: ( ( 'READ' ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:812:1: ( 'READ' )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:812:1: ( 'READ' )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:813:1: 'READ'
            {
             before(grammarAccess.getReadAccess().getREADKeyword_0()); 
            match(input,17,FOLLOW_17_in_rule__Read__Group__0__Impl1634); 
             after(grammarAccess.getReadAccess().getREADKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Read__Group__0__Impl"


    // $ANTLR start "rule__Read__Group__1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:826:1: rule__Read__Group__1 : rule__Read__Group__1__Impl ;
    public final void rule__Read__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:830:1: ( rule__Read__Group__1__Impl )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:831:2: rule__Read__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Read__Group__1__Impl_in_rule__Read__Group__11665);
            rule__Read__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Read__Group__1"


    // $ANTLR start "rule__Read__Group__1__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:837:1: rule__Read__Group__1__Impl : ( ( rule__Read__ParamAssignment_1 ) ) ;
    public final void rule__Read__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:841:1: ( ( ( rule__Read__ParamAssignment_1 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:842:1: ( ( rule__Read__ParamAssignment_1 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:842:1: ( ( rule__Read__ParamAssignment_1 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:843:1: ( rule__Read__ParamAssignment_1 )
            {
             before(grammarAccess.getReadAccess().getParamAssignment_1()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:844:1: ( rule__Read__ParamAssignment_1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:844:2: rule__Read__ParamAssignment_1
            {
            pushFollow(FOLLOW_rule__Read__ParamAssignment_1_in_rule__Read__Group__1__Impl1692);
            rule__Read__ParamAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getReadAccess().getParamAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Read__Group__1__Impl"


    // $ANTLR start "rule__Comment__Group__0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:858:1: rule__Comment__Group__0 : rule__Comment__Group__0__Impl rule__Comment__Group__1 ;
    public final void rule__Comment__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:862:1: ( rule__Comment__Group__0__Impl rule__Comment__Group__1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:863:2: rule__Comment__Group__0__Impl rule__Comment__Group__1
            {
            pushFollow(FOLLOW_rule__Comment__Group__0__Impl_in_rule__Comment__Group__01726);
            rule__Comment__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Comment__Group__1_in_rule__Comment__Group__01729);
            rule__Comment__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Comment__Group__0"


    // $ANTLR start "rule__Comment__Group__0__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:870:1: rule__Comment__Group__0__Impl : ( '/#' ) ;
    public final void rule__Comment__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:874:1: ( ( '/#' ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:875:1: ( '/#' )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:875:1: ( '/#' )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:876:1: '/#'
            {
             before(grammarAccess.getCommentAccess().getSolidusNumberSignKeyword_0()); 
            match(input,18,FOLLOW_18_in_rule__Comment__Group__0__Impl1757); 
             after(grammarAccess.getCommentAccess().getSolidusNumberSignKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Comment__Group__0__Impl"


    // $ANTLR start "rule__Comment__Group__1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:889:1: rule__Comment__Group__1 : rule__Comment__Group__1__Impl rule__Comment__Group__2 ;
    public final void rule__Comment__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:893:1: ( rule__Comment__Group__1__Impl rule__Comment__Group__2 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:894:2: rule__Comment__Group__1__Impl rule__Comment__Group__2
            {
            pushFollow(FOLLOW_rule__Comment__Group__1__Impl_in_rule__Comment__Group__11788);
            rule__Comment__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Comment__Group__2_in_rule__Comment__Group__11791);
            rule__Comment__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Comment__Group__1"


    // $ANTLR start "rule__Comment__Group__1__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:901:1: rule__Comment__Group__1__Impl : ( ( rule__Comment__CommentAssignment_1 ) ) ;
    public final void rule__Comment__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:905:1: ( ( ( rule__Comment__CommentAssignment_1 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:906:1: ( ( rule__Comment__CommentAssignment_1 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:906:1: ( ( rule__Comment__CommentAssignment_1 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:907:1: ( rule__Comment__CommentAssignment_1 )
            {
             before(grammarAccess.getCommentAccess().getCommentAssignment_1()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:908:1: ( rule__Comment__CommentAssignment_1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:908:2: rule__Comment__CommentAssignment_1
            {
            pushFollow(FOLLOW_rule__Comment__CommentAssignment_1_in_rule__Comment__Group__1__Impl1818);
            rule__Comment__CommentAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getCommentAccess().getCommentAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Comment__Group__1__Impl"


    // $ANTLR start "rule__Comment__Group__2"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:918:1: rule__Comment__Group__2 : rule__Comment__Group__2__Impl ;
    public final void rule__Comment__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:922:1: ( rule__Comment__Group__2__Impl )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:923:2: rule__Comment__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Comment__Group__2__Impl_in_rule__Comment__Group__21848);
            rule__Comment__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Comment__Group__2"


    // $ANTLR start "rule__Comment__Group__2__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:929:1: rule__Comment__Group__2__Impl : ( '#/' ) ;
    public final void rule__Comment__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:933:1: ( ( '#/' ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:934:1: ( '#/' )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:934:1: ( '#/' )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:935:1: '#/'
            {
             before(grammarAccess.getCommentAccess().getNumberSignSolidusKeyword_2()); 
            match(input,19,FOLLOW_19_in_rule__Comment__Group__2__Impl1876); 
             after(grammarAccess.getCommentAccess().getNumberSignSolidusKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Comment__Group__2__Impl"


    // $ANTLR start "rule__Log_Expr_Binary__Group__0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:954:1: rule__Log_Expr_Binary__Group__0 : rule__Log_Expr_Binary__Group__0__Impl rule__Log_Expr_Binary__Group__1 ;
    public final void rule__Log_Expr_Binary__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:958:1: ( rule__Log_Expr_Binary__Group__0__Impl rule__Log_Expr_Binary__Group__1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:959:2: rule__Log_Expr_Binary__Group__0__Impl rule__Log_Expr_Binary__Group__1
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__0__Impl_in_rule__Log_Expr_Binary__Group__01913);
            rule__Log_Expr_Binary__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__1_in_rule__Log_Expr_Binary__Group__01916);
            rule__Log_Expr_Binary__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__0"


    // $ANTLR start "rule__Log_Expr_Binary__Group__0__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:966:1: rule__Log_Expr_Binary__Group__0__Impl : ( '(' ) ;
    public final void rule__Log_Expr_Binary__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:970:1: ( ( '(' ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:971:1: ( '(' )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:971:1: ( '(' )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:972:1: '('
            {
             before(grammarAccess.getLog_Expr_BinaryAccess().getLeftParenthesisKeyword_0()); 
            match(input,20,FOLLOW_20_in_rule__Log_Expr_Binary__Group__0__Impl1944); 
             after(grammarAccess.getLog_Expr_BinaryAccess().getLeftParenthesisKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__0__Impl"


    // $ANTLR start "rule__Log_Expr_Binary__Group__1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:985:1: rule__Log_Expr_Binary__Group__1 : rule__Log_Expr_Binary__Group__1__Impl rule__Log_Expr_Binary__Group__2 ;
    public final void rule__Log_Expr_Binary__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:989:1: ( rule__Log_Expr_Binary__Group__1__Impl rule__Log_Expr_Binary__Group__2 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:990:2: rule__Log_Expr_Binary__Group__1__Impl rule__Log_Expr_Binary__Group__2
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__1__Impl_in_rule__Log_Expr_Binary__Group__11975);
            rule__Log_Expr_Binary__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__2_in_rule__Log_Expr_Binary__Group__11978);
            rule__Log_Expr_Binary__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__1"


    // $ANTLR start "rule__Log_Expr_Binary__Group__1__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:997:1: rule__Log_Expr_Binary__Group__1__Impl : ( ( rule__Log_Expr_Binary__FstAssignment_1 ) ) ;
    public final void rule__Log_Expr_Binary__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1001:1: ( ( ( rule__Log_Expr_Binary__FstAssignment_1 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1002:1: ( ( rule__Log_Expr_Binary__FstAssignment_1 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1002:1: ( ( rule__Log_Expr_Binary__FstAssignment_1 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1003:1: ( rule__Log_Expr_Binary__FstAssignment_1 )
            {
             before(grammarAccess.getLog_Expr_BinaryAccess().getFstAssignment_1()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1004:1: ( rule__Log_Expr_Binary__FstAssignment_1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1004:2: rule__Log_Expr_Binary__FstAssignment_1
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__FstAssignment_1_in_rule__Log_Expr_Binary__Group__1__Impl2005);
            rule__Log_Expr_Binary__FstAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLog_Expr_BinaryAccess().getFstAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__1__Impl"


    // $ANTLR start "rule__Log_Expr_Binary__Group__2"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1014:1: rule__Log_Expr_Binary__Group__2 : rule__Log_Expr_Binary__Group__2__Impl rule__Log_Expr_Binary__Group__3 ;
    public final void rule__Log_Expr_Binary__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1018:1: ( rule__Log_Expr_Binary__Group__2__Impl rule__Log_Expr_Binary__Group__3 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1019:2: rule__Log_Expr_Binary__Group__2__Impl rule__Log_Expr_Binary__Group__3
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__2__Impl_in_rule__Log_Expr_Binary__Group__22035);
            rule__Log_Expr_Binary__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__3_in_rule__Log_Expr_Binary__Group__22038);
            rule__Log_Expr_Binary__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__2"


    // $ANTLR start "rule__Log_Expr_Binary__Group__2__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1026:1: rule__Log_Expr_Binary__Group__2__Impl : ( ( rule__Log_Expr_Binary__OperatorAssignment_2 ) ) ;
    public final void rule__Log_Expr_Binary__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1030:1: ( ( ( rule__Log_Expr_Binary__OperatorAssignment_2 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1031:1: ( ( rule__Log_Expr_Binary__OperatorAssignment_2 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1031:1: ( ( rule__Log_Expr_Binary__OperatorAssignment_2 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1032:1: ( rule__Log_Expr_Binary__OperatorAssignment_2 )
            {
             before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorAssignment_2()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1033:1: ( rule__Log_Expr_Binary__OperatorAssignment_2 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1033:2: rule__Log_Expr_Binary__OperatorAssignment_2
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__OperatorAssignment_2_in_rule__Log_Expr_Binary__Group__2__Impl2065);
            rule__Log_Expr_Binary__OperatorAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__2__Impl"


    // $ANTLR start "rule__Log_Expr_Binary__Group__3"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1043:1: rule__Log_Expr_Binary__Group__3 : rule__Log_Expr_Binary__Group__3__Impl rule__Log_Expr_Binary__Group__4 ;
    public final void rule__Log_Expr_Binary__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1047:1: ( rule__Log_Expr_Binary__Group__3__Impl rule__Log_Expr_Binary__Group__4 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1048:2: rule__Log_Expr_Binary__Group__3__Impl rule__Log_Expr_Binary__Group__4
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__3__Impl_in_rule__Log_Expr_Binary__Group__32095);
            rule__Log_Expr_Binary__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__4_in_rule__Log_Expr_Binary__Group__32098);
            rule__Log_Expr_Binary__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__3"


    // $ANTLR start "rule__Log_Expr_Binary__Group__3__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1055:1: rule__Log_Expr_Binary__Group__3__Impl : ( ( rule__Log_Expr_Binary__SndAssignment_3 ) ) ;
    public final void rule__Log_Expr_Binary__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1059:1: ( ( ( rule__Log_Expr_Binary__SndAssignment_3 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1060:1: ( ( rule__Log_Expr_Binary__SndAssignment_3 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1060:1: ( ( rule__Log_Expr_Binary__SndAssignment_3 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1061:1: ( rule__Log_Expr_Binary__SndAssignment_3 )
            {
             before(grammarAccess.getLog_Expr_BinaryAccess().getSndAssignment_3()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1062:1: ( rule__Log_Expr_Binary__SndAssignment_3 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1062:2: rule__Log_Expr_Binary__SndAssignment_3
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__SndAssignment_3_in_rule__Log_Expr_Binary__Group__3__Impl2125);
            rule__Log_Expr_Binary__SndAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getLog_Expr_BinaryAccess().getSndAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__3__Impl"


    // $ANTLR start "rule__Log_Expr_Binary__Group__4"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1072:1: rule__Log_Expr_Binary__Group__4 : rule__Log_Expr_Binary__Group__4__Impl ;
    public final void rule__Log_Expr_Binary__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1076:1: ( rule__Log_Expr_Binary__Group__4__Impl )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1077:2: rule__Log_Expr_Binary__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__Group__4__Impl_in_rule__Log_Expr_Binary__Group__42155);
            rule__Log_Expr_Binary__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__4"


    // $ANTLR start "rule__Log_Expr_Binary__Group__4__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1083:1: rule__Log_Expr_Binary__Group__4__Impl : ( ')' ) ;
    public final void rule__Log_Expr_Binary__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1087:1: ( ( ')' ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1088:1: ( ')' )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1088:1: ( ')' )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1089:1: ')'
            {
             before(grammarAccess.getLog_Expr_BinaryAccess().getRightParenthesisKeyword_4()); 
            match(input,21,FOLLOW_21_in_rule__Log_Expr_Binary__Group__4__Impl2183); 
             after(grammarAccess.getLog_Expr_BinaryAccess().getRightParenthesisKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__Group__4__Impl"


    // $ANTLR start "rule__Log_Neg__Group__0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1112:1: rule__Log_Neg__Group__0 : rule__Log_Neg__Group__0__Impl rule__Log_Neg__Group__1 ;
    public final void rule__Log_Neg__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1116:1: ( rule__Log_Neg__Group__0__Impl rule__Log_Neg__Group__1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1117:2: rule__Log_Neg__Group__0__Impl rule__Log_Neg__Group__1
            {
            pushFollow(FOLLOW_rule__Log_Neg__Group__0__Impl_in_rule__Log_Neg__Group__02224);
            rule__Log_Neg__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Log_Neg__Group__1_in_rule__Log_Neg__Group__02227);
            rule__Log_Neg__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Neg__Group__0"


    // $ANTLR start "rule__Log_Neg__Group__0__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1124:1: rule__Log_Neg__Group__0__Impl : ( 'NOT' ) ;
    public final void rule__Log_Neg__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1128:1: ( ( 'NOT' ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1129:1: ( 'NOT' )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1129:1: ( 'NOT' )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1130:1: 'NOT'
            {
             before(grammarAccess.getLog_NegAccess().getNOTKeyword_0()); 
            match(input,22,FOLLOW_22_in_rule__Log_Neg__Group__0__Impl2255); 
             after(grammarAccess.getLog_NegAccess().getNOTKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Neg__Group__0__Impl"


    // $ANTLR start "rule__Log_Neg__Group__1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1143:1: rule__Log_Neg__Group__1 : rule__Log_Neg__Group__1__Impl ;
    public final void rule__Log_Neg__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1147:1: ( rule__Log_Neg__Group__1__Impl )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1148:2: rule__Log_Neg__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Log_Neg__Group__1__Impl_in_rule__Log_Neg__Group__12286);
            rule__Log_Neg__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Neg__Group__1"


    // $ANTLR start "rule__Log_Neg__Group__1__Impl"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1154:1: rule__Log_Neg__Group__1__Impl : ( ( rule__Log_Neg__ExprAssignment_1 ) ) ;
    public final void rule__Log_Neg__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1158:1: ( ( ( rule__Log_Neg__ExprAssignment_1 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1159:1: ( ( rule__Log_Neg__ExprAssignment_1 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1159:1: ( ( rule__Log_Neg__ExprAssignment_1 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1160:1: ( rule__Log_Neg__ExprAssignment_1 )
            {
             before(grammarAccess.getLog_NegAccess().getExprAssignment_1()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1161:1: ( rule__Log_Neg__ExprAssignment_1 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1161:2: rule__Log_Neg__ExprAssignment_1
            {
            pushFollow(FOLLOW_rule__Log_Neg__ExprAssignment_1_in_rule__Log_Neg__Group__1__Impl2313);
            rule__Log_Neg__ExprAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLog_NegAccess().getExprAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Neg__Group__1__Impl"


    // $ANTLR start "rule__RProgram__FstAssignment"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1176:1: rule__RProgram__FstAssignment : ( ruleStmnt_LST_Elem ) ;
    public final void rule__RProgram__FstAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1180:1: ( ( ruleStmnt_LST_Elem ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1181:1: ( ruleStmnt_LST_Elem )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1181:1: ( ruleStmnt_LST_Elem )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1182:1: ruleStmnt_LST_Elem
            {
             before(grammarAccess.getRProgramAccess().getFstStmnt_LST_ElemParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleStmnt_LST_Elem_in_rule__RProgram__FstAssignment2352);
            ruleStmnt_LST_Elem();

            state._fsp--;

             after(grammarAccess.getRProgramAccess().getFstStmnt_LST_ElemParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RProgram__FstAssignment"


    // $ANTLR start "rule__Stmnt_LST_Elem__NextAssignment_1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1191:1: rule__Stmnt_LST_Elem__NextAssignment_1 : ( ruleStmnt_LST_Elem ) ;
    public final void rule__Stmnt_LST_Elem__NextAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1195:1: ( ( ruleStmnt_LST_Elem ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1196:1: ( ruleStmnt_LST_Elem )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1196:1: ( ruleStmnt_LST_Elem )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1197:1: ruleStmnt_LST_Elem
            {
             before(grammarAccess.getStmnt_LST_ElemAccess().getNextStmnt_LST_ElemParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleStmnt_LST_Elem_in_rule__Stmnt_LST_Elem__NextAssignment_12383);
            ruleStmnt_LST_Elem();

            state._fsp--;

             after(grammarAccess.getStmnt_LST_ElemAccess().getNextStmnt_LST_ElemParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmnt_LST_Elem__NextAssignment_1"


    // $ANTLR start "rule__Repeat__StmntAssignment_1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1206:1: rule__Repeat__StmntAssignment_1 : ( ruleStmnt_LST_Elem ) ;
    public final void rule__Repeat__StmntAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1210:1: ( ( ruleStmnt_LST_Elem ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1211:1: ( ruleStmnt_LST_Elem )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1211:1: ( ruleStmnt_LST_Elem )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1212:1: ruleStmnt_LST_Elem
            {
             before(grammarAccess.getRepeatAccess().getStmntStmnt_LST_ElemParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleStmnt_LST_Elem_in_rule__Repeat__StmntAssignment_12414);
            ruleStmnt_LST_Elem();

            state._fsp--;

             after(grammarAccess.getRepeatAccess().getStmntStmnt_LST_ElemParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__StmntAssignment_1"


    // $ANTLR start "rule__Repeat__ExprAssignment_3"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1221:1: rule__Repeat__ExprAssignment_3 : ( ruleLog_Expr ) ;
    public final void rule__Repeat__ExprAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1225:1: ( ( ruleLog_Expr ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1226:1: ( ruleLog_Expr )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1226:1: ( ruleLog_Expr )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1227:1: ruleLog_Expr
            {
             before(grammarAccess.getRepeatAccess().getExprLog_ExprParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleLog_Expr_in_rule__Repeat__ExprAssignment_32445);
            ruleLog_Expr();

            state._fsp--;

             after(grammarAccess.getRepeatAccess().getExprLog_ExprParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat__ExprAssignment_3"


    // $ANTLR start "rule__Asg__LeftAssignment_0"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1236:1: rule__Asg__LeftAssignment_0 : ( ruleSym ) ;
    public final void rule__Asg__LeftAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1240:1: ( ( ruleSym ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1241:1: ( ruleSym )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1241:1: ( ruleSym )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1242:1: ruleSym
            {
             before(grammarAccess.getAsgAccess().getLeftSymParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleSym_in_rule__Asg__LeftAssignment_02476);
            ruleSym();

            state._fsp--;

             after(grammarAccess.getAsgAccess().getLeftSymParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Asg__LeftAssignment_0"


    // $ANTLR start "rule__Asg__RightAssignment_2"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1251:1: rule__Asg__RightAssignment_2 : ( ruleSym ) ;
    public final void rule__Asg__RightAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1255:1: ( ( ruleSym ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1256:1: ( ruleSym )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1256:1: ( ruleSym )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1257:1: ruleSym
            {
             before(grammarAccess.getAsgAccess().getRightSymParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleSym_in_rule__Asg__RightAssignment_22507);
            ruleSym();

            state._fsp--;

             after(grammarAccess.getAsgAccess().getRightSymParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Asg__RightAssignment_2"


    // $ANTLR start "rule__Read__ParamAssignment_1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1266:1: rule__Read__ParamAssignment_1 : ( ruleSym ) ;
    public final void rule__Read__ParamAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1270:1: ( ( ruleSym ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1271:1: ( ruleSym )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1271:1: ( ruleSym )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1272:1: ruleSym
            {
             before(grammarAccess.getReadAccess().getParamSymParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleSym_in_rule__Read__ParamAssignment_12538);
            ruleSym();

            state._fsp--;

             after(grammarAccess.getReadAccess().getParamSymParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Read__ParamAssignment_1"


    // $ANTLR start "rule__Comment__CommentAssignment_1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1281:1: rule__Comment__CommentAssignment_1 : ( RULE_STRING ) ;
    public final void rule__Comment__CommentAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1285:1: ( ( RULE_STRING ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1286:1: ( RULE_STRING )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1286:1: ( RULE_STRING )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1287:1: RULE_STRING
            {
             before(grammarAccess.getCommentAccess().getCommentSTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Comment__CommentAssignment_12569); 
             after(grammarAccess.getCommentAccess().getCommentSTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Comment__CommentAssignment_1"


    // $ANTLR start "rule__Log_Expr__TypeAssignment"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1296:1: rule__Log_Expr__TypeAssignment : ( ruleLog_Expr_T ) ;
    public final void rule__Log_Expr__TypeAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1300:1: ( ( ruleLog_Expr_T ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1301:1: ( ruleLog_Expr_T )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1301:1: ( ruleLog_Expr_T )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1302:1: ruleLog_Expr_T
            {
             before(grammarAccess.getLog_ExprAccess().getTypeLog_Expr_TParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleLog_Expr_T_in_rule__Log_Expr__TypeAssignment2600);
            ruleLog_Expr_T();

            state._fsp--;

             after(grammarAccess.getLog_ExprAccess().getTypeLog_Expr_TParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr__TypeAssignment"


    // $ANTLR start "rule__Log_Expr_Binary__FstAssignment_1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1311:1: rule__Log_Expr_Binary__FstAssignment_1 : ( ruleLog_Expr ) ;
    public final void rule__Log_Expr_Binary__FstAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1315:1: ( ( ruleLog_Expr ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1316:1: ( ruleLog_Expr )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1316:1: ( ruleLog_Expr )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1317:1: ruleLog_Expr
            {
             before(grammarAccess.getLog_Expr_BinaryAccess().getFstLog_ExprParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleLog_Expr_in_rule__Log_Expr_Binary__FstAssignment_12631);
            ruleLog_Expr();

            state._fsp--;

             after(grammarAccess.getLog_Expr_BinaryAccess().getFstLog_ExprParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__FstAssignment_1"


    // $ANTLR start "rule__Log_Expr_Binary__OperatorAssignment_2"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1326:1: rule__Log_Expr_Binary__OperatorAssignment_2 : ( ( rule__Log_Expr_Binary__OperatorAlternatives_2_0 ) ) ;
    public final void rule__Log_Expr_Binary__OperatorAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1330:1: ( ( ( rule__Log_Expr_Binary__OperatorAlternatives_2_0 ) ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1331:1: ( ( rule__Log_Expr_Binary__OperatorAlternatives_2_0 ) )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1331:1: ( ( rule__Log_Expr_Binary__OperatorAlternatives_2_0 ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1332:1: ( rule__Log_Expr_Binary__OperatorAlternatives_2_0 )
            {
             before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorAlternatives_2_0()); 
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1333:1: ( rule__Log_Expr_Binary__OperatorAlternatives_2_0 )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1333:2: rule__Log_Expr_Binary__OperatorAlternatives_2_0
            {
            pushFollow(FOLLOW_rule__Log_Expr_Binary__OperatorAlternatives_2_0_in_rule__Log_Expr_Binary__OperatorAssignment_22662);
            rule__Log_Expr_Binary__OperatorAlternatives_2_0();

            state._fsp--;


            }

             after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorAlternatives_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__OperatorAssignment_2"


    // $ANTLR start "rule__Log_Expr_Binary__SndAssignment_3"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1342:1: rule__Log_Expr_Binary__SndAssignment_3 : ( ruleLog_Expr ) ;
    public final void rule__Log_Expr_Binary__SndAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1346:1: ( ( ruleLog_Expr ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1347:1: ( ruleLog_Expr )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1347:1: ( ruleLog_Expr )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1348:1: ruleLog_Expr
            {
             before(grammarAccess.getLog_Expr_BinaryAccess().getSndLog_ExprParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleLog_Expr_in_rule__Log_Expr_Binary__SndAssignment_32695);
            ruleLog_Expr();

            state._fsp--;

             after(grammarAccess.getLog_Expr_BinaryAccess().getSndLog_ExprParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Expr_Binary__SndAssignment_3"


    // $ANTLR start "rule__Log_Neg__ExprAssignment_1"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1357:1: rule__Log_Neg__ExprAssignment_1 : ( ruleLog_Expr ) ;
    public final void rule__Log_Neg__ExprAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1361:1: ( ( ruleLog_Expr ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1362:1: ( ruleLog_Expr )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1362:1: ( ruleLog_Expr )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1363:1: ruleLog_Expr
            {
             before(grammarAccess.getLog_NegAccess().getExprLog_ExprParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleLog_Expr_in_rule__Log_Neg__ExprAssignment_12726);
            ruleLog_Expr();

            state._fsp--;

             after(grammarAccess.getLog_NegAccess().getExprLog_ExprParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Log_Neg__ExprAssignment_1"


    // $ANTLR start "rule__Sym__SymAssignment"
    // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1372:1: rule__Sym__SymAssignment : ( RULE_ID ) ;
    public final void rule__Sym__SymAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1376:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1377:1: ( RULE_ID )
            {
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1377:1: ( RULE_ID )
            // ../lu.uni.snt.repeat.ui/src-gen/lu/uni/snt/repeat/ui/contentassist/antlr/internal/InternalREPEAT.g:1378:1: RULE_ID
            {
             before(grammarAccess.getSymAccess().getSymIDTerminalRuleCall_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Sym__SymAssignment2757); 
             after(grammarAccess.getSymAccess().getSymIDTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Sym__SymAssignment"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleRProgram_in_entryRuleRProgram61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRProgram68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RProgram__FstAssignment_in_ruleRProgram94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStmnt_LST_Elem_in_entryRuleStmnt_LST_Elem121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStmnt_LST_Elem128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Stmnt_LST_Elem__Group__0_in_ruleStmnt_LST_Elem154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRepeat_in_entryRuleRepeat181 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRepeat188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Repeat__Group__0_in_ruleRepeat214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAsg_in_entryRuleAsg241 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAsg248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Asg__Group__0_in_ruleAsg274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRead_in_entryRuleRead301 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRead308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Read__Group__0_in_ruleRead334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComment_in_entryRuleComment361 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComment368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comment__Group__0_in_ruleComment394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_entryRuleLog_Expr421 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Expr428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr__TypeAssignment_in_ruleLog_Expr454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_T_in_entryRuleLog_Expr_T481 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Expr_T488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_T__Alternatives_in_ruleLog_Expr_T514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_Unary_in_entryRuleLog_Expr_Unary541 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Expr_Unary548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Unary__Alternatives_in_ruleLog_Expr_Unary574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_Binary_in_entryRuleLog_Expr_Binary601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Expr_Binary608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__0_in_ruleLog_Expr_Binary634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Neg_in_entryRuleLog_Neg661 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLog_Neg668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Neg__Group__0_in_ruleLog_Neg694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSym_in_entryRuleSym721 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSym728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Sym__SymAssignment_in_ruleSym754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRepeat_in_rule__Stmnt_LST_Elem__Alternatives_0792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAsg_in_rule__Stmnt_LST_Elem__Alternatives_0809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRead_in_rule__Stmnt_LST_Elem__Alternatives_0826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleComment_in_rule__Stmnt_LST_Elem__Alternatives_0843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_Unary_in_rule__Log_Expr_T__Alternatives875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_Binary_in_rule__Log_Expr_T__Alternatives892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Neg_in_rule__Log_Expr_Unary__Alternatives924 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSym_in_rule__Log_Expr_Unary__Alternatives941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Log_Expr_Binary__OperatorAlternatives_2_0974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__Log_Expr_Binary__OperatorAlternatives_2_0994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__Log_Expr_Binary__OperatorAlternatives_2_01014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Stmnt_LST_Elem__Group__0__Impl_in_rule__Stmnt_LST_Elem__Group__01047 = new BitSet(new long[]{0x0000000000064020L});
    public static final BitSet FOLLOW_rule__Stmnt_LST_Elem__Group__1_in_rule__Stmnt_LST_Elem__Group__01050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Stmnt_LST_Elem__Alternatives_0_in_rule__Stmnt_LST_Elem__Group__0__Impl1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Stmnt_LST_Elem__Group__1__Impl_in_rule__Stmnt_LST_Elem__Group__11107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Stmnt_LST_Elem__NextAssignment_1_in_rule__Stmnt_LST_Elem__Group__1__Impl1134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Repeat__Group__0__Impl_in_rule__Repeat__Group__01169 = new BitSet(new long[]{0x0000000000064020L});
    public static final BitSet FOLLOW_rule__Repeat__Group__1_in_rule__Repeat__Group__01172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__Repeat__Group__0__Impl1200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Repeat__Group__1__Impl_in_rule__Repeat__Group__11231 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__Repeat__Group__2_in_rule__Repeat__Group__11234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Repeat__StmntAssignment_1_in_rule__Repeat__Group__1__Impl1261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Repeat__Group__2__Impl_in_rule__Repeat__Group__21291 = new BitSet(new long[]{0x0000000000500020L});
    public static final BitSet FOLLOW_rule__Repeat__Group__3_in_rule__Repeat__Group__21294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Repeat__Group__2__Impl1322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Repeat__Group__3__Impl_in_rule__Repeat__Group__31353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Repeat__ExprAssignment_3_in_rule__Repeat__Group__3__Impl1380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Asg__Group__0__Impl_in_rule__Asg__Group__01418 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_rule__Asg__Group__1_in_rule__Asg__Group__01421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Asg__LeftAssignment_0_in_rule__Asg__Group__0__Impl1448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Asg__Group__1__Impl_in_rule__Asg__Group__11478 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Asg__Group__2_in_rule__Asg__Group__11481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__Asg__Group__1__Impl1509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Asg__Group__2__Impl_in_rule__Asg__Group__21540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Asg__RightAssignment_2_in_rule__Asg__Group__2__Impl1567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Read__Group__0__Impl_in_rule__Read__Group__01603 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Read__Group__1_in_rule__Read__Group__01606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__Read__Group__0__Impl1634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Read__Group__1__Impl_in_rule__Read__Group__11665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Read__ParamAssignment_1_in_rule__Read__Group__1__Impl1692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comment__Group__0__Impl_in_rule__Comment__Group__01726 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Comment__Group__1_in_rule__Comment__Group__01729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__Comment__Group__0__Impl1757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comment__Group__1__Impl_in_rule__Comment__Group__11788 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__Comment__Group__2_in_rule__Comment__Group__11791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comment__CommentAssignment_1_in_rule__Comment__Group__1__Impl1818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Comment__Group__2__Impl_in_rule__Comment__Group__21848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__Comment__Group__2__Impl1876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__0__Impl_in_rule__Log_Expr_Binary__Group__01913 = new BitSet(new long[]{0x0000000000500020L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__1_in_rule__Log_Expr_Binary__Group__01916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Log_Expr_Binary__Group__0__Impl1944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__1__Impl_in_rule__Log_Expr_Binary__Group__11975 = new BitSet(new long[]{0x0000000000003800L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__2_in_rule__Log_Expr_Binary__Group__11978 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__FstAssignment_1_in_rule__Log_Expr_Binary__Group__1__Impl2005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__2__Impl_in_rule__Log_Expr_Binary__Group__22035 = new BitSet(new long[]{0x0000000000500020L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__3_in_rule__Log_Expr_Binary__Group__22038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__OperatorAssignment_2_in_rule__Log_Expr_Binary__Group__2__Impl2065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__3__Impl_in_rule__Log_Expr_Binary__Group__32095 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__4_in_rule__Log_Expr_Binary__Group__32098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__SndAssignment_3_in_rule__Log_Expr_Binary__Group__3__Impl2125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__Group__4__Impl_in_rule__Log_Expr_Binary__Group__42155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Log_Expr_Binary__Group__4__Impl2183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Neg__Group__0__Impl_in_rule__Log_Neg__Group__02224 = new BitSet(new long[]{0x0000000000500020L});
    public static final BitSet FOLLOW_rule__Log_Neg__Group__1_in_rule__Log_Neg__Group__02227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__Log_Neg__Group__0__Impl2255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Neg__Group__1__Impl_in_rule__Log_Neg__Group__12286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Neg__ExprAssignment_1_in_rule__Log_Neg__Group__1__Impl2313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStmnt_LST_Elem_in_rule__RProgram__FstAssignment2352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStmnt_LST_Elem_in_rule__Stmnt_LST_Elem__NextAssignment_12383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStmnt_LST_Elem_in_rule__Repeat__StmntAssignment_12414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_rule__Repeat__ExprAssignment_32445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSym_in_rule__Asg__LeftAssignment_02476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSym_in_rule__Asg__RightAssignment_22507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSym_in_rule__Read__ParamAssignment_12538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Comment__CommentAssignment_12569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_T_in_rule__Log_Expr__TypeAssignment2600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_rule__Log_Expr_Binary__FstAssignment_12631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Log_Expr_Binary__OperatorAlternatives_2_0_in_rule__Log_Expr_Binary__OperatorAssignment_22662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_rule__Log_Expr_Binary__SndAssignment_32695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLog_Expr_in_rule__Log_Neg__ExprAssignment_12726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Sym__SymAssignment2757 = new BitSet(new long[]{0x0000000000000002L});

}
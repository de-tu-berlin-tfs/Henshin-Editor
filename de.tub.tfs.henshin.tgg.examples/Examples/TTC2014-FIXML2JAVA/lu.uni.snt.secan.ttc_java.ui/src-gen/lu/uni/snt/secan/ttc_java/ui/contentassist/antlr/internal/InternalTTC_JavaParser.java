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
package lu.uni.snt.secan.ttc_java.ui.contentassist.antlr.internal; 

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
import lu.uni.snt.secan.ttc_java.services.TTC_JavaGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalTTC_JavaParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "';'", "'class'", "'{'", "'}'", "'='", "'<'", "'>'", "'.'", "'new'", "'('", "')'", "','"
    };
    public static final int RULE_ID=4;
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


        public InternalTTC_JavaParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalTTC_JavaParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalTTC_JavaParser.tokenNames; }
    public String getGrammarFileName() { return "../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g"; }


     
     	private TTC_JavaGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(TTC_JavaGrammarAccess grammarAccess) {
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




    // $ANTLR start "entryRuleModel"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:61:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:62:1: ( ruleModel EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:63:1: ruleModel EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelRule()); 
            }
            pushFollow(FOLLOW_ruleModel_in_entryRuleModel67);
            ruleModel();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleModel74); if (state.failed) return ;

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
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:70:1: ruleModel : ( ( rule__Model__Group__0 ) ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:74:2: ( ( ( rule__Model__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:75:1: ( ( rule__Model__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:75:1: ( ( rule__Model__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:76:1: ( rule__Model__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:77:1: ( rule__Model__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:77:2: rule__Model__Group__0
            {
            pushFollow(FOLLOW_rule__Model__Group__0_in_ruleModel100);
            rule__Model__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getGroup()); 
            }

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
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleimport_"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:89:1: entryRuleimport_ : ruleimport_ EOF ;
    public final void entryRuleimport_() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:90:1: ( ruleimport_ EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:91:1: ruleimport_ EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getImport_Rule()); 
            }
            pushFollow(FOLLOW_ruleimport__in_entryRuleimport_127);
            ruleimport_();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getImport_Rule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleimport_134); if (state.failed) return ;

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
    // $ANTLR end "entryRuleimport_"


    // $ANTLR start "ruleimport_"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:98:1: ruleimport_ : ( ( rule__Import___Group__0 ) ) ;
    public final void ruleimport_() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:102:2: ( ( ( rule__Import___Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:103:1: ( ( rule__Import___Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:103:1: ( ( rule__Import___Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:104:1: ( rule__Import___Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getImport_Access().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:105:1: ( rule__Import___Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:105:2: rule__Import___Group__0
            {
            pushFollow(FOLLOW_rule__Import___Group__0_in_ruleimport_160);
            rule__Import___Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getImport_Access().getGroup()); 
            }

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
    // $ANTLR end "ruleimport_"


    // $ANTLR start "entryRuleclass_def"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:117:1: entryRuleclass_def : ruleclass_def EOF ;
    public final void entryRuleclass_def() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:118:1: ( ruleclass_def EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:119:1: ruleclass_def EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defRule()); 
            }
            pushFollow(FOLLOW_ruleclass_def_in_entryRuleclass_def187);
            ruleclass_def();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleclass_def194); if (state.failed) return ;

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
    // $ANTLR end "entryRuleclass_def"


    // $ANTLR start "ruleclass_def"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:126:1: ruleclass_def : ( ( rule__Class_def__Group__0 ) ) ;
    public final void ruleclass_def() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:130:2: ( ( ( rule__Class_def__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:131:1: ( ( rule__Class_def__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:131:1: ( ( rule__Class_def__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:132:1: ( rule__Class_def__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:133:1: ( rule__Class_def__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:133:2: rule__Class_def__Group__0
            {
            pushFollow(FOLLOW_rule__Class_def__Group__0_in_ruleclass_def220);
            rule__Class_def__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getGroup()); 
            }

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
    // $ANTLR end "ruleclass_def"


    // $ANTLR start "entryRulefeature"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:145:1: entryRulefeature : rulefeature EOF ;
    public final void entryRulefeature() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:146:1: ( rulefeature EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:147:1: rulefeature EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureRule()); 
            }
            pushFollow(FOLLOW_rulefeature_in_entryRulefeature247);
            rulefeature();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulefeature254); if (state.failed) return ;

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
    // $ANTLR end "entryRulefeature"


    // $ANTLR start "rulefeature"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:154:1: rulefeature : ( ( rule__Feature__Alternatives ) ) ;
    public final void rulefeature() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:158:2: ( ( ( rule__Feature__Alternatives ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:159:1: ( ( rule__Feature__Alternatives ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:159:1: ( ( rule__Feature__Alternatives ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:160:1: ( rule__Feature__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFeatureAccess().getAlternatives()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:161:1: ( rule__Feature__Alternatives )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:161:2: rule__Feature__Alternatives
            {
            pushFollow(FOLLOW_rule__Feature__Alternatives_in_rulefeature280);
            rule__Feature__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getFeatureAccess().getAlternatives()); 
            }

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
    // $ANTLR end "rulefeature"


    // $ANTLR start "entryRulestmt"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:173:1: entryRulestmt : rulestmt EOF ;
    public final void entryRulestmt() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:174:1: ( rulestmt EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:175:1: rulestmt EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStmtRule()); 
            }
            pushFollow(FOLLOW_rulestmt_in_entryRulestmt307);
            rulestmt();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStmtRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulestmt314); if (state.failed) return ;

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
    // $ANTLR end "entryRulestmt"


    // $ANTLR start "rulestmt"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:182:1: rulestmt : ( ( rule__Stmt__Group__0 ) ) ;
    public final void rulestmt() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:186:2: ( ( ( rule__Stmt__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:187:1: ( ( rule__Stmt__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:187:1: ( ( rule__Stmt__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:188:1: ( rule__Stmt__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStmtAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:189:1: ( rule__Stmt__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:189:2: rule__Stmt__Group__0
            {
            pushFollow(FOLLOW_rule__Stmt__Group__0_in_rulestmt340);
            rule__Stmt__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStmtAccess().getGroup()); 
            }

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
    // $ANTLR end "rulestmt"


    // $ANTLR start "entryRuledeclaration"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:201:1: entryRuledeclaration : ruledeclaration EOF ;
    public final void entryRuledeclaration() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:202:1: ( ruledeclaration EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:203:1: ruledeclaration EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationRule()); 
            }
            pushFollow(FOLLOW_ruledeclaration_in_entryRuledeclaration367);
            ruledeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuledeclaration374); if (state.failed) return ;

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
    // $ANTLR end "entryRuledeclaration"


    // $ANTLR start "ruledeclaration"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:210:1: ruledeclaration : ( ( rule__Declaration__Group__0 ) ) ;
    public final void ruledeclaration() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:214:2: ( ( ( rule__Declaration__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:215:1: ( ( rule__Declaration__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:215:1: ( ( rule__Declaration__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:216:1: ( rule__Declaration__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:217:1: ( rule__Declaration__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:217:2: rule__Declaration__Group__0
            {
            pushFollow(FOLLOW_rule__Declaration__Group__0_in_ruledeclaration400);
            rule__Declaration__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getGroup()); 
            }

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
    // $ANTLR end "ruledeclaration"


    // $ANTLR start "entryRuletypeParameter"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:229:1: entryRuletypeParameter : ruletypeParameter EOF ;
    public final void entryRuletypeParameter() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:230:1: ( ruletypeParameter EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:231:1: ruletypeParameter EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTypeParameterRule()); 
            }
            pushFollow(FOLLOW_ruletypeParameter_in_entryRuletypeParameter427);
            ruletypeParameter();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTypeParameterRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuletypeParameter434); if (state.failed) return ;

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
    // $ANTLR end "entryRuletypeParameter"


    // $ANTLR start "ruletypeParameter"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:238:1: ruletypeParameter : ( ( rule__TypeParameter__Group__0 ) ) ;
    public final void ruletypeParameter() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:242:2: ( ( ( rule__TypeParameter__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:243:1: ( ( rule__TypeParameter__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:243:1: ( ( rule__TypeParameter__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:244:1: ( rule__TypeParameter__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTypeParameterAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:245:1: ( rule__TypeParameter__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:245:2: rule__TypeParameter__Group__0
            {
            pushFollow(FOLLOW_rule__TypeParameter__Group__0_in_ruletypeParameter460);
            rule__TypeParameter__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTypeParameterAccess().getGroup()); 
            }

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
    // $ANTLR end "ruletypeParameter"


    // $ANTLR start "entryRuleassignment"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:257:1: entryRuleassignment : ruleassignment EOF ;
    public final void entryRuleassignment() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:258:1: ( ruleassignment EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:259:1: ruleassignment EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentRule()); 
            }
            pushFollow(FOLLOW_ruleassignment_in_entryRuleassignment487);
            ruleassignment();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleassignment494); if (state.failed) return ;

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
    // $ANTLR end "entryRuleassignment"


    // $ANTLR start "ruleassignment"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:266:1: ruleassignment : ( ( rule__Assignment__Group__0 ) ) ;
    public final void ruleassignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:270:2: ( ( ( rule__Assignment__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:271:1: ( ( rule__Assignment__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:271:1: ( ( rule__Assignment__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:272:1: ( rule__Assignment__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:273:1: ( rule__Assignment__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:273:2: rule__Assignment__Group__0
            {
            pushFollow(FOLLOW_rule__Assignment__Group__0_in_ruleassignment520);
            rule__Assignment__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getGroup()); 
            }

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
    // $ANTLR end "ruleassignment"


    // $ANTLR start "entryRulefully_qualified_name"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:285:1: entryRulefully_qualified_name : rulefully_qualified_name EOF ;
    public final void entryRulefully_qualified_name() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:286:1: ( rulefully_qualified_name EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:287:1: rulefully_qualified_name EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFully_qualified_nameRule()); 
            }
            pushFollow(FOLLOW_rulefully_qualified_name_in_entryRulefully_qualified_name547);
            rulefully_qualified_name();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFully_qualified_nameRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulefully_qualified_name554); if (state.failed) return ;

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
    // $ANTLR end "entryRulefully_qualified_name"


    // $ANTLR start "rulefully_qualified_name"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:294:1: rulefully_qualified_name : ( ( rule__Fully_qualified_name__Group__0 ) ) ;
    public final void rulefully_qualified_name() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:298:2: ( ( ( rule__Fully_qualified_name__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:299:1: ( ( rule__Fully_qualified_name__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:299:1: ( ( rule__Fully_qualified_name__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:300:1: ( rule__Fully_qualified_name__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFully_qualified_nameAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:301:1: ( rule__Fully_qualified_name__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:301:2: rule__Fully_qualified_name__Group__0
            {
            pushFollow(FOLLOW_rule__Fully_qualified_name__Group__0_in_rulefully_qualified_name580);
            rule__Fully_qualified_name__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getFully_qualified_nameAccess().getGroup()); 
            }

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
    // $ANTLR end "rulefully_qualified_name"


    // $ANTLR start "entryRuleexp"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:313:1: entryRuleexp : ruleexp EOF ;
    public final void entryRuleexp() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:314:1: ( ruleexp EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:315:1: ruleexp EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExpRule()); 
            }
            pushFollow(FOLLOW_ruleexp_in_entryRuleexp607);
            ruleexp();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getExpRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleexp614); if (state.failed) return ;

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
    // $ANTLR end "entryRuleexp"


    // $ANTLR start "ruleexp"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:322:1: ruleexp : ( ( rule__Exp__Alternatives ) ) ;
    public final void ruleexp() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:326:2: ( ( ( rule__Exp__Alternatives ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:327:1: ( ( rule__Exp__Alternatives ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:327:1: ( ( rule__Exp__Alternatives ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:328:1: ( rule__Exp__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getExpAccess().getAlternatives()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:329:1: ( rule__Exp__Alternatives )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:329:2: rule__Exp__Alternatives
            {
            pushFollow(FOLLOW_rule__Exp__Alternatives_in_ruleexp640);
            rule__Exp__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getExpAccess().getAlternatives()); 
            }

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
    // $ANTLR end "ruleexp"


    // $ANTLR start "entryRuleconstructor_call"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:341:1: entryRuleconstructor_call : ruleconstructor_call EOF ;
    public final void entryRuleconstructor_call() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:342:1: ( ruleconstructor_call EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:343:1: ruleconstructor_call EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getConstructor_callRule()); 
            }
            pushFollow(FOLLOW_ruleconstructor_call_in_entryRuleconstructor_call667);
            ruleconstructor_call();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getConstructor_callRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleconstructor_call674); if (state.failed) return ;

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
    // $ANTLR end "entryRuleconstructor_call"


    // $ANTLR start "ruleconstructor_call"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:350:1: ruleconstructor_call : ( ( rule__Constructor_call__Group__0 ) ) ;
    public final void ruleconstructor_call() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:354:2: ( ( ( rule__Constructor_call__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:355:1: ( ( rule__Constructor_call__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:355:1: ( ( rule__Constructor_call__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:356:1: ( rule__Constructor_call__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getConstructor_callAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:357:1: ( rule__Constructor_call__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:357:2: rule__Constructor_call__Group__0
            {
            pushFollow(FOLLOW_rule__Constructor_call__Group__0_in_ruleconstructor_call700);
            rule__Constructor_call__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getConstructor_callAccess().getGroup()); 
            }

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
    // $ANTLR end "ruleconstructor_call"


    // $ANTLR start "entryRulemethodCall"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:369:1: entryRulemethodCall : rulemethodCall EOF ;
    public final void entryRulemethodCall() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:370:1: ( rulemethodCall EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:371:1: rulemethodCall EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethodCallRule()); 
            }
            pushFollow(FOLLOW_rulemethodCall_in_entryRulemethodCall727);
            rulemethodCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethodCallRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulemethodCall734); if (state.failed) return ;

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
    // $ANTLR end "entryRulemethodCall"


    // $ANTLR start "rulemethodCall"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:378:1: rulemethodCall : ( ( rule__MethodCall__Group__0 ) ) ;
    public final void rulemethodCall() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:382:2: ( ( ( rule__MethodCall__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:383:1: ( ( rule__MethodCall__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:383:1: ( ( rule__MethodCall__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:384:1: ( rule__MethodCall__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethodCallAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:385:1: ( rule__MethodCall__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:385:2: rule__MethodCall__Group__0
            {
            pushFollow(FOLLOW_rule__MethodCall__Group__0_in_rulemethodCall760);
            rule__MethodCall__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethodCallAccess().getGroup()); 
            }

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
    // $ANTLR end "rulemethodCall"


    // $ANTLR start "entryRulemethod_def"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:397:1: entryRulemethod_def : rulemethod_def EOF ;
    public final void entryRulemethod_def() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:398:1: ( rulemethod_def EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:399:1: rulemethod_def EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defRule()); 
            }
            pushFollow(FOLLOW_rulemethod_def_in_entryRulemethod_def787);
            rulemethod_def();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulemethod_def794); if (state.failed) return ;

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
    // $ANTLR end "entryRulemethod_def"


    // $ANTLR start "rulemethod_def"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:406:1: rulemethod_def : ( ( rule__Method_def__Group__0 ) ) ;
    public final void rulemethod_def() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:410:2: ( ( ( rule__Method_def__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:411:1: ( ( rule__Method_def__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:411:1: ( ( rule__Method_def__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:412:1: ( rule__Method_def__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:413:1: ( rule__Method_def__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:413:2: rule__Method_def__Group__0
            {
            pushFollow(FOLLOW_rule__Method_def__Group__0_in_rulemethod_def820);
            rule__Method_def__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getGroup()); 
            }

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
    // $ANTLR end "rulemethod_def"


    // $ANTLR start "entryRulebody"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:425:1: entryRulebody : rulebody EOF ;
    public final void entryRulebody() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:426:1: ( rulebody EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:427:1: rulebody EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyRule()); 
            }
            pushFollow(FOLLOW_rulebody_in_entryRulebody847);
            rulebody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulebody854); if (state.failed) return ;

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
    // $ANTLR end "entryRulebody"


    // $ANTLR start "rulebody"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:434:1: rulebody : ( ( rule__Body__Group__0 ) ) ;
    public final void rulebody() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:438:2: ( ( ( rule__Body__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:439:1: ( ( rule__Body__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:439:1: ( ( rule__Body__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:440:1: ( rule__Body__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:441:1: ( rule__Body__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:441:2: rule__Body__Group__0
            {
            pushFollow(FOLLOW_rule__Body__Group__0_in_rulebody880);
            rule__Body__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroup()); 
            }

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
    // $ANTLR end "rulebody"


    // $ANTLR start "entryRuleargument"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:453:1: entryRuleargument : ruleargument EOF ;
    public final void entryRuleargument() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:454:1: ( ruleargument EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:455:1: ruleargument EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentRule()); 
            }
            pushFollow(FOLLOW_ruleargument_in_entryRuleargument907);
            ruleargument();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleargument914); if (state.failed) return ;

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
    // $ANTLR end "entryRuleargument"


    // $ANTLR start "ruleargument"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:462:1: ruleargument : ( ( rule__Argument__Group__0 ) ) ;
    public final void ruleargument() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:466:2: ( ( ( rule__Argument__Group__0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:467:1: ( ( rule__Argument__Group__0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:467:1: ( ( rule__Argument__Group__0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:468:1: ( rule__Argument__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentAccess().getGroup()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:469:1: ( rule__Argument__Group__0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:469:2: rule__Argument__Group__0
            {
            pushFollow(FOLLOW_rule__Argument__Group__0_in_ruleargument940);
            rule__Argument__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentAccess().getGroup()); 
            }

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
    // $ANTLR end "ruleargument"


    // $ANTLR start "entryRuleatom"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:481:1: entryRuleatom : ruleatom EOF ;
    public final void entryRuleatom() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:482:1: ( ruleatom EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:483:1: ruleatom EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAtomRule()); 
            }
            pushFollow(FOLLOW_ruleatom_in_entryRuleatom967);
            ruleatom();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAtomRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleatom974); if (state.failed) return ;

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
    // $ANTLR end "entryRuleatom"


    // $ANTLR start "ruleatom"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:490:1: ruleatom : ( ( rule__Atom__Alternatives ) ) ;
    public final void ruleatom() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:494:2: ( ( ( rule__Atom__Alternatives ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:495:1: ( ( rule__Atom__Alternatives ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:495:1: ( ( rule__Atom__Alternatives ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:496:1: ( rule__Atom__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAtomAccess().getAlternatives()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:497:1: ( rule__Atom__Alternatives )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:497:2: rule__Atom__Alternatives
            {
            pushFollow(FOLLOW_rule__Atom__Alternatives_in_ruleatom1000);
            rule__Atom__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAtomAccess().getAlternatives()); 
            }

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
    // $ANTLR end "ruleatom"


    // $ANTLR start "entryRulevariable_name"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:509:1: entryRulevariable_name : rulevariable_name EOF ;
    public final void entryRulevariable_name() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:510:1: ( rulevariable_name EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:511:1: rulevariable_name EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariable_nameRule()); 
            }
            pushFollow(FOLLOW_rulevariable_name_in_entryRulevariable_name1027);
            rulevariable_name();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariable_nameRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulevariable_name1034); if (state.failed) return ;

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
    // $ANTLR end "entryRulevariable_name"


    // $ANTLR start "rulevariable_name"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:518:1: rulevariable_name : ( ( rule__Variable_name__NameAssignment ) ) ;
    public final void rulevariable_name() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:522:2: ( ( ( rule__Variable_name__NameAssignment ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:523:1: ( ( rule__Variable_name__NameAssignment ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:523:1: ( ( rule__Variable_name__NameAssignment ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:524:1: ( rule__Variable_name__NameAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariable_nameAccess().getNameAssignment()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:525:1: ( rule__Variable_name__NameAssignment )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:525:2: rule__Variable_name__NameAssignment
            {
            pushFollow(FOLLOW_rule__Variable_name__NameAssignment_in_rulevariable_name1060);
            rule__Variable_name__NameAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariable_nameAccess().getNameAssignment()); 
            }

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
    // $ANTLR end "rulevariable_name"


    // $ANTLR start "entryRulestring_val"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:537:1: entryRulestring_val : rulestring_val EOF ;
    public final void entryRulestring_val() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:538:1: ( rulestring_val EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:539:1: rulestring_val EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getString_valRule()); 
            }
            pushFollow(FOLLOW_rulestring_val_in_entryRulestring_val1087);
            rulestring_val();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getString_valRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRulestring_val1094); if (state.failed) return ;

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
    // $ANTLR end "entryRulestring_val"


    // $ANTLR start "rulestring_val"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:546:1: rulestring_val : ( ( rule__String_val__ValueAssignment ) ) ;
    public final void rulestring_val() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:550:2: ( ( ( rule__String_val__ValueAssignment ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:551:1: ( ( rule__String_val__ValueAssignment ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:551:1: ( ( rule__String_val__ValueAssignment ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:552:1: ( rule__String_val__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getString_valAccess().getValueAssignment()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:553:1: ( rule__String_val__ValueAssignment )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:553:2: rule__String_val__ValueAssignment
            {
            pushFollow(FOLLOW_rule__String_val__ValueAssignment_in_rulestring_val1120);
            rule__String_val__ValueAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getString_valAccess().getValueAssignment()); 
            }

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
    // $ANTLR end "rulestring_val"


    // $ANTLR start "entryRuleint_val"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:565:1: entryRuleint_val : ruleint_val EOF ;
    public final void entryRuleint_val() throws RecognitionException {
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:566:1: ( ruleint_val EOF )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:567:1: ruleint_val EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInt_valRule()); 
            }
            pushFollow(FOLLOW_ruleint_val_in_entryRuleint_val1147);
            ruleint_val();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getInt_valRule()); 
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleint_val1154); if (state.failed) return ;

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
    // $ANTLR end "entryRuleint_val"


    // $ANTLR start "ruleint_val"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:574:1: ruleint_val : ( ( rule__Int_val__ValueAssignment ) ) ;
    public final void ruleint_val() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:578:2: ( ( ( rule__Int_val__ValueAssignment ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:579:1: ( ( rule__Int_val__ValueAssignment ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:579:1: ( ( rule__Int_val__ValueAssignment ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:580:1: ( rule__Int_val__ValueAssignment )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInt_valAccess().getValueAssignment()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:581:1: ( rule__Int_val__ValueAssignment )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:581:2: rule__Int_val__ValueAssignment
            {
            pushFollow(FOLLOW_rule__Int_val__ValueAssignment_in_ruleint_val1180);
            rule__Int_val__ValueAssignment();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getInt_valAccess().getValueAssignment()); 
            }

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
    // $ANTLR end "ruleint_val"


    // $ANTLR start "rule__Feature__Alternatives"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:593:1: rule__Feature__Alternatives : ( ( rulestmt ) | ( rulemethod_def ) );
    public final void rule__Feature__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:597:1: ( ( rulestmt ) | ( rulemethod_def ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_ID) ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1==RULE_ID||(LA1_1>=16 && LA1_1<=17)||LA1_1==19) ) {
                    alt1=1;
                }
                else if ( (LA1_1==21) ) {
                    alt1=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:598:1: ( rulestmt )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:598:1: ( rulestmt )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:599:1: rulestmt
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getFeatureAccess().getStmtParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_rulestmt_in_rule__Feature__Alternatives1216);
                    rulestmt();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getFeatureAccess().getStmtParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:604:6: ( rulemethod_def )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:604:6: ( rulemethod_def )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:605:1: rulemethod_def
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getFeatureAccess().getMethod_defParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_rulemethod_def_in_rule__Feature__Alternatives1233);
                    rulemethod_def();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getFeatureAccess().getMethod_defParserRuleCall_1()); 
                    }

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
    // $ANTLR end "rule__Feature__Alternatives"


    // $ANTLR start "rule__Stmt__Alternatives_0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:615:1: rule__Stmt__Alternatives_0 : ( ( ruledeclaration ) | ( ruleassignment ) );
    public final void rule__Stmt__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:619:1: ( ( ruledeclaration ) | ( ruleassignment ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==RULE_ID) ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==16||LA2_1==19) ) {
                    alt2=2;
                }
                else if ( (LA2_1==RULE_ID||LA2_1==17) ) {
                    alt2=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:620:1: ( ruledeclaration )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:620:1: ( ruledeclaration )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:621:1: ruledeclaration
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getStmtAccess().getDeclarationParserRuleCall_0_0()); 
                    }
                    pushFollow(FOLLOW_ruledeclaration_in_rule__Stmt__Alternatives_01265);
                    ruledeclaration();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getStmtAccess().getDeclarationParserRuleCall_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:626:6: ( ruleassignment )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:626:6: ( ruleassignment )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:627:1: ruleassignment
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getStmtAccess().getAssignmentParserRuleCall_0_1()); 
                    }
                    pushFollow(FOLLOW_ruleassignment_in_rule__Stmt__Alternatives_01282);
                    ruleassignment();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getStmtAccess().getAssignmentParserRuleCall_0_1()); 
                    }

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
    // $ANTLR end "rule__Stmt__Alternatives_0"


    // $ANTLR start "rule__Exp__Alternatives"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:637:1: rule__Exp__Alternatives : ( ( ruleatom ) | ( ruleconstructor_call ) | ( rulemethodCall ) );
    public final void rule__Exp__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:641:1: ( ( ruleatom ) | ( ruleconstructor_call ) | ( rulemethodCall ) )
            int alt3=3;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_INT:
                {
                alt3=1;
                }
                break;
            case RULE_ID:
                {
                int LA3_2 = input.LA(2);

                if ( (LA3_2==EOF||LA3_2==12) ) {
                    alt3=1;
                }
                else if ( (LA3_2==17||LA3_2==21) ) {
                    alt3=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 2, input);

                    throw nvae;
                }
                }
                break;
            case 20:
                {
                alt3=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:642:1: ( ruleatom )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:642:1: ( ruleatom )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:643:1: ruleatom
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getExpAccess().getAtomParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_ruleatom_in_rule__Exp__Alternatives1314);
                    ruleatom();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getExpAccess().getAtomParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:648:6: ( ruleconstructor_call )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:648:6: ( ruleconstructor_call )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:649:1: ruleconstructor_call
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getExpAccess().getConstructor_callParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_ruleconstructor_call_in_rule__Exp__Alternatives1331);
                    ruleconstructor_call();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getExpAccess().getConstructor_callParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:654:6: ( rulemethodCall )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:654:6: ( rulemethodCall )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:655:1: rulemethodCall
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getExpAccess().getMethodCallParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_rulemethodCall_in_rule__Exp__Alternatives1348);
                    rulemethodCall();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getExpAccess().getMethodCallParserRuleCall_2()); 
                    }

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
    // $ANTLR end "rule__Exp__Alternatives"


    // $ANTLR start "rule__Atom__Alternatives"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:665:1: rule__Atom__Alternatives : ( ( rulestring_val ) | ( ruleint_val ) | ( rulevariable_name ) );
    public final void rule__Atom__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:669:1: ( ( rulestring_val ) | ( ruleint_val ) | ( rulevariable_name ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case RULE_STRING:
                {
                alt4=1;
                }
                break;
            case RULE_INT:
                {
                alt4=2;
                }
                break;
            case RULE_ID:
                {
                alt4=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:670:1: ( rulestring_val )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:670:1: ( rulestring_val )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:671:1: rulestring_val
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomAccess().getString_valParserRuleCall_0()); 
                    }
                    pushFollow(FOLLOW_rulestring_val_in_rule__Atom__Alternatives1380);
                    rulestring_val();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomAccess().getString_valParserRuleCall_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:676:6: ( ruleint_val )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:676:6: ( ruleint_val )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:677:1: ruleint_val
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomAccess().getInt_valParserRuleCall_1()); 
                    }
                    pushFollow(FOLLOW_ruleint_val_in_rule__Atom__Alternatives1397);
                    ruleint_val();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomAccess().getInt_valParserRuleCall_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:682:6: ( rulevariable_name )
                    {
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:682:6: ( rulevariable_name )
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:683:1: rulevariable_name
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getAtomAccess().getVariable_nameParserRuleCall_2()); 
                    }
                    pushFollow(FOLLOW_rulevariable_name_in_rule__Atom__Alternatives1414);
                    rulevariable_name();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getAtomAccess().getVariable_nameParserRuleCall_2()); 
                    }

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
    // $ANTLR end "rule__Atom__Alternatives"


    // $ANTLR start "rule__Model__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:695:1: rule__Model__Group__0 : rule__Model__Group__0__Impl rule__Model__Group__1 ;
    public final void rule__Model__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:699:1: ( rule__Model__Group__0__Impl rule__Model__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:700:2: rule__Model__Group__0__Impl rule__Model__Group__1
            {
            pushFollow(FOLLOW_rule__Model__Group__0__Impl_in_rule__Model__Group__01444);
            rule__Model__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Model__Group__1_in_rule__Model__Group__01447);
            rule__Model__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Model__Group__0"


    // $ANTLR start "rule__Model__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:707:1: rule__Model__Group__0__Impl : ( ( rule__Model__ImportsAssignment_0 )* ) ;
    public final void rule__Model__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:711:1: ( ( ( rule__Model__ImportsAssignment_0 )* ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:712:1: ( ( rule__Model__ImportsAssignment_0 )* )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:712:1: ( ( rule__Model__ImportsAssignment_0 )* )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:713:1: ( rule__Model__ImportsAssignment_0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getImportsAssignment_0()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:714:1: ( rule__Model__ImportsAssignment_0 )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==11) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:714:2: rule__Model__ImportsAssignment_0
            	    {
            	    pushFollow(FOLLOW_rule__Model__ImportsAssignment_0_in_rule__Model__Group__0__Impl1474);
            	    rule__Model__ImportsAssignment_0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getImportsAssignment_0()); 
            }

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
    // $ANTLR end "rule__Model__Group__0__Impl"


    // $ANTLR start "rule__Model__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:724:1: rule__Model__Group__1 : rule__Model__Group__1__Impl ;
    public final void rule__Model__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:728:1: ( rule__Model__Group__1__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:729:2: rule__Model__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Model__Group__1__Impl_in_rule__Model__Group__11505);
            rule__Model__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Model__Group__1"


    // $ANTLR start "rule__Model__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:735:1: rule__Model__Group__1__Impl : ( ( rule__Model__ClassesAssignment_1 )* ) ;
    public final void rule__Model__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:739:1: ( ( ( rule__Model__ClassesAssignment_1 )* ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:740:1: ( ( rule__Model__ClassesAssignment_1 )* )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:740:1: ( ( rule__Model__ClassesAssignment_1 )* )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:741:1: ( rule__Model__ClassesAssignment_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getClassesAssignment_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:742:1: ( rule__Model__ClassesAssignment_1 )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==13) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:742:2: rule__Model__ClassesAssignment_1
            	    {
            	    pushFollow(FOLLOW_rule__Model__ClassesAssignment_1_in_rule__Model__Group__1__Impl1532);
            	    rule__Model__ClassesAssignment_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getClassesAssignment_1()); 
            }

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
    // $ANTLR end "rule__Model__Group__1__Impl"


    // $ANTLR start "rule__Import___Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:756:1: rule__Import___Group__0 : rule__Import___Group__0__Impl rule__Import___Group__1 ;
    public final void rule__Import___Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:760:1: ( rule__Import___Group__0__Impl rule__Import___Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:761:2: rule__Import___Group__0__Impl rule__Import___Group__1
            {
            pushFollow(FOLLOW_rule__Import___Group__0__Impl_in_rule__Import___Group__01567);
            rule__Import___Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Import___Group__1_in_rule__Import___Group__01570);
            rule__Import___Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Import___Group__0"


    // $ANTLR start "rule__Import___Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:768:1: rule__Import___Group__0__Impl : ( 'import' ) ;
    public final void rule__Import___Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:772:1: ( ( 'import' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:773:1: ( 'import' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:773:1: ( 'import' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:774:1: 'import'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getImport_Access().getImportKeyword_0()); 
            }
            match(input,11,FOLLOW_11_in_rule__Import___Group__0__Impl1598); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getImport_Access().getImportKeyword_0()); 
            }

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
    // $ANTLR end "rule__Import___Group__0__Impl"


    // $ANTLR start "rule__Import___Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:787:1: rule__Import___Group__1 : rule__Import___Group__1__Impl rule__Import___Group__2 ;
    public final void rule__Import___Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:791:1: ( rule__Import___Group__1__Impl rule__Import___Group__2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:792:2: rule__Import___Group__1__Impl rule__Import___Group__2
            {
            pushFollow(FOLLOW_rule__Import___Group__1__Impl_in_rule__Import___Group__11629);
            rule__Import___Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Import___Group__2_in_rule__Import___Group__11632);
            rule__Import___Group__2();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Import___Group__1"


    // $ANTLR start "rule__Import___Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:799:1: rule__Import___Group__1__Impl : ( ( rule__Import___EntryAssignment_1 ) ) ;
    public final void rule__Import___Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:803:1: ( ( ( rule__Import___EntryAssignment_1 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:804:1: ( ( rule__Import___EntryAssignment_1 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:804:1: ( ( rule__Import___EntryAssignment_1 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:805:1: ( rule__Import___EntryAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getImport_Access().getEntryAssignment_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:806:1: ( rule__Import___EntryAssignment_1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:806:2: rule__Import___EntryAssignment_1
            {
            pushFollow(FOLLOW_rule__Import___EntryAssignment_1_in_rule__Import___Group__1__Impl1659);
            rule__Import___EntryAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getImport_Access().getEntryAssignment_1()); 
            }

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
    // $ANTLR end "rule__Import___Group__1__Impl"


    // $ANTLR start "rule__Import___Group__2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:816:1: rule__Import___Group__2 : rule__Import___Group__2__Impl ;
    public final void rule__Import___Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:820:1: ( rule__Import___Group__2__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:821:2: rule__Import___Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Import___Group__2__Impl_in_rule__Import___Group__21689);
            rule__Import___Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Import___Group__2"


    // $ANTLR start "rule__Import___Group__2__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:827:1: rule__Import___Group__2__Impl : ( ';' ) ;
    public final void rule__Import___Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:831:1: ( ( ';' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:832:1: ( ';' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:832:1: ( ';' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:833:1: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getImport_Access().getSemicolonKeyword_2()); 
            }
            match(input,12,FOLLOW_12_in_rule__Import___Group__2__Impl1717); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getImport_Access().getSemicolonKeyword_2()); 
            }

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
    // $ANTLR end "rule__Import___Group__2__Impl"


    // $ANTLR start "rule__Class_def__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:852:1: rule__Class_def__Group__0 : rule__Class_def__Group__0__Impl rule__Class_def__Group__1 ;
    public final void rule__Class_def__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:856:1: ( rule__Class_def__Group__0__Impl rule__Class_def__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:857:2: rule__Class_def__Group__0__Impl rule__Class_def__Group__1
            {
            pushFollow(FOLLOW_rule__Class_def__Group__0__Impl_in_rule__Class_def__Group__01754);
            rule__Class_def__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Class_def__Group__1_in_rule__Class_def__Group__01757);
            rule__Class_def__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Class_def__Group__0"


    // $ANTLR start "rule__Class_def__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:864:1: rule__Class_def__Group__0__Impl : ( 'class' ) ;
    public final void rule__Class_def__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:868:1: ( ( 'class' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:869:1: ( 'class' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:869:1: ( 'class' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:870:1: 'class'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getClassKeyword_0()); 
            }
            match(input,13,FOLLOW_13_in_rule__Class_def__Group__0__Impl1785); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getClassKeyword_0()); 
            }

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
    // $ANTLR end "rule__Class_def__Group__0__Impl"


    // $ANTLR start "rule__Class_def__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:883:1: rule__Class_def__Group__1 : rule__Class_def__Group__1__Impl rule__Class_def__Group__2 ;
    public final void rule__Class_def__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:887:1: ( rule__Class_def__Group__1__Impl rule__Class_def__Group__2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:888:2: rule__Class_def__Group__1__Impl rule__Class_def__Group__2
            {
            pushFollow(FOLLOW_rule__Class_def__Group__1__Impl_in_rule__Class_def__Group__11816);
            rule__Class_def__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Class_def__Group__2_in_rule__Class_def__Group__11819);
            rule__Class_def__Group__2();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Class_def__Group__1"


    // $ANTLR start "rule__Class_def__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:895:1: rule__Class_def__Group__1__Impl : ( ( rule__Class_def__NameAssignment_1 ) ) ;
    public final void rule__Class_def__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:899:1: ( ( ( rule__Class_def__NameAssignment_1 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:900:1: ( ( rule__Class_def__NameAssignment_1 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:900:1: ( ( rule__Class_def__NameAssignment_1 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:901:1: ( rule__Class_def__NameAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getNameAssignment_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:902:1: ( rule__Class_def__NameAssignment_1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:902:2: rule__Class_def__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__Class_def__NameAssignment_1_in_rule__Class_def__Group__1__Impl1846);
            rule__Class_def__NameAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getNameAssignment_1()); 
            }

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
    // $ANTLR end "rule__Class_def__Group__1__Impl"


    // $ANTLR start "rule__Class_def__Group__2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:912:1: rule__Class_def__Group__2 : rule__Class_def__Group__2__Impl rule__Class_def__Group__3 ;
    public final void rule__Class_def__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:916:1: ( rule__Class_def__Group__2__Impl rule__Class_def__Group__3 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:917:2: rule__Class_def__Group__2__Impl rule__Class_def__Group__3
            {
            pushFollow(FOLLOW_rule__Class_def__Group__2__Impl_in_rule__Class_def__Group__21876);
            rule__Class_def__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Class_def__Group__3_in_rule__Class_def__Group__21879);
            rule__Class_def__Group__3();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Class_def__Group__2"


    // $ANTLR start "rule__Class_def__Group__2__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:924:1: rule__Class_def__Group__2__Impl : ( '{' ) ;
    public final void rule__Class_def__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:928:1: ( ( '{' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:929:1: ( '{' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:929:1: ( '{' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:930:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getLeftCurlyBracketKeyword_2()); 
            }
            match(input,14,FOLLOW_14_in_rule__Class_def__Group__2__Impl1907); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getLeftCurlyBracketKeyword_2()); 
            }

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
    // $ANTLR end "rule__Class_def__Group__2__Impl"


    // $ANTLR start "rule__Class_def__Group__3"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:943:1: rule__Class_def__Group__3 : rule__Class_def__Group__3__Impl rule__Class_def__Group__4 ;
    public final void rule__Class_def__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:947:1: ( rule__Class_def__Group__3__Impl rule__Class_def__Group__4 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:948:2: rule__Class_def__Group__3__Impl rule__Class_def__Group__4
            {
            pushFollow(FOLLOW_rule__Class_def__Group__3__Impl_in_rule__Class_def__Group__31938);
            rule__Class_def__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Class_def__Group__4_in_rule__Class_def__Group__31941);
            rule__Class_def__Group__4();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Class_def__Group__3"


    // $ANTLR start "rule__Class_def__Group__3__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:955:1: rule__Class_def__Group__3__Impl : ( ( rule__Class_def__InitialDeclarationsAssignment_3 )* ) ;
    public final void rule__Class_def__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:959:1: ( ( ( rule__Class_def__InitialDeclarationsAssignment_3 )* ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:960:1: ( ( rule__Class_def__InitialDeclarationsAssignment_3 )* )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:960:1: ( ( rule__Class_def__InitialDeclarationsAssignment_3 )* )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:961:1: ( rule__Class_def__InitialDeclarationsAssignment_3 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getInitialDeclarationsAssignment_3()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:962:1: ( rule__Class_def__InitialDeclarationsAssignment_3 )*
            loop7:
            do {
                int alt7=2;
                alt7 = dfa7.predict(input);
                switch (alt7) {
            	case 1 :
            	    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:962:2: rule__Class_def__InitialDeclarationsAssignment_3
            	    {
            	    pushFollow(FOLLOW_rule__Class_def__InitialDeclarationsAssignment_3_in_rule__Class_def__Group__3__Impl1968);
            	    rule__Class_def__InitialDeclarationsAssignment_3();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getInitialDeclarationsAssignment_3()); 
            }

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
    // $ANTLR end "rule__Class_def__Group__3__Impl"


    // $ANTLR start "rule__Class_def__Group__4"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:972:1: rule__Class_def__Group__4 : rule__Class_def__Group__4__Impl rule__Class_def__Group__5 ;
    public final void rule__Class_def__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:976:1: ( rule__Class_def__Group__4__Impl rule__Class_def__Group__5 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:977:2: rule__Class_def__Group__4__Impl rule__Class_def__Group__5
            {
            pushFollow(FOLLOW_rule__Class_def__Group__4__Impl_in_rule__Class_def__Group__41999);
            rule__Class_def__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Class_def__Group__5_in_rule__Class_def__Group__42002);
            rule__Class_def__Group__5();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Class_def__Group__4"


    // $ANTLR start "rule__Class_def__Group__4__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:984:1: rule__Class_def__Group__4__Impl : ( ( rule__Class_def__FeatureAssignment_4 )* ) ;
    public final void rule__Class_def__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:988:1: ( ( ( rule__Class_def__FeatureAssignment_4 )* ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:989:1: ( ( rule__Class_def__FeatureAssignment_4 )* )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:989:1: ( ( rule__Class_def__FeatureAssignment_4 )* )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:990:1: ( rule__Class_def__FeatureAssignment_4 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getFeatureAssignment_4()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:991:1: ( rule__Class_def__FeatureAssignment_4 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_ID) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:991:2: rule__Class_def__FeatureAssignment_4
            	    {
            	    pushFollow(FOLLOW_rule__Class_def__FeatureAssignment_4_in_rule__Class_def__Group__4__Impl2029);
            	    rule__Class_def__FeatureAssignment_4();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getFeatureAssignment_4()); 
            }

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
    // $ANTLR end "rule__Class_def__Group__4__Impl"


    // $ANTLR start "rule__Class_def__Group__5"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1001:1: rule__Class_def__Group__5 : rule__Class_def__Group__5__Impl ;
    public final void rule__Class_def__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1005:1: ( rule__Class_def__Group__5__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1006:2: rule__Class_def__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__Class_def__Group__5__Impl_in_rule__Class_def__Group__52060);
            rule__Class_def__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Class_def__Group__5"


    // $ANTLR start "rule__Class_def__Group__5__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1012:1: rule__Class_def__Group__5__Impl : ( '}' ) ;
    public final void rule__Class_def__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1016:1: ( ( '}' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1017:1: ( '}' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1017:1: ( '}' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1018:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getRightCurlyBracketKeyword_5()); 
            }
            match(input,15,FOLLOW_15_in_rule__Class_def__Group__5__Impl2088); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getRightCurlyBracketKeyword_5()); 
            }

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
    // $ANTLR end "rule__Class_def__Group__5__Impl"


    // $ANTLR start "rule__Stmt__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1043:1: rule__Stmt__Group__0 : rule__Stmt__Group__0__Impl rule__Stmt__Group__1 ;
    public final void rule__Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1047:1: ( rule__Stmt__Group__0__Impl rule__Stmt__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1048:2: rule__Stmt__Group__0__Impl rule__Stmt__Group__1
            {
            pushFollow(FOLLOW_rule__Stmt__Group__0__Impl_in_rule__Stmt__Group__02131);
            rule__Stmt__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Stmt__Group__1_in_rule__Stmt__Group__02134);
            rule__Stmt__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Stmt__Group__0"


    // $ANTLR start "rule__Stmt__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1055:1: rule__Stmt__Group__0__Impl : ( ( rule__Stmt__Alternatives_0 ) ) ;
    public final void rule__Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1059:1: ( ( ( rule__Stmt__Alternatives_0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1060:1: ( ( rule__Stmt__Alternatives_0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1060:1: ( ( rule__Stmt__Alternatives_0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1061:1: ( rule__Stmt__Alternatives_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStmtAccess().getAlternatives_0()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1062:1: ( rule__Stmt__Alternatives_0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1062:2: rule__Stmt__Alternatives_0
            {
            pushFollow(FOLLOW_rule__Stmt__Alternatives_0_in_rule__Stmt__Group__0__Impl2161);
            rule__Stmt__Alternatives_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStmtAccess().getAlternatives_0()); 
            }

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
    // $ANTLR end "rule__Stmt__Group__0__Impl"


    // $ANTLR start "rule__Stmt__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1072:1: rule__Stmt__Group__1 : rule__Stmt__Group__1__Impl ;
    public final void rule__Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1076:1: ( rule__Stmt__Group__1__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1077:2: rule__Stmt__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Stmt__Group__1__Impl_in_rule__Stmt__Group__12191);
            rule__Stmt__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Stmt__Group__1"


    // $ANTLR start "rule__Stmt__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1083:1: rule__Stmt__Group__1__Impl : ( ';' ) ;
    public final void rule__Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1087:1: ( ( ';' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1088:1: ( ';' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1088:1: ( ';' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1089:1: ';'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStmtAccess().getSemicolonKeyword_1()); 
            }
            match(input,12,FOLLOW_12_in_rule__Stmt__Group__1__Impl2219); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStmtAccess().getSemicolonKeyword_1()); 
            }

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
    // $ANTLR end "rule__Stmt__Group__1__Impl"


    // $ANTLR start "rule__Declaration__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1106:1: rule__Declaration__Group__0 : rule__Declaration__Group__0__Impl rule__Declaration__Group__1 ;
    public final void rule__Declaration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1110:1: ( rule__Declaration__Group__0__Impl rule__Declaration__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1111:2: rule__Declaration__Group__0__Impl rule__Declaration__Group__1
            {
            pushFollow(FOLLOW_rule__Declaration__Group__0__Impl_in_rule__Declaration__Group__02254);
            rule__Declaration__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Declaration__Group__1_in_rule__Declaration__Group__02257);
            rule__Declaration__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Declaration__Group__0"


    // $ANTLR start "rule__Declaration__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1118:1: rule__Declaration__Group__0__Impl : ( ( rule__Declaration__TypeAssignment_0 ) ) ;
    public final void rule__Declaration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1122:1: ( ( ( rule__Declaration__TypeAssignment_0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1123:1: ( ( rule__Declaration__TypeAssignment_0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1123:1: ( ( rule__Declaration__TypeAssignment_0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1124:1: ( rule__Declaration__TypeAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getTypeAssignment_0()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1125:1: ( rule__Declaration__TypeAssignment_0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1125:2: rule__Declaration__TypeAssignment_0
            {
            pushFollow(FOLLOW_rule__Declaration__TypeAssignment_0_in_rule__Declaration__Group__0__Impl2284);
            rule__Declaration__TypeAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getTypeAssignment_0()); 
            }

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
    // $ANTLR end "rule__Declaration__Group__0__Impl"


    // $ANTLR start "rule__Declaration__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1135:1: rule__Declaration__Group__1 : rule__Declaration__Group__1__Impl rule__Declaration__Group__2 ;
    public final void rule__Declaration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1139:1: ( rule__Declaration__Group__1__Impl rule__Declaration__Group__2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1140:2: rule__Declaration__Group__1__Impl rule__Declaration__Group__2
            {
            pushFollow(FOLLOW_rule__Declaration__Group__1__Impl_in_rule__Declaration__Group__12314);
            rule__Declaration__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Declaration__Group__2_in_rule__Declaration__Group__12317);
            rule__Declaration__Group__2();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Declaration__Group__1"


    // $ANTLR start "rule__Declaration__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1147:1: rule__Declaration__Group__1__Impl : ( ( rule__Declaration__TypeParameterAssignment_1 )? ) ;
    public final void rule__Declaration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1151:1: ( ( ( rule__Declaration__TypeParameterAssignment_1 )? ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1152:1: ( ( rule__Declaration__TypeParameterAssignment_1 )? )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1152:1: ( ( rule__Declaration__TypeParameterAssignment_1 )? )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1153:1: ( rule__Declaration__TypeParameterAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getTypeParameterAssignment_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1154:1: ( rule__Declaration__TypeParameterAssignment_1 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==17) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1154:2: rule__Declaration__TypeParameterAssignment_1
                    {
                    pushFollow(FOLLOW_rule__Declaration__TypeParameterAssignment_1_in_rule__Declaration__Group__1__Impl2344);
                    rule__Declaration__TypeParameterAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getTypeParameterAssignment_1()); 
            }

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
    // $ANTLR end "rule__Declaration__Group__1__Impl"


    // $ANTLR start "rule__Declaration__Group__2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1164:1: rule__Declaration__Group__2 : rule__Declaration__Group__2__Impl rule__Declaration__Group__3 ;
    public final void rule__Declaration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1168:1: ( rule__Declaration__Group__2__Impl rule__Declaration__Group__3 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1169:2: rule__Declaration__Group__2__Impl rule__Declaration__Group__3
            {
            pushFollow(FOLLOW_rule__Declaration__Group__2__Impl_in_rule__Declaration__Group__22375);
            rule__Declaration__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Declaration__Group__3_in_rule__Declaration__Group__22378);
            rule__Declaration__Group__3();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Declaration__Group__2"


    // $ANTLR start "rule__Declaration__Group__2__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1176:1: rule__Declaration__Group__2__Impl : ( ( rule__Declaration__NameAssignment_2 ) ) ;
    public final void rule__Declaration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1180:1: ( ( ( rule__Declaration__NameAssignment_2 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1181:1: ( ( rule__Declaration__NameAssignment_2 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1181:1: ( ( rule__Declaration__NameAssignment_2 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1182:1: ( rule__Declaration__NameAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getNameAssignment_2()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1183:1: ( rule__Declaration__NameAssignment_2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1183:2: rule__Declaration__NameAssignment_2
            {
            pushFollow(FOLLOW_rule__Declaration__NameAssignment_2_in_rule__Declaration__Group__2__Impl2405);
            rule__Declaration__NameAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getNameAssignment_2()); 
            }

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
    // $ANTLR end "rule__Declaration__Group__2__Impl"


    // $ANTLR start "rule__Declaration__Group__3"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1193:1: rule__Declaration__Group__3 : rule__Declaration__Group__3__Impl rule__Declaration__Group__4 ;
    public final void rule__Declaration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1197:1: ( rule__Declaration__Group__3__Impl rule__Declaration__Group__4 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1198:2: rule__Declaration__Group__3__Impl rule__Declaration__Group__4
            {
            pushFollow(FOLLOW_rule__Declaration__Group__3__Impl_in_rule__Declaration__Group__32435);
            rule__Declaration__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Declaration__Group__4_in_rule__Declaration__Group__32438);
            rule__Declaration__Group__4();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Declaration__Group__3"


    // $ANTLR start "rule__Declaration__Group__3__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1205:1: rule__Declaration__Group__3__Impl : ( '=' ) ;
    public final void rule__Declaration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1209:1: ( ( '=' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1210:1: ( '=' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1210:1: ( '=' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1211:1: '='
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getEqualsSignKeyword_3()); 
            }
            match(input,16,FOLLOW_16_in_rule__Declaration__Group__3__Impl2466); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getEqualsSignKeyword_3()); 
            }

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
    // $ANTLR end "rule__Declaration__Group__3__Impl"


    // $ANTLR start "rule__Declaration__Group__4"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1224:1: rule__Declaration__Group__4 : rule__Declaration__Group__4__Impl ;
    public final void rule__Declaration__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1228:1: ( rule__Declaration__Group__4__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1229:2: rule__Declaration__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__Declaration__Group__4__Impl_in_rule__Declaration__Group__42497);
            rule__Declaration__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Declaration__Group__4"


    // $ANTLR start "rule__Declaration__Group__4__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1235:1: rule__Declaration__Group__4__Impl : ( ( rule__Declaration__DefaultValueAssignment_4 ) ) ;
    public final void rule__Declaration__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1239:1: ( ( ( rule__Declaration__DefaultValueAssignment_4 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1240:1: ( ( rule__Declaration__DefaultValueAssignment_4 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1240:1: ( ( rule__Declaration__DefaultValueAssignment_4 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1241:1: ( rule__Declaration__DefaultValueAssignment_4 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getDefaultValueAssignment_4()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1242:1: ( rule__Declaration__DefaultValueAssignment_4 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1242:2: rule__Declaration__DefaultValueAssignment_4
            {
            pushFollow(FOLLOW_rule__Declaration__DefaultValueAssignment_4_in_rule__Declaration__Group__4__Impl2524);
            rule__Declaration__DefaultValueAssignment_4();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getDefaultValueAssignment_4()); 
            }

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
    // $ANTLR end "rule__Declaration__Group__4__Impl"


    // $ANTLR start "rule__TypeParameter__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1262:1: rule__TypeParameter__Group__0 : rule__TypeParameter__Group__0__Impl rule__TypeParameter__Group__1 ;
    public final void rule__TypeParameter__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1266:1: ( rule__TypeParameter__Group__0__Impl rule__TypeParameter__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1267:2: rule__TypeParameter__Group__0__Impl rule__TypeParameter__Group__1
            {
            pushFollow(FOLLOW_rule__TypeParameter__Group__0__Impl_in_rule__TypeParameter__Group__02564);
            rule__TypeParameter__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__TypeParameter__Group__1_in_rule__TypeParameter__Group__02567);
            rule__TypeParameter__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__TypeParameter__Group__0"


    // $ANTLR start "rule__TypeParameter__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1274:1: rule__TypeParameter__Group__0__Impl : ( '<' ) ;
    public final void rule__TypeParameter__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1278:1: ( ( '<' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1279:1: ( '<' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1279:1: ( '<' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1280:1: '<'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTypeParameterAccess().getLessThanSignKeyword_0()); 
            }
            match(input,17,FOLLOW_17_in_rule__TypeParameter__Group__0__Impl2595); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTypeParameterAccess().getLessThanSignKeyword_0()); 
            }

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
    // $ANTLR end "rule__TypeParameter__Group__0__Impl"


    // $ANTLR start "rule__TypeParameter__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1293:1: rule__TypeParameter__Group__1 : rule__TypeParameter__Group__1__Impl rule__TypeParameter__Group__2 ;
    public final void rule__TypeParameter__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1297:1: ( rule__TypeParameter__Group__1__Impl rule__TypeParameter__Group__2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1298:2: rule__TypeParameter__Group__1__Impl rule__TypeParameter__Group__2
            {
            pushFollow(FOLLOW_rule__TypeParameter__Group__1__Impl_in_rule__TypeParameter__Group__12626);
            rule__TypeParameter__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__TypeParameter__Group__2_in_rule__TypeParameter__Group__12629);
            rule__TypeParameter__Group__2();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__TypeParameter__Group__1"


    // $ANTLR start "rule__TypeParameter__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1305:1: rule__TypeParameter__Group__1__Impl : ( ( rule__TypeParameter__TypePAssignment_1 ) ) ;
    public final void rule__TypeParameter__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1309:1: ( ( ( rule__TypeParameter__TypePAssignment_1 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1310:1: ( ( rule__TypeParameter__TypePAssignment_1 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1310:1: ( ( rule__TypeParameter__TypePAssignment_1 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1311:1: ( rule__TypeParameter__TypePAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTypeParameterAccess().getTypePAssignment_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1312:1: ( rule__TypeParameter__TypePAssignment_1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1312:2: rule__TypeParameter__TypePAssignment_1
            {
            pushFollow(FOLLOW_rule__TypeParameter__TypePAssignment_1_in_rule__TypeParameter__Group__1__Impl2656);
            rule__TypeParameter__TypePAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getTypeParameterAccess().getTypePAssignment_1()); 
            }

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
    // $ANTLR end "rule__TypeParameter__Group__1__Impl"


    // $ANTLR start "rule__TypeParameter__Group__2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1322:1: rule__TypeParameter__Group__2 : rule__TypeParameter__Group__2__Impl ;
    public final void rule__TypeParameter__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1326:1: ( rule__TypeParameter__Group__2__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1327:2: rule__TypeParameter__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__TypeParameter__Group__2__Impl_in_rule__TypeParameter__Group__22686);
            rule__TypeParameter__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__TypeParameter__Group__2"


    // $ANTLR start "rule__TypeParameter__Group__2__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1333:1: rule__TypeParameter__Group__2__Impl : ( '>' ) ;
    public final void rule__TypeParameter__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1337:1: ( ( '>' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1338:1: ( '>' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1338:1: ( '>' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1339:1: '>'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTypeParameterAccess().getGreaterThanSignKeyword_2()); 
            }
            match(input,18,FOLLOW_18_in_rule__TypeParameter__Group__2__Impl2714); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTypeParameterAccess().getGreaterThanSignKeyword_2()); 
            }

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
    // $ANTLR end "rule__TypeParameter__Group__2__Impl"


    // $ANTLR start "rule__Assignment__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1358:1: rule__Assignment__Group__0 : rule__Assignment__Group__0__Impl rule__Assignment__Group__1 ;
    public final void rule__Assignment__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1362:1: ( rule__Assignment__Group__0__Impl rule__Assignment__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1363:2: rule__Assignment__Group__0__Impl rule__Assignment__Group__1
            {
            pushFollow(FOLLOW_rule__Assignment__Group__0__Impl_in_rule__Assignment__Group__02751);
            rule__Assignment__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Assignment__Group__1_in_rule__Assignment__Group__02754);
            rule__Assignment__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Assignment__Group__0"


    // $ANTLR start "rule__Assignment__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1370:1: rule__Assignment__Group__0__Impl : ( ( rule__Assignment__VarAssignment_0 ) ) ;
    public final void rule__Assignment__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1374:1: ( ( ( rule__Assignment__VarAssignment_0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1375:1: ( ( rule__Assignment__VarAssignment_0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1375:1: ( ( rule__Assignment__VarAssignment_0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1376:1: ( rule__Assignment__VarAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getVarAssignment_0()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1377:1: ( rule__Assignment__VarAssignment_0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1377:2: rule__Assignment__VarAssignment_0
            {
            pushFollow(FOLLOW_rule__Assignment__VarAssignment_0_in_rule__Assignment__Group__0__Impl2781);
            rule__Assignment__VarAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getVarAssignment_0()); 
            }

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
    // $ANTLR end "rule__Assignment__Group__0__Impl"


    // $ANTLR start "rule__Assignment__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1387:1: rule__Assignment__Group__1 : rule__Assignment__Group__1__Impl rule__Assignment__Group__2 ;
    public final void rule__Assignment__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1391:1: ( rule__Assignment__Group__1__Impl rule__Assignment__Group__2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1392:2: rule__Assignment__Group__1__Impl rule__Assignment__Group__2
            {
            pushFollow(FOLLOW_rule__Assignment__Group__1__Impl_in_rule__Assignment__Group__12811);
            rule__Assignment__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Assignment__Group__2_in_rule__Assignment__Group__12814);
            rule__Assignment__Group__2();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Assignment__Group__1"


    // $ANTLR start "rule__Assignment__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1399:1: rule__Assignment__Group__1__Impl : ( '=' ) ;
    public final void rule__Assignment__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1403:1: ( ( '=' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1404:1: ( '=' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1404:1: ( '=' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1405:1: '='
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1()); 
            }
            match(input,16,FOLLOW_16_in_rule__Assignment__Group__1__Impl2842); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getEqualsSignKeyword_1()); 
            }

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
    // $ANTLR end "rule__Assignment__Group__1__Impl"


    // $ANTLR start "rule__Assignment__Group__2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1418:1: rule__Assignment__Group__2 : rule__Assignment__Group__2__Impl ;
    public final void rule__Assignment__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1422:1: ( rule__Assignment__Group__2__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1423:2: rule__Assignment__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Assignment__Group__2__Impl_in_rule__Assignment__Group__22873);
            rule__Assignment__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Assignment__Group__2"


    // $ANTLR start "rule__Assignment__Group__2__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1429:1: rule__Assignment__Group__2__Impl : ( ( rule__Assignment__ExpAssignment_2 ) ) ;
    public final void rule__Assignment__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1433:1: ( ( ( rule__Assignment__ExpAssignment_2 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1434:1: ( ( rule__Assignment__ExpAssignment_2 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1434:1: ( ( rule__Assignment__ExpAssignment_2 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1435:1: ( rule__Assignment__ExpAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getExpAssignment_2()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1436:1: ( rule__Assignment__ExpAssignment_2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1436:2: rule__Assignment__ExpAssignment_2
            {
            pushFollow(FOLLOW_rule__Assignment__ExpAssignment_2_in_rule__Assignment__Group__2__Impl2900);
            rule__Assignment__ExpAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getExpAssignment_2()); 
            }

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
    // $ANTLR end "rule__Assignment__Group__2__Impl"


    // $ANTLR start "rule__Fully_qualified_name__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1452:1: rule__Fully_qualified_name__Group__0 : rule__Fully_qualified_name__Group__0__Impl rule__Fully_qualified_name__Group__1 ;
    public final void rule__Fully_qualified_name__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1456:1: ( rule__Fully_qualified_name__Group__0__Impl rule__Fully_qualified_name__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1457:2: rule__Fully_qualified_name__Group__0__Impl rule__Fully_qualified_name__Group__1
            {
            pushFollow(FOLLOW_rule__Fully_qualified_name__Group__0__Impl_in_rule__Fully_qualified_name__Group__02936);
            rule__Fully_qualified_name__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Fully_qualified_name__Group__1_in_rule__Fully_qualified_name__Group__02939);
            rule__Fully_qualified_name__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Fully_qualified_name__Group__0"


    // $ANTLR start "rule__Fully_qualified_name__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1464:1: rule__Fully_qualified_name__Group__0__Impl : ( RULE_ID ) ;
    public final void rule__Fully_qualified_name__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1468:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1469:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1469:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1470:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFully_qualified_nameAccess().getIDTerminalRuleCall_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Fully_qualified_name__Group__0__Impl2966); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFully_qualified_nameAccess().getIDTerminalRuleCall_0()); 
            }

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
    // $ANTLR end "rule__Fully_qualified_name__Group__0__Impl"


    // $ANTLR start "rule__Fully_qualified_name__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1481:1: rule__Fully_qualified_name__Group__1 : rule__Fully_qualified_name__Group__1__Impl ;
    public final void rule__Fully_qualified_name__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1485:1: ( rule__Fully_qualified_name__Group__1__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1486:2: rule__Fully_qualified_name__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Fully_qualified_name__Group__1__Impl_in_rule__Fully_qualified_name__Group__12995);
            rule__Fully_qualified_name__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Fully_qualified_name__Group__1"


    // $ANTLR start "rule__Fully_qualified_name__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1492:1: rule__Fully_qualified_name__Group__1__Impl : ( ( rule__Fully_qualified_name__Group_1__0 )* ) ;
    public final void rule__Fully_qualified_name__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1496:1: ( ( ( rule__Fully_qualified_name__Group_1__0 )* ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1497:1: ( ( rule__Fully_qualified_name__Group_1__0 )* )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1497:1: ( ( rule__Fully_qualified_name__Group_1__0 )* )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1498:1: ( rule__Fully_qualified_name__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFully_qualified_nameAccess().getGroup_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1499:1: ( rule__Fully_qualified_name__Group_1__0 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==19) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1499:2: rule__Fully_qualified_name__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Fully_qualified_name__Group_1__0_in_rule__Fully_qualified_name__Group__1__Impl3022);
            	    rule__Fully_qualified_name__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getFully_qualified_nameAccess().getGroup_1()); 
            }

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
    // $ANTLR end "rule__Fully_qualified_name__Group__1__Impl"


    // $ANTLR start "rule__Fully_qualified_name__Group_1__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1513:1: rule__Fully_qualified_name__Group_1__0 : rule__Fully_qualified_name__Group_1__0__Impl rule__Fully_qualified_name__Group_1__1 ;
    public final void rule__Fully_qualified_name__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1517:1: ( rule__Fully_qualified_name__Group_1__0__Impl rule__Fully_qualified_name__Group_1__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1518:2: rule__Fully_qualified_name__Group_1__0__Impl rule__Fully_qualified_name__Group_1__1
            {
            pushFollow(FOLLOW_rule__Fully_qualified_name__Group_1__0__Impl_in_rule__Fully_qualified_name__Group_1__03057);
            rule__Fully_qualified_name__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Fully_qualified_name__Group_1__1_in_rule__Fully_qualified_name__Group_1__03060);
            rule__Fully_qualified_name__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Fully_qualified_name__Group_1__0"


    // $ANTLR start "rule__Fully_qualified_name__Group_1__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1525:1: rule__Fully_qualified_name__Group_1__0__Impl : ( '.' ) ;
    public final void rule__Fully_qualified_name__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1529:1: ( ( '.' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1530:1: ( '.' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1530:1: ( '.' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1531:1: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFully_qualified_nameAccess().getFullStopKeyword_1_0()); 
            }
            match(input,19,FOLLOW_19_in_rule__Fully_qualified_name__Group_1__0__Impl3088); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFully_qualified_nameAccess().getFullStopKeyword_1_0()); 
            }

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
    // $ANTLR end "rule__Fully_qualified_name__Group_1__0__Impl"


    // $ANTLR start "rule__Fully_qualified_name__Group_1__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1544:1: rule__Fully_qualified_name__Group_1__1 : rule__Fully_qualified_name__Group_1__1__Impl ;
    public final void rule__Fully_qualified_name__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1548:1: ( rule__Fully_qualified_name__Group_1__1__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1549:2: rule__Fully_qualified_name__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Fully_qualified_name__Group_1__1__Impl_in_rule__Fully_qualified_name__Group_1__13119);
            rule__Fully_qualified_name__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Fully_qualified_name__Group_1__1"


    // $ANTLR start "rule__Fully_qualified_name__Group_1__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1555:1: rule__Fully_qualified_name__Group_1__1__Impl : ( RULE_ID ) ;
    public final void rule__Fully_qualified_name__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1559:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1560:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1560:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1561:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getFully_qualified_nameAccess().getIDTerminalRuleCall_1_1()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Fully_qualified_name__Group_1__1__Impl3146); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getFully_qualified_nameAccess().getIDTerminalRuleCall_1_1()); 
            }

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
    // $ANTLR end "rule__Fully_qualified_name__Group_1__1__Impl"


    // $ANTLR start "rule__Constructor_call__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1576:1: rule__Constructor_call__Group__0 : rule__Constructor_call__Group__0__Impl rule__Constructor_call__Group__1 ;
    public final void rule__Constructor_call__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1580:1: ( rule__Constructor_call__Group__0__Impl rule__Constructor_call__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1581:2: rule__Constructor_call__Group__0__Impl rule__Constructor_call__Group__1
            {
            pushFollow(FOLLOW_rule__Constructor_call__Group__0__Impl_in_rule__Constructor_call__Group__03179);
            rule__Constructor_call__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Constructor_call__Group__1_in_rule__Constructor_call__Group__03182);
            rule__Constructor_call__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Constructor_call__Group__0"


    // $ANTLR start "rule__Constructor_call__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1588:1: rule__Constructor_call__Group__0__Impl : ( 'new' ) ;
    public final void rule__Constructor_call__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1592:1: ( ( 'new' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1593:1: ( 'new' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1593:1: ( 'new' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1594:1: 'new'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getConstructor_callAccess().getNewKeyword_0()); 
            }
            match(input,20,FOLLOW_20_in_rule__Constructor_call__Group__0__Impl3210); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getConstructor_callAccess().getNewKeyword_0()); 
            }

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
    // $ANTLR end "rule__Constructor_call__Group__0__Impl"


    // $ANTLR start "rule__Constructor_call__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1607:1: rule__Constructor_call__Group__1 : rule__Constructor_call__Group__1__Impl ;
    public final void rule__Constructor_call__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1611:1: ( rule__Constructor_call__Group__1__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1612:2: rule__Constructor_call__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Constructor_call__Group__1__Impl_in_rule__Constructor_call__Group__13241);
            rule__Constructor_call__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Constructor_call__Group__1"


    // $ANTLR start "rule__Constructor_call__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1618:1: rule__Constructor_call__Group__1__Impl : ( ( rule__Constructor_call__MethodAssignment_1 ) ) ;
    public final void rule__Constructor_call__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1622:1: ( ( ( rule__Constructor_call__MethodAssignment_1 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1623:1: ( ( rule__Constructor_call__MethodAssignment_1 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1623:1: ( ( rule__Constructor_call__MethodAssignment_1 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1624:1: ( rule__Constructor_call__MethodAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getConstructor_callAccess().getMethodAssignment_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1625:1: ( rule__Constructor_call__MethodAssignment_1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1625:2: rule__Constructor_call__MethodAssignment_1
            {
            pushFollow(FOLLOW_rule__Constructor_call__MethodAssignment_1_in_rule__Constructor_call__Group__1__Impl3268);
            rule__Constructor_call__MethodAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getConstructor_callAccess().getMethodAssignment_1()); 
            }

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
    // $ANTLR end "rule__Constructor_call__Group__1__Impl"


    // $ANTLR start "rule__MethodCall__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1639:1: rule__MethodCall__Group__0 : rule__MethodCall__Group__0__Impl rule__MethodCall__Group__1 ;
    public final void rule__MethodCall__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1643:1: ( rule__MethodCall__Group__0__Impl rule__MethodCall__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1644:2: rule__MethodCall__Group__0__Impl rule__MethodCall__Group__1
            {
            pushFollow(FOLLOW_rule__MethodCall__Group__0__Impl_in_rule__MethodCall__Group__03302);
            rule__MethodCall__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__MethodCall__Group__1_in_rule__MethodCall__Group__03305);
            rule__MethodCall__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__MethodCall__Group__0"


    // $ANTLR start "rule__MethodCall__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1651:1: rule__MethodCall__Group__0__Impl : ( ( rule__MethodCall__NameAssignment_0 ) ) ;
    public final void rule__MethodCall__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1655:1: ( ( ( rule__MethodCall__NameAssignment_0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1656:1: ( ( rule__MethodCall__NameAssignment_0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1656:1: ( ( rule__MethodCall__NameAssignment_0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1657:1: ( rule__MethodCall__NameAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethodCallAccess().getNameAssignment_0()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1658:1: ( rule__MethodCall__NameAssignment_0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1658:2: rule__MethodCall__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__MethodCall__NameAssignment_0_in_rule__MethodCall__Group__0__Impl3332);
            rule__MethodCall__NameAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethodCallAccess().getNameAssignment_0()); 
            }

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
    // $ANTLR end "rule__MethodCall__Group__0__Impl"


    // $ANTLR start "rule__MethodCall__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1668:1: rule__MethodCall__Group__1 : rule__MethodCall__Group__1__Impl rule__MethodCall__Group__2 ;
    public final void rule__MethodCall__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1672:1: ( rule__MethodCall__Group__1__Impl rule__MethodCall__Group__2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1673:2: rule__MethodCall__Group__1__Impl rule__MethodCall__Group__2
            {
            pushFollow(FOLLOW_rule__MethodCall__Group__1__Impl_in_rule__MethodCall__Group__13362);
            rule__MethodCall__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__MethodCall__Group__2_in_rule__MethodCall__Group__13365);
            rule__MethodCall__Group__2();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__MethodCall__Group__1"


    // $ANTLR start "rule__MethodCall__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1680:1: rule__MethodCall__Group__1__Impl : ( ( rule__MethodCall__TypePAssignment_1 )? ) ;
    public final void rule__MethodCall__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1684:1: ( ( ( rule__MethodCall__TypePAssignment_1 )? ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1685:1: ( ( rule__MethodCall__TypePAssignment_1 )? )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1685:1: ( ( rule__MethodCall__TypePAssignment_1 )? )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1686:1: ( rule__MethodCall__TypePAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethodCallAccess().getTypePAssignment_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1687:1: ( rule__MethodCall__TypePAssignment_1 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==17) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1687:2: rule__MethodCall__TypePAssignment_1
                    {
                    pushFollow(FOLLOW_rule__MethodCall__TypePAssignment_1_in_rule__MethodCall__Group__1__Impl3392);
                    rule__MethodCall__TypePAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethodCallAccess().getTypePAssignment_1()); 
            }

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
    // $ANTLR end "rule__MethodCall__Group__1__Impl"


    // $ANTLR start "rule__MethodCall__Group__2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1697:1: rule__MethodCall__Group__2 : rule__MethodCall__Group__2__Impl rule__MethodCall__Group__3 ;
    public final void rule__MethodCall__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1701:1: ( rule__MethodCall__Group__2__Impl rule__MethodCall__Group__3 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1702:2: rule__MethodCall__Group__2__Impl rule__MethodCall__Group__3
            {
            pushFollow(FOLLOW_rule__MethodCall__Group__2__Impl_in_rule__MethodCall__Group__23423);
            rule__MethodCall__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__MethodCall__Group__3_in_rule__MethodCall__Group__23426);
            rule__MethodCall__Group__3();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__MethodCall__Group__2"


    // $ANTLR start "rule__MethodCall__Group__2__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1709:1: rule__MethodCall__Group__2__Impl : ( '(' ) ;
    public final void rule__MethodCall__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1713:1: ( ( '(' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1714:1: ( '(' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1714:1: ( '(' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1715:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethodCallAccess().getLeftParenthesisKeyword_2()); 
            }
            match(input,21,FOLLOW_21_in_rule__MethodCall__Group__2__Impl3454); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethodCallAccess().getLeftParenthesisKeyword_2()); 
            }

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
    // $ANTLR end "rule__MethodCall__Group__2__Impl"


    // $ANTLR start "rule__MethodCall__Group__3"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1728:1: rule__MethodCall__Group__3 : rule__MethodCall__Group__3__Impl ;
    public final void rule__MethodCall__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1732:1: ( rule__MethodCall__Group__3__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1733:2: rule__MethodCall__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__MethodCall__Group__3__Impl_in_rule__MethodCall__Group__33485);
            rule__MethodCall__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__MethodCall__Group__3"


    // $ANTLR start "rule__MethodCall__Group__3__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1739:1: rule__MethodCall__Group__3__Impl : ( ')' ) ;
    public final void rule__MethodCall__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1743:1: ( ( ')' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1744:1: ( ')' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1744:1: ( ')' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1745:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethodCallAccess().getRightParenthesisKeyword_3()); 
            }
            match(input,22,FOLLOW_22_in_rule__MethodCall__Group__3__Impl3513); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethodCallAccess().getRightParenthesisKeyword_3()); 
            }

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
    // $ANTLR end "rule__MethodCall__Group__3__Impl"


    // $ANTLR start "rule__Method_def__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1766:1: rule__Method_def__Group__0 : rule__Method_def__Group__0__Impl rule__Method_def__Group__1 ;
    public final void rule__Method_def__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1770:1: ( rule__Method_def__Group__0__Impl rule__Method_def__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1771:2: rule__Method_def__Group__0__Impl rule__Method_def__Group__1
            {
            pushFollow(FOLLOW_rule__Method_def__Group__0__Impl_in_rule__Method_def__Group__03552);
            rule__Method_def__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Method_def__Group__1_in_rule__Method_def__Group__03555);
            rule__Method_def__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group__0"


    // $ANTLR start "rule__Method_def__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1778:1: rule__Method_def__Group__0__Impl : ( ( rule__Method_def__NameAssignment_0 ) ) ;
    public final void rule__Method_def__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1782:1: ( ( ( rule__Method_def__NameAssignment_0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1783:1: ( ( rule__Method_def__NameAssignment_0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1783:1: ( ( rule__Method_def__NameAssignment_0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1784:1: ( rule__Method_def__NameAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getNameAssignment_0()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1785:1: ( rule__Method_def__NameAssignment_0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1785:2: rule__Method_def__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__Method_def__NameAssignment_0_in_rule__Method_def__Group__0__Impl3582);
            rule__Method_def__NameAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getNameAssignment_0()); 
            }

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
    // $ANTLR end "rule__Method_def__Group__0__Impl"


    // $ANTLR start "rule__Method_def__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1795:1: rule__Method_def__Group__1 : rule__Method_def__Group__1__Impl rule__Method_def__Group__2 ;
    public final void rule__Method_def__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1799:1: ( rule__Method_def__Group__1__Impl rule__Method_def__Group__2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1800:2: rule__Method_def__Group__1__Impl rule__Method_def__Group__2
            {
            pushFollow(FOLLOW_rule__Method_def__Group__1__Impl_in_rule__Method_def__Group__13612);
            rule__Method_def__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Method_def__Group__2_in_rule__Method_def__Group__13615);
            rule__Method_def__Group__2();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group__1"


    // $ANTLR start "rule__Method_def__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1807:1: rule__Method_def__Group__1__Impl : ( '(' ) ;
    public final void rule__Method_def__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1811:1: ( ( '(' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1812:1: ( '(' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1812:1: ( '(' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1813:1: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getLeftParenthesisKeyword_1()); 
            }
            match(input,21,FOLLOW_21_in_rule__Method_def__Group__1__Impl3643); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getLeftParenthesisKeyword_1()); 
            }

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
    // $ANTLR end "rule__Method_def__Group__1__Impl"


    // $ANTLR start "rule__Method_def__Group__2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1826:1: rule__Method_def__Group__2 : rule__Method_def__Group__2__Impl rule__Method_def__Group__3 ;
    public final void rule__Method_def__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1830:1: ( rule__Method_def__Group__2__Impl rule__Method_def__Group__3 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1831:2: rule__Method_def__Group__2__Impl rule__Method_def__Group__3
            {
            pushFollow(FOLLOW_rule__Method_def__Group__2__Impl_in_rule__Method_def__Group__23674);
            rule__Method_def__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Method_def__Group__3_in_rule__Method_def__Group__23677);
            rule__Method_def__Group__3();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group__2"


    // $ANTLR start "rule__Method_def__Group__2__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1838:1: rule__Method_def__Group__2__Impl : ( ( rule__Method_def__Group_2__0 )? ) ;
    public final void rule__Method_def__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1842:1: ( ( ( rule__Method_def__Group_2__0 )? ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1843:1: ( ( rule__Method_def__Group_2__0 )? )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1843:1: ( ( rule__Method_def__Group_2__0 )? )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1844:1: ( rule__Method_def__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getGroup_2()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1845:1: ( rule__Method_def__Group_2__0 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1845:2: rule__Method_def__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Method_def__Group_2__0_in_rule__Method_def__Group__2__Impl3704);
                    rule__Method_def__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getGroup_2()); 
            }

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
    // $ANTLR end "rule__Method_def__Group__2__Impl"


    // $ANTLR start "rule__Method_def__Group__3"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1855:1: rule__Method_def__Group__3 : rule__Method_def__Group__3__Impl rule__Method_def__Group__4 ;
    public final void rule__Method_def__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1859:1: ( rule__Method_def__Group__3__Impl rule__Method_def__Group__4 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1860:2: rule__Method_def__Group__3__Impl rule__Method_def__Group__4
            {
            pushFollow(FOLLOW_rule__Method_def__Group__3__Impl_in_rule__Method_def__Group__33735);
            rule__Method_def__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Method_def__Group__4_in_rule__Method_def__Group__33738);
            rule__Method_def__Group__4();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group__3"


    // $ANTLR start "rule__Method_def__Group__3__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1867:1: rule__Method_def__Group__3__Impl : ( ')' ) ;
    public final void rule__Method_def__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1871:1: ( ( ')' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1872:1: ( ')' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1872:1: ( ')' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1873:1: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getRightParenthesisKeyword_3()); 
            }
            match(input,22,FOLLOW_22_in_rule__Method_def__Group__3__Impl3766); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getRightParenthesisKeyword_3()); 
            }

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
    // $ANTLR end "rule__Method_def__Group__3__Impl"


    // $ANTLR start "rule__Method_def__Group__4"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1886:1: rule__Method_def__Group__4 : rule__Method_def__Group__4__Impl rule__Method_def__Group__5 ;
    public final void rule__Method_def__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1890:1: ( rule__Method_def__Group__4__Impl rule__Method_def__Group__5 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1891:2: rule__Method_def__Group__4__Impl rule__Method_def__Group__5
            {
            pushFollow(FOLLOW_rule__Method_def__Group__4__Impl_in_rule__Method_def__Group__43797);
            rule__Method_def__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Method_def__Group__5_in_rule__Method_def__Group__43800);
            rule__Method_def__Group__5();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group__4"


    // $ANTLR start "rule__Method_def__Group__4__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1898:1: rule__Method_def__Group__4__Impl : ( '{' ) ;
    public final void rule__Method_def__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1902:1: ( ( '{' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1903:1: ( '{' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1903:1: ( '{' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1904:1: '{'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getLeftCurlyBracketKeyword_4()); 
            }
            match(input,14,FOLLOW_14_in_rule__Method_def__Group__4__Impl3828); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getLeftCurlyBracketKeyword_4()); 
            }

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
    // $ANTLR end "rule__Method_def__Group__4__Impl"


    // $ANTLR start "rule__Method_def__Group__5"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1917:1: rule__Method_def__Group__5 : rule__Method_def__Group__5__Impl rule__Method_def__Group__6 ;
    public final void rule__Method_def__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1921:1: ( rule__Method_def__Group__5__Impl rule__Method_def__Group__6 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1922:2: rule__Method_def__Group__5__Impl rule__Method_def__Group__6
            {
            pushFollow(FOLLOW_rule__Method_def__Group__5__Impl_in_rule__Method_def__Group__53859);
            rule__Method_def__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Method_def__Group__6_in_rule__Method_def__Group__53862);
            rule__Method_def__Group__6();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group__5"


    // $ANTLR start "rule__Method_def__Group__5__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1929:1: rule__Method_def__Group__5__Impl : ( ( rule__Method_def__BodyAssignment_5 ) ) ;
    public final void rule__Method_def__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1933:1: ( ( ( rule__Method_def__BodyAssignment_5 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1934:1: ( ( rule__Method_def__BodyAssignment_5 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1934:1: ( ( rule__Method_def__BodyAssignment_5 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1935:1: ( rule__Method_def__BodyAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getBodyAssignment_5()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1936:1: ( rule__Method_def__BodyAssignment_5 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1936:2: rule__Method_def__BodyAssignment_5
            {
            pushFollow(FOLLOW_rule__Method_def__BodyAssignment_5_in_rule__Method_def__Group__5__Impl3889);
            rule__Method_def__BodyAssignment_5();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getBodyAssignment_5()); 
            }

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
    // $ANTLR end "rule__Method_def__Group__5__Impl"


    // $ANTLR start "rule__Method_def__Group__6"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1946:1: rule__Method_def__Group__6 : rule__Method_def__Group__6__Impl ;
    public final void rule__Method_def__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1950:1: ( rule__Method_def__Group__6__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1951:2: rule__Method_def__Group__6__Impl
            {
            pushFollow(FOLLOW_rule__Method_def__Group__6__Impl_in_rule__Method_def__Group__63919);
            rule__Method_def__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group__6"


    // $ANTLR start "rule__Method_def__Group__6__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1957:1: rule__Method_def__Group__6__Impl : ( '}' ) ;
    public final void rule__Method_def__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1961:1: ( ( '}' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1962:1: ( '}' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1962:1: ( '}' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1963:1: '}'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getRightCurlyBracketKeyword_6()); 
            }
            match(input,15,FOLLOW_15_in_rule__Method_def__Group__6__Impl3947); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getRightCurlyBracketKeyword_6()); 
            }

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
    // $ANTLR end "rule__Method_def__Group__6__Impl"


    // $ANTLR start "rule__Method_def__Group_2__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1990:1: rule__Method_def__Group_2__0 : rule__Method_def__Group_2__0__Impl rule__Method_def__Group_2__1 ;
    public final void rule__Method_def__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1994:1: ( rule__Method_def__Group_2__0__Impl rule__Method_def__Group_2__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:1995:2: rule__Method_def__Group_2__0__Impl rule__Method_def__Group_2__1
            {
            pushFollow(FOLLOW_rule__Method_def__Group_2__0__Impl_in_rule__Method_def__Group_2__03992);
            rule__Method_def__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Method_def__Group_2__1_in_rule__Method_def__Group_2__03995);
            rule__Method_def__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group_2__0"


    // $ANTLR start "rule__Method_def__Group_2__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2002:1: rule__Method_def__Group_2__0__Impl : ( ( rule__Method_def__ArgsAssignment_2_0 ) ) ;
    public final void rule__Method_def__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2006:1: ( ( ( rule__Method_def__ArgsAssignment_2_0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2007:1: ( ( rule__Method_def__ArgsAssignment_2_0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2007:1: ( ( rule__Method_def__ArgsAssignment_2_0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2008:1: ( rule__Method_def__ArgsAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getArgsAssignment_2_0()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2009:1: ( rule__Method_def__ArgsAssignment_2_0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2009:2: rule__Method_def__ArgsAssignment_2_0
            {
            pushFollow(FOLLOW_rule__Method_def__ArgsAssignment_2_0_in_rule__Method_def__Group_2__0__Impl4022);
            rule__Method_def__ArgsAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getArgsAssignment_2_0()); 
            }

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
    // $ANTLR end "rule__Method_def__Group_2__0__Impl"


    // $ANTLR start "rule__Method_def__Group_2__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2019:1: rule__Method_def__Group_2__1 : rule__Method_def__Group_2__1__Impl ;
    public final void rule__Method_def__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2023:1: ( rule__Method_def__Group_2__1__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2024:2: rule__Method_def__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__Method_def__Group_2__1__Impl_in_rule__Method_def__Group_2__14052);
            rule__Method_def__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group_2__1"


    // $ANTLR start "rule__Method_def__Group_2__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2030:1: rule__Method_def__Group_2__1__Impl : ( ( rule__Method_def__Group_2_1__0 )* ) ;
    public final void rule__Method_def__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2034:1: ( ( ( rule__Method_def__Group_2_1__0 )* ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2035:1: ( ( rule__Method_def__Group_2_1__0 )* )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2035:1: ( ( rule__Method_def__Group_2_1__0 )* )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2036:1: ( rule__Method_def__Group_2_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getGroup_2_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2037:1: ( rule__Method_def__Group_2_1__0 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==23) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2037:2: rule__Method_def__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Method_def__Group_2_1__0_in_rule__Method_def__Group_2__1__Impl4079);
            	    rule__Method_def__Group_2_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getGroup_2_1()); 
            }

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
    // $ANTLR end "rule__Method_def__Group_2__1__Impl"


    // $ANTLR start "rule__Method_def__Group_2_1__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2051:1: rule__Method_def__Group_2_1__0 : rule__Method_def__Group_2_1__0__Impl rule__Method_def__Group_2_1__1 ;
    public final void rule__Method_def__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2055:1: ( rule__Method_def__Group_2_1__0__Impl rule__Method_def__Group_2_1__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2056:2: rule__Method_def__Group_2_1__0__Impl rule__Method_def__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__Method_def__Group_2_1__0__Impl_in_rule__Method_def__Group_2_1__04114);
            rule__Method_def__Group_2_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Method_def__Group_2_1__1_in_rule__Method_def__Group_2_1__04117);
            rule__Method_def__Group_2_1__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group_2_1__0"


    // $ANTLR start "rule__Method_def__Group_2_1__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2063:1: rule__Method_def__Group_2_1__0__Impl : ( ',' ) ;
    public final void rule__Method_def__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2067:1: ( ( ',' ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2068:1: ( ',' )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2068:1: ( ',' )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2069:1: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getCommaKeyword_2_1_0()); 
            }
            match(input,23,FOLLOW_23_in_rule__Method_def__Group_2_1__0__Impl4145); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getCommaKeyword_2_1_0()); 
            }

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
    // $ANTLR end "rule__Method_def__Group_2_1__0__Impl"


    // $ANTLR start "rule__Method_def__Group_2_1__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2082:1: rule__Method_def__Group_2_1__1 : rule__Method_def__Group_2_1__1__Impl ;
    public final void rule__Method_def__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2086:1: ( rule__Method_def__Group_2_1__1__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2087:2: rule__Method_def__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Method_def__Group_2_1__1__Impl_in_rule__Method_def__Group_2_1__14176);
            rule__Method_def__Group_2_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Method_def__Group_2_1__1"


    // $ANTLR start "rule__Method_def__Group_2_1__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2093:1: rule__Method_def__Group_2_1__1__Impl : ( ( rule__Method_def__ArgsAssignment_2_1_1 ) ) ;
    public final void rule__Method_def__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2097:1: ( ( ( rule__Method_def__ArgsAssignment_2_1_1 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2098:1: ( ( rule__Method_def__ArgsAssignment_2_1_1 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2098:1: ( ( rule__Method_def__ArgsAssignment_2_1_1 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2099:1: ( rule__Method_def__ArgsAssignment_2_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getArgsAssignment_2_1_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2100:1: ( rule__Method_def__ArgsAssignment_2_1_1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2100:2: rule__Method_def__ArgsAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__Method_def__ArgsAssignment_2_1_1_in_rule__Method_def__Group_2_1__1__Impl4203);
            rule__Method_def__ArgsAssignment_2_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getArgsAssignment_2_1_1()); 
            }

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
    // $ANTLR end "rule__Method_def__Group_2_1__1__Impl"


    // $ANTLR start "rule__Body__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2114:1: rule__Body__Group__0 : rule__Body__Group__0__Impl rule__Body__Group__1 ;
    public final void rule__Body__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2118:1: ( rule__Body__Group__0__Impl rule__Body__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2119:2: rule__Body__Group__0__Impl rule__Body__Group__1
            {
            pushFollow(FOLLOW_rule__Body__Group__0__Impl_in_rule__Body__Group__04237);
            rule__Body__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Body__Group__1_in_rule__Body__Group__04240);
            rule__Body__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Body__Group__0"


    // $ANTLR start "rule__Body__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2126:1: rule__Body__Group__0__Impl : ( () ) ;
    public final void rule__Body__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2130:1: ( ( () ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2131:1: ( () )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2131:1: ( () )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2132:1: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getBodyAction_0()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2133:1: ()
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2135:1: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getBodyAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group__0__Impl"


    // $ANTLR start "rule__Body__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2145:1: rule__Body__Group__1 : rule__Body__Group__1__Impl ;
    public final void rule__Body__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2149:1: ( rule__Body__Group__1__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2150:2: rule__Body__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Body__Group__1__Impl_in_rule__Body__Group__14298);
            rule__Body__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Body__Group__1"


    // $ANTLR start "rule__Body__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2156:1: rule__Body__Group__1__Impl : ( ( rule__Body__StmtsAssignment_1 )* ) ;
    public final void rule__Body__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2160:1: ( ( ( rule__Body__StmtsAssignment_1 )* ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2161:1: ( ( rule__Body__StmtsAssignment_1 )* )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2161:1: ( ( rule__Body__StmtsAssignment_1 )* )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2162:1: ( rule__Body__StmtsAssignment_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getStmtsAssignment_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2163:1: ( rule__Body__StmtsAssignment_1 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_ID) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2163:2: rule__Body__StmtsAssignment_1
            	    {
            	    pushFollow(FOLLOW_rule__Body__StmtsAssignment_1_in_rule__Body__Group__1__Impl4325);
            	    rule__Body__StmtsAssignment_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getStmtsAssignment_1()); 
            }

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
    // $ANTLR end "rule__Body__Group__1__Impl"


    // $ANTLR start "rule__Argument__Group__0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2177:1: rule__Argument__Group__0 : rule__Argument__Group__0__Impl rule__Argument__Group__1 ;
    public final void rule__Argument__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2181:1: ( rule__Argument__Group__0__Impl rule__Argument__Group__1 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2182:2: rule__Argument__Group__0__Impl rule__Argument__Group__1
            {
            pushFollow(FOLLOW_rule__Argument__Group__0__Impl_in_rule__Argument__Group__04360);
            rule__Argument__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Argument__Group__1_in_rule__Argument__Group__04363);
            rule__Argument__Group__1();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Argument__Group__0"


    // $ANTLR start "rule__Argument__Group__0__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2189:1: rule__Argument__Group__0__Impl : ( ( rule__Argument__TypeAssignment_0 ) ) ;
    public final void rule__Argument__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2193:1: ( ( ( rule__Argument__TypeAssignment_0 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2194:1: ( ( rule__Argument__TypeAssignment_0 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2194:1: ( ( rule__Argument__TypeAssignment_0 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2195:1: ( rule__Argument__TypeAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentAccess().getTypeAssignment_0()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2196:1: ( rule__Argument__TypeAssignment_0 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2196:2: rule__Argument__TypeAssignment_0
            {
            pushFollow(FOLLOW_rule__Argument__TypeAssignment_0_in_rule__Argument__Group__0__Impl4390);
            rule__Argument__TypeAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentAccess().getTypeAssignment_0()); 
            }

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
    // $ANTLR end "rule__Argument__Group__0__Impl"


    // $ANTLR start "rule__Argument__Group__1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2206:1: rule__Argument__Group__1 : rule__Argument__Group__1__Impl rule__Argument__Group__2 ;
    public final void rule__Argument__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2210:1: ( rule__Argument__Group__1__Impl rule__Argument__Group__2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2211:2: rule__Argument__Group__1__Impl rule__Argument__Group__2
            {
            pushFollow(FOLLOW_rule__Argument__Group__1__Impl_in_rule__Argument__Group__14420);
            rule__Argument__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_rule__Argument__Group__2_in_rule__Argument__Group__14423);
            rule__Argument__Group__2();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Argument__Group__1"


    // $ANTLR start "rule__Argument__Group__1__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2218:1: rule__Argument__Group__1__Impl : ( ( rule__Argument__TypePAssignment_1 )? ) ;
    public final void rule__Argument__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2222:1: ( ( ( rule__Argument__TypePAssignment_1 )? ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2223:1: ( ( rule__Argument__TypePAssignment_1 )? )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2223:1: ( ( rule__Argument__TypePAssignment_1 )? )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2224:1: ( rule__Argument__TypePAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentAccess().getTypePAssignment_1()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2225:1: ( rule__Argument__TypePAssignment_1 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==17) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2225:2: rule__Argument__TypePAssignment_1
                    {
                    pushFollow(FOLLOW_rule__Argument__TypePAssignment_1_in_rule__Argument__Group__1__Impl4450);
                    rule__Argument__TypePAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentAccess().getTypePAssignment_1()); 
            }

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
    // $ANTLR end "rule__Argument__Group__1__Impl"


    // $ANTLR start "rule__Argument__Group__2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2235:1: rule__Argument__Group__2 : rule__Argument__Group__2__Impl ;
    public final void rule__Argument__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2239:1: ( rule__Argument__Group__2__Impl )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2240:2: rule__Argument__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Argument__Group__2__Impl_in_rule__Argument__Group__24481);
            rule__Argument__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

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
    // $ANTLR end "rule__Argument__Group__2"


    // $ANTLR start "rule__Argument__Group__2__Impl"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2246:1: rule__Argument__Group__2__Impl : ( ( rule__Argument__NameAssignment_2 ) ) ;
    public final void rule__Argument__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2250:1: ( ( ( rule__Argument__NameAssignment_2 ) ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2251:1: ( ( rule__Argument__NameAssignment_2 ) )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2251:1: ( ( rule__Argument__NameAssignment_2 ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2252:1: ( rule__Argument__NameAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentAccess().getNameAssignment_2()); 
            }
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2253:1: ( rule__Argument__NameAssignment_2 )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2253:2: rule__Argument__NameAssignment_2
            {
            pushFollow(FOLLOW_rule__Argument__NameAssignment_2_in_rule__Argument__Group__2__Impl4508);
            rule__Argument__NameAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentAccess().getNameAssignment_2()); 
            }

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
    // $ANTLR end "rule__Argument__Group__2__Impl"


    // $ANTLR start "rule__Model__ImportsAssignment_0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2270:1: rule__Model__ImportsAssignment_0 : ( ruleimport_ ) ;
    public final void rule__Model__ImportsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2274:1: ( ( ruleimport_ ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2275:1: ( ruleimport_ )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2275:1: ( ruleimport_ )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2276:1: ruleimport_
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getImportsImport_ParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_ruleimport__in_rule__Model__ImportsAssignment_04549);
            ruleimport_();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getImportsImport_ParserRuleCall_0_0()); 
            }

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
    // $ANTLR end "rule__Model__ImportsAssignment_0"


    // $ANTLR start "rule__Model__ClassesAssignment_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2285:1: rule__Model__ClassesAssignment_1 : ( ruleclass_def ) ;
    public final void rule__Model__ClassesAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2289:1: ( ( ruleclass_def ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2290:1: ( ruleclass_def )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2290:1: ( ruleclass_def )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2291:1: ruleclass_def
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getClassesClass_defParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruleclass_def_in_rule__Model__ClassesAssignment_14580);
            ruleclass_def();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getClassesClass_defParserRuleCall_1_0()); 
            }

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
    // $ANTLR end "rule__Model__ClassesAssignment_1"


    // $ANTLR start "rule__Import___EntryAssignment_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2300:1: rule__Import___EntryAssignment_1 : ( rulefully_qualified_name ) ;
    public final void rule__Import___EntryAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2304:1: ( ( rulefully_qualified_name ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2305:1: ( rulefully_qualified_name )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2305:1: ( rulefully_qualified_name )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2306:1: rulefully_qualified_name
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getImport_Access().getEntryFully_qualified_nameParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_rulefully_qualified_name_in_rule__Import___EntryAssignment_14611);
            rulefully_qualified_name();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getImport_Access().getEntryFully_qualified_nameParserRuleCall_1_0()); 
            }

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
    // $ANTLR end "rule__Import___EntryAssignment_1"


    // $ANTLR start "rule__Class_def__NameAssignment_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2315:1: rule__Class_def__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Class_def__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2319:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2320:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2320:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2321:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getNameIDTerminalRuleCall_1_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Class_def__NameAssignment_14642); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getNameIDTerminalRuleCall_1_0()); 
            }

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
    // $ANTLR end "rule__Class_def__NameAssignment_1"


    // $ANTLR start "rule__Class_def__InitialDeclarationsAssignment_3"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2330:1: rule__Class_def__InitialDeclarationsAssignment_3 : ( rulestmt ) ;
    public final void rule__Class_def__InitialDeclarationsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2334:1: ( ( rulestmt ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2335:1: ( rulestmt )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2335:1: ( rulestmt )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2336:1: rulestmt
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getInitialDeclarationsStmtParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_rulestmt_in_rule__Class_def__InitialDeclarationsAssignment_34673);
            rulestmt();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getInitialDeclarationsStmtParserRuleCall_3_0()); 
            }

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
    // $ANTLR end "rule__Class_def__InitialDeclarationsAssignment_3"


    // $ANTLR start "rule__Class_def__FeatureAssignment_4"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2345:1: rule__Class_def__FeatureAssignment_4 : ( rulefeature ) ;
    public final void rule__Class_def__FeatureAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2349:1: ( ( rulefeature ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2350:1: ( rulefeature )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2350:1: ( rulefeature )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2351:1: rulefeature
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getClass_defAccess().getFeatureFeatureParserRuleCall_4_0()); 
            }
            pushFollow(FOLLOW_rulefeature_in_rule__Class_def__FeatureAssignment_44704);
            rulefeature();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getClass_defAccess().getFeatureFeatureParserRuleCall_4_0()); 
            }

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
    // $ANTLR end "rule__Class_def__FeatureAssignment_4"


    // $ANTLR start "rule__Declaration__TypeAssignment_0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2360:1: rule__Declaration__TypeAssignment_0 : ( RULE_ID ) ;
    public final void rule__Declaration__TypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2364:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2365:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2365:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2366:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getTypeIDTerminalRuleCall_0_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Declaration__TypeAssignment_04735); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getTypeIDTerminalRuleCall_0_0()); 
            }

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
    // $ANTLR end "rule__Declaration__TypeAssignment_0"


    // $ANTLR start "rule__Declaration__TypeParameterAssignment_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2375:1: rule__Declaration__TypeParameterAssignment_1 : ( ruletypeParameter ) ;
    public final void rule__Declaration__TypeParameterAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2379:1: ( ( ruletypeParameter ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2380:1: ( ruletypeParameter )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2380:1: ( ruletypeParameter )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2381:1: ruletypeParameter
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getTypeParameterTypeParameterParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruletypeParameter_in_rule__Declaration__TypeParameterAssignment_14766);
            ruletypeParameter();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getTypeParameterTypeParameterParserRuleCall_1_0()); 
            }

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
    // $ANTLR end "rule__Declaration__TypeParameterAssignment_1"


    // $ANTLR start "rule__Declaration__NameAssignment_2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2390:1: rule__Declaration__NameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Declaration__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2394:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2395:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2395:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2396:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getNameIDTerminalRuleCall_2_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Declaration__NameAssignment_24797); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getNameIDTerminalRuleCall_2_0()); 
            }

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
    // $ANTLR end "rule__Declaration__NameAssignment_2"


    // $ANTLR start "rule__Declaration__DefaultValueAssignment_4"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2405:1: rule__Declaration__DefaultValueAssignment_4 : ( ruleexp ) ;
    public final void rule__Declaration__DefaultValueAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2409:1: ( ( ruleexp ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2410:1: ( ruleexp )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2410:1: ( ruleexp )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2411:1: ruleexp
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDeclarationAccess().getDefaultValueExpParserRuleCall_4_0()); 
            }
            pushFollow(FOLLOW_ruleexp_in_rule__Declaration__DefaultValueAssignment_44828);
            ruleexp();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDeclarationAccess().getDefaultValueExpParserRuleCall_4_0()); 
            }

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
    // $ANTLR end "rule__Declaration__DefaultValueAssignment_4"


    // $ANTLR start "rule__TypeParameter__TypePAssignment_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2420:1: rule__TypeParameter__TypePAssignment_1 : ( RULE_ID ) ;
    public final void rule__TypeParameter__TypePAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2424:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2425:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2425:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2426:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getTypeParameterAccess().getTypePIDTerminalRuleCall_1_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__TypeParameter__TypePAssignment_14859); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getTypeParameterAccess().getTypePIDTerminalRuleCall_1_0()); 
            }

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
    // $ANTLR end "rule__TypeParameter__TypePAssignment_1"


    // $ANTLR start "rule__Assignment__VarAssignment_0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2435:1: rule__Assignment__VarAssignment_0 : ( rulefully_qualified_name ) ;
    public final void rule__Assignment__VarAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2439:1: ( ( rulefully_qualified_name ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2440:1: ( rulefully_qualified_name )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2440:1: ( rulefully_qualified_name )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2441:1: rulefully_qualified_name
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getVarFully_qualified_nameParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_rulefully_qualified_name_in_rule__Assignment__VarAssignment_04890);
            rulefully_qualified_name();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getVarFully_qualified_nameParserRuleCall_0_0()); 
            }

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
    // $ANTLR end "rule__Assignment__VarAssignment_0"


    // $ANTLR start "rule__Assignment__ExpAssignment_2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2450:1: rule__Assignment__ExpAssignment_2 : ( ruleexp ) ;
    public final void rule__Assignment__ExpAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2454:1: ( ( ruleexp ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2455:1: ( ruleexp )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2455:1: ( ruleexp )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2456:1: ruleexp
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getAssignmentAccess().getExpExpParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_ruleexp_in_rule__Assignment__ExpAssignment_24921);
            ruleexp();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getAssignmentAccess().getExpExpParserRuleCall_2_0()); 
            }

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
    // $ANTLR end "rule__Assignment__ExpAssignment_2"


    // $ANTLR start "rule__Constructor_call__MethodAssignment_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2465:1: rule__Constructor_call__MethodAssignment_1 : ( rulemethodCall ) ;
    public final void rule__Constructor_call__MethodAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2469:1: ( ( rulemethodCall ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2470:1: ( rulemethodCall )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2470:1: ( rulemethodCall )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2471:1: rulemethodCall
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getConstructor_callAccess().getMethodMethodCallParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_rulemethodCall_in_rule__Constructor_call__MethodAssignment_14952);
            rulemethodCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getConstructor_callAccess().getMethodMethodCallParserRuleCall_1_0()); 
            }

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
    // $ANTLR end "rule__Constructor_call__MethodAssignment_1"


    // $ANTLR start "rule__MethodCall__NameAssignment_0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2480:1: rule__MethodCall__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__MethodCall__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2484:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2485:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2485:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2486:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethodCallAccess().getNameIDTerminalRuleCall_0_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__MethodCall__NameAssignment_04983); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethodCallAccess().getNameIDTerminalRuleCall_0_0()); 
            }

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
    // $ANTLR end "rule__MethodCall__NameAssignment_0"


    // $ANTLR start "rule__MethodCall__TypePAssignment_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2495:1: rule__MethodCall__TypePAssignment_1 : ( ruletypeParameter ) ;
    public final void rule__MethodCall__TypePAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2499:1: ( ( ruletypeParameter ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2500:1: ( ruletypeParameter )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2500:1: ( ruletypeParameter )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2501:1: ruletypeParameter
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethodCallAccess().getTypePTypeParameterParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruletypeParameter_in_rule__MethodCall__TypePAssignment_15014);
            ruletypeParameter();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethodCallAccess().getTypePTypeParameterParserRuleCall_1_0()); 
            }

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
    // $ANTLR end "rule__MethodCall__TypePAssignment_1"


    // $ANTLR start "rule__Method_def__NameAssignment_0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2510:1: rule__Method_def__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__Method_def__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2514:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2515:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2515:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2516:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getNameIDTerminalRuleCall_0_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Method_def__NameAssignment_05045); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getNameIDTerminalRuleCall_0_0()); 
            }

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
    // $ANTLR end "rule__Method_def__NameAssignment_0"


    // $ANTLR start "rule__Method_def__ArgsAssignment_2_0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2525:1: rule__Method_def__ArgsAssignment_2_0 : ( ruleargument ) ;
    public final void rule__Method_def__ArgsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2529:1: ( ( ruleargument ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2530:1: ( ruleargument )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2530:1: ( ruleargument )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2531:1: ruleargument
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getArgsArgumentParserRuleCall_2_0_0()); 
            }
            pushFollow(FOLLOW_ruleargument_in_rule__Method_def__ArgsAssignment_2_05076);
            ruleargument();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getArgsArgumentParserRuleCall_2_0_0()); 
            }

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
    // $ANTLR end "rule__Method_def__ArgsAssignment_2_0"


    // $ANTLR start "rule__Method_def__ArgsAssignment_2_1_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2540:1: rule__Method_def__ArgsAssignment_2_1_1 : ( ruleargument ) ;
    public final void rule__Method_def__ArgsAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2544:1: ( ( ruleargument ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2545:1: ( ruleargument )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2545:1: ( ruleargument )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2546:1: ruleargument
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getArgsArgumentParserRuleCall_2_1_1_0()); 
            }
            pushFollow(FOLLOW_ruleargument_in_rule__Method_def__ArgsAssignment_2_1_15107);
            ruleargument();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getArgsArgumentParserRuleCall_2_1_1_0()); 
            }

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
    // $ANTLR end "rule__Method_def__ArgsAssignment_2_1_1"


    // $ANTLR start "rule__Method_def__BodyAssignment_5"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2555:1: rule__Method_def__BodyAssignment_5 : ( rulebody ) ;
    public final void rule__Method_def__BodyAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2559:1: ( ( rulebody ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2560:1: ( rulebody )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2560:1: ( rulebody )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2561:1: rulebody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMethod_defAccess().getBodyBodyParserRuleCall_5_0()); 
            }
            pushFollow(FOLLOW_rulebody_in_rule__Method_def__BodyAssignment_55138);
            rulebody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMethod_defAccess().getBodyBodyParserRuleCall_5_0()); 
            }

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
    // $ANTLR end "rule__Method_def__BodyAssignment_5"


    // $ANTLR start "rule__Body__StmtsAssignment_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2570:1: rule__Body__StmtsAssignment_1 : ( rulestmt ) ;
    public final void rule__Body__StmtsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2574:1: ( ( rulestmt ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2575:1: ( rulestmt )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2575:1: ( rulestmt )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2576:1: rulestmt
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getStmtsStmtParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_rulestmt_in_rule__Body__StmtsAssignment_15169);
            rulestmt();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getStmtsStmtParserRuleCall_1_0()); 
            }

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
    // $ANTLR end "rule__Body__StmtsAssignment_1"


    // $ANTLR start "rule__Argument__TypeAssignment_0"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2585:1: rule__Argument__TypeAssignment_0 : ( RULE_ID ) ;
    public final void rule__Argument__TypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2589:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2590:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2590:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2591:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentAccess().getTypeIDTerminalRuleCall_0_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Argument__TypeAssignment_05200); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentAccess().getTypeIDTerminalRuleCall_0_0()); 
            }

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
    // $ANTLR end "rule__Argument__TypeAssignment_0"


    // $ANTLR start "rule__Argument__TypePAssignment_1"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2600:1: rule__Argument__TypePAssignment_1 : ( ruletypeParameter ) ;
    public final void rule__Argument__TypePAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2604:1: ( ( ruletypeParameter ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2605:1: ( ruletypeParameter )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2605:1: ( ruletypeParameter )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2606:1: ruletypeParameter
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentAccess().getTypePTypeParameterParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_ruletypeParameter_in_rule__Argument__TypePAssignment_15231);
            ruletypeParameter();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentAccess().getTypePTypeParameterParserRuleCall_1_0()); 
            }

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
    // $ANTLR end "rule__Argument__TypePAssignment_1"


    // $ANTLR start "rule__Argument__NameAssignment_2"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2615:1: rule__Argument__NameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Argument__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2619:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2620:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2620:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2621:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentAccess().getNameIDTerminalRuleCall_2_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Argument__NameAssignment_25262); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentAccess().getNameIDTerminalRuleCall_2_0()); 
            }

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
    // $ANTLR end "rule__Argument__NameAssignment_2"


    // $ANTLR start "rule__Variable_name__NameAssignment"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2630:1: rule__Variable_name__NameAssignment : ( RULE_ID ) ;
    public final void rule__Variable_name__NameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2634:1: ( ( RULE_ID ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2635:1: ( RULE_ID )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2635:1: ( RULE_ID )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2636:1: RULE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getVariable_nameAccess().getNameIDTerminalRuleCall_0()); 
            }
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Variable_name__NameAssignment5293); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getVariable_nameAccess().getNameIDTerminalRuleCall_0()); 
            }

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
    // $ANTLR end "rule__Variable_name__NameAssignment"


    // $ANTLR start "rule__String_val__ValueAssignment"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2645:1: rule__String_val__ValueAssignment : ( RULE_STRING ) ;
    public final void rule__String_val__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2649:1: ( ( RULE_STRING ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2650:1: ( RULE_STRING )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2650:1: ( RULE_STRING )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2651:1: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getString_valAccess().getValueSTRINGTerminalRuleCall_0()); 
            }
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__String_val__ValueAssignment5324); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getString_valAccess().getValueSTRINGTerminalRuleCall_0()); 
            }

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
    // $ANTLR end "rule__String_val__ValueAssignment"


    // $ANTLR start "rule__Int_val__ValueAssignment"
    // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2660:1: rule__Int_val__ValueAssignment : ( RULE_INT ) ;
    public final void rule__Int_val__ValueAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2664:1: ( ( RULE_INT ) )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2665:1: ( RULE_INT )
            {
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2665:1: ( RULE_INT )
            // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:2666:1: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getInt_valAccess().getValueINTTerminalRuleCall_0()); 
            }
            match(input,RULE_INT,FOLLOW_RULE_INT_in_rule__Int_val__ValueAssignment5355); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getInt_valAccess().getValueINTTerminalRuleCall_0()); 
            }

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
    // $ANTLR end "rule__Int_val__ValueAssignment"

    // $ANTLR start synpred9_InternalTTC_Java
    public final void synpred9_InternalTTC_Java_fragment() throws RecognitionException {   
        // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:962:2: ( rule__Class_def__InitialDeclarationsAssignment_3 )
        // ../lu.uni.snt.secan.ttc_java.ui/src-gen/lu/uni/snt/secan/ttc_java/ui/contentassist/antlr/internal/InternalTTC_Java.g:962:2: rule__Class_def__InitialDeclarationsAssignment_3
        {
        pushFollow(FOLLOW_rule__Class_def__InitialDeclarationsAssignment_3_in_synpred9_InternalTTC_Java1968);
        rule__Class_def__InitialDeclarationsAssignment_3();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_InternalTTC_Java

    // Delegated rules

    public final boolean synpred9_InternalTTC_Java() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalTTC_Java_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\53\uffff";
    static final String DFA7_eofS =
        "\53\uffff";
    static final String DFA7_minS =
        "\2\4\1\uffff\3\4\2\20\3\14\1\4\1\22\1\4\1\0\1\4\1\26\1\21\1\4\3"+
        "\14\1\4\1\uffff\1\22\1\14\1\4\1\26\1\4\1\26\1\21\1\25\1\22\1\14"+
        "\1\22\1\14\1\4\1\26\2\25\1\22\1\14\1\25";
    static final String DFA7_maxS =
        "\1\17\1\25\1\uffff\1\4\1\24\1\4\1\20\1\23\2\14\1\25\1\4\1\22\1"+
        "\24\1\0\1\4\1\26\1\25\1\4\2\14\1\25\1\4\1\uffff\1\22\1\14\1\4\1"+
        "\26\1\4\1\26\2\25\1\22\1\14\1\22\1\14\1\4\1\26\2\25\1\22\1\14\1"+
        "\25";
    static final String DFA7_acceptS =
        "\2\uffff\1\2\24\uffff\1\1\23\uffff";
    static final String DFA7_specialS =
        "\16\uffff\1\0\34\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\1\12\uffff\1\2",
            "\1\6\13\uffff\1\4\1\5\1\uffff\1\3\1\uffff\1\2",
            "",
            "\1\7",
            "\1\12\1\10\1\11\15\uffff\1\13",
            "\1\14",
            "\1\15",
            "\1\4\2\uffff\1\3",
            "\1\16",
            "\1\16",
            "\1\16\4\uffff\1\17\3\uffff\1\20",
            "\1\21",
            "\1\22",
            "\1\25\1\23\1\24\15\uffff\1\26",
            "\1\uffff",
            "\1\30",
            "\1\31",
            "\1\32\3\uffff\1\33",
            "\1\6",
            "\1\16",
            "\1\16",
            "\1\16\4\uffff\1\34\3\uffff\1\35",
            "\1\36",
            "",
            "\1\37",
            "\1\16",
            "\1\40",
            "\1\41",
            "\1\42",
            "\1\43",
            "\1\44\3\uffff\1\45",
            "\1\20",
            "\1\46",
            "\1\16",
            "\1\47",
            "\1\16",
            "\1\50",
            "\1\51",
            "\1\33",
            "\1\35",
            "\1\52",
            "\1\16",
            "\1\45"
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "()* loopback of 962:1: ( rule__Class_def__InitialDeclarationsAssignment_3 )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_14 = input.LA(1);

                         
                        int index7_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalTTC_Java()) ) {s = 23;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index7_14);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_ruleModel_in_entryRuleModel67 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModel74 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__Group__0_in_ruleModel100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleimport__in_entryRuleimport_127 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleimport_134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Import___Group__0_in_ruleimport_160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleclass_def_in_entryRuleclass_def187 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleclass_def194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Class_def__Group__0_in_ruleclass_def220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulefeature_in_entryRulefeature247 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulefeature254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Feature__Alternatives_in_rulefeature280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulestmt_in_entryRulestmt307 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulestmt314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Stmt__Group__0_in_rulestmt340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledeclaration_in_entryRuledeclaration367 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuledeclaration374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__Group__0_in_ruledeclaration400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruletypeParameter_in_entryRuletypeParameter427 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuletypeParameter434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TypeParameter__Group__0_in_ruletypeParameter460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleassignment_in_entryRuleassignment487 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleassignment494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__0_in_ruleassignment520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulefully_qualified_name_in_entryRulefully_qualified_name547 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulefully_qualified_name554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Fully_qualified_name__Group__0_in_rulefully_qualified_name580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleexp_in_entryRuleexp607 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleexp614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Exp__Alternatives_in_ruleexp640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleconstructor_call_in_entryRuleconstructor_call667 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleconstructor_call674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Constructor_call__Group__0_in_ruleconstructor_call700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulemethodCall_in_entryRulemethodCall727 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulemethodCall734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MethodCall__Group__0_in_rulemethodCall760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulemethod_def_in_entryRulemethod_def787 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulemethod_def794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group__0_in_rulemethod_def820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulebody_in_entryRulebody847 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulebody854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Body__Group__0_in_rulebody880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleargument_in_entryRuleargument907 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleargument914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group__0_in_ruleargument940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleatom_in_entryRuleatom967 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleatom974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Atom__Alternatives_in_ruleatom1000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulevariable_name_in_entryRulevariable_name1027 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulevariable_name1034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Variable_name__NameAssignment_in_rulevariable_name1060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulestring_val_in_entryRulestring_val1087 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulestring_val1094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__String_val__ValueAssignment_in_rulestring_val1120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleint_val_in_entryRuleint_val1147 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleint_val1154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Int_val__ValueAssignment_in_ruleint_val1180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulestmt_in_rule__Feature__Alternatives1216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulemethod_def_in_rule__Feature__Alternatives1233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledeclaration_in_rule__Stmt__Alternatives_01265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleassignment_in_rule__Stmt__Alternatives_01282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleatom_in_rule__Exp__Alternatives1314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleconstructor_call_in_rule__Exp__Alternatives1331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulemethodCall_in_rule__Exp__Alternatives1348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulestring_val_in_rule__Atom__Alternatives1380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleint_val_in_rule__Atom__Alternatives1397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulevariable_name_in_rule__Atom__Alternatives1414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__Group__0__Impl_in_rule__Model__Group__01444 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__Model__Group__1_in_rule__Model__Group__01447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__ImportsAssignment_0_in_rule__Model__Group__0__Impl1474 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_rule__Model__Group__1__Impl_in_rule__Model__Group__11505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Model__ClassesAssignment_1_in_rule__Model__Group__1__Impl1532 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_rule__Import___Group__0__Impl_in_rule__Import___Group__01567 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Import___Group__1_in_rule__Import___Group__01570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Import___Group__0__Impl1598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Import___Group__1__Impl_in_rule__Import___Group__11629 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__Import___Group__2_in_rule__Import___Group__11632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Import___EntryAssignment_1_in_rule__Import___Group__1__Impl1659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Import___Group__2__Impl_in_rule__Import___Group__21689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__Import___Group__2__Impl1717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Class_def__Group__0__Impl_in_rule__Class_def__Group__01754 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Class_def__Group__1_in_rule__Class_def__Group__01757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__Class_def__Group__0__Impl1785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Class_def__Group__1__Impl_in_rule__Class_def__Group__11816 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_rule__Class_def__Group__2_in_rule__Class_def__Group__11819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Class_def__NameAssignment_1_in_rule__Class_def__Group__1__Impl1846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Class_def__Group__2__Impl_in_rule__Class_def__Group__21876 = new BitSet(new long[]{0x0000000000008010L});
    public static final BitSet FOLLOW_rule__Class_def__Group__3_in_rule__Class_def__Group__21879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__Class_def__Group__2__Impl1907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Class_def__Group__3__Impl_in_rule__Class_def__Group__31938 = new BitSet(new long[]{0x0000000000008010L});
    public static final BitSet FOLLOW_rule__Class_def__Group__4_in_rule__Class_def__Group__31941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Class_def__InitialDeclarationsAssignment_3_in_rule__Class_def__Group__3__Impl1968 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Class_def__Group__4__Impl_in_rule__Class_def__Group__41999 = new BitSet(new long[]{0x0000000000008010L});
    public static final BitSet FOLLOW_rule__Class_def__Group__5_in_rule__Class_def__Group__42002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Class_def__FeatureAssignment_4_in_rule__Class_def__Group__4__Impl2029 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Class_def__Group__5__Impl_in_rule__Class_def__Group__52060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Class_def__Group__5__Impl2088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Stmt__Group__0__Impl_in_rule__Stmt__Group__02131 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__Stmt__Group__1_in_rule__Stmt__Group__02134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Stmt__Alternatives_0_in_rule__Stmt__Group__0__Impl2161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Stmt__Group__1__Impl_in_rule__Stmt__Group__12191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__Stmt__Group__1__Impl2219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__Group__0__Impl_in_rule__Declaration__Group__02254 = new BitSet(new long[]{0x0000000000020010L});
    public static final BitSet FOLLOW_rule__Declaration__Group__1_in_rule__Declaration__Group__02257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__TypeAssignment_0_in_rule__Declaration__Group__0__Impl2284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__Group__1__Impl_in_rule__Declaration__Group__12314 = new BitSet(new long[]{0x0000000000020010L});
    public static final BitSet FOLLOW_rule__Declaration__Group__2_in_rule__Declaration__Group__12317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__TypeParameterAssignment_1_in_rule__Declaration__Group__1__Impl2344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__Group__2__Impl_in_rule__Declaration__Group__22375 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_rule__Declaration__Group__3_in_rule__Declaration__Group__22378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__NameAssignment_2_in_rule__Declaration__Group__2__Impl2405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__Group__3__Impl_in_rule__Declaration__Group__32435 = new BitSet(new long[]{0x0000000000100070L});
    public static final BitSet FOLLOW_rule__Declaration__Group__4_in_rule__Declaration__Group__32438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__Declaration__Group__3__Impl2466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__Group__4__Impl_in_rule__Declaration__Group__42497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Declaration__DefaultValueAssignment_4_in_rule__Declaration__Group__4__Impl2524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TypeParameter__Group__0__Impl_in_rule__TypeParameter__Group__02564 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__TypeParameter__Group__1_in_rule__TypeParameter__Group__02567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__TypeParameter__Group__0__Impl2595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TypeParameter__Group__1__Impl_in_rule__TypeParameter__Group__12626 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__TypeParameter__Group__2_in_rule__TypeParameter__Group__12629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TypeParameter__TypePAssignment_1_in_rule__TypeParameter__Group__1__Impl2656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__TypeParameter__Group__2__Impl_in_rule__TypeParameter__Group__22686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__TypeParameter__Group__2__Impl2714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__0__Impl_in_rule__Assignment__Group__02751 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_rule__Assignment__Group__1_in_rule__Assignment__Group__02754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__VarAssignment_0_in_rule__Assignment__Group__0__Impl2781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__1__Impl_in_rule__Assignment__Group__12811 = new BitSet(new long[]{0x0000000000100070L});
    public static final BitSet FOLLOW_rule__Assignment__Group__2_in_rule__Assignment__Group__12814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__Assignment__Group__1__Impl2842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__Group__2__Impl_in_rule__Assignment__Group__22873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Assignment__ExpAssignment_2_in_rule__Assignment__Group__2__Impl2900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Fully_qualified_name__Group__0__Impl_in_rule__Fully_qualified_name__Group__02936 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__Fully_qualified_name__Group__1_in_rule__Fully_qualified_name__Group__02939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Fully_qualified_name__Group__0__Impl2966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Fully_qualified_name__Group__1__Impl_in_rule__Fully_qualified_name__Group__12995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Fully_qualified_name__Group_1__0_in_rule__Fully_qualified_name__Group__1__Impl3022 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_rule__Fully_qualified_name__Group_1__0__Impl_in_rule__Fully_qualified_name__Group_1__03057 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Fully_qualified_name__Group_1__1_in_rule__Fully_qualified_name__Group_1__03060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__Fully_qualified_name__Group_1__0__Impl3088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Fully_qualified_name__Group_1__1__Impl_in_rule__Fully_qualified_name__Group_1__13119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Fully_qualified_name__Group_1__1__Impl3146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Constructor_call__Group__0__Impl_in_rule__Constructor_call__Group__03179 = new BitSet(new long[]{0x0000000000100070L});
    public static final BitSet FOLLOW_rule__Constructor_call__Group__1_in_rule__Constructor_call__Group__03182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Constructor_call__Group__0__Impl3210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Constructor_call__Group__1__Impl_in_rule__Constructor_call__Group__13241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Constructor_call__MethodAssignment_1_in_rule__Constructor_call__Group__1__Impl3268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MethodCall__Group__0__Impl_in_rule__MethodCall__Group__03302 = new BitSet(new long[]{0x0000000000220000L});
    public static final BitSet FOLLOW_rule__MethodCall__Group__1_in_rule__MethodCall__Group__03305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MethodCall__NameAssignment_0_in_rule__MethodCall__Group__0__Impl3332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MethodCall__Group__1__Impl_in_rule__MethodCall__Group__13362 = new BitSet(new long[]{0x0000000000220000L});
    public static final BitSet FOLLOW_rule__MethodCall__Group__2_in_rule__MethodCall__Group__13365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MethodCall__TypePAssignment_1_in_rule__MethodCall__Group__1__Impl3392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MethodCall__Group__2__Impl_in_rule__MethodCall__Group__23423 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_rule__MethodCall__Group__3_in_rule__MethodCall__Group__23426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__MethodCall__Group__2__Impl3454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MethodCall__Group__3__Impl_in_rule__MethodCall__Group__33485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__MethodCall__Group__3__Impl3513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group__0__Impl_in_rule__Method_def__Group__03552 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__Method_def__Group__1_in_rule__Method_def__Group__03555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__NameAssignment_0_in_rule__Method_def__Group__0__Impl3582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group__1__Impl_in_rule__Method_def__Group__13612 = new BitSet(new long[]{0x0000000000400010L});
    public static final BitSet FOLLOW_rule__Method_def__Group__2_in_rule__Method_def__Group__13615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Method_def__Group__1__Impl3643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group__2__Impl_in_rule__Method_def__Group__23674 = new BitSet(new long[]{0x0000000000400010L});
    public static final BitSet FOLLOW_rule__Method_def__Group__3_in_rule__Method_def__Group__23677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group_2__0_in_rule__Method_def__Group__2__Impl3704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group__3__Impl_in_rule__Method_def__Group__33735 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_rule__Method_def__Group__4_in_rule__Method_def__Group__33738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__Method_def__Group__3__Impl3766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group__4__Impl_in_rule__Method_def__Group__43797 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Method_def__Group__5_in_rule__Method_def__Group__43800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__Method_def__Group__4__Impl3828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group__5__Impl_in_rule__Method_def__Group__53859 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__Method_def__Group__6_in_rule__Method_def__Group__53862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__BodyAssignment_5_in_rule__Method_def__Group__5__Impl3889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group__6__Impl_in_rule__Method_def__Group__63919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Method_def__Group__6__Impl3947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group_2__0__Impl_in_rule__Method_def__Group_2__03992 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_rule__Method_def__Group_2__1_in_rule__Method_def__Group_2__03995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__ArgsAssignment_2_0_in_rule__Method_def__Group_2__0__Impl4022 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group_2__1__Impl_in_rule__Method_def__Group_2__14052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group_2_1__0_in_rule__Method_def__Group_2__1__Impl4079 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_rule__Method_def__Group_2_1__0__Impl_in_rule__Method_def__Group_2_1__04114 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Method_def__Group_2_1__1_in_rule__Method_def__Group_2_1__04117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__Method_def__Group_2_1__0__Impl4145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__Group_2_1__1__Impl_in_rule__Method_def__Group_2_1__14176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Method_def__ArgsAssignment_2_1_1_in_rule__Method_def__Group_2_1__1__Impl4203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Body__Group__0__Impl_in_rule__Body__Group__04237 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Body__Group__1_in_rule__Body__Group__04240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Body__Group__1__Impl_in_rule__Body__Group__14298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Body__StmtsAssignment_1_in_rule__Body__Group__1__Impl4325 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Argument__Group__0__Impl_in_rule__Argument__Group__04360 = new BitSet(new long[]{0x0000000000020010L});
    public static final BitSet FOLLOW_rule__Argument__Group__1_in_rule__Argument__Group__04363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__TypeAssignment_0_in_rule__Argument__Group__0__Impl4390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group__1__Impl_in_rule__Argument__Group__14420 = new BitSet(new long[]{0x0000000000020010L});
    public static final BitSet FOLLOW_rule__Argument__Group__2_in_rule__Argument__Group__14423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__TypePAssignment_1_in_rule__Argument__Group__1__Impl4450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group__2__Impl_in_rule__Argument__Group__24481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__NameAssignment_2_in_rule__Argument__Group__2__Impl4508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleimport__in_rule__Model__ImportsAssignment_04549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleclass_def_in_rule__Model__ClassesAssignment_14580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulefully_qualified_name_in_rule__Import___EntryAssignment_14611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Class_def__NameAssignment_14642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulestmt_in_rule__Class_def__InitialDeclarationsAssignment_34673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulefeature_in_rule__Class_def__FeatureAssignment_44704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Declaration__TypeAssignment_04735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruletypeParameter_in_rule__Declaration__TypeParameterAssignment_14766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Declaration__NameAssignment_24797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleexp_in_rule__Declaration__DefaultValueAssignment_44828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__TypeParameter__TypePAssignment_14859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulefully_qualified_name_in_rule__Assignment__VarAssignment_04890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleexp_in_rule__Assignment__ExpAssignment_24921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulemethodCall_in_rule__Constructor_call__MethodAssignment_14952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__MethodCall__NameAssignment_04983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruletypeParameter_in_rule__MethodCall__TypePAssignment_15014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Method_def__NameAssignment_05045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleargument_in_rule__Method_def__ArgsAssignment_2_05076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleargument_in_rule__Method_def__ArgsAssignment_2_1_15107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulebody_in_rule__Method_def__BodyAssignment_55138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulestmt_in_rule__Body__StmtsAssignment_15169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Argument__TypeAssignment_05200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruletypeParameter_in_rule__Argument__TypePAssignment_15231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Argument__NameAssignment_25262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Variable_name__NameAssignment5293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__String_val__ValueAssignment5324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rule__Int_val__ValueAssignment5355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Class_def__InitialDeclarationsAssignment_3_in_synpred9_InternalTTC_Java1968 = new BitSet(new long[]{0x0000000000000002L});

}
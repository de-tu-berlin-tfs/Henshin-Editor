package de.tub.tfs.henshin.tgg.interpreter.config.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import de.tub.tfs.henshin.tgg.interpreter.config.services.TggInterpreterConfigGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTggInterpreterConfigParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_NL", "RULE_JSSCRIPT", "RULE_ID", "RULE_WS", "RULE_ANY_OTHER", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "'AdditionalOptions'", "'='", "'>'", "','"
    };
    public static final int RULE_ID=6;
    public static final int RULE_STRING=10;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__14=14;
    public static final int RULE_JSSCRIPT=5;
    public static final int T__13=13;
    public static final int RULE_ANY_OTHER=8;
    public static final int RULE_NL=4;
    public static final int RULE_INT=9;
    public static final int RULE_WS=7;
    public static final int RULE_SL_COMMENT=12;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=11;

    // delegates
    // delegators


        public InternalTggInterpreterConfigParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalTggInterpreterConfigParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalTggInterpreterConfigParser.tokenNames; }
    public String getGrammarFileName() { return "../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g"; }



     	private TggInterpreterConfigGrammarAccess grammarAccess;
     	
        public InternalTggInterpreterConfigParser(TokenStream input, TggInterpreterConfigGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "TggInterpreterConfig";	
       	}
       	
       	@Override
       	protected TggInterpreterConfigGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleTggInterpreterConfig"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:67:1: entryRuleTggInterpreterConfig returns [EObject current=null] : iv_ruleTggInterpreterConfig= ruleTggInterpreterConfig EOF ;
    public final EObject entryRuleTggInterpreterConfig() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTggInterpreterConfig = null;


        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:68:2: (iv_ruleTggInterpreterConfig= ruleTggInterpreterConfig EOF )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:69:2: iv_ruleTggInterpreterConfig= ruleTggInterpreterConfig EOF
            {
             newCompositeNode(grammarAccess.getTggInterpreterConfigRule()); 
            pushFollow(FOLLOW_ruleTggInterpreterConfig_in_entryRuleTggInterpreterConfig75);
            iv_ruleTggInterpreterConfig=ruleTggInterpreterConfig();

            state._fsp--;

             current =iv_ruleTggInterpreterConfig; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTggInterpreterConfig85); 

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
    // $ANTLR end "entryRuleTggInterpreterConfig"


    // $ANTLR start "ruleTggInterpreterConfig"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:76:1: ruleTggInterpreterConfig returns [EObject current=null] : ( (this_NL_0= RULE_NL )? (otherlv_1= 'AdditionalOptions' otherlv_2= '=' ( (lv_options_3_0= ruleProcessingEntry ) )+ this_NL_4= RULE_NL )* ) ;
    public final EObject ruleTggInterpreterConfig() throws RecognitionException {
        EObject current = null;

        Token this_NL_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token this_NL_4=null;
        EObject lv_options_3_0 = null;


         enterRule(); 
            
        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:79:28: ( ( (this_NL_0= RULE_NL )? (otherlv_1= 'AdditionalOptions' otherlv_2= '=' ( (lv_options_3_0= ruleProcessingEntry ) )+ this_NL_4= RULE_NL )* ) )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:80:1: ( (this_NL_0= RULE_NL )? (otherlv_1= 'AdditionalOptions' otherlv_2= '=' ( (lv_options_3_0= ruleProcessingEntry ) )+ this_NL_4= RULE_NL )* )
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:80:1: ( (this_NL_0= RULE_NL )? (otherlv_1= 'AdditionalOptions' otherlv_2= '=' ( (lv_options_3_0= ruleProcessingEntry ) )+ this_NL_4= RULE_NL )* )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:80:2: (this_NL_0= RULE_NL )? (otherlv_1= 'AdditionalOptions' otherlv_2= '=' ( (lv_options_3_0= ruleProcessingEntry ) )+ this_NL_4= RULE_NL )*
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:80:2: (this_NL_0= RULE_NL )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_NL) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:80:3: this_NL_0= RULE_NL
                    {
                    this_NL_0=(Token)match(input,RULE_NL,FOLLOW_RULE_NL_in_ruleTggInterpreterConfig122); 
                     
                        newLeafNode(this_NL_0, grammarAccess.getTggInterpreterConfigAccess().getNLTerminalRuleCall_0()); 
                        

                    }
                    break;

            }

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:84:3: (otherlv_1= 'AdditionalOptions' otherlv_2= '=' ( (lv_options_3_0= ruleProcessingEntry ) )+ this_NL_4= RULE_NL )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==13) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:84:5: otherlv_1= 'AdditionalOptions' otherlv_2= '=' ( (lv_options_3_0= ruleProcessingEntry ) )+ this_NL_4= RULE_NL
            	    {
            	    otherlv_1=(Token)match(input,13,FOLLOW_13_in_ruleTggInterpreterConfig136); 

            	        	newLeafNode(otherlv_1, grammarAccess.getTggInterpreterConfigAccess().getAdditionalOptionsKeyword_1_0());
            	        
            	    otherlv_2=(Token)match(input,14,FOLLOW_14_in_ruleTggInterpreterConfig148); 

            	        	newLeafNode(otherlv_2, grammarAccess.getTggInterpreterConfigAccess().getEqualsSignKeyword_1_1());
            	        
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:92:1: ( (lv_options_3_0= ruleProcessingEntry ) )+
            	    int cnt2=0;
            	    loop2:
            	    do {
            	        int alt2=2;
            	        int LA2_0 = input.LA(1);

            	        if ( (LA2_0==RULE_NL) ) {
            	            int LA2_1 = input.LA(2);

            	            if ( (LA2_1==RULE_ID) ) {
            	                alt2=1;
            	            }


            	        }
            	        else if ( (LA2_0==RULE_ID) ) {
            	            alt2=1;
            	        }


            	        switch (alt2) {
            	    	case 1 :
            	    	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:93:1: (lv_options_3_0= ruleProcessingEntry )
            	    	    {
            	    	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:93:1: (lv_options_3_0= ruleProcessingEntry )
            	    	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:94:3: lv_options_3_0= ruleProcessingEntry
            	    	    {
            	    	     
            	    	    	        newCompositeNode(grammarAccess.getTggInterpreterConfigAccess().getOptionsProcessingEntryParserRuleCall_1_2_0()); 
            	    	    	    
            	    	    pushFollow(FOLLOW_ruleProcessingEntry_in_ruleTggInterpreterConfig169);
            	    	    lv_options_3_0=ruleProcessingEntry();

            	    	    state._fsp--;


            	    	    	        if (current==null) {
            	    	    	            current = createModelElementForParent(grammarAccess.getTggInterpreterConfigRule());
            	    	    	        }
            	    	           		add(
            	    	           			current, 
            	    	           			"options",
            	    	            		lv_options_3_0, 
            	    	            		"ProcessingEntry");
            	    	    	        afterParserOrEnumRuleCall();
            	    	    	    

            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt2 >= 1 ) break loop2;
            	                EarlyExitException eee =
            	                    new EarlyExitException(2, input);
            	                throw eee;
            	        }
            	        cnt2++;
            	    } while (true);

            	    this_NL_4=(Token)match(input,RULE_NL,FOLLOW_RULE_NL_in_ruleTggInterpreterConfig181); 
            	     
            	        newLeafNode(this_NL_4, grammarAccess.getTggInterpreterConfigAccess().getNLTerminalRuleCall_1_3()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


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
    // $ANTLR end "ruleTggInterpreterConfig"


    // $ANTLR start "entryRuleProcessingEntry"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:122:1: entryRuleProcessingEntry returns [EObject current=null] : iv_ruleProcessingEntry= ruleProcessingEntry EOF ;
    public final EObject entryRuleProcessingEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProcessingEntry = null;


        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:123:2: (iv_ruleProcessingEntry= ruleProcessingEntry EOF )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:124:2: iv_ruleProcessingEntry= ruleProcessingEntry EOF
            {
             newCompositeNode(grammarAccess.getProcessingEntryRule()); 
            pushFollow(FOLLOW_ruleProcessingEntry_in_entryRuleProcessingEntry218);
            iv_ruleProcessingEntry=ruleProcessingEntry();

            state._fsp--;

             current =iv_ruleProcessingEntry; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProcessingEntry228); 

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
    // $ANTLR end "entryRuleProcessingEntry"


    // $ANTLR start "ruleProcessingEntry"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:131:1: ruleProcessingEntry returns [EObject current=null] : ( (this_NL_0= RULE_NL )? ( (lv_key_1_0= rulekeyValue ) ) otherlv_2= '=' otherlv_3= '>' (this_NL_4= RULE_NL )? ( (lv_value_5_0= ruleScriptOrValue ) ) (this_NL_6= RULE_NL )? (otherlv_7= ',' )? ) ;
    public final EObject ruleProcessingEntry() throws RecognitionException {
        EObject current = null;

        Token this_NL_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token this_NL_4=null;
        Token this_NL_6=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_key_1_0 = null;

        AntlrDatatypeRuleToken lv_value_5_0 = null;


         enterRule(); 
            
        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:134:28: ( ( (this_NL_0= RULE_NL )? ( (lv_key_1_0= rulekeyValue ) ) otherlv_2= '=' otherlv_3= '>' (this_NL_4= RULE_NL )? ( (lv_value_5_0= ruleScriptOrValue ) ) (this_NL_6= RULE_NL )? (otherlv_7= ',' )? ) )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:135:1: ( (this_NL_0= RULE_NL )? ( (lv_key_1_0= rulekeyValue ) ) otherlv_2= '=' otherlv_3= '>' (this_NL_4= RULE_NL )? ( (lv_value_5_0= ruleScriptOrValue ) ) (this_NL_6= RULE_NL )? (otherlv_7= ',' )? )
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:135:1: ( (this_NL_0= RULE_NL )? ( (lv_key_1_0= rulekeyValue ) ) otherlv_2= '=' otherlv_3= '>' (this_NL_4= RULE_NL )? ( (lv_value_5_0= ruleScriptOrValue ) ) (this_NL_6= RULE_NL )? (otherlv_7= ',' )? )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:135:2: (this_NL_0= RULE_NL )? ( (lv_key_1_0= rulekeyValue ) ) otherlv_2= '=' otherlv_3= '>' (this_NL_4= RULE_NL )? ( (lv_value_5_0= ruleScriptOrValue ) ) (this_NL_6= RULE_NL )? (otherlv_7= ',' )?
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:135:2: (this_NL_0= RULE_NL )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_NL) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:135:3: this_NL_0= RULE_NL
                    {
                    this_NL_0=(Token)match(input,RULE_NL,FOLLOW_RULE_NL_in_ruleProcessingEntry265); 
                     
                        newLeafNode(this_NL_0, grammarAccess.getProcessingEntryAccess().getNLTerminalRuleCall_0()); 
                        

                    }
                    break;

            }

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:139:3: ( (lv_key_1_0= rulekeyValue ) )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:140:1: (lv_key_1_0= rulekeyValue )
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:140:1: (lv_key_1_0= rulekeyValue )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:141:3: lv_key_1_0= rulekeyValue
            {
             
            	        newCompositeNode(grammarAccess.getProcessingEntryAccess().getKeyKeyValueParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_rulekeyValue_in_ruleProcessingEntry287);
            lv_key_1_0=rulekeyValue();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getProcessingEntryRule());
            	        }
                   		set(
                   			current, 
                   			"key",
                    		lv_key_1_0, 
                    		"keyValue");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,14,FOLLOW_14_in_ruleProcessingEntry299); 

                	newLeafNode(otherlv_2, grammarAccess.getProcessingEntryAccess().getEqualsSignKeyword_2());
                
            otherlv_3=(Token)match(input,15,FOLLOW_15_in_ruleProcessingEntry311); 

                	newLeafNode(otherlv_3, grammarAccess.getProcessingEntryAccess().getGreaterThanSignKeyword_3());
                
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:165:1: (this_NL_4= RULE_NL )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_NL) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:165:2: this_NL_4= RULE_NL
                    {
                    this_NL_4=(Token)match(input,RULE_NL,FOLLOW_RULE_NL_in_ruleProcessingEntry323); 
                     
                        newLeafNode(this_NL_4, grammarAccess.getProcessingEntryAccess().getNLTerminalRuleCall_4()); 
                        

                    }
                    break;

            }

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:169:3: ( (lv_value_5_0= ruleScriptOrValue ) )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:170:1: (lv_value_5_0= ruleScriptOrValue )
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:170:1: (lv_value_5_0= ruleScriptOrValue )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:171:3: lv_value_5_0= ruleScriptOrValue
            {
             
            	        newCompositeNode(grammarAccess.getProcessingEntryAccess().getValueScriptOrValueParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleScriptOrValue_in_ruleProcessingEntry345);
            lv_value_5_0=ruleScriptOrValue();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getProcessingEntryRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_5_0, 
                    		"ScriptOrValue");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:187:2: (this_NL_6= RULE_NL )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_NL) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:187:3: this_NL_6= RULE_NL
                    {
                    this_NL_6=(Token)match(input,RULE_NL,FOLLOW_RULE_NL_in_ruleProcessingEntry357); 
                     
                        newLeafNode(this_NL_6, grammarAccess.getProcessingEntryAccess().getNLTerminalRuleCall_6()); 
                        

                    }
                    break;

            }

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:191:3: (otherlv_7= ',' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==16) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:191:5: otherlv_7= ','
                    {
                    otherlv_7=(Token)match(input,16,FOLLOW_16_in_ruleProcessingEntry371); 

                        	newLeafNode(otherlv_7, grammarAccess.getProcessingEntryAccess().getCommaKeyword_7());
                        

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
    // $ANTLR end "ruleProcessingEntry"


    // $ANTLR start "entryRuleScriptOrValue"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:203:1: entryRuleScriptOrValue returns [String current=null] : iv_ruleScriptOrValue= ruleScriptOrValue EOF ;
    public final String entryRuleScriptOrValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleScriptOrValue = null;


        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:204:2: (iv_ruleScriptOrValue= ruleScriptOrValue EOF )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:205:2: iv_ruleScriptOrValue= ruleScriptOrValue EOF
            {
             newCompositeNode(grammarAccess.getScriptOrValueRule()); 
            pushFollow(FOLLOW_ruleScriptOrValue_in_entryRuleScriptOrValue410);
            iv_ruleScriptOrValue=ruleScriptOrValue();

            state._fsp--;

             current =iv_ruleScriptOrValue.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleScriptOrValue421); 

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
    // $ANTLR end "entryRuleScriptOrValue"


    // $ANTLR start "ruleScriptOrValue"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:212:1: ruleScriptOrValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_JSSCRIPT_0= RULE_JSSCRIPT | this_value_1= rulevalue ) ;
    public final AntlrDatatypeRuleToken ruleScriptOrValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_JSSCRIPT_0=null;
        AntlrDatatypeRuleToken this_value_1 = null;


         enterRule(); 
            
        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:215:28: ( (this_JSSCRIPT_0= RULE_JSSCRIPT | this_value_1= rulevalue ) )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:216:1: (this_JSSCRIPT_0= RULE_JSSCRIPT | this_value_1= rulevalue )
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:216:1: (this_JSSCRIPT_0= RULE_JSSCRIPT | this_value_1= rulevalue )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_JSSCRIPT) ) {
                alt8=1;
            }
            else if ( (LA8_0==RULE_ID||(LA8_0>=14 && LA8_0<=15)) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:216:6: this_JSSCRIPT_0= RULE_JSSCRIPT
                    {
                    this_JSSCRIPT_0=(Token)match(input,RULE_JSSCRIPT,FOLLOW_RULE_JSSCRIPT_in_ruleScriptOrValue461); 

                    		current.merge(this_JSSCRIPT_0);
                        
                     
                        newLeafNode(this_JSSCRIPT_0, grammarAccess.getScriptOrValueAccess().getJSSCRIPTTerminalRuleCall_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:225:5: this_value_1= rulevalue
                    {
                     
                            newCompositeNode(grammarAccess.getScriptOrValueAccess().getValueParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_rulevalue_in_ruleScriptOrValue494);
                    this_value_1=rulevalue();

                    state._fsp--;


                    		current.merge(this_value_1);
                        
                     
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
    // $ANTLR end "ruleScriptOrValue"


    // $ANTLR start "entryRulekeyValue"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:243:1: entryRulekeyValue returns [String current=null] : iv_rulekeyValue= rulekeyValue EOF ;
    public final String entryRulekeyValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulekeyValue = null;


        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:244:2: (iv_rulekeyValue= rulekeyValue EOF )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:245:2: iv_rulekeyValue= rulekeyValue EOF
            {
             newCompositeNode(grammarAccess.getKeyValueRule()); 
            pushFollow(FOLLOW_rulekeyValue_in_entryRulekeyValue540);
            iv_rulekeyValue=rulekeyValue();

            state._fsp--;

             current =iv_rulekeyValue.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulekeyValue551); 

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
    // $ANTLR end "entryRulekeyValue"


    // $ANTLR start "rulekeyValue"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:252:1: rulekeyValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (this_ID_1= RULE_ID | this_WS_2= RULE_WS )* ) ;
    public final AntlrDatatypeRuleToken rulekeyValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token this_ID_1=null;
        Token this_WS_2=null;

         enterRule(); 
            
        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:255:28: ( (this_ID_0= RULE_ID (this_ID_1= RULE_ID | this_WS_2= RULE_WS )* ) )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:256:1: (this_ID_0= RULE_ID (this_ID_1= RULE_ID | this_WS_2= RULE_WS )* )
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:256:1: (this_ID_0= RULE_ID (this_ID_1= RULE_ID | this_WS_2= RULE_WS )* )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:256:6: this_ID_0= RULE_ID (this_ID_1= RULE_ID | this_WS_2= RULE_WS )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulekeyValue591); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getKeyValueAccess().getIDTerminalRuleCall_0()); 
                
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:263:1: (this_ID_1= RULE_ID | this_WS_2= RULE_WS )*
            loop9:
            do {
                int alt9=3;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==RULE_ID) ) {
                    alt9=1;
                }
                else if ( (LA9_0==RULE_WS) ) {
                    alt9=2;
                }


                switch (alt9) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:263:6: this_ID_1= RULE_ID
            	    {
            	    this_ID_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulekeyValue612); 

            	    		current.merge(this_ID_1);
            	        
            	     
            	        newLeafNode(this_ID_1, grammarAccess.getKeyValueAccess().getIDTerminalRuleCall_1_0()); 
            	        

            	    }
            	    break;
            	case 2 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:271:10: this_WS_2= RULE_WS
            	    {
            	    this_WS_2=(Token)match(input,RULE_WS,FOLLOW_RULE_WS_in_rulekeyValue638); 

            	    		current.merge(this_WS_2);
            	        
            	     
            	        newLeafNode(this_WS_2, grammarAccess.getKeyValueAccess().getWSTerminalRuleCall_1_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


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
    // $ANTLR end "rulekeyValue"


    // $ANTLR start "entryRulevalue"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:286:1: entryRulevalue returns [String current=null] : iv_rulevalue= rulevalue EOF ;
    public final String entryRulevalue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulevalue = null;


        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:287:2: (iv_rulevalue= rulevalue EOF )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:288:2: iv_rulevalue= rulevalue EOF
            {
             newCompositeNode(grammarAccess.getValueRule()); 
            pushFollow(FOLLOW_rulevalue_in_entryRulevalue686);
            iv_rulevalue=rulevalue();

            state._fsp--;

             current =iv_rulevalue.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulevalue697); 

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
    // $ANTLR end "entryRulevalue"


    // $ANTLR start "rulevalue"
    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:295:1: rulevalue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ID_0= RULE_ID | kw= '=' | kw= '>' ) (this_ID_3= RULE_ID | this_WS_4= RULE_WS | kw= '=' | kw= '>' )* ) ;
    public final AntlrDatatypeRuleToken rulevalue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_3=null;
        Token this_WS_4=null;

         enterRule(); 
            
        try {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:298:28: ( ( (this_ID_0= RULE_ID | kw= '=' | kw= '>' ) (this_ID_3= RULE_ID | this_WS_4= RULE_WS | kw= '=' | kw= '>' )* ) )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:299:1: ( (this_ID_0= RULE_ID | kw= '=' | kw= '>' ) (this_ID_3= RULE_ID | this_WS_4= RULE_WS | kw= '=' | kw= '>' )* )
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:299:1: ( (this_ID_0= RULE_ID | kw= '=' | kw= '>' ) (this_ID_3= RULE_ID | this_WS_4= RULE_WS | kw= '=' | kw= '>' )* )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:299:2: (this_ID_0= RULE_ID | kw= '=' | kw= '>' ) (this_ID_3= RULE_ID | this_WS_4= RULE_WS | kw= '=' | kw= '>' )*
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:299:2: (this_ID_0= RULE_ID | kw= '=' | kw= '>' )
            int alt10=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt10=1;
                }
                break;
            case 14:
                {
                alt10=2;
                }
                break;
            case 15:
                {
                alt10=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:299:7: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulevalue738); 

                    		current.merge(this_ID_0);
                        
                     
                        newLeafNode(this_ID_0, grammarAccess.getValueAccess().getIDTerminalRuleCall_0_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:308:2: kw= '='
                    {
                    kw=(Token)match(input,14,FOLLOW_14_in_rulevalue762); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getValueAccess().getEqualsSignKeyword_0_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:315:2: kw= '>'
                    {
                    kw=(Token)match(input,15,FOLLOW_15_in_rulevalue781); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getValueAccess().getGreaterThanSignKeyword_0_2()); 
                        

                    }
                    break;

            }

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:320:2: (this_ID_3= RULE_ID | this_WS_4= RULE_WS | kw= '=' | kw= '>' )*
            loop11:
            do {
                int alt11=5;
                alt11 = dfa11.predict(input);
                switch (alt11) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:320:7: this_ID_3= RULE_ID
            	    {
            	    this_ID_3=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulevalue798); 

            	    		current.merge(this_ID_3);
            	        
            	     
            	        newLeafNode(this_ID_3, grammarAccess.getValueAccess().getIDTerminalRuleCall_1_0()); 
            	        

            	    }
            	    break;
            	case 2 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:328:10: this_WS_4= RULE_WS
            	    {
            	    this_WS_4=(Token)match(input,RULE_WS,FOLLOW_RULE_WS_in_rulevalue824); 

            	    		current.merge(this_WS_4);
            	        
            	     
            	        newLeafNode(this_WS_4, grammarAccess.getValueAccess().getWSTerminalRuleCall_1_1()); 
            	        

            	    }
            	    break;
            	case 3 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:337:2: kw= '='
            	    {
            	    kw=(Token)match(input,14,FOLLOW_14_in_rulevalue848); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getValueAccess().getEqualsSignKeyword_1_2()); 
            	        

            	    }
            	    break;
            	case 4 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:344:2: kw= '>'
            	    {
            	    kw=(Token)match(input,15,FOLLOW_15_in_rulevalue867); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getValueAccess().getGreaterThanSignKeyword_1_3()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


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
    // $ANTLR end "rulevalue"

    // Delegated rules


    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA11_eotS =
        "\41\uffff";
    static final String DFA11_eofS =
        "\1\1\1\uffff\1\7\5\uffff\2\7\1\14\1\16\10\uffff\1\1\10\uffff\1"+
        "\40\3\uffff";
    static final String DFA11_minS =
        "\1\4\1\uffff\1\4\5\uffff\4\4\3\uffff\1\4\1\uffff\4\4\3\uffff\1"+
        "\4\1\uffff\4\4\3\uffff";
    static final String DFA11_maxS =
        "\1\20\1\uffff\1\20\5\uffff\4\20\3\uffff\1\20\1\uffff\4\20\3\uffff"+
        "\1\20\1\uffff\4\20\3\uffff";
    static final String DFA11_acceptS =
        "\1\uffff\1\5\1\uffff\1\2\1\3\1\4\2\1\4\uffff\3\1\1\uffff\1\1\4"+
        "\uffff\3\1\1\uffff\1\1\4\uffff\3\1";
    static final String DFA11_specialS =
        "\41\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\1\1\uffff\1\2\1\3\6\uffff\1\4\1\5\1\1",
            "",
            "\1\6\1\uffff\1\7\1\10\6\uffff\1\11\1\7\1\6",
            "",
            "",
            "",
            "",
            "",
            "\1\6\1\uffff\1\7\1\10\6\uffff\1\11\2\7",
            "\1\6\1\uffff\2\7\6\uffff\1\7\1\12\1\7",
            "\1\13\1\1\2\14\6\uffff\1\15\1\16\1\7",
            "\1\16\1\1\1\17\6\uffff\1\16\2\1\1\16",
            "",
            "",
            "",
            "\1\1\1\uffff\1\20\1\21\6\uffff\1\22\2\1",
            "",
            "\1\1\1\uffff\1\20\1\21\6\uffff\1\22\2\1",
            "\1\1\1\uffff\2\1\6\uffff\1\1\1\23\1\1",
            "\1\24\1\20\1\25\1\1\6\uffff\1\26\1\27\1\1",
            "\1\1\1\27\1\30\6\uffff\1\1\2\27\1\1",
            "",
            "",
            "",
            "\1\31\1\uffff\1\31\1\32\6\uffff\1\33\2\31",
            "",
            "\1\31\1\uffff\1\31\1\32\6\uffff\1\33\2\31",
            "\1\31\1\uffff\2\31\6\uffff\1\31\1\34\1\31",
            "\1\35\1\1\2\36\6\uffff\1\37\1\40\1\31",
            "\1\40\1\1\1\17\6\uffff\1\40\2\1\1\40",
            "",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "()* loopback of 320:2: (this_ID_3= RULE_ID | this_WS_4= RULE_WS | kw= '=' | kw= '>' )*";
        }
    }
 

    public static final BitSet FOLLOW_ruleTggInterpreterConfig_in_entryRuleTggInterpreterConfig75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTggInterpreterConfig85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NL_in_ruleTggInterpreterConfig122 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_13_in_ruleTggInterpreterConfig136 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleTggInterpreterConfig148 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_ruleProcessingEntry_in_ruleTggInterpreterConfig169 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_RULE_NL_in_ruleTggInterpreterConfig181 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_ruleProcessingEntry_in_entryRuleProcessingEntry218 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProcessingEntry228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_NL_in_ruleProcessingEntry265 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_rulekeyValue_in_ruleProcessingEntry287 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleProcessingEntry299 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleProcessingEntry311 = new BitSet(new long[]{0x000000000000C070L});
    public static final BitSet FOLLOW_RULE_NL_in_ruleProcessingEntry323 = new BitSet(new long[]{0x000000000000C070L});
    public static final BitSet FOLLOW_ruleScriptOrValue_in_ruleProcessingEntry345 = new BitSet(new long[]{0x0000000000010012L});
    public static final BitSet FOLLOW_RULE_NL_in_ruleProcessingEntry357 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_16_in_ruleProcessingEntry371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleScriptOrValue_in_entryRuleScriptOrValue410 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleScriptOrValue421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_JSSCRIPT_in_ruleScriptOrValue461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulevalue_in_ruleScriptOrValue494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulekeyValue_in_entryRulekeyValue540 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulekeyValue551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulekeyValue591 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_RULE_ID_in_rulekeyValue612 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_RULE_WS_in_rulekeyValue638 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_rulevalue_in_entryRulevalue686 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulevalue697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulevalue738 = new BitSet(new long[]{0x000000000000C0C2L});
    public static final BitSet FOLLOW_14_in_rulevalue762 = new BitSet(new long[]{0x000000000000C0C2L});
    public static final BitSet FOLLOW_15_in_rulevalue781 = new BitSet(new long[]{0x000000000000C0C2L});
    public static final BitSet FOLLOW_RULE_ID_in_rulevalue798 = new BitSet(new long[]{0x000000000000C0C2L});
    public static final BitSet FOLLOW_RULE_WS_in_rulevalue824 = new BitSet(new long[]{0x000000000000C0C2L});
    public static final BitSet FOLLOW_14_in_rulevalue848 = new BitSet(new long[]{0x000000000000C0C2L});
    public static final BitSet FOLLOW_15_in_rulevalue867 = new BitSet(new long[]{0x000000000000C0C2L});

}
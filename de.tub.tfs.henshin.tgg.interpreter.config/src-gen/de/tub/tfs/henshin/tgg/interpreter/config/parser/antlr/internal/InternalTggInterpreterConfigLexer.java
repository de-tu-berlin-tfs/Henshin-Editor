package de.tub.tfs.henshin.tgg.interpreter.config.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTggInterpreterConfigLexer extends Lexer {
    public static final int RULE_ID=6;
    public static final int RULE_STRING=10;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_JSSCRIPT=5;
    public static final int RULE_ANY_OTHER=8;
    public static final int RULE_NL=4;
    public static final int RULE_INT=9;
    public static final int RULE_WS=7;
    public static final int RULE_SL_COMMENT=12;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=11;

    // delegates
    // delegators

    public InternalTggInterpreterConfigLexer() {;} 
    public InternalTggInterpreterConfigLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalTggInterpreterConfigLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g"; }

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:11:7: ( 'AdditionalOptions' )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:11:9: 'AdditionalOptions'
            {
            match("AdditionalOptions"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:12:7: ( '=' )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:12:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:13:7: ( '>' )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:13:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:14:7: ( ',' )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:14:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "RULE_JSSCRIPT"
    public final void mRULE_JSSCRIPT() throws RecognitionException {
        try {
            int _type = RULE_JSSCRIPT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:356:15: ( '{*' ( options {greedy=false; } : . )* '*}' )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:356:17: '{*' ( options {greedy=false; } : . )* '*}'
            {
            match("{*"); 

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:356:22: ( options {greedy=false; } : . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='*') ) {
                    int LA1_1 = input.LA(2);

                    if ( (LA1_1=='}') ) {
                        alt1=2;
                    }
                    else if ( ((LA1_1>='\u0000' && LA1_1<='|')||(LA1_1>='~' && LA1_1<='\uFFFF')) ) {
                        alt1=1;
                    }


                }
                else if ( ((LA1_0>='\u0000' && LA1_0<=')')||(LA1_0>='+' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:356:50: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match("*}"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_JSSCRIPT"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:358:9: (~ ( ( '\\r' | '\\n' | ' ' | '\\t' | '=' | '>' ) ) (~ ( ( '\\r' | '\\n' | ' ' | '\\t' | '=' ) ) )* )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:358:11: ~ ( ( '\\r' | '\\n' | ' ' | '\\t' | '=' | '>' ) ) (~ ( ( '\\r' | '\\n' | ' ' | '\\t' | '=' ) ) )*
            {
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='\b')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\u001F')||(input.LA(1)>='!' && input.LA(1)<='<')||(input.LA(1)>='?' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:358:43: (~ ( ( '\\r' | '\\n' | ' ' | '\\t' | '=' ) ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='\b')||(LA2_0>='\u000B' && LA2_0<='\f')||(LA2_0>='\u000E' && LA2_0<='\u001F')||(LA2_0>='!' && LA2_0<='<')||(LA2_0>='>' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:358:43: ~ ( ( '\\r' | '\\n' | ' ' | '\\t' | '=' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\b')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\u001F')||(input.LA(1)>='!' && input.LA(1)<='<')||(input.LA(1)>='>' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_NL"
    public final void mRULE_NL() throws RecognitionException {
        try {
            int _type = RULE_NL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:360:9: ( ( '\\r' | '\\n' )+ )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:360:11: ( '\\r' | '\\n' )+
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:360:11: ( '\\r' | '\\n' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\n'||LA3_0=='\r') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:
            	    {
            	    if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NL"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:362:9: ( ( ' ' | '\\t' )+ )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:362:11: ( ' ' | '\\t' )+
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:362:11: ( ' ' | '\\t' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='\t'||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:364:16: ( . )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:364:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:366:10: ( ( '0' .. '9' )+ )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:366:12: ( '0' .. '9' )+
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:366:12: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:366:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:13: ( ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:15: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:15: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='\"') ) {
                alt8=1;
            }
            else if ( (LA8_0=='\'') ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:16: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:20: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop6:
                    do {
                        int alt6=3;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0=='\\') ) {
                            alt6=1;
                        }
                        else if ( ((LA6_0>='\u0000' && LA6_0<='!')||(LA6_0>='#' && LA6_0<='[')||(LA6_0>=']' && LA6_0<='\uFFFF')) ) {
                            alt6=2;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:21: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' )
                    	    {
                    	    match('\\'); 
                    	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:66: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:86: '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:91: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop7:
                    do {
                        int alt7=3;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0=='\\') ) {
                            alt7=1;
                        }
                        else if ( ((LA7_0>='\u0000' && LA7_0<='&')||(LA7_0>='(' && LA7_0<='[')||(LA7_0>=']' && LA7_0<='\uFFFF')) ) {
                            alt7=2;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:92: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' )
                    	    {
                    	    match('\\'); 
                    	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;
                    	case 2 :
                    	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:368:137: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:370:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:370:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:370:24: ( options {greedy=false; } : . )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='*') ) {
                    int LA9_1 = input.LA(2);

                    if ( (LA9_1=='/') ) {
                        alt9=2;
                    }
                    else if ( ((LA9_1>='\u0000' && LA9_1<='.')||(LA9_1>='0' && LA9_1<='\uFFFF')) ) {
                        alt9=1;
                    }


                }
                else if ( ((LA9_0>='\u0000' && LA9_0<=')')||(LA9_0>='+' && LA9_0<='\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:370:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:372:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:372:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:372:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\f')||(LA10_0>='\u000E' && LA10_0<='\uFFFF')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:372:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:372:40: ( ( '\\r' )? '\\n' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='\n'||LA12_0=='\r') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:372:41: ( '\\r' )? '\\n'
                    {
                    // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:372:41: ( '\\r' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='\r') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:372:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    public void mTokens() throws RecognitionException {
        // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:8: ( T__13 | T__14 | T__15 | T__16 | RULE_JSSCRIPT | RULE_ID | RULE_NL | RULE_WS | RULE_ANY_OTHER | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT )
        int alt13=13;
        alt13 = dfa13.predict(input);
        switch (alt13) {
            case 1 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:10: T__13
                {
                mT__13(); 

                }
                break;
            case 2 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:16: T__14
                {
                mT__14(); 

                }
                break;
            case 3 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:22: T__15
                {
                mT__15(); 

                }
                break;
            case 4 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:28: T__16
                {
                mT__16(); 

                }
                break;
            case 5 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:34: RULE_JSSCRIPT
                {
                mRULE_JSSCRIPT(); 

                }
                break;
            case 6 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:48: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 7 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:56: RULE_NL
                {
                mRULE_NL(); 

                }
                break;
            case 8 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:64: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 9 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:72: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;
            case 10 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:87: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 11 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:96: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 12 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:108: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 13 :
                // ../de.tub.tfs.henshin.tgg.interpreter.config/src-gen/de/tub/tfs/henshin/tgg/interpreter/config/parser/antlr/internal/InternalTggInterpreterConfig.g:1:124: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;

        }

    }


    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA13_eotS =
        "\1\uffff\1\16\2\uffff\1\21\2\16\2\uffff\3\16\1\uffff\1\16\4\uffff"+
        "\2\16\2\uffff\2\16\2\uffff\2\16\1\uffff\5\16\1\uffff\4\16\1\uffff"+
        "\1\16\1\uffff\1\16\1\42\15\16\1\72\1\uffff";
    static final String DFA13_eofS =
        "\73\uffff";
    static final String DFA13_minS =
        "\1\0\1\144\2\uffff\1\0\1\52\1\60\2\uffff\2\0\1\52\1\uffff\1\144"+
        "\4\uffff\1\0\1\60\2\uffff\1\42\1\0\2\uffff\1\42\1\0\1\uffff\2\0"+
        "\1\151\2\0\1\uffff\4\0\1\uffff\1\0\1\uffff\1\164\2\0\1\151\1\157"+
        "\1\156\1\141\1\154\1\117\1\160\1\164\1\151\1\157\1\156\1\163\1\0"+
        "\1\uffff";
    static final String DFA13_maxS =
        "\1\uffff\1\144\2\uffff\1\uffff\1\52\1\71\2\uffff\2\uffff\1\57\1"+
        "\uffff\1\144\4\uffff\1\uffff\1\71\2\uffff\1\165\1\uffff\2\uffff"+
        "\1\165\1\uffff\1\uffff\2\uffff\1\151\2\uffff\1\uffff\4\uffff\1\uffff"+
        "\1\uffff\1\uffff\1\164\2\uffff\1\151\1\157\1\156\1\141\1\154\1\117"+
        "\1\160\1\164\1\151\1\157\1\156\1\163\1\uffff\1\uffff";
    static final String DFA13_acceptS =
        "\2\uffff\1\2\1\3\3\uffff\1\7\1\10\3\uffff\1\6\1\uffff\1\6\1\2\1"+
        "\3\1\4\2\uffff\1\7\1\10\2\uffff\1\6\1\13\2\uffff\1\6\5\uffff\1\5"+
        "\4\uffff\1\14\1\uffff\1\15\20\uffff\1\1";
    static final String DFA13_specialS =
        "\1\5\3\uffff\1\14\4\uffff\1\22\1\15\7\uffff\1\0\4\uffff\1\4\3\uffff"+
        "\1\7\1\uffff\1\16\1\2\1\uffff\1\13\1\21\1\uffff\1\17\1\10\1\20\1"+
        "\1\1\uffff\1\12\2\uffff\1\6\1\3\14\uffff\1\11\1\uffff}>";
    static final String[] DFA13_transitionS = {
            "\11\14\1\10\1\7\2\14\1\7\22\14\1\10\1\14\1\11\4\14\1\12\4\14"+
            "\1\4\2\14\1\13\12\6\3\14\1\2\1\3\2\14\1\1\71\14\1\5\uff84\14",
            "\1\15",
            "",
            "",
            "\11\16\2\uffff\2\16\1\uffff\22\16\1\uffff\34\16\1\uffff\uffc2"+
            "\16",
            "\1\22",
            "\12\23",
            "",
            "",
            "\11\27\2\31\2\27\1\31\22\27\1\31\1\27\1\30\32\27\1\31\36\27"+
            "\1\26\uffa3\27",
            "\11\33\2\31\2\33\1\31\22\33\1\31\6\33\1\34\25\33\1\31\36\33"+
            "\1\32\uffa3\33",
            "\1\35\4\uffff\1\36",
            "",
            "\1\37",
            "",
            "",
            "",
            "",
            "\11\41\2\42\2\41\1\42\22\41\1\42\11\41\1\40\22\41\1\42\uffc2"+
            "\41",
            "\12\23",
            "",
            "",
            "\1\43\4\uffff\1\43\64\uffff\1\43\5\uffff\1\43\3\uffff\1\43"+
            "\7\uffff\1\43\3\uffff\1\43\1\uffff\2\43",
            "\11\27\2\31\2\27\1\31\22\27\1\31\1\27\1\30\32\27\1\31\36\27"+
            "\1\26\uffa3\27",
            "",
            "",
            "\1\44\4\uffff\1\44\64\uffff\1\44\5\uffff\1\44\3\uffff\1\44"+
            "\7\uffff\1\44\3\uffff\1\44\1\uffff\2\44",
            "\11\33\2\31\2\33\1\31\22\33\1\31\6\33\1\34\25\33\1\31\36\33"+
            "\1\32\uffa3\33",
            "",
            "\11\46\2\47\2\46\1\47\22\46\1\47\11\46\1\45\22\46\1\47\uffc2"+
            "\46",
            "\11\50\2\51\2\50\1\51\22\50\1\51\34\50\1\51\uffc2\50",
            "\1\52",
            "\11\41\2\42\2\41\1\42\22\41\1\42\11\41\1\40\22\41\1\42\77"+
            "\41\1\53\uff82\41",
            "\11\41\2\42\2\41\1\42\22\41\1\42\11\41\1\40\22\41\1\42\uffc2"+
            "\41",
            "",
            "\11\27\2\31\2\27\1\31\22\27\1\31\1\27\1\30\32\27\1\31\36\27"+
            "\1\26\uffa3\27",
            "\11\33\2\31\2\33\1\31\22\33\1\31\6\33\1\34\25\33\1\31\36\33"+
            "\1\32\uffa3\33",
            "\11\46\2\47\2\46\1\47\22\46\1\47\11\46\1\45\4\46\1\54\15\46"+
            "\1\47\uffc2\46",
            "\11\46\2\47\2\46\1\47\22\46\1\47\11\46\1\45\22\46\1\47\uffc2"+
            "\46",
            "",
            "\11\50\2\51\2\50\1\51\22\50\1\51\34\50\1\51\uffc2\50",
            "",
            "\1\55",
            "\11\41\2\uffff\2\41\1\uffff\22\41\1\uffff\11\41\1\40\22\41"+
            "\1\uffff\uffc2\41",
            "\11\46\2\47\2\46\1\47\22\46\1\47\11\46\1\45\22\46\1\47\uffc2"+
            "\46",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\11\16\2\uffff\2\16\1\uffff\22\16\1\uffff\34\16\1\uffff\uffc2"+
            "\16",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__13 | T__14 | T__15 | T__16 | RULE_JSSCRIPT | RULE_ID | RULE_NL | RULE_WS | RULE_ANY_OTHER | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA13_18 = input.LA(1);

                        s = -1;
                        if ( (LA13_18=='*') ) {s = 32;}

                        else if ( ((LA13_18>='\u0000' && LA13_18<='\b')||(LA13_18>='\u000B' && LA13_18<='\f')||(LA13_18>='\u000E' && LA13_18<='\u001F')||(LA13_18>='!' && LA13_18<=')')||(LA13_18>='+' && LA13_18<='<')||(LA13_18>='>' && LA13_18<='\uFFFF')) ) {s = 33;}

                        else if ( ((LA13_18>='\t' && LA13_18<='\n')||LA13_18=='\r'||LA13_18==' '||LA13_18=='=') ) {s = 34;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA13_38 = input.LA(1);

                        s = -1;
                        if ( (LA13_38=='*') ) {s = 37;}

                        else if ( ((LA13_38>='\u0000' && LA13_38<='\b')||(LA13_38>='\u000B' && LA13_38<='\f')||(LA13_38>='\u000E' && LA13_38<='\u001F')||(LA13_38>='!' && LA13_38<=')')||(LA13_38>='+' && LA13_38<='<')||(LA13_38>='>' && LA13_38<='\uFFFF')) ) {s = 38;}

                        else if ( ((LA13_38>='\t' && LA13_38<='\n')||LA13_38=='\r'||LA13_38==' '||LA13_38=='=') ) {s = 39;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA13_30 = input.LA(1);

                        s = -1;
                        if ( ((LA13_30>='\u0000' && LA13_30<='\b')||(LA13_30>='\u000B' && LA13_30<='\f')||(LA13_30>='\u000E' && LA13_30<='\u001F')||(LA13_30>='!' && LA13_30<='<')||(LA13_30>='>' && LA13_30<='\uFFFF')) ) {s = 40;}

                        else if ( ((LA13_30>='\t' && LA13_30<='\n')||LA13_30=='\r'||LA13_30==' '||LA13_30=='=') ) {s = 41;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA13_44 = input.LA(1);

                        s = -1;
                        if ( (LA13_44=='*') ) {s = 37;}

                        else if ( ((LA13_44>='\u0000' && LA13_44<='\b')||(LA13_44>='\u000B' && LA13_44<='\f')||(LA13_44>='\u000E' && LA13_44<='\u001F')||(LA13_44>='!' && LA13_44<=')')||(LA13_44>='+' && LA13_44<='<')||(LA13_44>='>' && LA13_44<='\uFFFF')) ) {s = 38;}

                        else if ( ((LA13_44>='\t' && LA13_44<='\n')||LA13_44=='\r'||LA13_44==' '||LA13_44=='=') ) {s = 39;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA13_23 = input.LA(1);

                        s = -1;
                        if ( (LA13_23=='\"') ) {s = 24;}

                        else if ( (LA13_23=='\\') ) {s = 22;}

                        else if ( ((LA13_23>='\u0000' && LA13_23<='\b')||(LA13_23>='\u000B' && LA13_23<='\f')||(LA13_23>='\u000E' && LA13_23<='\u001F')||LA13_23=='!'||(LA13_23>='#' && LA13_23<='<')||(LA13_23>='>' && LA13_23<='[')||(LA13_23>=']' && LA13_23<='\uFFFF')) ) {s = 23;}

                        else if ( ((LA13_23>='\t' && LA13_23<='\n')||LA13_23=='\r'||LA13_23==' '||LA13_23=='=') ) {s = 25;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA13_0 = input.LA(1);

                        s = -1;
                        if ( (LA13_0=='A') ) {s = 1;}

                        else if ( (LA13_0=='=') ) {s = 2;}

                        else if ( (LA13_0=='>') ) {s = 3;}

                        else if ( (LA13_0==',') ) {s = 4;}

                        else if ( (LA13_0=='{') ) {s = 5;}

                        else if ( ((LA13_0>='0' && LA13_0<='9')) ) {s = 6;}

                        else if ( (LA13_0=='\n'||LA13_0=='\r') ) {s = 7;}

                        else if ( (LA13_0=='\t'||LA13_0==' ') ) {s = 8;}

                        else if ( (LA13_0=='\"') ) {s = 9;}

                        else if ( (LA13_0=='\'') ) {s = 10;}

                        else if ( (LA13_0=='/') ) {s = 11;}

                        else if ( ((LA13_0>='\u0000' && LA13_0<='\b')||(LA13_0>='\u000B' && LA13_0<='\f')||(LA13_0>='\u000E' && LA13_0<='\u001F')||LA13_0=='!'||(LA13_0>='#' && LA13_0<='&')||(LA13_0>='(' && LA13_0<='+')||(LA13_0>='-' && LA13_0<='.')||(LA13_0>=':' && LA13_0<='<')||(LA13_0>='?' && LA13_0<='@')||(LA13_0>='B' && LA13_0<='z')||(LA13_0>='|' && LA13_0<='\uFFFF')) ) {s = 12;}

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA13_43 = input.LA(1);

                        s = -1;
                        if ( (LA13_43=='*') ) {s = 32;}

                        else if ( ((LA13_43>='\u0000' && LA13_43<='\b')||(LA13_43>='\u000B' && LA13_43<='\f')||(LA13_43>='\u000E' && LA13_43<='\u001F')||(LA13_43>='!' && LA13_43<=')')||(LA13_43>='+' && LA13_43<='<')||(LA13_43>='>' && LA13_43<='\uFFFF')) ) {s = 33;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA13_27 = input.LA(1);

                        s = -1;
                        if ( (LA13_27=='\'') ) {s = 28;}

                        else if ( (LA13_27=='\\') ) {s = 26;}

                        else if ( ((LA13_27>='\u0000' && LA13_27<='\b')||(LA13_27>='\u000B' && LA13_27<='\f')||(LA13_27>='\u000E' && LA13_27<='\u001F')||(LA13_27>='!' && LA13_27<='&')||(LA13_27>='(' && LA13_27<='<')||(LA13_27>='>' && LA13_27<='[')||(LA13_27>=']' && LA13_27<='\uFFFF')) ) {s = 27;}

                        else if ( ((LA13_27>='\t' && LA13_27<='\n')||LA13_27=='\r'||LA13_27==' '||LA13_27=='=') ) {s = 25;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA13_36 = input.LA(1);

                        s = -1;
                        if ( (LA13_36=='\'') ) {s = 28;}

                        else if ( (LA13_36=='\\') ) {s = 26;}

                        else if ( ((LA13_36>='\u0000' && LA13_36<='\b')||(LA13_36>='\u000B' && LA13_36<='\f')||(LA13_36>='\u000E' && LA13_36<='\u001F')||(LA13_36>='!' && LA13_36<='&')||(LA13_36>='(' && LA13_36<='<')||(LA13_36>='>' && LA13_36<='[')||(LA13_36>=']' && LA13_36<='\uFFFF')) ) {s = 27;}

                        else if ( ((LA13_36>='\t' && LA13_36<='\n')||LA13_36=='\r'||LA13_36==' '||LA13_36=='=') ) {s = 25;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA13_57 = input.LA(1);

                        s = -1;
                        if ( ((LA13_57>='\u0000' && LA13_57<='\b')||(LA13_57>='\u000B' && LA13_57<='\f')||(LA13_57>='\u000E' && LA13_57<='\u001F')||(LA13_57>='!' && LA13_57<='<')||(LA13_57>='>' && LA13_57<='\uFFFF')) ) {s = 14;}

                        else s = 58;

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA13_40 = input.LA(1);

                        s = -1;
                        if ( ((LA13_40>='\t' && LA13_40<='\n')||LA13_40=='\r'||LA13_40==' '||LA13_40=='=') ) {s = 41;}

                        else if ( ((LA13_40>='\u0000' && LA13_40<='\b')||(LA13_40>='\u000B' && LA13_40<='\f')||(LA13_40>='\u000E' && LA13_40<='\u001F')||(LA13_40>='!' && LA13_40<='<')||(LA13_40>='>' && LA13_40<='\uFFFF')) ) {s = 40;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA13_32 = input.LA(1);

                        s = -1;
                        if ( (LA13_32=='}') ) {s = 43;}

                        else if ( (LA13_32=='*') ) {s = 32;}

                        else if ( ((LA13_32>='\u0000' && LA13_32<='\b')||(LA13_32>='\u000B' && LA13_32<='\f')||(LA13_32>='\u000E' && LA13_32<='\u001F')||(LA13_32>='!' && LA13_32<=')')||(LA13_32>='+' && LA13_32<='<')||(LA13_32>='>' && LA13_32<='|')||(LA13_32>='~' && LA13_32<='\uFFFF')) ) {s = 33;}

                        else if ( ((LA13_32>='\t' && LA13_32<='\n')||LA13_32=='\r'||LA13_32==' '||LA13_32=='=') ) {s = 34;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA13_4 = input.LA(1);

                        s = -1;
                        if ( ((LA13_4>='\u0000' && LA13_4<='\b')||(LA13_4>='\u000B' && LA13_4<='\f')||(LA13_4>='\u000E' && LA13_4<='\u001F')||(LA13_4>='!' && LA13_4<='<')||(LA13_4>='>' && LA13_4<='\uFFFF')) ) {s = 14;}

                        else s = 17;

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA13_10 = input.LA(1);

                        s = -1;
                        if ( (LA13_10=='\\') ) {s = 26;}

                        else if ( ((LA13_10>='\u0000' && LA13_10<='\b')||(LA13_10>='\u000B' && LA13_10<='\f')||(LA13_10>='\u000E' && LA13_10<='\u001F')||(LA13_10>='!' && LA13_10<='&')||(LA13_10>='(' && LA13_10<='<')||(LA13_10>='>' && LA13_10<='[')||(LA13_10>=']' && LA13_10<='\uFFFF')) ) {s = 27;}

                        else if ( (LA13_10=='\'') ) {s = 28;}

                        else if ( ((LA13_10>='\t' && LA13_10<='\n')||LA13_10=='\r'||LA13_10==' '||LA13_10=='=') ) {s = 25;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA13_29 = input.LA(1);

                        s = -1;
                        if ( (LA13_29=='*') ) {s = 37;}

                        else if ( ((LA13_29>='\u0000' && LA13_29<='\b')||(LA13_29>='\u000B' && LA13_29<='\f')||(LA13_29>='\u000E' && LA13_29<='\u001F')||(LA13_29>='!' && LA13_29<=')')||(LA13_29>='+' && LA13_29<='<')||(LA13_29>='>' && LA13_29<='\uFFFF')) ) {s = 38;}

                        else if ( ((LA13_29>='\t' && LA13_29<='\n')||LA13_29=='\r'||LA13_29==' '||LA13_29=='=') ) {s = 39;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA13_35 = input.LA(1);

                        s = -1;
                        if ( (LA13_35=='\"') ) {s = 24;}

                        else if ( (LA13_35=='\\') ) {s = 22;}

                        else if ( ((LA13_35>='\u0000' && LA13_35<='\b')||(LA13_35>='\u000B' && LA13_35<='\f')||(LA13_35>='\u000E' && LA13_35<='\u001F')||LA13_35=='!'||(LA13_35>='#' && LA13_35<='<')||(LA13_35>='>' && LA13_35<='[')||(LA13_35>=']' && LA13_35<='\uFFFF')) ) {s = 23;}

                        else if ( ((LA13_35>='\t' && LA13_35<='\n')||LA13_35=='\r'||LA13_35==' '||LA13_35=='=') ) {s = 25;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA13_37 = input.LA(1);

                        s = -1;
                        if ( (LA13_37=='/') ) {s = 44;}

                        else if ( (LA13_37=='*') ) {s = 37;}

                        else if ( ((LA13_37>='\u0000' && LA13_37<='\b')||(LA13_37>='\u000B' && LA13_37<='\f')||(LA13_37>='\u000E' && LA13_37<='\u001F')||(LA13_37>='!' && LA13_37<=')')||(LA13_37>='+' && LA13_37<='.')||(LA13_37>='0' && LA13_37<='<')||(LA13_37>='>' && LA13_37<='\uFFFF')) ) {s = 38;}

                        else if ( ((LA13_37>='\t' && LA13_37<='\n')||LA13_37=='\r'||LA13_37==' '||LA13_37=='=') ) {s = 39;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA13_33 = input.LA(1);

                        s = -1;
                        if ( (LA13_33=='*') ) {s = 32;}

                        else if ( ((LA13_33>='\u0000' && LA13_33<='\b')||(LA13_33>='\u000B' && LA13_33<='\f')||(LA13_33>='\u000E' && LA13_33<='\u001F')||(LA13_33>='!' && LA13_33<=')')||(LA13_33>='+' && LA13_33<='<')||(LA13_33>='>' && LA13_33<='\uFFFF')) ) {s = 33;}

                        else if ( ((LA13_33>='\t' && LA13_33<='\n')||LA13_33=='\r'||LA13_33==' '||LA13_33=='=') ) {s = 34;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA13_9 = input.LA(1);

                        s = -1;
                        if ( (LA13_9=='\\') ) {s = 22;}

                        else if ( ((LA13_9>='\u0000' && LA13_9<='\b')||(LA13_9>='\u000B' && LA13_9<='\f')||(LA13_9>='\u000E' && LA13_9<='\u001F')||LA13_9=='!'||(LA13_9>='#' && LA13_9<='<')||(LA13_9>='>' && LA13_9<='[')||(LA13_9>=']' && LA13_9<='\uFFFF')) ) {s = 23;}

                        else if ( (LA13_9=='\"') ) {s = 24;}

                        else if ( ((LA13_9>='\t' && LA13_9<='\n')||LA13_9=='\r'||LA13_9==' '||LA13_9=='=') ) {s = 25;}

                        else s = 14;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 13, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}
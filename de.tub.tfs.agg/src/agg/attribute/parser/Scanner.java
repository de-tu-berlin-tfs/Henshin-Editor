package agg.attribute.parser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Scanner {

	public static final int EOF = -1;

	public static final int WORD = 0;

	public static final int WHITE = 1;

	public static final int KEY = 2;

	public static final int OTHER = 3;

	public static final int EXIST = 4;

	/**
	 */
	protected Hashtable<String, Integer> fgKeys = new Hashtable<String, Integer>();

	/**
	 */
	protected StringBuffer fBuffer = new StringBuffer();

	/**
	 */
	protected String fDoc;

	/**
	 */
	protected int fPos;

	/**
	 */
	protected int fEnd;

	/**
	 */
	protected int fStartToken;

	/**
	 */
	protected boolean fEofSeen = false;

	private List<String> notValidName = new ArrayList<String>();

	/**
	 */
	private String[] fgKeywords = { "abstract", "boolean", "break", "byte",
			"case", "catch", "char", "class", "continue", "default", "do",
			"double", "else", "extends", "false", "final", "finally", "float",
			"for", "if", "implements", "import", "instanceof", "int",
			"interface", "long", "native", "new", "null", "package", "private",
			"protected", "public", "return", "short", "static", "super",
			"switch", "synchronized", "this", "throw", "throws", "transient",
			"true", "try", "void", "volatile", "while" };

	public Scanner() {
		initialize();
	}

	/**
	 * Returns the ending location of the current token in the document.
	 * 
	 * @return int
	 */
	public final int getLength() {
		return this.fPos - this.fStartToken;
	}

	/**
	 * Initialize the lookup table.
	 */
	void initialize() {
		Integer k = new Integer(KEY);
		for (int i = 0; i < this.fgKeywords.length; i++)
			this.fgKeys.put(this.fgKeywords[i], k);
	}

	/**
	 * Returns the starting location of the current token in the document.
	 * 
	 * @return int
	 */
	public final int getStartOffset() {
		return this.fStartToken;
	}

	/**
	 * Returns the next lexical token in the document.
	 * 
	 * @return int
	 */
	private int nextToken() {
		int c;
		this.fStartToken = this.fPos;
		while (true) {
			switch (c = read()) {
			case EOF:
				return EOF;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '!':
			case '"':
//			case '§':
			case '$':
			case '%':
			case '&':
			case '/':
			case '(':
			case ')':
			case '{':
			case '[':
			case ']':
			case '}':
			case '?':
			case '\'':
			case '`':
//			case '´':
			case '*':
			case '+':
			case '~':
			case '#':
			case '\\':
			case ',':
			case ';':
			case '.':
			case ':':
			case '-':
			case '^':
//			case '°':
			case '>':
			case '<':
			case '|':
			case '=':
//			case 'ä':
//			case 'ö':
//			case 'ü':
//			case 'Ä':
//			case 'Ö':
//			case 'Ü':	
//			case 'ß':
			case '_':
			case '@':
//			case '€':
				return OTHER;
			default:
				if (Character.isWhitespace((char) c)) {
					do {
						c = read();
					} while (Character.isWhitespace((char) c));
					unread(c);
					return WHITE;
				}
				if (Character.isJavaIdentifierStart((char) c)) {
					this.fBuffer.setLength(0);
					do {
						this.fBuffer.append((char) c);
						c = read();
					} while (Character.isJavaIdentifierPart((char) c));
					unread(c);
					String word = this.fBuffer.toString();
					Integer i = this.fgKeys.get(word);
					if (i != null)
						return i.intValue();// KEY
					if (this.notValidName.contains(word))
						return EXIST;
					return WORD;
				}
				return OTHER;
			}
		}
	}

	/**
	 * Returns next character.
	 * 
	 * @return int
	 */
	private int read() {
		if (this.fPos <= this.fEnd) {
			return this.fDoc.charAt(this.fPos++);
		}
		return EOF;
	}

	/**
	 * Method setRange.
	 * 
	 * @param text
	 *            String
	 */
	private void setRange(String text) {
		this.fDoc = text;
		this.fPos = 0;
		this.fEnd = this.fDoc.length() - 1;
	}

	/**
	 * Method unread.
	 * 
	 * @param c
	 *            int
	 */
	private void unread(int c) {
		if (c != EOF)
			this.fPos--;
	}

	/**
	 * a list of name that are not allowed Method setNotValidName.
	 * 
	 * @param notValidName
	 *            List<String>
	 */
	public void setNotValidName(List<String> notValidName) {
		this.notValidName.clear();
		this.notValidName.addAll(notValidName);
	}

	/**
	 * check a word of failed a word is failed with it is a java keyword or no
	 * java correct name Method checkWord.
	 * 
	 * @param text
	 *            String
	 * @return String if the text is valid then return null otherwise will be
	 *         return a name for execption
	 */
	public String checkWord(String text) {
		int token;
		setRange(text);
		token = nextToken();
		boolean valid = false;

		while (token != Scanner.EOF) {
			switch (token) {
			case Scanner.WHITE:
				return "the value is not a valid name";
			case Scanner.KEY:
				return "the value must not be a java key word";
			case Scanner.OTHER:
				return "the value is not a valid name";
			case Scanner.EXIST:
				return "the value is already exist";
			case Scanner.WORD:
				valid = true;
			}
			token = nextToken();
		}

		if (valid)
			return null;
		return "the value is not a valid name";

	}
}

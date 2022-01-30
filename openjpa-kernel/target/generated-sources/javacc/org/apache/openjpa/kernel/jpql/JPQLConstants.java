/* Generated By:JJTree&JavaCC: Do not edit this line. JPQLConstants.java */
package org.apache.openjpa.kernel.jpql;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface JPQLConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int COMMA = 5;
  /** RegularExpression Id. */
  int DOT = 6;
  /** RegularExpression Id. */
  int EQ = 7;
  /** RegularExpression Id. */
  int NE = 8;
  /** RegularExpression Id. */
  int GT = 9;
  /** RegularExpression Id. */
  int GE = 10;
  /** RegularExpression Id. */
  int LT = 11;
  /** RegularExpression Id. */
  int LE = 12;
  /** RegularExpression Id. */
  int PLUS = 13;
  /** RegularExpression Id. */
  int MINUS = 14;
  /** RegularExpression Id. */
  int TIMES = 15;
  /** RegularExpression Id. */
  int DIV = 16;
  /** RegularExpression Id. */
  int NEW = 17;
  /** RegularExpression Id. */
  int ALL = 18;
  /** RegularExpression Id. */
  int ANY = 19;
  /** RegularExpression Id. */
  int EXISTS = 20;
  /** RegularExpression Id. */
  int SOME = 21;
  /** RegularExpression Id. */
  int EMPTY = 22;
  /** RegularExpression Id. */
  int ASC = 23;
  /** RegularExpression Id. */
  int DESC = 24;
  /** RegularExpression Id. */
  int ORDER = 25;
  /** RegularExpression Id. */
  int BY = 26;
  /** RegularExpression Id. */
  int IS = 27;
  /** RegularExpression Id. */
  int MEMBER = 28;
  /** RegularExpression Id. */
  int OF = 29;
  /** RegularExpression Id. */
  int LIKE = 30;
  /** RegularExpression Id. */
  int ESCAPE = 31;
  /** RegularExpression Id. */
  int BETWEEN = 32;
  /** RegularExpression Id. */
  int NULL = 33;
  /** RegularExpression Id. */
  int KEY = 34;
  /** RegularExpression Id. */
  int VALUE = 35;
  /** RegularExpression Id. */
  int TYPE = 36;
  /** RegularExpression Id. */
  int ENTRY = 37;
  /** RegularExpression Id. */
  int AVG = 38;
  /** RegularExpression Id. */
  int MIN = 39;
  /** RegularExpression Id. */
  int MAX = 40;
  /** RegularExpression Id. */
  int SUM = 41;
  /** RegularExpression Id. */
  int COUNT = 42;
  /** RegularExpression Id. */
  int OR = 43;
  /** RegularExpression Id. */
  int AND = 44;
  /** RegularExpression Id. */
  int NOT = 45;
  /** RegularExpression Id. */
  int CONCAT = 46;
  /** RegularExpression Id. */
  int SUBSTRING = 47;
  /** RegularExpression Id. */
  int TRIM = 48;
  /** RegularExpression Id. */
  int LOWER = 49;
  /** RegularExpression Id. */
  int UPPER = 50;
  /** RegularExpression Id. */
  int LEADING = 51;
  /** RegularExpression Id. */
  int TRAILING = 52;
  /** RegularExpression Id. */
  int BOTH = 53;
  /** RegularExpression Id. */
  int LENGTH = 54;
  /** RegularExpression Id. */
  int LOCATE = 55;
  /** RegularExpression Id. */
  int ABS = 56;
  /** RegularExpression Id. */
  int SQRT = 57;
  /** RegularExpression Id. */
  int MOD = 58;
  /** RegularExpression Id. */
  int SIZE = 59;
  /** RegularExpression Id. */
  int INDEX = 60;
  /** RegularExpression Id. */
  int CURRENT_DATE = 61;
  /** RegularExpression Id. */
  int CURRENT_TIME = 62;
  /** RegularExpression Id. */
  int CURRENT_TIMESTAMP = 63;
  /** RegularExpression Id. */
  int SELECT = 64;
  /** RegularExpression Id. */
  int DISTINCT = 65;
  /** RegularExpression Id. */
  int FROM = 66;
  /** RegularExpression Id. */
  int UPDATE = 67;
  /** RegularExpression Id. */
  int DELETE = 68;
  /** RegularExpression Id. */
  int WHERE = 69;
  /** RegularExpression Id. */
  int GROUP = 70;
  /** RegularExpression Id. */
  int HAVING = 71;
  /** RegularExpression Id. */
  int AS = 72;
  /** RegularExpression Id. */
  int LEFT = 73;
  /** RegularExpression Id. */
  int OUTER = 74;
  /** RegularExpression Id. */
  int INNER = 75;
  /** RegularExpression Id. */
  int JOIN = 76;
  /** RegularExpression Id. */
  int FETCH = 77;
  /** RegularExpression Id. */
  int IN = 78;
  /** RegularExpression Id. */
  int SET = 79;
  /** RegularExpression Id. */
  int OBJECT = 80;
  /** RegularExpression Id. */
  int CASE = 81;
  /** RegularExpression Id. */
  int WHEN = 82;
  /** RegularExpression Id. */
  int ELSE = 83;
  /** RegularExpression Id. */
  int THEN = 84;
  /** RegularExpression Id. */
  int END = 85;
  /** RegularExpression Id. */
  int NULLIF = 86;
  /** RegularExpression Id. */
  int COALESCE = 87;
  /** RegularExpression Id. */
  int CLASS = 88;
  /** RegularExpression Id. */
  int INTEGER_LITERAL = 89;
  /** RegularExpression Id. */
  int DECIMAL_LITERAL = 90;
  /** RegularExpression Id. */
  int EXPONENT = 91;
  /** RegularExpression Id. */
  int STRING_LITERAL = 92;
  /** RegularExpression Id. */
  int STRING_LITERAL2 = 93;
  /** RegularExpression Id. */
  int CHARACTER_LITERAL = 94;
  /** RegularExpression Id. */
  int DATE_LITERAL = 95;
  /** RegularExpression Id. */
  int TIME_LITERAL = 96;
  /** RegularExpression Id. */
  int TIMESTAMP_LITERAL = 97;
  /** RegularExpression Id. */
  int BOOLEAN_LITERAL = 98;
  /** RegularExpression Id. */
  int IDENTIFIER = 99;
  /** RegularExpression Id. */
  int LETTER = 100;
  /** RegularExpression Id. */
  int DIGIT = 101;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\t\"",
    "\",\"",
    "\".\"",
    "\"=\"",
    "\"<>\"",
    "\">\"",
    "\">=\"",
    "\"<\"",
    "\"<=\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"NEW\"",
    "\"ALL\"",
    "\"ANY\"",
    "\"EXISTS\"",
    "\"SOME\"",
    "\"EMPTY\"",
    "\"ASC\"",
    "\"DESC\"",
    "\"ORDER\"",
    "\"BY\"",
    "\"IS\"",
    "\"MEMBER\"",
    "\"OF\"",
    "\"LIKE\"",
    "\"ESCAPE\"",
    "\"BETWEEN\"",
    "\"NULL\"",
    "\"KEY\"",
    "\"VALUE\"",
    "\"TYPE\"",
    "\"ENTRY\"",
    "\"AVG\"",
    "\"MIN\"",
    "\"MAX\"",
    "\"SUM\"",
    "\"COUNT\"",
    "\"OR\"",
    "\"AND\"",
    "\"NOT\"",
    "\"CONCAT\"",
    "\"SUBSTRING\"",
    "\"TRIM\"",
    "\"LOWER\"",
    "\"UPPER\"",
    "\"LEADING\"",
    "\"TRAILING\"",
    "\"BOTH\"",
    "\"LENGTH\"",
    "\"LOCATE\"",
    "\"ABS\"",
    "\"SQRT\"",
    "\"MOD\"",
    "\"SIZE\"",
    "\"INDEX\"",
    "\"CURRENT_DATE\"",
    "\"CURRENT_TIME\"",
    "\"CURRENT_TIMESTAMP\"",
    "\"SELECT\"",
    "\"DISTINCT\"",
    "\"FROM\"",
    "\"UPDATE\"",
    "\"DELETE\"",
    "\"WHERE\"",
    "\"GROUP\"",
    "\"HAVING\"",
    "\"AS\"",
    "\"LEFT\"",
    "\"OUTER\"",
    "\"INNER\"",
    "\"JOIN\"",
    "\"FETCH\"",
    "\"IN\"",
    "\"SET\"",
    "\"OBJECT\"",
    "\"CASE\"",
    "\"WHEN\"",
    "\"ELSE\"",
    "\"THEN\"",
    "\"END\"",
    "\"NULLIF\"",
    "\"COALESCE\"",
    "\"CLASS\"",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<EXPONENT>",
    "<STRING_LITERAL>",
    "<STRING_LITERAL2>",
    "<CHARACTER_LITERAL>",
    "<DATE_LITERAL>",
    "<TIME_LITERAL>",
    "<TIMESTAMP_LITERAL>",
    "<BOOLEAN_LITERAL>",
    "<IDENTIFIER>",
    "<LETTER>",
    "<DIGIT>",
    "\"(\"",
    "\")\"",
    "\":\"",
    "\"?\"",
  };

}
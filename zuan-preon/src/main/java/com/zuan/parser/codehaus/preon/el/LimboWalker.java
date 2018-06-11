/*
 * Copyright (c) 2018 Zuan_Wiko
 */

package com.zuan.parser.codehaus.preon.el;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;

import com.zuan.parser.codehaus.preon.el.ast.ArithmeticNode;
import com.zuan.parser.codehaus.preon.el.ast.ArithmeticNode.Operator;
import com.zuan.parser.codehaus.preon.el.ast.BooleanOperatorNode;
import com.zuan.parser.codehaus.preon.el.ast.BooleanOperatorNode.BooleanOperator;
import com.zuan.parser.codehaus.preon.el.ast.IndexSelector;
import com.zuan.parser.codehaus.preon.el.ast.IntegerNode;
import com.zuan.parser.codehaus.preon.el.ast.Node;
import com.zuan.parser.codehaus.preon.el.ast.PropertySelector;
import com.zuan.parser.codehaus.preon.el.ast.ReferenceNode;
import com.zuan.parser.codehaus.preon.el.ast.RelationalNode;
import com.zuan.parser.codehaus.preon.el.ast.RelationalNode.Relation;
import com.zuan.parser.codehaus.preon.el.ast.Selector;
import com.zuan.parser.codehaus.preon.el.ast.StringNode;

/**
 * The Class LimboWalker.
 *
 * @author zuan_
 */
@SuppressWarnings({"unchecked", "rawtypes" })
public class LimboWalker extends TreeParser {

  /** The Constant tokenNames. */
  public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>",
      "<UP>", "REFERENCE", "PROP", "INDEX", "ID", "INT", "BININT", "HEXINT", "STRING", "WS",
      "'<='", "'>='", "'<'", "'>'", "'=='", "'&&'", "'||'", "'('", "')'", "'+'", "'-'", "'*'",
      "'/'", "'^'", "'.'", "'['", "']'" };

  /** The Constant EOF. */
  public static final int EOF = -1;

  /** The Constant T__13. */
  public static final int T__13 = 13;

  /** The Constant T__14. */
  public static final int T__14 = 14;

  /** The Constant T__15. */
  public static final int T__15 = 15;

  /** The Constant T__16. */
  public static final int T__16 = 16;

  /** The Constant T__17. */
  public static final int T__17 = 17;

  /** The Constant T__18. */
  public static final int T__18 = 18;

  /** The Constant T__19. */
  public static final int T__19 = 19;

  /** The Constant T__20. */
  public static final int T__20 = 20;

  /** The Constant T__21. */
  public static final int T__21 = 21;

  /** The Constant T__22. */
  public static final int T__22 = 22;

  /** The Constant T__23. */
  public static final int T__23 = 23;

  /** The Constant T__24. */
  public static final int T__24 = 24;

  /** The Constant T__25. */
  public static final int T__25 = 25;

  /** The Constant T__26. */
  public static final int T__26 = 26;

  /** The Constant T__27. */
  public static final int T__27 = 27;

  /** The Constant T__28. */
  public static final int T__28 = 28;

  /** The Constant T__29. */
  public static final int T__29 = 29;

  /** The Constant REFERENCE. */
  public static final int REFERENCE = 4;

  /** The Constant PROP. */
  public static final int PROP = 5;

  /** The Constant INDEX. */
  public static final int INDEX = 6;

  /** The Constant ID. */
  public static final int ID = 7;

  /** The Constant INT. */
  public static final int INT = 8;

  /** The Constant BININT. */
  public static final int BININT = 9;

  /** The Constant HEXINT. */
  public static final int HEXINT = 10;

  /** The Constant STRING. */
  public static final int STRING = 11;

  /** The Constant WS. */
  public static final int WS = 12;

  // delegates
  // delegators

  /**
   * Instantiates a new limbo walker.
   *
   * @param input
   *          the input
   */
  public LimboWalker(TreeNodeStream input) {
    this(input, new RecognizerSharedState());
  }

  /**
   * Instantiates a new limbo walker.
   *
   * @param input
   *          the input
   * @param state
   *          the state
   */
  public LimboWalker(TreeNodeStream input, RecognizerSharedState state) {
    super(input, state);

  }

  /*
   * (non-Javadoc)
   * @see org.antlr.runtime.BaseRecognizer#getTokenNames()
   */
  @Override
  public String[] getTokenNames() {
    return LimboWalker.tokenNames;
  }

  /*
   * (non-Javadoc)
   * @see org.antlr.runtime.BaseRecognizer#getGrammarFileName()
   */
  @Override
  public String getGrammarFileName() {
    return "org\\codehaus\\preon\\el\\LimboWalker.g";
  }

  /** The context. */
  private ReferenceContext context;

  /**
   * Instantiates a new limbo walker.
   *
   * @param input
   *          the input
   * @param context
   *          the context
   */
  public LimboWalker(TreeNodeStream input, ReferenceContext context) {
    super(input);
    this.context = context;
  }

  /**
   * Mismatch.
   *
   * @param input
   *          the input
   * @param ttype
   *          the ttype
   * @param follow
   *          the follow
   * @throws RecognitionException
   *           the recognition exception
   */
  protected void mismatch(IntStream input, int ttype, BitSet follow)
      throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);
  }

  /*
   * (non-Javadoc)
   * @see org.antlr.runtime.BaseRecognizer#recoverFromMismatchedSet(org.antlr.
   * runtime. IntStream, org.antlr.runtime.RecognitionException,
   * org.antlr.runtime.BitSet)
   */
  @Override
  public Object recoverFromMismatchedSet(IntStream input, RecognitionException e,
      BitSet follow) throws RecognitionException {
    throw e;
  }

  // $ANTLR start "fexpr"
  // org\\codehaus\\preon\\el\\LimboWalker.g:50:1: fexpr returns [Node node] :
  // (a=
  /**
   * Fexpr.
   *
   * @return the node
   * @throws RecognitionException
   *           the recognition exception
   */
  // bexpr | a= vexpr );
  public final Node fexpr() throws RecognitionException {
    Node node = null;

    Node a = null;

    try {
      // org\\codehaus\\preon\\el\\LimboWalker.g:51:2: (a= bexpr | a= vexpr )
      int alt1 = 2;
      switch (input.LA(1)) {
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19: {
        alt1 = 1;
      }
        break;
      case REFERENCE:
      case INT:
      case BININT:
      case HEXINT:
      case STRING:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26: {
        alt1 = 2;
      }
        break;
      default:
        NoViableAltException nvae = new NoViableAltException("", 1, 0, input);

        throw nvae;
      }

      switch (alt1) {
      case 1:
      // org\\codehaus\\preon\\el\\LimboWalker.g:51:4: a= bexpr
      {
        pushFollow(FOLLOW_bexpr_in_fexpr56);
        a = bexpr();

        state._fsp--;

        node = a;

      }
        break;
      case 2:
      // org\\codehaus\\preon\\el\\LimboWalker.g:52:4: a= vexpr
      {
        pushFollow(FOLLOW_vexpr_in_fexpr65);
        a = vexpr();

        state._fsp--;

        node = a;

      }
        break;

      }
    }

    catch (RecognitionException e) {
      throw e;
    } finally {
    }
    return node;
  }
  // $ANTLR end "fexpr"

  // $ANTLR start "bexpr"
  // org\\codehaus\\preon\\el\\LimboWalker.g:55:1: bexpr returns [Node node] : (
  // ^( '&&' c= bexpr d= bexpr ) | ^( '||' c= bexpr d= bexpr ) | ^( '<=' a=
  // vexpr
  // b= vexpr ) | ^( '>=' a= vexpr b= vexpr ) | ^( '<' a= vexpr b= vexpr ) | ^(
  /**
   * Bexpr.
   *
   * @return the node
   * @throws RecognitionException
   *           the recognition exception
   */
  // '>' a= vexpr b= vexpr ) | ^( '==' a= vexpr b= vexpr ) );
  public final Node bexpr() throws RecognitionException {
    Node node = null;

    Node c = null;

    Node d = null;

    Node a = null;

    Node b = null;

    try {
      // org\\codehaus\\preon\\el\\LimboWalker.g:56:5: ( ^( '&&' c= bexpr d=
      // bexpr ) |
      // ^( '||' c= bexpr d= bexpr ) | ^( '<=' a= vexpr b= vexpr ) | ^( '>=' a=
      // vexpr
      // b= vexpr ) | ^( '<' a= vexpr b= vexpr ) | ^( '>' a= vexpr b= vexpr ) |
      // ^(
      // '==' a= vexpr b= vexpr ) )
      int alt2 = 7;
      switch (input.LA(1)) {
      case 18: {
        alt2 = 1;
      }
        break;
      case 19: {
        alt2 = 2;
      }
        break;
      case 13: {
        alt2 = 3;
      }
        break;
      case 14: {
        alt2 = 4;
      }
        break;
      case 15: {
        alt2 = 5;
      }
        break;
      case 16: {
        alt2 = 6;
      }
        break;
      case 17: {
        alt2 = 7;
      }
        break;
      default:
        NoViableAltException nvae = new NoViableAltException("", 2, 0, input);

        throw nvae;
      }

      switch (alt2) {
      case 1:
      // org\\codehaus\\preon\\el\\LimboWalker.g:56:9: ^( '&&' c= bexpr d= bexpr
      // )
      {
        match(input, 18, FOLLOW_18_in_bexpr89);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_bexpr_in_bexpr93);
        c = bexpr();

        state._fsp--;

        pushFollow(FOLLOW_bexpr_in_bexpr97);
        d = bexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = BooleanOperatorNode.create(BooleanOperator.AND, c, d);

      }
        break;
      case 2:
      // org\\codehaus\\preon\\el\\LimboWalker.g:57:9: ^( '||' c= bexpr d= bexpr
      // )
      {
        match(input, 19, FOLLOW_19_in_bexpr111);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_bexpr_in_bexpr115);
        c = bexpr();

        state._fsp--;

        pushFollow(FOLLOW_bexpr_in_bexpr119);
        d = bexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = BooleanOperatorNode.create(BooleanOperator.OR, c, d);

      }
        break;
      case 3:
      // org\\codehaus\\preon\\el\\LimboWalker.g:58:4: ^( '<=' a= vexpr b= vexpr
      // )
      {
        match(input, 13, FOLLOW_13_in_bexpr128);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_bexpr132);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_bexpr136);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = RelationalNode.create(Relation.LTE, a, b);

      }
        break;
      case 4:
      // org\\codehaus\\preon\\el\\LimboWalker.g:59:4: ^( '>=' a= vexpr b= vexpr
      // )
      {
        match(input, 14, FOLLOW_14_in_bexpr145);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_bexpr149);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_bexpr153);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = RelationalNode.create(Relation.GTE, a, b);

      }
        break;
      case 5:
      // org\\codehaus\\preon\\el\\LimboWalker.g:60:4: ^( '<' a= vexpr b= vexpr
      // )
      {
        match(input, 15, FOLLOW_15_in_bexpr162);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_bexpr166);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_bexpr170);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = RelationalNode.create(Relation.LT, a, b);

      }
        break;
      case 6:
      // org\\codehaus\\preon\\el\\LimboWalker.g:61:4: ^( '>' a= vexpr b= vexpr
      // )
      {
        match(input, 16, FOLLOW_16_in_bexpr179);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_bexpr183);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_bexpr187);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = RelationalNode.create(Relation.GT, a, b);

      }
        break;
      case 7:
      // org\\codehaus\\preon\\el\\LimboWalker.g:62:4: ^( '==' a= vexpr b= vexpr
      // )
      {
        match(input, 17, FOLLOW_17_in_bexpr196);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_bexpr200);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_bexpr204);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = RelationalNode.create(Relation.EQ, a, b);

      }
        break;

      }
    }

    catch (RecognitionException e) {
      throw e;
    } finally {
    }
    return node;
  }
  // $ANTLR end "bexpr"

  // $ANTLR start "vexpr"
  // org\\codehaus\\preon\\el\\LimboWalker.g:65:1: vexpr returns [Node node] : (
  // ^( '+' a= vexpr b= vexpr ) | ^( '-' a= vexpr b= vexpr ) | ^( '*' a= vexpr
  // b=
  // vexpr ) | ^( '/' a= vexpr b= vexpr ) | ^( '^' a= vexpr b= vexpr ) | INT |
  /**
   * Vexpr.
   *
   * @return the node
   * @throws RecognitionException
   *           the recognition exception
   */
  // BININT | HEXINT | STRING | ^( REFERENCE var= ID ( selector )* ) );
  public final Node vexpr() throws RecognitionException {
    Node node = null;

    CommonTree var = null;
    CommonTree INT1 = null;
    CommonTree BININT2 = null;
    CommonTree HEXINT3 = null;
    CommonTree STRING4 = null;
    Node a = null;

    Node b = null;

    Selector selector5 = null;

    try {
      // org\\codehaus\\preon\\el\\LimboWalker.g:66:2: ( ^( '+' a= vexpr b=
      // vexpr ) |
      // ^( '-' a= vexpr b= vexpr ) | ^( '*' a= vexpr b= vexpr ) | ^( '/' a=
      // vexpr b=
      // vexpr ) | ^( '^' a= vexpr b= vexpr ) | INT | BININT | HEXINT | STRING |
      // ^(
      // REFERENCE var= ID ( selector )* ) )
      int alt4 = 10;
      switch (input.LA(1)) {
      case 22: {
        alt4 = 1;
      }
        break;
      case 23: {
        alt4 = 2;
      }
        break;
      case 24: {
        alt4 = 3;
      }
        break;
      case 25: {
        alt4 = 4;
      }
        break;
      case 26: {
        alt4 = 5;
      }
        break;
      case INT: {
        alt4 = 6;
      }
        break;
      case BININT: {
        alt4 = 7;
      }
        break;
      case HEXINT: {
        alt4 = 8;
      }
        break;
      case STRING: {
        alt4 = 9;
      }
        break;
      case REFERENCE: {
        alt4 = 10;
      }
        break;
      default:
        NoViableAltException nvae = new NoViableAltException("", 4, 0, input);

        throw nvae;
      }

      switch (alt4) {
      case 1:
      // org\\codehaus\\preon\\el\\LimboWalker.g:66:4: ^( '+' a= vexpr b= vexpr
      // )
      {
        match(input, 22, FOLLOW_22_in_vexpr223);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_vexpr227);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_vexpr231);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = ArithmeticNode.create(Operator.plus, a, b);

      }
        break;
      case 2:
      // org\\codehaus\\preon\\el\\LimboWalker.g:67:4: ^( '-' a= vexpr b= vexpr
      // )
      {
        match(input, 23, FOLLOW_23_in_vexpr240);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_vexpr244);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_vexpr248);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = ArithmeticNode.create(Operator.minus, a, b);

      }
        break;
      case 3:
      // org\\codehaus\\preon\\el\\LimboWalker.g:68:4: ^( '*' a= vexpr b= vexpr
      // )
      {
        match(input, 24, FOLLOW_24_in_vexpr257);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_vexpr261);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_vexpr265);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = ArithmeticNode.create(Operator.mult, a, b);

      }
        break;
      case 4:
      // org\\codehaus\\preon\\el\\LimboWalker.g:69:4: ^( '/' a= vexpr b= vexpr
      // )
      {
        match(input, 25, FOLLOW_25_in_vexpr274);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_vexpr278);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_vexpr282);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = ArithmeticNode.create(Operator.div, a, b);

      }
        break;
      case 5:
      // org\\codehaus\\preon\\el\\LimboWalker.g:70:6: ^( '^' a= vexpr b= vexpr
      // )
      {
        match(input, 26, FOLLOW_26_in_vexpr293);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_vexpr297);
        a = vexpr();

        state._fsp--;

        pushFollow(FOLLOW_vexpr_in_vexpr301);
        b = vexpr();

        state._fsp--;

        match(input, Token.UP, null);
        node = ArithmeticNode.create(Operator.pow, a, b);

      }
        break;
      case 6:
      // org\\codehaus\\preon\\el\\LimboWalker.g:71:4: INT
      {
        INT1 = (CommonTree) match(input, INT, FOLLOW_INT_in_vexpr309);
        node = new IntegerNode(Integer.parseInt((INT1 != null ? INT1.getText() : null)));

      }
        break;
      case 7:
      // org\\codehaus\\preon\\el\\LimboWalker.g:72:6: BININT
      {
        BININT2 = (CommonTree) match(input, BININT, FOLLOW_BININT_in_vexpr318);
        node = IntegerNode.fromBin((BININT2 != null ? BININT2.getText() : null));

      }
        break;
      case 8:
      // org\\codehaus\\preon\\el\\LimboWalker.g:73:6: HEXINT
      {
        HEXINT3 = (CommonTree) match(input, HEXINT, FOLLOW_HEXINT_in_vexpr327);
        node = IntegerNode.fromHex((HEXINT3 != null ? HEXINT3.getText() : null));

      }
        break;
      case 9:
      // org\\codehaus\\preon\\el\\LimboWalker.g:74:6: STRING
      {
        STRING4 = (CommonTree) match(input, STRING, FOLLOW_STRING_in_vexpr336);
        node = new StringNode((STRING4 != null ? STRING4.getText() : null));

      }
        break;
      case 10:
      // org\\codehaus\\preon\\el\\LimboWalker.g:75:6: ^( REFERENCE var= ID (
      // selector
      // )* )
      {
        java.util.List selectors = new java.util.ArrayList();
        match(input, REFERENCE, FOLLOW_REFERENCE_in_vexpr352);

        match(input, Token.DOWN, null);
        var = (CommonTree) match(input, ID, FOLLOW_ID_in_vexpr356);
        // org\\codehaus\\preon\\el\\LimboWalker.g:77:21: ( selector )*
        loop3: do {
          int alt3 = 2;
          switch (input.LA(1)) {
          case PROP:
          case INDEX: {
            alt3 = 1;
          }
            break;

          }

          switch (alt3) {
          case 1:
          // org\\codehaus\\preon\\el\\LimboWalker.g:77:22: selector
          {
            pushFollow(FOLLOW_selector_in_vexpr359);
            selector5 = selector();

            state._fsp--;

            selectors.add(selector5);

          }
            break;

          default:
            break loop3;
          }
        } while (true);

        match(input, Token.UP, null);

        Reference ref = context.selectAttribute((var != null ? var.getText() : null));
        for (int i = 0; i < selectors.size(); i++) {
          ref = ((Selector) selectors.get(i)).select(ref);
        }
        node = new ReferenceNode(ref);

      }
        break;

      }
    }

    catch (RecognitionException e) {
      throw e;
    } finally {
    }
    return node;
  }
  // $ANTLR end "vexpr"

  // $ANTLR start "zexpr"
  // org\\codehaus\\preon\\el\\LimboWalker.g:86:1: zexpr returns [Node node] :
  // (a=
  /**
   * Zexpr.
   *
   * @return the node
   * @throws RecognitionException
   *           the recognition exception
   */
  // bexpr | ^( REFERENCE var= ID ( selector )* ) );
  public final Node zexpr() throws RecognitionException {
    Node node = null;

    CommonTree var = null;
    Node a = null;

    Selector selector6 = null;

    try {
      // org\\codehaus\\preon\\el\\LimboWalker.g:87:5: (a= bexpr | ^( REFERENCE
      // var=
      // ID ( selector )* ) )
      int alt6 = 2;
      switch (input.LA(1)) {
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19: {
        alt6 = 1;
      }
        break;
      case REFERENCE: {
        alt6 = 2;
      }
        break;
      default:
        NoViableAltException nvae = new NoViableAltException("", 6, 0, input);

        throw nvae;
      }

      switch (alt6) {
      case 1:
      // org\\codehaus\\preon\\el\\LimboWalker.g:87:9: a= bexpr
      {
        pushFollow(FOLLOW_bexpr_in_zexpr389);
        a = bexpr();

        state._fsp--;

        node = a;

      }
        break;
      case 2:
      // org\\codehaus\\preon\\el\\LimboWalker.g:88:6: ^( REFERENCE var= ID (
      // selector
      // )* )
      {
        java.util.List selectors = new java.util.ArrayList();
        match(input, REFERENCE, FOLLOW_REFERENCE_in_zexpr402);

        match(input, Token.DOWN, null);
        var = (CommonTree) match(input, ID, FOLLOW_ID_in_zexpr406);
        // org\\codehaus\\preon\\el\\LimboWalker.g:89:21: ( selector )*
        loop5: do {
          int alt5 = 2;
          switch (input.LA(1)) {
          case PROP:
          case INDEX: {
            alt5 = 1;
          }
            break;

          }

          switch (alt5) {
          case 1:
          // org\\codehaus\\preon\\el\\LimboWalker.g:89:22: selector
          {
            pushFollow(FOLLOW_selector_in_zexpr409);
            selector6 = selector();

            state._fsp--;

            selectors.add(selector6);

          }
            break;

          default:
            break loop5;
          }
        } while (true);

        match(input, Token.UP, null);

        Reference ref = context.selectAttribute((var != null ? var.getText() : null));
        for (int i = 0; i < selectors.size(); i++) {
          ref = ((Selector) selectors.get(i)).select(ref);
        }
        node = new ReferenceNode(ref);

      }
        break;

      }
    }

    catch (RecognitionException e) {
      throw e;
    } finally {
    }
    return node;
  }
  // $ANTLR end "zexpr"

  // $ANTLR start "selector"
  // org\\codehaus\\preon\\el\\LimboWalker.g:98:1: selector returns [Selector
  /**
   * Selector.
   *
   * @return the selector
   * @throws RecognitionException
   *           the recognition exception
   */
  // node] : ( ^( PROP ID ) | ^( INDEX i= vexpr ) );
  public final Selector selector() throws RecognitionException {
    Selector node = null;

    CommonTree ID7 = null;
    Node i = null;

    try {
      // org\\codehaus\\preon\\el\\LimboWalker.g:99:5: ( ^( PROP ID ) | ^( INDEX
      // i=
      // vexpr ) )
      int alt7 = 2;
      switch (input.LA(1)) {
      case PROP: {
        alt7 = 1;
      }
        break;
      case INDEX: {
        alt7 = 2;
      }
        break;
      default:
        NoViableAltException nvae = new NoViableAltException("", 7, 0, input);

        throw nvae;
      }

      switch (alt7) {
      case 1:
      // org\\codehaus\\preon\\el\\LimboWalker.g:99:9: ^( PROP ID )
      {
        match(input, PROP, FOLLOW_PROP_in_selector438);

        match(input, Token.DOWN, null);
        ID7 = (CommonTree) match(input, ID, FOLLOW_ID_in_selector440);

        match(input, Token.UP, null);

        node = new PropertySelector((ID7 != null ? ID7.getText() : null));

      }
        break;
      case 2:
      // org\\codehaus\\preon\\el\\LimboWalker.g:102:9: ^( INDEX i= vexpr )
      {
        match(input, INDEX, FOLLOW_INDEX_in_selector454);

        match(input, Token.DOWN, null);
        pushFollow(FOLLOW_vexpr_in_selector458);
        i = vexpr();

        state._fsp--;

        match(input, Token.UP, null);

        node = new IndexSelector(i);

      }
        break;

      }
    }

    catch (RecognitionException e) {
      throw e;
    } finally {
    }
    return node;
  }
  // $ANTLR end "selector"

  // Delegated rules

  /** The Constant FOLLOW_bexpr_in_fexpr56. */
  public static final BitSet FOLLOW_bexpr_in_fexpr56 =
      new BitSet(new long[]{0x0000000000000002L });

  /** The Constant FOLLOW_vexpr_in_fexpr65. */
  public static final BitSet FOLLOW_vexpr_in_fexpr65 =
      new BitSet(new long[]{0x0000000000000002L });

  /** The Constant FOLLOW_18_in_bexpr89. */
  public static final BitSet FOLLOW_18_in_bexpr89 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_bexpr_in_bexpr93. */
  public static final BitSet FOLLOW_bexpr_in_bexpr93 =
      new BitSet(new long[]{0x00000000000FE000L });

  /** The Constant FOLLOW_bexpr_in_bexpr97. */
  public static final BitSet FOLLOW_bexpr_in_bexpr97 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_19_in_bexpr111. */
  public static final BitSet FOLLOW_19_in_bexpr111 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_bexpr_in_bexpr115. */
  public static final BitSet FOLLOW_bexpr_in_bexpr115 =
      new BitSet(new long[]{0x00000000000FE000L });

  /** The Constant FOLLOW_bexpr_in_bexpr119. */
  public static final BitSet FOLLOW_bexpr_in_bexpr119 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_13_in_bexpr128. */
  public static final BitSet FOLLOW_13_in_bexpr128 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_bexpr132. */
  public static final BitSet FOLLOW_vexpr_in_bexpr132 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_bexpr136. */
  public static final BitSet FOLLOW_vexpr_in_bexpr136 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_14_in_bexpr145. */
  public static final BitSet FOLLOW_14_in_bexpr145 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_bexpr149. */
  public static final BitSet FOLLOW_vexpr_in_bexpr149 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_bexpr153. */
  public static final BitSet FOLLOW_vexpr_in_bexpr153 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_15_in_bexpr162. */
  public static final BitSet FOLLOW_15_in_bexpr162 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_bexpr166. */
  public static final BitSet FOLLOW_vexpr_in_bexpr166 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_bexpr170. */
  public static final BitSet FOLLOW_vexpr_in_bexpr170 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_16_in_bexpr179. */
  public static final BitSet FOLLOW_16_in_bexpr179 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_bexpr183. */
  public static final BitSet FOLLOW_vexpr_in_bexpr183 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_bexpr187. */
  public static final BitSet FOLLOW_vexpr_in_bexpr187 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_17_in_bexpr196. */
  public static final BitSet FOLLOW_17_in_bexpr196 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_bexpr200. */
  public static final BitSet FOLLOW_vexpr_in_bexpr200 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_bexpr204. */
  public static final BitSet FOLLOW_vexpr_in_bexpr204 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_22_in_vexpr223. */
  public static final BitSet FOLLOW_22_in_vexpr223 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_vexpr227. */
  public static final BitSet FOLLOW_vexpr_in_vexpr227 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_vexpr231. */
  public static final BitSet FOLLOW_vexpr_in_vexpr231 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_23_in_vexpr240. */
  public static final BitSet FOLLOW_23_in_vexpr240 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_vexpr244. */
  public static final BitSet FOLLOW_vexpr_in_vexpr244 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_vexpr248. */
  public static final BitSet FOLLOW_vexpr_in_vexpr248 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_24_in_vexpr257. */
  public static final BitSet FOLLOW_24_in_vexpr257 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_vexpr261. */
  public static final BitSet FOLLOW_vexpr_in_vexpr261 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_vexpr265. */
  public static final BitSet FOLLOW_vexpr_in_vexpr265 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_25_in_vexpr274. */
  public static final BitSet FOLLOW_25_in_vexpr274 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_vexpr278. */
  public static final BitSet FOLLOW_vexpr_in_vexpr278 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_vexpr282. */
  public static final BitSet FOLLOW_vexpr_in_vexpr282 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_26_in_vexpr293. */
  public static final BitSet FOLLOW_26_in_vexpr293 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_vexpr297. */
  public static final BitSet FOLLOW_vexpr_in_vexpr297 =
      new BitSet(new long[]{0x0000000007C00F10L });

  /** The Constant FOLLOW_vexpr_in_vexpr301. */
  public static final BitSet FOLLOW_vexpr_in_vexpr301 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_INT_in_vexpr309. */
  public static final BitSet FOLLOW_INT_in_vexpr309 =
      new BitSet(new long[]{0x0000000000000002L });

  /** The Constant FOLLOW_BININT_in_vexpr318. */
  public static final BitSet FOLLOW_BININT_in_vexpr318 =
      new BitSet(new long[]{0x0000000000000002L });

  /** The Constant FOLLOW_HEXINT_in_vexpr327. */
  public static final BitSet FOLLOW_HEXINT_in_vexpr327 =
      new BitSet(new long[]{0x0000000000000002L });

  /** The Constant FOLLOW_STRING_in_vexpr336. */
  public static final BitSet FOLLOW_STRING_in_vexpr336 =
      new BitSet(new long[]{0x0000000000000002L });

  /** The Constant FOLLOW_REFERENCE_in_vexpr352. */
  public static final BitSet FOLLOW_REFERENCE_in_vexpr352 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_ID_in_vexpr356. */
  public static final BitSet FOLLOW_ID_in_vexpr356 =
      new BitSet(new long[]{0x0000000000000068L });

  /** The Constant FOLLOW_selector_in_vexpr359. */
  public static final BitSet FOLLOW_selector_in_vexpr359 =
      new BitSet(new long[]{0x0000000000000068L });

  /** The Constant FOLLOW_bexpr_in_zexpr389. */
  public static final BitSet FOLLOW_bexpr_in_zexpr389 =
      new BitSet(new long[]{0x0000000000000002L });

  /** The Constant FOLLOW_REFERENCE_in_zexpr402. */
  public static final BitSet FOLLOW_REFERENCE_in_zexpr402 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_ID_in_zexpr406. */
  public static final BitSet FOLLOW_ID_in_zexpr406 =
      new BitSet(new long[]{0x0000000000000068L });

  /** The Constant FOLLOW_selector_in_zexpr409. */
  public static final BitSet FOLLOW_selector_in_zexpr409 =
      new BitSet(new long[]{0x0000000000000068L });

  /** The Constant FOLLOW_PROP_in_selector438. */
  public static final BitSet FOLLOW_PROP_in_selector438 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_ID_in_selector440. */
  public static final BitSet FOLLOW_ID_in_selector440 =
      new BitSet(new long[]{0x0000000000000008L });

  /** The Constant FOLLOW_INDEX_in_selector454. */
  public static final BitSet FOLLOW_INDEX_in_selector454 =
      new BitSet(new long[]{0x0000000000000004L });

  /** The Constant FOLLOW_vexpr_in_selector458. */
  public static final BitSet FOLLOW_vexpr_in_selector458 =
      new BitSet(new long[]{0x0000000000000008L });

}

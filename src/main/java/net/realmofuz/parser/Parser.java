package net.realmofuz.parser;

import net.realmofuz.lexer.Token;
import net.realmofuz.parser.ast.AST;
import net.realmofuz.type.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Parser {
    public int tokenIndex = -1;
    public List<Token> tokenList;

    public Parser(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public Token read() {
        tokenIndex++;
        return this.tokenList.get(tokenIndex);
    }

    public <E extends Token> E match(Class<E> tokenClass) {
        var r = this.read();
        if (!(tokenClass.isInstance(r)))
            throw new RuntimeException(STR."not expected: found `\{r}`, expected `\{tokenClass}`");
        return tokenClass.cast(r);
    }

    public Token peek() {
        return this.tokenList.get(tokenIndex + 1);
    }

    public AST.Module parse() {
        var fs = new ArrayList<AST.FunctionDeclaration>();
        while(true) {
            try {
                peek();
            } catch (IndexOutOfBoundsException ex) {
                break;
            }
            fs.add(parseFunction());
        }
        return new AST.Module(fs);
    }

    public AST.FunctionDeclaration parseFunction() {
        var tok = this.match(Token.Symbol.class);
        System.out.println(tok);

        var varNameParams = new ArrayList<String>();
        var varTypeParams = new ArrayList<Type>();

        this.match(Token.OpenParen.class);
        while(!(peek() instanceof Token.CloseParen)) {
            var varName = this.match(Token.Symbol.class);
            match(Token.Colon.class);
            var varType = parseType();

            varNameParams.add(varName.value());
            varTypeParams.add(varType);

            if(peek() instanceof Token.Comma)
                match(Token.Comma.class);
        }
        this.match(Token.CloseParen.class);

        var kw = this.match(Token.Keyword.class);
        if (!Objects.equals(kw.value(), "->"))
            throw new RuntimeException("aaa");

        var type = this.parseType();

        var eq = this.match(Token.Symbol.class);
        if (!Objects.equals(eq.value(), "="))
            throw new RuntimeException("aaa");

        var expr = parseExpression();

        return new AST.FunctionDeclaration(
            tok.value(),
            varTypeParams,
            varNameParams,
            type,
            expr
        );
    }

    public AST.Expression parseBaseValue() {
        if (peek() instanceof Token.Number nt) {
            match(Token.Number.class);
            return new AST.Expression.NumberValue(nt.value());
        }
        if(peek() instanceof Token.OpenParen op) {
            match(Token.OpenParen.class);
            var vs = new ArrayList<AST.Expression>();
            while(true) {
                vs.add(parseExpression());
                if(peek() instanceof Token.CloseParen cp) {
                    match(Token.CloseParen.class);
                    break;
                }
                match(Token.Comma.class);
            }
            return new AST.Expression.Parenthesis(vs);
        }
        if(peek() instanceof Token.Symbol sym) {
            read();
            return new AST.Expression.VariableValue(sym.value());
        }
        throw new RuntimeException("invalid base value `" + peek() + "`");
    }

    public AST.Expression parseTerm() {
        var lhs = parseBaseValue();
        while(peek() instanceof Token.Symbol ts &&
            (ts.value().equals("+") || ts.value().equals("-"))) {
            match(Token.Symbol.class);
            if(ts.value().equals("+"))
                lhs = new AST.Expression.Addition(lhs, parseBaseValue());
            if(ts.value().equals("-"))
                lhs = new AST.Expression.Subtraction(lhs, parseBaseValue());
        }
        return lhs;
    }

    public AST.Expression parseFactor() {
        var lhs = parseTerm();
        while(true) {
            if(peek() instanceof Token.Symbol ts
            && (ts.value().equals("*") || ts.value().equals("/"))) {
                match(Token.Symbol.class);
                if(ts.value().equals("*"))
                    lhs = new AST.Expression.Multiplication(lhs, parseTerm());
                if(ts.value().equals("/"))
                    lhs = new AST.Expression.Division(lhs, parseTerm());
            } else if ((peek() instanceof Token.Symbol ts
            && !("+-/*= \n\r").contains(ts.value()))
            || peek() instanceof Token.OpenParen op) {
                lhs = new AST.Expression.Multiplication(lhs, parseTerm());
            } else return lhs;
        }
    }

    public AST.Expression parseExponent() {
        var lhs = parseFactor();
        while(peek() instanceof Token.Symbol ts && ts.value().equals("^")) {
            match(Token.Symbol.class);
            lhs = new AST.Expression.Exponent(lhs, parseFactor());
        }
        return lhs;
    }

    public AST.Expression parseExpression() {
        if (peek() instanceof Token.Keyword kw) {
            if (Objects.equals(kw.value(), "do")) {
                match(Token.Keyword.class);
                match(Token.OpenBrace.class);
                var expressions = new ArrayList<AST.Expression>();
                while (!(peek() instanceof Token.CloseBrace)) {
                    expressions.add(parseExponent());
                    System.out.println(expressions);
                    match(Token.Semicolon.class);
                }
                match(Token.CloseBrace.class);
                return new AST.Expression.DoBlock(expressions);
            }

            if (Objects.equals(kw.value(), "switch")) {
                match(Token.Keyword.class);
                match(Token.OpenBrace.class);
                var expressions = new ArrayList<AST.Expression>();
                while (!(peek() instanceof Token.CloseBrace)) {
                    expressions.add(parseExponent());
                    match(Token.Semicolon.class);
                }
                match(Token.CloseBrace.class);
                return new AST.Expression.SwitchBlock(expressions);
            }
        }

        var r = parseExponent();
        if(peek() instanceof Token.Semicolon)
            match(Token.Semicolon.class);
        return r;
    }

    public Type parseType() {
        var symbol = this.match(Token.Symbol.class);
        if (Objects.equals(symbol.value(), "Z"))
            return new Type.Integer();
        if (Objects.equals(symbol.value(), "R"))
            return new Type.Real();
        if (Objects.equals(symbol.value(), "C"))
            return new Type.Complex();
        throw new RuntimeException(STR."not a valid type: `\{symbol.value()}`");
    }
}

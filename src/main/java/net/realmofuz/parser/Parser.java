package net.realmofuz.parser;

import net.realmofuz.lexer.Span;
import net.realmofuz.lexer.Token;
import net.realmofuz.parser.ast.AST;
import net.realmofuz.parser.ast.BinaryOperationType;
import net.realmofuz.type.Type;

import java.util.ArrayList;
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
            throw new RuntimeException("failed to match");
        return tokenClass.cast(r);
    }

    public Token peek() {
        try {
            return this.tokenList.get(tokenIndex + 1);
        } catch (IndexOutOfBoundsException ex) {
            return new Token.EOF(
                    this.tokenList.get(tokenIndex).span()
            );
        }
    }

    public List<AST.Expression> parse() {
        var fs = new ArrayList<AST.Expression>();
        while (true) {
            try {
                peek();
            } catch (IndexOutOfBoundsException ex) {
                break;
            }
            fs.add(parseHeaderFunction());
            if(peek() instanceof Token.EOF)
                break;
        }
        return fs;
    }

    public AST.Expression.BinaryOperation parseHeaderFunction() {
        var name = this.match(Token.Symbol.class);

        var params = new ArrayList<AST.Expression>();
        while (!(peek() instanceof Token.Symbol tk
                && tk.value().equals("="))) {
            params.add(parseBaseValue());
        }

        var kw = this.match(Token.Symbol.class);
        if (!Objects.equals(kw.value(), "="))
            throw new RuntimeException("aaa");

        var expr = parseExpression();

        return new AST.Expression.BinaryOperation(
                new AST.Value.VariableValue(name.value()),
                new AST.Value.FunctionValue(
                        params,
                        expr
                ),
                BinaryOperationType.STORE
        );
    }

    public AST.Value.FunctionValue parseFunction() {
        this.match(Token.Backslash.class);

        var names = new ArrayList<AST.Expression>();

        while (!(peek() instanceof Token.Keyword tk
        && tk.value().equals("->"))
        && peek() instanceof Token.Symbol ts) {
            names.add(parseBaseValue());
        }

        var kw = this.match(Token.Keyword.class);
        if (!Objects.equals(kw.value(), "->"))
            throw new RuntimeException("aaa");

        var expr = parseExpression();

        return new AST.Value.FunctionValue(
                names,
                expr
        );
    }

    public AST.Expression parseBaseValue() {
        if (peek() instanceof Token.Number nt) {
            match(Token.Number.class);
            return new AST.Value.NumberValue(nt.value());
        }
        if (peek() instanceof Token.OpenParen op) {
            match(Token.OpenParen.class);
            var vs = new ArrayList<AST.Expression>();
            while (true) {
                vs.add(parseExpression());
                if (peek() instanceof Token.CloseParen cp) {
                    match(Token.CloseParen.class);
                    break;
                }
                match(Token.Comma.class);
            }
            return new AST.Value.Parenthesis(vs);
        }
        if (peek() instanceof Token.Symbol sym) {
            read();
            return new AST.Value.VariableValue(sym.value());
        }
        if(peek() instanceof Token.Backslash) {
            return parseFunction();
        }
        throw new RuntimeException("check value docs pls <3 " + peek());
    }

    public AST.Expression parseTerm() {
        var lhs = parseBaseValue();
        while (peek() instanceof Token.Symbol ts &&
                (ts.value().equals("+") || ts.value().equals("-"))) {
            match(Token.Symbol.class);
            if (ts.value().equals("+"))
                lhs = new AST.Expression.BinaryOperation(lhs, parseBaseValue(),
                        BinaryOperationType.ADD);
            if (ts.value().equals("-"))
                lhs = new AST.Expression.BinaryOperation(lhs, parseBaseValue(),
                        BinaryOperationType.SUBTRACT);
        }
        return lhs;
    }

    public AST.Expression parseFactor() {
        var lhs = parseTerm();
        while (true) {
            if (peek() instanceof Token.Symbol ts
                    && (ts.value().equals("*") || ts.value().equals("/"))) {
                match(Token.Symbol.class);
                if (ts.value().equals("*"))
                    lhs = new AST.Expression.BinaryOperation(lhs, parseTerm(),
                        BinaryOperationType.MULTIPLY);
                if (ts.value().equals("/"))
                    lhs = new AST.Expression.BinaryOperation(lhs, parseTerm(),
                        BinaryOperationType.DIVIDE);
            } else if (peek() instanceof Token.OpenParen
            || (peek() instanceof Token.Symbol ts &&
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWYXZ_".contains(ts.value()))) {
                lhs = new AST.Expression.BinaryOperation(lhs, parseTerm(),
                        BinaryOperationType.PARENTHESIS_MULTIPLICATION);
            } else return lhs;
        }
    }

    public AST.Expression parseExponent() {
        var lhs = parseFactor();
        while (peek() instanceof Token.Symbol ts && ts.value().equals("^")) {
            match(Token.Symbol.class);
            lhs = new AST.Expression.BinaryOperation(lhs, parseFactor(),
                    BinaryOperationType.EXPONENT);
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
                    match(Token.Semicolon.class);
                }
                match(Token.CloseBrace.class);
                return new AST.Block.DoBlock(expressions);
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
                return new AST.Block.SwitchBlock(expressions);
            }
        }

        return parseExponent();
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

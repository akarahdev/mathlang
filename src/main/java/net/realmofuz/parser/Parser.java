package net.realmofuz.parser;

import net.realmofuz.lexer.Token;
import net.realmofuz.parser.ast.AST;
import net.realmofuz.type.Type;

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

    public<E extends Token> E match(Class<E> tokenClass) {
        var r = read();
        return (E) r;
    }

    public Token peek() {
        return this.tokenList.get(tokenIndex+1);
    }

    public AST parse() {
        return parseFunction();
    }

    public AST.FunctionDeclaration parseFunction() {
        var tok = this.match(Token.Symbol.class);
        System.out.println(tok);

        this.match(Token.OpenParen.class);
        this.match(Token.CloseParen.class);

        var kw = this.match(Token.Keyword.class);
        if(!Objects.equals(kw.value(), "->"))
            throw new RuntimeException("aaa");

        var type = this.parseType();

        return new AST.FunctionDeclaration(
             tok.value(),
             type
        );


    }

    public Type parseType() {
        var symbol = this.match(Token.Symbol.class);
        if(symbol.value() == "C")
            return new Type.ComplexNumber();
        throw new RuntimeException("not a valid type");
    }
}

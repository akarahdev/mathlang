package net.realmofuz.runtime;

import net.realmofuz.parser.ast.AST;

import java.util.ArrayList;
import java.util.HashMap;

public class RuntimeContext {
    public ArrayList<RuntimeValue> stack = new ArrayList<>();
    public HashMap<Integer, AST.Value.FunctionValue> functions;
}

package net.realmofuz.runtime;

import net.realmofuz.parser.ast.AST;

import java.util.ArrayList;
import java.util.List;

public class CompiledFunction {
    public List<Instruction> instructions = new ArrayList<>();
    public List<AST.Value.VariableValue> params = new ArrayList<>();

    public CompiledFunction() {

    }
}

package logic;

import vk.core.api.CompilationUnit;
import vk.core.internal.InternalCompiler;
import vk.core.internal.InternalResult;
import xml.Exercise;

public class Compiler {

    public static InternalResult[] compile(Exercise exercise) {
        String test = exercise.getTestList().get().getTestContent();
        String code = exercise.getClassList().get().getClassContent();
        String name = exercise.getName();
        CompilationUnit testcu = new CompilationUnit(name + "Test", test, true);
        CompilationUnit codecu = new CompilationUnit(name + "Code", code, false);
        InternalCompiler compiler = new InternalCompiler(new CompilationUnit[] {testcu, codecu});
        compiler.compileAndRunTests();
        InternalResult[] result = {(InternalResult)compiler.getCompilerResult(),(InternalResult)compiler.getTestResult()};
        return result;
    }

}

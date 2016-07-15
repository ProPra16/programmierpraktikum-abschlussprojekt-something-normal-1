package logic;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;
import xml.Exercise;

public class Compiler {

    private static boolean compileCode(CompilationUnit codeCU) {
        JavaStringCompiler compiler = CompilerFactory.getCompiler(codeCU);
        compiler.compileAndRunTests();
        return !compiler.getCompilerResult().hasCompileErrors();
    }

    public static boolean isCompileable(Exercise exercise){
        CompilationUnit codeCU = new CompilationUnit(exercise.getClassList().get().getName(),exercise.getClassList().get().getClassContent(),false);
        return compileCode(codeCU);
    }

    public static TestResult compileAndRunTests(Exercise exercise){
        CompilationUnit codeCU = new CompilationUnit(exercise.getClassList().get().getName(),exercise.getClassList().get().getClassContent(),false);
        CompilationUnit testCU = new CompilationUnit(exercise.getTestList().get().getName(),exercise.getTestList().get().getTestContent(),true);
        JavaStringCompiler compiler = CompilerFactory.getCompiler(codeCU, testCU);
        compiler.compileAndRunTests();
        return compiler.getTestResult();
    }

}

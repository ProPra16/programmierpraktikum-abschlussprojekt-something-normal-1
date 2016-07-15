package logic;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;
import xml.Exercise;

public class Compiler {

    private static boolean compileCode(CompilationUnit codeCU) {
        JavaStringCompiler compiler = CompilerFactory.getCompiler(codeCU);
        compiler.compileAndRunTests();
        return !compiler.getCompilerResult().hasCompileErrors();
    }

    public static boolean isCompileable(Exercise exercise){
        CompilationUnit codeCU = new CompilationUnit(exercise.getClassList().get().getName(),exercise.getClassList().get().getClassContent(),false);
        if(compileCode(codeCU)) System.out.println("jop");
        return compileCode(codeCU);
    }

    public static int compileAndRunTests(Exercise exercise){
        CompilationUnit codeCU = new CompilationUnit(exercise.getClassList().get().getName(),exercise.getClassList().get().getClassContent(),false);
        CompilationUnit testCU = new CompilationUnit(exercise.getTestList().get().getName(),exercise.getTestList().get().getTestContent(),true);
        JavaStringCompiler compiler = CompilerFactory.getCompiler(codeCU, testCU);
        compiler.compileAndRunTests();
        JavaStringCompiler testcompiler = CompilerFactory.getCompiler(testCU, codeCU);
        testcompiler.compileAndRunTests();
        if(testcompiler.getTestResult() == null) return 1; else return 2;

    }

}

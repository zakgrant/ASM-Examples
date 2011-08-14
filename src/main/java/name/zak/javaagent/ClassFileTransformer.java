package name.zak.javaagent;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassFileTransformer implements java.lang.instrument.ClassFileTransformer, Opcodes {

    static final Logger logger = LoggerFactory.getLogger(ClassFileTransformer.class);

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {

        logger.info("class file transformer invoked for className: {}", className);

        if (className.equals("name/zak/user/SomeClass")) {

            ClassWriter cw = new ClassWriter(0);
            FieldVisitor fv;
            MethodVisitor mv;

            cw.visit(V1_6, ACC_PUBLIC + ACC_SUPER, "name/zak/user/SomeClass", null, "java/lang/Object", null);

            {
                fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "value", "I", null, new Integer(100));
                fv.visitEnd();
            }
            {
                mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
                mv.visitCode();
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
                mv.visitInsn(RETURN);
                mv.visitMaxs(1, 1);
                mv.visitEnd();
            }
            {
                mv = cw.visitMethod(ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null);
                mv.visitCode();
                mv.visitLdcInsn("foo");
                mv.visitInsn(ARETURN);
                mv.visitMaxs(1, 1);
                mv.visitEnd();
            }
            {
                mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getValue", "()I", null, null);
                mv.visitCode();
                mv.visitIntInsn(SIPUSH, 200);
                mv.visitInsn(IRETURN);
                mv.visitMaxs(1, 0);
                mv.visitEnd();
            }
            cw.visitEnd();

            return cw.toByteArray();
        }

        return classfileBuffer;
    }

}
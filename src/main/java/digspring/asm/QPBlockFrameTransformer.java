package digspring.asm;

import org.objectweb.asm.*;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraft.launchwrapper.IClassTransformer;

public class QPBlockFrameTransformer implements IClassTransformer, Opcodes
{
	private static final String TARGETCLASSNAME = "com.yogpc.qp.QuarryPlusI";
	public static boolean findClass;

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (!TARGETCLASSNAME.equals(transformedName)) { return basicClass;}

		try {
			DigSpringCorePlugin.logger.info("---------------------Start BlockFrame(QuarryPlus) Transform----------------------");
			ClassReader cr = new ClassReader(basicClass);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			cr.accept(new CustomVisitor(name, cw), 8);
			basicClass = cw.toByteArray();
			// 書き換えたことを記録
			findClass = true;
			DigSpringCorePlugin.logger.info("---------------------Finish BlockFrame(QuarryPlus) Transform----------------------");
		} catch (Exception e) {
			throw new RuntimeException("failed : QPBlockFrameTransformer Loading");
		}
		return basicClass;
	}

	class CustomVisitor extends ClassVisitor
	{
		String owner;
		public CustomVisitor(String owner, ClassVisitor cv)
		{
			super(Opcodes.ASM4, cv);
			this.owner = owner;
		}

		static final String targetMethodName = "preInit";

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if (targetMethodName.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc))) {
				DigSpringCorePlugin.logger.info("Transform method [preInit]");
				return new CustomMethodVisitor(this.api, super.visitMethod(access, name, desc, signature, exceptions));
			}
			return super.visitMethod(access, name, desc, signature, exceptions);
		}
	}

	/**
	 * com/yogpc/qp/block/BlockFrame を
	 * digspring/BlockFrameDigSpring に
	 * 書き換えるだけ
	 */
	class CustomMethodVisitor extends MethodVisitor {
		public CustomMethodVisitor(int api, MethodVisitor mv) {
			super(api, mv);
		}

		public void visitTypeInsn(int opcode, String type) {
			super.visitTypeInsn(opcode, checkRewrite(type));
		}

		public void visitMethodInsn(int opcode, String owner, String name,
	            String desc, boolean itf) {
			super.visitMethodInsn(opcode, checkRewrite(owner), name, desc, itf);
		}

		// 書き換え対象だったら書き換える
		public String checkRewrite(String pass) {
			if (pass.equals("com/yogpc/qp/block/BlockFrame")) {
				pass = "digspring/BlockFrameDigSpring";
			}
			return pass;
		}
	}

}

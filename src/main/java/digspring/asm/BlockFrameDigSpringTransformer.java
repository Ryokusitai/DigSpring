package digspring.asm;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.*;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class BlockFrameDigSpringTransformer implements IClassTransformer, Opcodes
{
	private static final String TARGETCLASSNAME = "digspring.BlockFrameDigSpring";

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (!TARGETCLASSNAME.equals(transformedName)) { return basicClass;}
		if (!QPBlockFrameTransformer.findClass) {
			DigSpringCorePlugin.logger.info("---------------------Not found BlockFrame(QuarryPlus)----------------------");
			return basicClass;
		}

		try {
			DigSpringCorePlugin.logger.info("---------------------Start BlockFrameDigSpring Transform----------------------");
			ClassReader cr = new ClassReader(basicClass);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			cr.accept(new CustomVisitor(name, cw), 8);
			basicClass = cw.toByteArray();
			DigSpringCorePlugin.logger.info("---------------------Finish BlockFrameDigSpring Transform----------------------");
		} catch (Exception e) {
			throw new RuntimeException("failed : BlockFrameDigSpringTransformer Loading");
		}
		return basicClass;
	}

	/**
	 * QuarryPlusが導入されている場合のみ、ここに飛んでくる(はず)
	 */
	class CustomVisitor extends ClassVisitor
	{
		String owner;
		public CustomVisitor(String owner, ClassVisitor cv)
		{
			super(Opcodes.ASM4, cv);
			this.owner = owner;
		}

		static final String targetMethodName = "<init>";

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if (targetMethodName.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc))) {
				DigSpringCorePlugin.logger.info("Transform method [<init>]");
				return new CustomMethodVisitor(this.api, super.visitMethod(access, name, desc, signature, exceptions));
			}
			return super.visitMethod(access, name, desc, signature, exceptions);
		}

		//exteds の変更(Block(ダミー)からBlockFrame)
		public void visit(int version, int access, String name, String signature,
	            String superName, String[] interfaces) {
			if (superName.equals("net/minecraft/block/Block")) {
				superName = "com/yogpc/qp/block/BlockFrame";
			}
			super.visit(version, access, name, signature, superName, interfaces);
		}

		// initメソッド(コンストラクタ)を書き換え
		public void rewriteInit() {

		}
	}

	/**
	 * super(Material.air) を
	 * super() に
	 * 書き換えるだけ
	 */
	class CustomMethodVisitor extends MethodVisitor {
		public CustomMethodVisitor(int api, MethodVisitor mv) {
			super(api, mv);
		}

		// 1回しか呼ばれないので判定とかいれない
		public void visitMethodInsn(int opcode, String owner, String name,
	            String desc, boolean itf) {
			mv.visitMethodInsn(INVOKESPECIAL, "com/yogpc/qp/block/BlockFrame", "<init>", "()V", false);
		}

		// 上が呼ばれる直前にMaterial.airを読み込もうとするので無視する
		public void visitFieldInsn(int opcode, String owner, String name,
	            String desc) {
			return;
		}
	}

}

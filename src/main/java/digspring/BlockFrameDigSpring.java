package digspring;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFrameDigSpring extends Block	//BlockFrame
{
	private int breakNum;
	private long breakTick;

	public BlockFrameDigSpring()
	{
		super(Material.air);	// super();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if (world.isRemote) {
			return;
		}

		if (breakTick != world.getTotalWorldTime() && canChainBreak(breakNum))
			removeNeighboringFrames(world, x, y, z);
		else
			this.breakTick = world.getTotalWorldTime();
	}

	protected void removeNeighboringFrames(World world, int x, int y, int z) {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			Block nBlock = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (nBlock == this) {
				world.setBlockToAir(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			}
		}
	}

	private boolean canChainBreak(int breakNum)
	{
		if (DigSpringConfig.frameDestructionRange == -1)
			return true;

		if (0 <= breakNum && breakNum < DigSpringConfig.frameDestructionRange)
		{
			this.breakNum++;
			return true;
		}
		else {
			this.breakNum = 0;
			return false;
		}
	}

}

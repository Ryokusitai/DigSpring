package digspring;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFrameDigSpring extends Block	//BlockFrame
{

	public BlockFrameDigSpring()
	{
		super(Material.air);	// super();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if (world.isRemote) {
			return;
		}

		removeNeighboringFrames(world, x, y, z);
	}

	protected void removeNeighboringFrames(World world, int x, int y, int z) {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			Block nBlock = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (nBlock == this) {
				world.setBlockToAir(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			}
		}
	}

}

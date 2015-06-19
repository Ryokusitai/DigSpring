package digspring;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class DigSpringConfig
{
	public static int frameDestructionRange;

	public static void init(File configFile)
	{
		Configuration config = new Configuration(configFile);

		try
		{
			config.load();

			frameDestructionRange = config.getInt("frameDestructionRange", "QuarryPlusFrame", 50, -1, 1000, "QuarryPlusのフレームの破壊範囲を指定してください。(-1で無制限になります)");
		}
		catch (Exception e)
		{
			DigSpring.logger.info("コンフィグファイルのロードに失敗しました");
			e.printStackTrace();
		}
		finally
		{
			if (config.hasChanged())
			{
				config.save();
			}
		}
	}

}

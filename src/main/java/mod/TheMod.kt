package mod

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent

@Mod(name = "AimBot Test", modid = "aimbottest", version = "1.0.0")
class TheMod
{
    @EventHandler
    fun init(e : FMLPostInitializationEvent)
    {
        MinecraftForge.EVENT_BUS.register(AimBot)
    }
}
package mod

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.Entity
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.hypot

fun Double.toDegrees(): Double
{
    return this * 180 / PI
}

fun EntityPlayerSP.lookAt(pos : Vector3d)
{
    val playerHeadPos = Vector3d(posX, posY + 1.625, posZ)
    val direction = pos.subtract(playerHeadPos)

    with(direction) {
        val yaw     = -atan2(x, z).toDegrees().coerceIn(-180.0, 180.0)
        val pitch   = -atan2(y, hypot(x, -z)).toDegrees()

        rotationYaw = yaw.toFloat()
        rotationPitch = pitch.toFloat()
    }
}

object AimBot
{
    private var currentEntity : Entity? = null

    private fun findTarget() : Entity?
    {
        with(Minecraft.getMinecraft()) {
            for(entity in theWorld.loadedEntityList) {
                if(entity.isDead) continue
                if(entity == thePlayer) continue
                // Whatever other conditions...

                return entity
            }
        }

        return null
    }

    private fun aimAtEntity()
    {
        val enemyPosition = with(currentEntity!!) { Vector3d(posX, posY, posZ) }
        Minecraft.getMinecraft().thePlayer.lookAt(enemyPosition)
    }

    @SubscribeEvent
    fun update(e : ClientTickEvent)
    {
        if(e.phase != TickEvent.Phase.END) return
        if(Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return

        // If we aren't locked on a valid entity, try to find one
        if(currentEntity == null || currentEntity!!.isDead)
            currentEntity = findTarget()

        // If we did find one, aim at it
        if(currentEntity != null)
            aimAtEntity()
    }
}